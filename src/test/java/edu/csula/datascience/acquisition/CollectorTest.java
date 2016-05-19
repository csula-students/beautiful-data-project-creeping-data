package edu.csula.datascience.acquisition;

import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

/**
 * A test case to show how to use Collector and Source
 */
public class CollectorTest {
<<<<<<< HEAD
    private Collector<MockData, MockData> collector;
=======
    private Collector<SimpleModel, MockData> collector;
>>>>>>> 582ddf45c0e0800fa836e6f439a1da9e9c24fd88
    private Source<MockData> source;

    @Before
    public void setup() {
        collector = new MockCollector();
        source = new MockSource();
    }

    @Test
<<<<<<< HEAD
    public void mungeeCorrect() throws Exception {
        List<MockData> list = (List<MockData>) collector.mungee(source.next());
        List<MockData> expectedList = Lists.newArrayList(
                new MockData("mygirl", "100", "10"),
                new MockData("bmw", "2", "6")

=======
    public void mungee() throws Exception {
        List<SimpleModel> list = (List<SimpleModel>) collector.mungee(source.next());
        List<SimpleModel> expectedList = Lists.newArrayList(
            new SimpleModel("2", "content2"),
            new SimpleModel("3", "content3")
>>>>>>> 582ddf45c0e0800fa836e6f439a1da9e9c24fd88
        );

        Assert.assertEquals(list.size(), 2);

        for (int i = 0; i < 2; i ++) {
<<<<<<< HEAD
            Assert.assertEquals(list.get(i).title, expectedList.get(i).title);
            Assert.assertEquals(list.get(i).likecount, expectedList.get(i).likecount);
            Assert.assertEquals(list.get(i).viewCount, expectedList.get(i).viewCount);
        }
    }

    @Test
    public void mungeeWrong1() throws Exception {
        List<MockData> list = (List<MockData>) collector.mungee(source.next());
        List<MockData> expectedList = Lists.newArrayList(
                new MockData("bmw", "2", "6")
        );
        list.remove(0);


        Assert.assertEquals(list.size(), 1);

        for (int i = 0; i < 1; i ++) {
            Assert.assertEquals(list.get(i).title, expectedList.get(i).title);
            Assert.assertEquals(list.get(i).likecount, expectedList.get(i).likecount);
            Assert.assertEquals(list.get(i).viewCount, expectedList.get(i).viewCount);
        }
    }

}
=======
            Assert.assertEquals(list.get(i).getId(), expectedList.get(i).getId());
            Assert.assertEquals(list.get(i).getContent(), expectedList.get(i).getContent());
        }
    }
}
>>>>>>> 582ddf45c0e0800fa836e6f439a1da9e9c24fd88
