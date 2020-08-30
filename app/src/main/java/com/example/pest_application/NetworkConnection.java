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
}