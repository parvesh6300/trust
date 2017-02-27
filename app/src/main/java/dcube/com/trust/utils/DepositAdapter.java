package dcube.com.trust.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
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
import dcube.com.trust.R;

/**
 * Created by Rohit on 18/10/16.
 */
public class DepositAdapter extends BaseAdapter {

    Context context;

    public static LayoutInflater inflater;

    ArrayList<String> al_deposit_detail= new ArrayList<>();
    ArrayList<String> al_deposit_amount= new ArrayList<>();
    ArrayList<String> al_date= new ArrayList<>();

    CustomDialogClass cdd;

    Global global;


    CheckNetConnection cn;

    public DepositAdapter(Context mcontext)
    {
        this.context= mcontext;
        al_date = new ArrayList<>();
        al_deposit_detail = new ArrayList<>();
        al_deposit_amount = new ArrayList<>();

        global = (Global) context.getApplicationContext();

        cn = new CheckNetConnection(context);

        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        for (HashMap<String,String> hashmap  : global.getAl_deposit_details() )
        {
            al_date.add(hashmap.get(GlobalConstants.DEPOSIT_DATE));
            al_deposit_amount.add(hashmap.get(GlobalConstants.DEPOSIT_AMOUNT));
            al_deposit_detail.add(hashmap.get(GlobalConstants.DEPOSIT_REMARKS));
        }

    }

    public class ViewHolder{

        TextView tv_date,tv_month,tv_year,tv_detail,tv_amount;
    }

    @Override
    public View getView(final int pos, View convertview, ViewGroup viewGroup) {

        ViewHolder holder= new ViewHolder();

        convertview= inflater.inflate(R.layout.depositlist,null);

        holder.tv_date= (TextView)convertview.findViewById(R.id.tv_date);
        holder.tv_month= (TextView)convertview.findViewById(R.id.tv_month);
        holder.tv_year= (TextView)convertview.findViewById(R.id.tv_year);
        holder.tv_detail= (TextView)convertview.findViewById(R.id.tv_detail);
        holder.tv_amount= (TextView)convertview.findViewById(R.id.tv_amount);

        String[] date_time = al_date.get(pos).split("\\s+");

        String[] date = date_time[0].split("-");

        holder.tv_date.setText(date[2]);
        holder.tv_month.setText(date[1]+" ");
        holder.tv_year.setText("'"+date[0]);

        holder.tv_detail.setText(al_deposit_detail.get(pos));
        holder.tv_amount.setText(al_deposit_amount.get(pos)+" TZS");


        convertview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cdd = new CustomDialogClass(context,pos);
                cdd.show();
            }
        });

        return convertview;
    }

    @Override
    public int getCount() {
        return al_deposit_amount.size();
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

        TextView tv_date,tv_month,tv_year,cancel,confirm;
        EditText ed_remark,ed_amount;

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

            setContentView(R.layout.deposit_update_dialog);

            confirm = (TextView) findViewById(R.id.confirm);
            cancel = (TextView) findViewById(R.id.cancel);
            tv_date= (TextView) findViewById(R.id.tv_date);
            tv_month= (TextView)findViewById(R.id.tv_month);
            tv_year= (TextView) findViewById(R.id.tv_year);

            ed_remark= (EditText) findViewById(R.id.ed_remark);
            ed_amount= (EditText) findViewById(R.id.ed_amount);

            String[] date_time = al_date.get(pos).split("\\s+");

            String[] date = date_time[0].split("-");

            tv_date.setText(date[2]);
            tv_month.setText(date[1]+" ");
            tv_year.setText("'"+date[0]);

            ed_remark.setText(al_deposit_detail.get(pos));
            ed_amount.setText(al_deposit_amount.get(pos));


            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (ed_remark.getText().toString().matches(""))
                    {
                        Toast.makeText(context, "Enter Remark", Toast.LENGTH_SHORT).show();
                    }
                    else if (ed_amount.getText().toString().matches(""))
                    {
                        Toast.makeText(context, "Enter Amount", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
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



}
