package com.example.pest_application.UI.Home;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pest_application.R;
import com.example.pest_application.UI.showAll.ShowAllPestsAdapter;
import com.example.pest_application.UI.showAll.ShowAllPestsViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements  ShowAllPestsAdapter.OnItemListener {

    private TextView search_pestName,resultnotfound;
    private Spinner filter_state;
    private Context context;
    private Button searchPest_btn;
    private RecyclerView recyclerView;
    private ArrayList<String[]> pests = new ArrayList<>();
    private ShowAllPestsAdapter adpater;
    private SearchPestByStateViewModel searchPestByStateViewModel;
    private String state;
    private EditText search_Name;
    private int flag;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home_fragment, container, false);
        filter_state =view.findViewById(R.id.filter_state);
        searchPest_btn = view.findViewById(R.id.searchPest_btn);
        resultnotfound =  view.findViewById(R.id.resultnotfound);
        search_Name = view.findViewById(R.id.search_pestName);
        context = getActivity();
        List<String> states = new ArrayList<>();
        states.add( "State");
        states.add("VIC");
        states.add("NSW");
        states.add("NT");
        states.add("SA");
        states.add("ACT");
        states.add("WA");
        states.add("QLD");
        states.add("TAS");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, states);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filter_state.setAdapter(arrayAdapter);
        filter_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                state = filter_state.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //final String state = filter_state.getSelectedItem().toString();
        searchPestByStateViewModel = new ViewModelProvider(this).get(SearchPestByStateViewModel.class);
        recyclerView = view.findViewById(R.id.recyclerSearchAllpest);
        adpater = new ShowAllPestsAdapter(pests,context,this);
        recyclerView.setAdapter(adpater);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);

        if(search_Name.getText().toString().trim().length() == 0 && state != "State"){
            flag = 1;
        }
        else if (search_Name.getText().toString().trim().length() !=0 && state != "State"){
            flag = 3;
        }
        else if (search_Name.getText().toString().trim().length() !=0 && state == "State"){
            flag = 2;
        }
        else{
            flag = 4;
        }

        searchPest_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("sign_in", "json: " +state);
                if (flag == 1){
                    searchPestByStateViewModel.GetPestInfoTa(state);
                    searchPestByStateViewModel.getPestInfo().observe(getViewLifecycleOwner(), new Observer<ArrayList>() {
                    @Override
                    public void onChanged(ArrayList arrayList) {
                        pests = arrayList;
                        if (pests.size() != 0) {
                            resultnotfound.setVisibility(View.GONE);
                            adpater.setPest(pests);
                        } else {
                            pests = new ArrayList<>();
                            adpater.setPest(pests);
                            resultnotfound.setVisibility(View.VISIBLE);
                        }
                    }
                     });
                }
            }
        });





        return view;
    }

    @Override
    public void whenClickIt(int position) {

    }
}
