package dcube.com.trust;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import WebServicesHandler.GlobalConstants;
import WebServicesHandler.WebServices;
import dcube.com.trust.utils.Global;
import okhttp3.OkHttpClient;
import pl.droidsonroids.gif.GifTextView;

/**
 * Created by yadi on 14/10/16.
 */
public class PaymentDetailFragment extends Fragment {

    ViewPager viewPager;
    int nextFragment;
    RadioGroup radio_group_payment,radio_group_payment_mode;
    RadioButton radio_mpesa,radio_cash,radio_insurance;
    RadioButton radio_partial,radio_partial_grant,radio_full;

    GifTextView gif_loader;

    Context context = getActivity();

    String str_client_id;
    WebServices ws;
    TextView tv_cancel,generate;

    Global global;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.paymentdetail, container, false);

        viewPager = (ViewPager) GenerateInvoiceActivity.mInstance.findViewById(R.id.viewPager);

        global = (Global) getActivity().getApplicationContext();

        gif_loader = (GifTextView) v.findViewById(R.id.gif_loader);

        nextFragment = viewPager.getCurrentItem() + 1;

        tv_cancel =(TextView)v.findViewById(R.id.tv_cancel);

        generate = (TextView) v.findViewById(R.id.generate);

        radio_group_payment = (RadioGroup) v.findViewById(R.id.radio_group_payment);
        radio_group_payment_mode = (RadioGroup) v.findViewById(R.id.radio_group_payment_mode);

        radio_mpesa = (RadioButton) v.findViewById(R.id.radio_mpesa);
        radio_cash = (RadioButton) v.findViewById(R.id.radio_cash);
        radio_insurance = (RadioButton) v.findViewById(R.id.radio_insurance);

        radio_partial = (RadioButton) v.findViewById(R.id.radio_partial);
        radio_partial_grant = (RadioButton) v.findViewById(R.id.radio_partial_grant);
        radio_full = (RadioButton) v.findViewById(R.id.radio_full);


        generate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                new PaymentAsyncTask().execute();

                viewPager.setCurrentItem(nextFragment);
            }

        });



        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getActivity().finish();
            }
        });

        str_client_id = global.getAl_src_client_details().get(global.getSelected_client()).
                get(GlobalConstants.SRC_CLIENT_ID);


        return v;
    }

    public static PaymentDetailFragment newInstance(String text) {

        PaymentDetailFragment f = new PaymentDetailFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }





    public class PaymentAsyncTask extends AsyncTask<String, String, String> {

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

                al_str_key.add(GlobalConstants.PAYMENT_CLIENT_ID);
                al_str_value.add(str_client_id);

                al_str_key.add(GlobalConstants.PAYMENT_MODE);
                al_str_value.add(str_client_id);

                al_str_key.add(GlobalConstants.PAYMENT_TYPE);
                al_str_value.add(str_client_id);

                al_str_key.add(GlobalConstants.PAYMENT_AMOUNT);
                al_str_value.add(str_client_id);

                al_str_key.add(GlobalConstants.ACTION);
                al_str_value.add("get_cart_by_client_id");

                message = ws.GetCartService(context, al_str_key, al_str_value);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            gif_loader.setVisibility(View.GONE);

            if (!message.equalsIgnoreCase("true"))
            {
                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }
            else {

            }

        }

    }




}