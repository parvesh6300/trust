package dcube.com.trust;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import java.util.ArrayList;

import WebServicesHandler.CheckNetConnection;
import WebServicesHandler.GlobalConstants;
import WebServicesHandler.WebServices;
import dcube.com.trust.utils.Global;
import okhttp3.OkHttpClient;

public class SplashActivity extends Activity {

    Context context = SplashActivity.this;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    String login_pref = "Login_pref";

    String fst_login = "is_First_Login";
    String is_logged_in_pref = "Logged_in_pref";

    String user_name_pref = "user_name";
    String password_pref = "password";
    String role_key = "role";

    Boolean is_first_login= true ;
    Boolean is_logged_in = false;

    WebServices ws;
    Global global;

    String str_username,str_password,str_role,str_device_token;
    CheckNetConnection cn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getSharePreferences();

        setSharedPreferences();

        global = (Global) getApplicationContext();

        cn = new CheckNetConnection(this);

        Thread timerThread = new Thread(){
            public void run(){

                try{
                    sleep(2000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{

                    if (is_logged_in)
                    {
                        str_device_token = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                                Settings.Secure.ANDROID_ID);

//                        if (isOnline())
//                        {
//                            new OkHttpHandlerAsyncTask().execute();
//                        }
//                        else {
//                            Toast.makeText(SplashActivity.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
//                        }


                        if (cn.isNetConnected())
                        {
                            new OkHttpHandlerAsyncTask().execute();
                        }
                        else {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(SplashActivity.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                                }
                            });

                          }


                    }
                    else
                    {
                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }


                }
            }
        };
        timerThread.start();
    }





    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }


    public void setSharedPreferences()
    {
        pref = getSharedPreferences(login_pref,MODE_PRIVATE);

        editor = pref.edit();

        editor.putBoolean(fst_login,false);

        editor.apply();
    }


    public void getSharePreferences()
    {
        pref = getSharedPreferences(login_pref,MODE_PRIVATE);

        is_first_login = pref.getBoolean(fst_login,true);

        is_logged_in = pref.getBoolean(is_logged_in_pref,false);

        str_username = pref.getString(user_name_pref,"rohit@gmail.com");
        str_password = pref.getString(password_pref,"123");
        str_role = pref.getString(role_key,"2");

    }



    public class OkHttpHandlerAsyncTask extends AsyncTask<String, String, String> {

        OkHttpClient httpClient = new OkHttpClient();
        String resPonse = "";
        String message = "";

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... params) {
            try {

                ArrayList<String> al_str_key = new ArrayList<>();
                ArrayList<String> al_str_value = new ArrayList<>();

                al_str_key.add(GlobalConstants.LOGIN_USER_NAME);
                al_str_value.add(str_username);

                al_str_key.add(GlobalConstants.LOGIN_PASSWORD);
                al_str_value.add(str_password);

//                al_str_key.add(GlobalConstants.LOGIN_USER_ROle);
//
//                if (str_role.equalsIgnoreCase("2")) {
//                    al_str_value.add(String.valueOf(2));
//                }
//                else {
//                    al_str_value.add(String.valueOf(3));
//                }

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

            if (message.equalsIgnoreCase("true")) {

                setSharedPreferences();

                if ("nurse".equalsIgnoreCase(global.getAl_login_list().get(0).get(GlobalConstants.USER_ROLE)))  //role_id == 2
                {
                    Intent i = new Intent(SplashActivity.this, NurseHomeActivity.class);
                    startActivity(i);
                    finish();

                }
                else if ("finance".equalsIgnoreCase(global.getAl_login_list().get(0).get(GlobalConstants.USER_ROLE)))  //role_id == 3
                {
                    Intent i = new Intent(SplashActivity.this, FinanceHomeActivity.class);
                    startActivity(i);
                    finish();

                }
                else if (global.getAl_login_list().get(0).get(GlobalConstants.USER_ROLE).equalsIgnoreCase("nurse_finance"))
                {
                    Intent i = new Intent(SplashActivity.this, NurseHomeActivity.class);  //FinanceHomeActivity
                    startActivity(i);
                    finish();
                }
                else
                {
                    Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                }

            }

            else
            {
                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
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
