package dcube.com.trust.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import WebServicesHandler.CheckNetConnection;
import WebServicesHandler.GlobalConstants;
import WebServicesHandler.WebServices;
import dcube.com.trust.MoneyBankedActivity;
import dcube.com.trust.R;

/**
 * Created by Rohit on 17/11/16.
 */

public class MoneyBankedAdapter extends BaseAdapter {

    Context context;

    public static LayoutInflater inflater;

    ArrayList<String> al_money_detail;
    ArrayList<String> al_money_amount;
    ArrayList<String> al_date;
    ArrayList<String> al_money_bank_id;

    CustomDialogClass cdd;

    Global global;

    CheckNetConnection cn;

    WebServices ws;

    String str_upd_amount,str_upd_rsn,str_upd_id;


    public MoneyBankedAdapter(Context mcontext)
    {

        this.context = mcontext;

        global = (Global) context.getApplicationContext();

        al_money_detail= new ArrayList<>();
        al_money_amount= new ArrayList<>();
        al_date= new ArrayList<>();
        al_money_bank_id = new ArrayList<>();

        cn = new CheckNetConnection(context);

        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        for (HashMap<String, String> hashmap : global.getAl_money_bank_history())
        {
            al_money_detail.add(hashmap.get(GlobalConstants.MONEY_BANKED_REASON));
            al_date.add(hashmap.get(GlobalConstants.MONEY_BANKED_DATE));
            al_money_amount.add(hashmap.get(GlobalConstants.MONEY_BANKED_HIS_AMOUNT));
            al_money_bank_id.add(hashmap.get(GlobalConstants.MONEY_BANKED_ID));

        }



    }

    public class ViewHolder{

        TextView tv_date,tv_month,tv_year,tv_detail,tv_amount;
    }


    @Override
    public View getView(final int pos, View convertview, ViewGroup viewGroup) {

        ViewHolder holder= new ViewHolder();

        convertview= inflater.inflate(R.layout.moneybankedlist,null);

        holder.tv_date= (TextView)convertview.findViewById(R.id.tv_date);
        holder.tv_month= (TextView)convertview.findViewById(R.id.tv_month);
        holder.tv_year= (TextView)convertview.findViewById(R.id.tv_year);
        holder.tv_detail= (TextView)convertview.findViewById(R.id.tv_detail);
        holder.tv_amount= (TextView)convertview.findViewById(R.id.tv_amount);

        // "date": "2017-01-19",

        String[] date_time = al_date.get(pos).split("\\s+");

        String[] date = date_time[0].split("-");

        holder.tv_date.setText(date[2]);
        holder.tv_month.setText(date[1]+" '");
        holder.tv_year.setText(date[0]);

      //  holder.tv_detail.setText(al_money_detail.get(pos));

        String amount = al_money_amount.get(pos);

        amount = FormatString.getCommaInString( al_money_amount.get(pos));

        holder.tv_amount.setText(amount);


        convertview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                cdd = new CustomDialogClass(context,pos);
//                cdd.show();
            }
        });


        return convertview;
    }

    @Override
    public int getCount() {
        return al_money_detail.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }




    public class CustomDialogClass extends Dialog {

        public Context ctx;

        TextView tv_date,tv_month,tv_year;
        EditText ed_reason,ed_amount;
        Button cancel,confirm;

        int pos;

        public CustomDialogClass(Context context, int position) {

            super(context);
            // TODO Auto-generated constructor stub
            this.ctx = context;

            pos = position;

        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);

            requestWindowFeature(Window.FEATURE_NO_TITLE);

            setContentView(R.layout.money_bank_update_dialog);

            confirm = (Button) findViewById(R.id.confirm);
            cancel = (Button) findViewById(R.id.cancel);
            tv_date= (TextView) findViewById(R.id.tv_date);
            tv_month= (TextView)findViewById(R.id.tv_month);
            tv_year= (TextView) findViewById(R.id.tv_year);

            ed_reason= (EditText) findViewById(R.id.ed_reason);
            ed_amount= (EditText) findViewById(R.id.ed_amount);

//            ed_reason.setClickable(false);
            ed_reason.setEnabled(false);

            String[] date_time = al_date.get(pos).split("\\s+");

            String[] date = date_time[0].split("-");

            tv_date.setText(date[2]);
            tv_month.setText(date[1]+" ");
            tv_year.setText("'"+date[0]);

            ed_reason.setText("Money Banked");
            ed_amount.setText(al_money_amount.get(pos));


            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (ed_amount.getText().toString().matches(""))
                    {
                        Toast.makeText(context, "Enter Amount", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {

                        str_upd_id = al_money_bank_id.get(pos);
                        str_upd_amount = ed_amount.getText().toString();
                        str_upd_rsn = ed_reason.getText().toString();


                        al_money_amount.set(pos,str_upd_amount);


                        showAlertDialog();
                    }

                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    cdd.dismiss();
                }
            });


        }
    }


    public void showAlertDialog()
    {
        final Dialog alertDialog = new Dialog(context);

        alertDialog.setContentView(R.layout.confirmation_dialog);
        //alertDialog.create();
        alertDialog.show();

        Button tv_yes = (Button) alertDialog.findViewById(R.id.tv_yes);
        Button tv_no = (Button) alertDialog.findViewById(R.id.tv_no);

        tv_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (cn.isNetConnected())
                {
                    cdd.cancel();

                    new UpdateMoneyBankAsyncTask().execute();

                    alertDialog.dismiss();
                }
                else
                {
                    Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                }

            }
        });

        tv_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialog.cancel();
            }
        });
    }


    public class UpdateMoneyBankAsyncTask extends AsyncTask<String, String, String>
    {

        String message = "";

        @Override
        protected void onPreExecute() {

            ((MoneyBankedActivity)context).loader(context,true);
        }

        @Override
        protected String doInBackground(String... params) {

            try {

                ArrayList<String> al_str_key = new ArrayList<>();
                ArrayList<String> al_str_value = new ArrayList<>();

                al_str_key.add(GlobalConstants.USER_BRANCH_ID);
                al_str_value.add(global.getAl_login_list().get(0).get(GlobalConstants.USER_BRANCH_ID));

                al_str_key.add(GlobalConstants.MONEY_BANKED_HIS_AMOUNT);
                al_str_value.add(str_upd_amount);

                al_str_key.add(GlobalConstants.MONEY_BANKED_REASON);
                al_str_value.add(str_upd_rsn);

                al_str_key.add(GlobalConstants.MONEY_BANKED_ID);
                al_str_value.add(str_upd_id);

                al_str_key.add(GlobalConstants.ACTION);
                al_str_value.add("edit_banked_money");

                Log.i("Key",""+al_str_key);
                Log.i("Value",""+al_str_value);

                message = ws.UpdateMoneyBankService(context, al_str_key, al_str_value);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            ((MoneyBankedActivity)context).loader(context,false);

            updateBalance(context);

            if (message.equalsIgnoreCase("true"))
            {
                notifyDataSetChanged();
            }
            else
            {
                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

    }



    public void updateBalance(Context context)
    {
        ((MoneyBankedActivity)context).updateBalance(context);
    }



}
