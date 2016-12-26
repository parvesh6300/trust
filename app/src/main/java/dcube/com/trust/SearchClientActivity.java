package dcube.com.trust;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import WebServicesHandler.GlobalConstants;
import WebServicesHandler.WebServices;
import dcube.com.trust.utils.CustomAdapter;
import dcube.com.trust.utils.Global;
import okhttp3.OkHttpClient;
import pl.droidsonroids.gif.GifTextView;

public class SearchClientActivity extends Activity {

    String[] ITEMS = {"Select Branch","All","Arusha","Dar Es Salaam","Dodoma","Mbeya","Morogoro","Mwanza","Zanzibar"};
    Spinner branch;

    Context context = SearchClientActivity.this;
    ListView searchlist;

    MySpinnerAdapter adapter;
    CustomAdapter listadapter;
    EditText search;

    String src_keyword,str_branch = "Select Branch";

    GifTextView gif_loader;

    Global global;

    String compareValue;

    WebServices ws;
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_client);

        context = this;

        global = (Global) getApplicationContext();

        gif_loader = (GifTextView) findViewById(R.id.gif_loader);

        searchlist = (ListView) findViewById(R.id.searchlist);
        search = (EditText) findViewById(R.id.search);

        adapter = new MySpinnerAdapter(context, android.R.layout.simple_spinner_item, ITEMS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        branch = (Spinner) findViewById(R.id.spinner);
        branch.setAdapter(adapter);

        compareValue = global.getAl_login_list().get(0).get(GlobalConstants.USER_BRANCH);

        if (!compareValue.equals(null)) {
            int spinnerPosition = adapter.getPosition(compareValue);
            branch.setSelection(spinnerPosition);
        }

        branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {

                if (pos == 1)
                {
                    str_branch = "";
                }
                else
                {
                    str_branch = adapterView.getItemAtPosition(pos).toString().toLowerCase();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


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
                    if (isOnline())
                    {
                        src_keyword = s.toString();
//                    searchlist.setVisibility(View.VISIBLE);
                        new SearchClientAsyncTask().execute();
                    }
                    else {
                        Toast.makeText(SearchClientActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }

                }
                else
                {
                 //   searchlist.setVisibility(View.INVISIBLE);
                }
            }
        });


        searchlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {

                global.setSelected_client(pos);
                startActivity(new Intent(SearchClientActivity.this,ClientHomeActivity.class));

                Log.e("Client","Pos "+pos);

                Log.e("Clientid","id"+ global.getAl_src_client_details().get(global.getSelected_client()).
                        get(GlobalConstants.SRC_CLIENT_ID));

            }
        });


        listadapter = new CustomAdapter(SearchClientActivity.this);
    }


    public class SearchClientAsyncTask extends AsyncTask<String, String, String> {

        OkHttpClient httpClient = new OkHttpClient();
        String resPonse = "";
        String message = "";

        @Override
        protected void onPreExecute() {

            gif_loader.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            try {

                ArrayList<String> al_str_key = new ArrayList<>();
                ArrayList<String> al_str_value = new ArrayList<>();

                al_str_key.add(GlobalConstants.SRC_CLIENT_KEYWORD);
                al_str_value.add(src_keyword);

                al_str_key.add(GlobalConstants.SRC_CLIENT_BRANCH);
                al_str_value.add(str_branch);

                al_str_key.add(GlobalConstants.ACTION);
                al_str_value.add("search_client");

                for (int i = 0 ; i < al_str_key.size(); i++)
                {
                    Log.e("KEy",""+al_str_key.get(i));
                    Log.e("Value",""+al_str_value.get(i));
                }

                message = ws.SearchClientService(context, al_str_key, al_str_value);

                //            resPonse = callApiWithPerameter(GlobalConstants.TRUST_URL, al_str_key, al_str_value);
                //             Log.i("Login", "Login : " + resPonse);

//                return resPonse;

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            gif_loader.setVisibility(View.GONE);

            if (message.equalsIgnoreCase("true"))
            {
                if (global.getAl_src_client_details().size() == 0)
                {
                    listadapter.notifyDataSetChanged();
                }
                else
                {
                    searchlist.setAdapter(listadapter);
                }

            }
            else {

                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();

            }

        }

    }

    protected boolean isOnline() {

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected())
        {
            return true;
        }
        else {
            return false;
        }
    }


}