package edu.csula.datascience.acquisition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A mock implementation of collector for testing
 */
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
    }
}
