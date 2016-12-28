package dcube.com.trust;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

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

    ArrayList<String> al_product_name= new ArrayList<>();
    ArrayList<String> al_product_cost= new ArrayList<>();
    ArrayList<String> al_date= new ArrayList<>();

    Global global;
    GifTextView gif_loader;

    Context context;
    WebServices ws;
    String str_client_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_payment);

        context = this;

        global = (Global) getApplicationContext();

        gif_loader = (GifTextView) findViewById(R.id.gif_loader);

        lv_payment_details=(ListView)findViewById(R.id.lv_payment_details);

        tv_pay=(TextView)findViewById(R.id.tv_pay);

        tv_pending_amount=(TextView)findViewById(R.id.tv_pending_amount);
        tv_paid_amount=(TextView)findViewById(R.id.tv_paid_amount);
        tv_total_cost=(TextView)findViewById(R.id.tv_total_cost);


        tv_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(ViewPendingPaymentActivity.this,GenerateInvoiceActivity.class));
            }
        });


        str_client_id = global.getAl_src_client_details().get(global.getSelected_client()).get(GlobalConstants.SRC_CLIENT_ID);

        new PendingPaymentAsyncTask().execute();

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

                paymentAdapter = new PendingPaymentAdapter(context, al_date, al_product_name, al_product_cost);
                lv_payment_details.setAdapter(paymentAdapter);


                int paid_amount = Integer.parseInt(global.getPendAmountPaid());
                int total_cost = Integer.parseInt(global.getPendTotalCost());
                int pending_amount = total_cost - paid_amount;

                tv_pending_amount.setText(String.valueOf(pending_amount));

            }
            else {

                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();

            }

        }

    }





}
