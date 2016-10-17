package dcube.com.trust;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Sagar on 14/10/16.
 */
public class QuantityDialog extends Dialog {


    TextView textView,tv_quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        textView= (TextView)findViewById(R.id.tv_plus);
        tv_quantity=(TextView)findViewById(R.id.tv_quantity);



        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int i =0;

                i++;

                tv_quantity.setText(""+i);

            }
        });


    }

    public QuantityDialog(Context context, int themeResId) {
        super(context, themeResId);


        setContentView(themeResId);
    }






}
