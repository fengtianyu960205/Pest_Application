package com.example.pest_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.pest_application.UI.About.AboutFragment;
import com.example.pest_application.UI.Home.HomeFragment;
import com.example.pest_application.UI.Map.MapsActivity;
import com.example.pest_application.UI.Map.mapfragment;
import com.example.pest_application.UI.Report.ReportFragment;
import com.example.pest_application.UI.Search.Search_pestFragment;
import com.example.pest_application.UI.showAll.ShowAllPestsFragment;
import com.example.pest_application.UI.showAll.showActivity;
import com.google.android.material.navigation.NavigationView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements  BottomNavigationBar.OnTabSelectedListener {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private BottomNavigationBar bottomNavigationBar;
    int lastSelectedPosition = 0;
    private String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_home_black_24dp, "Search").setActiveColor(R.color.colorPrimaryDark))
                .addItem(new BottomNavigationItem(R.drawable.ic_photo_library_black_24dp, "Species").setActiveColor(R.color.colorPrimaryDark))
                .addItem(new BottomNavigationItem(R.drawable.ic_location_on_black_24dp, "Map").setActiveColor(R.color.colorPrimaryDark))
                .addItem(new BottomNavigationItem(R.drawable.ic_show_chart_black_24dp, "Report").setActiveColor(R.color.colorPrimaryDark))
                .addItem(new BottomNavigationItem(R.drawable.ic_help_black_24dp, "About").setActiveColor(R.color.colorPrimaryDark))
                .setFirstSelectedPosition(lastSelectedPosition )
                .initialise();
        bottomNavigationBar.setTabSelectedListener(this);


        // toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //drawerLayout = findViewById(R.id.mainact_layout);
        //navigationView = findViewById(R.id.nv);
        //toggle = new ActionBarDrawerToggle(this,
        //        drawerLayout,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        //drawerLayout.addDrawerListener(toggle);
       // toggle.syncState();
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // getSupportActionBar().setHomeButtonEnabled(true);
       // navigationView.setNavigationItemSelectedListener(this);
        replaceHomeFragment(new HomeFragment());
    }

    @Override
    public void onTabSelected(int position) {
        switch (position){
            case 0:
                replaceHomeFragment(new HomeFragment());
                break;
            case 1:
                replaceHomeFragment(new ShowAllPestsFragment());
                break;
            case 2:
                replaceHomeFragment(new mapfragment());
                break;
            case 3:
                replaceHomeFragment(new ReportFragment());
                break;
            case 4:
                replaceHomeFragment(new AboutFragment());
                break;
        }
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }
    /*@Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.Pest_Search:
                replaceFragment(new Search_pestFragment());
                break;
            case R.id.ShowAll_Pest:
                replaceFragment(new ShowAllPestsFragment());
                break;
            case R.id.Maps:
                startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                break;
            case R.id.Report:
                replaceFragment(new ReportFragment());
                break;
            case R.id.About:
                replaceFragment(new AboutFragment());
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }*/

    private void replaceFragment(Fragment nextFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, nextFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    private void replaceHomeFragment(Fragment nextFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, nextFragment);
        fragmentTransaction.commit();

    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    public void setToolBarTitle(String title){
        toolbar.setTitle(title);

    }*/


}
