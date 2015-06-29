package com.toulza.smsforwarder.database;

import android.provider.BaseColumns;

/**
 * Created by Pierre on 29/06/2015.
 */
public class DatabaseHelper {

    public final class SmsSmallbaseContract {
        // To prevent someone from accidentally instantiating the contract class,
        // give it an empty constructor.
        public SmsSmallbaseContract() {}

        /* Inner class that defines the table contents */
        public abstract class SmsSmallbase implements BaseColumns {
            public static final String TABLE_NAME = "smslist";
            public static final String COLUMN_NAME_FROM = "from";
            public static final String COLUMN_NAME_DATE = "date";
            public static final String COLUMN_NAME_CONTENT = "content";

        }
    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String DATE_TYPE = " DATE";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + SmsSmallbaseContract.SmsSmallbase.TABLE_NAME + " (" +
                    SmsSmallbaseContract.SmsSmallbase._ID + " INTEGER PRIMARY KEY," +
                    SmsSmallbaseContract.SmsSmallbase.COLUMN_NAME_FROM + TEXT_TYPE + COMMA_SEP +
                    SmsSmallbaseContract.SmsSmallbase.COLUMN_NAME_DATE + DATE_TYPE + COMMA_SEP +
                    SmsSmallbaseContract.SmsSmallbase.COLUMN_NAME_CONTENT + DATE_TYPE + COMMA_SEP +
            " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + SmsSmallbaseContract.SmsSmallbase.TABLE_NAME;
}
