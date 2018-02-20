package com.bit.apps.kanadra.View.Activity;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bit.apps.kanadra.R;
import android.widget.RelativeLayout.LayoutParams;
import com.bit.apps.kanadra.View.Fragment.AboutUs;
import com.bit.apps.kanadra.View.Fragment.Home;
import com.bit.apps.kanadra.View.Fragment.KanadraDiwan;
import com.bit.apps.kanadra.View.Fragment.KanadraGallery;
import com.bit.apps.kanadra.View.Fragment.Mbra;
import com.bit.apps.kanadra.View.Fragment.NewsArticles;
import com.bit.apps.kanadra.View.Fragment.VideoGallery;

import android.app.ActionBar;
//import android.widget.RelativeLayout.LayoutParams;


public class MainActivity extends AppCompatActivity {

    private ImageView menu, search;
//    private FragmentTransaction transaction;
//    Fragment selectedFragment = null;
    private TextView header;
//    private String header_title[] = {"الرئيسية", "العائلة", "من نحن","اضف"};
//    private LinearLayout search_layout;
    private FragmentManager fragmentManager;
    private Fragment fragment = null;
    private Class fragmentClass = null;
    private Bundle bundle;



    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    ActionBar actionbar;
    TextView textview;
    DrawerLayout.LayoutParams layoutparams;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initialization();

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);

        fragmentClass = Home.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        fragmentManager.beginTransaction().replace(R.id.content_fragment, fragment).commit();


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectedItemDrawer(item);
                return true;
            }
        });
        header = (TextView) findViewById(R.id.header_text);
        menu = (ImageView) findViewById(R.id.menu);
        search = (ImageView) findViewById(R.id.search_bar);
//        search_layout = (LinearLayout) findViewById(R.id.search_layout);






        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(drawerLayout.isDrawerOpen(Gravity.LEFT))
                    drawerLayout.closeDrawers();
                else
                    drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
//
//        search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if ( search_layout.getVisibility() == View.GONE) {
//                    search_layout.setVisibility(View.VISIBLE);
//                }
//
//                else if (search_layout.getVisibility() == View.VISIBLE){
//
//                    search_layout.setVisibility(View.GONE);
//                }
//            }
//        });



    }

    private void initialization() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        fragmentManager = getSupportFragmentManager();
        bundle = new Bundle();



    }


    public void selectedItemDrawer(MenuItem item){

        switch (item.getItemId()){

            case R.id.home_menu:
                fragmentClass = Home.class;
                break;
            case R.id.generalnews_menu:
                fragmentClass = NewsArticles.class;
                bundle.putString("code", "1");
                break;
            case R.id.familyNews_menu:
                fragmentClass = NewsArticles.class;
                bundle.putString("code", "2");
                break;
            case R.id.articles_menu:
                fragmentClass = NewsArticles.class;
                bundle.putString("code", "3");
                break;
            case R.id.shoot_menu:
                fragmentClass = NewsArticles.class;
                bundle.putString("code", "4");
                break;
            case R.id.familyDiwan_menu:
                fragmentClass = NewsArticles.class;
                bundle.putString("code", "5");
                break;
            case R.id.kanadraDiwan_menu:
                fragmentClass = KanadraDiwan.class;
                break;
            case R.id.gallary_menu:
                fragmentClass = KanadraGallery.class;
                break;
            case R.id.aboutus_menu:
                fragmentClass = AboutUs.class;
                break;
            case R.id.mbra_menu:
                fragmentClass = Mbra.class;
                break;
            case R.id.events_menu:
                fragmentClass = NewsArticles.class;
                bundle.putString("code", "6");
                break;
            case R.id.projects_menu:
                fragmentClass = NewsArticles.class;
                bundle.putString("code", "7");
                break;
            default:
                return;
        }


        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        fragment.setArguments(bundle);

        fragmentManager.beginTransaction().replace(R.id.content_fragment, fragment).commit();
        item.setChecked(true);
        header.setText(item.getTitle());
        drawerLayout.closeDrawers();

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && fragmentClass != Home.class) {
            fragmentClass = Home.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            fragmentManager.beginTransaction().replace(R.id.content_fragment, fragment).commit();

            header.setText("الرئيسية");

            return true;
        }


        return super.onKeyDown(keyCode, event);
    }
}
