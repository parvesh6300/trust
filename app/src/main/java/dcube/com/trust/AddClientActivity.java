package dcube.com.trust;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class AddClientActivity extends Activity {

    String[] spinnerArray = {"10-19","20-29","30-39","40+"};
    Spinner age_group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);

        age_group = (Spinner) findViewById(R.id.age_group);

        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item, spinnerArray);
        age_group.setAdapter(spinnerArrayAdapter);
    }
}
