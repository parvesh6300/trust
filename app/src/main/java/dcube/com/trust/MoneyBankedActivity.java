package dcube.com.trust;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import dcube.com.trust.utils.DepositAdapter;

public class MoneyBankedActivity extends Activity {


    ListView list_deposit;

    DepositAdapter depositAdapter;

    ArrayList<String> al_deposit_detail= new ArrayList<>();
    ArrayList<String> al_deposit_amount= new ArrayList<>();
    ArrayList<String> al_date= new ArrayList<>();

    TextView tv_total_amount,tv_deposit;

    EditText ed_deposit_amount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_banked);


        list_deposit=(ListView)findViewById(R.id.list_deposit);
        ed_deposit_amount=(EditText)findViewById(R.id.ed_deposit_amount);
        tv_total_amount=(TextView)findViewById(R.id.tv_total_amount);
        tv_deposit=(TextView)findViewById(R.id.tv_deposit);

        depositAdapter= new DepositAdapter(this,al_date,al_deposit_detail,al_deposit_amount);

        list_deposit.setAdapter(depositAdapter);





    }





}
