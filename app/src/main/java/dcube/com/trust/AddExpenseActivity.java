package dcube.com.trust;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import dcube.com.trust.utils.DepositAdapter;

public class AddExpenseActivity extends Activity {


    ListView list_expense;

    DepositAdapter depositAdapter;

    ArrayList<String> al_expense_amount= new ArrayList<>();
    ArrayList<String> al_expense_reason= new ArrayList<>();
    ArrayList<String> al_date= new ArrayList<>();

    TextView tv_total_amount,tv_deposit;

    EditText ed_expense_amount,ed_reason;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        list_expense= (ListView)findViewById(R.id.list_expense);

        ed_expense_amount=(EditText)findViewById(R.id.ed_expense_amount);
        ed_reason=(EditText)findViewById(R.id.ed_reason);
        tv_total_amount=(TextView)findViewById(R.id.tv_total_amount);
        tv_deposit=(TextView)findViewById(R.id.tv_deposit);

        depositAdapter= new DepositAdapter(this,al_date,al_expense_amount,al_expense_reason);

        list_expense.setAdapter(depositAdapter);
    }
}
