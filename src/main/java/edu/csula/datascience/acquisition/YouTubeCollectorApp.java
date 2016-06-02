package edu.csula.datascience.acquisition;

import twitter4j.Status;

import java.util.Collection;

/**
 * A simple example of using YouTube
 */
public class YouTubeCollectorApp {

    public static void main(String[] args) {
        String queries = "video review funny fail tutorial movie makeup game school math music minecraft movies drake beyonce frozen happy ariana grande adele buzzfeed chris brown eminem ellen show future hit i justin kevin kodak love hate why do people me you no yes one panda queen laugh try uber vine compilation work youtube zoo";
        String[] queriesSplit = queries.split(" ");
        for(String query: queriesSplit) {
            System.out.println(query);
            YouTubeSource source = new YouTubeSource(query);
            YouTubeCollector collector = new YouTubeCollector();
            Collection<VideoModel> videosList = null;
            videosList = source.next();
            Collection<VideoModel> cleanedList = collector.mungee(videosList);
            //System.out.println(cleanedList.size());
            //System.out.println("Size = " + cleanedList.size());
            if (videosList.size() > 0) {
                collector.save(cleanedList);
                System.out.println("Save");
            }
        }
    }

}
