package edu.csula.datascience.acquisition;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;



public class VideoNameCollector {

    public static void main(String[] args){
        
    }


    public FindIterable getFindIterable(){
        // establish database connection to MongoDB
        MongoClient mongoClient = new MongoClient();
        // select `bd-example` as testing database
        MongoDatabase database = mongoClient.getDatabase("data-science");
        // get collection of database
        MongoCollection<Document> collection = database.getCollection("videos");

        FindIterable cursor =  collection.find();
        cursor.forEach((Block<? super Document>)d ->{
            System.out.println(d.get("title").toString());
        });
        return cursor;
    }

}
