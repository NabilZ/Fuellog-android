package eu.roklapps.fuellog.app.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import eu.roklapps.fuellog.app.ui.card.CarCard;
import eu.roklapps.fuellog.app.ui.card.CardFuelListing;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;

public class FuelDatabase extends SQLiteOpenHelper {
    public static final String FUEL_PRICE_PER_UNIT = "pricePerUnit";
    public static final String FUEL_TOTAL_BOUGHT_FUEL = "totalBoughtFuel";
    public static final String FUEL_MILEAGE = "mileage";
    public static final String FUEL_USED_CAR = "usedCar";
    public static final String GAS_TYPE_TABLE = "gasType";
    public static final String NAME = "name";
    public static final String FUEL_EVENT_DATE = "eventDate";
    public static final String CARS_VENDOR = "vendor";
    private static final String FUEL_TABLE = "fuel";
    private static final String CARS_TABLE = "cars";
    private static final String CREATE_DATE = "createDate";
    private static final String MODIFY_DATE = "modifyDate";
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "fuellog.db";
    private final Context mContext;

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

        db.execSQL("create table " + GAS_TYPE_TABLE + "(" +
                "_id integer primary key autoincrement," +
                NAME + " text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public ArrayList<Card> getAllCars() {
        SQLiteDatabase database = this.getReadableDatabase();
        ArrayList<Card> cars = new ArrayList<>();

        CarCard card;

        Cursor cursor = database.query(CARS_TABLE, new String[]{NAME, CARS_VENDOR}, null, null, NAME, null, null);
        if (cursor.moveToFirst()) {
            do {
                card = new CarCard(mContext);
                card.setName(cursor.getString(0));
                card.setVendor(cursor.getString(1));
                cars.add(card);
            } while (cursor.moveToNext());
        }

        database.close();
        cursor.close();
        return cars;
    }

    public List<String> getAllCarsAsList() {
        List<String> mCars = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(CARS_TABLE, new String[]{NAME, CARS_VENDOR}, null, null, NAME, null, null);

        StringBuilder builder;

        if (cursor.moveToFirst()) {
            do {
                builder = new StringBuilder(cursor.getString(1)).append(" - ").append(cursor.getString(0));
                mCars.add(builder.toString());
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return mCars;
    }

    public void saveNewCar(ContentValues contentValues) {
        saveOperation(contentValues, CARS_TABLE);
    }

    public void saveNewFuelRecord(ContentValues contentValues) {
        saveOperation(contentValues, FUEL_TABLE);
    }

    private void saveOperation(ContentValues contentValues, String tableName) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        sqLiteDatabase.insert(tableName, null, contentValues);

        sqLiteDatabase.close();
    }

    public ArrayList<Card> getAllFuelEntries() {
        CardFuelListing card;
        CardHeader header;
        ArrayList<Card> cards = new ArrayList<>();

        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(FUEL_TABLE, new String[]{FUEL_EVENT_DATE, FUEL_TOTAL_BOUGHT_FUEL, FUEL_USED_CAR}, null, null, null, null, FUEL_EVENT_DATE + " DESC");

        if (cursor.moveToNext()) {
            do {
                card = new CardFuelListing(mContext);
                card.setDateText(cursor.getString(0));
                card.setCarText(String.valueOf(cursor.getString(2)));

                header = new CardHeader(mContext);
                header.setTitle(String.valueOf(cursor.getFloat(1)));
                card.addCardHeader(header);

                cards.add(card);
            } while (cursor.moveToNext());
        }
        database.close();
        cursor.close();

        return cards;
    }

    public Cursor getAllFuelEntriesAsCursor() {
        SQLiteDatabase database = this.getReadableDatabase();
        return database.query(FUEL_TABLE, new String[]{FUEL_EVENT_DATE, FUEL_TOTAL_BOUGHT_FUEL, FUEL_USED_CAR}, null, null, null, null, FUEL_EVENT_DATE + " DESC");
    }

    public Cursor getAllCarsAsCursor() {
        SQLiteDatabase database = this.getReadableDatabase();
        return database.query(CARS_TABLE, new String[]{NAME, CARS_VENDOR}, null, null, NAME, null, null);
    }
}
