package co.edu.udea.compumovil.gr3.lab3weather.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;

import co.edu.udea.compumovil.gr3.lab3weather.MainActivity;
import co.edu.udea.compumovil.gr3.lab3weather.R;
import co.edu.udea.compumovil.gr3.lab3weather.services.WeatherService;

/**
 * A simple {@link Fragment} subclass.
 */
public class settings extends Fragment {

    public Spinner timeSpinner;
    public AutoCompleteTextView acCities;
    private String[] times,cities;
    Button setButton;
    public settings() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Configuración");
        View thisview=inflater.inflate(R.layout.fragment_settings, container, false);

        //Views
        setButton=(Button)thisview.findViewById(R.id.btn_set);
        timeSpinner=(Spinner)thisview.findViewById(R.id.spinner_time);
        acCities=(AutoCompleteTextView)thisview.findViewById(R.id.auto_complete_city);

        //Arrays
        times=getResources().getStringArray(R.array.time_picker);
        cities=getResources().getStringArray(R.array.ciudades);

        //innicializar adapters
        ArrayAdapter<String> adapterTimes = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, times);
        ArrayAdapter<String> adapteCities = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, cities);
        adapterTimes.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        //Setear adapter time
        timeSpinner.setAdapter(adapterTimes);
        //Setear adapter ciudades
        acCities.setThreshold(1);
        acCities.setAdapter(adapteCities);
       //Button Listener
        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(), WeatherService.class);
                i.putExtra(MainActivity.TIME_TAG,Integer.parseInt(timeSpinner.getSelectedItem().toString()));
                i.putExtra(MainActivity.CITY_TAG,acCities.getText().toString());
                getActivity().startService(i);
                Fragment fragment = new weather();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_main, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();


            }
        });



        return thisview;
    }




}
