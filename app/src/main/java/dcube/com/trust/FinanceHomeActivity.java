package dcube.com.trust;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import WebServicesHandler.GlobalConstants;
import dcube.com.trust.utils.FinanceAdapter;
import dcube.com.trust.utils.Global;

public class FinanceHomeActivity extends Activity {

    GridView gridView;
    FinanceAdapter adapter;
    TextView tv_user_name,tv_logout;
    Global global;


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

        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(FinanceHomeActivity.this,LoginActivity.class));
                finish();
                global.getAl_login_list().clear();
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                switch (i)
                {
                    case 0:
                        startActivity(new Intent(FinanceHomeActivity.this,StockControl.class));
                        break;

                    case 1:
                        startActivity(new Intent(FinanceHomeActivity.this,ProductSoldHistory.class));
                        break;

                    case 2:
                        startActivity(new Intent(FinanceHomeActivity.this,DepositMoneyActivity.class));
                        break;

                    case 3:
                        startActivity(new Intent(FinanceHomeActivity.this,TotalRevenue.class));
                        break;

                    case 4:
                        startActivity(new Intent(FinanceHomeActivity.this,WithDrawMoneyActivity.class));
                        break;

                    case 5:
                        startActivity(new Intent(FinanceHomeActivity.this,AddExpenseActivity.class));
                        break;

                    case 6:
                        startActivity(new Intent(FinanceHomeActivity.this,MoneyBankedActivity.class));
                        break;

                    case 7:
                        startActivity(new Intent(FinanceHomeActivity.this,CartActivity.class));
                        break;

                }
            }
        });

    }
}
