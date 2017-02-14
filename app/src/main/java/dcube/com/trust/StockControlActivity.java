package dcube.com.trust;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
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
import dcube.com.trust.utils.StockAdapter;
import okhttp3.OkHttpClient;
import pl.droidsonroids.gif.GifTextView;

public class StockControlActivity extends Activity {

    TextView tv_quantity;

    ListView stock_list;

    EditText search;

    int i;

    StockAdapter stockAdapter;

    Global global;
    GifTextView gif_loader;
    WebServices ws;
    Context context = StockControlActivity.this;

    CheckNetConnection cn;
    String str_branch;

    RelativeLayout rel_parent_layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_stock_control);

        global = (Global) getApplicationContext();

        cn = new CheckNetConnection(this);

        rel_parent_layout = (RelativeLayout) findViewById(R.id.rel_parent_layout);

        tv_quantity = (TextView) findViewById(R.id.tv_quantity);

        stock_list = (ListView) findViewById(R.id.stock_list);
        gif_loader = (GifTextView) findViewById(R.id.gif_loader);

        search = (EditText) findViewById(R.id.search);

        str_branch = global.getAl_login_list().get(0).get(GlobalConstants.USER_BRANCH);


        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }
            @Override
            public void afterTextChanged(Editable s) {

                Log.e("TextWatcherTest", "afterTextChanged:\t" +s.toString());

                if(s.length() > 1)
                {
//                    adapter = new GuestProductListAdapter(GuestProductActivity.this,s.toString());
//                    productlist.setAdapter(adapter);
//                    adapter.notifyDataSetChanged();

                    stockAdapter = new StockAdapter(context,s.toString());
                    stock_list.setAdapter(stockAdapter);
                    stockAdapter.notifyDataSetChanged();

                }
                else
                {
//                    adapter = new GuestProductListAdapter(GuestProductActivity.this,"");
//                    productlist.setAdapter(adapter);
//                    adapter.notifyDataSetChanged();

                    stockAdapter = new StockAdapter(context,"");
                    stock_list.setAdapter(stockAdapter);
                    stockAdapter.notifyDataSetChanged();

                }

            }
        });

        rel_parent_layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                HideKeyboard.hideSoftKeyboard(StockControlActivity.this);
                return false;
            }
        });


        if (cn.isNetConnected())
        {
            new GetStockProductAsyncTask().execute();
        }
        else {
            Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
        }

    }


    /**
     * Hit the web service and get the products in stock
     */


    public class GetStockProductAsyncTask extends AsyncTask<String, String, String> {

        OkHttpClient httpClient = new OkHttpClient();
        String resPonse = "";
        String message = "";
        String str_client_id;

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

//                al_str_key.add(GlobalConstants.BRANCH);
//                al_str_value.add(str_branch);

                al_str_key.add(GlobalConstants.ACTION);
                al_str_value.add("get_stock_request"); //get_products_in_stock

                Log.i("Key",""+al_str_key);

                Log.i("Value",""+al_str_value);

                message = ws.GetProductStockService(context, al_str_key, al_str_value);  //GetProductStockService

            } catch (Exception e) {
                e.printStackTrace();
            }

            return "";
        }

        @Override
        protected void onPostExecute(String s) {

            gif_loader.setVisibility(View.INVISIBLE);

            if (message.equalsIgnoreCase("true")) {

                stockAdapter = new StockAdapter(context,"");
                stock_list.setAdapter(stockAdapter);
                stockAdapter.notifyDataSetChanged();

            }
            else {
                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

    }


    /**
     * Show loader
     * @param context
     */

    public void startLoader(Context context)
    {
        gif_loader.setVisibility(View.VISIBLE);

    }

    /**
     * Invisible loader
     * @param context
     */

    public void stopLoader(Context context)
    {
        gif_loader.setVisibility(View.INVISIBLE);
    }


    /**
     * Get the updated stock list
     * @param context
     */


    public void updateList(Context context)
    {
        new GetStockProductAsyncTask().execute();
    }




}
