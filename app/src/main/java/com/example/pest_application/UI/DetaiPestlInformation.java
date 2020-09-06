package com.example.pest_application.UI;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.pest_application.R;
import com.example.pest_application.UI.showAll.MapsActivity_pest;
import com.example.pest_application.UI.showAll.ShowAllPestsViewModel;

public class DetaiPestlInformation extends Fragment {
    private TextView PestName,PestCategory,PestWeight,PestHeight,PestCountry,Diet,show_Diet,DealWithThem,ActualDealWithThem,Tips,ActualTips,Score,Threat,show_Threat;
    private ImageView PestImage;
    private DetailViewModel detailViewModel;
    private Context context;
    private RatingBar rateScoreStar;
    private Button showLocation_btn,addLocation_btn;
    private int idnum;
    private EditText inputAddress,inputState,inputCity;
    private String strinputAddress,strinputState,strinputCity,pestID;
    private AddPestLocationViewModel addPestLocationViewModel;
    private addtolocationtoDB addtoDBViewModel;
    RequestOptions option =  new RequestOptions().centerCrop().placeholder(R.drawable.loading).error(R.drawable.loading);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.movieview_fragment, container, false);
        addPestLocationViewModel =  new ViewModelProvider(this).get(AddPestLocationViewModel.class);
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
        showLocation_btn = view.findViewById(R.id.showLocation_btn);
        Threat =  view.findViewById(R.id.Threat);
        show_Threat = view.findViewById(R.id.show_Threat);
        addLocation_btn = view.findViewById(R.id.addLocation_btn);
        detailViewModel = new ViewModelProvider(this).get(DetailViewModel.class);
        addtoDBViewModel = new ViewModelProvider(this).get(addtolocationtoDB.class);
        pestID = getArguments().getString("id");
        String name = getArguments().getString("pestName");
        String category = getArguments().getString("PestCategory");
        String height = getArguments().getString("height");
        String weight = getArguments().getString("weight");
        String country = getArguments().getString("country");
        String ways = getArguments().getString("ways");
        String diet = getArguments().getString("diet");
        String tips = getArguments().getString("tips");
        String imageURL = getArguments().getString("imageURL");
        String threat = getArguments().getString("threat");
        String scoress = getArguments().getString("score");
        idnum = Integer.parseInt(getArguments().getString("id"));
        PestName.setText(name);
        PestCategory.setText("Category: "+ category);
        if(!category.equals("Weeds")){
            PestWeight.setText("Weight: "+ weight);
        }
        else{
            PestWeight.setVisibility(View.INVISIBLE);
        }
        PestHeight.setText("Height: "+ height);
        PestCountry.setText("Region: "+country);
        show_Diet.setText(diet);
        ActualTips.setText(tips);
        ActualDealWithThem.setText(ways);
        show_Threat.setText(threat);
        String url = imageURL;
        Glide.with(context).load(url).apply(option).into(PestImage);
        Double rateScore = Double.parseDouble(scoress);
        float score = rateScore.floatValue();
        rateScoreStar.setRating(score);
        showLocation_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, MapsActivity_pest.class);
                intent.putExtra("Idnum", idnum);
                context.startActivity(intent);
            }
        });

        addLocation_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Location");
                //final Dialog dialog = new Dialog(getActivity());
                //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                //dialog.setCancelable(false);
                //dialog.setContentView(R.layout.addpestaddress);
                View viewInflated = LayoutInflater.from(getContext()).inflate(R.layout.addpestaddress, (ViewGroup) getView(), false);

                inputAddress = (EditText) viewInflated.findViewById(R.id.inputAddress);
                inputState = (EditText) viewInflated.findViewById(R.id.inputState);
                inputCity= (EditText) viewInflated.findViewById(R.id.inputCity);

                //final EditText input = new EditText(context);

                //input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
               // builder.setView(input);
                builder.setView(viewInflated);

                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        strinputAddress = inputAddress.getText().toString().trim();
                        strinputState = inputState.getText().toString().trim();
                        strinputCity = inputCity.getText().toString().trim();

                        addPestLocationViewModel.getPestLocationTask(strinputAddress+" "+strinputCity+" "+strinputState);
                        addPestLocationViewModel.getLiveDatapestLngLog().observe(getViewLifecycleOwner(), new Observer<Double[]>() {
                            @Override
                            public void onChanged(Double[] doubles) {
                                //Log.d("sign_up", "json: " + doubles[0]);
                                if(doubles[0] != 0.0 && doubles[1] != 0.0 ){
                                    Toast toast = Toast.makeText(context, "add location successfully ", Toast.LENGTH_SHORT);
                                    toast.show();
                                    String location = doubles[1].toString()+","+doubles[0].toString()+","+strinputCity+","+strinputState+","+pestID;
                                    addtoDBViewModel.addTask(location);
                                }
                                else{
                                    Toast toast = Toast.makeText(context, "can not find this location ", Toast.LENGTH_SHORT);
                                    toast.show();

                                }
                            }
                        });



                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
               // dialog.show();
            }
        });



        return view;
    }

}
