package edu.csula.datascience.acquisition;

        import com.google.api.client.googleapis.json.GoogleJsonResponseException;
        import com.google.api.client.http.HttpRequest;
        import com.google.api.client.http.HttpRequestInitializer;
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

public class YouTubeSource  implements Source<CommentThread> {

    YouTube youtube;
    Properties properties;

    private static String FILE_PATH = "misc/youtube_key.properties";

//    public static void main(String[] args){
//        YouTubeSource yts = new YouTubeSource();
//    }


    public YouTubeSource(String query){
        // Masking developer key using a properties file
        properties = new Properties();
        try {
            System.out.println(System.getProperty("user.dir"));
            properties.load(new FileInputStream(FILE_PATH));
        } catch (IOException e) {
            System.err.println("There was an error reading " + FILE_PATH + ": " + e.getCause()
                    + " : " + e.getMessage());
            System.exit(1);
        }

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
    public Collection<CommentThread> next(){
        return null;
    }
}