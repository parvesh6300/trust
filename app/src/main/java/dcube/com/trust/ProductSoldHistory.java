package dcube.com.trust;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import WebServicesHandler.CheckNetConnection;
import WebServicesHandler.GlobalConstants;
import WebServicesHandler.HideKeyboard;
import WebServicesHandler.WebServices;
import dcube.com.trust.utils.Global;
import dcube.com.trust.utils.SoldProductAdapter;
import okhttp3.OkHttpClient;
import pl.droidsonroids.gif.GifTextView;

public class ProductSoldHistory extends FragmentActivity implements OnDateSetListener,View.OnClickListener {

    ListView lv_sold_products;

    LinearLayout lin_date_from,lin_date_to;

    SoldProductAdapter soldProductAdapter;

    TextView tv_date_from,tv_date_to;

    DatePickerDialog dpd_from,dpd_to;

    EditText search;

    GifTextView gif_loader;
    WebServices ws;
    Context context = ProductSoldHistory.this;

    CheckNetConnection cn;

    String str_branch;
    Global global;

    public boolean isDateSelected;

    RelativeLayout rel_parent_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_sold_history);

        global = (Global) getApplicationContext();

        rel_parent_layout = (RelativeLayout) findViewById(R.id.rel_parent_layout);
        //getSupportActionBar().hide();
        gif_loader = (GifTextView) findViewById(R.id.gif_loader);

        lv_sold_products=(ListView)findViewById(R.id.lv_sold_products);

        lin_date_from=(LinearLayout)findViewById(R.id.lin_date_from);
        lin_date_to=(LinearLayout)findViewById(R.id.lin_date_to);

        search = (EditText) findViewById(R.id.search);

        tv_date_from=(TextView)findViewById(R.id.tv_date_from);
        tv_date_to=(TextView)findViewById(R.id.tv_date_to);
       // tv_total_sale = (TextView) findViewById(R.id.tv_total_sale);

        cn = new CheckNetConnection(this);

        str_branch = global.getAl_login_list().get(0).get(GlobalConstants.USER_BRANCH);


        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }
            @Override
            public void afterTextChanged(Editable s) {

                if(s.length() > 1)
                {
                    soldProductAdapter= new SoldProductAdapter(context,s.toString());
                    lv_sold_products.setAdapter(soldProductAdapter);
                    soldProductAdapter.notifyDataSetChanged();
                }
                else
                {
                    soldProductAdapter= new SoldProductAdapter(context,"");
                    lv_sold_products.setAdapter(soldProductAdapter);
                    soldProductAdapter.notifyDataSetChanged();
                }

            }
        });


        rel_parent_layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                HideKeyboard.hideSoftKeyboard(ProductSoldHistory.this);

                return false;
            }
        });



        if (cn.isNetConnected())
        {
            new ProductSoldAsyncTask().execute();
        }
        else
        {
            Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
        }

        lin_date_from.setOnClickListener(this);
        lin_date_to.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {

        if(view == lin_date_from)
        {

            Calendar now = Calendar.getInstance();
            dpd_from = DatePickerDialog.newInstance(ProductSoldHistory.this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            );
            dpd_from.show(getFragmentManager(), "Datepickerdialog");

            now.add(Calendar.DATE,0);
            dpd_from.setMaxDate(now);

        }

        if (view== lin_date_to)
        {
            Calendar now = Calendar.getInstance();
            dpd_to = DatePickerDialog.newInstance(ProductSoldHistory.this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            );
            dpd_to.show(getFragmentManager(), "Datepickerdialog");

            now.add(Calendar.DATE,0);
            dpd_to.setMaxDate(now);
        }

    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        if (view== dpd_from)
        {
            String d = ""+year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
            tv_date_from.setText(d);
        }


        if (view == dpd_to)
        {
            String d = ""+year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
            tv_date_to.setText(d);

            if (cn.isNetConnected())
            {
                isDateSelected = true;
                new ProductSoldAsyncTask().execute();
            }
            else
            {
                Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
            }


        }

    }


    /**
     * Hit the service and check qty of products sold
     */

    public class ProductSoldAsyncTask extends AsyncTask<String, String, String> {

        OkHttpClient httpClient = new OkHttpClient();
        String resPonse = "";
        String message = "";
        String str_client_id;
        String format_date_from,format_date_to;
        String str_date_from,str_date_to;

        @Override
        protected void onPreExecute() {

            gif_loader.setVisibility(View.VISIBLE);

            if (isDateSelected)
            {
                str_date_from = tv_date_from.getText().toString();
                str_date_to = tv_date_to.getText().toString();

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date;

                try {

                    date = format.parse(str_date_from+" 00:00:00");
                    Log.e("From","Date "+date);

                    format_date_from = format.format(date);
                    Log.e("From","Str "+format_date_from);

                    date = format.parse(str_date_to+" 00:00:00");
                    Log.e("From","Date "+date);

                    format_date_to = format.format(date);
                    Log.e("From","Str "+format_date_to);


                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }


        }

        @Override
        protected String doInBackground(String... params) {

            try {

                ArrayList<String> al_str_key = new ArrayList<>();
                ArrayList<String> al_str_value = new ArrayList<>();

                al_str_key.add(GlobalConstants.USER_BRANCH_ID);
                al_str_value.add(global.getAl_login_list().get(0).get(GlobalConstants.USER_BRANCH_ID));

                if (isDateSelected)
                {
                    al_str_key.add(GlobalConstants.SOLD_START_DATE);
                    al_str_value.add(format_date_from);

                    al_str_key.add(GlobalConstants.SOLD_END_DATE);
                    al_str_value.add(format_date_to);
                }

                al_str_key.add(GlobalConstants.BRANCH);
                al_str_value.add(str_branch);

                al_str_key.add(GlobalConstants.ACTION);
                al_str_value.add("products_soldout");

                Log.i("Key",""+al_str_key);
                Log.i("Value",""+al_str_value);

                message = ws.ProductSoldService(context, al_str_key, al_str_value);


            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            gif_loader.setVisibility(View.INVISIBLE);

            isDateSelected = false;

            if (message.equalsIgnoreCase("true"))
            {
                soldProductAdapter= new SoldProductAdapter(context,"");
                lv_sold_products.setAdapter(soldProductAdapter);
                lv_sold_products.setVisibility(View.VISIBLE);
                soldProductAdapter.notifyDataSetChanged();

            }
            else
            {
                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();

                lv_sold_products.setVisibility(View.INVISIBLE);

            }

        }

    }

}
