package dcube.com.trust;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import WebServicesHandler.GlobalConstants;
import WebServicesHandler.WebServices;
import dcube.com.trust.utils.Global;

/**
 * Created by Rohit on 21/02/17.
 */


public class TotalBranchBalance extends AsyncTask<String,String,String> {

    String str_balance;

    Context context;

    Global global;

    String message = "";

    WebServices ws;


    public TotalBranchBalance(Context ctx)
    {
        context = ctx;
    }



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
}
