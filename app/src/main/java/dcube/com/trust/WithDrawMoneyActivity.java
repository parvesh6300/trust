package dcube.com.trust;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class WithDrawMoneyActivity extends Activity {

    RadioGroup radio_group;

    RadioButton radio_other;

    EditText ed_other;

    TextView tv_withdraw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_draw_money);


        radio_group=(RadioGroup)findViewById(R.id.radio_group);
        radio_other=(RadioButton)findViewById(R.id.radio_other);

        ed_other=(EditText)findViewById(R.id.ed_other);

        tv_withdraw=(TextView)findViewById(R.id.tv_withdraw);


        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radio_other.isChecked())
                {
                    ed_other.setVisibility(View.VISIBLE);
                }
                else
                {
                    ed_other.setVisibility(View.INVISIBLE);
                }


            }
        });


        tv_withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ConfirmDialog cdd = new ConfirmDialog(WithDrawMoneyActivity.this);
                cdd.show();

            }
        });
    }

    public class ConfirmDialog extends Dialog{

        public Activity c;

        public TextView cancel;
        public TextView confirm;

        public ConfirmDialog(Context context) {
            super(context);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.withdraw_dialog);

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
