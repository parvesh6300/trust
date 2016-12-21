package dcube.com.trust;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dcube.com.trust.utils.Global;

/**
 * Created by yadi on 14/10/16.
 */
public class InvoiceFragment extends Fragment {

    ViewPager viewPager;
    int nextFragment;

    TextView tv_amount,tv_discount,tv_netamount;
    Global global;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.invoicedetail, container, false);

        global = (Global) getActivity().getApplicationContext();

        tv_amount = (TextView) v.findViewById(R.id.amount);
        tv_discount = (TextView) v.findViewById(R.id.discount);
        tv_netamount = (TextView) v.findViewById(R.id.netamount);


        /*viewPager = (ViewPager) GenerateInvoiceActivity.mInstance.findViewById(R.id.viewPager);

        nextFragment = viewPager.getCurrentItem() + 1;

        TextView generate = (TextView) v.findViewById(R.id.generate);
        generate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                viewPager.setCurrentItem(nextFragment);
                }
        });
        */





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