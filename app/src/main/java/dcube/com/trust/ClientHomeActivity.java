package dcube.com.trust;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import WebServicesHandler.GlobalConstants;
import dcube.com.trust.utils.ClientAdapter;
import dcube.com.trust.utils.Global;

public class ClientHomeActivity extends Activity {

    ClientAdapter adapter;
    GridView gridView;
    ImageView iv_cart;
    TextView tv_user_name;
    Global global;

    TextView tv_logout;

    String login_pref = "Login_pref";
    String is_logged_in_pref = "Logged_in_pref";

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_home);

        global = (Global) getApplicationContext();

        iv_cart = (ImageView) findViewById(R.id.iv_cart);
        tv_logout = (TextView) findViewById(R.id.tv_logout);
        tv_user_name = (TextView) findViewById(R.id.tv_user_name) ;

        adapter = new ClientAdapter(this);

        gridView = (GridView) findViewById(R.id.grid_view);
        gridView.setAdapter(adapter);

        tv_user_name.setText("Hi, "+global.getAl_login_list().get(0).get(GlobalConstants.USER_NAME));

        iv_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(ClientHomeActivity.this,CartActivity.class));
            }
        });


        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setSharedPreferences();
                startActivity(new Intent(ClientHomeActivity.this,LoginActivity.class));
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

                        startActivity(new Intent(ClientHomeActivity.this,AddAppointmentActivity.class));
                        break;

                    case 1:

                        startActivity(new Intent(ClientHomeActivity.this,ViewAppointmentActivity.class));
                        break;

                    case 2:

                        startActivity(new Intent(ClientHomeActivity.this,BuyProductActivity.class));
                        break;

                    case 3:

                        startActivity(new Intent(ClientHomeActivity.this,BuyPlanActivity.class));
                        break;

                    case 4:

                        startActivity(new Intent(ClientHomeActivity.this,ViewPlanActivity.class));
                        break;

                    case 5:

                        startActivity(new Intent(ClientHomeActivity.this,BuyServicesActivity.class));
                        break;

                    case 6:

                        startActivity(new Intent(ClientHomeActivity.this,ViewServicesActivity.class));
                        break;

                    case 7:

                        startActivity(new Intent(ClientHomeActivity.this,GenerateInvoiceActivity.class));
                        break;

                    case 8:

                        startActivity(new Intent(ClientHomeActivity.this,ViewPendingPaymentActivity.class));
                        break;
                }
            }
        });
    }

    public void setSharedPreferences()
    {
        pref = getSharedPreferences(login_pref,MODE_PRIVATE);

        editor = pref.edit();

        editor.putBoolean(is_logged_in_pref,false);

        editor.apply();
    }

}
