package edu.csula.datascience.acquisition;

/**
 * A simple model for testing
 */
public class SimpleModel {
    private final String id;
    private final String content;

    public SimpleModel(String id, String content) {
        this.id = id;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }


}
