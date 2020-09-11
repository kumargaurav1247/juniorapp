package com.parrot.sliderapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment1 extends Fragment {
    private ImageView imageView;
    private ImageView mChangePtlStation;
    private ImageView mContinueWaveDistribution;
    private TextView mPtlStationId;
    private  HHTSuccessfullyMappedResponseHandler mInterface;

    public Fragment1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_1, container, false);
        imageView = view.findViewById(R.id.image);



        mPtlStationId = (TextView) view.findViewById(R.id.ptlStationId);
        new PtlStationService().getPTlStationLinkedToHht(new PtlStationService.GetPtlStationResponseHandler() {
            @Override
            public void onPtlStationReceived(PtlStation mPtlStation) {

                Log.d("ptl","Interface: Success Object created ");
                mPtlStationId.setText( mPtlStation.getId());
            }

        });


        // On Change Ptl Station Button Click navigate to screen "Scan PTL Station"
        mChangePtlStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("ptl","Change PTL Station Requested");
                mInterface.onChangePtlStationRequested();

            }
        });


        // On Continue Wave Distribution Button Click navigate to screen "Place Shipper Box"
        mContinueWaveDistribution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("ptl","Continue Wave Distribution Requested");

                mInterface.onStartWaveDistributionRequested();

            }
        });

        return view;
    }


    public interface HHTSuccessfullyMappedResponseHandler {

        public void onChangePtlStationRequested();

        public void onStartWaveDistributionRequested();

    }

    public void setInterface(HHTSuccessfullyMappedResponseHandler setInterface){

        mInterface = setInterface;
    }





}
