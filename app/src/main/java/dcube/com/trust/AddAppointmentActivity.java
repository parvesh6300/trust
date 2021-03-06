package dcube.com.trust;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog.OnTimeSetListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import WebServicesHandler.CheckNetConnection;
import WebServicesHandler.GlobalConstants;
import WebServicesHandler.WebServices;
import dcube.com.trust.utils.Global;
import okhttp3.OkHttpClient;
import pl.droidsonroids.gif.GifTextView;

public class AddAppointmentActivity extends FragmentActivity implements OnTimeSetListener,OnDateSetListener{

    Spinner service;
    Context context = AddAppointmentActivity.this;

    Global global;

    TextView date;
    TextView time;
    TextView add;

    RelativeLayout datepicker;
    RelativeLayout timepicker;

    EditText ed_name,ed_contact,ed_remarks;
    GifTextView gif_loader;

    String str_client_name,str_client_contact,str_service,str_date,str_time,str_service_id,str_remark;

    WebServices ws;

    ArrayList<String> al_service_name;

    String str_client_id,str_branch;

    String format_time = "",format_date = "";

    CheckNetConnection cn;

    int int_selected_day;
    int int_today;

    ArrayAdapter<String> adapter;
    String str_pre_selected_service;

    String str_time_pick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_appointment);

        global = (Global) getApplicationContext();

        cn = new CheckNetConnection(this);

        gif_loader = (GifTextView) findViewById(R.id.gif_loader);
        service = (Spinner) findViewById(R.id.service);

        datepicker = (RelativeLayout) findViewById(R.id.datepicker);
        timepicker = (RelativeLayout) findViewById(R.id.timepicker);

        date = (TextView) findViewById(R.id.datepick);
        time = (TextView) findViewById(R.id.timepick);
        add = (TextView) findViewById(R.id.add);

        ed_name = (EditText) findViewById(R.id.ed_name);
        ed_contact = (EditText) findViewById(R.id.ed_contact);
        ed_remarks = (EditText) findViewById(R.id.ed_remarks) ;

        str_service = "";

        ed_name.setClickable(false);
        ed_name.setFocusable(false);
        ed_contact.setClickable(false);
        ed_contact.setFocusable(false);

        str_branch = global.getAl_login_list().get(0).get(GlobalConstants.USER_BRANCH);

        ed_name.setText(global.getAl_src_client_details().get(global.getSelected_client()).get(GlobalConstants.SRC_CLIENT_NAME));
        ed_contact.setText(global.getAl_src_client_details().get(global.getSelected_client()).get(GlobalConstants.SRC_CLIENT_CONTACT));

        al_service_name = new ArrayList<>();
     //   al_service_name.clear();


        if (global.isServiceAppointment())
        {
            int pos = global.getServiceAppointmentPos();

            str_pre_selected_service = global.getAl_view_service_details().get(pos).get(GlobalConstants.ORDER_ITEM_NAME);

            al_service_name.add(global.getAl_view_service_details().get(pos).get(GlobalConstants.ORDER_ITEM_NAME));

            Log.e("ServiceName",""+al_service_name);

            adapter = new ArrayAdapter<String>(context, R.layout.spinner_item, al_service_name);
            service.setAdapter(adapter);

            if (!str_pre_selected_service.equals(null) )
            {
                int spinnerPosition = adapter.getPosition(str_pre_selected_service.trim());

                Log.e("Position",""+spinnerPosition);
                service.setSelection(spinnerPosition);
            }

        }
        else
        {
            if (cn.isNetConnected())
            {
                new GetServiceAsyncTask().execute();
            }
            else
            {
                Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
            }


        }



        Calendar now = Calendar.getInstance();
        int_today = now.get(Calendar.DATE);


        service.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {

                str_service = adapterView.getItemAtPosition(pos).toString();

                if (global.isServiceAppointment())
                {
                    str_service_id = global.getAl_view_service_details().get(global.getServiceAppointmentPos()).get(GlobalConstants.ORDER_ITEM_ID);
                }
                else
                {
                    str_service_id = global.getAl_service_details().get(pos).get(GlobalConstants.SERVICE_ID);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//        service.setOnItemClickListener( new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
//
//                str_service = adapterView.getItemAtPosition(pos).toString();
//
//                if (global.isServiceAppointment())
//                {
//                    str_service_id = global.getAl_view_service_details().get(global.getServiceAppointmentPos()).get(GlobalConstants.ORDER_ITEM_ID);
//                }
//                else
//                {
//                    str_service_id = global.getAl_service_details().get(pos).get(GlobalConstants.SERVICE_ID);
//                }
//
//            }
//        });


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

                now.add(Calendar.DATE,0);
                dpd.setMinDate(now);

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

                if (int_today == int_selected_day)
                {
                    tpd.setMinTime(now.get(Calendar.HOUR_OF_DAY),Calendar.MINUTE,Calendar.SECOND);
                }

                tpd.show(getFragmentManager(), "Timepickerdialog"); //Datepickerdialog
            }
        });



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validate())
                {
                    if (cn.isNetConnected())
                    {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                        try {

                            Date date = format.parse(str_date+" "+str_time);
                            Log.e("Date","Format "+date);

                            format_time = format.format(date);
                            Log.e("Time","Format "+format_time);

                            format_date = new SimpleDateFormat("yyyy-MM-dd").format(date);
                            Log.e("Date","Format "+format_date);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                        new AddAppointmentAsyncTask().execute();

                    }
                    else
                    {
                        Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });


        str_client_id = global.getAl_src_client_details().get(global.getSelected_client()).get(GlobalConstants.SRC_CLIENT_ID);



    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {

        String str_format_time="";

        str_time_pick = hourOfDay+":"+minute+":"+second;

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try
        {
            Date date = format.parse("2017-01-30 "+str_time_pick);
            str_format_time = new SimpleDateFormat("hh:mm a").format(date);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        Log.i("Time","Picker "+str_format_time);

        time.setText(str_format_time);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth)
    {
        String d = ""+year+"-"+(monthOfYear+1)+"-"+dayOfMonth;

        int_selected_day = dayOfMonth;

        date.setText(d);
    }


    /**
     *  Validates whether all required fields are filled or not
     * @return boolean
     */


    public boolean validate()
    {
        if (ed_name.getText().toString().matches(""))
        {
            Toast.makeText(AddAppointmentActivity.this, "Enter Name", Toast.LENGTH_SHORT).show();
        }
        else if (ed_contact.getText().toString().matches(""))
        {
            Toast.makeText(AddAppointmentActivity.this, "Enter Contact No.", Toast.LENGTH_SHORT).show();
        }
        else if (str_service.matches(""))
        {
            Toast.makeText(AddAppointmentActivity.this, "Choose Service", Toast.LENGTH_SHORT).show();
        }
        else if (date.getText().toString().matches("Date"))
        {
            Toast.makeText(AddAppointmentActivity.this, "Specify Date", Toast.LENGTH_SHORT).show();
        }
        else if (time.getText().toString().matches("Time"))
        {
            Toast.makeText(AddAppointmentActivity.this, "Specify Time", Toast.LENGTH_SHORT).show();
        }
        else if (ed_remarks.getText().toString().matches(""))
        {
            Toast.makeText(AddAppointmentActivity.this, "Enter Remark", Toast.LENGTH_SHORT).show();
        }
        else
        {
            str_client_name = ed_name.getText().toString();
            str_client_contact = ed_contact.getText().toString();
            str_date = date.getText().toString();
            str_time = str_time_pick ; //time.getText().toString();
            str_remark = ed_remarks.getText().toString();

            return true;
        }

        return false;
    }


    /**
     *  Calls the web service and get Services respective to branch
     */

    public class GetServiceAsyncTask extends AsyncTask<String, String, String> {

        OkHttpClient httpClient = new OkHttpClient();
        String resPonse = "";
        String message = "";

        @Override
        protected void onPreExecute() {

            al_service_name = new ArrayList<>();
            gif_loader.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            try {

                ArrayList<String> al_str_key = new ArrayList<>();
                ArrayList<String> al_str_value = new ArrayList<>();

                al_str_key.add(GlobalConstants.USER_BRANCH_ID);
                al_str_value.add(global.getAl_login_list().get(0).get(GlobalConstants.USER_BRANCH_ID));

                al_str_key.add(GlobalConstants.ACTION);
                al_str_value.add("get_service_list");

                message = ws.GetServiceService(context, al_str_key, al_str_value);


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

                for (int i = 0 ; i < global.getAl_service_details().size() ; i++)
                {
                    al_service_name.add(global.getAl_service_details().get(i).get(GlobalConstants.SERVICE_NAME));
                }

                adapter = new ArrayAdapter<String>(context, R.layout.spinner_item, al_service_name);
                service.setAdapter(adapter);
               // service.setEnabled(true);


            }
            else {
                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

    }


    /**
     *  Add appointment for the user
     */

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

                al_str_key.add(GlobalConstants.USER_BRANCH_ID);
                al_str_value.add(global.getAl_login_list().get(0).get(GlobalConstants.USER_BRANCH_ID));

                al_str_key.add(GlobalConstants.APMT_CLIENT_ID);
                al_str_value.add(str_client_id);

                al_str_key.add(GlobalConstants.APMT_SERVICE_ID);
                al_str_value.add(str_service_id);

                al_str_key.add(GlobalConstants.BRANCH);
                al_str_value.add(str_branch);

                al_str_key.add(GlobalConstants.APMT_TIME);
                al_str_value.add(format_time);

                al_str_key.add(GlobalConstants.APMT_REMARK);
                al_str_value.add(str_remark);

                al_str_key.add(GlobalConstants.APMT_is_FOLLOW_UP);
                al_str_value.add("0");

                al_str_key.add(GlobalConstants.ACTION);
                al_str_value.add("add_appointment");

                for (int i =0 ; i < al_str_value.size() ; i++)
                {
                    Log.i("Key",al_str_key.get(i));
                    Log.i("Value",al_str_value.get(i));
                }

                message = ws.AddAppointmentService(context, al_str_key, al_str_value);

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
            else
            {
                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

    }


    /**
     *  Custom dialog of Added Successfully
     */

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

                    CalendarActivity.h.sendEmptyMessage(0);

                    global.setAppointmentSelected(false);

                    if (global.isServiceAppointment())
                    {
                        global.setServiceAppointment(false);
                        ViewServicesActivity.h.sendEmptyMessage(0);
                    }

                    dismiss();
                    finish();

                }
            });
        }
    }


}

