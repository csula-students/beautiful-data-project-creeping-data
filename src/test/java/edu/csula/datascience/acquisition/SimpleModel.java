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

<<<<<<< HEAD

=======
    public static SimpleModel build(MockData data) {
        return new SimpleModel(data.getId(), data.getContent());
    }
>>>>>>> 582ddf45c0e0800fa836e6f439a1da9e9c24fd88
}
