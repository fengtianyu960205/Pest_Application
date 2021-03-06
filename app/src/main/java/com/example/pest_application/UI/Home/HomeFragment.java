package com.example.pest_application.UI.Home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

public class HomeFragment extends Fragment implements  ShowAllPestsAdapter.OnItemListener, ShowAllPestAdapter.OnItemListener{

    private SearchView searchView;
    private TextView search_pestName,resultnotfound,mostSearch;
    private Spinner filter_state;
    private Context context;
    private Button searchPest_btn;
    private RecyclerView recyclerView,showPestHint,recyclerMostUserSearch;
    private ArrayList<String[]> pests = new ArrayList<>();
    private ArrayList<String> pestsName = new ArrayList<>();
    private ShowAllPestsAdapter adpater,adapter2;
    private ShowAllPestAdapter adpater1;
    private SearchPestByStateViewModel searchPestByStateViewModel;
    private SearchPestByCategoryNameViewModle searchPestByCategoryNameViewModel;
    private String state = "State";
    private EditText search_Name;
    private int flag;
    private String choosedname = "";
    private WebView privacyPolicy;
    private Button disagree_btn;
    private Button  agree_btn;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        pestsName.add("rabbit");
        pestsName.add("fox");
        pestsName.add("fish");
        pestsName.add("deer");
        pestsName.add("plant");
        pestsName.add("Sydney");
        pestsName.add("Melbourne");
        pestsName.add("Adelaide");
        pestsName.add("Canberra");

        String[] rabbit = new String[]{"13",
                "Feral Pig",
                "55-120cm",
                "50-100kg",
                "United Kingdom/United States/South America/Australia",
                "Pest",
                "You need to prepare clean towel, sterile bandage and antibiotic ointment: first place a clean towel over the injury to stop bleeding, wash the bite carefully with soap and water. Secondly, apply a sterile bandage to the woud and finally apply antibiotic ointment to prevent infection.",
                "DO NOT approach or attempt to feed the animal. Keep a safe distance and DO NOT corner or provoke the animal. Be calm and move slowly away from the animal.",
                "Pigs are highly intelligent and have been shown to communicate as many as 20 different vocalisations to express their thoughts!",
                "https://i.loli.net/2020/09/11/hKdWe6QpXcfEGN9.png",
                "Feral pig brings substantial damage to Australian environment. They carry disease of leptospirosis, which caused more than $106 million economic losses.",
                "5"};

        String[] fox = new String[]{"2",
                "European Fox",
                "35-50cm",
                "2.2-14kg",
                "Australia/Sardinia/Italy",
                "Invasive species",
                "Prey on over 300 animal species. Primarily voles, mice, ground squirrels, hamsters, gerbils, woodchucks and pocket gophers.",
                "Uses creosote, disel oil or ammonia to block access. Other effective control methods, including trapping, shooting and den fumingation.",
                "Did you know that foxes have soft whiskers on their wirsts that help with their pouncing aim.",
                "https://i.loli.net/2020/09/11/vceKI8xunEitQjh.png",
                "They prey on native wildlifes and are implicated with the decline and extinction of many small to medium-sized mamals.",
                "3.5"};
        pests.add(rabbit);
        pests.add(fox);




        View view = inflater.inflate(R.layout.home_fragment, container, false);
        filter_state =view.findViewById(R.id.filter_state);
        searchView = view.findViewById(R.id.searchView);
        searchPest_btn = view.findViewById(R.id.searchPest_btn);
        resultnotfound =  view.findViewById(R.id.resultnotfound);
        //search_Name = view.findViewById(R.id.search_pestName);
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
        //recyclerView.setVisibility(View.GONE);

        showPestHint = view.findViewById(R.id.showPestHint);
        adpater1 = new ShowAllPestAdapter(pestsName,context,this);
        showPestHint.setAdapter(adpater1);
        showPestHint.setLayoutManager(new LinearLayoutManager(context));
        showPestHint.setHasFixedSize(true);
        showPestHint.setVisibility(View.GONE);

        mostSearch = view.findViewById(R.id.mostSearch);



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                showPestHint.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                mostSearch.setVisibility(View.GONE);
                adpater1.getFilter().filter(newText);

                return false;
            }
        });


        searchPest_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(searchView.getQuery().toString().trim().length() == 0 && !state.equals("State") ){
                    flag = 1;
                }
                else if (searchView.getQuery().toString().trim().length() !=0 ){
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
                                showPestHint.setVisibility(View.GONE);
                                mostSearch.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);
                                adpater.setPest(pests);
                            } else {
                                //Log.d("sign_in", "json: " +state);
                                pests = new ArrayList<>();
                                adpater.setPest(pests);
                                showPestHint.setVisibility(View.GONE);
                                mostSearch.setVisibility(View.GONE);
                                resultnotfound.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                }
                else if(flag == 2){

                    searchPestByCategoryNameViewModel.GetPestInfoTa(searchView.getQuery().toString().trim()+","+state);
                    searchPestByCategoryNameViewModel.getPestInfo().observe(getViewLifecycleOwner(), new Observer<ArrayList>() {
                        //Log.d("sign_in", "json: " +flag);
                        @Override
                        public void onChanged(ArrayList arrayList) {
                            pests = arrayList;
                            if (pests.size() != 0) {
                                Log.d("sign_in", "jssdsdsdsdson: " +pests.size());
                                resultnotfound.setVisibility(View.GONE);
                                showPestHint.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);
                                adpater.setPest(pests);
                            } else {
                                //Log.d("sign_in", "json: " +4);
                                pests = new ArrayList<>();
                                adpater.setPest(pests);
                                showPestHint.setVisibility(View.GONE);
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

        SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPref",context.MODE_PRIVATE);
        int password = sharedPreferences.getInt("password", 0);
        if(password==0){
            passwordDialog();
        }

        int homefragmenttag = sharedPreferences.getInt("homeFragment", 0);
        if(password!=0 && (homefragmenttag == 2 || homefragmenttag==0)){
            getprivacydialog();
        }




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

    @Override
    public void whenClickpestName(int position) {
        searchView.setQuery(pestsName.get(position),false);
        //choosedname = pestsName.get(position);
    }

    public void getprivacydialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        //builder.setTitle("Location");


        View viewInflated = LayoutInflater.from(getContext()).inflate(R.layout.privacy, (ViewGroup) getView(), false);

       // inputAddress = (EditText) viewInflated.findViewById(R.id.inputAddress);
       // inputState = (EditText) viewInflated.findViewById(R.id.inputState);
       // inputCity= (EditText) viewInflated.findViewById(R.id.inputCity);

        privacyPolicy = (WebView) viewInflated.findViewById(R.id.privacyPolicy);
        privacyPolicy.getSettings().setJavaScriptEnabled(true);
        privacyPolicy.setWebViewClient(new WebViewClient());
        privacyPolicy.loadUrl("file:///android_asset/"+"privacy.html");
        //disagree_btn =  viewInflated.findViewById(R.id.disagree_btn);
        //agree_btn =  viewInflated.findViewById(R.id.agree_btn);
        //final EditText input = new EditText(context);

        //input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        // builder.setView(input);
        builder.setView(viewInflated);

        builder.setPositiveButton("agree", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPref",context.MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.putInt("homeFragment",1);
                myEdit.commit();
                dialog.dismiss();


            }
        });
        builder.setNegativeButton("disagree", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPref",context.MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.putInt("homeFragment",2);
                dialog.cancel();
                getActivity().finish();
            }
        });

        AlertDialog alert = builder.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();

    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    public void passwordDialog(){


        final EditText inputEditTextField= new EditText(getContext());
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle("Password");
        builder.setMessage("Input Password");
        builder.setView(inputEditTextField);
        builder.setPositiveButton("OK",null);

        final AlertDialog alert = builder.create();
        alert.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button positiveButton = alert.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String editTextInput = inputEditTextField.getText().toString();
                        if (editTextInput.equals("ME10")) {
                            SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPref", context.MODE_PRIVATE);
                            SharedPreferences.Editor myEdit = sharedPreferences.edit();
                            myEdit.putInt("password", 10);
                            myEdit.commit();
                            alert.dismiss();
                            //SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPref",context.MODE_PRIVATE);
                            int number = sharedPreferences.getInt("homeFragment", 0);
                            if (number == 0) {
                                getprivacydialog();
                            }
                        }
                    }
                });
            }
        });


        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }

}
