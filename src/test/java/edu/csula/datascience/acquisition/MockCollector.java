package edu.csula.datascience.acquisition;

<<<<<<< HEAD
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
=======
import java.util.Collection;
>>>>>>> 582ddf45c0e0800fa836e6f439a1da9e9c24fd88
import java.util.stream.Collectors;

/**
 * A mock implementation of collector for testing
 */
<<<<<<< HEAD
public class MockCollector implements Collector<MockData, MockData> {
    @Override
    public Collection<MockData> mungee(Collection<MockData> src) {

        List<MockData> cleanedList = new ArrayList<>();

        for (MockData video: src) {

            if (video.title != null && video.likecount != null && video.viewCount != null) {
                cleanedList.add(video);
            }

        }
        return cleanedList;
    }
    @Override
    public void save(Collection<MockData> data) {
=======
public class MockCollector implements Collector<SimpleModel, MockData> {
    @Override
    public Collection<SimpleModel> mungee(Collection<MockData> src) {
        // in your example, you might need to check src.hasNext() first
        return src
            .stream()
            .filter(data -> data.getContent() != null)
            .map(SimpleModel::build)
            .collect(Collectors.toList());
    }

    @Override
    public void save(Collection<SimpleModel> data) {
>>>>>>> 582ddf45c0e0800fa836e6f439a1da9e9c24fd88
    }
}
