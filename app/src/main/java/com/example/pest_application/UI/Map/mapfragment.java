package com.example.pest_application.UI.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.pest_application.R;

public class mapfragment  extends Fragment {

    private Button tourpestmao_btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.mapfragment, container, false);

        tourpestmao_btn =  view.findViewById(R.id.tourpestmao_btn);

        tourpestmao_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MapsActivity.class));
            }
        });
        return view;
    }
}
