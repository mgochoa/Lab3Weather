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
    //Button getWeather;
    ImageView iconView;
    TextView tvCiudad,tvTemp,tvHum,tvDescription,tvIcon;
    weatherPOJO wp;
    ImageLoader imageLoader= ImageLoader.getInstance();

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
        tvIcon=(TextView)thisview.findViewById(R.id.tv_icon);
        iconView=(ImageView)thisview.findViewById(R.id.icon_image);
        imageLoader.init(ImageLoaderConfiguration.createDefault(getContext()));

        myReceiver = new MyReceiver();
        //Creating the filter
        IntentFilter filter = new IntentFilter();
        filter.addAction(MainActivity.ACTION_CUSTOM);

        //Registering the receiver
        mBroadcastManager = LocalBroadcastManager.getInstance(getActivity().getApplicationContext());
        mBroadcastManager.registerReceiver(myReceiver, filter);

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
                tvCiudad.setText(wp.getName());
                tvDescription.setText(wp.getWeather().get(0).getDescription());
                tvTemp.setText(Double.toString(wp.getMain().getTemp()));
                tvHum.setText(Double.toString(wp.getMain().getHumidity()));
                tvIcon.setText(wp.getWeather().get(0).getIcon());
                imageLoader.displayImage(urlImage+wp.getWeather().get(0).getIcon()+".png",iconView);
               // Log.d("weather.java",wp.getName()+wp.getMain().getTemp()+wp.getMain().getHumidity()+wp.getWeather().get(0).getDescription()+wp.getWeather().get(0).getIcon());
            }

            Log.d(TAG, "INTENT RECEIVED");



            Toast.makeText(getContext(), "INTENT RECEIVED by Receiver", Toast.LENGTH_SHORT).show();

        }
    }

}
