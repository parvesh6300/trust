package dcube.com.trust;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.weiwangcn.betterspinner.library.BetterSpinner;

import java.util.Calendar;

import dcube.com.trust.utils.FollowupListAdapter;

public class ViewAppointmentActivity extends Activity {

    ListView followUpList;
    FollowupListAdapter adapter;

    BetterSpinner service;

    TextView tv_add_follow_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_appointment);

        tv_add_follow_up = (TextView) findViewById(R.id.tv_add_follow_up);

        followUpList = (ListView) findViewById(R.id.followuplist);
        adapter = new FollowupListAdapter(this);

        followUpList.setAdapter(adapter);


        String[] SERVICES = getResources().getStringArray(R.array.servicelist);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, SERVICES);
        service = (BetterSpinner) findViewById(R.id.service);
        service.setAdapter(adapter);


        tv_add_follow_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CustomDialogClass cdd = new CustomDialogClass(ViewAppointmentActivity.this);
                cdd.show();
            }
        });


    }


    public class CustomDialogClass extends Dialog implements TimePickerDialog.OnTimeSetListener,DatePickerDialog.OnDateSetListener {

        public Activity c;

        public TextView tv_cancel, tv_timepick, tv_datepick;
        public TextView tv_add;
        public RelativeLayout datepicker, timepicker;
        public EditText ed_note;

        public CustomDialogClass(Activity a) {
            super(a);
            // TODO Auto-generated constructor stub
            this.c = a;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);

            setContentView(R.layout.addfollowupdialog);

            datepicker = (RelativeLayout) findViewById(R.id.datepicker);
            timepicker = (RelativeLayout) findViewById(R.id.timepicker);
            ed_note = (EditText) findViewById(R.id.ed_note);
            tv_datepick = (TextView) findViewById(R.id.tv_datepick);
            tv_timepick = (TextView) findViewById(R.id.tv_timepick);

            tv_add = (TextView) findViewById(R.id.tv_add);
            tv_cancel = (TextView) findViewById(R.id.tv_cancel);

            datepicker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Calendar now = Calendar.getInstance();
                    DatePickerDialog dpd = DatePickerDialog.newInstance(
                            CustomDialogClass.this,
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
                            CustomDialogClass.this,
                            now.get(Calendar.HOUR_OF_DAY),
                            now.get(Calendar.MINUTE),
                            false);

                    tpd.setAccentColor(getResources().getColor(R.color.mdtp_accent_color));
                    tpd.show(getFragmentManager(), "Datepickerdialog");
                }
            });


            tv_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    dismiss();
                    finish();

                }
            });

            tv_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    dismiss();
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

            tv_timepick.setText(t);
        }

        @Override
        public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
            String d = ""+dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
            tv_datepick.setText(d);
        }

    }


}
