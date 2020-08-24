package com.example.pest_application.UI;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.pest_application.R;
import com.example.pest_application.UI.showAll.ShowAllPestsViewModel;

public class DetaiPestlInformation extends Fragment {
    private TextView PestName,PestCategory,PestWeight,PestHeight,PestCountry,Diet,show_Diet,DealWithThem,ActualDealWithThem,Tips,ActualTips,Score;
    private ImageView PestImage;
    private DetailViewModel detailViewModel;
    private Context context;
    private RatingBar rateScoreStar;
    RequestOptions option =  new RequestOptions().centerCrop().placeholder(R.drawable.loading).error(R.drawable.loading);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.movieview_fragment, container, false);
        context = getActivity();
        rateScoreStar = view.findViewById(R.id.rateScoreStar);
        PestName =  view.findViewById(R.id.PestName);
        PestCategory = view.findViewById(R.id.PestCategory);
        PestWeight =  view.findViewById(R.id.PestWeight);
        PestHeight = view.findViewById(R.id.PestHeight);
        PestCountry =  view.findViewById(R.id.PestCountry);
        Diet = view.findViewById(R.id.Diet);
        show_Diet =  view.findViewById(R.id.show_Diet);
        DealWithThem = view.findViewById(R.id.DealWithThem);
        ActualDealWithThem =  view.findViewById(R.id.ActualDealWithThem);
        Tips = view.findViewById(R.id.Tips);
        ActualTips =  view.findViewById(R.id.ActualTips);
        Score = view.findViewById(R.id.Score);
        PestImage = view.findViewById(R.id.PestImage);
        detailViewModel = new ViewModelProvider(this).get(DetailViewModel.class);
        String name = getArguments().getString("pestName");
        String category = getArguments().getString("PestCategory");
        String height = getArguments().getString("height");
        String weight = getArguments().getString("weight");
        String country = getArguments().getString("country");
        String ways = getArguments().getString("ways");
        String diet = getArguments().getString("diet");
        String tips = getArguments().getString("tips");
        String imageURL = getArguments().getString("imageURL");
        PestName.setText(name);
        PestCategory.setText("Category: "+ category);
        PestWeight.setText(weight+" KG");
        PestHeight.setText(height+" M");
        PestCountry.setText("Country: "+country);
        show_Diet.setText(diet);
        ActualTips.setText(tips);
        ActualDealWithThem.setText(ways);
        String url = imageURL;
        Glide.with(context).load(url).apply(option).into(PestImage);
        Double rateScore = 7.0;
        rateScore = rateScore / 2 ;
        float score = rateScore.floatValue();
        rateScoreStar.setRating(score);

        //String[] strings = detailViewModel.getPestInfo().getValue();
        //detailViewModel.getPestInfo().observe(getViewLifecycleOwner(), new Observer<String[]>() {
          //  @Override
          //  public void onChanged(String[] strings) {
               // Log.d("sign_in", "json: " + strings);
                //PestName.setText(strings[1]);
                //PestCategory.setText(strings[5]);
                //PestWeight.setText(strings[3]);
                //PestHeight.setText(strings[2]);
                //PestCountry.setText(strings[4]);
                //show_Diet.setText(strings[6]);
                //ActualDealWithThem.setText(strings[7]);
               // ActualTips.setText(strings[8]);
               // String url = strings[9];
               // Glide.with(context).load(url).apply(option).into(PestImage);

           // }
       // });*/

        return view;
    }
}
