package com.toulza.smsforwarder.receiver;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.toulza.smsforwarder.activity.Smslist;
import com.toulza.smsforwarder.data.MessageHelper;
import com.toulza.smsforwarder.data.Sms;
import com.toulza.smsforwarder.database.DatabaseHelper;

import java.util.Date;

/**
 * Created by Pierre on 26/06/2015.
 */
public class SmsReceiver extends BroadcastReceiver {

    public static final String SMS_EXTRA_NAME ="pdus";

    public static final String SMS_URI = "content://sms";

    public static final String ADDRESS = "address";
    public static final String PERSON = "person";
    public static final String DATE = "date";
    public static final String READ = "read";
    public static final String STATUS = "status";
    public static final String TYPE = "type";
    public static final String BODY = "body";
    public static final String SEEN = "seen";

    public static final int MESSAGE_TYPE_INBOX = 1;
    public static final int MESSAGE_TYPE_SENT = 2;

    public static final int MESSAGE_IS_NOT_READ = 0;
    public static final int MESSAGE_IS_READ = 1;

    public static final int MESSAGE_IS_NOT_SEEN = 0;
    public static final int MESSAGE_IS_SEEN = 1;




    @Override
    public void onReceive(Context context, Intent intent) {
        DatabaseHelper db = new DatabaseHelper(context);

        // Get the SMS map from Intent
        Bundle extras = intent.getExtras();

        String messages = "";
        String address = "";
        if ( extras != null )
        {
            // Get received SMS array
            Object[] smsExtra = (Object[]) extras.get(SMS_EXTRA_NAME);

            // Get ContentResolver object for pushing encrypted SMS to the incoming folder
            ContentResolver contentResolver = context.getContentResolver();
            String body = "";
            for ( int i = 0; i < smsExtra.length; ++i )
            {
                SmsMessage sms = SmsMessage.createFromPdu((byte[])smsExtra[i]);
                body = sms.getMessageBody().toString();

                address = sms.getOriginatingAddress();
                messages += "SMS from " + address + " :\n";
                messages += body + "\n";
                //Unusable if not default SMS app
                //putSmsToDatabase( contentResolver, sms );

            }
            if(body!=null) {
                MessageHelper sms = MessageHelper.getMessageInfos(body);
                if (sms != null) {
                    SmsManager smsManager = SmsManager.getDefault();
                    for(String dest : sms.getDest()) {
                        Date t = new Date();
                        db.createSmsForward(new Sms(0,t,address,dest,sms.getContent()));
                        smsManager.sendTextMessage(dest, null, sms.getContent(), null, null);
                        Toast.makeText(context, "Forwarded to : "  + sms.getDest(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        }

        // WARNING!!!
        // If you uncomment the next line then received SMS will not be put to incoming.
        // Be careful!
        // this.abortBroadcast();
    }

    private void putSmsToDatabase( ContentResolver contentResolver, SmsMessage sms )
    {
        // Create SMS row
        ContentValues values = new ContentValues();
        values.put( ADDRESS, sms.getOriginatingAddress() );
        values.put( DATE, sms.getTimestampMillis() );
        values.put( READ, MESSAGE_IS_NOT_READ );
        values.put( STATUS, sms.getStatus() );
        values.put( TYPE, MESSAGE_TYPE_INBOX );
        values.put( SEEN, MESSAGE_IS_NOT_SEEN );
        values.put( BODY, sms.getMessageBody().toString() );

        // Push row into the SMS table
        contentResolver.insert( Uri.parse(SMS_URI), values );
    }

}
