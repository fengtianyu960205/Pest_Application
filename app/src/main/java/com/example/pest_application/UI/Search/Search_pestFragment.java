package com.example.pest_application.UI.Search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.pest_application.R;

public class Search_pestFragment extends Fragment {

    private TextView textView;
    public Search_pestFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the View for this fragment
        View view = inflater.inflate(R.layout.show_allpest, container, false);
        //textView=view.findViewById(R.id.tv);
        //textView.setText("This is HOME");
        return view;
    }

}