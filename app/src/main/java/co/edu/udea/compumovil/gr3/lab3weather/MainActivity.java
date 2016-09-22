package co.edu.udea.compumovil.gr3.lab3weather;

import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import co.edu.udea.compumovil.gr3.lab3weather.Fragments.settings;
import co.edu.udea.compumovil.gr3.lab3weather.Fragments.weather;
import co.edu.udea.compumovil.gr3.lab3weather.POJO.weatherPOJO;
import co.edu.udea.compumovil.gr3.lab3weather.services.WeatherService;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static boolean active = false;
    public static String TIME_TAG="TIME";
    public static String CITY_TAG="CITY";
    public static  String ACTION_CUSTOM = "action.custom";
    public static String OBJECT_WP="OBJECT";
    public static int time=60;
    public static String ciudad="Medellin";
    public static ProgressDialog  progress;

    Fragment fragmentWeather,fragmentSettings,fragmentOption;
    FragmentManager fragmentManager;
    weather w =new weather();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if (!isMyServiceRunning()) {
            Intent i = new Intent(this, WeatherService.class);
            i.putExtra(MainActivity.TIME_TAG, time);
            this.startService(i);
            Log.d("weather","My service was not running");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer,toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        progress = ProgressDialog.show(this, "Cargando...",
                "Por favor espere", true);
        fragmentManager = getSupportFragmentManager();
        fragmentWeather = new weather();
        fragmentSettings=new settings();
        fragmentManager
                    .beginTransaction()
                    .replace(R.id.content_main, fragmentWeather)
                    .commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        fragmentOption=null;
        fragmentManager = getSupportFragmentManager();

       if (id == R.id.nav_weather) {
           fragmentOption=fragmentWeather;

        } else if (id == R.id.nav_settings) {
            fragmentOption=fragmentSettings;

       }

        if(fragmentOption!=null){
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.content_main, fragmentOption)
                    .commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        active = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        active = false;
    }
    public boolean isMyServiceRunning() {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (WeatherService.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }



}
