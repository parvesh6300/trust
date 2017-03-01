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
import dcube.com.trust.utils.ClientHistoryAdapter;
import dcube.com.trust.utils.Global;
import okhttp3.OkHttpClient;
import pl.droidsonroids.gif.GifTextView;

public class ClientHistoryActivity extends Activity {

    ListView list_history;

    ClientHistoryAdapter adapter;

    Context ctx = this;

    TextView tv_submit;

    WebServices ws;

    CheckNetConnection cn;

    Global global;

    GifTextView gif_loader;

    String str_client_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_history);

        global = (Global) getApplicationContext();

        cn = new CheckNetConnection(this);

        tv_submit = (TextView) findViewById(R.id.tv_submit);

        gif_loader = (GifTextView) findViewById(R.id.gif_loader);

        list_history = (ListView) findViewById(R.id.list_history);

        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        str_client_id = global.getAl_src_client_details().get(global.getSelected_client()).get(GlobalConstants.SRC_CLIENT_ID);


        if (cn.isNetConnected())
        {
            new ClientHistoryAsyncTask().execute();
        }
        else
        {
            Toast.makeText(ctx, "Check Internet Connection", Toast.LENGTH_SHORT).show();
        }

    }




    /**
     * Calls the web service and get account history
     */

    public class ClientHistoryAsyncTask extends AsyncTask<String, String, String> {

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

                al_str_key.add(GlobalConstants.ACTION);
                al_str_value.add("client_sales_history");

                for (int i =0 ; i < al_str_key.size() ; i++)
                {
                    Log.i("Key",""+ al_str_key.get(i));
                    Log.i("Value",""+ al_str_value.get(i));
                }

                message = ws.ClientHistoryService(ctx, al_str_key, al_str_value);

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
                adapter= new ClientHistoryAdapter(ClientHistoryActivity.this);
                list_history.setAdapter(adapter);
            }
            else
            {
                Toast.makeText(ctx, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

    }






}
