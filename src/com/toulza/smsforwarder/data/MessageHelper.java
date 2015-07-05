package com.toulza.smsforwarder.data;

import android.util.Log;
import android.util.Pair;

import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Pierre on 30/06/2015.
 */
public class MessageHelper {

    public String getContent() {
        return content;
    }
    public ArrayList<String> getDest() {return dest;}

    //destination phone number
    private ArrayList<String> dest = new ArrayList<String>();
    //content of message
    private String content ="";

    private final HashMap<String,String> forward = new HashMap<String,String>();

    private static String TAG = "MessageHelper";

    /**
     * construct a message Helper using received SMS body
     * @param messageBody
     * @return
     */
    public static MessageHelper getMessageInfos(String messageBody)
    {
        // MessageHelper instance
        MessageHelper tmp = new MessageHelper();

        // Initialize HashMap
        tmp.forward.put("FORWARD_CR","\r");
        tmp.forward.put("FORWARD_OTHER",":");

        // Verification of message type
        // - if starts with FORWARD_CR, then separator expected for data field is \r
        // - if starts with FORWARD_OTHER, then separator expected for data field is :
        String separator = null;
        String handler = null;
        for(Map.Entry<String,String> prefix: tmp.forward.entrySet()) {
            if(messageBody.startsWith(prefix.getKey())) {
                separator = prefix.getValue();
                handler = prefix.getKey();
            }

        }

        // If no prefix is found, the message has not to be processed.
        if(separator==null) {
            Log.e(TAG,"Message has not to be processed");
            return null;
        }

        // Let's split the message.
        String[] fields = messageBody.split(separator);

        // Message can't have less than 3 fields. Minimum required fields are :
        // - Destination
        // - Handler
        // - Body message
        if(fields.length < 3) {
            Log.e(TAG,"Message has less than 3 parameters");
            return null;
        }
        else{
            for(String field:fields)
            {
                //fields are of type
                // "D=+33626262626"
                // "B=Hello World"
                String[] fieldelements = field.split("=");
                if(fieldelements.length !=2 && !fieldelements[0].trim().equals(handler)) {
                    //Message can't be processed because corrupted
                    Log.e(TAG,"Unable to read field : " + field);
                    return null;
                }
                else
                {
                    //We trim field element
                    String key = fieldelements[0].trim();
                    String value = "";
                    if(fieldelements.length>1)
                    value = fieldelements[1].trim();

                    //Field type is destination
                    if(key.equals("D")){
                        //Control of SMS destinator
                        if(!value.startsWith("336") &&
                                !value.startsWith("337"))
                        {
                            //Message body has already been initialized
                            Log.e(TAG,"Message won't be processed because of ugly number : " + tmp.content);
                            return null;
                        }
                        //Add destination in dest list
                        if(!tmp.dest.contains(value))
                        tmp.dest.add(value);
                    }
                    //Field typeis destination
                    else if(key.equals("B")){
                        //initialize body
                        if(!tmp.content.equals("")) {
                            //Message body has already been initialized
                            Log.e(TAG,"Message body has already been initialized : " + tmp.content);
                            return null;
                        }
                        else if(tmp.content.startsWith("FORWARD")) {
                            //Message body wants to send something by someone else
                            Log.e(TAG,"Message body wants to redirect one more !! : " + tmp.content);
                            return null;
                        }
                        else {
                            tmp.content = value;
                        }
                    }
                    else
                    {
                        Log.i(TAG,"Field type not implemented : " + key);
                    }
                }
            }
        }
        if(tmp.dest.size() == 0 || tmp.content.equals(""))
        {
            Log.e(TAG,"Message cannot be used");
            return null;
        }

        return tmp;
    }
}
