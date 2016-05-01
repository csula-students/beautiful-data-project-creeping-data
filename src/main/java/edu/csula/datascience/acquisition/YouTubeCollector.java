package edu.csula.datascience.acquisition;

import com.google.api.services.youtube.model.CommentSnippet;
import com.google.api.services.youtube.model.CommentThread;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import twitter4j.Status;

import java.math.BigInteger;
import java.util.*;
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
        database = mongoClient.getDatabase("data-science");

        // select collection by name `tweets`
        collection = database.getCollection("videos");
    }

    @Override
    public Collection<VideoModel> mungee(Collection<VideoModel> src) {

        List<VideoModel> cleanedList = new ArrayList<>();

        for (VideoModel video: src) {

            if (video != null && video.id != null && video.title != null && video.publishedDate != null && video.dislikeCount != null &&
                    video.likeCount != null && video.commentCount != null && video.viewCount != null && !video.comments.isEmpty()) {
                cleanedList.add(video);
            }

        }
        return cleanedList;
    }

    @Override
    public void save(Collection<VideoModel> data) {
        // map to have O(1) access to a dbList based off id
        Map<String, BasicDBList> map = new HashMap<>();
        // check all video models
        for(VideoModel vm: data){
            // list of all comments for this video
            BasicDBList dbList = new BasicDBList();
            // check all comments of the video and add them into
            // the list
            for(List<CommentThread> commentList: vm.comments) {
                for (CommentThread ct : commentList) {
                    Document commentsDoc = new Document();
                    CommentSnippet cs = ct.getSnippet().getTopLevelComment().getSnippet();
                    commentsDoc.put("name", cs.getAuthorDisplayName());
                    commentsDoc.put("likeCount", cs.getLikeCount());
                    commentsDoc.put("comment", cs.getTextDisplay());
                    dbList.add(commentsDoc);
                }
            }
            map.put(vm.id, dbList);
        }

        // add all video models to mongo
        List<Document> documents = data.stream()
                .map(vm -> new Document()
                        .append("videoId", vm.id)
                        .append("title", vm.title)
                        .append("publishedDate", vm.publishedDate)
                        .append("dislikeCount", vm.dislikeCount.intValue())
                        .append("commentCount", vm.commentCount.intValue())
                        .append("viewCount", vm.viewCount.intValue())
                        .append("likeCount", vm.likeCount.intValue())
                        .append("comments", map.get(vm.id)))
                .collect(Collectors.toList());

        collection.insertMany(documents);
    }
}
