package edu.csula.datascience.acquisition;

import com.google.common.collect.Lists;

<<<<<<< HEAD
import java.util.ArrayList;
=======
>>>>>>> 582ddf45c0e0800fa836e6f439a1da9e9c24fd88
import java.util.Collection;
import java.util.function.Consumer;

/**
 * A mock source to provide data
 */
public class MockSource implements Source<MockData> {
    int index = 0;

<<<<<<< HEAD

//    public Collection<MockData> wrong() {
//        return Lists.newArrayList(
//                new MockData("bmw", "2", "6")
//        );
//    }

=======
>>>>>>> 582ddf45c0e0800fa836e6f439a1da9e9c24fd88
    @Override
    public boolean hasNext() {
        return index < 1;
    }

    @Override
    public Collection<MockData> next() {
        return Lists.newArrayList(
<<<<<<< HEAD
                new MockData("mygirl", "100", "10"),
                new MockData("bmw", "2", "6")
        );
    }

=======
            new MockData("1", null),
            new MockData("2", "content2"),
            new MockData("3", "content3")
        );
    }
>>>>>>> 582ddf45c0e0800fa836e6f439a1da9e9c24fd88
}
