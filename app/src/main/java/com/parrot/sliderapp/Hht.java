package com.parrot.sliderapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Hht {
    private String id;
    private String ptlStationId;


    // TODO: Create a Hht Model from a JSON:



    public static Hht fromJson (JSONObject jsonObject){


        try {
            Hht hht = new Hht();
            hht.id = jsonObject.getString("id");
            hht.ptlStationId = jsonObject.getString("ptlStationId");

            return hht;
        } catch(JSONException e) {
            e.printStackTrace();
            return null;
        }

    };

    public static String onhHhtListReceived (JSONArray hhtArray, String hhtId){

        try {

            for(int i = 0;i<hhtArray.length();i++) {
                JSONObject jsonObject1 = hhtArray.getJSONObject(i);

                Hht hht = new Hht();
                hht.id = jsonObject1.getString("id");
                if(hht.id.equals(hhtId)) {
                    return hhtId;
                }
            }
            return null;
        } catch(JSONException e) {
            e.printStackTrace();
            return null;
        }

    };

    public String getId() {
        return id;
    }

    public String getPtlStationId() {
        return ptlStationId;
    }


}
