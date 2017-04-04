package dcube.com.trust;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import WebServicesHandler.CheckNetConnection;
import WebServicesHandler.GlobalConstants;
import WebServicesHandler.HideKeyboard;
import WebServicesHandler.WebServices;
import dcube.com.trust.utils.Global;
import pl.droidsonroids.gif.GifTextView;

public class GuestDetailActivity extends Activity implements TextWatcher {

    Context context = GuestDetailActivity.this;

    RadioButton radio_retail,radio_institution,radio_name;
    EditText ed_retail_client,ed_institution,ed_name;
    RadioGroup radio_group_client;

    TextView buy;

    Global global;

    GifTextView gif_loader;

    String str_client_id,str_user_id,str_branch;

    String str_buyer_info="",str_buyer_remark="";

    WebServices ws;

    CheckNetConnection cn;

    RelativeLayout rel_parent_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_guest_detail);

        global = (Global) getApplicationContext();

        cn = new CheckNetConnection(context);

        rel_parent_layout = (RelativeLayout) findViewById(R.id.rel_parent_layout);

        buy = (TextView) findViewById(R.id.buy);

        gif_loader = (GifTextView) findViewById(R.id.gif_loader);

        radio_group_client = (RadioGroup) findViewById(R.id.radio_group_client);

        radio_retail = (RadioButton) findViewById(R.id.radio_retail);
        radio_institution = (RadioButton) findViewById(R.id.radio_institution);
        radio_name = (RadioButton) findViewById(R.id.radio_name);

        ed_retail_client = (EditText) findViewById(R.id.ed_retail_client);
        ed_institution = (EditText) findViewById(R.id.ed_institution);
        ed_name = (EditText) findViewById(R.id.ed_name);

        str_branch = global.getAl_login_list().get(0).get(GlobalConstants.USER_BRANCH_NAME);
        str_user_id = global.getAl_login_list().get(0).get(GlobalConstants.USER_ID);

        str_branch = global.getAl_login_list().get(0).get(GlobalConstants.USER_BRANCH_NAME);

        String two_letters = str_branch.substring(0,2).toUpperCase();

        str_client_id = two_letters+"_GUEST";


        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validate())
                {
                    if (cn.isNetConnected())
                    {
                        new CheckOutAsyncTask().execute();
                    }
                    else
                    {
                        Toast.makeText(GuestDetailActivity.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                }

//                startActivity(new Intent(GuestDetailActivity.this,GuestReceiptActivity.class));
//                finish();

            }
        });



        radio_group_client.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radio_retail.isChecked())
                {
                    str_buyer_info = "Retail client";
                    ed_retail_client.setVisibility(View.VISIBLE);
                }
                else
                {
                   // str_buyer_info = "";
                    ed_retail_client.setVisibility(View.INVISIBLE);
                }

                if (radio_institution.isChecked())
                {
                    str_buyer_info = "Institution";
                    ed_institution.setVisibility(View.VISIBLE);
                }
                else
                {
                    //str_buyer_info = "";
                    ed_institution.setVisibility(View.INVISIBLE);
                }

                if (radio_name.isChecked())
                {
                    str_buyer_info = "Name";
                    ed_name.setVisibility(View.VISIBLE);
                }
                else
                {
                    //str_buyer_info = "";
                    ed_name.setVisibility(View.INVISIBLE);
                }


            }
        });


        rel_parent_layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (edittextIsVisible())
                {
                    HideKeyboard.hideSoftKeyboard(GuestDetailActivity.this);
                }

                return false;
            }
        });

        ed_institution.addTextChangedListener(this);
        ed_retail_client.addTextChangedListener(this);
        ed_name.addTextChangedListener(this);

    }


    /**
     * Checks whether user has entered required fields
     * @return boolean
     */

    public boolean validate()
    {

        if (str_buyer_info.equalsIgnoreCase(""))
        {
            Toast.makeText(GuestDetailActivity.this, "Choose One of the Options", Toast.LENGTH_SHORT).show();
        }
        else
        {
            return true;
        }


        return false;
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

        str_buyer_remark = editable.toString();

    }


    /**
     * Hit web service and check out the items present in cart
     */


    public class CheckOutAsyncTask extends AsyncTask<String, String, String> {

        String message = "";
        String payment_id;

        @Override
        protected void onPreExecute() {

            payment_id = String.valueOf(global.getPayment_id());

            gif_loader.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(String... params) {
            try {

                ArrayList<String> al_str_key = new ArrayList<>();
                ArrayList<String> al_str_value = new ArrayList<>();

                al_str_key.add(GlobalConstants.USER_BRANCH_ID);
                al_str_value.add(global.getAl_login_list().get(0).get(GlobalConstants.USER_BRANCH_ID));

                al_str_key.add(GlobalConstants.PAYMENT_CLIENT_ID);
                al_str_value.add(str_client_id);

                al_str_key.add(GlobalConstants.PAYMENT_USER_ID);
                al_str_value.add(str_user_id);

                al_str_key.add(GlobalConstants.PAYMENT_ID);
                al_str_value.add(payment_id);

                al_str_key.add(GlobalConstants.PAYMENT_DISCOUNT);
                al_str_value.add("0");

                al_str_key.add(GlobalConstants.PAYMENT_IS_FULL_PAID);
                al_str_value.add("1");

                al_str_key.add(GlobalConstants.BRANCH);
                al_str_value.add(str_branch);

                al_str_key.add(GlobalConstants.GUEST_BUY_INFO);
                al_str_value.add(str_buyer_info);

                al_str_key.add(GlobalConstants.GUEST_BUY_REMARK);
                al_str_value.add(str_buyer_remark);

                al_str_key.add(GlobalConstants.ACTION);
                al_str_value.add("checkout_in_cart");

                for (int i =0; i<al_str_key.size() ; i++)
                {
                    Log.i("Key",al_str_key.get(i) );
                    Log.i("Value",al_str_value.get(i) );
                }

                message = ws.CheckOutService(context, al_str_key, al_str_value);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            gif_loader.setVisibility(View.INVISIBLE);

            if (message.equalsIgnoreCase("true"))
            {
                buy.setClickable(true);

                startActivity(new Intent(GuestDetailActivity.this,GuestReceiptActivity.class));
                finish();

            }
            else {
                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

    }


    /**
     * User can't go back
     */

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
    }


    public boolean edittextIsVisible()
    {
        if (ed_name.getVisibility() == View.VISIBLE  || ed_retail_client.getVisibility() == View.VISIBLE ||
                ed_institution.getVisibility() == View.VISIBLE)
        {
            return true;
        }


        return false;
    }




}
