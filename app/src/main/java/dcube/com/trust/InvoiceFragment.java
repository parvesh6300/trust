package dcube.com.trust;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

        /*viewPager = (ViewPager) GenerateInvoiceActivity.mInstance.findViewById(R.id.viewPager);

        nextFragment = viewPager.getCurrentItem() + 1;

        TextView generate = (TextView) v.findViewById(R.id.generate);
        generate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                viewPager.setCurrentItem(nextFragment);
                }
        });
        */


        String myTag = getTag();

        ((GenerateInvoiceActivity)getActivity()).setTabFragmentB(myTag);

        tv_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getActivity(),ClientHomeActivity.class));
            }
        });

        Log.e("Invoice","Called");




        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.e("Payment","Amount "+ global.getPayment_amount());
        Log.e("Amount","toPay "+global.getAmount_to_pay());
        Log.e("Discount","Amount "+global.getDiscount());
        Log.e("Payment","Mode "+global.getPayment_mode());

        tv_amount.setText(global.getPayment_amount());
        tv_discount.setText(global.getDiscount());
        tv_netamount.setText(global.getAmount_to_pay());
        tv_mode.setText("By "+global.getPayment_mode());

    }

    public static InvoiceFragment newInstance(String text) {

        InvoiceFragment f = new InvoiceFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }


}