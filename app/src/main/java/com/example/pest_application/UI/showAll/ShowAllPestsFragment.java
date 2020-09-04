package com.example.pest_application.UI.showAll;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pest_application.MainActivity;
import com.example.pest_application.Pest;
import com.example.pest_application.R;
import com.example.pest_application.UI.DetaiPestlInformation;

import java.util.ArrayList;

public class ShowAllPestsFragment extends Fragment implements  ShowAllPestsAdapter.OnItemListener{

    private Context context;
    private ShowAllPestsViewModel showAllPestsViewModel;
    private ShowAllPestsAdapter showAllPestsAdapter;
    private ArrayList<String[]> pests = new ArrayList<>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.show_allpest, container, false);
       // ((MainActivity) getActivity()).setToolBarTitle("All Pests");
        context = getActivity();
        RecyclerView recyclerView = view.findViewById(R.id.recyclerAllpest);
        showAllPestsAdapter = new ShowAllPestsAdapter(pests,context,this);
        recyclerView.setAdapter(showAllPestsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);
        showAllPestsViewModel = new ViewModelProvider(this).get(ShowAllPestsViewModel.class);
        showAllPestsViewModel.GetPestInfoTa();
        showAllPestsViewModel.getPestInfo().observe(getViewLifecycleOwner(), new Observer<ArrayList>() {
            @Override
            public void onChanged(ArrayList arrayList) {

                //Log.d("sign_in", "json: " + list.get(0).getCountry());
                pests = arrayList;
                showAllPestsAdapter.setPest(arrayList);

            }
        });

        return view;
    }

    @Override
    public void whenClickIt(int position) {
        DetaiPestlInformation nextFrag= new DetaiPestlInformation();
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

        nextFrag.setArguments(bundle);
        ((FragmentActivity)context).getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, nextFrag, "findThisFragment")
                .addToBackStack(null)
                .commit();
    }

   /* @Override
    public void whenClickIt(int position) {
        String[] pest = new String[10];
        pest[0] = pestList.get(getAdapterPosition())[0];
        pest[1] = pestList.get(getAdapterPosition())[1];
        pest[2] = pestList.get(getAdapterPosition())[2];
        pest[3] = pestList.get(getAdapterPosition())[3];
        pest[4] = pestList.get(getAdapterPosition())[4];
        pest[5] = pestList.get(getAdapterPosition())[5];
        pest[6] = pestList.get(getAdapterPosition())[6];
        pest[7] = pestList.get(getAdapterPosition())[7];
        pest[8] = pestList.get(getAdapterPosition())[8];
        pest[9] = pestList.get(getAdapterPosition())[9];
    }*/
}