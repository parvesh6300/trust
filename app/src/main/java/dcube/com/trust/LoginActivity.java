package dcube.com.trust;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LoginActivity extends Activity {

    RelativeLayout nurse;
    RelativeLayout finance;
    ImageView nurse_radio;
    ImageView finance_radio;
    TextView nurse_text;
    TextView finance_text;

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

        nurse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nurse_radio.setImageResource(R.drawable.radioselected);
                nurse_text.setTextColor(getResources().getColor(R.color.textColor));

                finance_radio.setImageResource(R.drawable.radiounselected);
                finance_text.setTextColor(getResources().getColor(R.color.greyed_out));

            }
        });

        finance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finance_radio.setImageResource(R.drawable.radioselected);
                finance_text.setTextColor(getResources().getColor(R.color.textColor));

                nurse_radio.setImageResource(R.drawable.radiounselected);
                nurse_text.setTextColor(getResources().getColor(R.color.greyed_out));

            }
        });

    }
}
