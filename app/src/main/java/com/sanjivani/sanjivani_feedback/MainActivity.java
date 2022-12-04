package com.sanjivani.sanjivani_feedback;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.sanjivani.sanjivani_feedback.helper.Utils;

public class MainActivity extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Utils.whiteIconStatusBar(this, R.color.white);

        context = MainActivity.this;

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(context, LoginActivity.class));
                finish();
            }
        };
        handler.postDelayed(runnable, 2000);

    }
}