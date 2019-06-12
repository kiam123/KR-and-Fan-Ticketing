package ncku.edu.tw.kr_and_fan_ticketing.Data;

import java.io.Serializable;

public class SearchItem implements Serializable {
    String mAirName;
    String mPrice;
    String mFromCountry;
    String mToCountry;
    String mFromTime;
    String mToTime;
    boolean mSubscription;

    public SearchItem(String mAirName, String mPrice, String mFromCountry, String mToCountry, String mFromTime, String mToTime, boolean mSubscription) {
        this.mAirName = mAirName;
        this.mPrice = mPrice;
        this.mFromCountry = mFromCountry;
        this.mToCountry = mToCountry;
        this.mFromTime = mFromTime;
        this.mToTime = mToTime;
        this.mSubscription = mSubscription;
    }

    public String getmAirName() {
        return mAirName;
    }

    public String getmPrice() {
        return mPrice;
    }

    public String getmFromCountry() {
        return mFromCountry;
    }

    public String getmToCountry() {
        return mToCountry;
    }

    public String getmFromTime() {
        return mFromTime;
    }

    public String getmToTime() {
        return mToTime;
    }

    public boolean ismSubscription() {
        return mSubscription;
    }
}
