package dcube.com.trust;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import dcube.com.trust.utils.Global;

public class GenerateInvoiceActivity extends FragmentActivity {

    TextView generate;
    ViewPager pager;
    Global global;

    public static GenerateInvoiceActivity mInstance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_invoice);

        mInstance = this;

        global = (Global) getApplicationContext();
        generate = (TextView) findViewById(R.id.generate);

        pager = (ViewPager) findViewById(R.id.viewPager);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        pager.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                return true;
            }
        });

    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {

            if(global.isPendingPayment() || global.isPlanRenew() )
            {
                return InvoiceFragment.newInstance("ThirdFragment, Instance 1");
            }
            else
            {
                switch(pos) {

                    case 0: return PaymentDetailFragment.newInstance("FirstFragment, Instance 1");
                    case 1: return TestFragment.newInstance("SecondFragment, Instance 1");
                    case 2: return InvoiceFragment.newInstance("ThirdFragment, Instance 1");
                    default: return PaymentDetailFragment.newInstance("FourthFragment, Default");
                }
            }

        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    @Override
    public void onBackPressed() {

    }
}
