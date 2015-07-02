package com.toulza.smsforwarder.data;

import java.util.Date;

/**
 * Created by Pierre on 01/07/2015.
 */
public class Sms {

    public Date getReceivedTime() {
        return receivedTime;
    }

    public void setReceivedTime(Date receivedTime) {
        this.receivedTime = receivedTime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;
    private Date receivedTime;
    private String source;
    private String dest;
    private String body;

    public Sms(int id,Date receivedTime, String source, String dest, String body)
    {
        super();
        this.id = id;
        this.receivedTime = receivedTime;
        this.source = source;
        this.dest = dest;
        this.body = body;
    }

    public Sms()
    {
    }

    @Override
    public String toString() {
        return "Sms [date=" + receivedTime.toString()
                + ", id=" + id
                + ", source=" + source
                + ", dest=" + dest
                + ", body=" + body
                + "]";
    }
}

