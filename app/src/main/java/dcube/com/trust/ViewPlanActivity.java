package dcube.com.trust;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
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
import dcube.com.trust.utils.Global;
import dcube.com.trust.utils.PlanAdapter;
import okhttp3.OkHttpClient;
import pl.droidsonroids.gif.GifTextView;

public class ViewPlanActivity extends Activity {

    ListView lv_plan;
    PlanAdapter planAdapter;

    ArrayList<String> al_plan_name = new ArrayList<>();
    ArrayList<String> al_date= new ArrayList<>();

    GifTextView gif_loader;

    Context context;

    WebServices ws;
    Global global;

    int position;
    String str_client_id;
    CustomDialogClass cdd;

    CheckNetConnection cn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_plan);

        context = this;

        global = (Global) getApplicationContext();

        lv_plan=(ListView)findViewById(R.id.lv_plan);

        gif_loader = (GifTextView) findViewById(R.id.gif_loader);

        cn = new CheckNetConnection(this);


        lv_plan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {

                position = pos;
                cdd= new CustomDialogClass(ViewPlanActivity.this);
                cdd.show();
            }
        });


        str_client_id = global.getAl_src_client_details().get(global.getSelected_client()).get(GlobalConstants.SRC_CLIENT_ID);


        if (cn.isNetConnected())
        {
            new ViewPlanAsyncTask().execute();
        }
        else {
            Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
        }


    }


    public class CustomDialogClass extends Dialog {

        public Activity c;

        public TextView cancel,tv_plan_price;
        public TextView confirm,tv_plan_name;

        public CustomDialogClass(Activity a) {
            super(a);
            // TODO Auto-generated constructor stub
            this.c = a;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.renewplan_dialog);

            confirm = (TextView) findViewById(R.id.confirm);
            cancel = (TextView) findViewById(R.id.cancel);
            tv_plan_name = (TextView) findViewById(R.id.tv_plan_name);
            tv_plan_price = (TextView) findViewById(R.id.tv_plan_price);

            tv_plan_name.setText(global.getAl_view_plan_details().get(position).get(GlobalConstants.ORDER_ITEM_ID));
            tv_plan_price.setText(global.getAl_view_plan_details().get(position).get(GlobalConstants.ORDER_ITEM_PRICE));


            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    dismiss();


                    final Dialog dialog = new Dialog(c);
                    dialog.setContentView(R.layout.custom_dialog);
                    dialog.show();

                    TextView text= (TextView)dialog.findViewById(R.id.text);
                    Button btn_yes=(Button)dialog.findViewById(R.id.btn_yes);

                    text.setText("Not Completed Yet");

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



    public class ViewPlanAsyncTask extends AsyncTask<String, String, String> {

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

                al_str_key.add(GlobalConstants.ORDER_ITEM_TYPE);
                al_str_value.add("plan");

                al_str_key.add(GlobalConstants.ACTION);
                al_str_value.add("my_order");

                message = ws.ViewPlanService(context, al_str_key, al_str_value);


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
                planAdapter= new PlanAdapter(context);
                lv_plan.setAdapter(planAdapter);
            }
            else {

                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

    }




    public class RenewPlanAsyncTask extends AsyncTask<String, String, String> {

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

                al_str_key.add(GlobalConstants.CLIENT_ID);
                al_str_value.add(str_client_id);

                al_str_key.add(GlobalConstants.PLAN_ID);
                al_str_value.add(global.getAl_plan_details().get(position).get(GlobalConstants.PLAN_ID));

                al_str_key.add(GlobalConstants.ACTION);
                al_str_value.add("renew_the_plan");

                message = ws.ViewPlanService(context, al_str_key, al_str_value);


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
                planAdapter= new PlanAdapter(context);
                lv_plan.setAdapter(planAdapter);
            }
            else {

                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

    }


}
