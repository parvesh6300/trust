package dcube.com.trust;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import WebServicesHandler.GlobalConstants;
import WebServicesHandler.WebServices;
import dcube.com.trust.utils.Global;
import dcube.com.trust.utils.StockAdapter;
import okhttp3.OkHttpClient;
import pl.droidsonroids.gif.GifTextView;

public class StockControlActivity extends Activity {

    TextView tv_quantity;

    ListView stock_list;

    int i;

    StockAdapter stockAdapter;

    Global global;
    GifTextView gif_loader;
    WebServices ws;
    Context context = StockControlActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_stock_control);

        global = (Global) getApplicationContext();

        tv_quantity = (TextView) findViewById(R.id.tv_quantity);

        stock_list = (ListView) findViewById(R.id.stock_list);
        gif_loader = (GifTextView) findViewById(R.id.gif_loader);


        if (isOnline())
        {
            new GetStockProductAsyncTask().execute();
        }
        else
        {
            Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
        }

    }


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

                al_str_key.add(GlobalConstants.ACTION);
                al_str_value.add("get_products_in_stock");

                message = ws.GetProductStockService(context, al_str_key, al_str_value);  //GetProductStockService

            } catch (Exception e) {
                e.printStackTrace();
            }

            return "";
        }

        @Override
        protected void onPostExecute(String s) {

            if (message.equalsIgnoreCase("true")) {

                gif_loader.setVisibility(View.GONE);

                stockAdapter = new StockAdapter(context);
                stock_list.setAdapter(stockAdapter);
                stockAdapter.notifyDataSetChanged();

            } else {
                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }


}
