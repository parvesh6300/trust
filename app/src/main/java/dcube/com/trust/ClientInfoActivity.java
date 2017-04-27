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

    TextView tv_name,tv_branch,tv_contact,tv_age,tv_his_contra,tv_submit;
    EditText ed_med_history;

    Context context = this;
    GifTextView gif_loader;

    CheckNetConnection cn;
    Global global;

    String str_client_id;

    WebServices ws;

    //***** Updations ******************

    TextView tv_sex,tv_emer_contact,tv_emer_rel,tv_children,tv_hiv_test;

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
        tv_submit = (TextView) findViewById(R.id.tv_submit);

        tv_sex = (TextView) findViewById(R.id.tv_sex);
        tv_emer_contact = (TextView) findViewById(R.id.tv_emer_contact);
        tv_emer_rel = (TextView) findViewById(R.id.tv_emer_rel);
        tv_children = (TextView) findViewById(R.id.tv_children);
        tv_hiv_test = (TextView) findViewById(R.id.tv_hiv_test);



        ed_med_history = (EditText) findViewById(R.id.ed_med_history);

        str_client_id = global.getAl_src_client_details().get(global.getSelected_client()).get(GlobalConstants.SRC_CLIENT_ID);

        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        if (cn.isNetConnected())
        {
            new GetClientInfoAsyncTask().execute();
        }
        else
        {
            Toast.makeText(ClientInfoActivity.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
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

            gif_loader.setVisibility(View.INVISIBLE);

            if (message.equalsIgnoreCase("true"))
            {
                tv_name.setText(global.getAl_client_info().get(0).get(GlobalConstants.CLIENT_NAME));
                tv_branch.setText(global.getAl_client_info().get(0).get(GlobalConstants.CLIENT_AREA));
                tv_contact.setText(global.getAl_client_info().get(0).get(GlobalConstants.CLIENT_CONTACT));
                tv_age.setText(global.getAl_client_info().get(0).get(GlobalConstants.CLIENT_AGE));
                tv_his_contra.setText(global.getAl_client_info().get(0).get(GlobalConstants.CLIENT_CONTRA_HISTORY));
                ed_med_history.setText(global.getAl_client_info().get(0).get(GlobalConstants.CLIENT_MED_HISTORY));

                tv_sex.setText(global.getAl_client_info().get(0).get(GlobalConstants.CLIENT_SEX));
                tv_emer_contact.setText(global.getAl_client_info().get(0).get(GlobalConstants.CLIENT_EMER_CONTACT));
                tv_emer_rel.setText(global.getAl_client_info().get(0).get(GlobalConstants.CLIENT_EMER_RELATION));
                tv_children.setText(global.getAl_client_info().get(0).get(GlobalConstants.CLIENT_CHILD));
                tv_hiv_test.setText(global.getAl_client_info().get(0).get(GlobalConstants.CLIENT_HIV_TEST));


            }
            else
            {
                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

    }


}
