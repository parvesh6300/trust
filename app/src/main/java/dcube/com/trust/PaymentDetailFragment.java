package dcube.com.trust;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
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

/**
 * Created by Rohit on 14/10/16.
 */

public class PaymentDetailFragment extends Fragment {

    ViewPager viewPager;
    int nextFragment;

    RadioGroup radio_group_payment, radio_group_payment_mode;
    RadioButton radio_mpesa, radio_cash, radio_insurance;
    RadioButton radio_partial, radio_partial_grant, radio_full;

    GifTextView gif_loader;

    Context context;

    String str_client_id, str_user_id;
    WebServices ws;
    TextView tv_cancel, generate;

    Global global;

    String str_payment_mode = "", str_payment_type = "", str_amount = "", str_discount="0",  str_isFull_paid = "0";
    String str_branch, str_response_amount;

    EditText ed_amount, ed_discount, ed_partial_amount, ed_payable_amount,ed_discount_rsn;
    float total_cost = 0;

    RelativeLayout rel_partial_layout,rel_parent_layout;
    LinearLayout layout_payment;

    CheckNetConnection cn;

    RelativeLayout rel_rsn_layout;

    String str_total_cost,str_discount_rsn="",str_amount_paid,str_amount_left;


    public static PaymentDetailFragment newInstance(String text) {

        PaymentDetailFragment f = new PaymentDetailFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.paymentdetail, container, false);

        viewPager = (ViewPager) GenerateInvoiceActivity.mInstance.findViewById(R.id.viewPager);

        global = (Global) getActivity().getApplicationContext();

        cn = new CheckNetConnection(getActivity());

        gif_loader = (GifTextView) v.findViewById(R.id.gif_loader);

        context = getActivity();

        nextFragment = viewPager.getCurrentItem() + 2;

        tv_cancel = (TextView) v.findViewById(R.id.tv_cancel);
        generate = (TextView) v.findViewById(R.id.generate);

        ed_amount = (EditText) v.findViewById(R.id.ed_amount);
        ed_discount = (EditText) v.findViewById(R.id.ed_discount);
        ed_partial_amount = (EditText) v.findViewById(R.id.ed_partial_amount);
        ed_payable_amount = (EditText) v.findViewById(R.id.ed_payable_amount);
        ed_discount_rsn = (EditText) v.findViewById(R.id.ed_discount_rsn);

        radio_group_payment = (RadioGroup) v.findViewById(R.id.radio_group_payment);
        radio_group_payment_mode = (RadioGroup) v.findViewById(R.id.radio_group_payment_mode);

        radio_mpesa = (RadioButton) v.findViewById(R.id.radio_mpesa);
        radio_cash = (RadioButton) v.findViewById(R.id.radio_cash);
        radio_insurance = (RadioButton) v.findViewById(R.id.radio_insurance);

        radio_partial = (RadioButton) v.findViewById(R.id.radio_partial);
        radio_partial_grant = (RadioButton) v.findViewById(R.id.radio_partial_grant);
        radio_full = (RadioButton) v.findViewById(R.id.radio_full);

        rel_parent_layout = (RelativeLayout) v.findViewById(R.id.rel_parent_layout);
        rel_partial_layout = (RelativeLayout) v.findViewById(R.id.rel_partial_layout);
        rel_rsn_layout = (RelativeLayout) v.findViewById(R.id.rel_rsn_layout);

        layout_payment = (LinearLayout)v.findViewById(R.id.layout_payment);

        str_branch = global.getAl_login_list().get(0).get(GlobalConstants.USER_BRANCH);

        generate.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                if (validate())
                {
                    str_total_cost = ed_amount.getText().toString();

                    str_response_amount = ed_payable_amount.getText().toString();

                    float f_total_amount = Float.parseFloat(ed_amount.getText().toString());
                    float f_payable_amount = Float.parseFloat(ed_payable_amount.getText().toString());
                    float f_discount = 0f;
                    float f_amount_paying = 0f;
                    float f_amount_left = 0f;

                    if (f_total_amount != f_payable_amount)
                    {
                        if (ed_discount_rsn.getText().toString().matches(""))
                        {
                            Toast.makeText(getActivity(), "Specify Discount Reason", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            str_discount = ed_discount.getText().toString();
                            str_discount_rsn = ed_discount_rsn.getText().toString();

                            f_discount = f_total_amount - f_payable_amount;

                            if (radio_full.isChecked())
                            {
                                f_amount_paying = f_payable_amount;
                                str_amount_left = "0";
                            }

                            else
                            {
                                f_amount_paying = Float.parseFloat(ed_partial_amount.getText().toString());
                                f_amount_left = f_payable_amount - f_amount_paying;
                                str_amount_left = String.valueOf(f_amount_left);
                            }


                            str_amount_paid = String.valueOf(f_amount_paying);

                            global.setPayment_amount(str_response_amount);
                            global.setDiscount(String.valueOf(f_discount));
                            global.setAmount_to_pay(String.valueOf(f_amount_paying));
                            global.setPayment_mode(str_payment_mode);

                            if (cn.isNetConnected())
                            {
                                new PaymentAsyncTask().execute();

                            } else
                            {
                                Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                    else
                    {
                        if (radio_full.isChecked())
                        {
                            f_amount_paying = f_payable_amount;
                            str_amount_left = "0";
                        }
                        else
                        {
                            f_amount_paying = Float.parseFloat(ed_partial_amount.getText().toString());
                            f_amount_left = f_payable_amount - f_amount_paying;
                            str_amount_left = String.valueOf(f_amount_left);
                        }


                        str_amount_paid = String.valueOf(f_amount_paying);

                        global.setPayment_amount(str_response_amount);
                        global.setDiscount(String.valueOf(f_discount));
                        global.setAmount_to_pay(String.valueOf(f_amount_paying));
                        global.setPayment_mode(str_payment_mode);

                        if (cn.isNetConnected())
                        {
                            new PaymentAsyncTask().execute();
                        }
                        else
                        {
                            Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                        }
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

                if (radio_cash.isChecked()) {
                    str_payment_mode = "cash";
                } else if (radio_mpesa.isChecked()) {
                    str_payment_mode = "mpesa";
                } else if (radio_insurance.isChecked()) {
                    str_payment_mode = "insurance";
                }
            }
        });


        radio_group_payment.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radio_partial.isChecked() || radio_partial_grant.isChecked())
                {
                    rel_partial_layout.setVisibility(View.VISIBLE);

                } else {
                    rel_partial_layout.setVisibility(View.INVISIBLE);

                }

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


        for (int i = 0; i < global.getAl_cart_details().size(); i++) {
            String str_item_type = global.getAl_cart_details().get(i).get(GlobalConstants.CART_ITEM_TYPE);

            if (str_item_type.equalsIgnoreCase("product"))
            {
                int qt = Integer.parseInt(global.getAl_cart_details().get(i).get(GlobalConstants.CART_AMOUNT));
                float each_price = Float.parseFloat(global.getAl_cart_details().get(i).get(GlobalConstants.GET_CART_ITEM_PRICE));

                total_cost = total_cost + (qt * each_price);
            }
            else
            {
                total_cost = total_cost + Float.parseFloat(global.getAl_cart_details().get(i).get(GlobalConstants.GET_CART_ITEM_PRICE));
            }

        }


        ed_amount.setText(String.valueOf(total_cost));
        ed_payable_amount.setText(String.valueOf(total_cost));
        ed_amount.setFocusable(false);
        ed_amount.setClickable(false);

        str_amount = ed_amount.getText().toString();

        str_client_id = global.getAl_src_client_details().get(global.getSelected_client()).
                get(GlobalConstants.SRC_CLIENT_ID);

        str_user_id = global.getAl_login_list().get(0).get(GlobalConstants.USER_ID);


        ed_discount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.toString().length() > 0)
                {
                    rel_rsn_layout.setVisibility(View.VISIBLE);
                    float f_discount = Float.parseFloat(editable.toString());
                    float f_total_cost = Float.parseFloat(ed_amount.getText().toString());

                    float f_discounted_amount = (f_discount * f_total_cost) / 100;
                    float f_amount_to_pay = f_total_cost - f_discounted_amount;

                    ed_payable_amount.setText(String.valueOf(f_amount_to_pay));


                } else {
                    rel_rsn_layout.setVisibility(View.GONE);
                    ed_payable_amount.setText(String.valueOf(ed_amount.getText().toString()));
                }


            }
        });



        rel_parent_layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                HideKeyboard.hideSoftKeyboard(getActivity());

                return false;
            }
        });



        return v;
    }

    /**
     * Check whether required fields are filled or not
     * @return boolean
     */


    public boolean validate() {

        float f_amount = Float.parseFloat(ed_amount.getText().toString());
        float f_payable = Float.parseFloat(ed_payable_amount.getText().toString());


        if (str_payment_mode.matches(""))
        {
            Toast.makeText(getActivity(), "Choose Payment Mode", Toast.LENGTH_SHORT).show();
        }
        else if (str_payment_type.matches(""))
        {
            Toast.makeText(getActivity(), "Choose Payment Type", Toast.LENGTH_SHORT).show();
        }
//        else if(f_amount != f_payable)
//        {
//            if (ed_discount_rsn.getText().toString().matches(""))
//            {
//                Toast.makeText(getActivity(), "Specify Discount Reason", Toast.LENGTH_SHORT).show();
//            }
//        }
        else if (radio_partial_grant.isChecked() || radio_partial.isChecked() )
        {
            if (ed_partial_amount.getText().toString().matches(""))
            {
                Toast.makeText(context, "Enter Amount you wanna Pay ", Toast.LENGTH_SHORT).show();
            }
            else
            {
                return true;
            }

        }
        else
        {

//            if (f_amount != f_payable)
//            {
//                if (ed_discount_rsn.getText().toString().matches(""))
//                {
//                    Toast.makeText(getActivity(), "Specify Discount Reason", Toast.LENGTH_SHORT).show();
//                }
//                else
//                {
//
//                }
//            }


            return true;
        }

        return false;
    }


    /**
     * Hit the payment service
     */

    private class PaymentAsyncTask extends AsyncTask<String, String, String> {

        String resPonse = "";
        String message = "";

        @Override
        protected void onPreExecute() {

            gif_loader.setVisibility(View.VISIBLE);

            generate.setClickable(false);

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
                al_str_value.add(str_response_amount);   // Amount after discount

                al_str_key.add(GlobalConstants.PAYMENT_FUlL_COST);
                al_str_value.add(str_total_cost);   //   Total cost

                al_str_key.add(GlobalConstants.PAYMENT_DISCOUNT_PER);
                al_str_value.add(str_discount);     // Discount Percentage

                al_str_key.add(GlobalConstants.PAYMENT_DISCOUNT_RSN);
                al_str_value.add(str_discount_rsn);  // Discount Reason

                al_str_key.add(GlobalConstants.PAYMENT_AMOUNT_PAID);
                al_str_value.add(str_amount_paid);  //  Amount paying by the client

                al_str_key.add(GlobalConstants.PAYMENT_AMOUNT_LEFT);
                al_str_value.add(str_amount_left);  // Amount left to pay

                al_str_key.add(GlobalConstants.BRANCH);
                al_str_value.add(str_branch);

                al_str_key.add(GlobalConstants.ACTION);
                al_str_value.add("payment");

                for (int i = 0; i < al_str_key.size(); i++)
                {
                    Log.i("Key", "" + al_str_key.get(i));
                    Log.i("Value", "" + al_str_value.get(i));
                }

                message = ws.PaymentService(context, al_str_key, al_str_value);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            if (message.equalsIgnoreCase("true")) {
                new CheckOutAsyncTask().execute();
            } else {
                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

    }

    /**
     * Hit the service and check out the items in cart
     */

    private class CheckOutAsyncTask extends AsyncTask<String, String, String> {

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

                for (int i = 0; i < al_str_key.size(); i++) {
                    Log.i("Key", al_str_key.get(i));
                    Log.i("Value", al_str_value.get(i));
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

            generate.setClickable(true);

            if (message.equalsIgnoreCase("true"))
            {
                viewPager.setCurrentItem(nextFragment);
            }
            else
            {
                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

    }

}