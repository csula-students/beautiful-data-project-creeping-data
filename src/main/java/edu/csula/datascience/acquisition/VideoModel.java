package edu.csula.datascience.acquisition;

import com.google.api.services.youtube.model.CommentThread;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ceejay562 on 4/28/2016.
 */
public class VideoModel {
    // snippets
    public String id;
    public String title;
    public String publishedDate;

    // Statistics
    public BigInteger dislikeCount;
    public BigInteger likeCount;
    public BigInteger commentCount;
    public BigInteger viewCount;

    public List<List<CommentThread>> comments;

    public VideoModel(){
        comments = new ArrayList<>();
    }

    /**
     * TODO finish this part
     *
     * @return The string representation of the model
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("Title=");
        sb.append(title);
        sb.append(",PublishedData=");
        sb.append(publishedDate);
        sb.append("}");
        return sb.toString();
    }

}
