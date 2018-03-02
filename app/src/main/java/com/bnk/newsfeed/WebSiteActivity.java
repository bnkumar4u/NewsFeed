package com.bnk.newsfeed;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

public class WebSiteActivity extends AppCompatActivity {

    ProgressBar pb;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_site);

        webView=(WebView)findViewById(R.id.web_view);
        String passedUrl=getIntent().getExtras().getString("url_to_load");
        webView.loadUrl(passedUrl);
        webView.getSettings().setJavaScriptEnabled(true);
        pb=(ProgressBar)findViewById(R.id.progree);
        int p=webView.getProgress();
        webView.setWebChromeClient(new WebChromeClient()
        {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {

                pb.setProgress(newProgress);
                if(newProgress==100)
                    pb.setVisibility(View.GONE);
                super.onProgressChanged(view, newProgress);
            }
        });
    }

}
