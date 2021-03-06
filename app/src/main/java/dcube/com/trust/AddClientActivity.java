package dcube.com.trust;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import WebServicesHandler.CheckNetConnection;
import WebServicesHandler.GlobalConstants;
import WebServicesHandler.HideKeyboard;
import WebServicesHandler.WebServices;
import dcube.com.trust.utils.Global;
import okhttp3.OkHttpClient;

public class AddClientActivity extends Activity implements View.OnClickListener {

    Context context;

    String[] spinnerArray = {"10-19","20-29","30-39","40+"};

    EditText ed_areacode,emer_areacode;

    Spinner age_group;
    Spinner area;

    TextView addclient;
   // GifTextView gif_loader;

    String str_age,str_area,str_name,str_contact,str_emer_contact,str_med_his,str_emer_name,str_emer_rel;
    String str_his_contra,str_contra_type,str_sex,str_child,str_hiv_test,str_about_clinic;

    EditText ed_name,ed_contact,ed_emer_contact,ed_med_history;

    RadioGroup radio_group_contra,radio_group_sex,radio_group_child,radio_group_hiv_test,radio_group_about_clinic;
    RadioButton radio_contra_yes,radio_contra_no,radio_contra_one_time;
    RadioButton radio_male,radio_female;
    RadioButton radio_child_one_two,radio_child_no,radio_child_three;
    RadioButton radio_test_yes_months,radio_test_last_2_yr,radio_test_more_2_yr,radio_test_no;
    RadioButton radio_social,radio_radio,radio_family,radio_flier,radio_event,radio_tv,radio_signage;

    //  Updated add client UI's

    EditText ed_emer_name,ed_emer_rel;

    CheckBox cb_contra_type_condom,cb_contra_type_fem_condom,cb_contra_type_oral,cb_contra_type_emer_pill;
    CheckBox cb_contra_type_iucd,cb_contra_type_implants,cb_contra_type_calendar,cb_contra_type_other,cb_contra_type_injectables;

    LinearLayout lin_type_of_contra;

    // *********************************  Updations ********************************


    ArrayList<String> al_type_of_contra;

    RelativeLayout rel_parent_layout;

    LinearLayout lin_detail;

    WebServices ws;

    String[] ITEMS = {"Select Branch","Arusha","Dar Es Salaam","Dodoma","Mbeya","Morogoro","Mwanza","Zanzibar"};

    CheckNetConnection cn;

    Global global;

    String compareValue;

   // ProgressDialog dialog;

    Dialog progress_dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_client);

        context = this;

        getViewById();

        al_type_of_contra = new ArrayList<>();

        cn = new CheckNetConnection(this);

        global = (Global) getApplicationContext();

        str_area = "Select Branch";

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, spinnerArray);
        age_group.setAdapter(spinnerArrayAdapter);

        age_group.setSelection(1);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, ITEMS);  //simple_dropdown_item_1line
        area.setAdapter(adapter);

        ed_areacode.setFocusable(false);
        ed_areacode.setKeyListener(null);
        emer_areacode.setKeyListener(null);


        age_group.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int pos, long l) {

               if (pos == 0)
               {
                   str_age = "10-19";
               }
               else
               {
                   str_age = parent.getItemAtPosition(pos).toString();

               }

           }

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {

           }
       });


//        area.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int pos, long l) {
//
//                str_area = parent.getItemAtPosition(pos).toString();
//                Log.e("Area",parent.getItemAtPosition(pos)+"");
//
//            }
//        });


        area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {

                str_area = adapterView.getItemAtPosition(pos).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        addclient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validate())
                {
                    if (cn.isNetConnected())
                    {
                        Log.e("Add Client","Clicked");

                        new AddClientAsyncTask().execute();
                    }
                    else {
                        Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });



        radio_group_contra.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radio_contra_yes.isChecked())
                {
                    str_his_contra = "yes";
                    lin_type_of_contra.setVisibility(View.VISIBLE);
                }
               else if (radio_contra_no.isChecked())
                {
                    str_his_contra = "no";
                    lin_type_of_contra.setVisibility(View.GONE);
                    al_type_of_contra.clear();
                    resetCheckList();
                }
               else if (radio_contra_one_time.isChecked())
                {
                    str_his_contra = "At one time, not now";
                    lin_type_of_contra.setVisibility(View.VISIBLE);
                }

            }
        });

        radio_group_sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radio_male.isChecked())
                {
                    str_sex = "male";
                }
               else if (radio_female.isChecked())
                {
                    str_sex = "female";
                }
            }
        });


        radio_group_child.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radio_child_one_two.isChecked())
                {
                    str_child = "1-2";
                }
                else if (radio_child_three.isChecked())
                {
                    str_child = "3+";
                }
               else if (radio_child_no.isChecked())
                {
                    str_child = "no";
                }

            }
        });

        radio_group_hiv_test.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radio_test_yes_months.isChecked())
                {
                    str_hiv_test = "Yes, Within 6 months";
                }
                else if (radio_test_last_2_yr.isChecked())
                {
                    str_hiv_test = "Yes, Within last 2 years";
                }
               else if (radio_test_more_2_yr.isChecked())
                {
                    str_hiv_test = "Yes, More than 2 years";
                }
               else if (radio_test_no.isChecked())
                {
                    str_hiv_test = "no";
                }

            }
        });


        radio_group_about_clinic.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radio_social.isChecked())
                {
                    str_about_clinic = "Social Media";
                }
                else if (radio_radio.isChecked())
                {
                    str_about_clinic = "Radio";
                }
                else if (radio_family.isChecked())
                {
                    str_about_clinic = "Friends / Family ";
                }
                else if (radio_flier.isChecked())
                {
                    str_about_clinic = "Flier";
                }
                else if (radio_event.isChecked())
                {
                    str_about_clinic = "At an Event";
                }
                else if (radio_tv.isChecked())
                {
                    str_about_clinic = "TV";
                }
                else if (radio_signage.isChecked())
                {
                    str_about_clinic = "Signage";
                }

            }
        });



        compareValue = global.getAl_login_list().get(0).get(GlobalConstants.USER_BRANCH_NAME);
        Log.e("Branch",""+compareValue);

        if (!compareValue.equals(null))
        {
            int spinnerPosition = adapter.getPosition(compareValue.trim());

            Log.e("Position",""+spinnerPosition);
            area.setSelection(spinnerPosition);
        }



        rel_parent_layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                HideKeyboard.hideSoftKeyboard(AddClientActivity.this);
                return false;
            }
        });

        lin_detail.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                HideKeyboard.hideSoftKeyboard(AddClientActivity.this);
                return false;
            }
        });


        cb_contra_type_condom.setOnClickListener(this);
        cb_contra_type_fem_condom.setOnClickListener(this);
        cb_contra_type_oral.setOnClickListener(this);
        cb_contra_type_emer_pill.setOnClickListener(this);
        cb_contra_type_iucd.setOnClickListener(this);
        cb_contra_type_implants.setOnClickListener(this);
        cb_contra_type_calendar.setOnClickListener(this);
        cb_contra_type_other.setOnClickListener(this);
        cb_contra_type_injectables.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        if (view == cb_contra_type_condom)
        {
            String type = "Condoms";

            if (cb_contra_type_condom.isChecked())
            {
                al_type_of_contra.add(type);
            }
            else
            {
                al_type_of_contra.remove(type);
            }
        }

        if (view == cb_contra_type_fem_condom)
        {
            String type = "Female condoms";

            if (cb_contra_type_fem_condom.isChecked())
            {
                al_type_of_contra.add(type);
            }
            else
            {
                al_type_of_contra.remove(type);
            }
        }

        if (view == cb_contra_type_oral)
        {
            String type = "Oral Contraceptive pill";

            if (cb_contra_type_oral.isChecked())
            {
                al_type_of_contra.add(type);
            }
            else
            {
                al_type_of_contra.remove(type);
            }
        }


        if (view == cb_contra_type_emer_pill)
        {
            String type = "Emergency Contraceptive pill ";

            if (cb_contra_type_emer_pill.isChecked())
            {
                al_type_of_contra.add(type);
            }
            else
            {
                al_type_of_contra.remove(type);
            }
        }


        if (view == cb_contra_type_iucd)
        {
            String type = "IUCD";

            if (cb_contra_type_iucd.isChecked())
            {
                al_type_of_contra.add(type);
            }
            else
            {
                al_type_of_contra.remove(type);
            }
        }


        if (view == cb_contra_type_calendar)
        {
            String type = "Calendar method";

            if (cb_contra_type_calendar.isChecked())
            {
                al_type_of_contra.add(type);
            }
            else
            {
                al_type_of_contra.remove(type);
            }
        }

        if (view == cb_contra_type_implants)
        {
            String type = "Implants";

            if (cb_contra_type_implants.isChecked())
            {
                al_type_of_contra.add(type);
            }
            else
            {
                al_type_of_contra.remove(type);
            }
        }

        if (view == cb_contra_type_injectables)
        {
            String type = "Injectables";

            if (cb_contra_type_injectables.isChecked())
            {
                al_type_of_contra.add(type);
            }
            else
            {
                al_type_of_contra.remove(type);
            }
        }

        if (view == cb_contra_type_other)
        {
            String type = "Other";

            if (cb_contra_type_other.isChecked())
            {
                al_type_of_contra.add(type);
            }
            else
            {
                al_type_of_contra.remove(type);
            }
        }


    }

    /**
     * Custom dialog to show Added Successfully
     */

    public class CustomDialogClass extends Dialog{

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

                    dismiss();

                    global.setCLientAdded(true);

                    startActivity(new Intent(AddClientActivity.this,NurseHomeActivity.class));  //ClientHomeActivity
                    finish();
                }
            });
        }
    }


    /**
     * Check whether all the required fields are filled or not
     * @return boolean
     */

    public boolean validate()
    {
        if (ed_name.getText().toString().matches(""))
        {
            Toast.makeText(AddClientActivity.this, "Enter Name", Toast.LENGTH_SHORT).show();
        }
        else if (ed_contact.getText().toString().matches(""))
        {
            Toast.makeText(AddClientActivity.this, "Enter Contact No.", Toast.LENGTH_SHORT).show();
        }
        else if (ed_contact.getText().toString().length() != 9)
        {
            Toast.makeText(AddClientActivity.this, "Contact No. is not of 9 digits", Toast.LENGTH_SHORT).show();
        }

        else if (ed_emer_name.getText().toString().matches(""))
        {
            Toast.makeText(AddClientActivity.this, "Enter Name of Emergency Contact ", Toast.LENGTH_SHORT).show();
        }

        else if (ed_emer_contact.getText().toString().matches(""))
        {
            Toast.makeText(AddClientActivity.this, "Enter Emergency Contact No.", Toast.LENGTH_SHORT).show();
        }
        else if (ed_emer_contact.getText().toString().length() != 9)
        {
            Toast.makeText(AddClientActivity.this, "Emer. Contact No. is not of 9 digits", Toast.LENGTH_SHORT).show();
        }

        else if (ed_emer_rel.getText().toString().matches(""))
        {
            Toast.makeText(AddClientActivity.this, "Enter Your Relation with the Person", Toast.LENGTH_SHORT).show();
        }

        else if (str_area.equalsIgnoreCase("Select Branch"))
        {
            Toast.makeText(AddClientActivity.this, "Select Area", Toast.LENGTH_SHORT).show();
        }

        else
        {
            str_name = ed_name.getText().toString().toLowerCase();
            str_contact = ed_contact.getText().toString().toLowerCase();
            str_emer_contact = ed_emer_contact.getText().toString().toLowerCase();
            str_med_his = ed_med_history.getText().toString().toLowerCase();
            str_emer_name = ed_emer_name.getText().toString().toLowerCase();
            str_emer_rel = ed_emer_rel.getText().toString().toLowerCase();

            return true;
        }

        return false;
    }


    /**
     * Fetch all the views id from the xml
     */


    public void getViewById()
    {
      //  gif_loader = (GifTextView) findViewById(R.id.gif_loader);

        age_group = (Spinner) findViewById(R.id.age_group);
        area = (Spinner) findViewById(R.id.area);

        addclient = (TextView) findViewById(R.id.addclient);

        ed_areacode = (EditText) findViewById(R.id.areacode);
        emer_areacode = (EditText) findViewById(R.id.emer_areacode);

        ed_name = (EditText) findViewById(R.id.ed_name);
        ed_contact = (EditText) findViewById(R.id.ed_contact);
        ed_emer_contact = (EditText) findViewById(R.id.ed_emer_contact);
        ed_med_history = (EditText) findViewById(R.id.ed_med_history);

        radio_group_contra = (RadioGroup) findViewById(R.id.radio_group_contra);
        radio_group_sex = (RadioGroup) findViewById(R.id.radio_group_sex);
        radio_group_child = (RadioGroup) findViewById(R.id.radio_group_child);
        radio_group_hiv_test = (RadioGroup) findViewById(R.id.radio_group_hiv_test);
        radio_group_about_clinic = (RadioGroup) findViewById(R.id.radio_group_about_clinic);

        radio_contra_yes = (RadioButton) findViewById(R.id.radio_contra_yes);
        radio_contra_no = (RadioButton) findViewById(R.id.radio_contra_no);
        radio_contra_one_time = (RadioButton) findViewById(R.id.radio_contra_one_time);

        radio_male = (RadioButton) findViewById(R.id.radio_male);
        radio_female = (RadioButton) findViewById(R.id.radio_female);

        radio_child_one_two = (RadioButton) findViewById(R.id.radio_child_one_two);
        radio_child_no = (RadioButton) findViewById(R.id.radio_child_no);
        radio_child_three = (RadioButton) findViewById(R.id.radio_child_three);

        radio_test_yes_months = (RadioButton) findViewById(R.id.radio_test_yes_months);
        radio_test_last_2_yr = (RadioButton) findViewById(R.id.radio_test_last_2_yr);
        radio_test_more_2_yr = (RadioButton) findViewById(R.id.radio_test_more_2_yr);
        radio_test_no = (RadioButton) findViewById(R.id.radio_test_no);

        radio_social = (RadioButton) findViewById(R.id.radio_social);
        radio_radio = (RadioButton) findViewById(R.id.radio_radio);
        radio_family = (RadioButton) findViewById(R.id.radio_family);
        radio_flier = (RadioButton) findViewById(R.id.radio_flier);
        radio_event = (RadioButton) findViewById(R.id.radio_event);
        radio_tv = (RadioButton) findViewById(R.id.radio_tv);
        radio_signage = (RadioButton) findViewById(R.id.radio_signage);

        // Updated UI views

        ed_emer_name = (EditText) findViewById(R.id.ed_emer_name);
        ed_emer_rel = (EditText) findViewById(R.id.ed_emer_rel);

        cb_contra_type_condom = (CheckBox) findViewById(R.id.cb_contra_type_condom);
        cb_contra_type_fem_condom = (CheckBox) findViewById(R.id.cb_contra_type_fem_condom);
        cb_contra_type_oral = (CheckBox) findViewById(R.id.cb_contra_type_oral);
        cb_contra_type_emer_pill = (CheckBox) findViewById(R.id.cb_contra_type_emer_pill);
        cb_contra_type_iucd = (CheckBox) findViewById(R.id.cb_contra_type_iucd);
        cb_contra_type_implants = (CheckBox) findViewById(R.id.cb_contra_type_implants);
        cb_contra_type_calendar = (CheckBox) findViewById(R.id.cb_contra_type_calendar);
        cb_contra_type_other = (CheckBox) findViewById(R.id.cb_contra_type_other);
        cb_contra_type_injectables = (CheckBox) findViewById(R.id.cb_contra_type_injectables);

        lin_type_of_contra = (LinearLayout) findViewById(R.id.lin_type_of_contra);
        lin_detail = (LinearLayout) findViewById(R.id.lin_detail);
        rel_parent_layout = (RelativeLayout) findViewById(R.id.rel_parent_layout);

        setDefaultValues();
    }


    /**
     * Set default value of Radio buttons
     */

    public void setDefaultValues()
    {
        radio_contra_yes.setChecked(true);
        radio_male.setChecked(true);
        radio_child_no.setChecked(true);
        radio_test_yes_months.setChecked(true);
        radio_social.setChecked(true);

        str_his_contra = "yes";
        str_sex = "male";
        str_child = "no";
        str_hiv_test = "Yes, Within 6 months";
        str_about_clinic = "Social Media";
    }


    /**
     * Reset all values of the Check boxes
     */

    public void resetCheckList()
    {
        cb_contra_type_condom.setChecked(false);
        cb_contra_type_fem_condom.setChecked(false);
        cb_contra_type_oral.setChecked(false);
        cb_contra_type_emer_pill.setChecked(false);
        cb_contra_type_iucd.setChecked(false);
        cb_contra_type_implants.setChecked(false);
        cb_contra_type_calendar.setChecked(false);
        cb_contra_type_other.setChecked(false);
        cb_contra_type_injectables.setChecked(false);


    }


    /**
     * Add Client details to server
     */


    public class AddClientAsyncTask extends AsyncTask<String, String, String> {

        OkHttpClient httpClient = new OkHttpClient();
        String resPonse = "";
        String message = "";

        @Override
        protected void onPreExecute() {

           // gif_loader.setVisibility(View.VISIBLE);

         //   progress_dialog = new ProgressDialog(AddClientActivity.this);
            showProgressDialog();

            addclient.setClickable(false);

            str_name = str_name.toLowerCase();
            str_age = str_age.toLowerCase();
            str_contact = str_contact.toLowerCase();
            str_emer_contact = str_emer_contact.toLowerCase();
            str_med_his = str_med_his.toLowerCase();
            str_his_contra = str_his_contra.toLowerCase();
            str_sex = str_sex.toLowerCase();
            str_child = str_child.toLowerCase();
            str_hiv_test = str_hiv_test.toLowerCase();
            str_about_clinic = str_about_clinic.toLowerCase();

            Log.i("Contra","Type "+al_type_of_contra.size());

            if (al_type_of_contra.size() > 0)
            {
                str_contra_type = TextUtils.join(",", al_type_of_contra);
            }
            else
            {
                str_contra_type = "";
            }

        }

        @Override
        protected String doInBackground(String... params) {
            try {

                ArrayList<String> al_str_key = new ArrayList<>();
                ArrayList<String> al_str_value = new ArrayList<>();

                al_str_key.add(GlobalConstants.USER_BRANCH_ID);
                al_str_value.add(global.getAl_login_list().get(0).get(GlobalConstants.USER_BRANCH_ID));

                al_str_key.add(GlobalConstants.CLIENT_NAME);
                al_str_value.add(str_name);

                al_str_key.add(GlobalConstants.CLIENT_AGE);
                al_str_value.add(str_age);

                al_str_key.add(GlobalConstants.CLIENT_CONTACT);
                al_str_value.add(str_contact);

                al_str_key.add(GlobalConstants.CLIENT_EMER_CONTACT);
                al_str_value.add(str_emer_contact);

                al_str_key.add(GlobalConstants.CLIENT_EMER_NAME);
                al_str_value.add(str_emer_name);

                al_str_key.add(GlobalConstants.CLIENT_EMER_RELATION);
                al_str_value.add(str_emer_rel);

                al_str_key.add(GlobalConstants.CLIENT_AREA);
                al_str_value.add(str_area);

                al_str_key.add(GlobalConstants.CLIENT_MED_HISTORY);
                al_str_value.add(str_med_his);

                al_str_key.add(GlobalConstants.CLIENT_CONTRA_HISTORY);
                al_str_value.add(str_his_contra);

                al_str_key.add(GlobalConstants.CLIENT_CONTRA_TYPE);
                al_str_value.add(str_contra_type);

                al_str_key.add(GlobalConstants.CLIENT_SEX);
                al_str_value.add(str_sex);

                al_str_key.add(GlobalConstants.CLIENT_CHILD);
                al_str_value.add(str_child);

                al_str_key.add(GlobalConstants.CLIENT_HIV_TEST);
                al_str_value.add(str_hiv_test);

                al_str_key.add(GlobalConstants.CLIENT_REACH_WAY);
                al_str_value.add(str_about_clinic);

                al_str_key.add(GlobalConstants.ACTION);
                al_str_value.add("add_new_client");

                for (int i =0 ; i < al_str_value.size() ; i++)
                {
                    Log.i("Key",al_str_key.get(i));
                    Log.i("Value",al_str_value.get(i));
                }

               message = ws.AddClientService(context, al_str_key, al_str_value);


            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {

         //   gif_loader.setVisibility(View.INVISIBLE);

            addclient.setClickable(true);

            progress_dialog.dismiss();

            if (message.equalsIgnoreCase("true"))
            {
                CustomDialogClass cdd=new CustomDialogClass(AddClientActivity.this);
                cdd.show();

            }
            else
            {
                Toast.makeText(AddClientActivity.this, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

    }



//    public void createProgressDialog() {
//
//
//        try {
//            dialog.show();
//        } catch (WindowManager.BadTokenException e) {
//
//        }
//        dialog.setCancelable(false);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        dialog.setContentView(R.layout.progress_dialog_add_contact);
//        // dialog.setMessage(Message);
//
//    }


    public void showProgressDialog() {

        progress_dialog = new Dialog(context);

        progress_dialog.setContentView(R.layout.progress_dialog);

        progress_dialog.setCancelable(false);

        //doneDialog.create();
        progress_dialog.show();

        Button btn_cancel = (Button) progress_dialog.findViewById(R.id.btn_cancel);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progress_dialog.dismiss();

                addclient.setClickable(true);

            }
        });
    }

}



