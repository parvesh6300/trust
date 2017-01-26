package dcube.com.trust;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import dcube.com.trust.utils.UpdatePlanAdapter;

public class UpdatePlanActivity extends Activity {

    ListView lv_products,lv_services;
    TextView tv_update_plan;

    UpdatePlanAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_update_plan);

        tv_update_plan = (TextView) findViewById(R.id.tv_update_plan);

        lv_products = (ListView) findViewById(R.id.lv_products);
        lv_services = (ListView) findViewById(R.id.lv_services);

        adapter = new UpdatePlanAdapter(UpdatePlanActivity.this);
        lv_products.setAdapter(adapter);


    }


}
