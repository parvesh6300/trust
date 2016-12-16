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
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import WebServicesHandler.GlobalConstants;
import WebServicesHandler.WebServices;
import dcube.com.trust.utils.PlanListAdapter;
import dcube.com.trust.utils.PlanSelectedAdapter;
import okhttp3.OkHttpClient;
import pl.droidsonroids.gif.GifTextView;

public class BuyPlanActivity extends Activity{

    ListView servicelist;
    PlanListAdapter adapter;

    TextView buy;
    GifTextView gif_loader;
    WebServices ws;

    Context context = this;

     ArrayList<String> name = new ArrayList<>();
     ArrayList<String> productCost = new ArrayList<>();
     ArrayList<String> serviceCost = new ArrayList<>();

    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_plan);

        addData();

        gif_loader = (GifTextView) findViewById(R.id.gif_loader);

        servicelist = (ListView) findViewById(R.id.servicelist);

        buy = (TextView) findViewById(R.id.buy);
        search = (EditText) findViewById(R.id.search);



        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CustomDialogClass cdd = new CustomDialogClass(BuyPlanActivity.this);
                cdd.show();
            }
        });


        servicelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                adapter.setSelectedIndex(i);
                adapter.notifyDataSetChanged();

            }
        });


        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                name = new ArrayList<>();
                productCost = new ArrayList<>();
                serviceCost = new ArrayList<>();

            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {

                Log.e("TextWatcherTest", "afterTextChanged:\t" +s.toString());

                if(s.toString().equalsIgnoreCase("IUCD"))
                {
                    name.add("IUCD Sleek/Copper T/Safeload");
                    productCost.add("5,000");
                    serviceCost.add("20,000");

                    name.add("CPAC + IUCD");
                    productCost.add("");
                    serviceCost.add("150,000");

                }
                else
                if(s.toString().equalsIgnoreCase("implant"))
                {
                    name.add("Implanon Implants");
                    productCost.add("implant");
                    serviceCost.add("23");

                    name.add("Jadelle");
                    productCost.add("implant");
                    serviceCost.add("7");
                }
                else
                {
                    addData();
                }

                adapter = new PlanListAdapter(BuyPlanActivity.this,name,productCost,serviceCost);
                servicelist.setAdapter(adapter);
            }
        });
    }

    public class CustomDialogClass extends Dialog {

        public Activity c;

        public TextView cancel;
        public TextView confirm;

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
            selected = (ListView) findViewById(R.id.selected_product_list);

            selectedAdapter = new PlanSelectedAdapter(BuyPlanActivity.this,name,productCost,serviceCost);
            selected.setAdapter(selectedAdapter);

            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    dismiss();

                    startActivity(new Intent(BuyPlanActivity.this,GenerateInvoiceActivity.class));
                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    dismiss();
                }
            });

            new GetPlanAsyncTask().execute();
        }

    }

    public  void addData(){
        name.add("Silverline + Implants");
        productCost.add("5,000");
        serviceCost.add("20,000");

        name.add("Injection Depo + Injection-Sayana");
        productCost.add("15,000");
        serviceCost.add("20,000");

        name.add("HIV + Family Planning");
        productCost.add("12,000");
        serviceCost.add("150,000");

        name.add("Blood Sugar + Urine Analysis");
        productCost.add("10,000");
        serviceCost.add("50,000");

        name.add("OCP's(3 Cycles) + EC(2 Packs)");
        productCost.add("15,000");
        serviceCost.add("Free");

    }



    public class GetPlanAsyncTask extends AsyncTask<String, String, String> {

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

                message = ws.GetPlanService(context, al_str_key, al_str_value);

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

                adapter = new PlanListAdapter(BuyPlanActivity.this,name,productCost,serviceCost);
                servicelist.setAdapter(adapter);
            }

        }

    }

}
