package com.example.pest_application.UI.About;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.pest_application.R;
import com.example.pest_application.UI.showAll.showActivity;

public class AboutFragment extends Fragment {

    private Button QA_btn;
    private Button Instruction_btn;
    private Button About_btn;
    private Context context;
    private WebView about_aboutWebview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.aboutscreen, container, false);
        context = getActivity();
        QA_btn = view.findViewById(R.id.QA_btn);
        //Instruction_btn = view.findViewById(R.id.Instruction_btn);
        //About_btn = view.findViewById(R.id.About_btn);
        QA_btn.setText("Q & A");
        QA_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = 1;
                startActivity(new Intent(context, AboutActivity.class).putExtra("num",num));
            }
        });
        about_aboutWebview = (WebView)view.findViewById(R.id.about_aboutWebview);
        about_aboutWebview.getSettings().setJavaScriptEnabled(true);
        about_aboutWebview.setWebViewClient(new WebViewClient());
        about_aboutWebview.loadUrl("file:///android_asset/"+"about.html");
        /*Instruction_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = 2;
                startActivity(new Intent(context, AboutActivity.class).putExtra("num",num));
            }
        });*/
        /*
        About_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = 3;
                startActivity(new Intent(context, AboutActivity.class).putExtra("num",num));
            }
        });*/
        return view;
    }
}
