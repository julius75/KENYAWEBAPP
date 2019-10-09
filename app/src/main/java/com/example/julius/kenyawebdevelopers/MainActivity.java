package com.example.julius.kenyawebdevelopers;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;

import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.VIBRATE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     */
    //private SectionsPagerAdapter mSectionsPagerAdapter;

    ImageView  menu_nav;
    private ViewPager mViewPager;
    private TabLayout tabLayout;
   DrawerLayout drawer;    
    private ViewPager viewPager;
    private TextView contactus;
    TextView fullnames, create_txt, nav_contact_us;
    final ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
    ArrayList<String> listItems=new ArrayList<>();
    ArrayAdapter<String> adapter2;
    ArrayList<String>list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        menu_nav = findViewById(R.id.menu_nav);
        // Set up the ViewPager with the sections adapter

        viewPager = findViewById(R.id.viewpager);
        drawer = findViewById(R.id.drawer_layout);
        //viewPager.setAdapter(mSectionsPagerAdapter);
        setupViewPager(viewPager);
        tabLayout = findViewById(R.id.servicestabs);
        tabLayout.setupWithViewPager(viewPager);
        contactus=findViewById(R.id.contactus);
        new GetservicepackageTask().execute(new ApiConnector());
        NavigationView navigationView = findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        //nav_sign_out_button=findViewById(R.id.nav_sign_out_button );
        navigationView.setNavigationItemSelectedListener(this);
        Menu nav_Menu = navigationView.getMenu();


        contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(MainActivity.this,ContactUsActivity.class);
                startActivity(intent);
            }
        });
        menu_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        //clearPager();
//        maintabLayout.setVisibility(View.GONE);
//        secondtablayout.setVisibility(View.GONE);


        if (!CheckingPermissionIsEnabledOrNot()) {
            //Calling method to enable permission.
            goToSplash();
        }
//        Servicesfragment fragments = new Servicesfragment();
//        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.Content, fragments);
//        fragmentTransaction.commit();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d("selecttag", "Tabis"+tab.getPosition());
                //tabLayout.getTabAt(tab.getPosition()).select();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
//                Log.d("reselectselecttaglayout", "Tabis"+tab.getPosition());
//                viewPager.setCurrentItem(tab.getPosition());
//                tabLayout.getTabAt(tab.getPosition()).select();
//                tabLayout.setupWithViewPager(viewPager);
//
//                Servicesfragment fragments = new Servicesfragment();
//                fragments.setId(tab.getPosition());
//                android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.Content, fragments);
//                fragmentTransaction.commit();


            }
        });

//        tabLayout.addOnTabSelectedListener(new  TabLayout.OnTabSelectedListener(){
//
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                // Do stuff
//                Log.d("selecttaglayout", "Tabis"+tab.getPosition());
//                tab.getPosition();
//                viewPager.setCurrentItem(tab.getPosition());
//                tabLayout.getTabAt(tab.getPosition()).select();
//                //tabLayout.setupWithViewPager(viewPager);
//
//                //ft.replace(R.id.fragment_container, fragment);
//
//                //clearPager();
////                Servicesfragment fragments = new Servicesfragment();
////                android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
////                fragmentTransaction.replace(R.id.Content, fragments);
////                fragmentTransaction.commit();
//
//
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//                Servicesfragment fragments = new Servicesfragment();
//                android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.Content, fragments);
//                fragmentTransaction.commit();
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//                tab.getPosition();
//                viewPager.setCurrentItem(tab.getPosition());
//                tabLayout.getTabAt(tab.getPosition()).select();
//                //tabLayout.setupWithViewPager(viewPager);
//
//            }
//        }
//
//        );
    }

//    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected() {
//        return onNavigationItemSelected();
//    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(id == R.id.nav_contact_us){
            Intent intent=new Intent(MainActivity.this,ContactUsActivity.class);
            startActivity(intent);
        }
        else  {
            Servicesfragment fragments = new Servicesfragment();
            fragments.setId("home");
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.Content, fragments);
            fragmentTransaction.commit();
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

   
//    public JsonArrayRequest(int method, String url, JSONObject jsonRequest,
//                            Response.Listener<JSONArray> listener, ErrorListener errorListener) {
//        super(method, url, (jsonRequest == null) ? null : jsonRequest.toString(),
//                listener, errorListener);
//    }
    private void setupViewPager(final ViewPager viewPager) {



        String url = Constants.GET_SERVICES;


        StringRequest stringRequest = new  StringRequest(Request.Method.POST, url,
                new Response.Listener< String>(){
                    @Override
                    public void onResponse(String s) {
                        try {
                            ArrayList<String> listItems=new ArrayList<>();
                            ArrayAdapter<String> adapter2;
                            ArrayList<String>list=new ArrayList<>();
                           // Log.d("my json response",s);
//                            JSONObject jsonObject = new JSONObject(s);
//                            JSONArray result = jsonObject.getJSONArray("results");
                            Servicesfragment fragobj1 = new Servicesfragment();
                            fragobj1.setId("home");
                            adapter.addFrag( fragobj1, "ALL SERVICES");
                            //adapter.addFrag( new Servicesfragment(), "packages");
                            String s2=s;
                            JSONArray result =new JSONArray(s);
                            for(int i=0;i<=result.length()-1;i++){
                                JSONObject newsdata = result.getJSONObject(i);
                                String servicename = newsdata.getString("s_name");
                                String ident=newsdata.getString("id");
                                Servicesfragment fragobj = new Servicesfragment();
                                fragobj.setId(ident);
                                adapter.addFrag(fragobj, servicename);
                                list.add(servicename);
                            }
                            viewPager.setAdapter(adapter);
                            listItems.addAll(list);
                            //adapter.notifyDataSetChanged();
                            //Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        //Toast.makeText(MainActivity.this, "Connection Error"+volleyError, Toast.LENGTH_SHORT).show();
                        // clearPager();
                        setupViewPager( viewPager);

                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Creating parameters
                Map<String, String> params = new Hashtable<String, String>();
                final String tcounty="Kiambu";
                //Adding parameters
                params.put("county", tcounty);
                //params.put("REQUEST_METHOD", "POST");
                //returning parameters
                return params;
            }
        };
        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        //Adding request to the queue
        requestQueue.add(stringRequest);


        //END OF UNCOMMENT CODE FOR VEHICLE TABS


    }

    /* adapter class for creating the views*/
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            Log.d("fragment position","pstn"+position);
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            Log.d("fragment list size","size"+mFragmentList.size());
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
    private void goToSplash() {
        Intent intent = new Intent(MainActivity.this, SplashActivity.class);
        startActivity(intent);
        finish();
    }
    public boolean CheckingPermissionIsEnabledOrNot() {
        int FirstPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), INTERNET);
        int SecondPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_NETWORK_STATE);
        int ThirdPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int ForthPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        int FifthPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_COARSE_LOCATION);
        int SixthPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
        int SeventhPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), VIBRATE);
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
    public void showreturn(JSONArray jsonArray){
     //Log.d("return of post",jsonArray.toString());
    }
    @SuppressLint("StaticFieldLeak")
    private class GetservicepackageTask extends AsyncTask<ApiConnector, Long, JSONArray> {
        @Override
        protected JSONArray doInBackground(ApiConnector... params) {
            try {
                //swipe_refresh_layout.setVisibility(View.VISIBLE);
                // it is executed on Background thread
                return params[0].GetServicepackages("1");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {

            showreturn(jsonArray);
        }
    }
}
