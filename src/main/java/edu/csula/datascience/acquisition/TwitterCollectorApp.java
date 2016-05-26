package edu.csula.datascience.acquisition;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import org.bson.Document;
import twitter4j.Status;

import java.util.Collection;

/**
 * A simple example of using Twitter
 */
public class TwitterCollectorApp {
    public static void main(String[] args) {
        FindIterable cursor = new VideoNameCollector().getFindIterable();

        cursor.forEach((Block<? super Document>) doc -> {

            TwitterSource source = new TwitterSource(
                    Long.MAX_VALUE,
                    "#" + doc.get("title").toString()
            );

            TwitterCollector collector = new TwitterCollector();

            while (source.hasNext()) {
                Collection<Status> tweets = source.next();
                Collection<Status> cleanedTweets = collector.mungee(tweets);
                collector.save(cleanedTweets);
            }
        });
    }
}
