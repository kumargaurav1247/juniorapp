package com.parrot.sliderapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parrot.sliderapp.PtlStation;
import com.parrot.sliderapp.PtlStationService;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment3 extends Fragment {
    private ImageView imageView;
    private ImageView mHhtSuccessfullyMapped;
    private TextView newPtlStationId;
    private HhtSuccessfullyMappedResponseHandler mInterface;

    public Fragment3() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_3, container, false);
        imageView = view.findViewById(R.id.image);
        newPtlStationId = view.findViewById(R.id.newPtlStationId);

        new PtlStationService().getPTlStationLinkedToHht(new PtlStationService.GetPtlStationResponseHandler() {
            @Override
            public void onPtlStationReceived(PtlStation mPtlStation) {

                Log.d("ptl","Interface: Success Object created ");
                newPtlStationId.setText(mPtlStation.getId());

            }
        });


        mHhtSuccessfullyMapped = view.findViewById(R.id.hhtSuccessfullyMapped);

        // On Place Item in Bin  Button Click navigate to screen "Move Pick Box Forward"
        mHhtSuccessfullyMapped.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Call Interface to take action on Continue Wave Distribution Request
                mInterface.onContinueWaveDistributionRequest();
            }
        });



        return view;
    }

    public interface HhtSuccessfullyMappedResponseHandler {
        public void onContinueWaveDistributionRequest();


    }

    public void setInterface(HhtSuccessfullyMappedResponseHandler setInterface){
        mInterface = setInterface;
    }



}
