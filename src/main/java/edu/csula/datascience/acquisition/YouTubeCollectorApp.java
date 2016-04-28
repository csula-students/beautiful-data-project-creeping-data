package edu.csula.datascience.acquisition;

import twitter4j.Status;

import java.util.Collection;

/**
 * A simple example of using YouTube
 */
public class YouTubeCollectorApp {

    public static void main(String[] args) {
        YouTubeSource source = new YouTubeSource("Batman");
        YouTubeCollector collector = new YouTubeCollector();
        //while(source.hasNext()){
            Collection<VideoModel> videosList = source.next();
            Collection<VideoModel> cleanedList = collector.mungee(videosList);
            collector.save(cleanedList);
        //}
    }

}
