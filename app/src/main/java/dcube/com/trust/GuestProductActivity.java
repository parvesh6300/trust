package dcube.com.trust;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import dcube.com.trust.utils.GuestProductListAdapter;
import dcube.com.trust.utils.GuestProductSelectedAdapter;
import okhttp3.OkHttpClient;
import pl.droidsonroids.gif.GifTextView;

public class GuestProductActivity extends Activity {

    ListView productlist;
    GuestProductListAdapter adapter;
    TextView buy;

    GifTextView gif_loader;
    Context context = GuestProductActivity.this;

    WebServices ws;
    EditText search;

    CustomDialogClass cdd;
    Global global;

    String str_branch;

    ArrayList<String> selected_quantity = new ArrayList<>();

    String str_client_id = "guest",str_user_id,str_amount_to_pay;

    CheckNetConnection cn;

    public static Handler h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        global = (Global) getApplicationContext();

        productlist = (ListView) findViewById(R.id.productlist);

        gif_loader = (GifTextView) findViewById(R.id.gif_loader);

        buy = (TextView) findViewById(R.id.buy);

        search = (EditText) findViewById(R.id.search);

        cn = new CheckNetConnection(this);

        str_user_id = global.getAl_login_list().get(0).get(GlobalConstants.USER_ID);

        str_branch = global.getAl_login_list().get(0).get(GlobalConstants.USER_BRANCH_NAME);

        String two_letters = str_branch.substring(0,2).toUpperCase();

        str_client_id = two_letters+"_GUEST";


        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    if (global.getAl_select_product().size() > 0)
                    {
                        cdd = new CustomDialogClass(GuestProductActivity.this);
                        cdd.show();
                    }
                    else
                    {
                        Toast.makeText(GuestProductActivity.this, "Chose any one Product", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Toast.makeText(GuestProductActivity.this, "Some Error occured", Toast.LENGTH_SHORT).show();
                }


            }
        });


        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }
            @Override
            public void afterTextChanged(Editable s) {

                Log.e("TextWatcherTest", "afterTextChanged:\t" +s.toString());

                if(s.length() > 1)
                {
                    adapter = new GuestProductListAdapter(GuestProductActivity.this,s.toString());
                    productlist.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                else
                {
                    adapter = new GuestProductListAdapter(GuestProductActivity.this,"");
                    productlist.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }

            }
        });



        if (cn.isNetConnected())
        {
            new GetPruductAsyncTask().execute();
        }
        else {
            Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
        }

        try {

            global.getAl_select_product().clear();
            global.getAl_selected_product_quantity().clear();
            global.getAl_selected_product_category().clear();
            global.getAl_selected_product_price().clear();
            global.getAl_selected_product_sku().clear();
            global.getAl_selected_product_name().clear();

        } catch (Exception e)
        {

        }

        h = new Handler() {

            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                switch(msg.what) {

                    case 0:
                        finish();
                        break;

                }
            }

        };



    }



    public class GetPruductAsyncTask extends AsyncTask<String, String, String> {

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

                al_str_key.add(GlobalConstants.ACTION);
                al_str_value.add("get_product_list");

                Log.i("Key",""+al_str_key);
                Log.i("Value",""+al_str_value);

                message = ws.GetProductService(context, al_str_key, al_str_value);


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
                adapter = new GuestProductListAdapter(GuestProductActivity.this,"");
                productlist.setAdapter(adapter);
            }
            else {

                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

    }


    public class CustomDialogClass extends Dialog {

        public Activity c;

        public TextView cancel;
        public TextView confirm;

        public ListView selected;

        GuestProductSelectedAdapter selectedAdapter;

        float amount_to_pay = 0;

        public CustomDialogClass(Activity a) {
            super(a);
            // TODO Auto-generated constructor stub
            this.c = a;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.buy_product_dialog);

            confirm = (TextView) findViewById(R.id.confirm);
            cancel = (TextView) findViewById(R.id.cancel);
            selected = (ListView) findViewById(R.id.selected_product_list);

            selectedAdapter = new GuestProductSelectedAdapter(GuestProductActivity.this);
            selected.setAdapter(selectedAdapter);


            for (int i=0 ; i < global.getAl_select_product().size() ; i++)
            {
                int qt =Integer.parseInt(global.getAl_selected_product_quantity().get(i));
                float each_price = Float.parseFloat(global.getAl_selected_product_price().get(i));

                amount_to_pay = amount_to_pay + (qt*each_price);
            }

            str_amount_to_pay = String.valueOf(amount_to_pay);

            global.setPayment_amount(str_amount_to_pay);
            global.setAmount_to_pay(str_amount_to_pay);

            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (cn.isNetConnected())
                    {
                        new AddToCartAsyncTask().execute();

                        cdd.dismiss();

                        buy.setClickable(false);
                    }
                    else
                    {
                        Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }


                   // startActivity(new Intent(GuestProductActivity.this,GenerateInvoiceActivity.class));

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



    public class AddToCartAsyncTask extends AsyncTask<String, String, String> {

        OkHttpClient httpClient = new OkHttpClient();
        String resPonse = "";
        String message = "";

        @Override
        protected void onPreExecute() {

            gif_loader.setVisibility(View.VISIBLE);

//            str_client_id = global.getAl_src_client_details().get(global.getSelected_client()).
//                    get(GlobalConstants.SRC_CLIENT_ID);
        }

        @Override
        protected String doInBackground(String... params) {

            try {

                for ( int j=0 ; j < global.getAl_select_product().size() ; j++)
                {
                    ArrayList<String> al_str_key = new ArrayList<>();
                    ArrayList<String> al_str_value = new ArrayList<>();

                    al_str_key.add(GlobalConstants.USER_BRANCH_ID);
                    al_str_value.add(global.getAl_login_list().get(0).get(GlobalConstants.USER_BRANCH_ID));

                    al_str_key.add(GlobalConstants.CART_CLIENT_ID);
                    al_str_value.add(str_client_id);

                    al_str_key.add(GlobalConstants.CART_ITEM_ID);
                    al_str_value.add(global.getAl_select_product().get(j));

                    al_str_key.add(GlobalConstants.CART_ITEM_TYPE);
                    al_str_value.add("product");

                    al_str_key.add(GlobalConstants.CART_AMOUNT);
                    al_str_value.add(global.getAl_selected_product_quantity().get(j));

                    al_str_key.add(GlobalConstants.BRANCH);
                    al_str_value.add(str_branch);

                    al_str_key.add(GlobalConstants.ACTION);
                    al_str_value.add("add_to_cart");

                    for (int i = 0; i< al_str_key.size() ; i++)
                    {
                        Log.i("Key",""+al_str_key.get(i));
                        Log.i("Value",""+al_str_value.get(i));
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



            if (message.equalsIgnoreCase("true"))
            {
                Log.i("Added","To cart");
                new PaymentAsyncTask().execute();

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

          //  gif_loader.setVisibility(View.VISIBLE);
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
                al_str_value.add("cash");

                al_str_key.add(GlobalConstants.PAYMENT_TYPE);
                al_str_value.add("full");

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
                al_str_value.add("0");

                al_str_key.add(GlobalConstants.PAYMENT_IS_FULL_PAID);
                al_str_value.add("1");

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
                Log.e("Paid","DOne");

                buy.setClickable(true);

                startActivity(new Intent(GuestProductActivity.this,GuestReceiptActivity.class));
                finish();

            }
            else {
                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

    }




}
