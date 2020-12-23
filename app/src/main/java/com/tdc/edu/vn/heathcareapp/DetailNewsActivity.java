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
import android.widget.Toast;

import javax.security.auth.callback.Callback;

public class DetailNewsActivity extends AppCompatActivity {
    ImageButton imageButtonBackSpace;
    WebView webViewNews;
    String url_news = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_news);
        setControl();
        setEvent();
    }
    private String getDataByCode() {
        try {
            url_news = getIntent().getExtras().getString("url_news");
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
        }
        return url_news;
    }
    private void setEvent() {
        imageButtonBackSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        try {

            WebSettings webSettings = webViewNews.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webViewNews.setWebViewClient(new Callback());
            if (!getDataByCode().equals("")){
                webViewNews.loadUrl(getDataByCode());
            }else {
                webViewNews.loadUrl("http://no.com/");
            }

        }catch (Exception ex){
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
        }

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