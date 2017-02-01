package dcube.com.trust;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import WebServicesHandler.CheckNetConnection;
import WebServicesHandler.GlobalConstants;
import WebServicesHandler.WebServices;
import dcube.com.trust.utils.Global;
import dcube.com.trust.utils.ViewPlanProductAdapter;
import dcube.com.trust.utils.ViewPlanServiceAdapter;
import okhttp3.OkHttpClient;
import pl.droidsonroids.gif.GifTextView;

public class UpdatePlanActivity extends Activity {

    ListView lv_products,lv_services;
    TextView tv_update_plan;

    ViewPlanProductAdapter product_adapter;
    ViewPlanServiceAdapter service_Adapter;

    GifTextView gif_loader;

    Global global;

    String str_client_id,str_plan_id;

    WebServices ws;

    CheckNetConnection cn;

    ArrayList<String> al_element_id;
    ArrayList<String> al_element_type;
    ArrayList<String> al_element_qty;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_update_plan);

        context = this;

        cn = new CheckNetConnection(this);

        global = (Global) getApplicationContext();

        gif_loader = (GifTextView) findViewById(R.id.gif_loader);

        tv_update_plan = (TextView) findViewById(R.id.tv_update_plan);

        lv_products = (ListView) findViewById(R.id.lv_products);
        lv_services = (ListView) findViewById(R.id.lv_services);


        tv_update_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                al_element_id = new ArrayList<String>();
                al_element_qty = new ArrayList<String>();
                al_element_type = new ArrayList<String>();


                for (int i =0 ; i < global.getAl_update_product_qty().size() ; i++)
                {
                    al_element_qty.add(global.getAl_update_product_qty().get(i));
                }

                for (int i =0 ; i < global.getAl_update_service_qty().size() ; i++)
                {
                    al_element_qty.add(global.getAl_update_service_qty().get(i));
                }



                if (global.getAl_view_plan_details().size() > 0)
                {
                    for (int i=0 ; i < global.getAl_view_plan_details().size() ; i++)
                    {
                        if (global.getAl_view_plan_details().get(i).get(GlobalConstants.PLAN_ELEMENT_TYPE).equalsIgnoreCase("Product"))
                        {
                            al_element_type.add("Product");
                            al_element_id.add(global.getAl_view_plan_details().get(i).get(GlobalConstants.PLAN_ELEMENT_ID));
                        }

//                        if (global.getAl_view_plan_details().get(i).get(GlobalConstants.PLAN_ELEMENT_TYPE).equalsIgnoreCase("Service"))
//                        {
//                            al_element_type.add("Service");
//                            al_element_id.add(global.getAl_view_plan_details().get(i).get(GlobalConstants.PLAN_ELEMENT_ID));
//                        }

                    }

                    for (int i=0 ; i < global.getAl_view_plan_details().size() ; i++)
                    {
//                        if (global.getAl_view_plan_details().get(i).get(GlobalConstants.PLAN_ELEMENT_TYPE).equalsIgnoreCase("Product"))
//                        {
//                            al_element_type.add("Product");
//                            al_element_id.add(global.getAl_view_plan_details().get(i).get(GlobalConstants.PLAN_ELEMENT_ID));
//                        }

                        if (global.getAl_view_plan_details().get(i).get(GlobalConstants.PLAN_ELEMENT_TYPE).equalsIgnoreCase("Service"))
                        {
                            al_element_type.add("Service");
                            al_element_id.add(global.getAl_view_plan_details().get(i).get(GlobalConstants.PLAN_ELEMENT_ID));
                        }

                    }


                    if (cn.isNetConnected())
                    {
                        new UpdatePlanAsyncTask().execute();
                    }
                    else {
                        Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });



        str_client_id = global.getAl_src_client_details().get(global.getSelected_client()).get(GlobalConstants.SRC_CLIENT_ID);

     //   str_plan_id = global.getAl_view_plan_details().get(global.getViewPlanSelectPos()).get(GlobalConstants.PLAN_ID);

        str_plan_id = global.getStr_selected_plan_id();


        if (cn.isNetConnected())
        {
            new GetPlanDataAsyncTask().execute();
        }
        else {
            Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
        }

    }



    public class GetPlanDataAsyncTask extends AsyncTask<String, String, String> {

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

                al_str_key.add(GlobalConstants.CLIENT_ID);
                al_str_value.add(str_client_id);

                al_str_key.add(GlobalConstants.PLAN_ID);
                al_str_value.add(str_plan_id);

                al_str_key.add(GlobalConstants.ACTION);
                al_str_value.add("fetch_availed_plans_details");

                Log.i("Key",""+al_str_key);
                Log.i("Value",""+al_str_value);

                message = ws.ViewPlanDataService(context, al_str_key, al_str_value);


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
                product_adapter = new ViewPlanProductAdapter(UpdatePlanActivity.this);
                lv_products.setAdapter(product_adapter);

                service_Adapter = new ViewPlanServiceAdapter(UpdatePlanActivity.this);
                lv_services.setAdapter(service_Adapter);

            }
            else {

                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

    }


    public class UpdatePlanAsyncTask extends AsyncTask<String, String, String> {

        OkHttpClient httpClient = new OkHttpClient();
        String resPonse = "";
        String message = "";

        String str_element_id,str_element_type,str_element_qty;

        @Override
        protected void onPreExecute() {


            gif_loader.setVisibility(View.VISIBLE);

            str_element_id = android.text.TextUtils.join(",", al_element_id);

            str_element_type = android.text.TextUtils.join(",", al_element_type);

            str_element_qty = android.text.TextUtils.join(",", al_element_qty);

            Log.e("Elem","id "+str_element_id);
            Log.e("Elem","type "+str_element_type);
            Log.e("Elem","qty "+str_element_qty);


            tv_update_plan.setEnabled(false);
        }

        @Override
        protected String doInBackground(String... params) {
            try {

                ArrayList<String> al_str_key = new ArrayList<>();
                ArrayList<String> al_str_value = new ArrayList<>();

                al_str_key.add(GlobalConstants.USER_BRANCH_ID);
                al_str_value.add(global.getAl_login_list().get(0).get(GlobalConstants.USER_BRANCH_ID));

                al_str_key.add(GlobalConstants.CLIENT_ID);
                al_str_value.add(str_client_id);

                al_str_key.add(GlobalConstants.PLAN_ID);
                al_str_value.add(str_plan_id);

                al_str_key.add(GlobalConstants.PLAN_ELEMENT_ID);
                al_str_value.add(str_element_id);

                al_str_key.add(GlobalConstants.PLAN_ELEMENT_QUANTITY);
                al_str_value.add(str_element_qty);

                al_str_key.add(GlobalConstants.PLAN_ELEMENT_TYPE);
                al_str_value.add(str_element_type);

                al_str_key.add(GlobalConstants.ACTION);
                al_str_value.add("update_availed_plan");


                Log.i("Key",""+al_str_key);
                Log.i("Value",""+al_str_value);


                message = ws.UpdatePlanService(context, al_str_key, al_str_value);


            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            gif_loader.setVisibility(View.GONE);

            tv_update_plan.setEnabled(true);

            if (message.equalsIgnoreCase("true"))
            {
                Log.i("Updated","");

                new GetPlanDataAsyncTask().execute();
            }
            else {

                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

    }



}
