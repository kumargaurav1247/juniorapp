package com.parrot.sliderapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PtlStation {
// TODO: Declare the member variables here

    private String id;
    private String displayName;
    private String putCycleStatus;
    private Boolean inUse;
    private int countPtlBins;
    private String waveNo;
    private int remainingSkuQuantity;
    private  int countUnlinkedStoreOrders;
    private String pickBoxId;



    private String  hhtId;

    // TODO: Create a PtlStation Model from a JSON:




    public static PtlStation fromJson (JSONObject jsonObject){


        try {
            PtlStation mPtlStation = new PtlStation();
            mPtlStation.id = jsonObject.getString("id");
            mPtlStation.displayName = jsonObject.getString("displayName");
            mPtlStation.putCycleStatus = jsonObject.getString("putCycleStatus");
            mPtlStation.inUse = jsonObject.getBoolean("inUse");
            mPtlStation.countPtlBins = jsonObject.getInt("countPtlBins");
            mPtlStation.waveNo = jsonObject.getString("waveNo");
            mPtlStation.remainingSkuQuantity = jsonObject.getInt("remainingSkuQuantity");
            mPtlStation.countUnlinkedStoreOrders = jsonObject.getInt("countUnlinkedStoreOrders");
            mPtlStation.pickBoxId = jsonObject.getString("pickBoxId");



            return mPtlStation;
        } catch(JSONException e) {
            e.printStackTrace();
            return null;
        }

    };



    public static String getptlStationId (JSONObject jsonObject){


        try {
            JSONArray itemArray = new JSONArray(jsonObject);
            String ptlStationId = itemArray.getString(0);

            return ptlStationId;
        } catch(JSONException e) {
            e.printStackTrace();
            return null;
        }

    };

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getPutCycleStatus() {
        return putCycleStatus;
    }

    public Boolean getInUse() {
        return inUse;
    }

    public int getCountPtlBins() {
        return countPtlBins;
    }

    public String getHhtId() {
        return hhtId;
    }

    public String getWaveNo() {
        return waveNo;
    }



    public int getCountUnlinkedStoreOrders() {
        return countUnlinkedStoreOrders;
    }
    public void setHhtId(String hhtId) {
        this.hhtId = hhtId;
    }

    public int getRemainingSkuQuantity() {
        return remainingSkuQuantity;
    }

}
