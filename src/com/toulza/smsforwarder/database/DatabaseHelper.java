package com.toulza.smsforwarder.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.toulza.smsforwarder.data.Sms;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Pierre on 29/06/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // database version
    private static final int database_VERSION = 1;
    // database name
    private static final String database_NAME = "SmsForwarderDB";
    private static final String table_Forward_list = "ForwardList";
    private static final String sms_id = "Id";
    private static final String sms_received_time = "ReceivedTime";
    private static final String sms_dest = "Destination";
    private static final String sms_source = "Source";
    private static final String sms_body = "Body";

    private static final String[] COLUMNS = {sms_id, sms_received_time, sms_dest, sms_source, sms_body };

    public DatabaseHelper(Context context) {
        super(context, database_NAME, null, database_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SMS_TABLE = "CREATE TABLE "+ table_Forward_list +" ( "
                + sms_id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + sms_received_time + " DATETIME, "
                + sms_dest + " TEXT, "
                + sms_source + " TEXT, "
                + sms_body + " TEXT )";
        db.execSQL(CREATE_SMS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + table_Forward_list );
        this.onCreate(db);
    }

    public void createSmsForward(Sms sms) {
        // get reference of the SmsDB database
        SQLiteDatabase db = this.getWritableDatabase();
        // make values to be inserted
        ContentValues values = new ContentValues();
        //values.put(sms_id, sms.getId());
        values.put(sms_dest, sms.getDest());
        values.put(sms_source, sms.getSource());
        values.put(sms_body, sms.getBody());
        Date date = new Date();
        values.put(sms_received_time, sms.getReceivedTime().toString());

        // insert sms
        db.insert(table_Forward_list, null, values);
        // close database transaction
        db.close();
    }

    public Sms readSms(int id) throws ParseException {
        // get reference of the SmsDB database
        SQLiteDatabase db = this.getReadableDatabase();
        // get sms query
        Cursor cursor = db.query(table_Forward_list,
        COLUMNS, " id = ?", new String[] { String.valueOf(id) }, null, null, null, null);
        // if results !=null, parse the first one
        if (cursor != null)
        cursor.moveToFirst();
        Sms sms = new Sms();
        sms.setId(cursor.getInt(0));
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");
        Date dt = sdf.parse(cursor.getString(1));
        sms.setReceivedTime(dt);
        sms.setDest(cursor.getString(2));
        sms.setSource(cursor.getString(3));
        sms.setBody(cursor.getString(4));
        return sms;
    }

    public List<Sms> getAllSms() throws ParseException {

        List forwards = new LinkedList();
        // select Sms query
        String query = "SELECT  * FROM " + table_Forward_list;

        // get reference of the SmsDB database
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        // parse all results
        Sms sms = null;
        if (cursor.moveToFirst()) {
            do {

                sms = new Sms();
                sms.setId(Integer.parseInt(cursor.getString(0)));
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date dt = sdf.parse(cursor.getString(1));
                sms.setReceivedTime(dt);
                sms.setDest(cursor.getString(2));
                sms.setSource(cursor.getString(3));
                sms.setBody(cursor.getString(4));
                // Add book to books
                forwards.add(sms);
                } while (cursor.moveToNext());
        }

        return forwards;
    }



}