package co.edu.udea.compumovil.gr3.lab3weather.Fragments;


import android.app.DownloadManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import co.edu.udea.compumovil.gr3.lab3weather.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class weather extends Fragment {

    Button getWeather;
    TextView tvWeather;
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
        tvWeather=(TextView)thisview.findViewById(R.id.tv_weather);
        getWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue queue = Volley.newRequestQueue(getContext());
                String url ="http://api.openweathermap.org/data/2.5/weather?q=Medellin,co&appid="+API_KEY;

// Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the first 500 characters of the response string.
                                tvWeather.setText("Response is: "+ response.substring(0,500));
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        tvWeather.setText("That didn't work!");
                    }
                });
// Add the request to the RequestQueue.
                queue.add(stringRequest);
            }
        });
        return thisview;
    }



}
