package dcube.com.trust;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
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

import WebServicesHandler.GlobalConstants;
import WebServicesHandler.WebServices;
import dcube.com.trust.utils.Global;
import dcube.com.trust.utils.ProductListAdapter;
import dcube.com.trust.utils.ProductSelectedAdapter;
import okhttp3.OkHttpClient;
import pl.droidsonroids.gif.GifTextView;

public class BuyProductActivity extends Activity{

    ListView productlist;
    ProductListAdapter adapter;
    TextView buy;

    GifTextView gif_loader;
    Context context = BuyProductActivity.this;

    WebServices ws;
    EditText search;

    Global global;

    CustomDialogClass cdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_buy_product);

        global = (Global) getApplicationContext();

        productlist = (ListView) findViewById(R.id.productlist);

        gif_loader = (GifTextView) findViewById(R.id.gif_loader);

        buy = (TextView) findViewById(R.id.buy);

        search = (EditText) findViewById(R.id.search);

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    if (global.getAl_select_product().size() > 0) {
                        cdd = new CustomDialogClass(BuyProductActivity.this);
                        cdd.show();
                    } else {
                        Toast.makeText(BuyProductActivity.this, "Chose any one Product", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(BuyProductActivity.this, "Chose any one Product", Toast.LENGTH_SHORT).show();
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

                Log.e("TextWatcherTest", "afterTextChanged:\t" + s.toString());

                if (s.length() > 1) {
                    adapter = new ProductListAdapter(BuyProductActivity.this, s.toString());
                    productlist.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    adapter = new ProductListAdapter(BuyProductActivity.this, "");
                    productlist.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        if (isOnline()) {
            new GetPruductAsyncTask().execute();
        } else {
            Toast.makeText(BuyProductActivity.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
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

                al_str_key.add(GlobalConstants.ACTION);
                al_str_value.add("get_product_list");

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
                adapter = new ProductListAdapter(BuyProductActivity.this,"");
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

        ProductSelectedAdapter selectedAdapter;

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

            selectedAdapter = new ProductSelectedAdapter(BuyProductActivity.this);
            selected.setAdapter(selectedAdapter);

            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    dismiss();
                //    new AddToCartAsyncTask().execute();
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


    /*


    public class AddToCartAsyncTask extends AsyncTask<String, String, String> {

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

                for ( int j=0 ; j < global.al_selected_product_id.size() ; j++)
                {
                    ArrayList<String> al_str_key = new ArrayList<>();
                    ArrayList<String> al_str_value = new ArrayList<>();

                    al_str_key.add(GlobalConstants.CART_CLIENT_ID);
                    al_str_value.add(str_client_id);

                    al_str_key.add(GlobalConstants.CART_ITEM_ID);
                    al_str_value.add(global.al_selected_product_id.get(j));

                    al_str_key.add(GlobalConstants.CART_ITEM_TYPE);
                    al_str_value.add("product");

                    al_str_key.add(GlobalConstants.CART_AMOUNT);
                    al_str_value.add(global.al_selected_product_quantity.get(j));

                    al_str_key.add(GlobalConstants.ACTION);
                    al_str_value.add("add_to_cart");

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
                startActivity(new Intent(BuyProductActivity.this,ClientHomeActivity.class));
                finish();
            }
            else {
                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

    }

    */

}
