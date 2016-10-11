package dcube.com.trust;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LoginActivity extends Activity implements View.OnClickListener{

    RelativeLayout nurse;
    RelativeLayout finance;
    ImageView nurse_radio;
    ImageView finance_radio;
    TextView nurse_text;
    TextView finance_text;
    TextView forgot;
    TextView signin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        nurse = (RelativeLayout) findViewById(R.id.nurse);
        finance = (RelativeLayout) findViewById(R.id.finance);

        nurse_radio = (ImageView) findViewById(R.id.nurse_radio);
        finance_radio = (ImageView) findViewById(R.id.finance_radio);
        nurse_text = (TextView) findViewById(R.id.nurse_text);
        finance_text = (TextView) findViewById(R.id.finance_text);
        forgot = (TextView) findViewById(R.id.forgot);
        signin = (TextView) findViewById(R.id.signin);
        forgot.setPaintFlags(forgot.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);


        nurse.setOnClickListener(this);
        finance.setOnClickListener(this);
        signin.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch(view.getId())
        {
            case R.id.nurse:
            {
                nurse_radio.setImageResource(R.drawable.radioselected);
                nurse_text.setTextColor(getResources().getColor(R.color.textColor));
                finance_radio.setImageResource(R.drawable.radiounselected);
                finance_text.setTextColor(getResources().getColor(R.color.greyed_out));

                break;
            }

            case R.id.finance:
            {

                finance_radio.setImageResource(R.drawable.radioselected);
                finance_text.setTextColor(getResources().getColor(R.color.textColor));
                nurse_radio.setImageResource(R.drawable.radiounselected);
                nurse_text.setTextColor(getResources().getColor(R.color.greyed_out));

                break;
            }

            case R.id.signin:
            {
                Intent i = new Intent(LoginActivity.this,FinanceHomeActivity.class);
                startActivity(i);
            }
        }
    }
}
