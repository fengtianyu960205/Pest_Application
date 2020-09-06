package com.example.pest_application;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkConnection {
    private OkHttpClient client = null;
    public static final MediaType JSON =
            MediaType.parse("application/json; charset=utf-8");

    public NetworkConnection() {
        client = new OkHttpClient();
    }

    private static final String BASE_URL =
            "https://laynxpb89l.execute-api.us-east-1.amazonaws.com/Pests/getpestinfo";

    private static final String Location_URL =
            "https://laynxpb89l.execute-api.us-east-1.amazonaws.com/Pests/getpestlocation?pestid=";

    private static final String searchPestBYState_URL =
            "https://laynxpb89l.execute-api.us-east-1.amazonaws.com/Pests/searchpestbystate?state=";

    private static final String searchPestBYCategoryName_URL =
            "https://laynxpb89l.execute-api.us-east-1.amazonaws.com/Pests/searchpestbycategoryname?CategoryName=";

    private static final String addPestLocation_URL =
            "https://laynxpb89l.execute-api.us-east-1.amazonaws.com/Pests/addpestlocation?Location=";
    // use password and username to get person object
    public String getAllpestInfo() {
        //final String methodPath = "mymoviememoir.person/findByUsernameAndPasswordHash/" + + "/" ;
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL );
        Request request = builder.build();
        String results = "";
        try {
            Response response = client.newCall(request).execute();
            results = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    public String getPestLocationInfo(Integer id) {
        //final String methodPath = "mymoviememoir.person/findByUsernameAndPasswordHash/" + + "/" ;
        String location = Location_URL  + id;
        //String location = "https://laynxpb89l.execute-api.us-east-1.amazonaws.com/Pests/getpestlocation?pestid=1";
        Request.Builder builder = new Request.Builder();
        builder.url(location );
        Request request = builder.build();
        String results = "";
        try {
            Response response = client.newCall(request).execute();
            results = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    public String searchPestBYState(String state) {
        //final String methodPath = "mymoviememoir.person/findByUsernameAndPasswordHash/" + + "/" ;
        String location = searchPestBYState_URL  + state;
        //String location = "https://laynxpb89l.execute-api.us-east-1.amazonaws.com/Pests/getpestlocation?pestid=1";
        Request.Builder builder = new Request.Builder();
        builder.url(location );
        Request request = builder.build();
        String results = "";
        try {
            Response response = client.newCall(request).execute();
            results = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    public String searchPestBYCategoryName(String Name) {
        //final String methodPath = "mymoviememoir.person/findByUsernameAndPasswordHash/" + + "/" ;
        String location = searchPestBYCategoryName_URL  + Name;
        //String location = "https://laynxpb89l.execute-api.us-east-1.amazonaws.com/Pests/getpestlocation?pestid=1";
        Request.Builder builder = new Request.Builder();
        builder.url(location );
        Request request = builder.build();
        String results = "";
        try {
            Response response = client.newCall(request).execute();
            results = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    public String getLatAndLng(String address) {
        final String methodPath = "https://api.opencagedata.com/geocode/v1/json?q=" + address + "&key=01f22e6351ba44169c1a4ca92a4830b2";
        Request.Builder builder = new Request.Builder();
        builder.url(methodPath);
        Request request = builder.build();
        String results = "";
        try {
            Response response = client.newCall(request).execute();
            results = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    public String addPestLocation(String Location) {

        String location = addPestLocation_URL  + Location;

        Request.Builder builder = new Request.Builder();
        builder.url(location );
        Request request = builder.build();
        String results = "204";
        try {
            Response response = client.newCall(request).execute();
            //results = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }
}