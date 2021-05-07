package edu.nwmissouri.dv.twitterStreaming;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * Custom Producer using Kafka for messaging.
 * Reads properties from the run.properties file in
 * src/main/resources.
 */
public class CustomProducer {
    private static FileInputStream runStream = null;
    private static Properties runProperties = new Properties();

    public static void main(String[] argv) throws Exception {
        // Create an input stream for the run properties ................
        String runFile = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator
                + "resources" + File.separator + "run.properties";
        System.out.println("Reading config from " + runFile);
        runStream = new FileInputStream(runFile);

        // Load properties and display
        runProperties.load(runStream);
        System.out.println("Run properties.................");
        System.out.println("BOOTSTRAP_SERVERS_CONFIG =      " + runProperties.getProperty("BOOTSTRAP_SERVERS_CONFIG"));
        System.out.println("KEY_SERIALIZER_CLASS_CONFIG =   " + runProperties.getProperty("KEY_SERIALIZER_CLASS_CONFIG"));
        System.out.println("VALUE_SERIALIZER_CLASS_CONFIG = " + runProperties.getProperty("VALUE_SERIALIZER_CLASS_CONFIG"));
        System.out.println("TOPIC =                         " + runProperties.getProperty("TOPIC"));
        System.out.println("TWITTER_USER =                  " + runProperties.getProperty("TWITTER_USER"));
        System.out.println("DELAY_MS =                      " + runProperties.getProperty("DELAY_MS"));

        String topicName = runProperties.getProperty("TOPIC");
        String user = runProperties.getProperty("TWITTER_USER");
        int delay_ms = Integer.parseInt(runProperties.getProperty("DELAY_MS"));

        //Configure the Producer
        Properties configProperties = new Properties();
        configProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                runProperties.getProperty("BOOTSTRAP_SERVERS_CONFIG"));
        configProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                runProperties.getProperty("KEY_SERIALIZER_CLASS_CONFIG"));
        configProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                runProperties.getProperty("VALUE_SERIALIZER_CLASS_CONFIG"));
        org.apache.kafka.clients.producer.Producer<String, CustomObject> producer = new KafkaProducer<String, CustomObject>(configProperties);
//    org.apache.kafka.clients.producer.Producer<String, Integer> producer = new KafkaProducer<String, Integer>(configProperties);
//    org.apache.kafka.clients.producer.Producer<String, JSONObject> producer = new KafkaProducer<String, JSONObject>(configProperties);

//      Producer<String,CustomObject> kafkaProducer = new KafkaProducer<String,CustomObject>(props, new StringSerializer(),new KafkaJsonSerializer());

        System.out.println("==========================================");
        System.out.println("You must start a consumer to see messages.");
        System.out.println("==========================================");
        System.out.println("\nStarting custom producer..............\n");

        Twitter twitter = getTwitterinstance();


        Map<Integer, String> countries = new LinkedHashMap<>();

        countries.put(1, "	Worldwide 	");
//        countries.put(23424748, "	Australia 	");
//        countries.put(23424768, "	Brazil 	");
//        countries.put(23424775, "	Canada 	");
//        countries.put(23424819, "	France 	");
//        countries.put(23424829, "	Germany 	");
        countries.put(23424848, "	India 	");
//        countries.put(23424856, "	Japan 	");
//        countries.put(23424900, "	Mexico 	");
//        countries.put(23424916, "	New Zealand 	");
//        countries.put(23424922, "	Pakistan 	");
//        countries.put(23424936, "	Russia 	");
//        countries.put(23424938, "	Saudi Arabia 	");
//        countries.put(23424942, "	South Africa 	");
//        countries.put(23424948, "	Singapore 	");
        countries.put(23424975, "	United Kingdom 	");
        countries.put(23424977, "	United States 	");

        while (true) {
            for (Map.Entry<Integer, String> country : countries.entrySet()) {
                Trends trends = twitter.getPlaceTrends(country.getKey());
                int i = 1;
                CustomObject co;
                ResponseList<Location> locations;
                for (Trend trend : trends.getTrends()) {
                    co = new CustomObject(country.getValue(), i, trend.getName(), trend.getTweetVolume());
                    System.out.println(String.format("Country Name:" + country.getValue() + "Trend " + (i) + "  %s (tweet_volume: %d)", co.getName(), co.getTweetCount()));
                    ProducerRecord<String, CustomObject> rec = new ProducerRecord<>(topicName, co);
                    producer.send(rec);
                    i++;
                }
//                Thread.sleep(1000);
//            producer.close();
            }
            Thread.sleep(120000);
        }
    }

    public static Twitter getTwitterinstance() throws IOException {

        FileInputStream twitterStream = null;
        Properties twitterProperties = new Properties();

        String twitterFile = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main"
                + File.separator + "resources" + File.separator + "twitter4j.properties";
        System.out.println("Reading config from " + twitterFile + "\n");
        twitterStream = new FileInputStream(twitterFile);

        twitterProperties.load(twitterStream);
        System.out.println("Displaying Twitter properties:\n");
        System.out.println("  oauth.consumerKey =       " + twitterProperties.getProperty("oauth.consumerKey"));
        System.out.println("  oauth.consumerSecret =    " + twitterProperties.getProperty("oauth.consumerSecret"));
        System.out.println("  oauth.accessToken =       " + twitterProperties.getProperty("oauth.accessToken"));
        System.out.println("  oauth.accessTokenSecret = " + twitterProperties.getProperty("oauth.accessTokenSecret"));

        ConfigurationBuilder cb = new ConfigurationBuilder();

        cb.setDebugEnabled(true).setOAuthConsumerKey(twitterProperties.getProperty("oauth.consumerKey"))
                .setOAuthConsumerSecret(twitterProperties.getProperty("oauth.consumerSecret"))
                .setOAuthAccessToken(twitterProperties.getProperty("oauth.accessToken"))
                .setOAuthAccessTokenSecret(twitterProperties.getProperty("oauth.accessTokenSecret"));

        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
        System.out.println("\nReturning twitter instance ..............\n");
        return twitter;
    }


    public static List<Integer> getPlaceTrends() {
        List<Integer> list = new ArrayList<>();

        try {
            Twitter twitter = new TwitterFactory().getInstance();
            ResponseList<Location> locations;
            locations = twitter.getAvailableTrends();
            System.out.println("Showing available trends");
            for (Location location : locations) {
                System.out.println(location.getName() + " (woeid:" + location.getWoeid() + ")");
                list.add(location.getWoeid());
            }
            System.out.println("done.");
//            System.exit(0);
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get trends: " + te.getMessage());
//            System.exit(-1);
        }
        return list;
    }

    public static List<TrendingNow> getAvailableTrends(int woe) {
        List<TrendingNow> trendingNowList = new ArrayList<>();
        try {
            int woeid = woe;
            Twitter twitter = new TwitterFactory().getInstance();
            Trends trends = twitter.getPlaceTrends(woeid);
            System.out.println("Showing trends for " + trends.getLocation().getName());

            TrendingNow trendingNow;
            for (Trend trend : trends.getTrends()) {
                System.out.println(String.format("%s (tweet_volume: %d)", trend.getName(), trend.getTweetVolume()));
                trendingNow = new TrendingNow(trend.getName(), trend.getTweetVolume());
                trendingNowList.add(trendingNow);
            }

            System.out.println("done.");
//            System.exit(0);
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get trends: " + te.getMessage());
            System.exit(-1);
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
            System.out.println("WOEID must be number");
            System.exit(-1);
        }
        return trendingNowList;

    }

}
