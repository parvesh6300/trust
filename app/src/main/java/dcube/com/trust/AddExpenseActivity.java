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

import dcube.com.trust.utils.ExpenseAdapter;

public class AddExpenseActivity extends Activity {

    ListView list_expense;

    ExpenseAdapter expenseAdapter;

    ArrayList<String> al_expense_amount= new ArrayList<>();
    ArrayList<String> al_expense_reason= new ArrayList<>();
    ArrayList<String> al_date= new ArrayList<>();

    TextView tv_total_amount,tv_submit;

    EditText ed_expense_amount,ed_reason;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        list_expense= (ListView)findViewById(R.id.list_expense);

        ed_expense_amount=(EditText)findViewById(R.id.ed_expense_amount);
        ed_reason=(EditText)findViewById(R.id.ed_reason);
        tv_total_amount=(TextView)findViewById(R.id.tv_total_amount);
        tv_submit=(TextView)findViewById(R.id.tv_submit);

        expenseAdapter= new ExpenseAdapter(this,al_date,al_expense_amount,al_expense_reason);

        list_expense.setAdapter(expenseAdapter);


        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CustomDialogClass cdd = new CustomDialogClass(AddExpenseActivity.this);
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

            setContentView(R.layout.addexpense_dialog);

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
