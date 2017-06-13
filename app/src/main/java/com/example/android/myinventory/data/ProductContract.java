package com.example.android.myinventory.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by JukUm on 6/11/2017.
 */

public final class ProductContract {

    private ProductContract() {}

    /**
     * CONTENT_AUTHORITY - The name for the entire content provider
     * BASE_CONTENT_URI  - Using the CONTENT_AUTHORITY, creates the base of all URI's used by other apps
     * PATH_PRODUCTS     - The possible path appended to BASE_CONTENT_URI
     **/
    public static final String CONTENT_AUTHORITY = "com.example.android.myinventory";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_PRODUCTS = "products";

    public static abstract class ProductEntry implements BaseColumns {

        /**
         * CONTENT_URI       - The content URI to access the product data in the provider
         * CONTENT_LIST_TYPE - The MIME type of the {@link #CONTENT_URI} for a list of products.
         * CONTENT_ITEM_TYPE - The MIME type of the {@link #CONTENT_URI} for a single product.
         */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PRODUCTS);
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCTS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCTS;


        //Name of the table
        public static final String TABLE_NAME = "products";

        //Name of the columns
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_PRODUCT_NAME = "name";
        public static final String COLUMN_PRODUCT_QUANTITY = "quantity";
        public static final String COLUMN_PRODUCT_PRICE = "price";
        public static final String COLUMN_PRODUCT_IMAGE = "picture";

    }
}