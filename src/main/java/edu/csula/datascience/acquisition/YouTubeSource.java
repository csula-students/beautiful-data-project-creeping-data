package edu.csula.datascience.acquisition;

        import com.google.api.client.googleapis.json.GoogleJsonResponseException;
        import com.google.api.client.http.HttpRequest;
        import com.google.api.client.http.HttpRequestInitializer;
        import com.google.api.client.http.javanet.NetHttpTransport;
        import com.google.api.client.json.jackson2.JacksonFactory;
        import com.google.api.services.youtube.YouTube;
        import com.google.api.services.youtube.model.*;
        import org.apache.http.HttpEntity;
        import org.apache.http.HttpResponse;
        import org.apache.http.NameValuePair;
        import org.apache.http.client.HttpClient;
        import org.apache.http.client.methods.HttpGet;
        import org.apache.http.client.utils.URLEncodedUtils;
        import org.apache.http.impl.client.DefaultHttpClient;
        import org.apache.http.message.BasicNameValuePair;
        import org.codehaus.jackson.JsonNode;
        import org.codehaus.jackson.node.ArrayNode;

        import java.io.*;
        import java.util.*;
        import java.util.regex.Matcher;
        import java.util.regex.Pattern;

public class YouTubeSource  implements Source<SearchResult> {
    String query;
    YouTube youtube;
    Properties properties;
    List<SearchResult> searchResultList;

    private static String FILE_PATH = "misc/youtube_key.properties";
    private static long MAX_ITEMS = 2;

    public static void main(String[] args){
        String q = "dog";
        YouTubeSource yts = new YouTubeSource(q);
    }

    public YouTubeSource(String query){
        this.query = query;

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

        search();

    }


    /**
     * @return if there is something else to do.
     */
    @Override
    public boolean hasNext(){
        return true;
    }


    /***
     *
     * @return TODO:: RETURN SOMEthing
     */
    @Override
    public Collection<SearchResult> next(){
        List<SearchResult> searchResultList = null;
        try {
            YouTube.Search.List search = youtube.search().list("id,snippet");
            search.setKey(this.properties.getProperty("api_key"));
            search.setQ(this.query);
            search.setMaxResults(MAX_ITEMS);
            // Call the API and print results.
            SearchListResponse response = search.execute();
            searchResultList = response.getItems();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return searchResultList;
    }

    public void search(){
        List<SearchResult> searchResultList = null;
        try {
            YouTube.Search.List search = youtube.search().list("id,snippet");
            search.setKey(this.properties.getProperty("api_key"));
            search.setQ(this.query);
            search.setMaxResults(MAX_ITEMS);
            // Call the API and print results.
            SearchListResponse response = search.execute();
            searchResultList = response.getItems();
            Iterator<SearchResult> searchResultIterator = searchResultList.iterator();
            while(searchResultIterator.hasNext()){
                SearchResult video = searchResultIterator.next();
                ResourceId rId = video.getId();
                //determine if the video is a video
                if (rId.getKind().equals("youtube#video")) {
                    Thumbnail thumbnail = video.getSnippet().getThumbnails().getDefault();

                    video.getSnippet().getDescription();
                    System.out.println(" Video Id" + rId.getVideoId());
                    System.out.println(" Title: " + video.getSnippet().getTitle());
                    System.out.println(" Description: " + video.getSnippet().);
                    System.out.println(" Thumbnail: " + thumbnail.getUrl());
                    System.out.println("\n-------------------------------------------------------------\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}