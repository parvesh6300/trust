package dcube.com.trust;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import WebServicesHandler.GlobalConstants;
import WebServicesHandler.WebServices;
import dcube.com.trust.utils.ClientAdapter;
import dcube.com.trust.utils.Global;
import okhttp3.OkHttpClient;

public class ClientHomeActivity extends Activity {

    ClientAdapter adapter;
    GridView gridView;
    ImageView iv_cart;
    TextView tv_user_name;
    Global global;

    TextView tv_logout,tv_notify;

    String login_pref = "Login_pref";
    String is_logged_in_pref = "Logged_in_pref";

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    String str_client_id;

    WebServices ws;

    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_home);

        global = (Global) getApplicationContext();

        iv_cart = (ImageView) findViewById(R.id.iv_cart);
        tv_logout = (TextView) findViewById(R.id.tv_logout);
        tv_user_name = (TextView) findViewById(R.id.tv_user_name) ;
        tv_notify = (TextView) findViewById(R.id.notify);

        adapter = new ClientAdapter(this);

        gridView = (GridView) findViewById(R.id.grid_view);
        gridView.setAdapter(adapter);

        tv_user_name.setText("Hi, "+global.getAl_login_list().get(0).get(GlobalConstants.USER_NAME));

        iv_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(ClientHomeActivity.this,CartActivity.class));
            }
        });


        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setSharedPreferences();
                startActivity(new Intent(ClientHomeActivity.this,LoginActivity.class));
                finish();
                global.getAl_login_list().clear();
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                switch(i)
                {
                    case 0:

                        startActivity(new Intent(ClientHomeActivity.this,AddAppointmentActivity.class));
                        break;

                    case 1:

                        startActivity(new Intent(ClientHomeActivity.this,ViewAppointmentActivity.class));
                        break;

                    case 2:

                        startActivity(new Intent(ClientHomeActivity.this,BuyProductActivity.class));
                        break;

                    case 3:

                        startActivity(new Intent(ClientHomeActivity.this,BuyPlanActivity.class));
                        break;

                    case 4:

                        startActivity(new Intent(ClientHomeActivity.this,ViewPlanActivity.class));
                        break;

                    case 5:

                        startActivity(new Intent(ClientHomeActivity.this,BuyServicesActivity.class));
                        break;

                    case 6:

                        startActivity(new Intent(ClientHomeActivity.this,ViewServicesActivity.class));
                        break;

                    case 7:

                        startActivity(new Intent(ClientHomeActivity.this,GenerateInvoiceActivity.class));
                        break;

                    case 8:

                        startActivity(new Intent(ClientHomeActivity.this,ViewPendingPaymentActivity.class));
                        break;
                }
            }
        });



        str_client_id = global.getAl_src_client_details().get(global.getSelected_client()).
                get(GlobalConstants.SRC_CLIENT_ID);


        new GetCartItemsAsyncTask().execute();


    }

    public void setSharedPreferences()
    {
        pref = getSharedPreferences(login_pref,MODE_PRIVATE);

        editor = pref.edit();

        editor.putBoolean(is_logged_in_pref,false);

        editor.apply();
    }




    public class GetCartItemsAsyncTask extends AsyncTask<String, String, String> {

        OkHttpClient httpClient = new OkHttpClient();
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

            //   gif_loader.setVisibility(View.GONE);

            if (message.equalsIgnoreCase("true"))
            {
                tv_notify.setText(String.valueOf(global.getTotal_cart_items()));
            }
            else {
                //  Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
//                CartAdapter adapter = new CartAdapter(CartActivity.this);
//                lv_cart_items.setAdapter(adapter);
            }

        }

    }

}
