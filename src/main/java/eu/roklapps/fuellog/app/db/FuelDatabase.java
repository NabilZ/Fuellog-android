package eu.roklapps.fuellog.app.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class FuelDatabase extends SQLiteOpenHelper {
    public static final String FUEL_TABLE = "fuel";
    public static final String FUEL_PRICE_PER_UNIT = "pricePerUnit";
    public static final String FUEL_TOTAL_BOUGHT_FUEL = "totalBoughtFuel";
    public static final String FUEL_MILEAGE = "mileage";
    public static final String FUEL_USED_CAR = "usedCar";
    public static final String GAS_TYPE_TABLE = "gasType";
    public static final String NAME = "name";
    public static final String CARS_TABLE = "cars";
    public static final String FUEL_EVENT_DATE = "eventDate";
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "fuellog.db";
    public static final String CREATE_DATE = "createDate";
    public static final String MODIFY_DATE = "modifyDate";
    public static final String CARS_VENDOR = "vendor";
    private Context mContext;

    public FuelDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + FUEL_TABLE + "(" +
                        "_id integer primary key autoincrement," +
                        FUEL_PRICE_PER_UNIT + " real not null, " +
                        FUEL_TOTAL_BOUGHT_FUEL + " real not null, " +
                        FUEL_MILEAGE + " real not null, " +
                        FUEL_EVENT_DATE + " text, " +
                        CREATE_DATE + " text, " +
                        MODIFY_DATE + " text," +
                        GAS_TYPE_TABLE + " integer," +
                        FUEL_USED_CAR + " integer not null);"
        );

        db.execSQL("create table " + CARS_TABLE + "(" +
                "_id integer primary key autoincrement, " +
                NAME + " text, " +
                GAS_TYPE_TABLE + " integer," +
                CARS_VENDOR + " text);");

        db.execSQL("create table " + GAS_TYPE_TABLE + "(" +
                "_id integer primary key autoincrement," +
                NAME + " text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<String> getAllCars() {
        SQLiteDatabase database = this.getReadableDatabase();
        List<String> cars = new ArrayList<>();

        Cursor cursor = database.query(CARS_TABLE, new String[]{NAME}, null, null, NAME, null, null);
        if (cursor.moveToFirst()) {
            do {
                cars.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        database.close();
        cursor.close();
        return cars;
    }

    public void saveNewCar(ContentValues contentValues) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        sqLiteDatabase.insert(CARS_TABLE, null, contentValues);

        sqLiteDatabase.close();
    }
}
