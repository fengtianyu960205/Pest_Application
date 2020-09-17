package com.example.pest_application.UI.Report;

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

public class report2Viewmodel extends ViewModel {

    NetworkConnection networkConnection = new NetworkConnection();
    private MutableLiveData<ArrayList> statePest;
    private Context context;
    ArrayList<String[]> list = new ArrayList<>();
    public report2Viewmodel(){
        statePest = new MutableLiveData<>();
    }

    public LiveData<ArrayList> getStateInfo(){
        return statePest;
    }

    public void setStateInfo(ArrayList<String[]> pest) {
        statePest.setValue(pest);
    }

    public void GetStateInfoTa(){
        report2Viewmodel.searchreport2Task searchPestByStateTask = new report2Viewmodel.searchreport2Task();
        searchPestByStateTask.execute();
    }

    private class searchreport2Task extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            //Log.d("sign_in", "json: " +params[0] );
            return networkConnection.getReport2();
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

                        String[] pest = new String[3];
                        Log.d("sign_in", "dfdfddd: " + pestInfo);
                        Integer id = pestInfo.getInt("number");
                        String idStr = id.toString();
                        String state = pestInfo.getString("state");
                        String category = pestInfo.getString("category");


                        //Pest pest = new Pest(id,name,height,weight,country,category,diet,ways,tips,imageURL);
                        pest[0] = idStr;
                        pest[1] = state;
                        pest[2] = category;
                        list.add(pest);
                    }

                }
                else{
                    list = new ArrayList<>();
                }

                statePest.setValue(list);
            } catch (Exception e) {
                statePest.setValue(list);
            }
        }
    }


}

