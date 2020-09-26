package com.example.pest_application.UI.Map;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;

public class GeofenceBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        //Toast.makeText(context, "permission denied ", Toast.LENGTH_SHORT).show();
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);

        if(geofencingEvent.hasError()){
            Log.d("TAG", "error: " + "geofencing error");
            return;
        }
       List<Geofence> geofenceList = geofencingEvent.getTriggeringGeofences();
        //for (Geofence geofence : geofenceList){

            int transitiontype = geofencingEvent.getGeofenceTransition();

            switch(transitiontype){
                case Geofence.GEOFENCE_TRANSITION_ENTER:
                    Toast.makeText(context, "Geofence enter ", Toast.LENGTH_SHORT).show();
                    break;
                case Geofence.GEOFENCE_TRANSITION_EXIT:
                    Toast.makeText(context, "Geofence exit ", Toast.LENGTH_SHORT).show();
                    break;
                case Geofence.GEOFENCE_TRANSITION_DWELL:
                    Toast.makeText(context, "Geofence dwell ", Toast.LENGTH_SHORT).show();
                    break;
        //}

        }

    }
}
