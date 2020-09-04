package com.example.pest_application.UI.showAll;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.pest_application.Pest;
import com.example.pest_application.R;
import com.example.pest_application.UI.DetaiPestlInformation;
import com.example.pest_application.UI.DetailViewModel;

import java.util.ArrayList;

public class ShowAllPestsAdapter extends RecyclerView.Adapter<ShowAllPestsAdapter.Holder> {
    private Context context;
    RequestOptions option;
    private ArrayList<String[]> pestList = new ArrayList<>();
    private Application application;
    private DetailViewModel detailViewModel;
    public OnItemListener monItemListener;

    public ShowAllPestsAdapter(ArrayList<String[]> pestList, Context context,OnItemListener monItemListener){
        this.pestList = pestList;
        this.context = context;
        this.monItemListener = monItemListener;
        option = new RequestOptions().centerCrop().placeholder(R.drawable.loading).error(R.drawable.loading);

    }

    @NonNull
    @Override
    public ShowAllPestsAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.show_allpest_adapter,parent,false);
        Holder viewHolder = new Holder(itemView,monItemListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShowAllPestsAdapter.Holder holder, int position) {
        if(pestList.size() !=0) {
            String[] currentPest = pestList.get(position);
            holder.PestName.setText(currentPest[1]);
            holder.category.setText(currentPest[5]);
            String url = currentPest[9];
            Glide.with(context).load(url).apply(option).into(holder.Image1);
        }


    }

    @Override
    public int getItemCount() {

        return pestList.size();


    }

    public void setPest(ArrayList<String[]> pests) {

        this.pestList = pests;
        notifyDataSetChanged();
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView Image1;
        private TextView PestName, category;
        public OnItemListener onItemListener;



        public Holder(View itemView,OnItemListener onItemListener){
            super(itemView);
            Image1 = itemView.findViewById(R.id.Image1);
            PestName = itemView.findViewById(R.id.PestName);
            category = itemView .findViewById(R.id.Category);
            this.onItemListener =onItemListener ;
            itemView.setOnClickListener(this);

            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     detailViewModel = new ViewModelProvider((FragmentActivity)context).get(DetailViewModel.class);
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

                    detailViewModel.setPestInfo(pest);
                    DetaiPestlInformation nextFrag= new DetaiPestlInformation();
                    ((FragmentActivity)context).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content_frame, nextFrag, "findThisFragment")
                            .addToBackStack(null)
                            .commit();
                }
            });*/
        }

        @Override
        public void onClick(View v) {
            onItemListener.whenClickIt(getAdapterPosition());
        }
    }
    public interface OnItemListener{

        void whenClickIt(int position);
    }

}
