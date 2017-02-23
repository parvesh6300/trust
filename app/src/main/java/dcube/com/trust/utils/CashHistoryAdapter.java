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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import WebServicesHandler.CheckNetConnection;
import WebServicesHandler.GlobalConstants;
import WebServicesHandler.WebServices;
import dcube.com.trust.CashInHandHistory;
import dcube.com.trust.R;

/**
 * Created by Rohit on 23/02/17.
 */
public class CashHistoryAdapter extends BaseAdapter {

    Context context;

    public static LayoutInflater inflater;

    ArrayList<String> al_cash_detail= new ArrayList<>();
    ArrayList<String> al_cash_amount= new ArrayList<>();
    ArrayList<String> al_date= new ArrayList<>();
    ArrayList<String> al_cash_id= new ArrayList<>();

    Global global;

    CheckNetConnection cn;

    WebServices ws;

    CustomDialogClass cdd;

    String str_upd_amount,str_upd_rsn,str_upd_hand_id;


    public CashHistoryAdapter(Context mcontext)
    {
        this.context= mcontext;

        this.al_date= new ArrayList<>();
        this.al_cash_detail= new ArrayList<>();
        this.al_cash_amount= new ArrayList<>();


        global = (Global) context.getApplicationContext();

        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        cn = new CheckNetConnection(context);

        for (HashMap<String,String> hashmap : global.getAl_cash_in_hand_his() )
        {

            al_cash_amount.add(hashmap.get(GlobalConstants.HAND_AMOUNT));
            al_cash_detail.add(hashmap.get(GlobalConstants.HAND_HIS_REASON));
            al_date.add(hashmap.get(GlobalConstants.HAND_HIS_CREATED));
            al_cash_id.add(hashmap.get(GlobalConstants.HAND_HIS_ID));

        }

    }

    public class ViewHolder{

        TextView tv_date,tv_month,tv_year,tv_detail,tv_amount;
    }



    @Override
    public int getCount() {
        return al_cash_amount.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int pos, View convertview, ViewGroup viewGroup) {

        ViewHolder holder= new ViewHolder();

        convertview= inflater.inflate(R.layout.cash_hist_list,null);

        holder.tv_date= (TextView)convertview.findViewById(R.id.tv_date);
        holder.tv_month= (TextView)convertview.findViewById(R.id.tv_month);
        holder.tv_year= (TextView)convertview.findViewById(R.id.tv_year);
        holder.tv_detail= (TextView)convertview.findViewById(R.id.tv_detail);
        holder.tv_amount= (TextView)convertview.findViewById(R.id.tv_amount);

        String[] date_time = al_date.get(pos).split("\\s+");

        String[] date = date_time[0].split("-");


        holder.tv_month.setText(date[1]+" ");
        holder.tv_year.setText("'"+date[0]);
        holder.tv_date.setText(date[2]);


        holder.tv_amount.setText(al_cash_amount.get(pos)+" TZS");

        holder.tv_detail.setText(al_cash_detail.get(pos));


        convertview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cdd = new CustomDialogClass(context,pos);
                cdd.show();
            }
        });


        return convertview;


    }


    public class CustomDialogClass extends Dialog {

        public Context ctx;

        TextView tv_date,tv_month,tv_year,cancel,confirm;
        EditText ed_amount,ed_reason;

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

            setContentView(R.layout.cash_update_dialog);

            confirm = (TextView) findViewById(R.id.confirm);
            cancel = (TextView) findViewById(R.id.cancel);
            tv_date= (TextView) findViewById(R.id.tv_date);
            tv_month= (TextView)findViewById(R.id.tv_month);
            tv_year= (TextView) findViewById(R.id.tv_year);

            ed_amount= (EditText) findViewById(R.id.ed_amount);
            ed_reason = (EditText) findViewById(R.id.ed_reason);

            ed_reason.setEnabled(false);


            String[] date_time = al_date.get(pos).split("\\s+");

            String[] date = date_time[0].split("-");

            tv_date.setText(date[2]);
            tv_month.setText(date[1]+" ");
            tv_year.setText("'"+date[0]);

            ed_amount.setText(al_cash_amount.get(pos));
            ed_reason.setText(al_cash_detail.get(pos));


            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (ed_reason.getText().toString().matches(""))
                    {
                        Toast.makeText(context, "Enter Reason", Toast.LENGTH_SHORT).show();
                    }
                    else if (ed_amount.getText().toString().matches(""))
                    {
                        Toast.makeText(context, "Enter Amount", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        str_upd_hand_id = al_cash_id.get(pos);
                        str_upd_amount = ed_amount.getText().toString();
                        str_upd_rsn = ed_reason.getText().toString();

                        al_cash_amount.set(pos,str_upd_amount);
                        al_cash_detail.set(pos,str_upd_rsn);

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

        TextView tv_yes = (TextView) alertDialog.findViewById(R.id.tv_yes);
        TextView tv_no = (TextView) alertDialog.findViewById(R.id.tv_no);

        tv_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (cn.isNetConnected())
                {
                    cdd.cancel();

                    new UpdateExpenseAsyncTask().execute();

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



    public class UpdateExpenseAsyncTask extends AsyncTask<String, String, String> {

        String message = "";

        @Override
        protected void onPreExecute() {

            ((CashInHandHistory)context).loader(context,true);
        }

        @Override
        protected String doInBackground(String... params) {

            try {

                ArrayList<String> al_str_key = new ArrayList<>();
                ArrayList<String> al_str_value = new ArrayList<>();

                al_str_key.add(GlobalConstants.USER_BRANCH_ID);
                al_str_value.add(global.getAl_login_list().get(0).get(GlobalConstants.USER_BRANCH_ID));

                al_str_key.add(GlobalConstants.HAND_HIS_AMOUNT);
                al_str_value.add(str_upd_amount);

                al_str_key.add(GlobalConstants.HAND_HIS_REASON);
                al_str_value.add(str_upd_rsn);

                al_str_key.add(GlobalConstants.HAND_HIS_ID);
                al_str_value.add(str_upd_hand_id);

                al_str_key.add(GlobalConstants.ACTION);
                al_str_value.add("edit_cash_in_hand");

                Log.i("Key",""+al_str_key);
                Log.i("Value",""+al_str_value);

                message = ws.UpdateCashInHandService(context, al_str_key, al_str_value);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            ((CashInHandHistory)context).loader(context,false);

            if (message.equalsIgnoreCase("true"))
            {
                // ((ExpenseHistoryActivity)context).updateList(context);
                notifyDataSetChanged();
            }
            else
            {
                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

    }



    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }





}
