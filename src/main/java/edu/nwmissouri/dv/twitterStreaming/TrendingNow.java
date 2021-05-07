package edu.nwmissouri.dv.twitterStreaming;

public class TrendingNow {
    String name;
    int volume;

    public TrendingNow(String name, int volume) {
        this.name = name;
        this.volume = volume;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TrendingNow{" +
                "name='" + name + '\'' +
                ", volume=" + volume +
                '}';
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }
}
