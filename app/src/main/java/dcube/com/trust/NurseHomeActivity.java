package dcube.com.trust;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import dcube.com.trust.utils.NurseAdapter;

public class NurseHomeActivity extends Activity {

    GridView gridView;
    NurseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse_home);

        adapter = new NurseAdapter(this);

        gridView = (GridView) findViewById(R.id.grid_view);
        gridView.setAdapter(adapter);

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

                }
            }
        });
    }
}
