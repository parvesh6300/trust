package dcube.com.trust;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import WebServicesHandler.CheckNetConnection;
import WebServicesHandler.GlobalConstants;
import WebServicesHandler.HideKeyboard;
import WebServicesHandler.WebServices;
import dcube.com.trust.utils.Global;
import okhttp3.OkHttpClient;
import pl.droidsonroids.gif.GifTextView;

public class OperatingExpensesActivity extends Activity {

    RadioGroup radio_group;

    RadioButton radio_other,radio_petty_cash,radio_running_exp,radio_dkt_commodity,radio_non_dkt,radio_marketing;
    RadioButton radio_commodity,radio_salary,radio_rent,radio_consultancy,radio_equipment;

    EditText ed_other,ed_wd_amount,ed_petty_rsn;

    ImageView image_view;

    TextView tv_withdraw;

    GifTextView gif_loader;

    Context context;

    WebServices ws;
    Global global;
    String str_user_id;
    String str_exp_rsn="",str_wd_amount="",str_branch="",str_remarks="",str_pety_rsn="";

    CustomDialogClass cdd;

    private static final int CAMERA_REQUEST = 1888;

    CheckNetConnection cn;

    RelativeLayout rel_parent_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_draw_money);

        context = this;

        global = (Global) getApplicationContext();

        rel_parent_layout = (RelativeLayout) findViewById(R.id.rel_parent_layout);

        cn = new CheckNetConnection(this);

        gif_loader = (GifTextView) findViewById(R.id.gif_loader);

        image_view = (ImageView) findViewById(R.id.image_view);

        radio_group=(RadioGroup)findViewById(R.id.radio_group);

        radio_other=(RadioButton)findViewById(R.id.radio_other);
        radio_petty_cash=(RadioButton)findViewById(R.id.radio_petty_cash);
        radio_running_exp=(RadioButton)findViewById(R.id.radio_running_exp);
        radio_commodity=(RadioButton)findViewById(R.id.radio_commodity);
        radio_salary=(RadioButton)findViewById(R.id.radio_salary);
        radio_rent=(RadioButton)findViewById(R.id.radio_rent);
        radio_consultancy=(RadioButton)findViewById(R.id.radio_consultancy);
        radio_equipment=(RadioButton)findViewById(R.id.radio_equipment);

        radio_dkt_commodity = (RadioButton) findViewById(R.id.radio_dkt_commodity);
        radio_non_dkt = (RadioButton) findViewById(R.id.radio_non_dkt);
        radio_marketing = (RadioButton) findViewById(R.id.radio_marketing);

        ed_other=      (EditText)findViewById(R.id.ed_other);
        ed_wd_amount = (EditText) findViewById(R.id.ed_wd_amount);
        ed_petty_rsn = (EditText) findViewById(R.id.ed_petty_rsn);

        tv_withdraw=(TextView)findViewById(R.id.tv_withdraw);

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
                    ed_other.setVisibility(View.GONE);
                }

                if (radio_petty_cash.isChecked())
                {
                    ed_petty_rsn.setVisibility(View.VISIBLE);
                }
                else
                {
                    ed_petty_rsn.setVisibility(View.GONE);
                }


                if (radio_petty_cash.isChecked())
                {
                    str_exp_rsn = "Petty Cash";
                    str_remarks = "NA";
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
                else if (radio_running_exp.isChecked())
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
                else
                {

                    if (radio_petty_cash.isChecked() || radio_other.isChecked() )
                    {
                        if (radio_petty_cash.isChecked())
                        {
                            if (ed_petty_rsn.getText().toString().matches(""))
                            {
                                Toast.makeText(OperatingExpensesActivity.this, "Specify Reason", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                str_remarks = ed_petty_rsn.getText().toString();  //str_pety_rsn
                                str_wd_amount = ed_wd_amount.getText().toString().trim();
                                new GetBranchBalanceAsyncTask().execute();
                            }
                        }
                        else if (radio_other.isChecked())
                        {
                            if (ed_other.getText().toString().matches(""))
                            {
                                Toast.makeText(OperatingExpensesActivity.this, "Specify Reason", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                str_remarks = ed_other.getText().toString();
                                str_wd_amount = ed_wd_amount.getText().toString().trim();
                                new GetBranchBalanceAsyncTask().execute();
                            }
                        }
//                        else
//                        {
//                            str_wd_amount = ed_wd_amount.getText().toString().trim();
//                            new GetBranchBalanceAsyncTask().execute();
//                        }

                    }
                    else
                    {
                        str_wd_amount = ed_wd_amount.getText().toString().trim();
                        new GetBranchBalanceAsyncTask().execute();
                    }
/*

                    if (str_exp_rsn.equalsIgnoreCase("other") )
                    {
                        if (ed_other.getText().toString().matches(""))
                        {
                            Toast.makeText(OperatingExpensesActivity.this, "Specify Reason", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            str_remarks = ed_other.getText().toString();
                        }
                    }
                    else
                    {
                        str_remarks = "NA";
                    }

                    if (radio_petty_cash.isChecked())
                    {
                        if (ed_petty_rsn.getText().toString().matches(""))
                        {
                            Toast.makeText(OperatingExpensesActivity.this, "Specify Reason", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            str_wd_amount = ed_wd_amount.getText().toString().trim();
                            str_pety_rsn = ed_petty_rsn.getText().toString();
                            new GetBranchBalanceAsyncTask().execute();
                        }
                    }
                    else
                    {
                        str_wd_amount = ed_wd_amount.getText().toString().trim();
                        str_pety_rsn = "" ;
                        new GetBranchBalanceAsyncTask().execute();
                    }

                    */

                }

            }
        });


        rel_parent_layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                HideKeyboard.hideSoftKeyboard(OperatingExpensesActivity.this);
                return false;
            }
        });



        str_user_id = global.getAl_login_list().get(0).get(GlobalConstants.USER_ID);

    }

    /**
     * Show the details of transaction
     */


    public class CustomDialogClass extends Dialog {

        public Activity c;

        public TextView cancel,tv_account_total,tv_wd_amount;
        public TextView confirm,tv_balance;

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

            confirm = (TextView) findViewById(R.id.confirm);
            cancel = (TextView) findViewById(R.id.cancel);

            tv_account_total = (TextView) findViewById(R.id.tv_account_total);
            tv_wd_amount = (TextView) findViewById(R.id.tv_wd_amount);
            tv_balance = (TextView) findViewById(R.id.tv_balance);

            float account_total = Float.parseFloat(global.getStr_branch_balance());
            float wd_amount = Float.parseFloat(str_wd_amount);
            float balance = account_total - wd_amount ;

            tv_account_total.setText("ACCOUNT TOTAL : "+global.getStr_branch_balance()+" TZS");
            tv_wd_amount.setText("WITHDRAW : "+str_wd_amount+" TZS");
            tv_balance.setText("PROJECTED BALANCE : "+String.valueOf(balance)+" TZS");


            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (cn.isNetConnected())
                    {
                        dismiss();
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

//                al_str_key.add(GlobalConstants.WD_PETTY_RSN);
//                al_str_value.add(str_pety_rsn);

                al_str_key.add(GlobalConstants.WD_REMARK);
                al_str_value.add(str_remarks);

                al_str_key.add(GlobalConstants.BRANCH);
                al_str_value.add(str_branch);

                al_str_key.add(GlobalConstants.ACTION);
                al_str_value.add("add_in_expense");

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

    public void insufficientDialog() {

        final Dialog doneDialog = new Dialog(context);

        doneDialog.setContentView(R.layout.insufficient_amount_dialog);

        //doneDialog.create();
        doneDialog.show();

        TextView tv_ok = (TextView) doneDialog.findViewById(R.id.tv_ok);
        TextView tv_account_total = (TextView) doneDialog.findViewById(R.id.tv_account_total);
        TextView tv_wd_amount = (TextView) doneDialog.findViewById(R.id.tv_wd_amount);

        tv_account_total.setText("ACCOUNT TOTAL : "+global.getStr_branch_balance()+" TZS");
        tv_wd_amount.setText("WITHDRAW : "+str_wd_amount+" TZS");

        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                doneDialog.dismiss();
            }
        });
    }


    /**
     * Custom confirmation dialog
     */

    public void showDoneDialog() {

        final Dialog doneDialog = new Dialog(context);

        doneDialog.setContentView(R.layout.stockalertdialog);

        //doneDialog.create();
        doneDialog.show();

        TextView tv_yes = (TextView) doneDialog.findViewById(R.id.tv_yes);
        TextView tv_no = (TextView) doneDialog.findViewById(R.id.tv_no);
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
                finish();

            }
        });
    }


    /**
     * Hit service and get the branch balance
     */


    public class GetBranchBalanceAsyncTask extends AsyncTask<String, String, String> {

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
                al_str_value.add("get_branch_balance");

                for (int i =0 ; i < al_str_key.size() ; i++)
                {
                    Log.i("Key",""+ al_str_key.get(i));
                    Log.i("Value",""+ al_str_value.get(i));
                }

                message = ws.GetBranchBalanceService(context, al_str_key, al_str_value);

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
                float account_total = Float.parseFloat(global.getStr_branch_balance());
                float wd_amount = Float.parseFloat(str_wd_amount);

                if (account_total > wd_amount)
                {
                    cdd = new CustomDialogClass(OperatingExpensesActivity.this);
                    cdd.show();
                }
                else
                {
                    insufficientDialog();
                }

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
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            image_view.setImageBitmap(photo);
        }

    }
}
