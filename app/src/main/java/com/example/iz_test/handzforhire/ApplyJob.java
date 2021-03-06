package com.example.iz_test.handzforhire;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.glide.Glideconstants;
import com.glide.RoundedCornersTransformation;
import com.listeners.ApiResponseListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class ApplyJob extends BackKeyHandlerActivity implements SimpleGestureFilter.SimpleGestureListener , ApiResponseListener<String ,String>{

    private static final String JOB_URL = Constant.SERVER_URL+"apply_jobs";
    private static final String GET_AVERAGERAT = Constant.SERVER_URL+"get_average_rating";
    public static String APP_KEY = "X-APP-KEY";
    public static String JOB_ID = "job_id";
    public static String EMPLOYER_ID = "employer_id";
    public static String EMPLOYEE_ID = "employee_id";
    public static String COMMENTS = "comments";
    public static String USER_TYPE = "user_type";
    public static String KEY_USERID = "user_id";
    public static String TYPE = "type";
    String value = "HandzForHire@~";
    String job_id,user_id,employer_id,job_name,profile_name,image,date,start_time,end_time,amount,type,comments,username,firstname;
    TextView name,dat,amt,pay,text,job,rat_val,type_text,rating_text;
    ProgressDialog progress_dialog;
    ImageView profile_image;
    EditText com;
    RelativeLayout rating_lay;
    String usertype = "employee";
    int timeout = 60000;
    Dialog dialog;
    private SimpleGestureFilter detector;
    ImageView handz_button;
    private String merchantid;
    private SessionManager session;
    String app_value = "HandzForHire@~";
    private static final String PAYPAL_UPDATEURL = Constant.SERVER_URL+"user_merchant_id_update?";

    public static String MERCHANTID = "merchant_id";

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(intent.getStringExtra("isfrom") !=null && intent.getStringExtra("isfrom").equals("paypal")){
            PaypalCon.partnerReferralPrefillData(session.readReraalapilink(),session.ReadAccessToekn(), ApplyJob.this);
        }
    }


    public  void UpdatePaypal(){
        dialog.show();
 /*       System.out.println("URL "+ PAYPAL_UPDATEURL+APP_KEY+"="+value+"&"+KEY_USERID+"="+id+"&"+MERCHANTID+"="+merchantid+"&device=android");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, PAYPAL_UPDATEURL+APP_KEY+"="+value+"&"+KEY_USERID+"="+id+"&"+MERCHANTID+"="+merchantid+"&device=android",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(Registrationpage3.this,response,Toast.LENGTH_LONG).show();

                        System.out.println("eeeee:"+response);
                        onResponserecieved(response,1);
                        dialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        if (error instanceof TimeoutError ||error instanceof NoConnectionError) {
                            final Dialog dialog = new Dialog(EditUserProfile.this);
                            dialog.setContentView(R.layout.custom_dialog);
                            // set the custom dialog components - text, image and button
                            TextView text = (TextView) dialog.findViewById(R.id.text);
                            text.setText("Error Connecting To Network");
                            Button dialogButton = (Button) dialog.findViewById(R.id.ok);
                            // if button is clicked, close the custom dialog
                            dialogButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });

                            dialog.show();
                            Window window = dialog.getWindow();
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            window.setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        }else if (error instanceof AuthFailureError) {
                            Toast.makeText(getApplicationContext(),"Authentication Failure while performing the request",Toast.LENGTH_LONG).show();
                        }else if (error instanceof NetworkError) {
                            Toast.makeText(getApplicationContext(),"Network error while performing the request",Toast.LENGTH_LONG).show();
                        }else {
                            try {
                                String responseBody = new String(error.networkResponse.data, "utf-8");
                                JSONObject jsonObject = new JSONObject(responseBody);
                                String status = jsonObject.getString("msg");
                                if (!status.equals("")) {
                                    // custom dialog
                                    final Dialog dialog = new Dialog(EditUserProfile.this);
                                    dialog.setContentView(R.layout.custom_dialog);

                                    // set the custom dialog components - text, image and button
                                    TextView text = (TextView) dialog.findViewById(R.id.text);
                                    text.setText(status);
                                    Button dialogButton = (Button) dialog.findViewById(R.id.ok);
                                    // if button is clicked, close the custom dialog
                                    dialogButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();
                                        }
                                    });

                                    dialog.show();
                                    Window window = dialog.getWindow();
                                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                    window.setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                }

                            } catch (JSONException e) {
                                //Handle a malformed json response
                                System.out.println("volley error ::" + e.getMessage());
                            } catch (UnsupportedEncodingException errors) {
                                System.out.println("volley error ::" + errors.getMessage());
                            }
                        }

                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
              *//*  params.put(APP_KEY,value);
                params.put(KEY_USERID,id);
                params.put(MERCHANTID, merchantid);
                params.put(Constant.DEVICE, Constant.ANDROID);
                System.out.println("Params "+params);*//*
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //stringRequest.setRetryPolicy(new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);*/

        String url= PAYPAL_UPDATEURL+APP_KEY+"="+app_value+"&"+KEY_USERID+"="+user_id+"&"+MERCHANTID+"="+merchantid+"&device=android";

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        dialog.dismiss();
                        Log.d("Response", response.toString());
                        System.out.println("updatea merchantd" + response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        dialog.dismiss();
                        if (error instanceof TimeoutError ||error instanceof NoConnectionError) {
                            final Dialog dialog = new Dialog(ApplyJob.this);
                            dialog.setContentView(R.layout.custom_dialog);
                            // set the custom dialog components - text, image and button
                            TextView text = (TextView) dialog.findViewById(R.id.text);
                            text.setText("Error Connecting To Network");
                            Button dialogButton = (Button) dialog.findViewById(R.id.ok);
                            // if button is clicked, close the custom dialog
                            dialogButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });

                            dialog.show();
                            Window window = dialog.getWindow();
                            dialog.getWindow().setDimAmount(0);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            window.setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        }else if (error instanceof AuthFailureError) {
                            Toast.makeText(getApplicationContext(),"Authentication Failure while performing the request",Toast.LENGTH_LONG).show();
                        }else if (error instanceof NetworkError) {
                            Toast.makeText(getApplicationContext(),"Network error while performing the request",Toast.LENGTH_LONG).show();
                        }else {
                            try {
                                String responseBody = new String(error.networkResponse.data, "utf-8");
                                JSONObject jsonObject = new JSONObject(responseBody);
                                String status = jsonObject.getString("msg");
                                if (!status.equals("")) {
                                    // custom dialog
                                    final Dialog dialog = new Dialog(ApplyJob.this);
                                    dialog.setContentView(R.layout.custom_dialog);

                                    // set the custom dialog components - text, image and button
                                    TextView text = (TextView) dialog.findViewById(R.id.text);
                                    text.setText(status);
                                    Button dialogButton = (Button) dialog.findViewById(R.id.ok);
                                    // if button is clicked, close the custom dialog
                                    dialogButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();
                                        }
                                    });

                                    dialog.show();
                                    Window window = dialog.getWindow();
                                    dialog.getWindow().setDimAmount(0);
                                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                    window.setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                }

                            } catch (JSONException e) {
                                //Handle a malformed json response
                                System.out.println("volley error ::" + e.getMessage());
                            } catch (UnsupportedEncodingException errors) {
                                System.out.println("volley error ::" + errors.getMessage());
                            }
                        }
                    }
                }
        );


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(getRequest);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apply_job);

        dialog = new Dialog(ApplyJob.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progressbar);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        detector = new SimpleGestureFilter(this,this);
        session = new SessionManager(getApplicationContext());

        TextView apply = (TextView) findViewById(R.id.apply);
        name = (TextView) findViewById(R.id.text1);
        dat = (TextView) findViewById(R.id.tv2);
        amt = (TextView) findViewById(R.id.tv4);
        text = (TextView) findViewById(R.id.tv7);
        job = (TextView) findViewById(R.id.tv1);
        rat_val=(TextView)findViewById(R.id.text3);
        type_text = (TextView)findViewById(R.id.type);
        com = (EditText) findViewById(R.id.edit);
        profile_image = (ImageView) findViewById(R.id.profile_image);
        rating_lay = (RelativeLayout) findViewById(R.id.rating);
        handz_button = (ImageView) findViewById(R.id.handz_button);
        TextView t1 = (TextView) findViewById(R.id.t1);
        TextView t2 = (TextView) findViewById(R.id.t2);
        TextView t3 = (TextView) findViewById(R.id.t3);
        TextView t4 = (TextView) findViewById(R.id.tv6);
        rating_text = (TextView) findViewById(R.id.text2);

        String fontPath1 = "fonts/LibreFranklin-SemiBoldItalic.ttf";
        Typeface tf1 = Typeface.createFromAsset(getAssets(), fontPath1);
        rating_text.setTypeface(tf1);

        String fontPath = "fonts/LibreFranklin-SemiBold.ttf";
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
        dat.setTypeface(tf);
        amt.setTypeface(tf);
        type_text.setTypeface(tf);
        job.setTypeface(tf);
        text.setTypeface(tf);
        apply.setTypeface(tf);
        t1.setTypeface(tf);
        t2.setTypeface(tf);
        t3.setTypeface(tf);
        t4.setTypeface(tf);
        rat_val.setTypeface(tf);

        String fontPath2 = "fonts/cambriab.ttf";
        Typeface tf2 = Typeface.createFromAsset(getAssets(), fontPath2);
        name.setTypeface(tf2);

        Intent i = getIntent();
        job_id = i.getStringExtra("jobId");
        user_id = i.getStringExtra("userId");
        employer_id = i.getStringExtra("employerId");
        job_name = i.getStringExtra("job_name");
        date = i.getStringExtra("date");
        start_time = i.getStringExtra("start_time");
        end_time = i.getStringExtra("end_time");
        profile_name = i.getStringExtra("profile_name");
        amount = i.getStringExtra("amount");
        type = i.getStringExtra("type");
        image = i.getStringExtra("image");
        usertype=i.getStringExtra("usertype");
        username=i.getStringExtra("username");
        firstname=i.getStringExtra("firstname");
        if(profile_name.equals(""))
        {
            name.setText(username);
        }
        if(profile_name.equals("")&&username.equals(""))
        {
            name.setText(firstname);
        }
        else
        {
            name.setText(profile_name);
        }

        /*String s1 = "1.00";
=======
        String s1 = "1.00";
        if(amount.contains("$"))
        {
            amount = amount.replace("$","");
        }
>>>>>>> devlop
        String multi = String.valueOf(Float.valueOf(amount)*Float.valueOf(s1));
        String total_amount = String.format("%.2f", Float.valueOf(multi));*/
        amt.setText(amount);
        type_text.setText(type);
        text.setText(profile_name);
        job.setText(job_name);

        DateFormat dateInstance = SimpleDateFormat.getDateInstance();
        DateFormat srcDf = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat destDf = new SimpleDateFormat("EEEE, MMMM dd, yyyy");
        try {
            java.util.Date dates = srcDf.parse(date);
            dat.setText("" + destDf.format(dates));

        } catch (Exception e)
        {
            System.out.println("error " + e.getMessage());
        }

        if(image!= null && image.contains("http://graph.facebook.com/"))
        {
            image = image.replace("https://www.handzadmin.com/assets/images/uploads/profile/","");
        }

        Glide.with(ApplyJob.this).load(image).apply(RequestOptions.bitmapTransform(new RoundedCornersTransformation(this,0, Glideconstants.sCorner,Glideconstants.sColor, Glideconstants.sBorder)).error(R.drawable.default_profile)).into(profile_image);

        getAverageRatigng();

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comments = com.getText().toString().trim();
                applyJob();
            }
        });

        com.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com.setHint("");
            }
        });

        rating_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ApplyJob.this,ReviewRating.class);
                i.putExtra("userId", user_id);
                i.putExtra("image",image);
                i.putExtra("name", profile_name);
                startActivity(i);
            }
        });

        handz_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ApplyJob.this,LendProfilePage.class);
                i.putExtra("userId", user_id);
                startActivity(i);
            }
        });


    }
    public void getAverageRatigng() {
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_AVERAGERAT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("average rat:" + response);
                        try {
                            JSONObject object = new JSONObject(response);
                           rat_val.setText(object.getString("average_rating"));
                        }catch (Exception e){
                            System.out.println("exception "+e.getMessage());
                        }
                        dialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        dialog.dismiss();
                        //Toast.makeText(LoginActivity.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put(APP_KEY, value);
                map.put(KEY_USERID, user_id);
                map.put(TYPE, usertype);
                map.put(Constant.DEVICE, Constant.ANDROID);
                System.out.println(" Map "+map);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public void applyJob()
    {
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, JOB_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        onResponserecieved1(response, 2);
                        dialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        if (error instanceof TimeoutError ||error instanceof NoConnectionError) {
                            final Dialog dialog = new Dialog(ApplyJob.this);
                            dialog.setContentView(R.layout.custom_dialog);
                            // set the custom dialog components - text, image and button
                            TextView text = (TextView) dialog.findViewById(R.id.text);
                            text.setText("Error Connecting To Network");
                            Button dialogButton = (Button) dialog.findViewById(R.id.ok);
                            // if button is clicked, close the custom dialog
                            dialogButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });

                            dialog.show();
                            Window window = dialog.getWindow();
                            dialog.getWindow().setDimAmount(0);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            window.setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        }else if (error instanceof AuthFailureError) {
                            Toast.makeText(getApplicationContext(),"Authentication Failure while performing the request",Toast.LENGTH_LONG).show();
                        }else if (error instanceof NetworkError) {
                            Toast.makeText(getApplicationContext(),"Network error while performing the request",Toast.LENGTH_LONG).show();
                        }else {
                            try {
                                String responseBody = new String(error.networkResponse.data, "utf-8");
                                JSONObject jsonObject = new JSONObject(responseBody);
                                System.out.println("error" + jsonObject);
                                String status = jsonObject.getString("msg");
                               // if (status.equals("You are not allowed to apply for the job")) {
                                    // custom dialog
                                    final Dialog dialog = new Dialog(ApplyJob.this);
                                    dialog.setContentView(R.layout.custom_dialog);

                                    // set the custom dialog components - text, image and button
                                    final TextView text = (TextView) dialog.findViewById(R.id.text);
                                    text.setText(status);
                                    Button dialogButton = (Button) dialog.findViewById(R.id.ok);
                                    // if button is clicked, close the custom dialog
                                    dialogButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v)
                                        {
                                            if(text.getText().toString().equals(getString(R.string.paypal_link_error_applyjob))) {

                                                new Thread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Intent intent = Utility.GetPaypalLinkIntent(session , ApplyJob.this , Constant.APPLY_JOB);
                                                        startActivityForResult(intent ,Constant.LINK_PAYPAL );
                                                    }
                                                }).start();

                                            }

                                            dialog.dismiss();
                                        }
                                    });

                                    dialog.show();
                                    Window window = dialog.getWindow();
                                    dialog.getWindow().setDimAmount(0);
                                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                    window.setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                             //   }
                            } catch (JSONException e) {

                            } catch (UnsupportedEncodingException error1) {

                            }
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put(APP_KEY, value);
                params.put(JOB_ID, job_id);
                params.put(USER_TYPE, usertype);
                params.put(EMPLOYEE_ID, user_id);
                params.put(EMPLOYER_ID, employer_id);
                params.put(COMMENTS, comments);
                params.put(Constant.DEVICE, Constant.ANDROID);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
       // stringRequest.setRetryPolicy(new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    public void onResponserecieved1(String jsonobject, int requesttype) {
        System.out.println("response from interface"+jsonobject);

        String status = null;

        try {

            JSONObject jResult = new JSONObject(jsonobject);

            status = jResult.getString("status");

            if(status.equals("success"))
            {
                Intent i = new Intent(ApplyJob.this,LendProfilePage.class);
                i.putExtra("userId",user_id);
                startActivity(i);
           /*     // custom dialog
                final Dialog dialog = new Dialog(ApplyJob.this);
                dialog.setContentView(R.layout.gray_custom);

                // set the custom dialog components - text, image and button
                TextView text = (TextView) dialog.findViewById(R.id.text);
                text.setText("Job Applied Successfully");
                Button dialogButton = (Button) dialog.findViewById(R.id.ok);
                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Intent i = new Intent(ApplyJob.this,LendProfilePage.class);
                        i.putExtra("userId",user_id);
                        startActivity(i);
                    }
                });

                dialog.show();
                Window window = dialog.getWindow();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                window.setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);*/
            }
            else
            {
                // custom dialog
                final Dialog dialog = new Dialog(ApplyJob.this);
                dialog.setContentView(R.layout.custom_dialog);

                // set the custom dialog components - text, image and button
                TextView text = (TextView) dialog.findViewById(R.id.text);
                text.setText("Job Applied Failed.Please try again....");
                Button dialogButton = (Button) dialog.findViewById(R.id.ok);
                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Intent i = new Intent(ApplyJob.this,LendProfilePage.class);
                        i.putExtra("userId",user_id);
                        startActivity(i);
                    }
                });

                dialog.show();
                Window window = dialog.getWindow();
                dialog.getWindow().setDimAmount(0);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                window.setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onSwipe(int direction) {
        String str = "";

        switch (direction) {

            case SimpleGestureFilter.SWIPE_RIGHT : str = "Swipe Right";
                Intent j = new Intent(getApplicationContext(), SwitchingSide.class);
                startActivity(j);
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                finish();
                break;
            case SimpleGestureFilter.SWIPE_LEFT :  str = "Swipe Left";
                Intent i = new Intent(getApplicationContext(), LendProfilePage.class);
                i.putExtra("userId", Profilevalues.user_id);
                i.putExtra("address", Profilevalues.address);
                i.putExtra("city", Profilevalues.city);
                i.putExtra("state", Profilevalues.state);
                i.putExtra("zipcode", Profilevalues.zipcode);
                startActivity(i);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                finish();
                break;
           /* case SimpleGestureFilter.SWIPE_DOWN :  str = "Swipe Down";
                break;
            case SimpleGestureFilter.SWIPE_UP :    str = "Swipe Up";
                break;*/

        }
        //  Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDoubleTap() {

    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event){

        this.detector.onTouchEvent(event);
        return super.dispatchTouchEvent(event);
    }

    @Override
    public void OnResponseReceived(String i, String s) {
        merchantid = i;
        UpdatePaypal();
    }
}
