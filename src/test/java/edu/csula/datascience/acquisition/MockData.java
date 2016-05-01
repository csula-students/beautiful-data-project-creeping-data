package edu.csula.datascience.acquisition;

import com.google.api.services.youtube.YouTube;
import com.google.common.collect.Interner;

import java.util.Properties;
import java.util.Stack;

/**
 * Mock raw data
 */
public class MockData {
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
