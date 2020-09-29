package com.example.pest_application.UI.About;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.pest_application.R;

public class AboutAboutFragmant extends Fragment {

    private WebView about_aboutWebview;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.about_about, container, false);
        about_aboutWebview = view.findViewById(R.id.about_aboutWebview);
        about_aboutWebview.getSettings().setJavaScriptEnabled(true);
        about_aboutWebview.setWebViewClient(new WebViewClient());
        about_aboutWebview.loadUrl("file:///android_asset/"+"about.html");

        return view;
    }
}
