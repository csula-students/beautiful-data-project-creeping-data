package edu.csula.datascience.acquisition;

import twitter4j.Status;

import java.util.Collection;

/**
 * A simple example of using YouTube
 */
public class YouTubeCollectorApp {

    public static void main(String[] args) {
        YouTubeSource source = new YouTubeSource("trailer");
        YouTubeCollector collector = new YouTubeCollector();
        Collection<VideoModel> videosList = null;
        while(source.hasNext()){
            videosList = source.next();
            Collection<VideoModel> cleanedList = collector.mungee(videosList);
            System.out.println(cleanedList.size());
            System.out.println("RUNINGSSSSSSSS");
            collector.save(cleanedList);
        }
    }

}
