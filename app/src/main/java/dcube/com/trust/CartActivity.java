package dcube.com.trust;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import dcube.com.trust.utils.CartAdapter;

public class CartActivity extends Activity {

    ListView lv_cart_items;
    TextView tv_check_out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        lv_cart_items = (ListView) findViewById(R.id.lv_cart_items);

        CartAdapter adapter = new CartAdapter(CartActivity.this);
        lv_cart_items.setAdapter(adapter);

        tv_check_out = (TextView) findViewById(R.id.tv_check_out);

        tv_check_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialogClass dialog = new CustomDialogClass(CartActivity.this);
                dialog.show();
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

            setContentView(R.layout.cart_confirm_dialog);

            confirm = (TextView) findViewById(R.id.tv_yes);
            cancel = (TextView) findViewById(R.id.tv_no);

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
