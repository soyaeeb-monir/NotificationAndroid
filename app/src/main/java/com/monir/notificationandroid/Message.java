package com.monir.notificationandroid;

public class Message {
    private CharSequence text;
    private long timeStamp;
    private CharSequence sender;

    public Message(CharSequence text, CharSequence sender) {
        this.text = text;
        this.sender = sender;
        timeStamp = System.currentTimeMillis();
    }

    public CharSequence getText() {
        return text;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public CharSequence getSender() {
        return sender;
    }
}
