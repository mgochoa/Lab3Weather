package co.edu.udea.compumovil.gr3.lab3weather;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import co.edu.udea.compumovil.gr3.lab3weather.Fragments.settings;
import co.edu.udea.compumovil.gr3.lab3weather.Fragments.weather;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static String TIME_TAG="TIME";
    public static String CITY_TAG="CITY";
    public static  String ACTION_CUSTOM = "action.custom";
    public static String OBJECT_WP="OBJECT";

    Fragment fragmentWeather,fragmentSettings,fragmentOption;
    FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer,toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        fragmentManager = getSupportFragmentManager();
        fragmentWeather=new weather();
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


}
