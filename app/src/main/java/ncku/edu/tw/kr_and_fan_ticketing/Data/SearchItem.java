package ncku.edu.tw.kr_and_fan_ticketing.Data;

public class SearchItem {
    String mAirName;
    String mPrice;
    String mFromCountry;
    String mToCountry;
    String mFromTime;
    String mToTime;

    public SearchItem(String mAirName, String mPrice, String mFromCountry, String mToCountry, String mFromTime, String mToTime) {
        this.mAirName = mAirName;
        this.mPrice = mPrice;
        this.mFromCountry = mFromCountry;
        this.mToCountry = mToCountry;
        this.mFromTime = mFromTime;
        this.mToTime = mToTime;
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
}
