package dcube.com.trust;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import dcube.com.trust.utils.ClientAdapter;

public class ClientHomeActivity extends Activity {

    ClientAdapter adapter;
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_home);

        adapter = new ClientAdapter(this);

        gridView = (GridView) findViewById(R.id.grid_view);
        gridView.setAdapter(adapter);

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

                        startActivity(new Intent(ClientHomeActivity.this,ProductActivity.class));
                        break;

                    case 3:

                        startActivity(new Intent(ClientHomeActivity.this,BuyPlanActivity.class));
                        break;

                    case 4:

                        startActivity(new Intent(ClientHomeActivity.this,ViewPlanActivity.class));
                        break;

                    case 5:

                        startActivity(new Intent(ClientHomeActivity.this,GenerateInvoiceActivity.class));
                        break;

                    case 6:

                        startActivity(new Intent(ClientHomeActivity.this,ViewPendingPaymentActivity.class));
                        break;
                }
            }
        });
    }
}
