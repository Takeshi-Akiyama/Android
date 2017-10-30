package com.example.takeshi_a.oreonotification;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

public class NotificationService extends IntentService {
    private Context mContext;

    public NotificationService() {
        super("hoge");
    }

    @Override
    public void onCreate() {
        System.out.println("NotificationService : onCreate");
        super.onCreate();
        mContext = this;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        System.out.println("NotificationService : onHandleIntent");

        int notificationId = intent.getIntExtra("NOTIFICATION_ID", 0);
        String channelId = intent.getStringExtra("CHANNEL_ID");
        String channelName = intent.getStringExtra("CHANNEL_NAME");

        // リソース設定
        final Uri uri1 = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.popinfo_alarm);
        final Uri uri2 = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.test_se);

        // Android 8.0 以上の場合は channel を作成
        final NotificationChannel channel = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ?
                settingChannel(channelId, channelName, uri2) : null;

        // 通知作成
        final Notification notification = settingNotitication(channelId, uri1);


        // 通知送信
        sendNotification(channel, notification, notificationId);
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        System.out.println("NotificationService : onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        System.out.println("NotificationService : onDestroy");
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        System.out.println("NotificationService : onUnbind");
        return super.onUnbind(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("NotificationService : onBind");
        return super.onBind(intent);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        System.out.println("NotificationService : onConfigurationChanged");
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        System.out.println("NotificationService : onLowMemory");
        super.onLowMemory();
    }

    @Override
    public void onRebind(Intent intent) {
        System.out.println("NotificationService : onRebind");
        super.onRebind(intent);
    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        System.out.println("NotificationService : onStart");
        super.onStart(intent, startId);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        System.out.println("NotificationService : onTaskRemoved");
        super.onTaskRemoved(rootIntent);
    }

    @Override
    public void onTrimMemory(int level) {
        System.out.println("NotificationService : onTrimMemory");
        super.onTrimMemory(level);
    }

    // 通知送信
    private void sendNotification(NotificationChannel channel, Notification notification,
            int notificationId) {
        System.out.println("NotificationService : sendNotification");

        NotificationManager manager = (NotificationManager) getSystemService(
                Context.NOTIFICATION_SERVICE);

        // Android 8.0 以上の場合は channel を設定
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(channel);
        }

        try {
            for (int i = 0; i < 3; i++) {
                manager.notify(notificationId + i, notification);
                Thread.sleep(60000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 通知作成
    private Notification settingNotitication(String channelId, Uri uri) {
        System.out.println("NotificationService : settingNotitication");

        return new NotificationCompat.Builder(mContext, channelId)
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
    private NotificationChannel settingChannel(String channelId, String channelName, Uri uri) {
        System.out.println("NotificationService : settingChannel");

        // オーディオ属性の設定
        AudioAttributes audioAttributes = new AudioAttributes.Builder().setUsage(
                AudioAttributes.USAGE_NOTIFICATION).setContentType(
                AudioAttributes.CONTENT_TYPE_MUSIC).build();

        // チャンネル作成
        final NotificationChannel channel = new NotificationChannel(
                channelId,
                channelName,
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
