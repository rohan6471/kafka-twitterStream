package edu.nwmissouri.dv.twitterStreaming;

public class TredingCountryName {
    String country;
    int woeIDnumber;

    public TredingCountryName(String country, int woeIDnumber) {
        this.country = country;
        this.woeIDnumber = woeIDnumber;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getWoeIDnumber() {
        return woeIDnumber;
    }

    public void setWoeIDnumber(int woeIDnumber) {
        this.woeIDnumber = woeIDnumber;
    }
}
