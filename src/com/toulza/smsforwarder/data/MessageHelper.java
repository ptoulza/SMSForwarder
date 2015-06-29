package com.toulza.smsforwarder.data;

/**
 * Created by Pierre on 30/06/2015.
 */
public class MessageHelper {

    public String getDest() {
        return dest;
    }

    public String getContent() {
        return content;
    }

    public String getHandler() {
        return handler;
    }

    //destination phone number
    private String dest;
    //content of message
    private String content;
    //Handler string of message
    private String handler;

    /**
     * construct a message Helper using received SMS body
     * @param messageBody
     * @return
     */
    public static MessageHelper getMessageInfos(String messageBody)
    {
        String[] bodies = messageBody.split("\r");
        //Todo : find a way to parameter the message structure.

        //3 lines. What is expected !! Great
        if(bodies.length == 3)
        {
            MessageHelper tmp = new MessageHelper();
            tmp.handler = bodies[0];
            tmp.dest = bodies[1];
            tmp.content = bodies[2];
            return tmp;
        }
        return null;
    }
}
