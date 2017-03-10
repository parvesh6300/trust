package dcube.com.trust;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import WebServicesHandler.CheckNetConnection;
import WebServicesHandler.GlobalConstants;
import WebServicesHandler.HideKeyboard;
import WebServicesHandler.WebServices;
import dcube.com.trust.utils.Global;
import okhttp3.OkHttpClient;
import pl.droidsonroids.gif.GifTextView;

public class LoginActivity extends Activity implements View.OnClickListener,View.OnTouchListener {

    static String role;
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

    int role_id = 2;

    WebServices ws;

    Context context = LoginActivity.this;

    Global global;

    ArrayList<HashMap<String, String>> al_login_user;

    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;

    String login_pref = "Login_pref";
    String is_logged_in_pref = "Logged_in_pref";
    String user_name_pref = "user_name";
    String password_pref = "password";
    String role_key = "role";

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    RelativeLayout rel_parent_layout;

    CheckNetConnection cn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        global = (Global) getApplicationContext();

        cn = new CheckNetConnection(this);

        role = "nurse";

        nurse = (RelativeLayout) findViewById(R.id.nurse);
        finance = (RelativeLayout) findViewById(R.id.finance);

        ed_user_name = (EditText) findViewById(R.id.ed_user_name);
        ed_pwd = (EditText) findViewById(R.id.ed_pwd);

        rel_parent_layout = (RelativeLayout) findViewById(R.id.rel_parent_layout);

        gif_loader = (GifTextView) findViewById(R.id.gif_loader);

        nurse_radio = (ImageView) findViewById(R.id.nurse_radio);
        finance_radio = (ImageView) findViewById(R.id.finance_radio);
        nurse_text = (TextView) findViewById(R.id.nurse_text);
        finance_text = (TextView) findViewById(R.id.finance_text);
        forgot = (TextView) findViewById(R.id.forgot);
        signin = (TextView) findViewById(R.id.signin);
        forgot.setPaintFlags(forgot.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);


//        rel_parent_layout.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//
//                HideKeyboard.hideSoftKeyboard(LoginActivity.this);
//                return false;
//            }
//        });


        nurse.setOnClickListener(this);
        finance.setOnClickListener(this);
        signin.setOnClickListener(this);
        rel_parent_layout.setOnClickListener(this);
        //rel_parent_layout.setOnTouchListener(this);
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
                role_id = 2;

                break;
            }

            case R.id.finance: {

                finance_radio.setImageResource(R.drawable.radioselected);
                finance_text.setTextColor(getResources().getColor(R.color.textColor));
                nurse_radio.setImageResource(R.drawable.radiounselected);
                nurse_text.setTextColor(getResources().getColor(R.color.greyed_out));

                role_id = 3;
                role = "finance";
                break;
            }

            case R.id.signin: {

                Log.i("Sign","in clicked");

                if (validate())
                {
                    str_user_name = ed_user_name.getText().toString().trim();
                    str_password = ed_pwd.getText().toString().trim();

                    str_device_token = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                            Settings.Secure.ANDROID_ID);

                    if (cn.isNetConnected())
                    {
                        new OkHttpHandlerAsyncTask().execute();
                    }
                    else {
                        Toast.makeText(LoginActivity.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            case R.id.rel_parent_layout:
            {
                if (ed_pwd.isClickable() || ed_user_name.isClickable()  || ed_pwd.isFocusable() || ed_user_name.isFocusable())
                {
                    HideKeyboard.hideSoftKeyboard(LoginActivity.this);
                }


            }

        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (v==rel_parent_layout)
        {
            HideKeyboard.hideSoftKeyboard(LoginActivity.this);

        }

        return false;
    }


    /**
     * Hit web service and check user exists or not
     */

    public class OkHttpHandlerAsyncTask extends AsyncTask<String, String, String> {

        OkHttpClient httpClient = new OkHttpClient();
        String resPonse = "";
        String message = "";

        @Override
        protected void onPreExecute() {

            gif_loader.setVisibility(View.VISIBLE);

            signin.setClickable(false);
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

                al_str_key.add(GlobalConstants.ACTION);
                al_str_value.add("login");

                message = ws.LoginService(context, al_str_key, al_str_value);


            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            gif_loader.setVisibility(View.INVISIBLE);

            signin.setClickable(true);

            if (message.equalsIgnoreCase("true"))
            {
                setSharedPreferences();

                if (role.equalsIgnoreCase(global.getAl_login_list().get(0).get(GlobalConstants.USER_ROLE)) )  //role_id == 2
                {
                    Intent i = new Intent(LoginActivity.this, NurseHomeActivity.class);
                    startActivity(i);
                    finish();
                }

                else if (role.equalsIgnoreCase(global.getAl_login_list().get(0).get(GlobalConstants.USER_ROLE)))  //role_id == 3
                {
                    Intent i = new Intent(LoginActivity.this, FinanceHomeActivity.class);
                    startActivity(i);
                    finish();
                }
                else if (global.getAl_login_list().get(0).get(GlobalConstants.USER_ROLE).equalsIgnoreCase("nurse_finance"))
                {
                    if (role.equalsIgnoreCase("nurse"))
                    {
                        Intent i = new Intent(LoginActivity.this, NurseHomeActivity.class);
                        startActivity(i);
                        finish();
                    }
                    else
                    {
                        Intent i = new Intent(LoginActivity.this, FinanceHomeActivity.class);
                        startActivity(i);
                        finish();
                    }
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Verify your Role again", Toast.LENGTH_SHORT).show();
                }

            }
            else {

                Toast.makeText(LoginActivity.this, "" + message, Toast.LENGTH_SHORT).show();

            }

        }

    }


    /**
     * User tap two times to close the app
     */

    @Override
    public void onBackPressed() {

        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
        {
            super.onBackPressed();
            return;
        }
        else
        {
            Toast.makeText(getBaseContext(), "Tap back button in order to exit", Toast.LENGTH_SHORT).show();
        }

        mBackPressed = System.currentTimeMillis();

    }


    /**
     * Save login details in shared preferences
     */

    public void setSharedPreferences()
    {
        pref = getSharedPreferences(login_pref,MODE_PRIVATE);

        editor = pref.edit();

        editor.putBoolean(is_logged_in_pref,true);

        editor.putString(user_name_pref,ed_user_name.getText().toString().trim());
        editor.putString(password_pref, ed_pwd.getText().toString().trim());
        editor.putString(role_key , String.valueOf(role_id));

        editor.apply();
    }


    /**
     * Checks whether user has entered the required fields
     * @return boolean
     */

    public boolean validate()
    {
        if (ed_user_name.getText().toString().trim().matches(""))
        {
            Toast.makeText(LoginActivity.this, "Enter Username", Toast.LENGTH_SHORT).show();
        }
        else if (ed_pwd.getText().toString().trim().matches(""))
        {
            Toast.makeText(LoginActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
        }
        else {
            return true;
        }

        return false;

    }


}


