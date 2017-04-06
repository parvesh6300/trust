package dcube.com.trust;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import WebServicesHandler.CheckNetConnection;
import WebServicesHandler.GlobalConstants;
import WebServicesHandler.WebServices;
import dcube.com.trust.utils.ExpenseAdapter;
import dcube.com.trust.utils.Global;
import okhttp3.OkHttpClient;
import pl.droidsonroids.gif.GifTextView;

public class ExpenseHistoryActivity extends Activity implements DatePickerDialog.OnDateSetListener,View.OnClickListener {

    ListView list_expense;

    ExpenseAdapter expenseAdapter;

    LinearLayout lin_date_from,lin_date_to;

    RelativeLayout rel_parent_layout;

    ArrayAdapter<String> spinnerArrayAdapter;

    String[] ITEMS = {"Choose Expense Type","All","Petty Cash","Running Expenses","Salaries","Rent","Consultancies","Equipment",
            "Commodities","DKT commodities","Non DKT commodities","Marketing","Other"};

    Spinner sp_exp_type;

    //   TextView tv_total_amount,tv_submit;

    TextView tv_submit;

    EditText ed_expense_amount,ed_reason;
    GifTextView gif_loader;
    Global global;
    String str_amount,str_reason,str_branch;
    WebServices ws;
    Context context = ExpenseHistoryActivity.this;
    CustomDialogClass cdd;

    CheckNetConnection cn;

    TextView tv_total_expense;

    TextView tv_date_from,tv_date_to;

    DatePickerDialog dpd_from,dpd_to;

    String str_exp_type;

    boolean is_date_selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_expense);

        global = (Global) getApplicationContext();

        cn = new CheckNetConnection(this);

        sp_exp_type = (Spinner) findViewById(R.id.spinner);

        lin_date_from=(LinearLayout)findViewById(R.id.lin_date_from);
        lin_date_to=(LinearLayout)findViewById(R.id.lin_date_to);

        rel_parent_layout = (RelativeLayout) findViewById(R.id.rel_parent_layout);

        tv_date_from=(TextView)findViewById(R.id.tv_date_from);
        tv_date_to=(TextView)findViewById(R.id.tv_date_to);

        gif_loader = (GifTextView) findViewById(R.id.gif_loader);

        list_expense= (ListView)findViewById(R.id.list_expense);

       // ed_expense_amount=(EditText)findViewById(R.id.ed_expense_amount);
        ed_reason=(EditText)findViewById(R.id.ed_reason);

        tv_total_expense = (TextView) findViewById(R.id.tv_total_expense);
//        tv_total_amount=(TextView)findViewById(R.id.tv_total_amount);
        tv_submit=(TextView)findViewById(R.id.tv_submit);

        spinnerArrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, ITEMS);
        sp_exp_type.setAdapter(spinnerArrayAdapter);

        str_branch = global.getAl_login_list().get(0).get(GlobalConstants.USER_BRANCH);


        if (cn.isNetConnected())
        {
            new ExpenseHistoryAsyncTask().execute();
        }
        else
        {
            Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
        }


        sp_exp_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {

                if (pos == 0 || pos ==1)
                {
                    expenseAdapter= new ExpenseAdapter(context,"");
                }
                else
                {
                    str_exp_type = sp_exp_type.getItemAtPosition(pos).toString();
                    expenseAdapter= new ExpenseAdapter(context,str_exp_type);
                }

                list_expense.setAdapter(expenseAdapter);
                expenseAdapter.notifyDataSetChanged();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        lin_date_from.setOnClickListener(this);
        lin_date_to.setOnClickListener(this);
        tv_submit.setOnClickListener(this);


//        rel_parent_layout.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//
//                HideKeyboard.hideSoftKeyboard(ExpenseHistoryActivity.this);
//                return false;
//            }
//        });


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
                is_date_selected = true;
                new ExpenseHistoryAsyncTask().execute();
            }
            else
            {
                Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
            }


        }
    }

    @Override
    public void onClick(View view) {

        if(view == lin_date_from)
        {

            Calendar now = Calendar.getInstance();
            dpd_from = DatePickerDialog.newInstance(ExpenseHistoryActivity.this,
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
            dpd_to = DatePickerDialog.newInstance(ExpenseHistoryActivity.this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            );
            dpd_to.show(getFragmentManager(), "Datepickerdialog");

            now.add(Calendar.DATE,0);
            dpd_to.setMaxDate(now);
        }

        if (view == tv_submit)
        {
            finish();
        }


    }


    /**
     * Custom dialog of expense details
     */

    public class CustomDialogClass extends Dialog {

        public Activity c;

        public TextView cancel,tv_expense_amount;
        public TextView confirm,tv_expense_reason;

        public CustomDialogClass(Activity a) {
            super(a);
            // TODO Auto-generated constructor stub
            this.c = a;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);

            setContentView(R.layout.addexpense_dialog);

            confirm = (TextView) findViewById(R.id.confirm);
            cancel = (TextView) findViewById(R.id.cancel);
            tv_expense_reason = (TextView) findViewById(R.id.tv_expense_reason);
            tv_expense_amount = (TextView) findViewById(R.id.tv_expense_amount);

            tv_expense_amount.setText("Expense Amount : "+str_amount+" TZS");
            tv_expense_reason.setText("Expense : "+str_reason);

            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (cn.isNetConnected())
                    {
                        new AddExpenseAsyncTask().execute();
                    }
                    else
                    {
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
     * Hit web service and Add expense to database
     */

    public class AddExpenseAsyncTask extends AsyncTask<String, String, String> {

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

                al_str_key.add(GlobalConstants.EXP_AMOUNT);
                al_str_value.add(str_amount);

                al_str_key.add(GlobalConstants.EXP_REASON);
                al_str_value.add(str_reason);

                al_str_key.add(GlobalConstants.BRANCH);
                al_str_value.add(str_branch);

                al_str_key.add(GlobalConstants.ACTION);
                al_str_value.add("add_in_expense");

                for (int i =0 ; i < al_str_value.size() ; i++)
                {
                    Log.i("Key",al_str_key.get(i));
                    Log.i("Value",al_str_value.get(i));
                }

                message = ws.AddExpenseService(context, al_str_key, al_str_value);

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
            else
            {
                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

    }


    /**
     * Custom expense deposited dialog
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
        tv_message.setText("Expense Deposited");
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
     * Hit web service and show expense history
     */


    public class ExpenseHistoryAsyncTask extends AsyncTask<String, String, String> {

        OkHttpClient httpClient = new OkHttpClient();
        String resPonse = "";
        String message = "";

        String format_date_from,format_date_to;
        String str_date_from,str_date_to;

        @Override
        protected void onPreExecute() {

            gif_loader.setVisibility(View.VISIBLE);

            if (is_date_selected)
            {
                try {

                    str_date_from = tv_date_from.getText().toString();
                    str_date_to = tv_date_to.getText().toString();

                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  //HH:mm:ss
                    Date date;

                    date = format.parse(str_date_from); //+" 00:00:00"
                    Log.e("From","Date "+date);

                    format_date_from = format.format(date);
                    Log.e("From","Str "+format_date_from);

                    date = format.parse(str_date_to);  //+" 00:00:00"
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

//                al_str_key.add(GlobalConstants.BRANCH);
//                al_str_value.add(str_branch);

                if (is_date_selected)
                {
                    al_str_key.add(GlobalConstants.EXP_DATE_FROM);
                    al_str_value.add(format_date_from);

                    al_str_key.add(GlobalConstants.EXP_DATE_TO);
                    al_str_value.add(format_date_to);
                }

                al_str_key.add(GlobalConstants.ACTION);
                al_str_value.add("get_expense_history");

                Log.i("Key",""+al_str_key);
                Log.i("Value",""+al_str_value);

                message = ws.ExpenseHistoryService(context, al_str_key, al_str_value);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            gif_loader.setVisibility(View.INVISIBLE);

            is_date_selected = false;

            if (message.equalsIgnoreCase("true"))
            {
                list_expense.setVisibility(View.VISIBLE);
                expenseAdapter= new ExpenseAdapter(context,"");
                list_expense.setAdapter(expenseAdapter);
                expenseAdapter.notifyDataSetChanged();

                tv_total_expense.setText(global.getStr_total_expense()+" TZS");

            }
            else
            {
                list_expense.setVisibility(View.INVISIBLE);
                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

    }


    /**
     * Show loader
     * @param context
     * @param show boolean
     */

    public void loader(Context context,boolean show)
    {
        if (show)
        {
            gif_loader.setVisibility(View.VISIBLE);
        }
        else
        {
            gif_loader.setVisibility(View.INVISIBLE);
        }

    }


    /**
     * Updates the Expense List
     * @param context
     */


    public void updateList(Context context)
    {
        new ExpenseHistoryAsyncTask().execute();
    }




}
