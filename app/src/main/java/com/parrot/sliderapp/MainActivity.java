package com.parrot.sliderapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private FrameLayout frameLayout;

    private TextView ptlStationId;
    private TextView waveNo;
    private static MainActivity instance;

    private Context context;
    private String MY_PREFERENCES = "myPreferences";




    @Override
    public <T extends View> T findViewById(int id) {
        return super.findViewById(id);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = getSharedPreferences("MY_PREFERENCES", 0);
        //prefs.edit().remove("serverIpAddress");
        //prefs.edit().putString("serverIpAddress", "api-testing.flexli.innnn").apply();
        String serverIpAddress = prefs.getString("serverIpAddress", null);
        Log.d("ptl", "Main Activity" + serverIpAddress);


        frameLayout = findViewById(R.id.main_framelayout);


        ptlStationId = (TextView) findViewById(R.id.ptlStationId);
        waveNo = findViewById(R.id.waveNo);
        instance = this;
        context = this;



        // Get Ptl Station linked to HHT, if no Station is linked initiate steps to link HHT with Ptl Station
        new LinkHhtToPtlStationDeck().showUp(context);



    };





    



    // Navigate to next fragment in the same Loop
    public void navigateToNextFragmentInSameLoop(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slideout_from_left);
        fragmentTransaction.replace(frameLayout.getId(), fragment);
        fragmentTransaction
                .commit();

    }


    // Start new Loop
    public void startNewLoop(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up);
        fragmentTransaction.replace(frameLayout.getId(), fragment);
        fragmentTransaction.commitAllowingStateLoss();

    }

    // End current Loop
    public void endCurrentLoop() {
        final Fragment toRemove = getSupportFragmentManager().findFragmentById(R.id.main_framelayout);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_top, R.anim.slide_out_bottom);
        fragmentTransaction.remove(toRemove);
        fragmentTransaction.commitAllowingStateLoss();


    }


    // Set First Fragment
    public void setFirstFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up);
        fragmentTransaction.replace(frameLayout.getId(), fragment);
        fragmentTransaction.commitAllowingStateLoss();

    }
    // Update Ptl Station and Wave Id when HHT is successfully linked
    public void updatePtlStationIdAndWaveId(String mPtlStationId, String mWaveId) {

        ptlStationId.setText(mPtlStationId);
        waveNo.setText(mWaveId);



    }

    public String getPtlStationId() {
        return ptlStationId.getText().toString();
    }
    public String getWaveNo() {
        return waveNo.getText().toString();
    }

    // Get Instance of Main Activity
    public static MainActivity getInstance() {
        return instance;
    }

    public Context getContext(){return context;}

    public SharedPreferences getSharePreferences(){

        SharedPreferences prefs = getSharedPreferences("MY_PREFERENCES", 0);

        return prefs;
    }

    }



