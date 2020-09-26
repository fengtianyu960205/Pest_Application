package com.example.pest_application.UI.Map;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pest_application.NetworkConnection;
import com.example.pest_application.UI.showAll.MapPest_viewMoel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetAllPestLocationViewMOdel extends ViewModel {

    private NetworkConnection networkConnection = new NetworkConnection();;
    private MutableLiveData<ArrayList> pestLoca;
    private Context context;

    ArrayList<String[]> list = new ArrayList<>();

    public GetAllPestLocationViewMOdel(){
        pestLoca = new MutableLiveData<>();
    }

    public LiveData<ArrayList> getPestLoca(){
        return pestLoca;
    }

    public void setPestLoca(ArrayList<String[]> pest) {
        pestLoca.setValue(pest);
    }

    public void GetPestLocaTa(String state){
        GetAllPestLocationViewMOdel.GetAllPestLocaTask getpestlocaTask = new GetAllPestLocationViewMOdel.GetAllPestLocaTask();
        getpestlocaTask.execute(state);
    }

    private class GetAllPestLocaTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            return networkConnection.getAllpestLocation(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {

            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("Pests");
                list = new ArrayList<>();
                if (jsonArray != null && jsonArray.length() > 0){
                    for(int i = 0; i < jsonArray.length() ; i++){
                        JSONObject pestInfo = jsonArray.getJSONObject(i);
                        String[] pest = new String[5];
                        //Log.d("sign_in", "json: " + pestInfo);
                        //Integer id = pestInfo.getInt("pestId");
                        // String idStr = id.toString();
                        //String name = pestInfo.getString("pestName");
                        Double longtitude = pestInfo.getDouble("longtitude");
                        Double latitude = pestInfo.getDouble("latitude");
                        String city = pestInfo.getString("city");
                        String name = pestInfo.getString("name");
                        String category = pestInfo.getString("category");
                        String longtitudestr = longtitude.toString();
                        String latitudestr = latitude.toString();

                        //Pest pest = new Pest(id,name,height,weight,country,category,diet,ways,tips,imageURL);
                        pest[0] = name;
                        pest[1] = category;
                        pest[2] = city;
                        pest[3] = latitudestr;
                        pest[4] = longtitudestr;




                        list.add(pest);
                    }

                }
                else{
                    list = new ArrayList<>();
                }

                pestLoca.setValue(list);
            } catch (Exception e) {
                pestLoca.setValue(list);
            }
        }
    }
}
