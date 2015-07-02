package com.toulza.smsforwarder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.toulza.smsforwarder.R;
import com.toulza.smsforwarder.data.Sms;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Pierre on 02/07/2015.
 */
public class SmsListItemAdapter extends ArrayAdapter<Sms> {
    public SmsListItemAdapter(Context context, ArrayList<Sms> smslist) {
        super(context, 0, smslist);
    }

    private static final String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Sms sms = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.forward_list_main_item, parent, false);
        }
        // Lookup view for data population
        TextView id = (TextView) convertView.findViewById(R.id.id);
        TextView received = (TextView) convertView.findViewById(R.id.received);
        TextView dest = (TextView) convertView.findViewById(R.id.dest);
        TextView source = (TextView) convertView.findViewById(R.id.source);
        TextView body = (TextView) convertView.findViewById(R.id.body);

        id.setText(sms.getId()+"");
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        received.setText(sdf.format(sms.getReceivedTime()));
        dest.setText(sms.getDest());
        source.setText(sms.getSource());
        body.setText(sms.getBody());
        // Return the completed view to render on screen
        return convertView;
    }
}
