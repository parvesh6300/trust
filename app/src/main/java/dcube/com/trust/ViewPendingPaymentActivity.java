package dcube.com.trust;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
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
    String str_payment_mode = "cash";
    String str_branch;

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

                if (cn.isNetConnected())
                {
                    for (int i =0 ; i < global.getAl_order_id().size() ; i++ )
                    {
                        count = i;
                        new PaymentAsyncTask().execute();
                    }
                }
                else
                {
                    Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
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

            gif_loader.setVisibility(View.GONE);

            if (message.equalsIgnoreCase("true")) {

                tv_paid_amount.setText(global.getPendAmountPaid());
                tv_total_cost.setText(global.getPendTotalCost());

                paymentAdapter = new PendingPaymentAdapter(context);
                lv_payment_details.setAdapter(paymentAdapter);

                int paid_amount = Integer.parseInt(global.getPendAmountPaid());
                int total_cost = Integer.parseInt(global.getPendTotalCost());
                int pending_amount = total_cost - paid_amount;

                str_amount_to_pay = String.valueOf(pending_amount);

                tv_pending_amount.setText(str_amount_to_pay);

            }
            else {

                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();

            }

        }

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

                al_str_key.add(GlobalConstants.PAYMENT_CLIENT_ID);
                al_str_value.add(str_client_id);

                al_str_key.add(GlobalConstants.PAYMENT_USER_ID);
                al_str_value.add(str_user_id);

                al_str_key.add(GlobalConstants.PAYMENT_MODE);
                al_str_value.add("");

                al_str_key.add(GlobalConstants.PAYMENT_TYPE);
                al_str_value.add("");

                al_str_key.add(GlobalConstants.PAYMENT_AMOUNT);
                al_str_value.add(str_amount_to_pay);

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
                Log.i("Message",message);
                new ClearPendingPaymentAsyncTask().execute();
            }
            else {
                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

    }


    public class ClearPendingPaymentAsyncTask extends AsyncTask<String, String, String> {

        String resPonse = "";
        String message = "";
        String order_id;

        @Override
        protected void onPreExecute() {

            gif_loader.setVisibility(View.VISIBLE);

            order_id = global.getAl_order_id().get(count);
        }

        @Override
        protected String doInBackground(String... params) {
            try {

                ArrayList<String> al_str_key = new ArrayList<>();
                ArrayList<String> al_str_value = new ArrayList<>();

                al_str_key.add(GlobalConstants.PAYMENT_ID);
                al_str_value.add(str_client_id);

                al_str_key.add(GlobalConstants.PEND_ORDER_ID);
                al_str_value.add(order_id);

                al_str_key.add(GlobalConstants.BRANCH);
                al_str_value.add(str_branch);

                al_str_key.add(GlobalConstants.ACTION);
                al_str_value.add("clear_pending_payment");

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
//                startActivity(new Intent(ViewPendingPaymentActivity.this,GenerateInvoiceActivity.class));
//                finish();
            }
            else {
                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

    }




}
