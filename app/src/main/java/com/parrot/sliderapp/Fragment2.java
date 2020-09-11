package com.parrot.sliderapp;

import android.hardware.Camera;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment2 extends Fragment implements ZXingScannerView.ResultHandler{

    private ImageView imageView;
     EditText mScanPtlStationInputText;
    private ScanPtlStationResponseHandler mInterface;

    public interface onSomeEventListener {
        public void someEvent(String s);
    }

    onSomeEventListener someEventListener;





    public Fragment2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_2, container, false);
        imageView = view.findViewById(R.id.image);
        mScanPtlStationInputText = (EditText) view.findViewById(R.id.scanPtlStationInputText);

        mScanPtlStationInputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String ptlStationId = mScanPtlStationInputText.getText().toString();
                Log.d("ptl","Your message is: " +  ptlStationId);

                if(!ptlStationId.equals("")) {

                    String hhtId = mInterface.getHhtId();

                    Log.d("ptl","Scan PTl Station is working");
                    // On Ptl Station Barcode Scan Scan link HHT to Ptl Station and move to next fragment
                    new PtlStationService().linkPtlStationWithHht(hhtId,ptlStationId, new PtlStationService.LinkPtlStationResponseHandler() {
                        @Override
                        public void onPltStationLinked(PtlStation ptlStation) {
                            if(ptlStation == null){

                                mInterface.onPtlLinkingError();
                                Log.d("ptl","Error in linking Ptl Station");

                            }else {
                                Log.d("ptl", "Ptl Station Linked ");
                                // Call interface to take action on Ptl Station Linked with HHT
                                mInterface.onPtlStationLinked(ptlStation.getId(), ptlStation.getWaveNo());
                            }
                        }
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        return view;
    }


    public interface ScanPtlStationResponseHandler {
        public void onPtlStationLinked(String ptlStationId, String waveNo);

        public void onPtlLinkingError();

        public String getHhtId();


    }

    public void setInterface(ScanPtlStationResponseHandler setInterface){
        mInterface = setInterface;

    }

    @Override
    public void handleResult(Result result) {
    }


}
