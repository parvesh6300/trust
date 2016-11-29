package dcube.com.trust.utils;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import dcube.com.trust.R;

/**
 * Created by Sagar on 28/11/16.
 */
public class CartAdapter extends BaseAdapter {

    Context context;
    public static LayoutInflater inflater;

    ArrayList<String> al_product_name= new ArrayList<>();
    ArrayList<String> al_product_desc= new ArrayList<>();
    ArrayList<String> al_product_price= new ArrayList<>();


    public CartAdapter(Context mcontext)
    {
        context = mcontext;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        addValues();

    }

    public class ViewHolder
    {
        TextView tv_name,tv_desc,tv_price;
        EditText ed_quantity;
        ImageView iv_product_image,iv_cancel;
    }

    @Override
    public View getView(final int pos, View convertView, ViewGroup viewGroup) {

        final ViewHolder holder= new ViewHolder();


        convertView = inflater.inflate(R.layout.cart_list,viewGroup,false);

        holder.tv_name= (TextView)convertView.findViewById(R.id.tv_name);
        holder.tv_desc= (TextView)convertView.findViewById(R.id.tv_desc);
        holder.tv_price=(TextView)convertView.findViewById(R.id.tv_price);

        holder.ed_quantity = (EditText)convertView.findViewById(R.id.ed_quantity);

        holder.iv_product_image = (ImageView) convertView.findViewById(R.id.iv_product_image);
        holder.iv_cancel = (ImageView) convertView.findViewById(R.id.iv_cancel);


        holder.tv_name.setText(al_product_name.get(pos));
        holder.tv_desc.setText(al_product_desc.get(pos));
        holder.tv_price.setText(al_product_price.get(pos));

        holder.ed_quantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                try {
                    int qty = Integer.parseInt(holder.ed_quantity.getText().toString());

                    String price = String.valueOf(qty* Integer.parseInt(al_product_price.get(pos)));

                    holder.tv_price.setText(price);
                }
                catch (Exception e)
                {

                }



            }
        });


        return convertView;
    }

    @Override
    public int getCount() {
        return al_product_name.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    public void addValues()
    {
        al_product_name.add("Bull Condom");
        al_product_name.add("Fiesta");
        al_product_name.add("Silverline");

        al_product_desc.add("Best for the use");
        al_product_desc.add("Gives best feeling");
        al_product_desc.add("Red in color with strawberry flavor");

        al_product_price.add("100");
        al_product_price.add("150");
        al_product_price.add("180");

    }



}
