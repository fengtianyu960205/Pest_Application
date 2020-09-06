package com.example.pest_application.UI.Home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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
import com.example.pest_application.UI.DetaiPestlInformation;
import com.example.pest_application.UI.showAll.ShowAllPestsAdapter;
import com.example.pest_application.UI.showAll.showActivity;

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
    private SearchPestByCategoryNameViewModle searchPestByCategoryNameViewModel;
    private String state = "State";
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
        searchPestByCategoryNameViewModel = new ViewModelProvider(this).get(SearchPestByCategoryNameViewModle.class);
        recyclerView = view.findViewById(R.id.recyclerSearchAllpest);
        adpater = new ShowAllPestsAdapter(pests,context,this);
        recyclerView.setAdapter(adpater);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);



        searchPest_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(search_Name.getText().toString().trim().length() == 0 && !state.equals("State") ){
                    flag = 1;
                }
                else if (search_Name.getText().toString().trim().length() !=0 ){
                    flag = 2;
                    closeKeyBoard();
                }
                else{
                    flag = 3;
                }
                //Log.d("sign_in", "json: " +state);
                if (flag == 1){
                    searchPestByStateViewModel.GetPestInfoTa(state);
                    searchPestByStateViewModel.getPestInfo().observe(getViewLifecycleOwner(), new Observer<ArrayList>() {
                    @Override
                    public void onChanged(ArrayList arrayList) {
                        pests = arrayList;
                        if (pests.size() != 0) {
                            //Log.d("sign_in", "json: " +state);
                            resultnotfound.setVisibility(View.GONE);
                            adpater.setPest(pests);
                        } else {
                            //Log.d("sign_in", "json: " +state);
                            pests = new ArrayList<>();
                            adpater.setPest(pests);
                            resultnotfound.setVisibility(View.VISIBLE);
                        }
                    }
                     });
                }
                else if(flag == 2){

                    searchPestByCategoryNameViewModel.GetPestInfoTa(search_Name.getText().toString().trim()+","+state);
                    searchPestByCategoryNameViewModel.getPestInfo().observe(getViewLifecycleOwner(), new Observer<ArrayList>() {
                        //Log.d("sign_in", "json: " +flag);
                        @Override
                        public void onChanged(ArrayList arrayList) {
                            pests = arrayList;
                            if (pests.size() != 0) {
                                Log.d("sign_in", "jssdsdsdsdson: " +pests.size());
                                resultnotfound.setVisibility(View.GONE);
                                adpater.setPest(pests);
                            } else {
                                //Log.d("sign_in", "json: " +4);
                                pests = new ArrayList<>();
                                adpater.setPest(pests);
                                resultnotfound.setVisibility(View.VISIBLE);
                            }

                        }
                    });
                }
                else{
                    pests = new ArrayList<>();
                    adpater.setPest(pests);
                    resultnotfound.setVisibility(View.VISIBLE);
                }
            }
        });





        return view;
    }

    public void closeKeyBoard(){
        InputMethodManager inputManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                0);
    }

    @Override
    public void whenClickIt(int position) {
        //DetaiPestlInformation nextFrag= new DetaiPestlInformation();
        Bundle bundle=new Bundle();
        Log.d("sign_in", "ddddd: " + pests.get(position)[1]);
        bundle.putString("id",pests.get(position)[0]);
        bundle.putString("pestName",pests.get(position)[1]);
        bundle.putString("height",pests.get(position)[2]);
        bundle.putString("weight",pests.get(position)[3]);
        bundle.putString("country",pests.get(position)[4]);
        bundle.putString("PestCategory",pests.get(position)[5]);
        bundle.putString("diet",pests.get(position)[6]);
        bundle.putString("ways",pests.get(position)[7]);
        bundle.putString("tips",pests.get(position)[8]);
        bundle.putString("imageURL",pests.get(position)[9]);
        bundle.putString("threat",pests.get(position)[10]);
        bundle.putString("score",pests.get(position)[11]);
        startActivity(new Intent(context, showActivity.class).putExtra("info",bundle));
    }
}
