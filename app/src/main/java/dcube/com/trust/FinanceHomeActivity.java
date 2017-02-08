package dcube.com.trust;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import WebServicesHandler.GlobalConstants;
import dcube.com.trust.utils.FinanceAdapter;
import dcube.com.trust.utils.Global;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_finance_home);

        adapter = new FinanceAdapter(this);

        global = (Global) getApplicationContext();

        gridView = (GridView) findViewById(R.id.grid_view);
        gridView.setAdapter(adapter);

        tv_user_name = (TextView) findViewById(R.id.tv_user_name);
        tv_logout = (TextView) findViewById(R.id.tv_logout);

        tv_user_name.setText("Hi, "+global.getAl_login_list().get(0).get(GlobalConstants.USER_NAME));

        global.setStr_branch_balance("0");
        global.setStr_total_sale("0");

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
                        startActivity(new Intent(FinanceHomeActivity.this,WithDrawMoneyActivity.class));//ProductSoldHistory
                        break;

                    case 2:
                        startActivity(new Intent(FinanceHomeActivity.this,StockControlActivity.class));//DepositMoneyActivity
                        break;

                    case 3:
                        startActivity(new Intent(FinanceHomeActivity.this,DepositMoneyActivity.class)); // HelloChartActivity
                        break;

                    case 4:
                        startActivity(new Intent(FinanceHomeActivity.this,AccountHistoryActivity.class));//WithDrawMoneyActivity
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


    }


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



    public void setSharedPreferences()
    {
        pref = getSharedPreferences(login_pref,MODE_PRIVATE);

        editor = pref.edit();

        editor.putBoolean(is_logged_in_pref,false);

        editor.apply();
    }





}
