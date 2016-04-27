package edu.csula.datascience.acquisition;

import twitter4j.Status;

import java.util.Collection;

/**
 * A simple example of using YouTube
 */
public class YouTubeCollectorApp {

    public static void main(String[] args) {

        // TODO: use this to return videos containing very high view counts
        YouTubeSource source = new YouTubeSource();
        YouTubeCollector collector = new YouTubeCollector();

        while (source.hasNext()) {

            // TODO: get video data
            Collection<Status> videos = source.next();
            Collection<Status> cleanedVideos = collector.mungee(videos);
            collector.save(cleanedVideos);
        }

    }

}
