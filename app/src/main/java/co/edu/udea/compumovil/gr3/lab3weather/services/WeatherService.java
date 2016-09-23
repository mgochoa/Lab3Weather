package co.edu.udea.compumovil.gr3.lab3weather.services;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.apache.commons.lang3.text.WordUtils;
import org.json.JSONObject;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import co.edu.udea.compumovil.gr3.lab3weather.Fragments.weather;
import co.edu.udea.compumovil.gr3.lab3weather.MainActivity;
import co.edu.udea.compumovil.gr3.lab3weather.POJO.weatherPOJO;
import co.edu.udea.compumovil.gr3.lab3weather.R;
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
    int tiempo=MainActivity.time;
    String ciudad=MainActivity.ciudad;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d(TAG, "El extra tiempo es: "+tiempo+" El extra ciudad es "+ciudad);



        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "Servicio destruido...");
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
                //RequestQueue queue = Volley.newRequestQueue(getBaseContext());
                //Variable ciudad para cambiar en settings
                String url ="http://api.openweathermap.org/data/2.5/weather?q=Medellin,co&appid="+API_KEY+"&lang=es&units=metric";



                // Request a string response from the provided URL.
                JsonObjectRequest jsObjRequest = new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                            @Override
                            public void onResponse(JSONObject response) {
                                outGson=new Gson();
                                wp=outGson.fromJson(response.toString(),weatherPOJO.class);
                                Log.d("weather.java",wp.getName()+wp.getMain().getTemp()+wp.getMain().getHumidity()+wp.getWeather().get(0).getDescription()+wp.getWeather().get(0).getIcon());
                                Intent intent = new Intent();
                                intent.setAction(MainActivity.ACTION_CUSTOM);
                                intent.putExtra(MainActivity.OBJECT_WP,  wp);
                                if(MainActivity.active) {
                                    weather.mBroadcastManager.sendBroadcastSync(intent);
                                }else{
                                    Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
                                    PendingIntent resultPendingIntent =
                                            PendingIntent.getActivity(
                                                    getApplicationContext(),
                                                    1,
                                                    resultIntent,
                                                    PendingIntent.FLAG_UPDATE_CURRENT
                                            );

                                    //notification
                                    StringBuilder contenido =new StringBuilder();
                                    contenido.append("Temperatura: ");
                                    contenido.append((int)wp.getMain().getTemp());
                                    contenido.append("°C");
                                    contenido.append("\nHumedad: ");
                                    contenido.append((int)wp.getMain().getHumidity());
                                    contenido.append("%");
                                    contenido.append("\nDescripción: ");
                                    contenido.append(WordUtils.capitalize(wp.getWeather().get(0).getDescription()));


                                    NotificationCompat.Builder mBuilder =
                                            new NotificationCompat.Builder(getApplicationContext())
                                                    .setSmallIcon(R.drawable.iconweather)
                                                    .setContentTitle("Clima para hoy")
                                                    .setContentText(WordUtils.capitalize(wp.getWeather().get(0).getDescription()))
                                                    .setDefaults(Notification.DEFAULT_ALL) // requires VIBRATE permission
                                                    .setContentIntent(resultPendingIntent)
                                                    .setStyle(new NotificationCompat.BigTextStyle()
                                                            .bigText(contenido.toString()));



                                    NotificationManager notificationManager =
                                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                    notificationManager.notify(0, mBuilder.build() );

                                }

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
        //Variable time a cambiar en los settings.
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
