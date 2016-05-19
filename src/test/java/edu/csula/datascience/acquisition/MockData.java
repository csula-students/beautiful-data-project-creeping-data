package edu.csula.datascience.acquisition;

<<<<<<< HEAD
import com.google.api.services.youtube.YouTube;
import com.google.common.collect.Interner;

import java.util.Properties;
import java.util.Stack;

=======
>>>>>>> 582ddf45c0e0800fa836e6f439a1da9e9c24fd88
/**
 * Mock raw data
 */
public class MockData {
<<<<<<< HEAD
    String title;
    String likecount;
    String viewCount;

    public MockData(String title, String likecount, String viewCount) {
        this.title =  title;
        this.likecount = likecount;
        this.viewCount = viewCount;
    }

    public String getQuery() { return title; }

    public String getYoutube()
    {
        return likecount;
    }
    public String getPageToken()
    {
        return viewCount;
    }

   }
=======
    private final String id;
    private final String content;

    public MockData(String id, String content) {
        this.id = id;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
>>>>>>> 582ddf45c0e0800fa836e6f439a1da9e9c24fd88
