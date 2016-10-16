package dcube.com.trust;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
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
    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_client);

        context = this;

        searchlist = (ListView) findViewById(R.id.searchlist);
        search = (EditText) findViewById(R.id.search);

        adapter = new MySpinnerAdapter(context, android.R.layout.simple_spinner_item, ITEMS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        listadapter = new CustomAdapter(this);
        searchlist.setAdapter(listadapter);

        branch = (Spinner) findViewById(R.id.spinner);
        branch.setAdapter(adapter);

        searchlist.setVisibility(View.INVISIBLE);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {

                Log.e("TextWatcherTest", "afterTextChanged:\t" +s.toString());
                if(s.length() >1)
                {
                    searchlist.setVisibility(View.VISIBLE);
                }
                else
                {
                    searchlist.setVisibility(View.INVISIBLE);
                }
            }
        });

        searchlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                startActivity(new Intent(SearchClientActivity.this,ClientHomeActivity.class));
            }
        });
    }
}