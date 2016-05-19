//package edu.csula.datascience.examples;
//
//import com.google.gson.Gson;
//import org.apache.commons.csv.CSVFormat;
//import org.apache.commons.csv.CSVParser;
//import org.elasticsearch.action.bulk.*;
//import org.elasticsearch.action.index.IndexRequest;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.action.search.SearchType;
//import org.elasticsearch.client.Client;
//import org.elasticsearch.common.settings.Settings;
//import org.elasticsearch.common.unit.ByteSizeUnit;
//import org.elasticsearch.common.unit.ByteSizeValue;
//import org.elasticsearch.common.unit.TimeValue;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.node.Node;
//import org.elasticsearch.search.SearchHit;
//import org.elasticsearch.search.aggregations.AggregationBuilders;
//import org.elasticsearch.search.aggregations.bucket.terms.Terms;
//
//import java.io.File;
//import java.io.IOException;
//import java.net.URISyntaxException;
//import java.nio.charset.Charset;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//
//import static org.elasticsearch.node.NodeBuilder.nodeBuilder;
//
///**
// * A quick elastic search example app
// *
// * It will parse the csv file from the resource folder under main and send these
// * data to elastic search instance running locally
// *
// * After that we will be using elastic search to do full text search
//<<<<<<< HEAD
// */
//public class ElasticSearchExampleApp {
//    public static void main(String[] args) throws URISyntaxException, IOException {
//        Node node = nodeBuilder().settings(Settings.builder()
//                .put("cluster.name", "ceasar")
//            .put("path.home", "elasticsearch-data")).node();
//        Client client = node.client();
//
//=======
// *
// * gradle command to run this app `gradle esExample`
// */
//public class ElasticSearchExampleApp {
//    private final static String indexName = "bd-data";
//    private final static String typeName = "city-temperatures";
//
//    public static void main(String[] args) throws URISyntaxException, IOException {
//        Node node = nodeBuilder().settings(Settings.builder()
//            .put("cluster.name", "realEric")
//            .put("path.home", "elasticsearch-data")).node();
//        Client client = node.client();
//
//        /**
//         *
//         *
//         * INSERT data to elastic search
//         */
//
//>>>>>>> 582ddf45c0e0800fa836e6f439a1da9e9c24fd88
//        // as usual process to connect to data source, we will need to set up
//        // node and client// to read CSV file from the resource folder
//        File csv = new File(
//            ClassLoader.getSystemResource("GlobalLandTemperaturesByState.csv")
//                .toURI()
//        );
//
//        // create bulk processor
//        BulkProcessor bulkProcessor = BulkProcessor.builder(
//            client,
//            new BulkProcessor.Listener() {
//                @Override
//                public void beforeBulk(long executionId,
//                                       BulkRequest request) {
//                }
//
//                @Override
//                public void afterBulk(long executionId,
//                                      BulkRequest request,
//                                      BulkResponse response) {
//                }
//
//                @Override
//                public void afterBulk(long executionId,
//                                      BulkRequest request,
//                                      Throwable failure) {
//                    System.out.println("Facing error while importing data to elastic search");
//                    failure.printStackTrace();
//                }
//            })
//            .setBulkActions(10000)
//            .setBulkSize(new ByteSizeValue(1, ByteSizeUnit.GB))
//            .setFlushInterval(TimeValue.timeValueSeconds(5))
//            .setConcurrentRequests(1)
//            .setBackoffPolicy(
//                BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 3))
//            .build();
//
//        // Gson library for sending json to elastic search
//        Gson gson = new Gson();
//
//        try {
//            // after reading the csv file, we will use CSVParser to parse through
//            // the csv files
//            CSVParser parser = CSVParser.parse(
//                csv,
//                Charset.defaultCharset(),
//                CSVFormat.EXCEL.withHeader()
//            );
//
//<<<<<<< HEAD
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//
//=======
//>>>>>>> 582ddf45c0e0800fa836e6f439a1da9e9c24fd88
//            // for each record, we will insert data into Elastic Search
//            parser.forEach(record -> {
//                // cleaning up dirty data which doesn't have time or temperature
//                if (
//                    !record.get("dt").isEmpty() &&
//<<<<<<< HEAD
//                        !record.get("AverageTemperature").isEmpty()
//                    ) {
//                    Temperature temp = new Temperature(
//                        LocalDate.parse(record.get("dt"), formatter),
//=======
//                    !record.get("AverageTemperature").isEmpty()
//                ) {
//                    Temperature temp = new Temperature(
//                        record.get("dt"),
//>>>>>>> 582ddf45c0e0800fa836e6f439a1da9e9c24fd88
//                        Double.valueOf(record.get("AverageTemperature")),
//                        record.get("State"),
//                        record.get("Country")
//                    );
//
//<<<<<<< HEAD
//                    bulkProcessor.add(new IndexRequest("bd-example", "temperatures")
//=======
//                    bulkProcessor.add(new IndexRequest(indexName, typeName)
//>>>>>>> 582ddf45c0e0800fa836e6f439a1da9e9c24fd88
//                        .source(gson.toJson(temp))
//                    );
//                }
//            });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//<<<<<<< HEAD
//        // simple search by field name "state" and find Washington
//        SearchResponse response = client.prepareSearch("bd-example")
//            .setTypes("temperatures")
//            .setSearchType(SearchType.DEFAULT)
//            .setQuery(QueryBuilders.matchQuery("state", "Washington"))   // Query
//            .setScroll(new TimeValue(60000))
//            .setSize(60).setExplain(true)
//            .execute()
//            .actionGet();
//
//        //Scroll until no hits are returned
//        while (true) {
//
//            for (SearchHit hit : response.getHits().getHits()) {
//                System.out.println(hit.sourceAsString());
//            }
//            response = client
//                .prepareSearchScroll(response.getScrollId())
//                .setScroll(new TimeValue(60000))
//                .execute()
//                .actionGet();
//            //Break condition: No hits are returned
//            if (response.getHits().getHits().length == 0) {
//                break;
//            }
//        }
//
//        SearchResponse sr = node.client().prepareSearch("bd-example")
//            .setTypes("temperatures")
//=======
//        /**
//         * Structured search
//         */
//
//        // simple search by field name "state" and find Washington
////        SearchResponse response = client.prepareSearch(indexName)
////            .setTypes(typeName)
////            .setSearchType(SearchType.DEFAULT)
////            .setQuery(QueryBuilders.matchQuery("state", "Washington"))   // Query
////            .setScroll(new TimeValue(60000))
////            .setSize(60).setExplain(true)
////            .execute()
////            .actionGet();
////
////        //Scroll until no hits are returned
////        while (true) {
////
////            for (SearchHit hit : response.getHits().getHits()) {
////                System.out.println(hit.sourceAsString());
////            }
////            response = client
////                .prepareSearchScroll(response.getScrollId())
////                .setScroll(new TimeValue(60000))
////                .execute()
////                .actionGet();
////            //Break condition: No hits are returned
////            if (response.getHits().getHits().length == 0) {
////                break;
////            }
////        }
////
//        /**
//         * AGGREGATION
//         */
//        SearchResponse sr = node.client().prepareSearch(indexName)
//            .setTypes(typeName)
//>>>>>>> 582ddf45c0e0800fa836e6f439a1da9e9c24fd88
//            .setQuery(QueryBuilders.matchAllQuery())
//            .addAggregation(
//                AggregationBuilders.terms("stateAgg").field("state")
//                    .size(Integer.MAX_VALUE)
//            )
//            .execute().actionGet();
//
//        // Get your facet results
//        Terms agg1 = sr.getAggregations().get("stateAgg");
//
//        for (Terms.Bucket bucket: agg1.getBuckets()) {
//            System.out.println(bucket.getKey() + ": " + bucket.getDocCount());
//        }
//    }
//
//    static class Temperature {
//<<<<<<< HEAD
//        final LocalDate date;
//=======
//        final String date;
//>>>>>>> 582ddf45c0e0800fa836e6f439a1da9e9c24fd88
//        final double averageTemperature;
//        final String state;
//        final String country;
//
//<<<<<<< HEAD
//        public Temperature(LocalDate date, double averageTemperature, String state, String country) {
//=======
//        public Temperature(String date, double averageTemperature, String state, String country) {
//>>>>>>> 582ddf45c0e0800fa836e6f439a1da9e9c24fd88
//            this.date = date;
//            this.averageTemperature = averageTemperature;
//            this.state = state;
//            this.country = country;
//        }
//    }
//}
