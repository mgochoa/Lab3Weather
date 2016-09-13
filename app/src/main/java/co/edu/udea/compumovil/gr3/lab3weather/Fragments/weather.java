package co.edu.udea.compumovil.gr3.lab3weather.Fragments;


import android.app.DownloadManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONObject;

import co.edu.udea.compumovil.gr3.lab3weather.POJO.weatherPOJO;
import co.edu.udea.compumovil.gr3.lab3weather.R;
import co.edu.udea.compumovil.gr3.lab3weather.Singleton.MySingleton;

/**
 * A simple {@link Fragment} subclass.
 */
public class weather extends Fragment {

    Button getWeather;
    ImageView iconView;
    TextView tvCiudad,tvTemp,tvHum,tvDescription,tvIcon;
    Gson outGson;
    weatherPOJO wp;
    ImageLoader imageLoader= ImageLoader.getInstance();
    private final String API_KEY="a114981a45d6ad13ade4e27c615513b9";

    public weather() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View thisview=inflater.inflate(R.layout.fragment_weather, container, false);
        getActivity().setTitle("Clima");
        getWeather=(Button)thisview.findViewById(R.id.btn_weather);
        tvCiudad=(TextView)thisview.findViewById(R.id.tv_city);
        tvTemp=(TextView)thisview.findViewById(R.id.tv_temp);
        tvHum=(TextView)thisview.findViewById(R.id.tv_humidity);
        tvDescription=(TextView)thisview.findViewById(R.id.tv_description);
        tvIcon=(TextView)thisview.findViewById(R.id.tv_icon);
        iconView=(ImageView)thisview.findViewById(R.id.icon_image);
        getWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue queue = Volley.newRequestQueue(getContext());
                String url ="http://api.openweathermap.org/data/2.5/weather?q=Medellin,co&appid="+API_KEY+"&lang=en&units=metric";
                final String  urlImage="http://openweathermap.org/img/w/";

        // Request a string response from the provided URL.
                JsonObjectRequest jsObjRequest = new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                //tvWeather.setText("Response: " + response.toString());
                                outGson=new Gson();
                                wp=outGson.fromJson(response.toString(),weatherPOJO.class);
                                tvCiudad.setText(wp.getName());
                                tvDescription.setText(wp.getWeather().get(0).getDescription());
                                tvTemp.setText(Integer.toString(wp.getMain().getTemp()));
                                tvHum.setText(Integer.toString(wp.getMain().getHumidity()));
                                tvIcon.setText(wp.getWeather().get(0).getIcon());
                                imageLoader.init(ImageLoaderConfiguration.createDefault(getContext()));
                                imageLoader.displayImage(urlImage+wp.getWeather().get(0).getIcon()+".png",iconView);
                                Log.d("weather.java",wp.getName()+wp.getMain().getTemp()+wp.getMain().getHumidity()+wp.getWeather().get(0).getDescription()+wp.getWeather().get(0).getIcon());

                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO Auto-generated method stub

                            }
                        });
                MySingleton.getInstance(getContext()).addToRequestQueue(jsObjRequest);


            }
        });
        return thisview;
    }



}
