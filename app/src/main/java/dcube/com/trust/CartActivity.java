package dcube.com.trust;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import WebServicesHandler.CheckNetConnection;
import WebServicesHandler.GlobalConstants;
import WebServicesHandler.HideKeyboard;
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

    float total_cost=0;

    CheckNetConnection cn;

    public static Handler h;
    CartAdapter adapter;

    RelativeLayout rel_parent_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cart);

        global = (Global) getApplicationContext();

        cn = new CheckNetConnection(this);

        lv_cart_items = (ListView) findViewById(R.id.lv_cart_items);

        gif_loader = (GifTextView) findViewById(R.id.gif_loader);

        tv_check_out = (TextView) findViewById(R.id.tv_check_out);

        rel_parent_layout = (RelativeLayout) findViewById(R.id.rel_parent_layout);


        tv_check_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try
                {
                    if (!global.getAl_cart_details().isEmpty())
                    {
                        CustomDialogClass dialog = new CustomDialogClass(CartActivity.this);
                        dialog.show();
                    }
                    else
                    {
                        Toast.makeText(CartActivity.this, "Cart is Empty", Toast.LENGTH_SHORT).show();
                    }

                }
                catch (Exception e)
                {
                    Toast.makeText(CartActivity.this, "Some error occured", Toast.LENGTH_SHORT).show();
                }


            }
        });



        rel_parent_layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                HideKeyboard.hideSoftKeyboard(CartActivity.this);
                return false;
            }
        });


        str_client_id = global.getAl_src_client_details().get(global.getSelected_client()).
                get(GlobalConstants.SRC_CLIENT_ID);


        if (cn.isNetConnected())
        {
            new GetCartItemsAsyncTask().execute();
        }
        else {
            Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
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


    /**
     * Custom Confirmation Dialog
     */

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

            total_cost =0;

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


            tv_message.setText("Your order total is: "+total_cost+" TZS");


            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    global.setCart(true);
                    global.setPlanRenew(false);
                    global.setPendingPayment(false);

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


    /**
     * Hit web service and get items of cart
     */


    public class GetCartItemsAsyncTask extends AsyncTask<String, String, String> {

        OkHttpClient httpClient = new OkHttpClient();
        String resPonse = "";
        String message = "";

        @Override
        protected void onPreExecute() {

            tv_check_out.setEnabled(false);

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
                al_str_value.add("get_cart_by_client_id");

                Log.i("Key",""+al_str_key);
                Log.i("Value",""+al_str_value);

                message = ws.GetCartService(context, al_str_key, al_str_value);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            gif_loader.setVisibility(View.INVISIBLE);
            tv_check_out.setEnabled(true);

            if (message.equalsIgnoreCase("true"))
            {
                adapter = new CartAdapter(CartActivity.this);
                lv_cart_items.setAdapter(adapter);

            }
            else {

                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

    }



    /**
     * Show loader visibility
     * @param context
     * @param show boolean
     */


    public void loader(Context context, boolean show)
    {
        if (show)
        {
            gif_loader.setVisibility(View.VISIBLE);
            tv_check_out.setEnabled(false);
        }
        else
        {
            gif_loader.setVisibility(View.INVISIBLE);
            tv_check_out.setEnabled(true);
        }


    }


    /**
     * Updates the list after updating the cart
     * @param context
     */


    public void updateList(Context context)
    {
        CartAdapter adapter;
        adapter = new CartAdapter(context);
        lv_cart_items.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


}
