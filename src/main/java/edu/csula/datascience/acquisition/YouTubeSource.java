package edu.csula.datascience.acquisition;

        import com.google.api.client.googleapis.json.GoogleJsonResponseException;
        import com.google.api.client.http.HttpRequest;
        import com.google.api.client.http.HttpRequestInitializer;
        import com.google.api.services.youtube.YouTube;
        import com.google.api.services.youtube.model.ResourceId;
        import com.google.api.services.youtube.model.SearchListResponse;
        import com.google.api.services.youtube.model.SearchResult;
        import com.google.api.services.youtube.model.Thumbnail;
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

        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.util.ArrayList;
        import java.util.Iterator;
        import java.util.List;
        import java.util.Properties;
        import java.util.regex.Matcher;
        import java.util.regex.Pattern;

public class YouTubeSource  implements Source<ArrayList<Integer>> {
    @Override
    public boolean hasNext(){
        return true;
    }

    @Override
    public 
}