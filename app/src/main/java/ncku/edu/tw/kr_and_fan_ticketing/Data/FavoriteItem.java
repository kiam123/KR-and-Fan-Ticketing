package ncku.edu.tw.kr_and_fan_ticketing.Data;

import java.io.Serializable;

public class FavoriteItem implements Serializable {
    String mAirName;
    String mDate;
    String mPrice;
    String mFromCountry;
    String mToCountry;
    String mFromTime;
    String mToTime;
    String mFromRangePrice;
    String mToRangePrice;
    String mUrlImage;

    public FavoriteItem(String mAirName, String mDate, String mPrice, String mFromCountry, String mToCountry, String mFromTime, String mToTime, String mFromRangePrice, String mToRangePrice, String mUrlImage) {
        this.mAirName = mAirName;
        this.mDate = mDate;
        this.mPrice = mPrice;
        this.mFromCountry = mFromCountry;
        this.mToCountry = mToCountry;
        this.mFromTime = mFromTime;
        this.mToTime = mToTime;
        this.mFromRangePrice = mFromRangePrice;
        this.mToRangePrice = mToRangePrice;
        this.mUrlImage = mUrlImage;
    }

    public String getmAirName() {
        return mAirName;
    }

    public String getmDate() {
        return mDate;
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

    public String getmUrlImage() {
        return mUrlImage;
    }

    public String getId() {
        return mFromCountry + mToCountry + mDate + mAirName + mFromTime;
    }

    public void setmFromRangePrice(String mFromRangePrice) {
        this.mFromRangePrice = mFromRangePrice;
    }

    public void setmToRangePrice(String mToRangePrice) {
        this.mToRangePrice = mToRangePrice;
    }
}
