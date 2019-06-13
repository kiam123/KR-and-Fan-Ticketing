package ncku.edu.tw.kr_and_fan_ticketing.Data;

public class FavoriteItem {
    String mAirName;
    String mPrice;
    String mFromCountry;
    String mToCountry;
    String mFromTime;
    String mToTime;
    String mFromRangePrice;
    String mToRangePrice;

    public FavoriteItem(String mAirName, String mPrice, String mFromCountry, String mToCountry, String mFromTime, String mToTime, String mFromRangePrice, String mToRangePrice) {
        this.mAirName = mAirName;
        this.mPrice = mPrice;
        this.mFromCountry = mFromCountry;
        this.mToCountry = mToCountry;
        this.mFromTime = mFromTime;
        this.mToTime = mToTime;
        this.mFromRangePrice = mFromRangePrice;
        this.mToRangePrice = mToRangePrice;
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

    public String getmFromRangePrice() {
        return mFromRangePrice;
    }

    public String getmToRangePrice() {
        return mToRangePrice;
    }

    public void setmFromRangePrice(String mFromRangePrice) {
        this.mFromRangePrice = mFromRangePrice;
    }

    public void setmToRangePrice(String mToRangePrice) {
        this.mToRangePrice = mToRangePrice;
    }
}
