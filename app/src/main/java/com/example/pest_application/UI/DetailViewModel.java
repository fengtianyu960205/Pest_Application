package com.example.pest_application.UI;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class DetailViewModel extends ViewModel {
    private MutableLiveData<String[]> pestInfo;
    private Context context;
    String[] list = new String[10];


    public DetailViewModel(){
        pestInfo = new MutableLiveData<>();
    }

    public LiveData<String[]> getPestInfo(){
        return pestInfo;
    }

    public void setPestInfo(String[] pest) {
        pestInfo.setValue(pest);
    }

}
