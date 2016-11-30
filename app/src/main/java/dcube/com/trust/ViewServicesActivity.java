package dcube.com.trust;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewServicesActivity extends Activity {

    ListView lv_plan;
    ServiceAdapter adapter;

    ArrayList<String> al_plan_name = new ArrayList<>();
    ArrayList<String> al_date= new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_services);

        lv_plan=(ListView)findViewById(R.id.lv_plan);

        adapter= new ServiceAdapter(this,al_date,al_plan_name);
        lv_plan.setAdapter(adapter);

        lv_plan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                CustomDialogClass cdd= new CustomDialogClass(ViewServicesActivity.this);
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

            setContentView(R.layout.renew_service_dialog);

            confirm = (TextView) findViewById(R.id.confirm);
            cancel = (TextView) findViewById(R.id.cancel);

            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    dismiss();
                    //   finish();


                    final Dialog dialog = new Dialog(c);
                    dialog.setContentView(R.layout.custom_dialog);
                    dialog.show();

                    TextView text= (TextView)dialog.findViewById(R.id.text);
                    Button btn_yes=(Button)dialog.findViewById(R.id.btn_yes);

                    text.setText("Renewed Successfully");

                    btn_yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            dialog.cancel();
                            finish();
                        }
                    });
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
