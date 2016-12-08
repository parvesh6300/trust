package dcube.com.trust;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import WebServicesHandler.GlobalConstants;
import dcube.com.trust.utils.Global;
import dcube.com.trust.utils.NurseAdapter;

public class NurseHomeActivity extends Activity {

    GridView gridView;
    NurseAdapter adapter;
    ImageView iv_cart;
    TextView tv_user_name,tv_logout;

    Global global;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse_home);

        global = (Global) getApplicationContext();

        adapter = new NurseAdapter(this);

        iv_cart = (ImageView) findViewById(R.id.iv_cart);

        tv_user_name = (TextView) findViewById(R.id.tv_user_name);
        tv_logout = (TextView) findViewById(R.id.tv_logout);

        gridView = (GridView) findViewById(R.id.grid_view);
        gridView.setAdapter(adapter);

        tv_user_name.setText("Hi, "+global.getAl_login_list().get(0).get(GlobalConstants.USER_NAME));

        iv_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NurseHomeActivity.this,CartActivity.class));
            }
        });

        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(NurseHomeActivity.this,LoginActivity.class));
                finish();

                global.getAl_login_list().clear();
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                switch(i)
                {
                    case 0:

                        startActivity(new Intent(NurseHomeActivity.this,AddClientActivity.class));
                        break;

                    case 1:

                        startActivity(new Intent(NurseHomeActivity.this,SearchClientActivity.class));
                        break;

                    case 2:

                        startActivity(new Intent(NurseHomeActivity.this,CalendarActivity.class));
                        break;

                    case 3:

                        startActivity(new Intent(NurseHomeActivity.this,ProductActivity.class));
                        break;
                }
            }
        });
    }
}
