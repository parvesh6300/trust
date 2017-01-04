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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import WebServicesHandler.CheckNetConnection;
import WebServicesHandler.GlobalConstants;
import WebServicesHandler.WebServices;
import dcube.com.trust.utils.Global;
import dcube.com.trust.utils.ServiceListAdapter;
import dcube.com.trust.utils.ServiceSelectedAdapter;
import okhttp3.OkHttpClient;
import pl.droidsonroids.gif.GifTextView;

public class BuyServicesActivity extends Activity {

    ListView servicelist;
    ServiceListAdapter adapter;
    GifTextView gif_loader;
    TextView buy;

    Global global;

    EditText search;
    WebServices ws;
    Context context = BuyServicesActivity.this;
    CustomDialogClass cdd;

    String str_client_id;
    CheckNetConnection cn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_services);

        global = (Global) getApplicationContext();

        cn = new CheckNetConnection(this);

        servicelist = (ListView) findViewById(R.id.servicelist);

        buy = (TextView) findViewById(R.id.buy);
        search = (EditText) findViewById(R.id.search);

        gif_loader = (GifTextView) findViewById(R.id.gif_loader);

        cdd = new CustomDialogClass(BuyServicesActivity.this);


        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try
                {
                    if (global.getAl_selected_service_id().size() >0)
                    {
                        cdd.show();
                    }
                    else
                    {
                        Toast.makeText(BuyServicesActivity.this, "Chose any one Service", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e)
                {
                    Toast.makeText(BuyServicesActivity.this, "Chose any one Service", Toast.LENGTH_SHORT).show();
                }


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

                if (s.length() > 1)
                {
                    adapter = new ServiceListAdapter(BuyServicesActivity.this, s.toString());
                    servicelist.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                else
                {
                    adapter = new ServiceListAdapter(BuyServicesActivity.this, "");
                    servicelist.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }


            }
        });


        if (cn.isNetConnected())
        {
            new GetServiceAsyncTask().execute();
        }
        else {
            Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
        }


        str_client_id = global.getAl_src_client_details().get(global.getSelected_client()).get(GlobalConstants.SRC_CLIENT_ID);

    }



    public class CustomDialogClass extends Dialog {

        public Activity c;

        public TextView cancel;
        public TextView confirm,tv_service_cost;

        public ListView selected;

        ServiceSelectedAdapter selectedAdapter;

        int int_service_price = 0;

        public CustomDialogClass(Activity a) {
            super(a);
            // TODO Auto-generated constructor stub
            this.c = a;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);

            setContentView(R.layout.buy_service_dialog);

            confirm = (TextView) findViewById(R.id.confirm);
            cancel = (TextView) findViewById(R.id.cancel);
            tv_service_cost = (TextView) findViewById(R.id.tv_service_cost);
            selected = (ListView) findViewById(R.id.selected_product_list);

            selectedAdapter = new ServiceSelectedAdapter(BuyServicesActivity.this);
            selected.setAdapter(selectedAdapter);

            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (cn.isNetConnected())
                    {
                        dismiss();
                        new AddServiceCartAsyncTask().execute();
                    }
                    else {
                        Toast.makeText(BuyServicesActivity.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }

                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    dismiss();
                }
            });

            for (int count = 0 ; count < global.getAl_selected_service().size() ; count++)
            {
                 int_service_price = int_service_price +
                        Integer.parseInt(global.getAl_selected_service().get(count).get(GlobalConstants.SERVICE_PRICE));
            }

            tv_service_cost.setText(String.valueOf(int_service_price));

        }
    }


    public class GetServiceAsyncTask extends AsyncTask<String, String, String> {

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
                al_str_value.add("get_service_list");

                message = ws.GetServiceService(context, al_str_key, al_str_value);

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

            if (!message.equalsIgnoreCase("true"))
            {
                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }
            else {

                adapter = new ServiceListAdapter(BuyServicesActivity.this,"");
                servicelist.setAdapter(adapter);
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



    public class AddServiceCartAsyncTask extends AsyncTask<String, String, String> {

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

                for (int j = 0 ; j < global.getAl_selected_service_id().size() ; j++)
                {
                    ArrayList<String> al_str_key = new ArrayList<>();
                    ArrayList<String> al_str_value = new ArrayList<>();

                    al_str_key.add(GlobalConstants.CART_CLIENT_ID);
                    al_str_value.add(str_client_id);

                    al_str_key.add(GlobalConstants.CART_ITEM_TYPE);
                    al_str_value.add("service");

                    al_str_key.add(GlobalConstants.CART_ITEM_ID);
                    al_str_value.add(global.getAl_selected_service_id().get(j));

                    al_str_key.add(GlobalConstants.CART_AMOUNT);
                    al_str_value.add("1");

                    al_str_key.add(GlobalConstants.ACTION);
                    al_str_value.add("add_to_cart");

                    for (int i = 0 ; i < al_str_key.size(); i++)
                    {
                        Log.e("Key",""+al_str_key.get(i));
                        Log.e("Value",""+al_str_value.get(i));
                    }

                    message = ws.AddToCartService(context, al_str_key, al_str_value);

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
                ClientHomeActivity.h.sendEmptyMessage(0);
                startActivity(new Intent(BuyServicesActivity.this,ClientHomeActivity.class));
                finish();
            }
            else
            {
                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

    }



}
