package com.example.julius.kenyawebdevelopers;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;


public class PackagesListViewAdapter extends BaseAdapter {
    private JSONArray dataArray;
    private JSONObject jsonObject, orderobject;
    String Image;
    String servid;
    private static LayoutInflater inflater = null;
    private Context context;
    Activity activity;

    public PackagesListViewAdapter(JSONArray jsonArray, Activity a) {
        try {
            this.dataArray = jsonArray;
            activity = a;
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PackagesListViewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        try {
            return this.dataArray.length();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // set up convert view if it is null
        ListCell cell;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.packages_list_view_cell, null);
            cell = new ListCell();
//            cell.title = convertView.findViewById(R.id.title);
            cell.p_name = convertView.findViewById(R.id.p_name);
            cell.p_details= convertView.findViewById(R.id.p_details);
            cell.p_price= convertView.findViewById(R.id.p_price);
          cell.book = convertView.findViewById(R.id.book);
            cell.call_user = convertView.findViewById(R.id.call_user);
            cell.sms_user = convertView.findViewById(R.id.sms_user);
            cell.book.setVisibility(View.VISIBLE);
            convertView.setTag(cell);
        } else {
            cell = (ListCell) convertView.getTag();
        }
        //change the data of cell
        try {
            jsonObject = this.dataArray.getJSONObject(position);
            Log.d("position",""+position);
            //orderobject=this.dataArray.getJSONObject(position);
            servid=jsonObject.getString("id");
            //ImageView book=(ImageView) convertView.findViewById(R.id.book);
            //cell.book.setTag(position,jsonObject);
            cell.book.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("inadapter","I will book now"+servid);
                    Log.d("position clicked",""+position);
                    try {
                        orderobject=dataArray.getJSONObject(position);//(JSONObject) v.getTag(position);
                        servid=orderobject.getString("id");
                        Log.d("secinadapter","I will book now"+servid);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    // (JSONObject) getItem(position);
                    Log.d("inadapterorderobj",orderobject.toString());
                    order(orderobject);

                }
            });
            cell.call_user.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onClick(View v) {
                    try {
                        orderobject=dataArray.getJSONObject(position);
                        servid=orderobject.getString("id");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                        try {
                            //startActivity(callIntent);
                            Uri call = Uri.parse("tel:" +"0718676342");
                            Intent surf = new Intent(Intent.ACTION_DIAL, call);
                            activity.startActivity(surf);
                        } catch (Exception e) {
                            //catch me outside
                            e.printStackTrace();
                        }

                }
            });
            cell.sms_user.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String text="KENYA WEB";
                    String cost="KES";
                    try {
                        orderobject=dataArray.getJSONObject(position);//(JSONObject) v.getTag(position);
                        servid=orderobject.getString("id");
                        text=orderobject.getString("p_name");
                       cost=orderobject.getString("p_currency")+": "+orderobject.getString("p_price");

                        Log.d("secinadapter","I will book now"+servid);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                    smsIntent.setData(Uri.parse("smsto:"));
                    smsIntent.setType("vnd.android-dir/mms-sms");
                    smsIntent.putExtra("address", "0718676342");
                    smsIntent.putExtra("sms_body", "Hi, I want to ask about " + text +": of charges "+cost+" from your Kenya web developers App.");

                    try {
                        activity.startActivity(smsIntent);
                    } catch (Exception e) {
                        //catch me outside
                        e.printStackTrace();
                    }
                }
            });
            cell.p_name.setText(jsonObject.getString("p_name") + " ");
            cell.p_details.setText(jsonObject.getString("p_details") + " ");
            if(jsonObject.getString("p_price").equalsIgnoreCase("0")){
                cell.p_price.setText(jsonObject.getString("p_currency"));
            }else{
                cell.p_price.setText(jsonObject.getString("p_currency")+": "+jsonObject.getString("p_price") + " ");
            }
//            cell.location.setText(jsonObject.getString("county").replace("_", " "));
//            cell.views.setText(jsonObject.getString("ad_views"));
//            cell.title.setText(jsonObject.getString("title"));
           // cell.p_name.setAnimation(AnimationUtils.loadAnimation(getView(position,convertView,parent).getContext() , R.anim.flash_leave_now));
//            cel

//            if (SPrice == 0) {
//                cell.price.setText("Price on Enquiry");
//            } else {
//                String Sprice2 = NumberFormat.getNumberInstance(Locale.US).format(SPrice);
//                cell.price.setText("Kes. " + Sprice2);
//            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return convertView;
    }

    public void order(JSONObject jsonObject){
        //Intent intent=new Intent(context,OrderActivity.class);
//                    context.startActivity(intent);
        Intent intent=new Intent(activity,OrderActivity.class);
        intent.putExtra("orderobject",orderobject.toString());
        activity.startActivity(intent);
//                    finish();
    }

    private class ListCell {
        private TextView p_details;
        private TextView p_price;
        private TextView p_name;
        private Button book, call_user,sms_user;
        //private ImageView service_pic;
    }
}

