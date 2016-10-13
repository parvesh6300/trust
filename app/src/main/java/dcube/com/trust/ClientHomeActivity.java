package dcube.com.trust;

import android.app.Activity;
import android.os.Bundle;
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
    }
}
