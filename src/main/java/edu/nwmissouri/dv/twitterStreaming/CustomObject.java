package edu.nwmissouri.dv.twitterStreaming;

public class CustomObject {
    private String countryName;
    private int position;
    private String name;
    private int tweetCount;

    public CustomObject(String countryName, int top, String name, int tweetCount) {
        this.countryName = countryName;
        this.position = top;
        this.name = name;
        this.tweetCount = tweetCount;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
    //    private TrendingNow trendingNow;
//
//    public CustomObject(TrendingNow trendingNow) {
//        this.trendingNow = trendingNow;
//    }
//
//    public TrendingNow getTrendingNow() {
//        return trendingNow;
//    }
//
//    public void setTrendingNow(TrendingNow trendingNow) {
//        this.trendingNow = trendingNow;
//    }
//
//    public CustomObject() {
//    }
//
//    @Override
//    public String toString() {
//        return "CustomObject{" +
//                "trendingNow=" + trendingNow +
//                '}';
//    }

    //    public CustomObject(String name, String text, int count) {
//        this.name = name;
//        this.text = text;
//        this.count = count;
//    }
//
//    public CustomObject(String name, int tweetCount) {
//        this.name = name;
//        this.tweetCount = tweetCount;
//    }


    public CustomObject(int top, String name, int tweetCount) {
        this.position = top;
        this.name = name;
        this.tweetCount = tweetCount;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    //
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
//
//    public String getText() {
//        return text;
//    }
//
//    public void setText(String text) {
//        this.text = text;
//    }
//


    public int getTweetCount() {
        return tweetCount;
    }

    public void setTweetCount(int tweetCount) {
        this.tweetCount = tweetCount;
    }
}
