package dcube.com.trust;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dcube.com.trust.utils.Global;

/**
 * Created by yadi on 14/10/16.
 */
public class InvoiceFragment extends Fragment  {

    ViewPager viewPager;
    int nextFragment;

    TextView tv_amount,tv_discount,tv_netamount,tv_mode,tv_continue;
    Global global;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.invoicedetail, container, false);

        global = (Global) getActivity().getApplicationContext();

        tv_amount = (TextView) v.findViewById(R.id.amount);
        tv_discount = (TextView) v.findViewById(R.id.discount);
        tv_netamount = (TextView) v.findViewById(R.id.netamount);
        tv_mode = (TextView) v.findViewById(R.id.mode) ;
        tv_continue = (TextView) v.findViewById(R.id.tv_continue);


        tv_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(global.isPendingPayment())
                {
                    ClientHomeActivity.h.sendEmptyMessage(0);
                }
                else if (global.isPlanRenew())
                {
                    ViewPlanActivity.h.sendEmptyMessage(0);
                    ClientHomeActivity.h.sendEmptyMessage(0);
                }
                else
                {
                    CartActivity.h.sendEmptyMessage(0);
                    ClientHomeActivity.h.sendEmptyMessage(0);
                }

                getActivity().startActivity(new Intent(getActivity(),ClientHomeActivity.class));
                getActivity().finish();

            }
        });

        Log.i("Invoice","Called");

        Log.i("Payment","Amount "+ global.getPayment_amount());
        Log.i("Amount","toPay "+global.getAmount_to_pay());
        Log.i("Discount","Amount "+global.getDiscount());
        Log.i("Payment","Mode "+global.getPayment_mode());

        tv_amount.setText(global.getPayment_amount());
        tv_discount.setText(global.getDiscount());
        tv_netamount.setText(global.getAmount_to_pay());
        tv_mode.setText("By "+global.getPayment_mode());


        return v;
    }


    public static InvoiceFragment newInstance(String text) {

        InvoiceFragment f = new InvoiceFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }


}