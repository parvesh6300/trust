package dcube.com.trust;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import dcube.com.trust.utils.NurseAdapter;

public class NurseHomeActivity extends Activity {

    GridView gridView;
    NurseAdapter adapter;
    ImageView iv_cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse_home);

        adapter = new NurseAdapter(this);

        iv_cart = (ImageView) findViewById(R.id.iv_cart);

        gridView = (GridView) findViewById(R.id.grid_view);
        gridView.setAdapter(adapter);


        iv_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NurseHomeActivity.this,CartActivity.class));
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
