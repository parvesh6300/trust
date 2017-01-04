package dcube.com.trust;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import WebServicesHandler.GlobalConstants;
import WebServicesHandler.WebServices;
import dcube.com.trust.utils.Global;
import dcube.com.trust.utils.NurseAdapter;

public class NurseHomeActivity extends Activity {

    GridView gridView;
    NurseAdapter adapter;

    TextView tv_user_name,tv_logout;

    Global global;

    String str_client_id;

    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;

    String login_pref = "Login_pref";
    String is_logged_in_pref = "Logged_in_pref";

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    WebServices ws;

    Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse_home);

        global = (Global) getApplicationContext();

        adapter = new NurseAdapter(this);

        tv_user_name = (TextView) findViewById(R.id.tv_user_name);
        tv_logout = (TextView) findViewById(R.id.tv_logout);

        gridView = (GridView) findViewById(R.id.grid_view);
        gridView.setAdapter(adapter);

        tv_user_name.setText("Hi, "+global.getAl_login_list().get(0).get(GlobalConstants.USER_NAME));
        

        global.setAppointmentSelected(false);

        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setSharedPreferences();
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

                        startActivity(new Intent(NurseHomeActivity.this,NurseProductActivity.class));
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
