package com.toulza.smsforwarder.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.toulza.smsforwarder.R;
import com.toulza.smsforwarder.adapter.SmsListItemAdapter;
import com.toulza.smsforwarder.data.Sms;
import com.toulza.smsforwarder.database.DatabaseHelper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pierre on 26/06/2015.
 */
public class Smslist extends Activity {

    DatabaseHelper db = new DatabaseHelper(this);
    List<Sms> list;
    SmsListItemAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.forward_list_main);
        // get all books
        try {
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
        }
    }
}
