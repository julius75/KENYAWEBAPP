package com.example.julius.kenyawebdevelopers;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;


class ApiConnector {
    //used to get the user's specific details from database
  //  JSONArray GetConstituencies(String CountyID) {
//        String county = "Kiambu";
//        String url = Constants.BASE_URL + "home/getConstituency.php?county=" + county;
//        HttpEntity httpEntity = null;
//        try {
//            DefaultHttpClient httpClient = new DefaultHttpClient();
//            HttpGet httpGet = new HttpGet(url);
//            HttpResponse httpResponse = httpClient.execute(httpGet);
//            httpEntity = httpResponse.getEntity();
//
//            HttpURLConnection urlConnection = (HttpURLConnection) urlObj.openConnection();
//            InputStream is = urlConnection.getInputStream();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        JSONArray jsonArray = null;
//        if (httpEntity != null) {
//            try {
//                String entityResponse = EntityUtils.toString(httpEntity);
//                jsonArray = new JSONArray(entityResponse);
//            } catch (JSONException | IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return jsonArray;
 //   }
    JSONArray Getpackages() {
        //but we will have to specify the service code we are accessing the packages as below
        //Getpackages(String servicecode);
        String url = Constants.GET_PACKAGES;
        //HttpEntity httpEntity = null;
        int statusCode=0;
        InputStream is=null;
        HttpURLConnection urlConnection= null;
        try {
            URL urlObj = new URL(url);
            urlConnection = (HttpURLConnection) urlObj.openConnection();
//            urlConnection .setReadTimeout(10000);
//
            urlConnection .setConnectTimeout(150000);

            urlConnection .setRequestMethod("POST");

            urlConnection .setDoInput(true);

            urlConnection .setDoOutput(true);
            //is = urlConnection.getInputStream();

        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONArray jsonArray = null;
        try {
            statusCode = urlConnection.getResponseCode();
//            urlConnection.setRequestMethod("GET");
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (statusCode == urlConnection.HTTP_OK) {
            try {
                is = urlConnection.getInputStream();
                BufferedReader in = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                //String entityResponse = EntityUtils.toString(in.toString());
                Log.d("apiconn",in.toString());
                String str = in.readLine();
                Log.d("apicbbbonn", str);
                jsonArray =new JSONArray(str);
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
        }

        return jsonArray;
    }
    JSONArray SendContacts(JSONArray contactdetails){
        URL url = null;
        JSONArray jsonArray = null;
        try {
            url =new URL(Constants.GET_SUBSERVICES);

            Map<String,Object> params = new LinkedHashMap<>();
            params.put("serviced", contactdetails);
            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String,Object> param : params.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }
            byte[] postDataBytes = postData.toString().getBytes("UTF-8");

            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            conn.setDoOutput(true);
            conn.getOutputStream().write(postDataBytes);

            Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

//        for (int c; (c = in.read()) >= 0;)
//            System.out.print((char)c);

            String str = ((BufferedReader) in).readLine();
            //Log.d("subservices of"+serviced, str);
            jsonArray =new JSONArray(str);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return jsonArray;
    }
    JSONArray GetsubService(String serviced){
        URL url = null;
        JSONArray jsonArray = null;
        try {
            url =new URL(Constants.GET_SUBSERVICES);

            Map<String,Object> params = new LinkedHashMap<>();
            params.put("serviced", serviced);
            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String,Object> param : params.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }
            byte[] postDataBytes = postData.toString().getBytes("UTF-8");

            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            conn.setDoOutput(true);
            conn.getOutputStream().write(postDataBytes);

            Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

//        for (int c; (c = in.read()) >= 0;)
//            System.out.print((char)c);

            String str = ((BufferedReader) in).readLine();
            Log.d("subservices of"+serviced, str);
            jsonArray =new JSONArray(str);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return jsonArray;

    }
    JSONArray GetServicepackages(String serviced){
        URL url = null;
        JSONArray jsonArray = null;
        try {
            url =new URL(Constants.GET_SPECIFICPACKAGES);

        Map<String,Object> params = new LinkedHashMap<>();
        params.put("name", "Freddie the Fish");
        params.put("email", "fishie@seamail.example.com");
        params.put("serviced", serviced);
        params.put("message", "Shark attacks in Botany Bay have gotten out of control. We need more defensive dolphins to protect the schools here, but Mayor Porpoise is too busy stuffing his snout with lobsters. He's so shellfish.");

        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String,Object> param : params.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");

        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conn.setDoOutput(true);
        conn.getOutputStream().write(postDataBytes);

        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

//        for (int c; (c = in.read()) >= 0;)
//            System.out.print((char)c);

        String str = ((BufferedReader) in).readLine();
        Log.d("apicbbbonn", str);
        jsonArray =new JSONArray(str);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return jsonArray;

    }


    JSONArray Get2services() {
        String url = Constants.GET_SECONDLEVELSERVICES;
        //HttpEntity httpEntity = null;
        int statusCode=0;
        InputStream is=null;
        HttpURLConnection urlConnection= null;
        try {
              URL urlObj = new URL(url);
            urlConnection = (HttpURLConnection) urlObj.openConnection();
//            urlConnection .setReadTimeout(10000);
//
            urlConnection .setConnectTimeout(150000);

            urlConnection .setRequestMethod("POST");

            urlConnection .setDoInput(true);

            urlConnection .setDoOutput(true);
            //is = urlConnection.getInputStream();

        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONArray jsonArray = null;
        try {
            statusCode = urlConnection.getResponseCode();
//            urlConnection.setRequestMethod("GET");
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (statusCode == urlConnection.HTTP_OK) {
            try {
                is = urlConnection.getInputStream();
                BufferedReader in = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
              //String entityResponse = EntityUtils.toString(in.toString());
                Log.d("apiconn",in.toString());
               String str = in.readLine();
                Log.d("apicbbbonn", str);
                jsonArray =new JSONArray(str);
                } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
        }

        return jsonArray;
    }
}
