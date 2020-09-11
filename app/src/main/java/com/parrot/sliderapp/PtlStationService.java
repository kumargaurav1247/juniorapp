package com.parrot.sliderapp;

import android.content.SharedPreferences;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class PtlStationService {

    public PtlStation mPtlStation;
    public String mPtlStationId;
    // private String hhtId = "2c063482-456f-41ec-a8be-9ab64e40fb45";
    SharedPreferences prefs = MainActivity.getInstance().getSharePreferences();
    String serverIpAddress = prefs.getString("serverIpAddress","");


    //final String base_url = "http://" + serverIpAddress + "/KD-PTL/api/v1/";
    //final String base_url = "http://api-testing.flexli.in/KD-PTL/api/v1/";
    // final String base_url = "http://192.168.1.111:3000/api/v1/";
    final String base_url = "http://3.6.158.79/KD-PTL/api/v1/";



    // Response handler to Return Ptl Station already linked with Hht
    public interface GetPtlStationResponseHandler {

        public void onPtlStationReceived(PtlStation mPtlStation);
    }




    public void getPTlStationLinkedToHht(final GetPtlStationResponseHandler action){

        // Call Hht Service to get Hht Id
        new HhtService().getHhtId(new HhtService.GetHhtResponseHandler() {
            @Override
            public void onHhtReceived(final String hhtId) {
                Log.d("ptl","Hht Received " + hhtId);


                final String get_url = base_url + "Hhts/" + hhtId + "/ptlStation";
                Log.d("ptl","URL " + get_url );
                // Get Ptl Station linked with HHt Id
                AsyncHttpClient client = new AsyncHttpClient();
                client.get(get_url,new JsonHttpResponseHandler(){


                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode,  headers, response);
                        Log.d("ptl","Success! PTL Station attached " + response.toString());
                        mPtlStation = PtlStation.fromJson(response);
                        if(mPtlStation == null) {
                            // If No Ptl Station is linked then create Ptl Station instance and save Hht Id in it
                            mPtlStation = new PtlStation();
                            mPtlStation.setHhtId(hhtId);
                        } else{
                            // If Ptl Station is linked then add Hht Id to Ptl Station Instance
                            mPtlStation.setHhtId(hhtId);
                        }
                        Log.d("ptl","Ptl Station Object created  " + mPtlStation);
                        action.onPtlStationReceived(mPtlStation);
                    }
                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject jsonObject) {

                        Log.d("ptl","Failure! Unable to get Ptl Station linked to Hht");

                        Log.d("ptl","StatusCode" + statusCode);
                        action.onPtlStationReceived(mPtlStation);
                    }
                });
            }
        });
    }


    // Response handler to Return newly Linked Ptl Station with Hht

    public interface LinkPtlStationResponseHandler {

        public void onPltStationLinked(PtlStation ptlStation );


    }



    public void linkPtlStationWithHht(String mHhtId, String mPtlStationId,final LinkPtlStationResponseHandler action) {


        final String post_URL = base_url + "Hhts/" + mHhtId + "/ptlStations/" + mPtlStationId;
        Log.d("ptl","log" + post_URL);

        AsyncHttpClient client = new AsyncHttpClient();

        client.post(post_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("ptl", "Success! HHT Linked with Ptl Station " + response.toString());
                mPtlStation = PtlStation.fromJson(response);
                action.onPltStationLinked(mPtlStation);
            }



            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject jsonObject) {


                Log.d("ptl", "Failure! Linking Hht to Ptl Station " );
                action.onPltStationLinked(mPtlStation);
            }

        });
    }


    // Response handler to Return Ptl Station already linked with Hht
    public interface LinkPickBoxResponseHandler {

        public void onPickBoxLinked(PtlStation ptlStation);
    }

    public void linkPickBox(String pickBoxId, String ptlStationId, final PtlStationService.LinkPickBoxResponseHandler action ){



        final String post_url = base_url + "PtlStations/" + ptlStationId + "/pickBox/" + pickBoxId;
        Log.d("ptl","URL " + post_url );
        // Link Pick Box
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(post_url,new JsonHttpResponseHandler(){


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode,  headers, response);
                Log.d("ptl","Success! PickBox linked with PTLStation " + response.toString());
                mPtlStation = PtlStation.fromJson(response);
                Log.d("ptl","PtlStation" + mPtlStation );
                action.onPickBoxLinked(mPtlStation);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String string,Throwable throwable) {
                Log.d("ptl","Failure! Unable to get PickBox");
                Log.d("ptl","StatusCode " + statusCode);
                Log.d("ptl","Headers " + headers);
                Log.d("ptl","Error " + string);


                action.onPickBoxLinked(mPtlStation);

            }
        });
    }
}

