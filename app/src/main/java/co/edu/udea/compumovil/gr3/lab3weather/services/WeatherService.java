package co.edu.udea.compumovil.gr3.lab3weather.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import co.edu.udea.compumovil.gr3.lab3weather.Fragments.weather;
import co.edu.udea.compumovil.gr3.lab3weather.MainActivity;
import co.edu.udea.compumovil.gr3.lab3weather.POJO.weatherPOJO;
import co.edu.udea.compumovil.gr3.lab3weather.Singleton.MySingleton;

/**
 * Created by mguillermo.ochoa on 19/09/16.
 */
public class WeatherService extends Service {
    private static final String TAG ="WeatherService.java";
    private final String API_KEY="a114981a45d6ad13ade4e27c615513b9";
    Gson outGson;
    weather w=new weather();
    public weatherPOJO wp;
    TimerTask timerTask;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int tiempo=intent.getIntExtra(MainActivity.TIME_TAG,60);
        String ciudad=intent.getStringExtra(MainActivity.CITY_TAG);
        Log.d(TAG, "El extra tiempo es: "+tiempo+" El extra ciudad es "+ciudad);



        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "Servicio creado...");
        super.onCreate();
        Timer timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                RequestQueue queue = Volley.newRequestQueue(getBaseContext());
                String url ="http://api.openweathermap.org/data/2.5/weather?q=Medellin,co&appid="+API_KEY+"&lang=en&units=metric";



                // Request a string response from the provided URL.
                JsonObjectRequest jsObjRequest = new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                outGson=new Gson();
                                wp=outGson.fromJson(response.toString(),weatherPOJO.class);
                                Log.d("weather.java",wp.getName()+wp.getMain().getTemp()+wp.getMain().getHumidity()+wp.getWeather().get(0).getDescription()+wp.getWeather().get(0).getIcon());
                                Intent intent = new Intent();
                                intent.setAction(MainActivity.ACTION_CUSTOM);
                                intent.putExtra(MainActivity.OBJECT_WP,  wp);
                                weather.mBroadcastManager.sendBroadcastSync(intent);


                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO Auto-generated method stub

                            }
                        });
                MySingleton.getInstance(getBaseContext()).addToRequestQueue(jsObjRequest);


            }
        };

        timer.scheduleAtFixedRate(timerTask, 0, 10*1000);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



    public WeatherService() {

    }
}
