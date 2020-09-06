package com.example.pest_application.UI;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pest_application.NetworkConnection;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AddPestLocationViewModel extends ViewModel {
    NetworkConnection networkConnection = new NetworkConnection();
    private MutableLiveData<Double[]> pestLngLog;
    private Context context;


    public AddPestLocationViewModel() {
        pestLngLog = new MutableLiveData<>();
    }


    public LiveData<Double[]> getLiveDatapestLngLog() {
        return pestLngLog;
    }

    public void setpestLngLog(Double[] pest) {
        pestLngLog.setValue(pest);
    }


    public void getPestLocationTask(String location){
        try {

            GetPestLocation getTask = new GetPestLocation();
            getTask.execute(location);


        } catch (Exception e) {
            Log.e("getLiveDatapestLngLog","getLiveDatapestLngLog error");
        }
    }

    private class GetPestLocation extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return networkConnection.getLatAndLng(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                Double[] pestLocation = new Double[2];

                JSONObject jsonObject = new JSONObject(result);

                JSONArray resultArray = jsonObject.getJSONArray("results");
                if (resultArray.length()>0){

                    JSONObject object  =  resultArray.getJSONObject(0);
                    //Log.d("sign_up", "json: " + object);
                    JSONObject firstResult = object.getJSONObject("geometry");
                   // Log.d("sign_in", "json: " + firstResult);
                    double lat = firstResult.getDouble("lat");
                    double lng = firstResult.getDouble("lng");
                    LatLng latLng = new LatLng(lat,lng);
                    pestLocation[0] = lat;
                    pestLocation[1] = lng;
                    pestLngLog.setValue(pestLocation);
                }
                else{
                    pestLocation[0] = 0.0;
                    pestLocation[1] = 0.0;
                    pestLngLog.setValue(pestLocation);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}
