package com.example.pest_application.UI.Report;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.example.pest_application.R;
import com.example.pest_application.UI.Home.SearchPestByStateViewModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ReportFragment extends Fragment {

    private AnyChartView pieChart;
    private TextView welcometoreport,barchart;
    private reportViewmodel reviewmodel ;
    private ArrayList<Integer> number = new ArrayList<>();
    private ArrayList<String> StateName = new ArrayList<>();
    private ArrayList<String[]> info = new ArrayList<>();
    private report2Viewmodel re2ViewModel;
    private ArrayList<Integer> number2 = new ArrayList<>();
    private ArrayList<String> StateName2 = new ArrayList<>();
    private ArrayList<String> category2 = new ArrayList<>();
    private ArrayList<String[]> info2 = new ArrayList<>();
    private ArrayList<String[]> pest = new ArrayList<>();
    private ArrayList<String[]> invasive = new ArrayList<>();
    private ArrayList<String[]> weeds = new ArrayList<>();
    private BarChart bargraph;
    private ProgressBar reportprogressbar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.report, container, false);
        pieChart = view.findViewById(R.id.pieChart);
        welcometoreport = view.findViewById(R.id.welcometoreport);
        reportprogressbar = view.findViewById(R.id.reportprogressbar);
        //barchart = view.findViewById(R.id.barchart);
        //pieChart.setVisibility(View.GONE);
        bargraph = view.findViewById(R.id.bargraph);
        reviewmodel = new ViewModelProvider(this).get(reportViewmodel.class);

        re2ViewModel = new ViewModelProvider(this).get(report2Viewmodel.class);
        reviewmodel.GetStateInfoTa();
        reviewmodel.getStateInfo().observe(getViewLifecycleOwner(), new Observer<ArrayList>() {
            @Override
            public void onChanged(ArrayList arrayList) {
                info = arrayList;
                for (int i = 0; i < info.size(); i++) {
                    Integer a = Integer.parseInt(info.get(i)[0]);
                    number.add(a);
                    StateName.add(info.get(i)[1]);
                }
                setupPieChart();
            }
        });

        re2ViewModel.GetStateInfoTa();
        re2ViewModel.getStateInfo().observe(getViewLifecycleOwner(), new Observer<ArrayList>() {
            @Override
            public void onChanged(ArrayList arrayList) {
                info2 = arrayList;
                for (int i = 0; i < info2.size(); i++) {
                    //Integer a = Integer.parseInt(info2.get(i)[0]);
                   // number2.add(a);
                   // StateName2.add(info.get(i)[1]);
                   // category2.add(info.get(i)[2]);
                    if(i < 8){
                        String[] newStr = new String[2];
                        newStr[0] = info2.get(i)[1];
                        newStr[1] = info2.get(i)[0];
                        invasive.add(newStr);
                        Collections.sort(invasive,new Comparator<String[]>() {
                            public int compare(String[] strings, String[] otherStrings) {
                                return strings[0].compareTo(otherStrings[0]);
                            }
                        });



                    }
                    if(7 < i && i < 16){
                        String[] newStr = new String[2];
                        newStr[0] = info2.get(i)[1];
                        newStr[1] = info2.get(i)[0];
                        pest.add(newStr);
                        Collections.sort(pest,new Comparator<String[]>() {
                            public int compare(String[] strings, String[] otherStrings) {
                                return strings[0].compareTo(otherStrings[0]);
                            }
                        });
                    }
                    if(15 < i && i < 24){
                        String[] newStr = new String[2];
                        newStr[0] = info2.get(i)[1];
                        newStr[1] = info2.get(i)[0];
                        weeds.add(newStr);
                        Collections.sort(weeds,new Comparator<String[]>() {
                            public int compare(String[] strings, String[] otherStrings) {
                                return strings[0].compareTo(otherStrings[0]);
                            }
                        });
                    }
                }
                setupbarGraph();
            }
        });
        return view;
    }

    public void setupPieChart(){
        Pie pie = AnyChart.pie();
        List<DataEntry> dataEntries = new ArrayList<>();
        for (int i = 0; i < StateName.size(); i++ ){
            dataEntries.add(new ValueDataEntry(StateName.get(i),number.get(i)));
        }
        pie.data(dataEntries);
        reportprogressbar.setVisibility(View.INVISIBLE);
        pieChart.setChart(pie);
    }

    public void setupbarGraph(){

        ArrayList<BarEntry> entriesInvasive = new ArrayList<>();
        ArrayList<BarEntry> entriesPest = new ArrayList<>();
        ArrayList<BarEntry> entriesWeeds = new ArrayList<>();

        for (int i = 0; i < 8; i++ ){
            entriesInvasive.add(new BarEntry(i,Integer.parseInt(invasive.get(i)[1])));
            entriesPest.add(new BarEntry(i,Integer.parseInt(pest.get(i)[1])));
            entriesWeeds.add(new BarEntry(i,Integer.parseInt(weeds.get(i)[1])));
        }

        BarDataSet barDataSet1 = new BarDataSet(entriesInvasive,"Invasive");
        barDataSet1.setColor(Color.RED);
        BarDataSet barDataSet2 = new BarDataSet(entriesPest,"Pest");
        barDataSet2.setColor(Color.BLUE);
        BarDataSet barDataSet3 = new BarDataSet(entriesWeeds,"Weeds");
        barDataSet3.setColor(Color.GREEN);
        BarData theData = new BarData(barDataSet1,barDataSet2,barDataSet3);
        bargraph.setData(theData);
        String[] stateNames = new String[]{"ACT","NSW","NT","QLD","SA","TAS","VIC","WA"};
        XAxis xAxis = bargraph.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(stateNames));
        xAxis.setCenterAxisLabels(true );
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);
        xAxis.setGranularityEnabled(true);
        //xAxis.setTextSize(0.44f);

        bargraph.setDragEnabled(true);
        bargraph.setVisibleXRangeMaximum(3);
        //Description description = new Description();
        //description.setText("Month");
        //bargraph.setDescription(description);
        float barSpace = 0.08f;
        float groupSpace = 0.44f;
        theData.setBarWidth(0.10f);
        bargraph.getXAxis().setAxisMinimum(0);
        bargraph.getXAxis().setAxisMaximum(0+bargraph.getBarData().getGroupWidth(barSpace,groupSpace)*8  );
        bargraph.getAxisLeft().setAxisMinimum(0);
        bargraph.groupBars(0,groupSpace,barSpace);
        bargraph.setDoubleTapToZoomEnabled(false);




        bargraph.animateY(2000);
        bargraph.invalidate();
    }

    /*
    @Override
    public void onResume() {

        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener(){
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent){
                if (keyCode == KeyEvent.KEYCODE_BACK && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                    Log.d("sign_in", "json: " + "dsdsdsdsdds");
                    return true;
                }
                return false;
            }
        });

    }*/
}
