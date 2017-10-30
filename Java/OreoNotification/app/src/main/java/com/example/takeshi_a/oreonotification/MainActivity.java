package com.example.takeshi_a.oreonotification;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        final Uri uri1 = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.popinfo_alarm);
        final Uri uri2 = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.test_se);

        // Android 8.0 以上の場合は channel を作成
        final NotificationChannel channel =
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ? settingChannel(uri2) : null;

        // 通知作成
        final Notification notification = settingNotitication(mContext, "channel1", uri1);

        // 送信ボタン
        Button button = (Button) findViewById(R.id.send);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 通知送信
                sendNotification(channel, notification);
            }
        });
    }

    // 通知送信
    private void sendNotification(NotificationChannel channel, Notification notification) {
        NotificationManager manager = (NotificationManager) getSystemService(
                Context.NOTIFICATION_SERVICE);

        // Android 8.0 以上の場合は channel を設定
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(channel);
        }
        manager.notify(mNotificationId++, notification);
    }

    // 通知作成
    private Notification settingNotitication(Context context, String channelId, Uri uri) {

        return new NotificationCompat.Builder(this, channelId)
                .setContentTitle("タイトル( 8.0 ~ )")
                .setContentText("テキスト")
                .setSmallIcon(R.drawable.small_notification_icon)
                .setSound(uri, AudioManager.STREAM_NOTIFICATION)
                .setVibrate(new long[]{0, 1000, 500, 1000, 500})
                .setChannelId(channelId)
                .build();
    }

    // チャンネル作成
    @TargetApi(Build.VERSION_CODES.O)
    private NotificationChannel settingChannel(Uri uri) {
        // オーディオ属性の設定
        AudioAttributes audioAttributes = new AudioAttributes.Builder().setUsage(
                AudioAttributes.USAGE_NOTIFICATION).setContentType(
                AudioAttributes.CONTENT_TYPE_MUSIC).build();

        // チャンネル作成
        final NotificationChannel channel = new NotificationChannel(
                "channel1",
                "チャンネル１",
                NotificationManager.IMPORTANCE_DEFAULT
        );

        channel.setVibrationPattern(new long[]{0, 500, 100});
        channel.setSound(uri, audioAttributes);
        channel.setShowBadge(true);
        channel.setLightColor(Color.GREEN);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        return channel;
    }
}