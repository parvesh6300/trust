package dcube.com.trust;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;

import dcube.com.trust.utils.ClientHistoryAdapter;

public class ClientHistoryActivity extends Activity {

    ListView list_history;

    ClientHistoryAdapter adapter;

    Context ctx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_history);

        list_history = (ListView) findViewById(R.id.list_history);
        adapter = new ClientHistoryAdapter(ctx);





    }



}
