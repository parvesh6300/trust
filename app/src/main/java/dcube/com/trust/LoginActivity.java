package dcube.com.trust;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import WebServicesHandler.GlobalConstants;
import WebServicesHandler.WebServices;
import dcube.com.trust.utils.Global;
import okhttp3.OkHttpClient;
import pl.droidsonroids.gif.GifTextView;

public class LoginActivity extends Activity implements View.OnClickListener {

    static String role = "nurse";
    RelativeLayout nurse;
    RelativeLayout finance;
    ImageView nurse_radio;
    ImageView finance_radio;
    TextView nurse_text;
    TextView finance_text;
    TextView forgot;
    TextView signin;
    EditText ed_user_name;
    EditText ed_pwd;

    String str_user_name;
    String str_password;
    String str_device_token;

    GifTextView gif_loader;

    WebServices ws;

    Context context = LoginActivity.this;


    Global global;

    ArrayList<HashMap<String,String>> al_login_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        global = (Global) getApplicationContext();

        nurse = (RelativeLayout) findViewById(R.id.nurse);
        finance = (RelativeLayout) findViewById(R.id.finance);

        ed_user_name = (EditText) findViewById(R.id.ed_user_name);
        ed_pwd = (EditText) findViewById(R.id.ed_pwd);

        gif_loader = (GifTextView) findViewById(R.id.gif_loader);

        nurse_radio = (ImageView) findViewById(R.id.nurse_radio);
        finance_radio = (ImageView) findViewById(R.id.finance_radio);
        nurse_text = (TextView) findViewById(R.id.nurse_text);
        finance_text = (TextView) findViewById(R.id.finance_text);
        forgot = (TextView) findViewById(R.id.forgot);
        signin = (TextView) findViewById(R.id.signin);
        forgot.setPaintFlags(forgot.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        nurse.setOnClickListener(this);
        finance.setOnClickListener(this);
        signin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.nurse: {
                nurse_radio.setImageResource(R.drawable.radioselected);
                nurse_text.setTextColor(getResources().getColor(R.color.textColor));
                finance_radio.setImageResource(R.drawable.radiounselected);
                finance_text.setTextColor(getResources().getColor(R.color.greyed_out));

                role = "nurse";

                break;
            }

            case R.id.finance: {

                finance_radio.setImageResource(R.drawable.radioselected);
                finance_text.setTextColor(getResources().getColor(R.color.textColor));
                nurse_radio.setImageResource(R.drawable.radiounselected);
                nurse_text.setTextColor(getResources().getColor(R.color.greyed_out));

                role = "finance";
                break;
            }

            case R.id.signin: {
                Intent i;

                if (role.equals("nurse")) {

                    str_user_name = ed_user_name.getText().toString().trim();
                    str_password = ed_pwd.getText().toString().trim();

                    str_device_token = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                            Settings.Secure.ANDROID_ID);

                    if (isOnline())
                    {
                        new OkHttpHandlerAsyncTask().execute();
                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    i = new Intent(LoginActivity.this, FinanceHomeActivity.class);
                    startActivity(i);
                }

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


    public class OkHttpHandlerAsyncTask extends AsyncTask<String, String, String> {

        OkHttpClient httpClient = new OkHttpClient();
        String resPonse = "";
        String message ="";

        @Override
        protected void onPreExecute() {

            gif_loader.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            try {

                ArrayList<String> al_str_key = new ArrayList<>();
                ArrayList<String> al_str_value = new ArrayList<>();

                al_str_key.add(GlobalConstants.LOGIN_USER_NAME);
                al_str_value.add(str_user_name);

                al_str_key.add(GlobalConstants.LOGIN_PASSWORD);
                al_str_value.add(str_password);

                al_str_key.add(GlobalConstants.LOGIN_DEVICE_TYPE);
                al_str_value.add("android");

                al_str_key.add(GlobalConstants.LOGIN_DEVICE_TOKEN);
                al_str_value.add(str_device_token);

                al_str_key.add(GlobalConstants.LOGIN_ACTION);
                al_str_value.add("login");

                message = ws.LoginService(context, al_str_key, al_str_value);

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
                Toast.makeText(LoginActivity.this, ""+message, Toast.LENGTH_SHORT).show();
            }
            else
            {
                Intent i = new Intent(LoginActivity.this, NurseHomeActivity.class);
                startActivity(i);
                finish();
            }

        }

    }

}
