package edu.csula.datascience.acquisition;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.*;

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
    Stack<VideoModel> videoModels;
    String pageToken;
    private static String FILE_PATH = "misc/youtube_key.properties";
    private static long MAX_ITEMS = 50;

    public YouTubeSource(String query){
        this.query = query;
        this.videoModels = new Stack<>();
        this.pageToken = "";
        // Masking developer key using a properties file
        properties = new Properties();
        try {
            System.out.println("Properties file Path: " + System.getProperty("user.dir") + FILE_PATH);
            properties.load(new FileInputStream(FILE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), request -> {
        }).setApplicationName("youtube-creeper-data-science").build();

        while(pageToken != null)
             search();

    }

    /**
     *
     *
     */
    @Override
    public boolean hasNext(){
        return !videoModels.empty();
    }

    /***
     *
     * @return TODO:: RETURN SOMEthing
     */
    @Override
    public Collection<VideoModel> next(){
        return videoModels;
    }

    private void search(){
        try {
            YouTube.Search.List search = youtube.search().list("id,snippet");
            search.setKey(this.properties.getProperty("api_key"));
            search.setQ(this.query);
            search.setOrder("viewcount");
            if(!pageToken.equals("")){
                search.setPageToken(pageToken);
            }

            search.setMaxResults(MAX_ITEMS);
            // Call the API and print results.
            SearchListResponse response = search.execute();
            //get page token from response
            pageToken = response.getNextPageToken();
            List<SearchResult> searchResultList = response.getItems();
            Iterator<SearchResult> searchResultIterator = searchResultList.iterator();
            int counter  =0;
            while(searchResultIterator.hasNext()){
                //System.out.println(counter++);
                SearchResult video = searchResultIterator.next();

                ResourceId rId = video.getId();
                //determine if the video is a video
                if (rId.getKind().equals("youtube#video")) {
                    addVideoModel(video);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Because of the Youtube api, we must make another request to get the statistics of a video.
     * Also, this function will set up the video model and add it to video models list.
     *
     * @param videoResult The upper level information of a video.
     */
    private void addVideoModel(SearchResult videoResult){
        try {
            // set parameters of request
            YouTube.Videos.List videos = youtube.videos().list("id,statistics");
            videos.setId(videoResult.getId().getVideoId());
            videos.setKey(properties.getProperty("api_key"));
            videos.setMaxResults(MAX_ITEMS);

            // make request and get response
            VideoListResponse responseList = videos.execute();
            List<Video> resultsList = responseList.getItems();

            if(resultsList != null) {
                // Only one item in the list because video searched bu ID.
                Video video = resultsList.get(0);
                VideoStatistics stats = video.getStatistics();
                videoModels.push(getVideoModel(stats, videoResult));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set up a Video model object
     *
     * @param stats the stats information for the video model
     * @param video the video inforamtion for the video model*
     *@return Video model
    */
    private VideoModel getVideoModel(VideoStatistics stats, SearchResult video){
        VideoModel vm = new VideoModel();
//        System.out.println(vm);
//        System.out.println(video);
        vm.id = video.getId().getVideoId();
        vm.title = video.getSnippet().getTitle();
        vm.publishedDate = video.getSnippet().getPublishedAt().toString();
        vm.commentCount = stats.getCommentCount();
        vm.viewCount = stats.getViewCount();
        //System.out.println(vm.viewCount);
        // null pointer here
        vm.likeCount = stats.getLikeCount();
        vm.dislikeCount = stats.getDislikeCount();
        return vm;
    }

}