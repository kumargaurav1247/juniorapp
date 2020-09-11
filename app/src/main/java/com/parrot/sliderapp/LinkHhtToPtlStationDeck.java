package com.parrot.sliderapp;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class LinkHhtToPtlStationDeck extends AppCompatActivity {

    // Declare Variables
    Context mContext;
    // Global variable to store Ptlstation initially linked to Hht. Please note that we are not updating following
    // variable once Ptl Station is updated by user
    PtlStation mPtlStation ;

    public void showUp(Context context) {
        mContext = context;
        new PtlStationService().getPTlStationLinkedToHht(new PtlStationService.GetPtlStationResponseHandler() {
            @Override
            public void onPtlStationReceived(PtlStation PtlStation) {
                Log.d("ptl", "Interface: Success Object created ");
                // Store Ptl Station in global variable
                mPtlStation = PtlStation;


                    // Set First Fragment to Scan Ptl Station fragment
                MainActivity.getInstance().setFirstFragment(getFragment2());


            }
        });
    }



    public  Fragment2  getFragment2(){
        Fragment2  scanPtlStationFragment = new Fragment2 ();
        scanPtlStationFragment.setInterface(new Fragment2 .ScanPtlStationResponseHandler() {
            @Override
            public void onPtlStationLinked(String ptlStationId, String waveNo) {



                // Update Ptl Station Id and Wave Id in Main Activity
                MainActivity.getInstance().updatePtlStationIdAndWaveId(ptlStationId,waveNo);

                // On Scanning Ptl Station, navigate screen to an instance of HhtSuccessfullyMappedFragment with a response handler
                // to handle start wave distribution request
                MainActivity.getInstance().navigateToNextFragmentInSameLoop(getFragment3());
            }

            @Override
            public void onPtlLinkingError() {

            }


            @Override
            public String getHhtId() {
                return mPtlStation.getHhtId();
            }
        });
        return scanPtlStationFragment;
    }

    public Fragment getFragment3() {
        Fragment3  hhtSuccessfullyMappedFragment = new Fragment3();
        hhtSuccessfullyMappedFragment.setInterface(new Fragment3.HhtSuccessfullyMappedResponseHandler() {
            @Override
            public void onContinueWaveDistributionRequest() {
                MainActivity.getInstance().endCurrentLoop();

            }
        }) ;

        return hhtSuccessfullyMappedFragment;
    }

    public void createToast(String text){

        Toast.makeText(mContext,text,Toast.LENGTH_LONG).show();

    }

}

