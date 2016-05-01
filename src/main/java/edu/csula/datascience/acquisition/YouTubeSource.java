package edu.csula.datascience.acquisition;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.*;
import com.google.api.services.youtube.model.CommentThread;
import com.google.api.services.youtube.model.CommentThreadListResponse;

import java.io.*;
import java.util.*;

/**
 * Collection of public youtube video data using the youtube api
 *
 */
public class YouTubeSource  implements Source<VideoModel> {
    String query;
    YouTube youtube;
    Properties properties;
    Stack<List<VideoModel>> videoModels;
    String pageToken;
    String commentPageToken;
    private static String FILE_PATH = "misc/youtube_key.properties";
    private static long MAX_ITEMS = 50;

    public YouTubeSource(String query) {
        this.query = query;
        this.videoModels = new Stack<>();
        this.pageToken = "";
        this.commentPageToken = "";

        // Masking developer key using a properties file
        properties = new Properties();

        try {
            System.out.println("Properties file Path: " + System.getProperty("user.dir") + FILE_PATH);
            // get the properties of the file
            properties.load(new FileInputStream(FILE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), request -> {
        }).setApplicationName("youtube-creeper-data-science").build();
        search();
    }

    /**
     *
     *
     */
    @Override
    public boolean hasNext() {
        return !videoModels.empty();
    }

    /***
     * @return TODO:: RETURN SOMEthing
     */
    @Override
    public Collection<VideoModel> next() {
        System.out.println(videoModels.peek().size());

        // to avoid downloading too much data, we download the next page
        if(pageToken != null)search();

        System.out.println(videoModels.peek().size());
        return videoModels.pop();
    }

    /**
     * Search videos based on user queries
     *
     *
     */
    private void search() {
        //while(pageToken != null) {
            try {
                // set up the search url
                YouTube.Search.List search = youtube.search().list("id,snippet");
                search.setKey(this.properties.getProperty("api_key"));
                search.setQ(this.query);
                search.setPageToken(pageToken);
                search.setMaxResults(MAX_ITEMS);

                // excecute request and get response
                SearchListResponse response = search.execute();

                // get token for the next page
                pageToken = response.getNextPageToken();

                // conver json response to a list
                List<SearchResult> searchResultList = response.getItems();
                Iterator<SearchResult> searchResultIterator = searchResultList.iterator();

                // iterate through all results
                while (searchResultIterator.hasNext()) {
                    List<VideoModel> vms = new ArrayList<>();
                    SearchResult video = searchResultIterator.next();
                    ResourceId rId = video.getId();

                    // check for only youtube videos
                    if (rId.getKind().equals("youtube#video")) {
                        System.out.println(video.getSnippet().getTitle());
                        addVideoModel(video, vms);
                    }

                    videoModels.push(vms);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
       // }
    }

    /**
     * Because of the Youtube api, we must make another request to get the statistics of a video.
     * Also, this function will set up the video model and add it to video models list.
     *
     * @param videoResult The upper level information of a video.
     */
    private void addVideoModel(SearchResult videoResult, List<VideoModel> vms) {
        try {
            // set parameters of request
            YouTube.Videos.List videos = youtube.videos().list("id,statistics");
            videos.setId(videoResult.getId().getVideoId());
            videos.setKey(properties.getProperty("api_key"));
            videos.setMaxResults(MAX_ITEMS);

            // make request and get response
            VideoListResponse responseList = videos.execute();
            List<Video> resultsList = responseList.getItems();

            if (resultsList != null) {
                // Only one item in the list because video searched bu ID.
                Video video = resultsList.get(0);
                VideoStatistics stats = video.getStatistics();
                vms.add(getVideoModel(stats, videoResult));
                //System.out.println(vms.size());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private List< List<CommentThread> > ViewerComments(SearchResult Video){
        List<List<CommentThread>> comments = new ArrayList<>();
        int counter =0;
        //iterate through all the comment pages of the ceraint vide
        while(commentPageToken != null) {
            try {
                //make a request and get a response
                CommentThreadListResponse videoComments = youtube.commentThreads()
                                .list("snippet")
                                .setKey(properties.getProperty("api_key"))
                                .setVideoId(Video.getId().getVideoId())
                                .setMaxResults((long) 100)
                                .setPageToken(commentPageToken)
                                .setTextFormat("plainText")
                                .execute();
                counter++;
                //get the next page
                commentPageToken = videoComments.getNextPageToken();
                comments.add(videoComments.getItems());
            } catch (IOException e) {
                System.out.print(e);
            }
        }

        System.out.println("Number of Comments downloaded = " + comments.size());

        // reset the comments page token back to the initial page
        commentPageToken = "";

        return  comments;
    }
    /**
     * Set up a Video model object
     *
     * @param stats the stats information for the video model
     * @param video the video inforamtion for the video model*
     * @return Video model
     */
    private VideoModel getVideoModel(VideoStatistics stats, SearchResult video) {
        // set up the video model
        VideoModel vm = new VideoModel();
        vm.id = video.getId().getVideoId();
        vm.title = video.getSnippet().getTitle();
        vm.publishedDate = video.getSnippet().getPublishedAt().toString();
        vm.commentCount = stats.getCommentCount();
        vm.viewCount = stats.getViewCount();
        vm.likeCount = stats.getLikeCount();
        vm.dislikeCount = stats.getDislikeCount();
        // check if there are not comments in the video
        if(vm.commentCount != null) {
            vm.comments = ViewerComments(video);
        }

        return vm;
    }
}

