package edu.csula.datascience.acquisition;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import twitter4j.Status;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An example of Collector implementation using YouTube with MongoDB Java driver
 */
public class YouTubeCollector implements Collector<VideoModel, VideoModel> {
    MongoClient mongoClient;
    MongoDatabase database;
    MongoCollection<Document> collection;
    public YouTubeCollector() {
        // establish database connection to MongoDB
        mongoClient = new MongoClient();

        // select `bd-example` as testing database
        database = mongoClient.getDatabase("masd");

        // select collection by name `tweets`
        collection = database.getCollection("videos");
    }
    @Override
    public Collection<VideoModel> mungee(Collection<VideoModel> src) {
        return src;
    }

    @Override
    public void save(Collection<VideoModel> data) {
        List<Document> documents = data.stream()
                .map(item -> new Document()
                        .append("title", "ToDO")
                        .append("publishedDate", "ToDO")
                        .append("dislikeCount", item.dislikeCount.intValue())
                        .append("commentCount", item.commentCount.intValue())
                        .append("viewCount", item.viewCount.intValue())
                        .append("likeCount", item.likeCount.intValue()))
                .collect(Collectors.toList());

        collection.insertMany(documents);
    }
}
