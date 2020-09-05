package com.parrot.sliderapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment3 extends Fragment {
    private ImageView imageView;

    TextView textView;

    public Fragment3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_3, container, false);
        imageView = view.findViewById(R.id.image);
        textView = view.findViewById(R.id.newstationId);
        Bundle bundle = this.getArguments();
        String data = null;
        if (bundle != null) {
            data = bundle.getString( "key");
        }
        textView.setText(data);
        return view;
    }
}
