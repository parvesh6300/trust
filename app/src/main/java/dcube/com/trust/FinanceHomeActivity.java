package dcube.com.trust;

import android.app.Activity;
import android.os.Bundle;
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
    }
}
