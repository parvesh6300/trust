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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

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

public class AddPlanAppointmentActivity extends FragmentActivity implements TimePickerDialog.OnTimeSetListener,DatePickerDialog.OnDateSetListener {


 //   BetterSpinner service;
    Context context = AddPlanAppointmentActivity.this;

    Global global;

    TextView date;
    TextView time;
    TextView add;

    RelativeLayout datepicker;
    RelativeLayout timepicker;

    EditText ed_name,ed_contact,ed_plan;
    GifTextView gif_loader;

    String str_client_name,str_client_contact,str_service,str_date,str_time,str_service_id;

    WebServices ws;

    ArrayList<String> al_service_name;

    String str_client_id,str_branch;

    String format_time = "",format_date = "";

    CheckNetConnection cn;

    int int_selected_day;
    int int_today;
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plan_appointment);


        global = (Global) getApplicationContext();

        cn = new CheckNetConnection(this);

        gif_loader = (GifTextView) findViewById(R.id.gif_loader);
//        service = (BetterSpinner) findViewById(R.id.service);

        datepicker = (RelativeLayout) findViewById(R.id.datepicker);
        timepicker = (RelativeLayout) findViewById(R.id.timepicker);

        date = (TextView) findViewById(R.id.datepick);
        time = (TextView) findViewById(R.id.timepick);
        add = (TextView) findViewById(R.id.add);

        ed_name = (EditText) findViewById(R.id.ed_name);
        ed_contact = (EditText) findViewById(R.id.ed_contact);
        ed_plan = (EditText) findViewById(R.id.ed_plan);

        str_service = "";

        ed_name.setClickable(false);
        ed_name.setFocusable(false);
        ed_contact.setClickable(false);
        ed_contact.setFocusable(false);
        ed_plan.setClickable(false);
        ed_plan.setFocusable(false);

        pos = getIntent().getIntExtra("pos",0);

        str_branch = global.getAl_login_list().get(0).get(GlobalConstants.USER_BRANCH);

        ed_name.setText(global.getAl_src_client_details().get(global.getSelected_client()).get(GlobalConstants.SRC_CLIENT_NAME));
        ed_contact.setText(global.getAl_src_client_details().get(global.getSelected_client()).get(GlobalConstants.SRC_CLIENT_CONTACT));
        ed_plan.setText(global.getAl_view_plan_details().get(pos).get(GlobalConstants.ORDER_ITEM_ID));


//        al_service_name = new ArrayList<>();
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, al_service_name);
//        service.setAdapter(adapter);

        Calendar now = Calendar.getInstance();
        int_today = now.get(Calendar.DATE);

//        service.setOnItemClickListener( new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
//
//                str_service = adapterView.getItemAtPosition(pos).toString();
//
//                str_service_id = global.getAl_service_details().get(pos).get(GlobalConstants.SERVICE_ID);
//            }
//        });


        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        AddPlanAppointmentActivity.this,
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
                        AddPlanAppointmentActivity.this,
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        true);

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

//                            format_time = new SimpleDateFormat("HH:mm:ss").format(date);
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


        str_client_id = global.getAl_src_client_details().get(global.getSelected_client()).
                get(GlobalConstants.SRC_CLIENT_ID);



    }


    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {

        String t = "";

        t = hourOfDay+":"+minute+":"+second;

        time.setText(t);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        String d = ""+year+"-"+(monthOfYear+1)+"-"+dayOfMonth;

        int_selected_day = dayOfMonth;

        date.setText(d);
    }

    /**
     * Validates wheter user filled the required fields
     * @return boolean
     */

    public boolean validate()
    {
        if (ed_name.getText().toString().matches(""))
        {
            Toast.makeText(context, "Enter Name", Toast.LENGTH_SHORT).show();
        }
        else if (ed_contact.getText().toString().matches(""))
        {
            Toast.makeText(context, "Enter Contact No.", Toast.LENGTH_SHORT).show();
        }
//        else if (str_service.matches(""))
//        {
//            Toast.makeText(context, "Choose Service", Toast.LENGTH_SHORT).show();
//        }
        else if (date.getText().toString().matches("Date"))
        {
            Toast.makeText(context, "Specify Date", Toast.LENGTH_SHORT).show();
        }
        else if (time.getText().toString().matches("Time"))
        {
            Toast.makeText(context, "Specify Time", Toast.LENGTH_SHORT).show();
        }
        else
        {
            str_client_name = ed_name.getText().toString();
            str_client_contact = ed_contact.getText().toString();
            str_date = date.getText().toString();
            str_time = time.getText().toString();
            return true;
        }

        return false;
    }


    /**
     * Add appointment of the user for availed plan service
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

                al_str_key.add(GlobalConstants.APMT_PLAN_ID);
                al_str_value.add(global.getAl_view_plan_details().get(pos).get(GlobalConstants.ORDER_ITEM_ID));

                al_str_key.add(GlobalConstants.BRANCH);
                al_str_value.add(str_branch);

                al_str_key.add(GlobalConstants.APMT_TIME);
                al_str_value.add(format_time);

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
                CustomDialogClass cdd = new CustomDialogClass(AddPlanAppointmentActivity.this);
                cdd.show();
            }
            else
            {
                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

    }

    /**
     * Custom dialog to show Appointment Added Succesfully
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

//                    CalendarActivity.h.sendEmptyMessage(0);
//                    ClientHomeActivity.h.sendEmptyMessage(0);
                    dismiss();
                    finish();
                }
            });
        }
    }


}
