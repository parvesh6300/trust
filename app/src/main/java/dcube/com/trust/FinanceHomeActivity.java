package dcube.com.trust;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import dcube.com.trust.utils.FinanceAdapter;

public class FinanceHomeActivity extends Activity {

    GridView gridView;
    FinanceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_finance_home);

        adapter = new FinanceAdapter(this);

        gridView = (GridView) findViewById(R.id.grid_view);
        gridView.setAdapter(adapter);

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
