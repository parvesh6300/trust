package dcube.com.trust;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import dcube.com.trust.utils.MoneyBankedAdapter;

public class MoneyBankedActivity extends Activity {


    ListView lv_money_banked;

    MoneyBankedAdapter moneyBankedAdapter;

    ArrayList<String> al_money_detail= new ArrayList<>();
    ArrayList<String> al_money_amount= new ArrayList<>();
    ArrayList<String> al_date= new ArrayList<>();

    TextView tv_total_amount,tv_deposit;

    EditText ed_deposit_amount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_money_banked);


        lv_money_banked=(ListView)findViewById(R.id.lv_money_banked);
        ed_deposit_amount=(EditText)findViewById(R.id.ed_deposit_amount);
        tv_total_amount=(TextView)findViewById(R.id.tv_total_amount);
        tv_deposit=(TextView)findViewById(R.id.tv_deposit);


        moneyBankedAdapter= new MoneyBankedAdapter(this,al_date,al_money_detail,al_money_amount);
        lv_money_banked.setAdapter(moneyBankedAdapter);
//
//        View header = getLayoutInflater().inflate(R.layout.moneybankedlistheader, lv_money_banked, false);
//        lv_money_banked.addHeaderView(header, null, false);

        tv_deposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CustomDialogClass cdd = new CustomDialogClass(MoneyBankedActivity.this);
                cdd.show();
            }
        });


    }


    public class CustomDialogClass extends Dialog {

        public Activity c;

        public TextView cancel;
        public TextView confirm;

        public CustomDialogClass(Activity a) {
            super(a);
            // TODO Auto-generated constructor stub
            this.c = a;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            requestWindowFeature(Window.FEATURE_NO_TITLE);

            setContentView(R.layout.money_banked_dialog);

            confirm = (TextView) findViewById(R.id.confirm);
            cancel = (TextView) findViewById(R.id.cancel);

            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    dismiss();
                    finish();

                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    dismiss();
                }
            });

        }
    }




}


