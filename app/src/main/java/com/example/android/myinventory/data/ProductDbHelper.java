package com.example.android.myinventory.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.myinventory.data.ProductContract.ProductEntry;

/**
 * Created by JukUm on 6/11/2017.
 */


/**
 * Database helper for the Inventory app. Manages database creation and version management.
 */
public class ProductDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;               //Database version. Must be incremented on schema change.
    public static final String DATABASE_NAME = "stock.db";      //Name of the database file.


    /**
     * Constructs a new instance of {@link ProductDbHelper}.
     *
     * @param context is the context of the app.
     */
    public ProductDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    /**
     * Called when the database is created for the first time.
     * Creates a String that contains the SQL statement for table creation.
     * Executes the SQL statement.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_PRODUCTS_TABLE = "CREATE TABLE IF NOT EXISTS "
                + ProductEntry.TABLE_NAME + "("
                + ProductEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ProductEntry.COLUMN_PRODUCT_NAME + " TEXT NOT NULL,"
                + ProductEntry.COLUMN_PRODUCT_PRICE + " INTEGER NOT NULL,"
                + ProductEntry.COLUMN_PRODUCT_QUANTITY + " INTEGER NOT NULL,"
                + ProductEntry.COLUMN_PRODUCT_IMAGE + " TEXT NOT NULL);";

        db.execSQL(SQL_CREATE_PRODUCTS_TABLE);
    }

    /**
     * Called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String SQL_DELETE_ENTRIES = "DELETE TABLE IF EXISTS " + ProductContract.ProductEntry.TABLE_NAME;
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}