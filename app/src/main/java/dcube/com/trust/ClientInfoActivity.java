package dcube.com.trust;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import WebServicesHandler.CheckNetConnection;
import WebServicesHandler.GlobalConstants;
import WebServicesHandler.WebServices;
import dcube.com.trust.utils.Global;
import okhttp3.OkHttpClient;
import pl.droidsonroids.gif.GifTextView;

public class ClientInfoActivity extends Activity {

    TextView tv_name,tv_branch,tv_contact,tv_age,tv_his_contra;
    EditText ed_med_history;

    Context context = this;
    GifTextView gif_loader;

    CheckNetConnection cn;
    Global global;

    String str_client_id;

    WebServices ws;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_info);

        context = this;

        global = (Global) getApplicationContext();

        cn = new CheckNetConnection(this);

        gif_loader = (GifTextView) findViewById(R.id.gif_loader);

        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_branch = (TextView) findViewById(R.id.tv_branch);
        tv_contact = (TextView) findViewById(R.id.tv_contact);
        tv_age = (TextView) findViewById(R.id.tv_age);
        tv_his_contra = (TextView) findViewById(R.id.tv_his_contra);

        ed_med_history = (EditText) findViewById(R.id.ed_med_history);


        str_client_id = global.getAl_src_client_details().get(global.getSelected_client()).get(GlobalConstants.SRC_CLIENT_ID);


        if (cn.isNetConnected())
        {
            new GetClientInfoAsyncTask().execute();
        }

    }




    /**
     *  Get Client Info
     */

    public class GetClientInfoAsyncTask extends AsyncTask<String, String, String> {

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

                al_str_key.add(GlobalConstants.ACTION);
                al_str_value.add("get_client_info");

                for (int i =0 ; i < al_str_value.size() ; i++)
                {
                    Log.i("Key",al_str_key.get(i));
                    Log.i("Value",al_str_value.get(i));
                }

                message = ws.GetClientInfoService(context, al_str_key, al_str_value);

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
                tv_name.setText(global.getAl_client_info().get(0).get(GlobalConstants.CLIENT_NAME));
                tv_branch.setText(global.getAl_client_info().get(0).get(GlobalConstants.CLIENT_AREA));
                tv_contact.setText(global.getAl_client_info().get(0).get(GlobalConstants.CLIENT_CONTACT));
                tv_age.setText(global.getAl_client_info().get(0).get(GlobalConstants.CLIENT_AGE));
                tv_his_contra.setText(global.getAl_client_info().get(0).get(GlobalConstants.CLIENT_CONTRA_HISTORY));
                ed_med_history.setText(global.getAl_client_info().get(0).get(GlobalConstants.CLIENT_MED_HISTORY));
            }
            else
            {
                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

    }


}
