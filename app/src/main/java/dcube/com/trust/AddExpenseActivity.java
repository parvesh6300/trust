package dcube.com.trust;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import WebServicesHandler.CheckNetConnection;
import WebServicesHandler.GlobalConstants;
import WebServicesHandler.WebServices;
import dcube.com.trust.utils.ExpenseAdapter;
import dcube.com.trust.utils.Global;
import okhttp3.OkHttpClient;
import pl.droidsonroids.gif.GifTextView;

public class AddExpenseActivity extends Activity {

    ListView list_expense;

    ExpenseAdapter expenseAdapter;

    TextView tv_total_amount,tv_submit;

    EditText ed_expense_amount,ed_reason;
    GifTextView gif_loader;
    Global global;
    String str_amount,str_reason,str_branch;
    WebServices ws;
    Context context = AddExpenseActivity.this;
    CustomDialogClass cdd;

    CheckNetConnection cn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        global = (Global) getApplicationContext();

        cn = new CheckNetConnection(this);

        gif_loader = (GifTextView) findViewById(R.id.gif_loader);

        list_expense= (ListView)findViewById(R.id.list_expense);

        ed_expense_amount=(EditText)findViewById(R.id.ed_expense_amount);
        ed_reason=(EditText)findViewById(R.id.ed_reason);
        tv_total_amount=(TextView)findViewById(R.id.tv_total_amount);
        tv_submit=(TextView)findViewById(R.id.tv_submit);

        str_branch = global.getAl_login_list().get(0).get(GlobalConstants.USER_BRANCH);

        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ed_expense_amount.getText().toString().matches(""))
                {
                    Toast.makeText(AddExpenseActivity.this, "Enter amount", Toast.LENGTH_SHORT).show();

                }
                else if (ed_reason.getText().toString().matches(""))
                {
                    Toast.makeText(AddExpenseActivity.this, "Specify Reason", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    str_amount = ed_expense_amount.getText().toString();
                    str_reason = ed_reason.getText().toString();

                    cdd = new CustomDialogClass(AddExpenseActivity.this);
                    cdd.show();
                }

            }
        });


        if (cn.isNetConnected())
        {
            new ExpenseHistoryAsyncTask().execute();
        }
        else
        {
            Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
        }

    }

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

            tv_expense_amount.setText("Expense Amount : "+str_amount+" Tsh");
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

            gif_loader.setVisibility(View.GONE);

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



    public class ExpenseHistoryAsyncTask extends AsyncTask<String, String, String> {

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

                al_str_key.add(GlobalConstants.BRANCH);
                al_str_value.add(str_branch);

                al_str_key.add(GlobalConstants.ACTION);
                al_str_value.add("get_in_expense");

                message = ws.ExpenseHistoryService(context, al_str_key, al_str_value);

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

                expenseAdapter= new ExpenseAdapter(context);
                list_expense.setAdapter(expenseAdapter);

            }
            else {
                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

    }

}
