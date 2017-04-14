package dcube.com.trust;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import WebServicesHandler.CheckNetConnection;
import WebServicesHandler.GlobalConstants;
import WebServicesHandler.WebServices;
import dcube.com.trust.utils.Global;
import dcube.com.trust.utils.PendingPaymentAdapter;
import okhttp3.OkHttpClient;
import pl.droidsonroids.gif.GifTextView;

public class ViewPendingPaymentActivity extends Activity {


    TextView tv_pay,tv_pending_amount,tv_paid_amount,tv_total_cost;
    ListView lv_payment_details;
    PendingPaymentAdapter paymentAdapter;

    Global global;
    GifTextView gif_loader;

    Context context;
    WebServices ws;
    String str_client_id,str_user_id;
    String str_amount_to_pay;
    String str_payment_mode="";
    String str_branch;

    CustomDialogClass cdd;

    CheckNetConnection cn;

    int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_payment);

        context = this;

        cn = new CheckNetConnection(this);

        global = (Global) getApplicationContext();

        gif_loader = (GifTextView) findViewById(R.id.gif_loader);

        lv_payment_details=(ListView)findViewById(R.id.lv_payment_details);

        tv_pay=(TextView)findViewById(R.id.tv_pay);

        tv_pending_amount=(TextView)findViewById(R.id.tv_pending_amount);
        tv_paid_amount=(TextView)findViewById(R.id.tv_paid_amount);
        tv_total_cost=(TextView)findViewById(R.id.tv_total_cost);

        str_branch = global.getAl_login_list().get(0).get(GlobalConstants.USER_BRANCH);


        tv_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                global.setPendingPayment(true);

                global.setPayment_amount(str_amount_to_pay);
                global.setAmount_to_pay(str_amount_to_pay);
                global.setDiscount(String.valueOf(0));
                global.setPayment_mode(str_payment_mode);

                try{

                    if (global.getAl_pend_pmt_details().size() > 0)
                    {
                        if (cn.isNetConnected())
                        {
                            cdd = new CustomDialogClass(ViewPendingPaymentActivity.this);
                            cdd.show();
                         //   new ClearPendingPaymentAsyncTask().execute();
                        }
                        else
                        {
                            Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                        }

                    }

                }
                catch (Exception e)
                {
                    Log.i("Exception","Occurs");
                }

            }
        });


        str_client_id = global.getAl_src_client_details().get(global.getSelected_client()).get(GlobalConstants.SRC_CLIENT_ID);

        str_user_id = global.getAl_login_list().get(0).get(GlobalConstants.USER_ID);


        if (cn.isNetConnected())
        {
            new PendingPaymentAsyncTask().execute();
        }
        else {
            Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
        }

    }


    /**
     * Hit the service and do pending payment
     */


    public class PendingPaymentAsyncTask extends AsyncTask<String, String, String> {

        OkHttpClient httpClient = new OkHttpClient();
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

                al_str_key.add(GlobalConstants.CART_CLIENT_ID);
                al_str_value.add(str_client_id);

                al_str_key.add(GlobalConstants.ACTION);
                al_str_value.add("pending_payment");

                message = ws.PendingPaymentService(context, al_str_key, al_str_value);


            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            gif_loader.setVisibility(View.INVISIBLE);

            if (message.equalsIgnoreCase("true")) {

                tv_paid_amount.setText(global.getPendAmountPaid());
                tv_total_cost.setText(global.getPendTotalCost());

                paymentAdapter = new PendingPaymentAdapter(context);
                lv_payment_details.setAdapter(paymentAdapter);

//                int paid_amount = Integer.parseInt(global.getPendAmountPaid());
//                int total_cost = Integer.parseInt(global.getPendTotalCost());
//                int pending_amount = total_cost - paid_amount;
//
//                str_amount_to_pay = String.valueOf(pending_amount);
//
//                tv_pending_amount.setText(str_amount_to_pay);

                float f_paid_amount = Float.parseFloat(global.getPendAmountPaid());
                float f_total_cost = Float.parseFloat(global.getPendTotalCost());
                float f_pending_amount = f_total_cost - f_paid_amount;

                str_amount_to_pay  = String.valueOf(f_pending_amount);

                tv_pending_amount.setText(str_amount_to_pay);

            }
            else {

                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();

            }

        }

    }



    /**
     * Hit the service and clear pending payment
     */

    public class ClearPendingPaymentAsyncTask extends AsyncTask<String, String, String> {

        String resPonse = "";
        String message = "";
        String order_id;

        @Override
        protected void onPreExecute() {

            gif_loader.setVisibility(View.VISIBLE);

            order_id = global.getAl_order_id().get(count);

            global.setPayment_mode(str_payment_mode);
        }

        @Override
        protected String doInBackground(String... params) {
            try {

                ArrayList<String> al_str_key = new ArrayList<>();
                ArrayList<String> al_str_value = new ArrayList<>();

                al_str_key.add(GlobalConstants.PAYMENT_CLIENT_ID);
                al_str_value.add(str_client_id);

                al_str_key.add(GlobalConstants.USER_BRANCH_ID);
                al_str_value.add(global.getAl_login_list().get(0).get(GlobalConstants.USER_BRANCH_ID));

                al_str_key.add(GlobalConstants.PAYMENT_MODE);
                al_str_value.add(str_payment_mode);

                al_str_key.add(GlobalConstants.PEND_AMOUNT);
                al_str_value.add(str_amount_to_pay);
//
//                al_str_key.add(GlobalConstants.BRANCH);
//                al_str_value.add(str_branch);

                al_str_key.add(GlobalConstants.ACTION);
                al_str_value.add("clear_pending_payment");

                Log.i("Key",""+al_str_key);
                Log.i("Value",""+al_str_value);

                message = ws.PaymentService(context, al_str_key, al_str_value);

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
                startActivity(new Intent(ViewPendingPaymentActivity.this,GenerateInvoiceActivity.class));
                finish();
            }
            else {
                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

    }


    /**
     * Custom Confirmation Dialog
     */

    public class CustomDialogClass extends Dialog {

        public Activity c;

        RadioGroup rg_payment_mode;
        RadioButton radio_cash,radio_mpesa;

        public TextView cancel;
        public TextView confirm,tv_message;

        public CustomDialogClass(Activity a) {
            super(a);
            // TODO Auto-generated constructor stub
            this.c = a;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            requestWindowFeature(Window.FEATURE_NO_TITLE);

            setContentView(R.layout.choose_payment_mode_dialog);  //confirm_clear_pend_pay_dialog

            confirm = (TextView) findViewById(R.id.tv_yes);
            cancel = (TextView) findViewById(R.id.tv_no);
            tv_message = (TextView) findViewById(R.id.tv_message);

            rg_payment_mode = (RadioGroup) findViewById(R.id.rg_payment_mode);

            radio_cash = (RadioButton) findViewById(R.id.radio_cash);
            radio_mpesa = (RadioButton) findViewById(R.id.radio_mpesa);

            tv_message.setText("Your Pending Amount is: "+str_amount_to_pay+" TZS");


            rg_payment_mode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                    if (checkedId == radio_cash.getId())
                    {
                        str_payment_mode = "cash";
                    }
                    else if (checkedId == radio_mpesa.getId())
                    {
                        str_payment_mode = "mpesa";
                    }


                }
            });

            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (str_payment_mode.equalsIgnoreCase(""))
                    {
                        Toast.makeText(c, "Choose Payment Mode", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        dismiss();
                        new ClearPendingPaymentAsyncTask().execute();
                    }

                  //  context.startActivity(new Intent(ViewPendingPaymentActivity.this,GenerateInvoiceActivity.class));
                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    dismiss();
                }
            });
        }
    }



}
