package com.example.pest_application.UI.About;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.pest_application.R;
import com.example.pest_application.UI.DetaiPestlInformation;
import com.example.pest_application.UI.Home.HomeFragment;
import com.example.pest_application.UI.Map.mapfragment;
import com.example.pest_application.UI.Report.ReportFragment;
import com.example.pest_application.UI.showAll.ShowAllPestsFragment;

public class AboutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity);
        Intent intent = getIntent();
        int num = intent.getIntExtra("num",0);
        switch (num){

            case 1:
                replaceHomeFragment(new AboutQestionFragment());
                break;
            case 2:
                replaceHomeFragment(new AboutInstructionFragment());
                break;
            case 3:
                replaceHomeFragment(new AboutAboutFragmant());
                break;

        }
    }
    private void replaceHomeFragment(Fragment nextFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, nextFragment);
        fragmentTransaction.commit();

    }
}
