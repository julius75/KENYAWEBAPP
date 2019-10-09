package com.example.julius.kenyawebdevelopers;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.VIBRATE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class Servicesfragment extends Fragment {
    private GridView GetAllAdsListView;
    private JSONArray jsonArray;
    SwipeRefreshLayout swipe_refresh_layout,swipe_refresh_layout2;
    ImageView available;
    private String id;
    private Button book;
    private ListView GetAllAds2ListView;

    public Servicesfragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String serviceid=this.id;
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_services, container, false);
        GetAllAdsListView = view.findViewById(R.id.GetAdsListView);
        GetAllAds2ListView =view.findViewById(R.id.GetServicesListView);
        Log.d("serviceids",serviceid+" ");
        if (!CheckingPermissionIsEnabledOrNot()) {
            //Calling method to enable permission.
            goToSplash();
        }
        if(serviceid!=null && serviceid!="home"){
            new GetSubservicesTask().execute(new ApiConnector());
        }else if(serviceid=="home"){
            new GetAllservicesTask().execute(new ApiConnector());
        }
        else{
            new GetAllPackagesTask().execute(new ApiConnector());
        }
//        if(passedlevel isfinal display the second layout){}

       //mathewadaptersetting();

        available = view.findViewById(R.id.available);
        swipe_refresh_layout = view.findViewById(R.id.swipe_refreshi_layout);
//        swipe_refresh_layout2 = view.findViewById(R.id.swipe_refreshi_layout2);

        swipe_refresh_layout.setRefreshing(true);

        swipe_refresh_layout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        if(id!=null && id!="home"){
                            new GetSubservicesTask().execute(new ApiConnector());
                        }else if(id=="home"){
                            new GetAllservicesTask().execute(new ApiConnector());
                        }
                        else{
                            new GetAllPackagesTask().execute(new ApiConnector());
                        }
                        //mathewadaptersetting();
                    }
                }
        );
//        swipe_refresh_layout2.setRefreshing(true);
//
//        swipe_refresh_layout2.setOnRefreshListener(
//                new SwipeRefreshLayout.OnRefreshListener() {
//                    @Override
//                    public void onRefresh() {
//                        if(id!=null){
//                            new GetSubservicesTask().execute(new ApiConnector());
//                        }else{
//                            new GetAllPackagesTask().execute(new ApiConnector());
//                            //new GetAllservicesTask().execute(new ApiConnector());
//                        }
//                        //mathewadaptersetting();
//                    }
//                }
//        );


        GetAllAdsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                JSONObject itemClicked = null;
                //book=GetAllAdsListView.getChildAt(position).findViewById(R.id.book);
//                Log.d("BOOK isible",book.getVisibility()+"yes0");
//                if(book.getVisibility() == View.VISIBLE) {
//                    Log.d("BOOKING","I WILL BOOK THIS");
//                }else{
                    //book=GetAllAdsListView.findViewById(R.id.book);
                    try {
                        itemClicked = jsonArray.getJSONObject(position);
                        Log.d("BOOKING","cant BOOK THIS"+itemClicked.toString());
                        //Intent showDetails = new Intent(getActivity().getApplicationContext(),ServiceDetailsActivity.class);
//                    showDetails.putExtra("item", itemClicked.getInt("id"));
//                    startActivity(showDetails);
                        String item= itemClicked.getString("id");
                        new GetSpecificPackagesTask(item).execute(new ApiConnector());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
//                }



            }
        });
//        if(book.getVisibility() == View.VISIBLE) {
//            // Visible
////            book.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View view) {
////
////                }
////            });
//            GetAllAdsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    JSONObject itemClicked = null;
//                    try {
//                        itemClicked = jsonArray.getJSONObject(position);
//                        //Intent showDetails = new Intent(getActivity().getApplicationContext(),ServiceDetailsActivity.class);
////                    showDetails.putExtra("item", itemClicked.getInt("id"));
////                    startActivity(showDetails);
//                        Log.d("BOOKING","I WILL BOOK THIS"+itemClicked.toString());
//                        String item= itemClicked.getString("id");
//                        //new GetSpecificPackagesTask(item).execute(new ApiConnector());
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//
//                }
//            });
//        } else {
//            // Invisible
//        }
        return view;
    }

    public void setListAdapter(JSONArray jsonArray) {
        //Log.d("listadapterarray",jsonArray.toString());
        try {
            this.jsonArray = jsonArray;
             if (jsonArray == null) {
                available.setVisibility(View.VISIBLE);
                swipe_refresh_layout.setVisibility(View.GONE);
            }else {
                 if(jsonArray.length()<2){
                     GetAllAdsListView.setNumColumns(1);
                 }
                 else{
                     GetAllAdsListView.setNumColumns(2);
                     }
                 JSONObject jsonObject = jsonArray.getJSONObject(0);
                 Log.d("jsonobjectTapped",jsonObject.toString());
                     if(jsonObject.has("s_details")){
                         String p_details= jsonObject.getString("s_details")+"WOOW";
                         String p_name= jsonObject.getString("s_name");
                         String p_currency="KES";
                         float p_price=00;
                         jsonArray.getJSONObject(0).put("p_name",p_name);
                         jsonArray.getJSONObject(0).put("p_details",p_details);
                         jsonArray.getJSONObject(0).put("p_price",p_price);
                         jsonArray.getJSONObject(0).put("p_currency",p_currency);
                         this.GetAllAdsListView.setAdapter(new PackagesListViewAdapter(jsonArray, getActivity()));
//                         book=this.GetAllAdsListView.findViewById(R.id.book);
//                         book.setVisibility(View.VISIBLE);
                     }
                     else{
                         this.GetAllAdsListView.setAdapter(new GetServicesListViewAdapter(jsonArray, getActivity()));
//                         book=this.GetAllAdsListView.findViewById(R.id.book);
//                         book.setVisibility(View.VISIBLE);
                     }

             }
            if (swipe_refresh_layout.isRefreshing()) {
                swipe_refresh_layout.setRefreshing(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //sample adapter testing
    public void mathewadaptersetting(){
        JSONObject jsonObj1=null;
        JSONObject jsonObj2=null;
        JSONArray array=new JSONArray();
        JSONArray array2=new JSONArray();

        jsonObj1=new JSONObject();
        jsonObj2=new JSONObject();


        try {
            array.put(new JSONObject().put("s_name", "WEB DESIGN").put("s_image","MOBILE APPS DEV."))
                    .put(new JSONObject().put("s_name", "MOBILE APPS DEV.").put("s_image","MOBILE APPS DEV."))
                    .put(new JSONObject().put("s_name", "MPESA INTEGRATION").put("s_image","MOBILE APPS DEV."))
                    .put(new JSONObject().put("s_name", "BULK SMS INTEGRATION").put("s_image","MOBILE APPS DEV."))
                    .put(new JSONObject().put("s_namee", "DATA ANALYSIS").put("s_image","MOBILE APPS DEV."))
                    .put(new JSONObject().put("s_name", "DOMAIN HOSTING").put("s_image","MOBILE APPS DEV."))
                    .put(new JSONObject().put("s_name", "MOBILE APPS DEV.").put("s_image","MOBILE APPS DEV."))
                    .put(new JSONObject().put("s_name", "MPESA INTEGRATION").put("s_image","MOBILE APPS DEV."))
                    .put(new JSONObject().put("s_name", "BULK SMS INTEGRATION").put("s_image","MOBILE APPS DEV."))
                    .put(new JSONObject().put("s_namee", "DATA ANALYSIS").put("s_image","MOBILE APPS DEV.")
                    );
            jsonObj1.put("employees", array);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Log.d("array",array.toString());

//        Response response = null;
//        response = Response.status(Status.OK).entity(jsonObj1.toString()).build();
//        return response;
        setListAdapter(array);
    }

    public void setId(String ident) {
        this.id=ident;
    }
    private void goToSplash() {
        Intent intent = new Intent(getActivity(), SplashActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
    public boolean CheckingPermissionIsEnabledOrNot() {
        int FirstPermissionResult = ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), INTERNET);
        int SecondPermissionResult = ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), ACCESS_NETWORK_STATE);
        int ThirdPermissionResult = ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int ForthPermissionResult = ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), READ_EXTERNAL_STORAGE);
        int FifthPermissionResult = ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), ACCESS_COARSE_LOCATION);
        int SixthPermissionResult = ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), ACCESS_FINE_LOCATION);
        int SeventhPermissionResult = ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), VIBRATE);
//        int EighthPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), CALL_PHONE);
//        int NinethPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), SEND_SMS);

        return FirstPermissionResult == PackageManager.PERMISSION_GRANTED &&
                SecondPermissionResult == PackageManager.PERMISSION_GRANTED &&
                ThirdPermissionResult == PackageManager.PERMISSION_GRANTED &&
                ForthPermissionResult == PackageManager.PERMISSION_GRANTED &&
                FifthPermissionResult == PackageManager.PERMISSION_GRANTED &&
                SixthPermissionResult == PackageManager.PERMISSION_GRANTED &&
                SeventhPermissionResult == PackageManager.PERMISSION_GRANTED;
//                EighthPermissionResult == PackageManager.PERMISSION_GRANTED &&
//                NinethPermissionResult == PackageManager.PERMISSION_GRANTED;
    }

    @SuppressLint("StaticFieldLeak")
    private class GetAllservicesTask extends AsyncTask<ApiConnector, Long, JSONArray> {
        @Override
        protected JSONArray doInBackground(ApiConnector... params) {
            try {
                swipe_refresh_layout.setVisibility(View.VISIBLE);
                // it is executed on Background thread
                return params[0].Get2services();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {

            setListAdapter(jsonArray);
        }
    }
    @SuppressLint("StaticFieldLeak")
    private class GetSubservicesTask extends AsyncTask<ApiConnector, Long, JSONArray> {
        @Override
        protected JSONArray doInBackground(ApiConnector... params) {
            try {
                swipe_refresh_layout.setVisibility(View.VISIBLE);
                // it is executed on Background thread
                return params[0].GetsubService(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            if (!CheckingPermissionIsEnabledOrNot()) {
                //Calling method to enable permission.
                goToSplash();
            } else {
            setListAdapter(jsonArray);
            }
        }
    }
    public void setListAdapter2(JSONArray jsonArray) {
        //Log.d("listadapterarray",jsonArray.toString());
        try {
            this.jsonArray = jsonArray;
            if (jsonArray == null) {
                //available.setVisibility(View.VISIBLE);
                swipe_refresh_layout.setVisibility(View.GONE);
            }else {
                if(jsonArray.length()<2){
                    GetAllAdsListView.setNumColumns(1);
                }
                else{
                    GetAllAdsListView.setNumColumns(2);
                }
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                Log.d("jsonobjectTapped33",jsonObject.toString());
                if(jsonObject.has("s_details")){
                    String p_details= jsonObject.getString("s_details");
                    String p_name= jsonObject.getString("s_name");
                    String p_currency="KES";
                    float p_price=00;
                    jsonArray.getJSONObject(0).put("p_name",p_name);
                    jsonArray.getJSONObject(0).put("p_details",p_details);
                    jsonArray.getJSONObject(0).put("p_price",p_price);
                    jsonArray.getJSONObject(0).put("p_currency",p_currency);
                    //this.GetAllAdsListView.setAdapter(new PackagesListViewAdapter(jsonArray, getActivity()));
                }
                else{
                    //this.GetAllAdsListView.setAdapter(new GetServicesListViewAdapter(jsonArray, getActivity()));
                }
//                book=this.GetAllAdsListView.findViewById(R.id.book);
//                book.setVisibility(View.VISIBLE);
                this.GetAllAdsListView.setAdapter(new PackagesListViewAdapter(jsonArray, getActivity()));
            }
            if (swipe_refresh_layout.isRefreshing()) {
                swipe_refresh_layout.setRefreshing(false);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class GetAllPackagesTask extends AsyncTask<ApiConnector, Long, JSONArray> {
        @Override
        protected JSONArray doInBackground(ApiConnector... params) {
            try {
                swipe_refresh_layout.setVisibility(View.VISIBLE);
                // it is executed on Background thread
                //Getpackages(String servicecode);
                return params[0].Getpackages();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            if (!CheckingPermissionIsEnabledOrNot()) {
                //Calling method to enable permission.
                goToSplash();
            } else {
                setListAdapter2(jsonArray);
            }
            //setListAdapter2(jsonArray);
        }
    }
    @SuppressLint("StaticFieldLeak")
    private class GetSpecificPackagesTask extends AsyncTask<ApiConnector, Long, JSONArray> {
        String item;
        public GetSpecificPackagesTask(String item2) {
            super();
            // do stuff
            this.item=item2;
                   }
        @Override
        protected JSONArray doInBackground(ApiConnector... params) {
            try {
                swipe_refresh_layout.setVisibility(View.VISIBLE);
                // it is executed on Background thread
                //Getpackages(String servicecode);
                Log.d("identitysentto",item);
                return params[0].GetServicepackages(item);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            if (!CheckingPermissionIsEnabledOrNot()) {
                //Calling method to enable permission.
                goToSplash();
            } else {
                setListAdapter2(jsonArray);
            }
//            setListAdapter2(jsonArray);
        }
    }
}
