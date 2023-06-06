package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.example.Adapter.MainAdapter;
import com.example.fragment.PublishFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setItemIconTintList(null);
        MainAdapter mainAdapter = new MainAdapter(getSupportFragmentManager());
        mViewPager = findViewById(R.id.viewpager);
        mViewPager.setAdapter(mainAdapter);
        setNavigation();

    }

    private void setNavigation() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                resetIcon();
                switchMenu(item);
                return true;
            }
        });

        // This method cannot implement the method of click the bottomButton once and the fragment change, it must click twice to achieve the jump method.
//        bottomNavigationView.setOnItemReselectedListener(new NavigationBarView.OnItemReselectedListener() {
//            @Override
//            public void onNavigationItemReselected(@NonNull MenuItem item) {
//                resetIcon();
//                switchMenu(item);
//            }
//        });

        // The slide of the fragment
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                switchMenu(bottomNavigationView.getMenu().getItem(position));

            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void resetIcon() {
        MenuItem home = bottomNavigationView.getMenu().findItem(R.id.MainFragment);
        home.setIcon(R.drawable.frame_3);
        home = bottomNavigationView.getMenu().findItem(R.id.publish);
        home.setIcon(R.drawable.publish);
        home = bottomNavigationView.getMenu().findItem(R.id.order);
        home.setIcon(R.drawable.order);
        home = bottomNavigationView.getMenu().findItem(R.id.yours);
        home.setIcon(R.drawable.my);

    }

    @SuppressLint("NonConstantResourceId")
    private void switchMenu(MenuItem item) {
        switch (item.getItemId()){
            case R.id.MainFragment:
                item.setIcon(R.drawable.frame_3);
                mViewPager.setCurrentItem(0);
                break;
            case R.id.publish:
                item.setIcon(R.drawable.publish);
                mViewPager.setCurrentItem(1);
                break;
            case R.id.order:
                item.setIcon(R.drawable.order);
                mViewPager.setCurrentItem(2);
                break;
            case R.id.yours:
                item.setIcon(R.drawable.my);
                mViewPager.setCurrentItem(3);
                break;
            default:
        }
    }
}