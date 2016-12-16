package dcube.com.trust;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog.OnTimeSetListener;
import com.weiwangcn.betterspinner.library.BetterSpinner;

import java.util.ArrayList;
import java.util.Calendar;

import WebServicesHandler.GlobalConstants;
import WebServicesHandler.WebServices;
import okhttp3.OkHttpClient;
import pl.droidsonroids.gif.GifTextView;

public class AddAppointmentActivity extends FragmentActivity implements OnTimeSetListener, OnDateSetListener{

    BetterSpinner service;
    Context context = AddAppointmentActivity.this;

    TextView date;
    TextView time;
    TextView add;

    RelativeLayout datepicker;
    RelativeLayout timepicker;

    EditText ed_name,ed_email;
    GifTextView gif_loader;

    String str_client_name,str_client_email,str_service,str_date,str_time;

     WebServices ws;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_appointment);

        gif_loader = (GifTextView) findViewById(R.id.gif_loader);

        datepicker = (RelativeLayout) findViewById(R.id.datepicker);
        timepicker = (RelativeLayout) findViewById(R.id.timepicker);

        date = (TextView) findViewById(R.id.datepick);
        time = (TextView) findViewById(R.id.timepick);
        add = (TextView) findViewById(R.id.add);

        ed_name = (EditText) findViewById(R.id.ed_name);
        ed_email = (EditText) findViewById(R.id.ed_email);

        String[] SERVICES = getResources().getStringArray(R.array.servicelist);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, SERVICES);
        service = (BetterSpinner) findViewById(R.id.service);
        service.setAdapter(adapter);

        str_service = "";

        service.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {

                str_service = adapterView.getItemAtPosition(pos).toString();
            }
        });

        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        AddAppointmentActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setAccentColor(getResources().getColor(R.color.mdtp_accent_color));
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });

        timepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar now = Calendar.getInstance();
                TimePickerDialog tpd = TimePickerDialog.newInstance(
                        AddAppointmentActivity.this,
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        false);

                tpd.setAccentColor(getResources().getColor(R.color.mdtp_accent_color));
                tpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validate())
                {
                    if (isOnline())
                    {
                        new AddAppointmentAsyncTask().execute();
                    }
                    else
                    {
                        Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {

        String t = "";

        if(hourOfDay < 12)
             t = "" + hourOfDay + ":"+minute+" AM";
        else
        if(hourOfDay == 12)
            t = "" + hourOfDay + ":"+minute+" PM";
        else
        if(hourOfDay > 12)
            t = "" + (hourOfDay-12) + ":"+minute+" PM";

        time.setText(t);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String d = ""+dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
        date.setText(d);
    }

    public class CustomDialogClass extends Dialog {

        public Activity c;
        public Button ok;

        public CustomDialogClass(Activity a) {
            super(a);
            // TODO Auto-generated constructor stub
            this.c = a;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.custom_dialog);
            ok = (Button) findViewById(R.id.btn_yes);

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    finish();
                }
            });
        }
    }



    public class AddAppointmentAsyncTask extends AsyncTask<String, String, String> {

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

                al_str_key.add(GlobalConstants.APMT_CLIENT_NAME);
                al_str_value.add(str_client_name);

                al_str_key.add(GlobalConstants.APMT_CLIENT_EMAIL);
                al_str_value.add(str_client_email);

                al_str_key.add(GlobalConstants.APMT_CLIENT_SERVICE);
                al_str_value.add(str_service);

                al_str_key.add(GlobalConstants.APMT_DATE);
                al_str_value.add(str_date);

                al_str_key.add(GlobalConstants.APMT_TIME);
                al_str_value.add(str_time);

                al_str_key.add(GlobalConstants.ACTION);
                al_str_value.add("login");

                for (int i =0 ; i < al_str_value.size() ; i++)
                {
                    Log.i("Key",al_str_key.get(i));
                    Log.i("Value",al_str_value.get(i));
                }

                message = ws.AddAppointmentService(context, al_str_key, al_str_value);

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
                CustomDialogClass cdd = new CustomDialogClass(AddAppointmentActivity.this);
                cdd.show();
            }
            else {

                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

    }


    public boolean validate()
    {
        if (ed_name.getText().toString().matches(""))
        {
            Toast.makeText(AddAppointmentActivity.this, "Enter Name", Toast.LENGTH_SHORT).show();
        }
        else if (ed_email.getText().toString().matches(""))
        {
            Toast.makeText(AddAppointmentActivity.this, "Enter E-mail", Toast.LENGTH_SHORT).show();
        }
        else if (str_service.matches(""))
        {
            Toast.makeText(AddAppointmentActivity.this, "Choose Service", Toast.LENGTH_SHORT).show();
        }
        else if (date.getText().toString().matches(""))
        {
            Toast.makeText(AddAppointmentActivity.this, "Specify Date", Toast.LENGTH_SHORT).show();
        }
        else if (time.getText().toString().matches(""))
        {
            Toast.makeText(AddAppointmentActivity.this, "Specify Time", Toast.LENGTH_SHORT).show();
        }
        else
        {
            str_client_name = ed_name.getText().toString();
            str_client_email = ed_email.getText().toString();
            str_date = date.getText().toString();
            str_time = time.getText().toString();
            return true;
        }

        return false;
    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

}

