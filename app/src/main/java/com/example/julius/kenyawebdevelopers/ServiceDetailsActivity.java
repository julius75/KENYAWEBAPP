package com.example.julius.kenyawebdevelopers;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

import org.json.JSONArray;

public class ServiceDetailsActivity extends AppCompatActivity {
    ImageView back;
    private JSONArray jsonArray;
    SwipeRefreshLayout swipe_refresh_layout;
    private GridView GetAllAdsListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_details);
        back=findViewById(R.id.back);
        swipe_refresh_layout = findViewById(R.id.swipe_refreshi_layout);
        GetAllAdsListView =findViewById(R.id.GetServicesListView);
        new GetAllPackagesTask().execute(new ApiConnector());
        swipe_refresh_layout.setRefreshing(true);


        swipe_refresh_layout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        new GetAllPackagesTask().execute(new ApiConnector());
                        //mathewadaptersetting();
                    }
                }
        );
        //new Servicesfragment.GetAllAdsTask().execute(new ApiConnector());
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void setListAdapter(JSONArray jsonArray) {
        Log.d("listadapterarray",jsonArray.toString());
        try {
            this.jsonArray = jsonArray;
            if (jsonArray == null) {
                //available.setVisibility(View.VISIBLE);
                swipe_refresh_layout.setVisibility(View.GONE);
            }else {
                this.GetAllAdsListView.setAdapter(new PackagesListViewAdapter(jsonArray, ServiceDetailsActivity.this));
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

            setListAdapter(jsonArray);
        }
    }
}
