package ncku.edu.tw.kr_and_fan_ticketing.Service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.Serializable;

public class SearchHelper extends SQLiteOpenHelper implements Serializable {
    public static final String DATABASE_NAME = "favorite.db";
    public static final String TABLE_NAME = "favorite_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "AircraftName";
    public static final String COL_3 = "Origin";
    public static final String COL_4 = "Destination";
    public static final String COL_5 = "FromTime";
    public static final String COL_6 = "ToTime";
    public static final String COL_7 = "Price";
    public static final String COL_8 = "FromPrice";
    public static final String COL_9 = "ToPrice";

    public SearchHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "( " +
                COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_2 + " VARCHAR(50), " +
                COL_3 + " VARCHAR(50), " +
                COL_4 + " VARCHAR(50), " +
                COL_5 + " VARCHAR(50), " +
                COL_6 + " VARCHAR(50), " +
                COL_7 + " VARCHAR(50), " +
                COL_8 + " VARCHAR(50), " +
                COL_9 + " VARCHAR(50)  " +");";
        sqLiteDatabase.execSQL(SQL);
    }

    public boolean insertData(String AircraftName, String Origin, String Destination, String FromTime, String ToTime,
                              String price, String rangeFromPrice, String rangeToPrice) {  //加入資料
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, AircraftName);
        contentValues.put(COL_3, Origin);
        contentValues.put(COL_4, Destination);
        contentValues.put(COL_5, FromTime);
        contentValues.put(COL_6, ToTime);
        contentValues.put(COL_7, price);
        contentValues.put(COL_8, rangeFromPrice);
        contentValues.put(COL_9, rangeToPrice);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean isDataExist(String AircraftName, String Origin, String Destination, String FromTime, String ToTime,
                               String FromPrice, String ToPrice) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean flag;
        Cursor res = db.rawQuery("select * from " + TABLE_NAME + " where " +
                COL_2 + " = '" + AircraftName + "' and " +
                COL_3 + " = '" + Origin + "' and " +
                COL_4 + " = '" + Destination + "' and " +
                COL_5 + " = '" + FromTime + "' and " +
                COL_6 + " = '" + ToTime + "' and " +
                COL_7 + " = '" + FromPrice + "' and " +
                COL_8 + " = '" + ToPrice + "'", null);//如果找不到就回傳null
        flag = res.getCount() == 0 ? true : false;
        res.close();

        return flag;
    }

    public Cursor getAllData(String AircraftName, String Origin, String Destination, String FromTime, String ToTime) {//取得所有資料，可利用他修改或刪除資料
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME+ " where " +
                COL_2 + " = '" + AircraftName + "' and " +
                COL_3 + " = '" + Origin + "' and " +
                COL_4 + " = '" + Destination + "' and " +
                COL_5 + " = '" + FromTime + "' and " +
                COL_6 + " = '" + ToTime + "'", null);//如果找不到就回傳null

        return res;
    }

    public Cursor getAllData1(String AircraftName, String Origin, String Destination, String FromTime, String ToTime,
                               String FromPrice, String ToPrice) {//取得所有資料，可利用他修改或刪除資料
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME + " where " +
                COL_2 + " = '" + AircraftName + "' and " +
                COL_3 + " = '" + Origin + "' and " +
                COL_4 + " = '" + Destination + "' and " +
                COL_5 + " = '" + FromTime + "' and " +
                COL_6 + " = '" + ToTime + "' and " +
                COL_7 + " = '" + FromPrice + "' and " +
                COL_8 + " = '" + ToPrice + "'", null);//如果找不到就回傳null

        return res;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
