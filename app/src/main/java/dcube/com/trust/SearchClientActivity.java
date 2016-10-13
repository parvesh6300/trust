package dcube.com.trust;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Spinner;

import dcube.com.trust.utils.CustomAdapter;

public class SearchClientActivity extends Activity {

    String[] ITEMS = {"Select Branch", "Arusha", "Dodoma", "Mwanza", "Dar Es Salaam", "Morogoro", "Mbeya", "Zanzibar"};
    Spinner branch;

    Context context;
    ListView searchlist;
    MySpinnerAdapter adapter;
    CustomAdapter listadapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_client);

        context = this;

        searchlist = (ListView) findViewById(R.id.searchlist);

        adapter = new MySpinnerAdapter(context, android.R.layout.simple_spinner_item, ITEMS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        listadapter = new CustomAdapter(this);
        searchlist.setAdapter(listadapter);

        branch = (Spinner) findViewById(R.id.spinner);
        branch.setAdapter(adapter);


    }
}