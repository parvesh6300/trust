package dcube.com.trust;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.weiwangcn.betterspinner.library.BetterSpinner;

public class AddClientActivity extends Activity {

    Context context;

    String[] spinnerArray = {"10-19","20-29","30-39","40+"};
    Spinner age_group;
    TextView addclient;


    BetterSpinner area;

    String[] ITEMS = {"Select Branch", "Arusha", "Dodoma", "Mwanza", "Dar Es Salaam", "Morogoro", "Mbeya", "Zanzibar"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);

        context = this;
        age_group = (Spinner) findViewById(R.id.age_group);
        addclient = (TextView) findViewById(R.id.addclient);

        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
        age_group.setAdapter(spinnerArrayAdapter);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, ITEMS);
        area = (BetterSpinner) findViewById(R.id.area);
        area.setAdapter(adapter);


        addclient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CustomDialogClass cdd=new CustomDialogClass(AddClientActivity.this);
                cdd.show();
            }
        });
    }

    public class CustomDialogClass extends Dialog{

        public Activity c;
        public Button ok;

        public CustomDialogClass(Activity a) {
            super(a);
            // TODO Auto-generated constructor stub
            this.c = a;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            requestWindowFeature(Window.FEATURE_NO_TITLE);

            setContentView(R.layout.custom_dialog);

            ok = (Button) findViewById(R.id.btn_yes);

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    finish();
                }
            });
        }
    }
}



