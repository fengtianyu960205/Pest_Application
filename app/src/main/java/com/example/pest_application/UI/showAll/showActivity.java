package com.example.pest_application.UI.showAll;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.pest_application.R;
import com.example.pest_application.UI.DetaiPestlInformation;

public class showActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showactivity);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("info");
        DetaiPestlInformation nextFrag= new DetaiPestlInformation();
        nextFrag.setArguments(bundle);
        replaceHomeFragment(nextFrag);
    }

    private void replaceHomeFragment(Fragment nextFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, nextFragment);
        fragmentTransaction.commit();

    }
}
