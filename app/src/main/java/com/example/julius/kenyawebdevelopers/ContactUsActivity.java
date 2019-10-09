package com.example.julius.kenyawebdevelopers;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.Hashtable;
import java.util.Map;

public class ContactUsActivity extends AppCompatActivity {
ImageView back;
JSONArray contactdetails;
    TextView countTxt, countTxt2;
    Button finished;
    EditText feedback, subject,email,mobile,fname,lname;
    ProgressBar progress;
    String SEmail="N/A", SFeedback="N/A", SMobile="N/A", SFirstname="N/A",Slastname="N/A",SSubject="N/A";
    public static final String DEFAULT = "N/A";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        countTxt = findViewById(R.id.countTxt);
        countTxt2 = findViewById(R.id.countTxt2);
        finished = findViewById(R.id.finished);
        progress = findViewById(R.id.progress);
        feedback =findViewById(R.id.feedback);
        subject = findViewById(R.id.subject);
        email = findViewById(R.id.email);
       fname = findViewById(R.id.fname);
        lname = findViewById(R.id.lname);
        mobile = findViewById(R.id.phone);
        progress.setVisibility(View.INVISIBLE);
        back=findViewById(R.id.back);
        finished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (feedback.length() > 0 &&
                        feedback.length() < 251 &&
                        subject.length() > 0 &&
                        subject.length() < 71 ) {
                    //send feedback to server
                    finished.setVisibility(View.INVISIBLE);
                    progress.setVisibility(View.VISIBLE);
                    SEmail = email.getText().toString();
                    SFirstname=fname.getText().toString();
                    Slastname=lname.getText().toString();
                    SMobile = mobile.getText().toString();
                    SFeedback = feedback.getText().toString();
                    SSubject = subject.getText().toString();
                    if(mobile.length()<16 && mobile.length()>9 ){
                        if(email.length()<6 && email.length()>40 ){
                            email.setError("Check your email address please");
                        }else{
                            sendFeedback();
                        }

                    } else{
                        mobile.setError("Check your phone number");
                    }

                } else if (feedback.length() > 250) {
                    feedback.setError("Exceeded limit");
                    subject.setError("Exceeded limit");
                } else {
                    feedback.setError("Please fill feedback");
                    subject.setError("Please fill subject");
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
    private void sendFeedback() {
        //delete the token of the user on the server
        //Showing the progress dialog
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.FEEDBACK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.d("Contact response",s);
                        if (s.equals("Successful")) {
                            progress.setVisibility(View.INVISIBLE);

                            Toast toast = Toast.makeText(ContactUsActivity.this, " Thank you for your Feedback :)", Toast.LENGTH_LONG);
                            View toastView = toast.getView(); //This'll return the default View of the Toast.
                            TextView toastMessage = (TextView) toastView.findViewById(android.R.id.message);
                            toastMessage.setTextSize(12);
                            toastMessage.setTextColor(getResources().getColor(R.color.white));
                            toastMessage.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_launcher, 0, 0, 0);
                            toastMessage.setGravity(Gravity.CENTER);
                            toastMessage.setCompoundDrawablePadding(10);
                            toastView.setBackground(getResources().getDrawable(R.drawable.bg_button2));
                            toast.show();

                            goToMain();
                        } else {
                            progress.setVisibility(View.INVISIBLE);
                            finished.setVisibility(View.VISIBLE);
                            AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                            builder.setMessage("Sorry, an error occurred.\nTry again?")
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            //take new user to registration
                                            sendFeedback();
                                        }
                                    })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // User cancelled the dialog
                                            dialog.dismiss();
                                        }
                                    });
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        progress.setVisibility(View.INVISIBLE);
                        finished.setVisibility(View.VISIBLE);
                        AlertDialog.Builder builder = new AlertDialog.Builder(ContactUsActivity.this);
                        builder.setMessage("Sorry, an error occurred.\nTry again?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //take new user to registration
                                        sendFeedback();
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // User cancelled the dialog
                                        dialog.dismiss();
                                    }
                                });
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Creating parameters
                Map<String, String> params = new Hashtable<String, String>();

                //Adding parameters
                params.put("email", SEmail);
                params.put("feedback", SFeedback.replaceAll("'", "''"));
                params.put("subject", SSubject.replaceAll("'", "''"));
                params.put("mobile", SMobile);
                params.put("fname", SFirstname);
                params.put("lname", Slastname);
                params.put("REQUEST_METHOD", "POST");

                //returning parameters
                return params;
            }
        };
        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(ContactUsActivity.this);
        //Adding request to the queue
        requestQueue.add(stringRequest);
    }
    @SuppressLint("StaticFieldLeak")
    private class GetSubservicesTask extends AsyncTask<ApiConnector, Long, JSONArray> {
        @Override
        protected JSONArray doInBackground(ApiConnector... params) {
            try {
                //swipe_refresh_layout.setVisibility(View.VISIBLE);
                // it is executed on Background thread
                return params[0].SendContacts(contactdetails);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {

            //setListAdapter(jsonArray);
        }
    }
    private void goToMain() {
        Intent intent=new Intent(ContactUsActivity.this,MainActivity.class);
        startActivity(intent);
//        MainFragment fragment = new MainFragment();
//        android.support.v4.app.FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.Content, fragment);
//        fragmentTransaction.commit();
    }
}
