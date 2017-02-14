package dcube.com.trust;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
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
import dcube.com.trust.utils.AccountHistoryAdapter;
import dcube.com.trust.utils.ExpenseAdapter;
import dcube.com.trust.utils.Global;
import okhttp3.OkHttpClient;
import pl.droidsonroids.gif.GifTextView;

public class AccountHistoryActivity extends Activity {

    ListView list_account;

    ExpenseAdapter expenseAdapter;

    TextView tv_total_amount;

    TextView tv_submit;

    EditText ed_expense_amount,ed_reason;
    GifTextView gif_loader;
    Global global;
    String str_amount,str_reason,str_branch;
    WebServices ws;
    Context context = AccountHistoryActivity.this;

    AccountHistoryAdapter adapter;

    RelativeLayout rel_parent_layout;

    CheckNetConnection cn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_account_history);

        global = (Global) getApplicationContext();

        cn = new CheckNetConnection(this);

        rel_parent_layout = (RelativeLayout) findViewById(R.id.rel_parent_layout);

        gif_loader = (GifTextView) findViewById(R.id.gif_loader);

        list_account= (ListView)findViewById(R.id.list_expense);

        ed_expense_amount=(EditText)findViewById(R.id.ed_expense_amount);
        ed_reason=(EditText)findViewById(R.id.ed_reason);
        tv_total_amount=(TextView)findViewById(R.id.tv_total_amount);
        tv_submit=(TextView)findViewById(R.id.tv_submit);

        str_branch = global.getAl_login_list().get(0).get(GlobalConstants.USER_BRANCH);

        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });


        if (cn.isNetConnected())
        {
            new GetBranchBalanceAsyncTask().execute();
            new AccountHistoryAsyncTask().execute();
        }
        else
        {
            Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
        }


        rel_parent_layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                HideKeyboard.hideSoftKeyboard(AccountHistoryActivity.this);

                return false;
            }
        });



    }


    /**
     * Calls the web service and get account history
     */


    public class AccountHistoryAsyncTask extends AsyncTask<String, String, String> {

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

                al_str_key.add(GlobalConstants.ACTION);
                al_str_value.add("account_history");

                for (int i =0 ; i < al_str_key.size() ; i++)
                {
                    Log.e("Key",""+ al_str_key.get(i));
                    Log.e("Value",""+ al_str_value.get(i));
                }

                message = ws.MoneyBankHistoryService(context, al_str_key, al_str_value);

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
                adapter= new AccountHistoryAdapter(AccountHistoryActivity.this);
                list_account.setAdapter(adapter);
            }
            else {
                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

    }



    /**
     * Calls the web service and get current balance of Branch
     */

    public class GetBranchBalanceAsyncTask extends AsyncTask<String, String, String> {

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
                tv_total_amount.setText(global.getStr_branch_balance()+" TZS");
            }
            else {
                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

    }


}
