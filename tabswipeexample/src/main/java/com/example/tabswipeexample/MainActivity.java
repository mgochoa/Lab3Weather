package com.example.tabswipeexample;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {


    Toolbar tb;
    TabLayout tl;
    ViewPager vp;
    FragmentPagerAdapter fpa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tb=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        tl=(TabLayout) findViewById(R.id.tabLayout);
        vp=(ViewPager)findViewById(R.id.ViewPager);
        FragmentPagerAdapter vpa=new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return null;
            }

            @Override
            public int getCount() {
                return 0;
            }
        };
        vp.setAdapter(vpa);

    }
}
