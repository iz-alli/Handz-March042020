package com.example.iz_test.handzforhire;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.listeners.ApiResponseListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class SummaryAdd extends BackKeyHandlerActivity implements SimpleGestureFilter.SimpleGestureListener ,ApiResponseListener<String ,String> {

    private static final String URL = Constant.SERVER_URL+"create_job";
    private static final String EDIT_URL = Constant.SERVER_URL+"edit_job";
    public static String USER_ID = "user_id";
    public static String JOB_NAME = "job_name";
    public static String JOB_CATEGORY = "job_category";
    public static String JOB_DESCRIPTION = "job_description";
    public static String JOB_DATE = "job_date";
    public static String JOB_START_DATE = "job_start_date";
    public static String JOB_END_DATE = "job_end_date";
    public static String START_TIME = "start_time";
    public static String END_TIME = "end_time";
    public static String JOB_PAYMENT_AMOUNT = "job_payment_amount";
    public static String JOB_PAYMENT_TYPE = "job_payment_type";
    public static String ADDRESS = "address";
    public static String CITY = "city";
    public static String STATE = "state";
    public static String ZIPCODE = "zipcode";
    public static String POST_ADDRESS = "post_address";
    public static String APP_KEY = "X-APP-KEY";
    public static String LATITUDE = "lat";
    public static String LONGITUDE = "lon";
    public static String CURRENT_LOCATION = "currentlocation";
    public static String POCKET_EXPENSE = "out_of_pocket_expense";
    public static String USER_TYPE = "user_type";
    public static String JOB_STATE = "job_state";
    public static String ESTIMATED_PAYMENT = "job_estimated_payment";
    public static String FLEXIBLE = "job_date_time_flexible";
    public static String JOB_ZIPCODE = "job_zipcode";
    public static String JOB_ADDRESS = "job_address";
    public static String JOB_CITY = "job_city";
    public static String JOB_PAYOUT = "jobPayout";
    public static String PAYPAL_FEE = "paypalFee";
    public static String FEE_DETAILS = "fee_details";
    public static String JOB_EXPIRE = "job_expire";
    public static String SUB_CATEGORY = "sub_category";
    public static String CATEGORY_COLOR = "job_category_color";
    public static String DELIST = "delist";
    public static String JOB_ID = "job_id";
    String key = "HandzForHire@~";
    String job_id, hour_expected,job_expire;
    TextView job_payout,pocket_expense,paypal_merchant;
    EditText hourly_value,expected_value;
    String value,id,name,category,description,date,start_time,expected_hours,end_time,amount,type,current_location;
    String post_address,checkbox_status,latitude,longitude,estimated_amount,flexible_status;
    String fee_details = "add";
    String address = "No 2, Third Floor 2nd, 2, Main Rd, Subramaniya Swamy Nagar, ";
    String city = "Chennai";
    String state = "Tamil Nadu";
    String zipcode = "600087";
    String usertype = "employer";
    String sub_category;
    String job_category_color;
    String expense,fee,payout,edit_job,duration,session_status;
    String delist = "yes";
    Dialog dialog;
    private SimpleGestureFilter detector;
    SessionManager session;
    String merchantid;
    String app_value = "HandzForHire@~";
    private static final String PAYPAL_UPDATEURL = Constant.SERVER_URL+"user_merchant_id_update?";
    public static String KEY_USERID = "user_id";
    public static String MERCHANTID = "merchant_id";

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
         if(intent.getStringExtra("isfrom") !=null && intent.getStringExtra("isfrom").equals("paypal")){

             PaypalCon.partnerReferralPrefillData(session.readReraalapilink(),session.ReadAccessToekn(),this);
            // UpdatePaypal();

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

        String url= PAYPAL_UPDATEURL+APP_KEY+"="+app_value+"&"+KEY_USERID+"="+id+"&"+MERCHANTID+"="+merchantid+"&device=android";

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
                            final Dialog dialog = new Dialog(SummaryAdd.this);
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
                                    final Dialog dialog = new Dialog(SummaryAdd.this);
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
        setContentView(R.layout.summary_add);

        ImageView logo = (ImageView) findViewById(R.id.logo);
        ImageView back = (ImageView) findViewById(R.id.back);
        TextView create_job = (TextView) findViewById(R.id.create_btn);
        TextView edit = (TextView) findViewById(R.id.edit_btn);
        pocket_expense = (TextView) findViewById(R.id.ope);
        paypal_merchant = (TextView) findViewById(R.id.pmpf);
        job_payout = (TextView) findViewById(R.id.ajp);
        hourly_value = (EditText) findViewById(R.id.hourly_text);
        expected_value = (EditText) findViewById(R.id.expected_text);
        ImageView info = (ImageView) findViewById(R.id.info);
        TextView text = (TextView) findViewById(R.id.text);
        TextView text1 = (TextView) findViewById(R.id.text1);
        TextView text2 = (TextView) findViewById(R.id.text2);
        TextView text3 = (TextView) findViewById(R.id.text3);
        TextView text4 = (TextView) findViewById(R.id.text4);
        TextView text5 = (TextView) findViewById(R.id.text5);
        TextView text6 = (TextView) findViewById(R.id.text6);
        TextView text7 = (TextView) findViewById(R.id.text7);
        TextView text8 = (TextView) findViewById(R.id.text8);
        TextView text9 = (TextView) findViewById(R.id.text9);
        TextView text10 = (TextView) findViewById(R.id.text10);
        TextView text11 = (TextView) findViewById(R.id.text11);
        TextView text12 = (TextView) findViewById(R.id.text12);
        TextView total = (TextView) findViewById(R.id.total);


        Intent i = getIntent();
        id = i.getStringExtra("userId");
        name = i.getStringExtra("job_name");
        category = i.getStringExtra("job_category");
        job_category_color = i.getStringExtra("job_category_color");
        sub_category = i.getStringExtra("sub_category");
        description = i.getStringExtra("job_decription");
        date = i.getStringExtra("job_date");
        start_time = i.getStringExtra("start_time");
        expected_hours = i.getStringExtra("expected_hours");
        amount = i.getStringExtra("payment_amount");
        type = i.getStringExtra("payment_type");
        current_location = i.getStringExtra("current_location");
        post_address = i.getStringExtra("post_address");
        latitude = i.getStringExtra("latitude");
        longitude = i.getStringExtra("longitude");
        estimated_amount = i.getStringExtra("estimated_amount");
        flexible_status = i.getStringExtra("flexible_status");
        job_expire = i.getStringExtra("job_expire");
        edit_job = i.getStringExtra("edit_job");
        job_id = i.getStringExtra("job_id");
        duration = i.getStringExtra("duration");
        System.out.println("eeeeeeeee:time_value:sa::"+start_time);

        String fontPath = "fonts/LibreFranklin-SemiBold.ttf";
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
        text.setTypeface(tf);
        edit.setTypeface(tf);
        create_job.setTypeface(tf);

        String fontPath1 = "fonts/LibreFranklin-SemiBoldItalic.ttf";
        Typeface tf1 = Typeface.createFromAsset(getAssets(), fontPath1);
        text1.setTypeface(tf1);
        text2.setTypeface(tf1);
        text3.setTypeface(tf1);
        text4.setTypeface(tf1);
        text5.setTypeface(tf1);
        text6.setTypeface(tf1);
        text8.setTypeface(tf1);
        text10.setTypeface(tf1);
        text11.setTypeface(tf1);
        text12.setTypeface(tf1);
        pocket_expense.setTypeface(tf1);
        paypal_merchant.setTypeface(tf1);
        job_payout.setTypeface(tf1);
        hourly_value.setTypeface(tf1);
        expected_value.setTypeface(tf1);
        total.setTypeface(tf1);

        String fontPath2 = "fonts/LibreFranklin-Italic.ttf";
        Typeface tf2 = Typeface.createFromAsset(getAssets(), fontPath2);
        text7.setTypeface(tf2);
        text9.setTypeface(tf2);

        detector = new SimpleGestureFilter(this,this);

        session = new SessionManager(getApplicationContext());
        HashMap<String, String> check = session.getCheckboxStatus();
        session_status = check.get(SessionManager.CHECKBOX_STATUS);
        System.out.println("sssssssssssss:session:status:::::"+session_status+"...check:::"+check);

        dialog = new Dialog(SummaryAdd.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progressbar);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        if(edit_job.equals("yes"))
        {
            create_job.setVisibility(View.INVISIBLE);
            edit.setVisibility(View.VISIBLE);
        }
        else
        {
            edit.setVisibility(View.INVISIBLE);
            create_job.setVisibility(View.VISIBLE);
        }

        hourly_value.setText(amount);
        expected_value.setText(expected_hours);
        String hour = hourly_value.getText().toString();
        String expected = expected_value.getText().toString();
        hour_expected = String.valueOf(Float.valueOf(hour)*Float.valueOf(expected));
        String job_pay_value = String.format("%.2f", Float.valueOf(hour_expected));
        job_payout.setText(job_pay_value);
      /*  Integer pay = Integer.valueOf(job_pay_value);
        if(pay<0)
        {
            create_job.setVisibility(View.INVISIBLE);
            job_payout.setText("");
        }
        else
        {
            create_job.setVisibility(View.VISIBLE);
            job_payout.setText(job_pay_value);
        }
*/

        String s1 = "100";
        String multi = String.valueOf(Float.valueOf(s1)*Float.valueOf(hour_expected));
        System.out.println("sssssssssssss:summary:multi:"+multi);
        String s2 = "130";
        String add1 = String.valueOf(Float.valueOf(multi)+Float.valueOf(s2));
        System.out.println("sssssssssssss:summary:add1:"+add1);
        String s3 = "97.1";
        String div = String.valueOf(Float.valueOf(add1)/Float.valueOf(s3));
        System.out.println("sssssssssssss:summary:div:"+div);
        String pocket_value = String.format("%.2f", Float.valueOf(div));
        System.out.println("sssssssssssss:summary:total1:"+pocket_value);
        String handz_fee = "1.00";
        String pay_fee = String.valueOf(Float.valueOf(pocket_value)-Float.valueOf(hour_expected)-Float.valueOf(handz_fee));
        String total_value = String.format("%.2f", Float.valueOf(pay_fee));
        System.out.println("sssssssssssss:summary:pay_fee:"+pay_fee+"total2:::"+total_value);
        paypal_merchant.setText(total_value);
        pocket_expense.setText(pocket_value);


        hourly_value.addTextChangedListener(tw);
        expected_value.addTextChangedListener(tw1);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SummaryAdd.this,ProfilePage.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });

        create_job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(session_status.equals("checked"))
                {
                    payout = "$" + job_payout.getText().toString();
                    expense = "$" + pocket_expense.getText().toString();
                    fee = "$" + paypal_merchant.getText().toString();
                    System.out.println("sssssssssssss:add:pay:expe:fee:"+payout+".."+expense+".."+fee);
                    System.out.println("sssssssssssss:check_Ststus::::"+checkbox_status);
                    registerUser();
                }
                else
                {
                    final Dialog dialog = new Dialog(SummaryAdd.this);
                    dialog.setContentView(R.layout.create_job_popup);
                    Button ok_Button = (Button) dialog.findViewById(R.id.ok_btn);
                    final CheckBox checkBox = (CheckBox) dialog.findViewById(R.id.checkBox);
                    TextView text1 = (TextView) dialog.findViewById(R.id.text1);
                    TextView text2 = (TextView) dialog.findViewById(R.id.text2);
                    TextView text3 = (TextView) dialog.findViewById(R.id.text3);
                    String fontPath = "fonts/LibreFranklin-SemiBold.ttf";
                    Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
                    ok_Button.setTypeface(tf);
                    checkBox.setTypeface(tf);
                    text1.setTypeface(tf);
                    text2.setTypeface(tf);
                    text3.setTypeface(tf);

                    ok_Button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(checkBox.isChecked())
                            {
                                checkbox_status = "checked";
                                session.saveCheckboxStatus(checkbox_status);
                            }
                            else
                            {
                                checkbox_status = "unchecked";
                                session.saveCheckboxStatus(checkbox_status);
                            }

                            dialog.dismiss();
                            payout = "$" + job_payout.getText().toString();
                            expense = "$" + pocket_expense.getText().toString();
                            fee = "$" + paypal_merchant.getText().toString();
                            System.out.println("sssssssssssss:add:pay:expe:fee:"+payout+".."+expense+".."+fee);
                            System.out.println("sssssssssssss:check_Ststus::::"+checkbox_status);
                            registerUser();
                        }
                    });
                    dialog.show();
                    Window window = dialog.getWindow();
                    dialog.getWindow().setDimAmount(0);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    window.setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    return;
                }
            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // custom dialog
                final Dialog dialog = new Dialog(SummaryAdd.this);
                dialog.setContentView(R.layout.custom_dialog);

                // set the custom dialog components - text, image and button
                TextView text = (TextView) dialog.findViewById(R.id.text);
                text.setText("This fee may minimally increase if a tip is added at the time of payment.");
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
                return;
            }
        });


        edit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(session_status.equals("checked"))
                {
                    payout = "$" + job_payout.getText().toString();
                    expense = "$" + pocket_expense.getText().toString();
                    fee = "$" + paypal_merchant.getText().toString();
                    System.out.println("sssssssssssss:add:pay:expe:fee:"+payout+".."+expense+".."+fee);
                    System.out.println("sssssssssssss:check_Ststus::::"+checkbox_status);
                    editJob();
                }
                else
                {

                    final Dialog dialog = new Dialog(SummaryAdd.this);
                    dialog.setContentView(R.layout.create_job_popup);
                    Button ok_Button = (Button) dialog.findViewById(R.id.ok_btn);
                    final CheckBox checkBox = (CheckBox) dialog.findViewById(R.id.checkBox);
                    TextView text1 = (TextView) dialog.findViewById(R.id.text1);
                    TextView text2 = (TextView) dialog.findViewById(R.id.text2);
                    TextView text3 = (TextView) dialog.findViewById(R.id.text3);
                    String fontPath = "fonts/LibreFranklin-SemiBold.ttf";
                    Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
                    ok_Button.setTypeface(tf);
                    checkBox.setTypeface(tf);
                    text1.setTypeface(tf);
                    text2.setTypeface(tf);
                    text3.setTypeface(tf);

                    // if button is clicked, close the custom dialog
                    ok_Button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(checkBox.isChecked())
                            {
                                checkbox_status = "checked";
                                session.saveCheckboxStatus(checkbox_status);
                            }
                            else
                            {
                                checkbox_status = "unchecked";
                                session.saveCheckboxStatus(checkbox_status);
                            }

                            dialog.dismiss();
                            payout = "$" + job_payout.getText().toString();
                            expense = "$" + pocket_expense.getText().toString();
                            fee = "$" + paypal_merchant.getText().toString();
                            System.out.println("sssssssssssss:add:pay:expe:fee:"+payout+".."+expense+".."+fee);
                            System.out.println("sssssssssssss:check_Ststus::::"+checkbox_status);
                            editJob();
                        }
                    });
                    dialog.show();
                    Window window = dialog.getWindow();
                    dialog.getWindow().setDimAmount(0);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    window.setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    return;
                }
            }
        });
    }

    private void registerUser()
    {
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(Registrationpage3.this,response,Toast.LENGTH_LONG).show();
                        System.out.println("eeeee:createjob2"+response);
                        onResponserecieved(response,1);
                        dialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        if (error instanceof TimeoutError ||error instanceof NoConnectionError) {
                            final Dialog dialog = new Dialog(SummaryAdd.this);
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
                                    final Dialog dialog = new Dialog(SummaryAdd.this);
                                    dialog.setContentView(R.layout.custom_dialog);

                                    // set the custom dialog components - text, image and button
                                    final TextView text = (TextView) dialog.findViewById(R.id.text);
                                    text.setText(status);
                                    Button dialogButton = (Button) dialog.findViewById(R.id.ok);
                                    // if button is clicked, close the custom dialog
                                    dialogButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                          if(text.getText().toString().equals(getString(R.string.paypal_link_error))) {

                                              new Thread(new Runnable() {
                                                  @Override
                                                  public void run() {
                                                      Intent intent = Utility.GetPaypalLinkIntent(session , SummaryAdd.this , Constant.SUMMARY_ADD);
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
                                }

                            } catch (JSONException e) {

                            } catch (UnsupportedEncodingException error1) {

                            }
                        }
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(APP_KEY,key);
                params.put(USER_ID,id);
                params.put(JOB_NAME,name);
                params.put(USER_TYPE,usertype);
                params.put(JOB_CATEGORY, category);
                params.put(JOB_DESCRIPTION,description);
                params.put(JOB_DATE,date);
                params.put(JOB_START_DATE,start_time);
                params.put(JOB_END_DATE,start_time);
                params.put(START_TIME,start_time);
                params.put(END_TIME,start_time);
                params.put(JOB_PAYMENT_AMOUNT,amount);
                params.put(POCKET_EXPENSE,expense);
                params.put(JOB_PAYMENT_TYPE,duration);
                params.put(ADDRESS,address);
                params.put(CITY,city);
                params.put(CURRENT_LOCATION,current_location);
                params.put(STATE,state);
                params.put(ZIPCODE,zipcode);
                params.put(POST_ADDRESS,post_address);
                params.put(LATITUDE,latitude);
                params.put(LONGITUDE,longitude);
                params.put(JOB_ADDRESS,address);
                params.put(JOB_CITY,city);
                params.put(JOB_STATE,state);
                params.put(JOB_ZIPCODE,zipcode);
                params.put(ESTIMATED_PAYMENT,payout);
                params.put(FLEXIBLE,flexible_status);
                params.put(PAYPAL_FEE,fee);
                params.put(JOB_PAYOUT,payout);
                params.put(FEE_DETAILS,fee_details);
                params.put(JOB_EXPIRE,job_expire);
                params.put(SUB_CATEGORY,sub_category);
                params.put(CATEGORY_COLOR,job_category_color);
                params.put(DELIST,delist);
                params.put(Constant.DEVICE, Constant.ANDROID);
                System.out.println("summaryadd:X-APP-KEY::"+key+"/user_id::"+id+"/job_name::"+name+"/user_type::"+usertype+"/job_category::"+category+"/job_description::"+description+"/job_date::"+date);
                System.out.println("summaryadd:start_time::"+start_time+"/out_of_pocket_expense::"+expense+"/job_payment_type::"+duration+"/job_payment_amount::"+amount+"/address::"+address+"/city::"+city+"/currentlocation::"+current_location);
                System.out.println("summaryadd:state::"+state+"/zipcode::"+zipcode+"/post_address::"+post_address+"/lat::"+latitude+"/lon::"+longitude+"/jobPayout::"+payout+"/job_date_time_flexible::"+flexible_status);
                System.out.println("summaryadd:paypalFee::"+fee+"/fee_details::"+fee_details+"/job_expire::"+job_expire+"/sub_category::"+sub_category+"/job_category_color::"+job_category_color+"/delist::"+delist+"/job_estimated_payment::"+expense);
                return params;
            }

        };
        /*RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);*/
        RetryPolicy policy = new DefaultRetryPolicy(50000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void onResponserecieved(String jsonobject, int requesttype) {
        System.out.println("response from interface"+jsonobject);

        String status = null;

        try {

            JSONObject jResult = new JSONObject(jsonobject);

            status = jResult.getString("status");
            job_id = jResult.getString("job_id");
            System.out.println("jjjjjjjjjjjob:id::"+job_id);

            if(status.equals("success"))
            {
                Intent i = new Intent(SummaryAdd.this,PostedJobs.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.putExtra("userId", id);
                i.putExtra("jobId",job_id);
                i.putExtra("address", address);
                i.putExtra("city", city);
                i.putExtra("state", state);
                i.putExtra("zipcode", zipcode);
                startActivity(i);
                finish();
            }
            else
            {

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    TextWatcher tw = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {

            if (!s.toString().matches("^\\$(\\d{1,3}(\\,\\d{3})*|(\\d+))(\\.\\d{2})?$")) {
                String userInput = "" + s.toString().replaceAll("[^\\d]", "");
                StringBuilder cashAmountBuilder = new StringBuilder(userInput);
                while (cashAmountBuilder.length() > 3 && cashAmountBuilder.charAt(0) == '0') {
                    cashAmountBuilder.deleteCharAt(0);
                }
                while (cashAmountBuilder.length() < 3) {
                    cashAmountBuilder.insert(0, '0');
                }
                cashAmountBuilder.insert(cashAmountBuilder.length() - 2, '.');

                hourly_value.removeTextChangedListener(this);
                hourly_value.setText(cashAmountBuilder.toString());

                hourly_value.setTextKeepState(cashAmountBuilder.toString());
                Selection.setSelection(hourly_value.getText(), cashAmountBuilder.toString().length());

                hourly_value.addTextChangedListener(this);
            }

            String new_pay_amount = hourly_value.getText().toString();
            System.out.println("sssssssssssss::new_pay_amount:"+new_pay_amount);
            String new_hours = expected_value.getText().toString();
            String job_estimated = String.valueOf(Float.valueOf(new_pay_amount)*Float.valueOf(new_hours));
            System.out.println("sssssssssssss:job_estimated:multiply:"+job_estimated);
             String job_pay_value = String.format("%.2f", Float.valueOf(job_estimated));
            job_payout.setText(job_pay_value);

            String s1 = "100";
            String multi = String.valueOf(Float.valueOf(s1)*Float.valueOf(job_estimated));
            System.out.println("sssssssssssss:summary:multi:"+multi);
            String s2 = "130";
            String add1 = String.valueOf(Float.valueOf(multi)+Float.valueOf(s2));
            System.out.println("sssssssssssss:summary:add1:"+add1);
            String s3 = "97.1";
            String div = String.valueOf(Float.valueOf(add1)/Float.valueOf(s3));
            System.out.println("sssssssssssss:summary:div:"+div);
            String pocket_value = String.format("%.2f", Float.valueOf(div));
            System.out.println("sssssssssssss:summary:total1:"+pocket_value);
            String handz_fee = "1.00";
            String pay_fee = String.valueOf(Float.valueOf(pocket_value)-Float.valueOf(job_estimated)-Float.valueOf(handz_fee));
            String total_value = String.format("%.2f", Float.valueOf(pay_fee));
            System.out.println("sssssssssssss:summary:pay_fee:"+pay_fee+"total2:::"+total_value);
            paypal_merchant.setText(total_value);
            pocket_expense.setText(pocket_value);


        }
    };

    TextWatcher tw1 = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {

            if (!s.toString().matches("^\\$(\\d{1,3}(\\,\\d{3})*|(\\d+))(\\.\\d{2})?$")) {
                String userInput = "" + s.toString().replaceAll("[^\\d]", "");
                StringBuilder cashAmountBuilder = new StringBuilder(userInput);
                while (cashAmountBuilder.length() > 3 && cashAmountBuilder.charAt(0) == '0') {
                    cashAmountBuilder.deleteCharAt(0);
                }
                while (cashAmountBuilder.length() < 3) {
                    cashAmountBuilder.insert(0, '0');
                }
                cashAmountBuilder.insert(cashAmountBuilder.length() - 2, '.');

                expected_value.removeTextChangedListener(this);
                expected_value.setText(cashAmountBuilder.toString());

                expected_value.setTextKeepState(cashAmountBuilder.toString());
                Selection.setSelection(expected_value.getText(), cashAmountBuilder.toString().length());

                expected_value.addTextChangedListener(this);
            }
            String new_hours = expected_value.getText().toString();
            System.out.println("sssssssssssss::new_hours:"+new_hours);
            String new_amount = hourly_value.getText().toString();
            String estimated = String.valueOf(Float.valueOf(new_hours)*Float.valueOf(new_amount));
            System.out.println("sssssssssssss:estimated:multiply:"+estimated);
             String job_pay_value = String.format("%.2f", Float.valueOf(estimated));
            job_payout.setText(job_pay_value);

            String s1 = "100";
            String multi = String.valueOf(Float.valueOf(s1)*Float.valueOf(estimated));
            System.out.println("sssssssssssss:summary:multi:"+multi);
            String s2 = "130";
            String add1 = String.valueOf(Float.valueOf(multi)+Float.valueOf(s2));
            System.out.println("sssssssssssss:summary:add1:"+add1);
            String s3 = "97.1";
            String div = String.valueOf(Float.valueOf(add1)/Float.valueOf(s3));
            System.out.println("sssssssssssss:summary:div:"+div);
            String pocket_value = String.format("%.2f", Float.valueOf(div));
            System.out.println("sssssssssssss:summary:total1:"+pocket_value);
            String handz_fee = "1.00";
            String pay_fee = String.valueOf(Float.valueOf(pocket_value)-Float.valueOf(estimated)-Float.valueOf(handz_fee));
            String total_value = String.format("%.2f", Float.valueOf(pay_fee));
            System.out.println("sssssssssssss:summary:pay_fee:"+pay_fee+"total2:::"+total_value);
            paypal_merchant.setText(total_value);
            pocket_expense.setText(pocket_value);

        }
    };

    private void editJob() {
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, EDIT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(Registrationpage3.this,response,Toast.LENGTH_LONG).show();
                        System.out.println("eeeee:" + response);
                        onResponserecieved1(response, 1);
                        dialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        if (error instanceof TimeoutError ||error instanceof NoConnectionError) {
                            final Dialog dialog = new Dialog(SummaryAdd.this);
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
                                    final Dialog dialog = new Dialog(SummaryAdd.this);
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

                            } catch (UnsupportedEncodingException error1) {

                            }
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put(APP_KEY,key);
                params.put(USER_ID,id);
                params.put(JOB_NAME,name);
                params.put(USER_TYPE,usertype);
                params.put(JOB_CATEGORY, category);
                params.put(JOB_DESCRIPTION,description);
                params.put(JOB_DATE,date);
                params.put(JOB_START_DATE,start_time);
                params.put(JOB_END_DATE,start_time);
                params.put(START_TIME,start_time);
                params.put(END_TIME,start_time);
                params.put(JOB_PAYMENT_AMOUNT,amount);
                params.put(POCKET_EXPENSE,expense);
                params.put(JOB_PAYMENT_TYPE,duration);
                params.put(ADDRESS,address);
                params.put(CITY,city);
                params.put(CURRENT_LOCATION,current_location);
                params.put(STATE,state);
                params.put(ZIPCODE,zipcode);
                params.put(POST_ADDRESS,post_address);
                params.put(LATITUDE,latitude);
                params.put(LONGITUDE,longitude);
                params.put(JOB_ADDRESS,address);
                params.put(JOB_CITY,city);
                params.put(JOB_STATE,state);
                params.put(JOB_ZIPCODE,zipcode);
                params.put(ESTIMATED_PAYMENT,payout);
                params.put(FLEXIBLE,flexible_status);
                params.put(PAYPAL_FEE,fee);
                params.put(JOB_PAYOUT,payout);
                params.put(FEE_DETAILS,fee_details);
                params.put(JOB_EXPIRE,job_expire);
                params.put(SUB_CATEGORY,sub_category);
                params.put(CATEGORY_COLOR,job_category_color);
                params.put(DELIST,delist);
                params.put(JOB_ID,job_id);
                params.put(Constant.DEVICE, Constant.ANDROID);
                System.out.println("summaryadd:X-APP-KEY::"+key+"/user_id::"+id+"/job_name::"+name+"/user_type::"+usertype+"/job_category::"+category+"/job_description::"+description+"/job_date::"+date);
                System.out.println("summaryadd:start_time::"+start_time+"/out_of_pocket_expense::"+expense+"/job_payment_type::"+duration+"/job_payment_amount::"+amount+"/address::"+address+"/city::"+city+"/currentlocation::"+current_location);
                System.out.println("summaryadd:state::"+state+"/zipcode::"+zipcode+"/post_address::"+post_address+"/lat::"+latitude+"/lon::"+longitude+"/jobPayout::"+payout+"/job_date_time_flexible::"+flexible_status);
                System.out.println("summaryadd:paypalFee::"+fee+"/fee_details::"+fee_details+"/job_expire::"+job_expire+"/sub_category::"+sub_category+"/job_category_color::"+job_category_color+"/delist::"+delist+"/job_estimated_payment::"+expense);
                return params;
            }
        };

        RetryPolicy policy = new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void onResponserecieved1(String jsonobject, int requesttype) {
        System.out.println("response from interface" + jsonobject);

        String status = null;

        try {

            JSONObject jResult = new JSONObject(jsonobject);

            status = jResult.getString("status");
            job_id = jResult.getString("job_id");
            System.out.println("jjjjjjjjjjjob:id::" + job_id);

            if (status.equals("success")) {

                Intent i = new Intent(SummaryAdd.this, EditPostedJobs.class);
                i.putExtra("userId", id);
                i.putExtra("jobId", job_id);
                i.putExtra("address", address);
                i.putExtra("city", city);
                i.putExtra("state", state);
                i.putExtra("zipcode", zipcode);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
            } else {

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
                j.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(j);
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                finish();
                break;
            case SimpleGestureFilter.SWIPE_LEFT :  str = "Swipe Left";

                Intent  i = new Intent(getApplicationContext(), ProfilePage.class);
                i.putExtra("userId", Profilevalues.user_id);
                i.putExtra("address", Profilevalues.address);
                i.putExtra("city", Profilevalues.city);
                i.putExtra("state", Profilevalues.state);
                i.putExtra("zipcode", Profilevalues.zipcode);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                finish();
                break;
            /*case SimpleGestureFilter.SWIPE_DOWN :  str = "Swipe Down";
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
    public void OnResponseReceived(String s, String s2) {
        merchantid = s;
        UpdatePaypal();
    }
}
