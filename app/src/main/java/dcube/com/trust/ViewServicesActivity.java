package dcube.com.trust;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import WebServicesHandler.CheckNetConnection;
import WebServicesHandler.GlobalConstants;
import WebServicesHandler.WebServices;
import dcube.com.trust.utils.FormatString;
import dcube.com.trust.utils.Global;
import dcube.com.trust.utils.ServiceAdapter;
import okhttp3.OkHttpClient;
import pl.droidsonroids.gif.GifTextView;

public class ViewServicesActivity extends Activity {

    ListView lv_services;
    ServiceAdapter adapter;

    GifTextView gif_loader;

    Context context;

    WebServices ws;

    String str_client_id;
    CustomDialogClass cdd;

    Global global;

    public static Handler h;

    CheckNetConnection cn;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_services);

        global = (Global) getApplicationContext();

        lv_services =(ListView)findViewById(R.id.lv_services);

        gif_loader = (GifTextView) findViewById(R.id.gif_loader);

        context = this;

        cn = new CheckNetConnection(this);


        lv_services.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {

                position = pos;

                global.setServiceAppointmentPos(position);
                global.setServiceAppointment(true);
                startActivity(new Intent(ViewServicesActivity.this,CalendarActivity.class));

            }
        });
        

        str_client_id = global.getAl_src_client_details().get(global.getSelected_client()).get(GlobalConstants.SRC_CLIENT_ID);


        if (cn.isNetConnected())
        {
            new ViewServiceAsyncTask().execute();
        }
        else
        {
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
     * Renew Confirmation dialog
     */


    public class CustomDialogClass extends Dialog {

        public Activity c;

        public Button cancel,confirm;
        public TextView tv_service_cost,tv_service_name;

        public CustomDialogClass(Activity a) {
            super(a);
            // TODO Auto-generated constructor stub
            this.c = a;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            requestWindowFeature(Window.FEATURE_NO_TITLE);

            setContentView(R.layout.renew_service_dialog);

            confirm = (Button) findViewById(R.id.confirm);
            cancel = (Button) findViewById(R.id.cancel);
            tv_service_cost = (TextView) findViewById(R.id.tv_service_cost);
            tv_service_name = (TextView) findViewById(R.id.tv_service_name);

            tv_service_name.setText(global.getAl_view_service_details().get(position).get(GlobalConstants.ORDER_ITEM_NAME));

            String formatted_srvc_cost = global.getAl_view_service_details().get(position).get(GlobalConstants.ORDER_ITEM_PRICE);

            formatted_srvc_cost = FormatString.getCommaInString(formatted_srvc_cost);

            tv_service_cost.setText(formatted_srvc_cost);      //global.getAl_view_service_details().get(position).get(GlobalConstants.ORDER_ITEM_PRICE)


            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    dismiss();
                    //   finish();

                    final Dialog dialog = new Dialog(c);
                    dialog.setContentView(R.layout.custom_dialog);
                    dialog.show();

                    TextView text= (TextView)dialog.findViewById(R.id.text);
                    Button btn_yes=(Button)dialog.findViewById(R.id.btn_yes);

                    text.setText("Renewed Successfully");

                    btn_yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            dialog.cancel();
                            finish();
                        }
                    });

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
     * Hit the server and get availed services
     */


    public class ViewServiceAsyncTask extends AsyncTask<String, String, String> {

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

                al_str_key.add(GlobalConstants.ORDER_ITEM_TYPE);
                al_str_value.add("service");

                al_str_key.add(GlobalConstants.ACTION);
                al_str_value.add("my_order");

                message = ws.ViewServiceService(context, al_str_key, al_str_value);


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
                adapter= new ServiceAdapter(context);
                lv_services.setAdapter(adapter);
            }
            else
            {
                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();

            }

        }

    }


}
