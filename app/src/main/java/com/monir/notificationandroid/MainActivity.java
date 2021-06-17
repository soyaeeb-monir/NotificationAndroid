package com.monir.notificationandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.media.session.MediaSessionCompat;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import static androidx.core.app.NotificationCompat.*;
import static com.monir.notificationandroid.App.CHANNEL_1_ID;
import static com.monir.notificationandroid.App.CHANNEL_2_ID;

public class MainActivity extends AppCompatActivity {
    private NotificationManagerCompat notificationManager;
    private EditText editTextTitle;
    private EditText editTextMessage;
    private MediaSessionCompat mediaSession;
    static List<Message> MESSAGES = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notificationManager = NotificationManagerCompat.from(this);
//        editTextTitle = findViewById(R.id.edit_text_title);
//        editTextMessage = findViewById(R.id.edit_text_message);
        mediaSession = new MediaSessionCompat(this, "tag");

        MESSAGES.add(new Message("Hello","Jim"));
        MESSAGES.add(new Message("Hello",null));
        MESSAGES.add(new Message("Hi","Paulo"));
    }

    public void sendOnChannel1(View view) {
//        String title = editTextTitle.getText().toString();
//        String message = editTextMessage.getText().toString();

        // set large icon and big picture
        // Bitmap picture = BitmapFactory.decodeResource(getResources(), R.drawable.tom);

        // onClick behavior on notification
        Intent activityIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this,
                0, activityIntent, 0);

//        // set a button on notification
//        Intent broadCastIntent = new Intent(this, NotificationReceiver.class);
//        broadCastIntent.putExtra("toastMessage", message);
//        PendingIntent actionIntent = PendingIntent.getBroadcast(this,
//                0, broadCastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Creating Messing Style...
        RemoteInput remoteInput = new RemoteInput.Builder("key_text_reply")
                .setLabel("Your answer...")
                .build();

        Intent replyIntent = new Intent(this, DirectReplyReceiver.class);

        PendingIntent replyPendingIntent = PendingIntent.getBroadcast(this,
                0,replyIntent,0);

        Notification.Action replyAction = new Notification.Action.Builder(
                R.drawable.ic_reply,
                "Reply",
                replyPendingIntent
        ).addRemoteInput(remoteInput).build();

//        NotificationCompat.Action action = new NotificationCompat.Action.Builder(
//                R.drawable.ic_reply,
//                "Reply",
//                replyPendingIntent).addRemoteInput(remoteInput).build();

        NotificationCompat.MessagingStyle messagingStyle = new
                NotificationCompat.MessagingStyle("Me");
        messagingStyle.setConversationTitle("Group Chat");

        for(Message chatMessage : MESSAGES){
            NotificationCompat.MessagingStyle.Message notificationMessage =
                    new NotificationCompat.MessagingStyle.Message(
                            chatMessage.getText(),
                            chatMessage.getTimeStamp(),
                            chatMessage.getSender()
                    );
            messagingStyle.addMessage(notificationMessage);
        }

        // Creating actual notification
        Notification notification1 = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_one)
//                .setContentTitle(title)
//                .setContentText(message)
//                //.setLargeIcon(picture)
//                .setStyle(new NotificationCompat.BigTextStyle()
//                        .bigText(getString(R.string.text))
//                        .setBigContentTitle("Big text Title")
//                        .setSummaryText("This is summary"))
//                .setStyle(new NotificationCompat.BigPictureStyle()
//                        .bigPicture(picture)
//                        .bigLargeIcon(null))
                .setStyle(messagingStyle)
                //.addAction(replyAction)
                .setColor(Color.BLUE)
                .setPriority(PRIORITY_HIGH)
                .setCategory(CATEGORY_MESSAGE)
                .setContentIntent(contentIntent)
                //.addAction(R.mipmap.ic_launcher, "toast", actionIntent)
                //.setColor(Color.BLUE)
                .setOnlyAlertOnce(true)
                .setAutoCancel(true)
                .build();
        notificationManager.notify(1, notification1);
    }

    public void sendOnChannel2(View view) {
//        String title = editTextTitle.getText().toString();
//        String message = editTextMessage.getText().toString();
//
//        Bitmap artwork = BitmapFactory.decodeResource(getResources(), R.drawable.tom);

        Notification notification2 = new NotificationCompat.Builder(this, CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_two)
//                .setContentTitle(title)
//                .setContentText(message)
//                .setLargeIcon(artwork)
                .addAction(R.drawable.ic_previous, "previous", null)
                .addAction(R.drawable.ic_play, "play", null)
                .addAction(R.drawable.ic_next, "next", null)
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(0, 1, 2)
                        .setMediaSession(mediaSession.getSessionToken()))
                .setSubText("sub text")
//                .setStyle(new NotificationCompat.InboxStyle()
//                        .addLine("This is Line 1")
//                        .addLine("This is Line 1")
//                        .addLine("This is Line 1")
//                        .addLine("This is Line 1")
//                        .addLine("This is Line 1")
//                        .addLine("This is Line 1")
//                        .addLine("This is Line 1")
//                        .setBigContentTitle("Big Content title")
//                        .setSummaryText("Content Summary"))
                .setPriority(PRIORITY_LOW)
                .build();
        notificationManager.notify(2, notification2);
    }
}