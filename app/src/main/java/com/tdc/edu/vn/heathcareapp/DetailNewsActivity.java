package com.tdc.edu.vn.heathcareapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;

import javax.security.auth.callback.Callback;

public class DetailNewsActivity extends AppCompatActivity {
    ImageButton imageButtonBackSpace;
    WebView webViewNews;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_news);

        setControl();
        setEvent();



    }

    private void setEvent() {
        imageButtonBackSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        WebSettings webSettings = webViewNews.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webViewNews.setWebViewClient(new Callback());
        webViewNews.loadUrl("https://hellobacsi.com/song-khoe/dinh-duong/rau-cu-qua-giau-dinh-duong/#gref");
    }

    private void setControl() {
        webViewNews = findViewById(R.id.webView_detail);
        imageButtonBackSpace = findViewById(R.id.icon_backspace_news_detail);
    }

    private class  Callback extends WebViewClient{
        @Override
        public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
            return  false;
        }
    }
}