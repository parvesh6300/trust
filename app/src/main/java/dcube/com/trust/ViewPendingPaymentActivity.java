package dcube.com.trust;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import dcube.com.trust.utils.PendingPaymentAdapter;

public class ViewPendingPaymentActivity extends Activity {


    TextView tv_pay;
    ListView lv_payment_details;
    PendingPaymentAdapter paymentAdapter;

    ArrayList<String> al_product_name= new ArrayList<>();
    ArrayList<String> al_product_cost= new ArrayList<>();
    ArrayList<String> al_date= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_payment);

        lv_payment_details=(ListView)findViewById(R.id.lv_payment_details);
        tv_pay=(TextView)findViewById(R.id.tv_pay);

        paymentAdapter=new PendingPaymentAdapter(this,al_date,al_product_name,al_product_cost);
        lv_payment_details.setAdapter(paymentAdapter);


        tv_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(ViewPendingPaymentActivity.this,GenerateInvoiceActivity.class));
            }
        });


    }





}
