package dcube.com.trust;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import WebServicesHandler.GlobalConstants;
import WebServicesHandler.WebServices;
import dcube.com.trust.utils.CartAdapter;
import dcube.com.trust.utils.Global;
import okhttp3.OkHttpClient;
import pl.droidsonroids.gif.GifTextView;

public class CartActivity extends Activity {

    ListView lv_cart_items;
    TextView tv_check_out;
    GifTextView gif_loader;
    WebServices ws;
    Context context = CartActivity.this;

    String str_client_id;
    Global global;

    int total_cost=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        global = (Global) getApplicationContext();

        lv_cart_items = (ListView) findViewById(R.id.lv_cart_items);

        gif_loader = (GifTextView) findViewById(R.id.gif_loader);

        tv_check_out = (TextView) findViewById(R.id.tv_check_out);

        tv_check_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for ( int i =0 ; i< global.getAl_cart_details().size() ; i++)
                {
                    total_cost = total_cost + Integer.parseInt(global.getAl_cart_details().get(i).get(GlobalConstants.GET_CART_ITEM_PRICE));
                }

                CustomDialogClass dialog = new CustomDialogClass(CartActivity.this);
                dialog.show();
            }
        });


        str_client_id = global.getAl_src_client_details().get(global.getSelected_client()).
                get(GlobalConstants.SRC_CLIENT_ID);

        new GetCartItemsAsyncTask().execute();

    }


    public class CustomDialogClass extends Dialog {

        public Activity c;

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

            setContentView(R.layout.cart_confirm_dialog);

            confirm = (TextView) findViewById(R.id.tv_yes);
            cancel = (TextView) findViewById(R.id.tv_no);
            tv_message = (TextView) findViewById(R.id.tv_message);

            tv_message.setText("Your order total is: "+total_cost+" Tsh");

            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    dismiss();
                    context.startActivity(new Intent(CartActivity.this,GenerateInvoiceActivity.class));
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



    public class GetCartItemsAsyncTask extends AsyncTask<String, String, String> {

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
                al_str_value.add("get_cart_by_client_id");

                message = ws.GetCartService(context, al_str_key, al_str_value);

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

                CartAdapter adapter = new CartAdapter(CartActivity.this);
                lv_cart_items.setAdapter(adapter);
            }

        }

    }



}
