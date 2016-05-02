package edu.csula.datascience.acquisition;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

/**
 * A mock source to provide data
 */
public class MockSource implements Source<MockData> {
    int index = 0;


//    public Collection<MockData> wrong() {
//        return Lists.newArrayList(
//                new MockData("bmw", "2", "6")
//        );
//    }

    @Override
    public boolean hasNext() {
        return index < 1;
    }

    @Override
    public Collection<MockData> next() {
        return Lists.newArrayList(
                new MockData("mygirl", "100", "10"),
                new MockData("bmw", "2", "6")
        );
    }

}
