package com.example.pest_application.UI.Home;

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

public class SearchPestByStateViewModel extends ViewModel {
    NetworkConnection networkConnection = new NetworkConnection();
    private MutableLiveData<ArrayList> pestInfo;
    private Context context;
    //ArrayList<Pest> list = new ArrayList<>();
    ArrayList<String[]> list = new ArrayList<>();
    public SearchPestByStateViewModel(){
        pestInfo = new MutableLiveData<>();
    }

    public LiveData<ArrayList> getPestInfo(){
        return pestInfo;
    }

    public void setPestInfo(ArrayList<String[]> pest) {
        pestInfo.setValue(pest);
    }

    public void GetPestInfoTa(String state){
        SearchPestByStateViewModel.searchPestByStateTask searchPestByStateTask = new SearchPestByStateViewModel.searchPestByStateTask();
        searchPestByStateTask.execute(state);
    }

    private class searchPestByStateTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            //Log.d("sign_in", "json: " +params[0] );
            return networkConnection.searchPestBYState(params[0]);
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

                        String[] pest = new String[12];
                        Log.d("sign_in", "json: " + pestInfo);
                        Integer id = pestInfo.getInt("pestId");
                        String idStr = id.toString();
                        String name = pestInfo.getString("pestName");
                        String height = pestInfo.getString("height");
                        String weight = pestInfo.getString("weight");
                        String country = pestInfo.getString("country");
                        String category = pestInfo.getString("category");
                        String diet = pestInfo.getString("diet");
                        String ways = pestInfo.getString("ways");
                        String tips = pestInfo.getString("tips");
                        String imageURL = pestInfo.getString("imageURL");
                        String threat = pestInfo.getString("threat");
                        String score = pestInfo.getString("score");

                        //Pest pest = new Pest(id,name,height,weight,country,category,diet,ways,tips,imageURL);
                        pest[0] = idStr;
                        pest[1] = name;
                        pest[2] = height;
                        pest[3] = weight;
                        pest[4] = country;
                        pest[5] = category;
                        pest[6] = diet;
                        pest[7] = ways;
                        pest[8] = tips;
                        pest[9] = imageURL;
                        pest[10] = threat;
                        pest[11] = score;


                        list.add(pest);
                    }

                }
                else{
                    list = new ArrayList<>();
                }

                pestInfo.setValue(list);
            } catch (Exception e) {
                pestInfo.setValue(list);
            }
        }
    }

}
