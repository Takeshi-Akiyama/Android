package com.example.takeshi_a.oreonotification;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button mButtonSendNotificationA;
    private Button mButtonSendNotificationB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // widget設定
        setupWidget();

        // Listener設定
        setupLisetner();
    }

    /**
     * リスナーを設定します
     */
    private void setupLisetner() {
        mButtonSendNotificationA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mButtonSendNotificationB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    /**
     * ウィジェットを設定します
     */
    private void setupWidget() {
        mButtonSendNotificationA = (Button) findViewById(R.id.btn_send_notificationA);
        mButtonSendNotificationB = (Button) findViewById(R.id.btn_send_notificationB);
    }
}
