package dcube.com.trust;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import WebServicesHandler.CheckNetConnection;
import WebServicesHandler.GlobalConstants;
import WebServicesHandler.HideKeyboard;
import WebServicesHandler.WebServices;
import dcube.com.trust.utils.CustomAdapter;
import dcube.com.trust.utils.Global;
import okhttp3.OkHttpClient;
import pl.droidsonroids.gif.GifTextView;

public class SearchClientActivity extends Activity {

   // String[] ITEMS = {"All","Arusha","Dar Es Salaam","Dodoma","Mbeya","Morogoro","Mwanza","Zanzibar"};
    Spinner branch;

    String[] ITEMS = {"All","Dodoma","Mwanza","Arusha","Dar Es Salaam","Morogoro","Mbeya","Zanzibar","Kahama"};

    Context context = SearchClientActivity.this;
    ListView searchlist;

    MySpinnerAdapter adapter;
    CustomAdapter listadapter;
    EditText search;

    TextView tv_no_client;

    String src_keyword="",str_branch = "Select Branch",str_branch_id="";

    GifTextView gif_loader;

    Global global;

    String compareValue;

    WebServices ws;
    int pos;

    RelativeLayout rel_parent_layout;

    ArrayAdapter<String> spinnerArrayAdapter;

    CheckNetConnection cn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_client);

        context = this;

        global = (Global) getApplicationContext();

        cn = new CheckNetConnection(this);

        rel_parent_layout = (RelativeLayout) findViewById(R.id.rel_parent_layout);

        gif_loader = (GifTextView) findViewById(R.id.gif_loader);

        tv_no_client = (TextView) findViewById(R.id.tv_no_client);

        searchlist = (ListView) findViewById(R.id.searchlist);
        search = (EditText) findViewById(R.id.search);
        branch = (Spinner) findViewById(R.id.spinner);


//        adapter = new MySpinnerAdapter(context, android.R.layout.simple_spinner_item, ITEMS);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerArrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, ITEMS);
        branch.setAdapter(spinnerArrayAdapter);

        compareValue = global.getAl_login_list().get(0).get(GlobalConstants.USER_BRANCH_NAME);

        branch.setSelection(0);

//        if (!compareValue.equals(null))
//        {
//            int spinnerPosition = spinnerArrayAdapter.getPosition(compareValue);
//            branch.setSelection(spinnerPosition);
//        }

        branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {

                if (pos == 0)
                {
                    str_branch = "";
                    str_branch_id = "";
                }
                else
                {
                    str_branch = adapterView.getItemAtPosition(pos).toString();
                    str_branch_id = String.valueOf(pos);
                }

                if (cn.isNetConnected())
                {

                    new SearchClientAsyncTask().execute();
//                    if (src_keyword != null && !src_keyword.isEmpty() && !src_keyword.equals("null"))
//                    {
//                        new SearchClientAsyncTask().execute();
//                    }
//                    else
//                    {
//                        src_keyword = "";
//                    }

                }
                else
                {
                    Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
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

                if(s.length() == 0)
                {
                    src_keyword = "";
                    new SearchClientAsyncTask().execute();
                }
                else
                {

                    if (cn.isNetConnected())
                    {
                        src_keyword = s.toString();
                        // new SearchClientAsyncTask().execute();
                        tv_no_client.setVisibility(View.GONE);
                        searchlist.setVisibility(View.VISIBLE);

                        listadapter = new CustomAdapter(SearchClientActivity.this,src_keyword);
                        searchlist.setAdapter(listadapter);
                        listadapter.notifyDataSetChanged();

                    }
                    else
                    {
                        Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }


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


        rel_parent_layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                HideKeyboard.hideSoftKeyboard(SearchClientActivity.this);
                return false;
            }
        });




        if (cn.isNetConnected())
        {
            new SearchClientAsyncTask().execute();
        }
        else
        {
            Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
        }


    }


    /**
     * Hit the service and seacrh clients
     */


    public class SearchClientAsyncTask extends AsyncTask<String, String, String> {

        OkHttpClient httpClient = new OkHttpClient();
        String resPonse = "";
        String message = "";

        @Override
        protected void onPreExecute() {

            searchlist.setEnabled(false);
            gif_loader.setVisibility(View.VISIBLE);
            searchlist.setVisibility(View.GONE);

        }

        @Override
        protected String doInBackground(String... params) {

            try {

                ArrayList<String> al_str_key = new ArrayList<>();
                ArrayList<String> al_str_value = new ArrayList<>();

                al_str_key.add(GlobalConstants.USER_BRANCH_ID);
                al_str_value.add(str_branch_id);  //global.getAl_login_list().get(0).get(GlobalConstants.USER_BRANCH_ID)

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


            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            gif_loader.setVisibility(View.INVISIBLE);
            searchlist.setEnabled(true);

            if (message.equalsIgnoreCase("true"))
            {
                if (global.getAl_src_client_details().size() == 0)
                {
                    listadapter.notifyDataSetChanged();
                    tv_no_client.setVisibility(View.VISIBLE);
                }
                else
                {
                    listadapter = new CustomAdapter(SearchClientActivity.this,"");
                    tv_no_client.setVisibility(View.GONE);
                    searchlist.setVisibility(View.VISIBLE);
                    searchlist.setAdapter(listadapter);
                    listadapter.notifyDataSetChanged();
                }

            }
            else {

                searchlist.setVisibility(View.GONE);
                tv_no_client.setVisibility(View.VISIBLE);
             //   Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();

            }

        }

    }

    @Override
    protected void onResume() {

        if (cn.isNetConnected())
        {
            new SearchClientAsyncTask().execute();
        }
        else
        {
            Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
        }

        super.onResume();

    }
}