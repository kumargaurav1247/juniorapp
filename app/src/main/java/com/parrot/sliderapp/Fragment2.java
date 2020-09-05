package com.parrot.sliderapp;

import android.hardware.Camera;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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
     Button button;



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
        button = (Button) view.findViewById(R.id.scn_btn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("key",mScanPtlStationInputText.getText().toString());

                Fragment3 frg = new Fragment3();
                frg.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.main_framelayout,frg).commit();
            }
        });
        return view;
    }

    @Override
    public void handleResult(Result result) {

    }
}
