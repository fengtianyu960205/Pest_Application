package com.example.pest_application.UI.Home;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.request.RequestOptions;
import com.example.pest_application.R;
import com.example.pest_application.UI.DetailViewModel;
import com.example.pest_application.UI.showAll.ShowAllPestsAdapter;

import java.util.ArrayList;

public class ShowAllPestAdapter extends RecyclerView.Adapter<ShowAllPestAdapter.Holder> implements Filterable {
    private Context context;

    private ArrayList<String> pestList ;
    private ArrayList<String> fullpestList ;
    private Application application;
    //private DetailViewModel detailViewModel;
    public OnItemListener monItemListener;

    private Filter pestFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<String> filterList = new ArrayList<>();
            if (constraint == null && constraint.length() ==0){
                filterList.addAll(fullpestList);
            }
            else{
                String pattern = constraint.toString().trim();
                for (String item : fullpestList){
                     if (item.contains(pattern)){
                         filterList.add(item);
                     }
                }
            }
            FilterResults results = new FilterResults();
            results.values =  filterList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            pestList.clear();
            pestList.addAll((ArrayList)results.values);
            notifyDataSetChanged();
        }
    };

    public ShowAllPestAdapter(ArrayList<String> pestList, Context context,OnItemListener monItemListener){
        this.pestList = pestList;
        this.fullpestList = new ArrayList<String>(pestList);
        this.context = context;
        this.monItemListener = monItemListener;


    }


    @NonNull
    @Override
    public ShowAllPestAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.showpesttext,parent,false);
        ShowAllPestAdapter.Holder viewHolder = new ShowAllPestAdapter.Holder(itemView,monItemListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShowAllPestAdapter.Holder holder, int position) {

            holder.pestNametext.setText(pestList.get(position));
            holder.pestNametext.setText(pestList.get(position));

    }

    @Override
    public int getItemCount() {
        return pestList.size();
    }

    /*public void setPest(ArrayList<String> pests) {

        this.pestList = pests;
        notifyDataSetChanged();
    }*/

    @Override
    public Filter getFilter() {
        return pestFilter;
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView pestNametext;
        public ShowAllPestAdapter.OnItemListener onItemListener;

        public Holder(@NonNull View itemView, ShowAllPestAdapter.OnItemListener onItemListener) {
            super(itemView);
            pestNametext = itemView.findViewById(R.id.pestNametext);
            this.onItemListener =onItemListener ;
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            onItemListener.whenClickpestName(getAdapterPosition());
        }
    }

    public interface OnItemListener{

        void whenClickpestName(int position);
    }
}
