package dcube.com.trust;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import WebServicesHandler.CheckNetConnection;
import WebServicesHandler.GlobalConstants;
import WebServicesHandler.HideKeyboard;
import WebServicesHandler.WebServices;
import dcube.com.trust.utils.Global;
import dcube.com.trust.utils.PlanListAdapter;
import dcube.com.trust.utils.PlanProductAdapter;
import dcube.com.trust.utils.PlanServiceAdapter;
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

   // CustomDialogClass cdd;

    EditText search;

    CheckNetConnection cn;

    String str_client_id;

    PlanDialogClass dialog;

    RelativeLayout rel_parent_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_buy_plan);

        global = (Global) getApplicationContext();

        gif_loader = (GifTextView) findViewById(R.id.gif_loader);

        servicelist = (ListView) findViewById(R.id.servicelist);

        rel_parent_layout = (RelativeLayout) findViewById(R.id.rel_parent_layout);

        buy = (TextView) findViewById(R.id.buy);
        search = (EditText) findViewById(R.id.search);

        cn = new CheckNetConnection(this);

        /*

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try
                {
                    if (global.getAl_selected_plan_id().size() > 0)
                    {
                        cdd = new CustomDialogClass(BuyPlanActivity.this);
                        cdd.show();
                    }
                    else
                    {
                        Toast.makeText(BuyPlanActivity.this, "Chose any one Plan", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e)
                {
                    Toast.makeText(BuyPlanActivity.this, "Chose any one Plan", Toast.LENGTH_SHORT).show();
                }


            }
        });
*/

        servicelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {

//                adapter.setSelectedIndex(i);
//                adapter.notifyDataSetChanged();

                global.setPlan_selected_pos(pos);

                if (cn.isNetConnected())
                {
                    new GetPlanDataAsyncTask().execute();
                }
                else
                {
                    Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                }

            }
        });


        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }
            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() > 1)
                {
                    adapter = new PlanListAdapter(BuyPlanActivity.this, s.toString());
                    servicelist.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                else
                {
                    adapter = new PlanListAdapter(BuyPlanActivity.this, "");
                    servicelist.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }

            }
        });


        if (cn.isNetConnected())
        {
            new GetPlanAsyncTask().execute();
        }
        else
        {
            Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
        }


        rel_parent_layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                HideKeyboard.hideSoftKeyboard(BuyPlanActivity.this);
                return false;
            }
        });


        str_client_id = global.getAl_src_client_details().get(global.getSelected_client()).
                get(GlobalConstants.SRC_CLIENT_ID);


    }


    @Override
    public void onBackPressed() {
       // super.onBackPressed();

        ClientHomeActivity.h.sendEmptyMessage(0);
        startActivity(new Intent(BuyPlanActivity.this,ClientHomeActivity.class));
        finish();
    }

    /**
     * Custom dialog shows the elements in plan
     */


    public class PlanDialogClass extends Dialog
    {

        public Context c;

        public Button tv_cancel;
        public Button tv_add_to_cart;

        public ListView lv_products,lv_services;

        PlanProductAdapter productAdapter;
        PlanServiceAdapter serviceAdapter;


        public  PlanDialogClass(Context context1) {
            super(context1);
            // TODO Auto-generated constructor stub
            this.c = context1;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            requestWindowFeature(Window.FEATURE_NO_TITLE);

            setContentView(R.layout.plan_detail_dialog);

            tv_add_to_cart = (Button) findViewById(R.id.tv_add_to_cart);
            tv_cancel = (Button) findViewById(R.id.tv_cancel);

            lv_services = (ListView) findViewById(R.id.lv_services);
            lv_products = (ListView) findViewById(R.id.lv_products);


            productAdapter = new PlanProductAdapter(c);
            lv_products.setAdapter(productAdapter);

            serviceAdapter = new PlanServiceAdapter(c);
            lv_services.setAdapter(serviceAdapter);


            tv_add_to_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (cn.isNetConnected())
                    {
                        dismiss();
                        new AddPlanCartAsyncTask().execute();
                    }
                    else
                    {
                        Toast.makeText(BuyPlanActivity.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }


                }
            });

            tv_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    dismiss();
                }
            });



        }



    }


    /**
     * Hit web service and get list of plans
     */


    public class GetPlanAsyncTask extends AsyncTask<String, String, String>
    {

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
                al_str_value.add("get_plans_of_branch");

                Log.i("Key",""+al_str_key);
                Log.i("Value",""+al_str_value);

                message = ws.BuyPlanService(context, al_str_key, al_str_value);


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
                adapter = new PlanListAdapter(BuyPlanActivity.this,"");
                servicelist.setAdapter(adapter);

            }
            else {

                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

    }


    /**
     * Hit web service and add plan to cart
     */


    public class AddPlanCartAsyncTask extends AsyncTask<String, String, String>
    {

        OkHttpClient httpClient = new OkHttpClient();
        String resPonse = "";
        String message = "";
        int pos ;

        @Override
        protected void onPreExecute() {

            gif_loader.setVisibility(View.VISIBLE);

            pos = global.getPlan_selected_pos();

        }

        @Override
        protected String doInBackground(String... params) {
            try {

//                for (int i = 0 ; i < global.al_selected_plan_id.size() ; i++)
//                {
                    ArrayList<String> al_str_key = new ArrayList<>();
                    ArrayList<String> al_str_value = new ArrayList<>();

                    al_str_key.add(GlobalConstants.USER_BRANCH_ID);
                    al_str_value.add(global.getAl_login_list().get(0).get(GlobalConstants.USER_BRANCH_ID));

                    al_str_key.add(GlobalConstants.CART_CLIENT_ID);
                    al_str_value.add(str_client_id);

                    al_str_key.add(GlobalConstants.CART_ITEM_TYPE);
                    al_str_value.add("plan");

                    al_str_key.add(GlobalConstants.CART_ITEM_ID);
                    al_str_value.add(global.getAl_plan_details().get(pos).get(GlobalConstants.PLAN_ID));

                    al_str_key.add(GlobalConstants.CART_AMOUNT);
                    al_str_value.add("1");

                    al_str_key.add(GlobalConstants.ACTION);
                    al_str_value.add("add_to_cart");

                    for (int j = 0; j < al_str_key.size(); j++)
                    {
                        Log.i("KEy", "" + al_str_key.get(j));
                        Log.i("Value", "" + al_str_value.get(j));
                    }

                    message = ws.AddToCartService(context, al_str_key, al_str_value);

//                }

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
                Toast.makeText(context, "Plan Added", Toast.LENGTH_SHORT).show();

//                ClientHomeActivity.h.sendEmptyMessage(0);
//                startActivity(new Intent(BuyPlanActivity.this,ClientHomeActivity.class));
//                finish();
            }
            else
            {
                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

    }


    /**
     * Hit web service and get details of the plan elements
     */


    public class GetPlanDataAsyncTask extends AsyncTask<String, String, String>
    {

        OkHttpClient httpClient = new OkHttpClient();
        String resPonse = "";
        String message = "";

        int pos ;

        @Override
        protected void onPreExecute() {

            gif_loader.setVisibility(View.VISIBLE);

            pos = global.getPlan_selected_pos();

        }

        @Override
        protected String doInBackground(String... params) {
            try {

                ArrayList<String> al_str_key = new ArrayList<>();
                ArrayList<String> al_str_value = new ArrayList<>();

                al_str_key.add(GlobalConstants.PLAN_ID);
                al_str_value.add(global.getAl_plan_details().get(pos).get(GlobalConstants.PLAN_ID));

                al_str_key.add(GlobalConstants.ACTION);
                al_str_value.add("get_plans_details");

                Log.i("Key",""+al_str_key);
                Log.i("Value",""+al_str_value);

                message = ws.GetPlanDataService(context, al_str_key, al_str_value);


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
                dialog = new PlanDialogClass(BuyPlanActivity.this);
                dialog.show();


            }
            else {

                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

    }



    /*

    public class CustomDialogClass extends Dialog {

        public Activity c;
        float int_product_price = 0;
        float int_service_price = 0;

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

                    if (cn.isNetConnected())
                    {
                        dismiss();
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
                        Float.parseFloat(global.getAl_selected_plan().get(count).get(GlobalConstants.PLAN_PRODUCT_PRICE));

                int_service_price = int_service_price +
                        Float.parseFloat(global.getAl_selected_plan().get(count).get(GlobalConstants.PLAN_SERVICE_PRICE));
            }

            tv_product_cost.setText(String.valueOf(int_product_price));
            tv_service_cost.setText(String.valueOf(int_service_price));

        }

    }


    */






}
