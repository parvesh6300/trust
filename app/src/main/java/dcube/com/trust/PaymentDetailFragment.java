package dcube.com.trust;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import WebServicesHandler.CheckNetConnection;
import WebServicesHandler.GlobalConstants;
import WebServicesHandler.WebServices;
import dcube.com.trust.utils.Global;
import pl.droidsonroids.gif.GifTextView;

/**
 * Created by yadi on 14/10/16.
 */

public class PaymentDetailFragment extends Fragment {

    ViewPager viewPager;
    int nextFragment;

    RadioGroup radio_group_payment,radio_group_payment_mode;
    RadioButton radio_mpesa,radio_cash,radio_insurance;
    RadioButton radio_partial,radio_partial_grant,radio_full;

    GifTextView gif_loader;

    Context context;

    String str_client_id,str_user_id;
    WebServices ws;
    TextView tv_cancel,generate;

    Global global;

    String str_payment_mode = "",str_payment_type = "",str_amount="",str_discount,str_amount_to_pay,str_isFull_paid="0";
    String str_branch,str_discounted_amount,str_response_amount;

    EditText ed_amount,ed_discount;
    float int_discount_per,int_discounted_amount,int_amount_to_pay;
    float total_cost=0;


    CheckNetConnection cn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.paymentdetail, container, false);

        viewPager = (ViewPager) GenerateInvoiceActivity.mInstance.findViewById(R.id.viewPager);

        global = (Global) getActivity().getApplicationContext();

        cn = new CheckNetConnection(getActivity());

        gif_loader = (GifTextView) v.findViewById(R.id.gif_loader);

        context = getActivity();

        nextFragment = viewPager.getCurrentItem() + 2;

        tv_cancel =(TextView)v.findViewById(R.id.tv_cancel);
        generate = (TextView) v.findViewById(R.id.generate);

        ed_amount = (EditText) v.findViewById(R.id.ed_amount);
        ed_discount = (EditText) v.findViewById(R.id.ed_discount);

        radio_group_payment = (RadioGroup) v.findViewById(R.id.radio_group_payment);
        radio_group_payment_mode = (RadioGroup) v.findViewById(R.id.radio_group_payment_mode);

        radio_mpesa = (RadioButton) v.findViewById(R.id.radio_mpesa);
        radio_cash = (RadioButton) v.findViewById(R.id.radio_cash);
        radio_insurance = (RadioButton) v.findViewById(R.id.radio_insurance);

        radio_partial = (RadioButton) v.findViewById(R.id.radio_partial);
        radio_partial_grant = (RadioButton) v.findViewById(R.id.radio_partial_grant);
        radio_full = (RadioButton) v.findViewById(R.id.radio_full);


        str_branch = global.getAl_login_list().get(0).get(GlobalConstants.USER_BRANCH);

        generate.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v)
            {

                if (validate())
                {
                    str_discount = ed_discount.getText().toString();
                    total_cost = Float.parseFloat(ed_amount.getText().toString());

                    if (!(str_discount.equalsIgnoreCase("0.0")  || str_discount.equals(null) || str_discount == null || str_discount.matches("")))
                    {
                        int_discount_per = Float.parseFloat(str_discount);
                        int_discounted_amount = (int_discount_per * total_cost)/100;
                        int_amount_to_pay = total_cost - int_discounted_amount;

                        str_response_amount = String.valueOf(int_amount_to_pay);

                    }
                    else
                    {
                        str_amount_to_pay = ed_amount.getText().toString();

                        int_amount_to_pay = Float.parseFloat(str_amount_to_pay);

                        str_response_amount = String.valueOf(int_amount_to_pay);

                        int_discounted_amount = 0;
                        str_discounted_amount = "0";
                    }


                    if (str_payment_type.equalsIgnoreCase("partial"))
                    {
                        int_amount_to_pay = int_amount_to_pay/2 ;
                    }

                    str_amount_to_pay = String.valueOf(int_amount_to_pay);

                    Log.e("Amount",""+str_amount_to_pay);

                    global.setPayment_amount(str_amount);
                    global.setAmount_to_pay(str_amount_to_pay);
                    global.setDiscount(String.valueOf(int_discounted_amount));
                    global.setPayment_mode(str_payment_mode);


                    if (cn.isNetConnected())
                    {
                        new PaymentAsyncTask().execute();
                    }
                    else {
                        Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });
        

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getActivity().finish();
            }
        });


        radio_group_payment_mode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radio_cash.isChecked())
                {
                    str_payment_mode = "cash";
                }
                else if (radio_mpesa.isChecked())
                {
                    str_payment_mode = "mpesa";
                }
                else if(radio_insurance.isChecked())
                {
                    str_payment_mode = "insurance";
                }
            }
        });


        radio_group_payment.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radio_full.isChecked())
                {
                    str_payment_type = "full";
                    str_isFull_paid = "1";
                }
                else if (radio_partial.isChecked())
                {
                    str_payment_type = "partial";
                    str_isFull_paid = "0";
                }
                else if (radio_partial_grant.isChecked())
                {
                    str_payment_type = "grant";
                    str_isFull_paid = "0";
                }

            }
        });


//        for ( int i =0 ; i< global.getAl_cart_details().size() ; i++)
//        {
//            total_cost = total_cost + Integer.parseInt(global.getAl_cart_details().get(i).get(GlobalConstants.GET_CART_ITEM_PRICE));
//        }



        for ( int i =0 ; i< global.getAl_cart_details().size() ; i++)
        {
            String str_item_type = global.getAl_cart_details().get(i).get(GlobalConstants.CART_ITEM_TYPE);

            if (str_item_type.equalsIgnoreCase("product"))
            {
                int qt =Integer.parseInt(global.getAl_cart_details().get(i).get(GlobalConstants.CART_AMOUNT));
                float each_price = Float.parseFloat(global.getAl_cart_details().get(i).get(GlobalConstants.GET_CART_ITEM_PRICE));

                total_cost = total_cost + (qt*each_price);
            }
            else
            {
                total_cost = total_cost + Float.parseFloat(global.getAl_cart_details().get(i).get(GlobalConstants.GET_CART_ITEM_PRICE));

            }

        }


        ed_amount.setText(String.valueOf(total_cost));
        ed_amount.setFocusable(false);
        ed_amount.setClickable(false);

        str_amount = ed_amount.getText().toString();

        str_client_id = global.getAl_src_client_details().get(global.getSelected_client()).
                get(GlobalConstants.SRC_CLIENT_ID);

        str_user_id = global.getAl_login_list().get(0).get(GlobalConstants.USER_ID);

        return v;
    }



    public static PaymentDetailFragment newInstance(String text) {

        PaymentDetailFragment f = new PaymentDetailFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }



    public class PaymentAsyncTask extends AsyncTask<String, String, String> {


        String resPonse = "";
        String message = "";

        @Override
        protected void onPreExecute() {

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

                al_str_key.add(GlobalConstants.PAYMENT_MODE);
                al_str_value.add(str_payment_mode);

                al_str_key.add(GlobalConstants.PAYMENT_TYPE);
                al_str_value.add(str_payment_type);

                al_str_key.add(GlobalConstants.PAYMENT_AMOUNT);
                al_str_value.add(str_response_amount);

                al_str_key.add(GlobalConstants.BRANCH);
                al_str_value.add(str_branch);

                al_str_key.add(GlobalConstants.ACTION);
                al_str_value.add("payment");

                for (int i=0 ; i< al_str_key.size() ; i++)
                {
                    Log.i("Key",""+al_str_key.get(i));
                    Log.i("Value",""+al_str_value.get(i));
                }

                message = ws.PaymentService(context, al_str_key, al_str_value);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {


            if (message.equalsIgnoreCase("true"))
            {
                new CheckOutAsyncTask().execute();
            }
            else {
                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

    }



    public class CheckOutAsyncTask extends AsyncTask<String, String, String> {

        String message = "";
        String payment_id;

        @Override
        protected void onPreExecute() {

            payment_id = String.valueOf(global.getPayment_id());
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
                al_str_value.add(global.getDiscount());

                al_str_key.add(GlobalConstants.PAYMENT_IS_FULL_PAID);
                al_str_value.add(str_isFull_paid);

                al_str_key.add(GlobalConstants.BRANCH);
                al_str_value.add(str_branch);

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

            gif_loader.setVisibility(View.GONE);

            if (message.equalsIgnoreCase("true"))
            {
                viewPager.setCurrentItem(nextFragment);
            }
            else {
                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

    }



    public boolean validate()
    {
        if (str_payment_mode.matches(""))
        {
            Toast.makeText(getActivity(), "Choose Payment Mode", Toast.LENGTH_SHORT).show();
        }
        else if (str_payment_type.matches(""))
        {
            Toast.makeText(getActivity(), "Choose Payment Type", Toast.LENGTH_SHORT).show();
        }
        else {
            return true;
        }
        return false;
    }

}