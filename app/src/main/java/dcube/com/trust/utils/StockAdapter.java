package dcube.com.trust.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import dcube.com.trust.R;

/**
 * Created by Sagar on 14/10/16.
 */
public class StockAdapter extends BaseAdapter {

    Context mcontext;

    ArrayList<String> al_product= new ArrayList<>();
    ArrayList<String> al_category= new ArrayList<>();
    ArrayList<String> al_quantity= new ArrayList<>();

    public static LayoutInflater inflater;

    Dialog dialog;
    Dialog alertDialog;

    ViewHolder holder;

    public  int quantity;

    public StockAdapter(Context context, ArrayList<String> product,ArrayList<String> category,ArrayList<String> quantity)
    {
        this.mcontext= context;
        this.al_product=product;
        this.al_category=category;
        this.al_quantity=quantity;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public class ViewHolder
    {
        TextView tv_product,tv_category,tv_quantity,tv_quantity_label;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {


        holder= new ViewHolder();

        convertView = inflater.inflate(R.layout.viewstock,null);

        holder.tv_product= (TextView)convertView.findViewById(R.id.tv_product);
        holder.tv_category= (TextView)convertView.findViewById(R.id.tv_category);
        holder.tv_quantity= (TextView)convertView.findViewById(R.id.tv_quantity);
        holder.tv_quantity_label=(TextView)convertView.findViewById(R.id.tv_quantity_label);

        holder.tv_product.setText(al_product.get(i));

        Log.e("Product","Product "+al_product.get(i));

        holder.tv_category.setText(al_category.get(i));

        Log.e("Category","Category "+al_category.get(i));

        holder.tv_quantity.setText(al_quantity.get(i));

        Log.e("Quantity","Quantity "+al_quantity.get(i));

        holder.tv_quantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.tv_quantity.setBackgroundColor(Color.parseColor("#603370"));
                holder.tv_quantity.setTextColor(Color.parseColor("#ffffff"));

                dialog = new Dialog(mcontext);

                dialog.setContentView(R.layout.quantitydialog);

                final TextView tv_quantity= (TextView)dialog.findViewById(R.id.tv_quantity);
                TextView tv_plus= (TextView)dialog.findViewById(R.id.tv_plus);
                TextView tv_minus= (TextView)dialog.findViewById(R.id.tv_minus);
                TextView tv_current_time= (TextView)dialog.findViewById(R.id.tv_current_time);
                TextView tv_product_name= (TextView)dialog.findViewById(R.id.tv_product_name);
                TextView tv_product_cat= (TextView)dialog.findViewById(R.id.tv_product_cat);
                TextView tv_request= (TextView)dialog.findViewById(R.id.tv_request);
                TextView tv_cancel=(TextView)dialog.findViewById(R.id.tv_cancel);

                dialog.setCancelable(false);
                dialog.create();
                dialog.show();

                tv_plus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        quantity++;
                        tv_quantity.setText(String.valueOf(quantity));
                    }
                });


                tv_minus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (quantity>0){
                            quantity--;
                        }

                        tv_quantity.setText(String.valueOf(quantity));
                    }
                });

                tv_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog.cancel();

                        holder.tv_quantity.setBackgroundResource(R.drawable.rounded_corner);
                        holder.tv_quantity.setText(al_quantity.get(i));
                        holder.tv_quantity.setTextColor(Color.parseColor("#603370"));

                    }
                });

                tv_request.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        showAlertDialog();
                    }
                });
            }
        });

        return convertView;
    }

    @Override
    public int getCount() {
        return al_product.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    public void showAlertDialog()
    {
        alertDialog = new Dialog(mcontext);

        alertDialog.setContentView(R.layout.stockalertdialog);
        //alertDialog.create();
        alertDialog.show();

        TextView tv_yes= (TextView)alertDialog.findViewById(R.id.tv_yes);
        TextView tv_no= (TextView)alertDialog.findViewById(R.id.tv_no);

        tv_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.cancel();
                showDoneDialog();
            }
        });

        tv_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialog.cancel();
            }
        });
    }

    public void showDoneDialog()
    {
        final Dialog doneDialog= new Dialog(mcontext);

        doneDialog.setContentView(R.layout.stockalertdialog);

        //doneDialog.create();
        doneDialog.show();

        TextView tv_yes= (TextView)doneDialog.findViewById(R.id.tv_yes);
        TextView tv_no= (TextView)doneDialog.findViewById(R.id.tv_no);
        TextView tv_message=(TextView)doneDialog.findViewById(R.id.tv_message);
        TextView tv_title=(TextView)doneDialog.findViewById(R.id.tv_title);

        tv_title.setText("Confirmation Dialop");
        tv_message.setText("Stock has been requested");
        tv_yes.setText("OK");
        tv_no.setVisibility(View.GONE);

        tv_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialog.cancel();
                doneDialog.cancel();
            }
        });
    }
}
