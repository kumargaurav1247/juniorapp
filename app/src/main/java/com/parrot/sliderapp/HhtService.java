package com.parrot.sliderapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.UUID;

import cz.msebera.android.httpclient.Header;

public class HhtService {
    //final String base_url = "http://api-testing.flexli.in/KD-PTL/api/v1/";
    //final String base_url = "http://192.168.1.111:3000/api/v1/";
    final String base_url = "http://3.6.158.79/KD-PTL/api/v1/";
    private  String mHhtId;
    private Hht mHht;
    private Hht[] mHhtList;
    private Context mContext;

    public interface GetHhtResponseHandler {
        public void onHhtReceived(String hhtId );
    }


    public void getHhtId(final GetHhtResponseHandler getHhtResponseHandler){

        SharedPreferences prefs = MainActivity.getInstance().getSharePreferences();

        // Check if HHT Id already generated and stored locally
        String hhtId = prefs.getString("HHT_ID","");

        if(!hhtId.isEmpty()) {
            Log.d("ptl", "HHT ID already exist " + hhtId);

            // Check if HHT Id exist in DataBase. If not Save HHT Id in Database

            getHhtList(hhtId, new getHhtListResponseHandler() {
                @Override
                public void onHHTNotUpdated(String hhtId) {
                    Log.d("ptl", "Update HHT ID in database " + hhtId);
                    createHht(hhtId, new createHhtResponseHandler() {
                        @Override
                        public void onHhtCreated(Hht hht) {
                            getHhtResponseHandler.onHhtReceived(hht.getId());
                        }
                    });
                }

                @Override
                public void onHHTUpdated(String hhtId) {
                    getHhtResponseHandler.onHhtReceived(hhtId);
                }
            });



        } else{

            // Generate and Store HHT Id.
            hhtId = (UUID.randomUUID().toString()).substring(0,4);
            // Store Hht Id locally
            prefs.edit().putString("HHT_ID", hhtId).apply();
            Log.d("ptl","New HHT ID is generated " + hhtId);

            // Save Hht Id in Database
            createHht(hhtId, new createHhtResponseHandler() {
                @Override
                public void onHhtCreated(Hht hht) {
                    getHhtResponseHandler.onHhtReceived(hht.getId());
                }
            });
        }
    }

    public interface createHhtResponseHandler {
        public void onHhtCreated( Hht hht);
    }

    public void createHht(String hhtId, final HhtService.createHhtResponseHandler action) {

        // Passing JSON Object in Body
        RequestParams params = new RequestParams();
        params.put("id", hhtId);
        params.setUseJsonStreamer(true);


        final String post_URL = base_url + "Hhts/" +  hhtId;

        Log.d("ptl","URL " + post_URL);
        AsyncHttpClient client = new AsyncHttpClient();

        // Adding Content Header
        client.addHeader("Content-Type", "application/json");

        client.put(post_URL,params,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("ptl", "Success! Hht created" + response.toString());
                mHht = Hht.fromJson(response);
                Log.d("ptl","HHT " + mHht);
                action.onHhtCreated( mHht);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable,JSONObject response) {
                Log.d("ptl", "Failure! Hht not created ");
                Log.d("ptl", "Status code " + statusCode  );
                Log.d("ptl", "headers " + headers  );
                Log.d("ptl", "Server Response  " + response  );
                Log.d("ptl", "throwable " + throwable  );
                action.onHhtCreated(mHht);
            }

        });
    }

    public interface getHhtListResponseHandler {
        public void onHHTNotUpdated( String hhtId);
        public void onHHTUpdated( String hhtId);
    }

    public void getHhtList(final String hhtId, final HhtService.getHhtListResponseHandler action) {




        final String get_URL = base_url + "Hhts";

        Log.d("ptl","URL " + get_URL);
        AsyncHttpClient client = new AsyncHttpClient();



        client.get(get_URL,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                //super.onSuccess(statusCode, headers, response);
                Log.d("ptl", "Success! Hht List received" + response.toString());
                if(Hht.onhHhtListReceived(response,hhtId) == null)
                {
                    Log.d("ptl","HHT not updated in dataBase " + hhtId);
                    action.onHHTNotUpdated(hhtId);
                }
                else{
                    Log.d("ptl","HHT updated in dataBase " + hhtId);
                    action.onHHTUpdated(hhtId);

                }

            };
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject response) {
                Log.d("ptl", "Failure! Hht List not received ");
                Log.d("ptl", "Status code " + statusCode  );
                Log.d("ptl", "headers " + headers  );
                Log.d("ptl", "Server Response  " + response  );
                Log.d("ptl", "throwable " + throwable  );

            }

        });
    }

}
