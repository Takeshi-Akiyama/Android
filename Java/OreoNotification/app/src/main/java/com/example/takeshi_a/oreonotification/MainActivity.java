package com.example.takeshi_a.oreonotification;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private int mNotificationId = 0;
    private final static String CHANNEL_ID = "channel1";
    private final static String CHANNEL_NAME = "通知チャンネル１";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        // 送信ボタン
        Button button = (Button) findViewById(R.id.send);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 通知送信
                Intent intent = new Intent(mContext,NotificationService.class);
                intent.putExtra("NOTIFICATION_ID",mNotificationId++);
                intent.putExtra("CHANNEL_ID", CHANNEL_ID);
                intent.putExtra("CHANNEL_NAME", CHANNEL_NAME);

                startService(intent);
            }
        });
    }
}