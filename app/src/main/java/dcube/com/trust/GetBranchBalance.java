package dcube.com.trust;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import WebServicesHandler.GlobalConstants;
import WebServicesHandler.WebServices;
import dcube.com.trust.utils.Global;
import okhttp3.OkHttpClient;

/**
 * Created by Rohit on 20/01/17.
 */

public class GetBranchBalance  {

    String str_balance;

    Context context;

    Global global;

    String message = "";

    WebServices ws;


    public GetBranchBalance(Context mcontext)
    {
        this.context = mcontext;

        global = (Global) context.getApplicationContext();
    }


    public String branchBalance()
    {
        new GetBranchBalanceAsyncTask().execute();

        Log.e("Branch","Balance in global"+str_balance);

        return str_balance;
    }


    public String getBalance()
    {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    ArrayList<String> al_str_key = new ArrayList<>();
                    ArrayList<String> al_str_value = new ArrayList<>();

                    al_str_key.add(GlobalConstants.USER_BRANCH_ID);
                    al_str_value.add(global.getAl_login_list().get(0).get(GlobalConstants.USER_BRANCH_ID));

                    al_str_key.add(GlobalConstants.ACTION);
                    al_str_value.add("get_branch_balance");

                    for (int i =0 ; i < al_str_key.size() ; i++)
                    {
                        Log.i("Key",""+ al_str_key.get(i));
                        Log.i("Value",""+ al_str_value.get(i));
                    }

                    message = ws.GetBranchBalanceService(context, al_str_key, al_str_value);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        thread.start();

        if (message.equalsIgnoreCase("true"))
        {
            return global.getStr_branch_balance();
        }

        else
        {
            return "0";
        }

    }



    public String getBranchBal()
    {

        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {


                try {

                    ArrayList<String> al_str_key = new ArrayList<>();
                    ArrayList<String> al_str_value = new ArrayList<>();

                    al_str_key.add(GlobalConstants.USER_BRANCH_ID);
                    al_str_value.add(global.getAl_login_list().get(0).get(GlobalConstants.USER_BRANCH_ID));

                    al_str_key.add(GlobalConstants.ACTION);
                    al_str_value.add("get_branch_balance");

                    for (int i =0 ; i < al_str_key.size() ; i++)
                    {
                        Log.i("Key",""+ al_str_key.get(i));
                        Log.i("Value",""+ al_str_value.get(i));
                    }

                    message = ws.GetBranchBalanceService(context, al_str_key, al_str_value);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }


            @Override
            protected void onPostExecute(String s) {

                if (message.equalsIgnoreCase("true"))
                {
                    Log.e("Branch","Balance in global"+global.getStr_branch_balance());
                    str_balance = global.getStr_branch_balance();

                }
                else
                {
                    Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
                }

            }
        }.execute();


        Log.i("Branch","Bal "+str_balance);

        return str_balance;
    }



    public class GetBranchBalanceAsyncTask extends AsyncTask<String, String, String>
    {

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

                al_str_key.add(GlobalConstants.USER_BRANCH_ID);
                al_str_value.add(global.getAl_login_list().get(0).get(GlobalConstants.USER_BRANCH_ID));

                al_str_key.add(GlobalConstants.ACTION);
                al_str_value.add("get_branch_balance");

                for (int i =0 ; i < al_str_key.size() ; i++)
                {
                    Log.i("Key",""+ al_str_key.get(i));
                    Log.i("Value",""+ al_str_value.get(i));
                }

                message = ws.GetBranchBalanceService(context, al_str_key, al_str_value);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            if (message.equalsIgnoreCase("true"))
            {
                Log.e("Branch","Balance in global"+global.getStr_branch_balance());
                str_balance = global.getStr_branch_balance();

            }
            else {
                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

    }


}
