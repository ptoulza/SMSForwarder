package com.toulza.smsforwarder.activity;

import android.app.Activity;
import android.app.ListActivity;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.toulza.smsforwarder.R;
import com.toulza.smsforwarder.data.Sms;
import com.toulza.smsforwarder.database.DatabaseHelper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pierre on 26/06/2015.
 */
public class Smslist extends ListActivity {

    DatabaseHelper db = new DatabaseHelper(this);
    List<Sms> list;
    SimpleCursorAdapter adapterc;
    DatabaseHelper database;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // get all books
        /*try {
            list = db.getAllSms();

            ArrayList listSms = new ArrayList();
            for (int i = 0; i < list.size(); i++) {
                listSms.add(i, list.get(i));
            }
            adapter = new SmsListItemAdapter(this.getApplicationContext(),listSms);

            ListView listView = (ListView) findViewById(R.id.list);
            listView.setAdapter(adapter);
        } catch (ParseException e) {
            e.printStackTrace();
        }*/

        setContentView(android.R.layout.list_content);
    }

    @Override
    public void onResume() {

        super.onResume();
        database = new DatabaseHelper(this);
        Cursor smslist = database.getAllSmsCursor();
        smslist.registerContentObserver(new Observer(new Handler()));
        String[] fromColumns = {DatabaseHelper.sms_dest,DatabaseHelper.sms_received_time,DatabaseHelper.sms_body};
        int[] toViews = {R.id.dest,R.id.received,R.id.body};
        adapterc = new SimpleCursorAdapter(this,
                R.layout.forward_list_main_item, smslist, fromColumns, toViews, 0);

        setListAdapter(adapterc);
        adapterc.notifyDataSetChanged();
    }

    class Observer extends ContentObserver {
        // left blank below constructor for this Contact observer example to work
        // or if you want to make this work using Handler then change below registering  //line
        public Observer(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            this.onChange(selfChange, null);
            Smslist.this.adapterc.notifyDataSetChanged();
            // Override this method to listen to any changes
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
                    }
    }
}
