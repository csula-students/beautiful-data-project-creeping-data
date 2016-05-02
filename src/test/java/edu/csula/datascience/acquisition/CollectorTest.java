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
    private Collector<MockData, MockData> collector;
    private Source<MockData> source;

    @Before
    public void setup() {
        collector = new MockCollector();
        source = new MockSource();
    }

    @Test
    public void mungeeCorrect() throws Exception {
        List<MockData> list = (List<MockData>) collector.mungee(source.next());
        List<MockData> expectedList = Lists.newArrayList(
                new MockData("mygirl", "100", "10"),
                new MockData("bmw", "2", "6")

        );

        Assert.assertEquals(list.size(), 2);

        for (int i = 0; i < 2; i ++) {
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
