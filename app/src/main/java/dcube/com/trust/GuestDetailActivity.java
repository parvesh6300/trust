package dcube.com.trust;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class GuestDetailActivity extends Activity {

    Context context = GuestDetailActivity.this;

    RadioButton radio_retail,radio_institution,radio_name;
    EditText ed_retail_client,ed_institution,ed_name;
    RadioGroup radio_group_client;

    TextView buy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_guest_detail);

        buy = (TextView) findViewById(R.id.buy);

        radio_group_client = (RadioGroup) findViewById(R.id.radio_group_client);

        radio_retail = (RadioButton) findViewById(R.id.radio_retail);
        radio_institution = (RadioButton) findViewById(R.id.radio_institution);
        radio_name = (RadioButton) findViewById(R.id.radio_name);

        ed_retail_client = (EditText) findViewById(R.id.ed_retail_client);
        ed_institution = (EditText) findViewById(R.id.ed_institution);
        ed_name = (EditText) findViewById(R.id.ed_name);


        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(GuestDetailActivity.this,GuestReceiptActivity.class));
                finish();

            }
        });



        radio_group_client.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radio_retail.isChecked())
                {
                    ed_retail_client.setVisibility(View.VISIBLE);
                }
                else
                {
                    ed_retail_client.setVisibility(View.INVISIBLE);
                }

                if (radio_institution.isChecked())
                {
                    ed_institution.setVisibility(View.VISIBLE);
                }
                else
                {
                    ed_institution.setVisibility(View.INVISIBLE);
                }

                if (radio_name.isChecked())
                {
                    ed_name.setVisibility(View.VISIBLE);
                }
                else
                {
                    ed_name.setVisibility(View.INVISIBLE);
                }


            }
        });





    }
}
