package dcube.com.trust;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import dcube.com.trust.utils.FormatString;
import dcube.com.trust.utils.Global;

public class GuestReceiptActivity extends Activity {

    TextView tv_amount,tv_discount,tv_netamount,tv_mode,tv_continue;
    Global global;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_receipt);

        global = (Global) getApplicationContext();

        tv_amount = (TextView) findViewById(R.id.amount);
        tv_discount = (TextView) findViewById(R.id.discount);
        tv_netamount = (TextView) findViewById(R.id.netamount);
        tv_mode = (TextView) findViewById(R.id.mode) ;
        tv_continue = (TextView) findViewById(R.id.tv_continue);


        tv_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GuestProductActivity.h.sendEmptyMessage(0);

                startActivity(new Intent(GuestReceiptActivity.this,NurseHomeActivity.class));
                finish();

            }
        });

        Log.e("Invoice","Called");

        Log.e("Payment","Amount "+ global.getPayment_amount());
        Log.e("Amount","toPay "+global.getAmount_to_pay());
        Log.e("Discount","Amount "+global.getDiscount());
        Log.e("Payment","Mode "+global.getPayment_mode());

        String formatted_payment_amnt = global.getPayment_amount();

        formatted_payment_amnt = FormatString.getCommaInString(formatted_payment_amnt);

        tv_amount.setText(formatted_payment_amnt);       // global.getPayment_amount()

        tv_discount.setText("0");

        tv_netamount.setText(formatted_payment_amnt);    // global.getPayment_amount()
//        tv_mode.setText("By "+global.getPayment_mode());

    }





}
