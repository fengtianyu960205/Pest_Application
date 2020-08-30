package com.example.pest_application.UI.showAll;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pest_application.NetworkConnection;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MapPest_viewMoel extends ViewModel {
    private NetworkConnection networkConnection = new NetworkConnection();;
    private MutableLiveData<ArrayList> pestLoca;
    private Context context;
    //ArrayList<Pest> list = new ArrayList<>();
    ArrayList<String[]> list = new ArrayList<>();
    public MapPest_viewMoel(){
        pestLoca = new MutableLiveData<>();
    }

    public LiveData<ArrayList> getPestLoca(){
        return pestLoca;
    }

    public void setPestLoca(ArrayList<String[]> pest) {
        pestLoca.setValue(pest);
    }

    public void GetPestLocaTa(int id){
        GetPestLocaTask getpestlocaTask = new GetPestLocaTask();
        getpestlocaTask.execute(id);
    }

    private class GetPestLocaTask extends AsyncTask<Integer, Void, String> {
        @Override
        protected String doInBackground(Integer... params) {
            Log.d("sign_in", "findloction: " + params[0]);
            return networkConnection.getPestLocationInfo(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {

            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("Pests_location");

                if (jsonArray != null && jsonArray.length() > 0){
                    for(int i = 0; i < jsonArray.length() ; i++){
                        JSONObject pestInfo = jsonArray.getJSONObject(i);
                        String[] pest = new String[4];
                        Log.d("sign_in", "json: " + pestInfo);
                        //Integer id = pestInfo.getInt("pestId");
                       // String idStr = id.toString();
                        //String name = pestInfo.getString("pestName");
                        Double longtitude = pestInfo.getDouble("longtitude");
                        Double latitude = pestInfo.getDouble("latitude");
                        String location = pestInfo.getString("location");
                        String longtitudestr = longtitude.toString();
                        String latitudestr = latitude.toString();

                        //Pest pest = new Pest(id,name,height,weight,country,category,diet,ways,tips,imageURL);
                        pest[0] = longtitudestr;
                        pest[1] = latitudestr;
                        pest[2] = location;



                        list.add(pest);
                    }

                }

                pestLoca.setValue(list);
            } catch (Exception e) {
                pestLoca.setValue(list);
            }
        }
    }
}
