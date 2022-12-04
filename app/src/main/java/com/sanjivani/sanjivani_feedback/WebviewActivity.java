package com.sanjivani.sanjivani_feedback;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class WebviewActivity extends AppCompatActivity {

    WebView webView;
    TextView tv_ToolBarTitle;
    public static String feedbackFormUrl, toolbarTitle;
    CardView cvProgressBar;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        tv_ToolBarTitle = findViewById(R.id.tv_ToolBarTitle);
        cvProgressBar = findViewById(R.id.cvProgressBar);
        webView = findViewById(R.id.webView);

        tv_ToolBarTitle.setText(toolbarTitle);

        cvProgressBar.setVisibility(View.VISIBLE);

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                cvProgressBar.setVisibility(View.GONE);
            }
        };
        handler.postDelayed(runnable, 1000);

        callWebView();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (webView.canGoBack()) {
                        webView.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    private void callWebView() {
        webView.loadUrl(feedbackFormUrl);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.canGoBack();
        webView.canGoForward();
    }
}