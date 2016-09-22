package co.edu.udea.compumovil.gr3.lab3weather.broadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import co.edu.udea.compumovil.gr3.lab3weather.MainActivity;
import co.edu.udea.compumovil.gr3.lab3weather.services.WeatherService;


public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            Log.d("weather","BoOT_COMPLETED");
            Intent serviceIntent = new Intent(context,WeatherService.class);
            serviceIntent.putExtra(MainActivity.TIME_TAG, 60);
            context.startService(serviceIntent);
        }
    }
}
