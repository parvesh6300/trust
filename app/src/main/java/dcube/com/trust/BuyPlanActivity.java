package dcube.com.trust;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import WebServicesHandler.GlobalConstants;
import WebServicesHandler.WebServices;
import dcube.com.trust.utils.Global;
import dcube.com.trust.utils.PlanListAdapter;
import dcube.com.trust.utils.PlanSelectedAdapter;
import okhttp3.OkHttpClient;
import pl.droidsonroids.gif.GifTextView;

public class BuyPlanActivity extends Activity{

    ListView servicelist;
    PlanListAdapter adapter;

    TextView buy;
    GifTextView gif_loader;
    WebServices ws;

    Context context = this;

    Global global;

    CustomDialogClass cdd;

    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_buy_plan);

         global = (Global) getApplicationContext();

        gif_loader = (GifTextView) findViewById(R.id.gif_loader);

        servicelist = (ListView) findViewById(R.id.servicelist);

        buy = (TextView) findViewById(R.id.buy);
        search = (EditText) findViewById(R.id.search);

        if (isOnline()) {
            new GetPlanAsyncTask().execute();
        } else {
            Toast.makeText(BuyPlanActivity.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
        }

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cdd = new CustomDialogClass(BuyPlanActivity.this);
                cdd.show();
            }
        });


        servicelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                adapter.setSelectedIndex(i);
                adapter.notifyDataSetChanged();

            }
        });


        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {

                Log.e("TextWatcherTest", "afterTextChanged:\t" +s.toString());

            }
        });
    }

    public class CustomDialogClass extends Dialog {

        public Activity c;
        int int_product_price = 0;
        int int_service_price = 0;

        public TextView cancel,tv_product_cost;
        public TextView confirm,tv_service_cost;

        public ListView selected;

        PlanSelectedAdapter selectedAdapter;

        public CustomDialogClass(Activity a) {
            super(a);
            // TODO Auto-generated constructor stub
            this.c = a;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            requestWindowFeature(Window.FEATURE_NO_TITLE);

            setContentView(R.layout.buyplan_dialog);

            confirm = (TextView) findViewById(R.id.confirm);
            cancel = (TextView) findViewById(R.id.cancel);
            tv_product_cost = (TextView) findViewById(R.id.tv_product_cost);
            tv_service_cost = (TextView) findViewById(R.id.tv_service_cost);

            selected = (ListView) findViewById(R.id.selected_product_list);

            selectedAdapter = new PlanSelectedAdapter(context);
            selected.setAdapter(selectedAdapter);

            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (isOnline())
                    {
                        new AddPlanCartAsyncTask().execute();
                    }
                    else {
                        Toast.makeText(BuyPlanActivity.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }


                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    dismiss();
                }
            });


            for (int count = 0 ; count < global.getAl_selected_plan().size() ; count++)
            {
                int_product_price = int_product_price +
                        Integer.parseInt(global.getAl_selected_plan().get(count).get(GlobalConstants.PLAN_PRODUCT_PRICE));

                int_service_price = int_service_price +
                        Integer.parseInt(global.getAl_selected_plan().get(count).get(GlobalConstants.PLAN_SERVICE_PRICE));
            }

            tv_product_cost.setText(String.valueOf(int_product_price));
            tv_service_cost.setText(String.valueOf(int_service_price));

        }

    }


    public class GetPlanAsyncTask extends AsyncTask<String, String, String> {

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

                al_str_key.add(GlobalConstants.ACTION);
                al_str_value.add("get_plan_list");

                message = ws.GetPlanService(context, al_str_key, al_str_value);

                //            resPonse = callApiWithPerameter(GlobalConstants.TRUST_URL, al_str_key, al_str_value);
                //             Log.i("Login", "Login : " + resPonse);

//                return resPonse;

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
                adapter = new PlanListAdapter(BuyPlanActivity.this);
                servicelist.setAdapter(adapter);

            }
            else {

                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

    }

    protected boolean isOnline() {

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected())
        {
            return true;
        }
        else {
            return false;
        }
    }


    public class AddPlanCartAsyncTask extends AsyncTask<String, String, String> {

        OkHttpClient httpClient = new OkHttpClient();
        String resPonse = "";
        String message = "";
        String str_client_id;

        @Override
        protected void onPreExecute() {

            gif_loader.setVisibility(View.VISIBLE);

            str_client_id = global.getAl_src_client_details().get(global.getSelected_client()).
                    get(GlobalConstants.SRC_CLIENT_ID);
        }

        @Override
        protected String doInBackground(String... params) {
            try {

                for (int i = 0 ; i < global.al_selected_plan_id.size() ; i++) {
                    ArrayList<String> al_str_key = new ArrayList<>();
                    ArrayList<String> al_str_value = new ArrayList<>();

                    al_str_key.add(GlobalConstants.CART_CLIENT_ID);
                    al_str_value.add(str_client_id);

                    al_str_key.add(GlobalConstants.CART_ITEM_TYPE);
                    al_str_value.add("plan");

                    al_str_key.add(GlobalConstants.CART_ITEM_ID);
                    al_str_value.add(global.al_selected_plan_id.get(i));

                    al_str_key.add(GlobalConstants.CART_AMOUNT);
                    al_str_value.add("1");

                    al_str_key.add(GlobalConstants.ACTION);
                    al_str_value.add("add_to_cart");

                    for (int j = 0; j < al_str_key.size(); j++) {
                        Log.e("KEy", "" + al_str_key.get(j));
                        Log.e("Value", "" + al_str_value.get(j));
                    }

                    message = ws.CartService(context, al_str_key, al_str_value);

                }

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
                cdd.dismiss();
                startActivity(new Intent(BuyPlanActivity.this,GenerateInvoiceActivity.class));
            }
            else
            {
                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

    }


}
