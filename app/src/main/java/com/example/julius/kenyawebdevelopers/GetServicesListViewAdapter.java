package com.example.julius.kenyawebdevelopers;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

public class GetServicesListViewAdapter extends BaseAdapter {
    private JSONArray dataArray;
    String Image;
    private static LayoutInflater inflater = null;
    private Context context;
    Activity activity;

    public GetServicesListViewAdapter(JSONArray jsonArray, Activity a) {
        try {
            this.dataArray = jsonArray;
            activity = a;
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public GetServicesListViewAdapter(Context context) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        // set up convert view if it is null
        ListCell cell;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.get_all_services_list_view_cell, null);
            cell = new ListCell();
//            cell.title = convertView.findViewById(R.id.title);
            cell.service_pic = convertView.findViewById(R.id.service_pic);
            cell.service= convertView.findViewById(R.id.service);
//            cell.book = convertView.findViewById(R.id.book);
//            cell.book.setVisibility(View.GONE);

            convertView.setTag(cell);
        } else {
            cell = (ListCell) convertView.getTag();
        }

        //change the data of cell
        try {
            JSONObject jsonObject = this.dataArray.getJSONObject(position);


            Image = jsonObject.getString("s_image");
            Log.d("imageforservice","www.oro.co.ke/modern/"+ Image);


            Glide
                    .with(activity.getApplicationContext())
                    .load("https://www.oro.co.ke/modern/"+ Image)
                    .thumbnail(0.1f)
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.logo)
                    .crossFade()
                    .into(cell.service_pic);

            cell.service.setText(jsonObject.getString("s_name") + " ");
            //cell.service.setAnimation(AnimationUtils.loadAnimation(getView(position,convertView,parent).getContext() , R.anim.flash_leave_now));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return convertView;
    }

    private class ListCell {
        private TextView service;
        private TextView top;
        private Button book;
        private ImageView service_pic;
    }
}
