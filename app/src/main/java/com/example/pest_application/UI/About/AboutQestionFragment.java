package com.example.pest_application.UI.About;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.fragment.app.Fragment;

import com.example.pest_application.R;

public class AboutQestionFragment extends Fragment {
    private WebView about_questionWebview;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.aboutquestion, container, false);
        about_questionWebview = (WebView)view.findViewById(R.id.about_questionWebview);
        about_questionWebview.getSettings().setJavaScriptEnabled(true);
        about_questionWebview.setWebViewClient(new WebViewClient());
        about_questionWebview.loadUrl("file:///android_asset/"+"question.html");
        return view;
    }
}
