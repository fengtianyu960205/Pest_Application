package com.example.pest_application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.anychart.core.series.renderingsettings.Context;
import com.example.pest_application.UI.Map.MapsActivity;

public class privacyActivity extends AppCompatActivity {
    private WebView privacyPolicy;
    private Button disagree_btn;
    private Button  agree_btn;
    //private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.privacy);
        privacyPolicy = (WebView) findViewById(R.id.privacyPolicy);
        disagree_btn =  findViewById(R.id.disagree_btn);
        agree_btn =  findViewById(R.id.agree_btn);
        //toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //toolbar.setToolBarTitle("Privacy Policy");
        //setToolBarTitle("Privacy Policy");
        //setToolBarTitle("Privacy Policy");
        //toolbar.setTitleTextColor(getColor(R.color.colorWhite));
        //toolbar.setTitleText
        //privacyActivity.this.setTitle("Privacy Policy");
        privacyActivity.this.setTitle("");
        privacyPolicy.getSettings().setJavaScriptEnabled(true);
        privacyPolicy.setWebViewClient(new WebViewClient());
        privacyPolicy.loadUrl("file:///android_asset/"+"privacy.html");
        agree_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(privacyActivity.this, MainActivity.class));
            }
        });
        disagree_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
