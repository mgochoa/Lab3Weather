package co.edu.udea.compumovil.gr3.lab3weather.Fragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.apache.commons.lang3.text.WordUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import co.edu.udea.compumovil.gr3.lab3weather.MainActivity;
import co.edu.udea.compumovil.gr3.lab3weather.POJO.weatherPOJO;
import co.edu.udea.compumovil.gr3.lab3weather.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class weather extends Fragment {

    private MyReceiver myReceiver;
    public static LocalBroadcastManager mBroadcastManager;
    final String  urlImage="http://openweathermap.org/img/w/";
    ImageView iconView;
    TextView tvCiudad,tvTemp,tvHum,tvDescription,tvTime;
    weatherPOJO wp;
    ImageLoader imageLoader= ImageLoader.getInstance();
    Calendar cal;
    SimpleDateFormat sdf = new SimpleDateFormat("d MMM yyyy HH:mm:ss");

    public weather() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View thisview=inflater.inflate(R.layout.fragment_weather, container, false);
        getActivity().setTitle("Clima");

        //getWeather=(Button)thisview.findViewById(R.id.btn_weather);
        tvCiudad=(TextView)thisview.findViewById(R.id.tv_city);
        tvTemp=(TextView)thisview.findViewById(R.id.tv_temp);
        tvHum=(TextView)thisview.findViewById(R.id.tv_humidity);
        tvDescription=(TextView)thisview.findViewById(R.id.tv_description);
        tvTime=(TextView)thisview.findViewById(R.id.tv_time);
        iconView=(ImageView)thisview.findViewById(R.id.icon_image);
        imageLoader.init(ImageLoaderConfiguration.createDefault(getContext()));

        myReceiver = new MyReceiver();
        //Creating the filter
        IntentFilter filter = new IntentFilter();
        filter.addAction(MainActivity.ACTION_CUSTOM);

        //Registering the receiver
        mBroadcastManager = LocalBroadcastManager.getInstance(getActivity().getApplicationContext());
        mBroadcastManager.registerReceiver(myReceiver, filter);
        if ((savedInstanceState != null)
                && (savedInstanceState.getParcelable(MainActivity.OBJECT_WP) != null)) {
                    wp=savedInstanceState.getParcelable(MainActivity.OBJECT_WP);
            Log.d("Saving",wp.getName()+wp.getMain().getTemp()+wp.getMain().getHumidity()+wp.getWeather().get(0).getDescription()+wp.getWeather().get(0).getIcon());
            updateUI(wp);
                }

        return thisview;
    }



    @Override
    public void onDestroy() {
        mBroadcastManager.unregisterReceiver(myReceiver);
        super.onDestroy();
    }

    public class MyReceiver extends BroadcastReceiver {

        private final String TAG = "weather.java";

        @Override
        public void onReceive(Context context, Intent intent) {

            wp=intent.getParcelableExtra(MainActivity.OBJECT_WP);
            if(wp!=null){
                updateUI(wp);

            }

            Log.d(TAG, "INTENT RECEIVED");



            Toast.makeText(getContext(), "Información actualizada", Toast.LENGTH_SHORT).show();

        }
    }
    public void updateUI(weatherPOJO wp){
        cal=Calendar.getInstance();
        this.tvCiudad.setText("\t"+wp.getName());
        tvDescription.setText("\t"+WordUtils.capitalize(wp.getWeather().get(0).getDescription()));
        tvTemp.setText("\t"+Double.toString(wp.getMain().getTemp())+"°C");
        tvHum.setText("\t"+Double.toString(wp.getMain().getHumidity()));
        imageLoader.displayImage(urlImage+wp.getWeather().get(0).getIcon()+".png",iconView);
        tvTime.setText("\t"+sdf.format(cal.getTime()));

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            wp=savedInstanceState.getParcelable(MainActivity.OBJECT_WP);
            Log.d("Saving",wp.getName()+wp.getMain().getTemp()+wp.getMain().getHumidity()+wp.getWeather().get(0).getDescription()+wp.getWeather().get(0).getIcon());
            updateUI(wp);
            // Restore last state for checked position.

        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.d("Saving",wp.getName()+wp.getMain().getTemp()+wp.getMain().getHumidity()+wp.getWeather().get(0).getDescription()+wp.getWeather().get(0).getIcon());

        outState.putParcelable(MainActivity.OBJECT_WP,wp);
        //Save the fragment's state here

    }





}
