package com.example.pest_application.UI;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pest_application.NetworkConnection;

public class addtolocationtoDB extends ViewModel {
    NetworkConnection networkConnection = new NetworkConnection();

    private MutableLiveData<Integer> addLocationtoDB;

    public addtolocationtoDB() {
        addLocationtoDB = new MutableLiveData<>();
    }

    public LiveData<Integer> AddLocationToDB() {
        return addLocationtoDB;
    }


    public void addTask(String location){
        try {
            addtolocationtoDB.AddLocationDB credentialTask  = new addtolocationtoDB.AddLocationDB();
            credentialTask.execute(location);

        } catch (Exception e) {
            addLocationtoDB.setValue(1);
        }
    }

    private class AddLocationDB extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return networkConnection.addPestLocation(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            if (result.equals("204"))
                addLocationtoDB.setValue(0);
            else
                addLocationtoDB.setValue(1);

        }
    }
}
