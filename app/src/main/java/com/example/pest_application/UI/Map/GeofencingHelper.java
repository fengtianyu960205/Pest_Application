package com.example.pest_application.UI.Map;

import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.util.Log;
import android.widget.EditText;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.maps.model.LatLng;

public class GeofencingHelper extends ContextWrapper {

    private static final String TAG = "GeofenceHelper";
    PendingIntent pendingIntent;


    public GeofencingHelper(Context base) {
        super(base);
    }

    public GeofencingRequest geofencingRequest(Geofence geofence){
        return new GeofencingRequest.Builder()
                .addGeofence(geofence)
                .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
                .build();
    }

    public Geofence getGeofence(String ID, LatLng latLng, float radius, int type){
        Log.d("TAG", "hghghgjjh: " + type);
        return new Geofence.Builder().setCircularRegion(latLng.latitude,latLng.longitude,radius )
                .setRequestId(ID)
                .setTransitionTypes(type)
                .setLoiteringDelay(50000)
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .build();
    }

    public PendingIntent getPendingIntent(){
        if(pendingIntent != null){
            return pendingIntent;
        }
        Intent intent = new Intent(this,GeofenceBroadcastReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this,2607,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }

    public String getErroeString(Exception e){
        if(e instanceof ApiException){
            ApiException apiException = (ApiException)e;
            switch(apiException.getStatusCode()){
                case GeofenceStatusCodes
                        .GEOFENCE_NOT_AVAILABLE:
                    return "GeoFencing not available";
                case GeofenceStatusCodes
                        .GEOFENCE_TOO_MANY_GEOFENCES:
                    return "GeoFencing too many";
                case GeofenceStatusCodes
                        .GEOFENCE_TOO_MANY_PENDING_INTENTS:
                    return "GeoFencing too mant pending intents";

            }
        }
        return e.getLocalizedMessage();
    }
}
