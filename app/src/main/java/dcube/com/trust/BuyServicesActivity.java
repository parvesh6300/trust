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
import dcube.com.trust.utils.ServiceListAdapter;
import okhttp3.OkHttpClient;
import pl.droidsonroids.gif.GifTextView;

public class BuyServicesActivity extends Activity {

    ListView servicelist;
    ServiceListAdapter adapter;
    GifTextView gif_loader;
    TextView buy;

    EditText search;
    WebServices ws;
    Context context = BuyServicesActivity.this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_services);


        //addData();

        servicelist = (ListView) findViewById(R.id.servicelist);

        buy = (TextView) findViewById(R.id.buy);
        search = (EditText) findViewById(R.id.search);

        gif_loader = (GifTextView) findViewById(R.id.gif_loader);


        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CustomDialogClass cdd = new CustomDialogClass(BuyServicesActivity.this);
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


            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {

                Log.e("TextWatcherTest", "afterTextChanged:\t" +s.toString());

            }
        });


        new GetServiceAsyncTask().execute();

    }



    public class CustomDialogClass extends Dialog {

        public Activity c;

        public TextView cancel;
        public TextView confirm;

        public ListView selected;

        ServiceSelectedAdapter selectedAdapter;

        public CustomDialogClass(Activity a) {
            super(a);
            // TODO Auto-generated constructor stub
            this.c = a;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);

            setContentView(R.layout.buy_service_dialog);

            confirm = (TextView) findViewById(R.id.confirm);
            cancel = (TextView) findViewById(R.id.cancel);
            selected = (ListView) findViewById(R.id.selected_product_list);

//            selectedAdapter = new ServiceSelectedAdapter(BuyServicesActivity.this,name,serviceCost);
//            selected.setAdapter(selectedAdapter);

            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    dismiss();

                    startActivity(new Intent(BuyServicesActivity.this,GenerateInvoiceActivity.class));
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


    public class GetServiceAsyncTask extends AsyncTask<String, String, String> {

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
                al_str_value.add("get_service_list");

                message = ws.GetServiceService(context, al_str_key, al_str_value);

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

                adapter = new ServiceListAdapter(BuyServicesActivity.this);
                servicelist.setAdapter(adapter);
            }

        }

    }

}
