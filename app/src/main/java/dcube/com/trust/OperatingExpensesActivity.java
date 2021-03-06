package dcube.com.trust;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import WebServicesHandler.CheckNetConnection;
import WebServicesHandler.GlobalConstants;
import WebServicesHandler.HideKeyboard;
import WebServicesHandler.WebServices;
import dcube.com.trust.utils.FormatString;
import dcube.com.trust.utils.Global;
import okhttp3.OkHttpClient;
import pl.droidsonroids.gif.GifTextView;

public class OperatingExpensesActivity extends FragmentActivity implements DatePickerDialog.OnDateSetListener
//TimePickerDialog.OnTimeSetListener,
{

    RadioGroup radio_group;

    RadioButton radio_other,radio_running_exp,radio_dkt_commodity,radio_non_dkt,radio_marketing;
    RadioButton radio_commodity,radio_salary,radio_rent,radio_consultancy,radio_equipment;
    RadioButton radio_cleaning,radio_internet,radio_security;

    EditText ed_other,ed_wd_amount;

    ImageView image_view;

    TextView tv_withdraw,tv_total_amount;

    GifTextView gif_loader;

    Context context;

    WebServices ws;
    Global global;
    String str_user_id;
    String str_exp_rsn="",str_wd_amount="",str_branch="",str_remarks="";

    CustomDialogClass cdd;

    CheckNetConnection cn;

    RelativeLayout rel_parent_layout;

    public boolean is_pic_selected;
    public static final String UPLOAD_KEY = "image";

    Bitmap photo;
    public static HashMap<String,String> data;

    ImageView iv_receipt;

    private final int CAMERA_REQUEST = 1888;

    TextView tv_receipt;

    RelativeLayout datepicker;
 //   RelativeLayout timepicker;

    int int_selected_day;
    int int_today;

    TextView date;
//    TextView time;

    String str_time_pick,str_date,str_time;

    String format_time = "",format_date = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_operating_exp);

        context = this;

        global = (Global) getApplicationContext();

        rel_parent_layout = (RelativeLayout) findViewById(R.id.rel_parent_layout);

        cn = new CheckNetConnection(this);

        datepicker = (RelativeLayout) findViewById(R.id.datepicker);

      //  timepicker = (RelativeLayout) findViewById(R.id.timepicker);

        date = (TextView) findViewById(R.id.datepick);

     //   time = (TextView) findViewById(R.id.timepick);

        tv_receipt = (TextView) findViewById(R.id.tv_receipt);

        iv_receipt = (ImageView)findViewById(R.id.iv_receipt);

        gif_loader = (GifTextView) findViewById(R.id.gif_loader);

      //  image_view = (ImageView) findViewById(R.id.image_view);

        radio_group=(RadioGroup)findViewById(R.id.radio_group);

        radio_other=(RadioButton)findViewById(R.id.radio_other);
     //   radio_petty_cash=(RadioButton)findViewById(R.id.radio_petty_cash);
        radio_running_exp=(RadioButton)findViewById(R.id.radio_running_exp);
        radio_commodity=(RadioButton)findViewById(R.id.radio_commodity);
        radio_salary=(RadioButton)findViewById(R.id.radio_salary);
        radio_rent=(RadioButton)findViewById(R.id.radio_rent);
        radio_consultancy=(RadioButton)findViewById(R.id.radio_consultancy);
        radio_equipment=(RadioButton)findViewById(R.id.radio_equipment);

        radio_cleaning=(RadioButton)findViewById(R.id.radio_cleaning);
        radio_internet=(RadioButton)findViewById(R.id.radio_internet);
        radio_security=(RadioButton)findViewById(R.id.radio_security);

        radio_dkt_commodity = (RadioButton) findViewById(R.id.radio_dkt_commodity);
        radio_non_dkt = (RadioButton) findViewById(R.id.radio_non_dkt);
        radio_marketing = (RadioButton) findViewById(R.id.radio_marketing);

        ed_other=      (EditText)findViewById(R.id.ed_other);
        ed_wd_amount = (EditText) findViewById(R.id.ed_wd_amount);
      //  ed_petty_rsn = (EditText) findViewById(R.id.ed_petty_rsn);

        tv_withdraw=(TextView)findViewById(R.id.tv_withdraw);
        tv_total_amount = (TextView) findViewById(R.id.tv_total_amount);

        str_branch = global.getAl_login_list().get(0).get(GlobalConstants.USER_BRANCH);


        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {


                if (radio_other.isChecked())
                {
                    ed_other.setVisibility(View.VISIBLE);
                }
                else
                {
                    ed_other.setVisibility(View.INVISIBLE);
                }


                if (radio_running_exp.isChecked())
                {
                    str_exp_rsn = "Running Expenses";
                    str_remarks = "NA";
                }
                else if (radio_commodity.isChecked())
                {
                    str_exp_rsn = "Commodities";
                    str_remarks = "NA";
                }
                else if (radio_dkt_commodity.isChecked())
                {
                    str_exp_rsn = "DKT commodities";
                    str_remarks = "NA";
                }
                else if (radio_non_dkt.isChecked())
                {
                    str_exp_rsn = "Non DKT commodities";
                    str_remarks = "NA";
                }
                else if (radio_marketing.isChecked())
                {
                    str_exp_rsn = "Marketing";
                    str_remarks = "NA";
                }
                else if (radio_salary.isChecked())
                {
                    str_exp_rsn = "Salaries";
                    str_remarks = "NA";
                }
                else if (radio_rent.isChecked())
                {
                    str_exp_rsn = "Rent";
                    str_remarks = "NA";
                }
                else if (radio_consultancy.isChecked())
                {
                    str_exp_rsn = "Consultancies";
                    str_remarks = "NA";
                }
                else if (radio_equipment.isChecked())
                {
                    str_exp_rsn = "Equipment";
                    str_remarks = "NA";
                }
                else if (radio_internet.isChecked())
                {
                    str_exp_rsn = "Internet";
                    str_remarks = "NA";
                }
                else if (radio_security.isChecked())
                {
                    str_exp_rsn = "Security";
                    str_remarks = "NA";
                }
                else if (radio_cleaning.isChecked())
                {
                    str_exp_rsn = "Cleaning";
                    str_remarks = "NA";
                }
                else if (radio_other.isChecked())
                {
                    str_exp_rsn = "other";
                    str_remarks = ed_other.getText().toString();
                }

            }
        });



        tv_withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ed_wd_amount.getText().toString().matches(""))
                {
                    Toast.makeText(OperatingExpensesActivity.this, "Enter Amount", Toast.LENGTH_SHORT).show();
                }
                else if (ed_wd_amount.getText().toString().equalsIgnoreCase("0"))
                {
                    Toast.makeText(OperatingExpensesActivity.this, "Amount should be greater than 0", Toast.LENGTH_SHORT).show();
                }
                else if (str_exp_rsn.matches(""))
                {
                    Toast.makeText(OperatingExpensesActivity.this, "Specify Reason", Toast.LENGTH_SHORT).show();
                }
                else if (date.getText().toString().matches("Date"))
                {
                    Toast.makeText(OperatingExpensesActivity.this, "Specify Date", Toast.LENGTH_SHORT).show();
                }
//                else if (time.getText().toString().matches("Time"))
//                {
//                    Toast.makeText(OperatingExpensesActivity.this, "Specify Time", Toast.LENGTH_SHORT).show();
//                }
                else
                {

                    if (radio_other.isChecked() )
                    {

                            if (ed_other.getText().toString().matches(""))
                            {
                                Toast.makeText(OperatingExpensesActivity.this, "Specify Reason", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                str_remarks = ed_other.getText().toString();

                                validateAmount();

                               // new GetBranchBalanceAsyncTask().execute();
                            }

                    }
                    else
                    {

                        validateAmount();
                    }


                }

            }
        });

        tv_receipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });



        rel_parent_layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                HideKeyboard.hideSoftKeyboard(OperatingExpensesActivity.this);
                return false;
            }
        });


        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar now = Calendar.getInstance();


                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        OperatingExpensesActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setAccentColor(getResources().getColor(R.color.mdtp_accent_color));

                now.add(Calendar.DATE,0);
                //dpd.setMinDate(now);

                dpd.show(getFragmentManager(), "Datepickerdialog");

                dpd.setMaxDate(now);

            }
        });

        /*

        timepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar now = Calendar.getInstance();

                int_today = now.get(Calendar.DATE);

                int hour = now.get(Calendar.HOUR_OF_DAY);
                int min = now.get(Calendar.MINUTE);
                int sec = now.get(Calendar.SECOND);

                TimePickerDialog tpd = TimePickerDialog.newInstance(
                        OperatingExpensesActivity.this,
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        false);

                tpd.setAccentColor(getResources().getColor(R.color.mdtp_accent_color));

                if (int_today == int_selected_day)
                {
                    //tpd.setMinTime(now.get(Calendar.HOUR_OF_DAY),Calendar.MINUTE,Calendar.SECOND);
                    tpd.setMaxTime(hour,min,sec);
                }

                tpd.show(getFragmentManager(), "Timepickerdialog"); //Datepickerdialog



            }
        });

*/

        str_user_id = global.getAl_login_list().get(0).get(GlobalConstants.USER_ID);


       callWebServices();

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        String d = ""+year+"-"+(monthOfYear+1)+"-"+dayOfMonth;

        int_selected_day = dayOfMonth;

        date.setText(d);
    }

    /*

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


    */



    /**
     * Show the details of transaction
     */


    public class CustomDialogClass extends Dialog {

        public Activity c;

        public TextView tv_balance,tv_account_total,tv_wd_amount;
        public Button confirm,cancel;

        public CustomDialogClass(Activity a) {
            super(a);
            // TODO Auto-generated constructor stub
            this.c = a;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.withdraw_dialog);

            confirm = (Button) findViewById(R.id.confirm);
            cancel = (Button) findViewById(R.id.cancel);

            tv_account_total = (TextView) findViewById(R.id.tv_account_total);
            tv_wd_amount = (TextView) findViewById(R.id.tv_wd_amount);
            tv_balance = (TextView) findViewById(R.id.tv_balance);

            float account_total = Float.parseFloat(global.getStr_exp_bal());
            float wd_amount = Float.parseFloat(str_wd_amount);
            float balance = account_total - wd_amount ;

            String formatted_exp_bal =  global.getStr_exp_bal();

            formatted_exp_bal = FormatString.getCommaInString(formatted_exp_bal);

            tv_account_total.setText("EXPENSE BALANCE : "+formatted_exp_bal+" TZS");  //global.getStr_exp_bal()

            String formatted_wd_amount =  str_wd_amount;

            formatted_wd_amount = FormatString.getCommaInString(formatted_wd_amount);

            tv_wd_amount.setText("WITHDRAW : "+formatted_wd_amount+" TZS");


            String formatted_bal =  String.valueOf(balance);

            formatted_bal = FormatString.getCommaInString(formatted_bal);

            tv_balance.setText("PROJECTED BALANCE : "+formatted_bal+" TZS");  //String.valueOf(balance)


            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (cn.isNetConnected())
                    {

                        if (is_pic_selected)
                        {
                            sendImage(photo);
                        }

                        dismiss();

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

                        new WithDrawMoneyAsyncTask().execute();
                    }
                    else {
                        Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }

                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    dismiss();
                }
            });


        }
    }


    /**
     * Hit the web service and withdraw the money
     */

    public class WithDrawMoneyAsyncTask extends AsyncTask<String, String, String> {

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

                al_str_key.add(GlobalConstants.WD_USER_ID);
                al_str_value.add(str_user_id);

                al_str_key.add(GlobalConstants.WD_AMOUNT);
                al_str_value.add(str_wd_amount);

                al_str_key.add(GlobalConstants.WD_REASON);
                al_str_value.add(str_exp_rsn);

                if (is_pic_selected)
                {
                    al_str_key.add(GlobalConstants.PT_IMAGE);
                    al_str_value.add(data.get(UPLOAD_KEY));
                }

//                al_str_key.add(GlobalConstants.WD_PETTY_RSN);
//                al_str_value.add(str_pety_rsn);

                al_str_key.add(GlobalConstants.WD_REMARK);
                al_str_value.add(str_remarks);

                al_str_key.add(GlobalConstants.BRANCH);
                al_str_value.add(str_branch);

                al_str_key.add(GlobalConstants.CREATED);
                al_str_value.add(format_time);

                al_str_key.add(GlobalConstants.ACTION);
                al_str_value.add("add_expenses");

                for (int i = 0 ; i<al_str_key.size() ; i++)
                {
                    Log.i("Key",al_str_key.get(i));
                    Log.i("Value",al_str_value.get(i));
                }

                message = ws.WithdrawMoneyService(context, al_str_key, al_str_value);


            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            gif_loader.setVisibility(View.INVISIBLE);

            if (message.equalsIgnoreCase("true"))
            {

                date.setText("Date");

              //  time.setText("Time");

                callWebServices();

                ed_wd_amount.setText("");
                ed_other.setText("");
                // iv_receipt.refreshDrawableState();

                iv_receipt.setImageBitmap(null);

                is_pic_selected = false;

                showDoneDialog();
            }
            else {

                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }

        }


    }


    /**
     * Amount not sufficient dialog
     */

    public void insufficientDialog()
    {

        final Dialog doneDialog = new Dialog(context);

        doneDialog.setContentView(R.layout.insufficient_amount_dialog);

        //doneDialog.create();
        doneDialog.show();

        Button tv_ok = (Button) doneDialog.findViewById(R.id.tv_ok);
        TextView tv_account_total = (TextView) doneDialog.findViewById(R.id.tv_account_total);
        TextView tv_wd_amount = (TextView) doneDialog.findViewById(R.id.tv_wd_amount);

        tv_account_total.setText("EXPENSE BALANCE : "+global.getStr_exp_bal()+" TZS");
        tv_wd_amount.setText("WITHDRAW : "+str_wd_amount+" TZS");

        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                doneDialog.dismiss();
            }
        });
    }


    /**
     * Custom Done dialog
     */

    public void showDoneDialog()
    {

        final Dialog doneDialog = new Dialog(context);

        doneDialog.setContentView(R.layout.stockalertdialog);

        //doneDialog.create();
        doneDialog.show();

        Button tv_yes = (Button) doneDialog.findViewById(R.id.tv_yes);
        Button tv_no = (Button) doneDialog.findViewById(R.id.tv_no);
        TextView tv_message = (TextView) doneDialog.findViewById(R.id.tv_message);
        TextView tv_title = (TextView) doneDialog.findViewById(R.id.tv_title);

        tv_title.setText("Confirmation Dialog");
        tv_message.setText("Money Withdrawn successfully");
        tv_yes.setText("OK");
        tv_no.setVisibility(View.GONE);

        tv_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cdd.dismiss();
                doneDialog.cancel();

              //  finish();

            }
        });
    }


    /**
     * Hit service and get the Expense balance
     */


    public class GetExpBalanceAsyncTask extends AsyncTask<String, String, String>
    {

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

                al_str_key.add(GlobalConstants.ACTION);
                al_str_value.add("get_expense_balance");

                for (int i =0 ; i < al_str_key.size() ; i++)
                {
                    Log.i("Key",""+ al_str_key.get(i));
                    Log.i("Value",""+ al_str_value.get(i));
                }

                message = ws.GetExpenseBalanceService(context, al_str_key, al_str_value);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            gif_loader.setVisibility(View.INVISIBLE);

            if (message.equalsIgnoreCase("true"))
            {
                String formatted_exp_bal =  global.getStr_exp_bal();

                formatted_exp_bal = FormatString.getCommaInString(formatted_exp_bal);

                tv_total_amount.setText(formatted_exp_bal+" TZS");  //global.getStr_exp_bal()

            }
            else {
                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

    }


    /**
     * Show clicked pic in imageview
     * @param requestCode
     * @param resultCode
     * @param data
     */


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {
            photo = (Bitmap) data.getExtras().get("data");
            iv_receipt.setImageBitmap(photo);

            is_pic_selected = true;

        }

    }


    /**
     * Convert image into string
     * @param bmp
     * @return
     */


    public String getStringImage(Bitmap bmp)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }


    /**
     * Save image in Hash map
     * @param params
     */


    public void sendImage(Bitmap... params)
    {
        Bitmap bitmap = params[0];

        String uploadImage = getStringImage(bitmap);

        data = new HashMap<>();

        data.put(UPLOAD_KEY, uploadImage);

    }





    /**
     * Validate Amount is sufficient or not
     */

    public void validateAmount()
    {
        str_wd_amount = ed_wd_amount.getText().toString().trim();

        int wd_amount = Integer.parseInt(str_wd_amount);
        int exp_bal = Integer.parseInt(global.getStr_exp_bal());

        if (exp_bal > wd_amount)
        {
            str_date = date.getText().toString();
            str_time =  "00:00:00";           // str_time_pick ;

            cdd = new CustomDialogClass(OperatingExpensesActivity.this);
            cdd.show();

        }
        else
        {
            insufficientDialog();
        }

    }


    public void callWebServices()
    {
        if (cn.isNetConnected())
        {
            new GetExpBalanceAsyncTask().execute();
        }
        else
        {
            Toast.makeText(OperatingExpensesActivity.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
        }

    }


}
