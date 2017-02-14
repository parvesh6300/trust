package dcube.com.trust;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import WebServicesHandler.CheckNetConnection;
import WebServicesHandler.GlobalConstants;
import WebServicesHandler.WebServices;
import dcube.com.trust.utils.FinanceAdapter;
import dcube.com.trust.utils.Global;
import okhttp3.OkHttpClient;

public class FinanceHomeActivity extends Activity {

    GridView gridView;
    FinanceAdapter adapter;
    TextView tv_user_name,tv_logout;
    Global global;

    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;

    String login_pref = "Login_pref";
    String is_logged_in_pref = "Logged_in_pref";

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    WebServices ws;
    Context context = this;

    CheckNetConnection cn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_finance_home);

        adapter = new FinanceAdapter(this);

        cn = new CheckNetConnection(context);

        global = (Global) getApplicationContext();

        gridView = (GridView) findViewById(R.id.grid_view);


        tv_user_name = (TextView) findViewById(R.id.tv_user_name);
        tv_logout = (TextView) findViewById(R.id.tv_logout);

        tv_user_name.setText("Hi, "+global.getAl_login_list().get(0).get(GlobalConstants.USER_NAME));

        global.setStr_branch_balance("0");
        global.setStr_total_sale("0");

        gridView.setAdapter(adapter);


        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setSharedPreferences();
                startActivity(new Intent(FinanceHomeActivity.this,LoginActivity.class));
                finish();
                global.getAl_login_list().clear();
            }
        });

//        Order of menu - bank money, operating expense,
//                stock management ,deposit money, account history, expense history, revenue, sales analytics.


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                switch (i)
                {
                    case 0:
                        startActivity(new Intent(FinanceHomeActivity.this,MoneyBankedActivity.class));//StockControlActivity
                        break;

                    case 1:
                        startActivity(new Intent(FinanceHomeActivity.this,OperatingExpensesActivity.class));//ProductSoldHistory
                        break;

                    case 2:
                        startActivity(new Intent(FinanceHomeActivity.this,StockControlActivity.class));//DepositMoneyActivity
                        break;

                    case 3:
                        startActivity(new Intent(FinanceHomeActivity.this,DepositMoneyActivity.class)); // HelloChartActivity
                        break;

                    case 4:
                        startActivity(new Intent(FinanceHomeActivity.this,AccountHistoryActivity.class));//OperatingExpensesActivity
                        break;

                    case 5:
                        startActivity(new Intent(FinanceHomeActivity.this,ExpenseHistoryActivity.class));//ExpenseHistoryActivity
                        break;

                    case 6:
                        startActivity(new Intent(FinanceHomeActivity.this,HelloChartActivity.class));//MoneyBankedActivity
                        break;

                    case 7:
                        startActivity(new Intent(FinanceHomeActivity.this,ProductSoldHistory.class));//AccountHistoryActivity
                        break;

                }
            }
        });



        if (cn.isNetConnected())
        {
            new GetStockProductAsyncTask().execute();
        }
        else
        {
            Toast.makeText(FinanceHomeActivity.this, "Check Net Connection", Toast.LENGTH_SHORT).show();
        }



    }


    /**
     * Tap two times to close the app
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
     * Set login details in shared preferences
     */


    public void setSharedPreferences()
    {
        pref = getSharedPreferences(login_pref,MODE_PRIVATE);

        editor = pref.edit();

        editor.putBoolean(is_logged_in_pref,false);

        editor.apply();
    }


    /**
     * Hit web service and get stock products details
     */


    public class GetStockProductAsyncTask extends AsyncTask<String, String, String> {

        OkHttpClient httpClient = new OkHttpClient();
        String resPonse = "";
        String message = "";
        String str_client_id;

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

//                al_str_key.add(GlobalConstants.BRANCH);
//                al_str_value.add(str_branch);

                al_str_key.add(GlobalConstants.ACTION);
                al_str_value.add("get_stock_request"); //get_products_in_stock

                Log.i("Key",""+al_str_key);
                Log.i("Value",""+al_str_value);

                message = ws.GetProductStockService(context, al_str_key, al_str_value);  //GetProductStockService

            } catch (Exception e) {
                e.printStackTrace();
            }

            return "";
        }

        @Override
        protected void onPostExecute(String s) {

            if (message.equalsIgnoreCase("true"))
            {
                gridView.setAdapter(adapter);
            }

        }

    }


}
