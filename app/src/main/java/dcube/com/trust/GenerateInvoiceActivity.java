package dcube.com.trust;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

public class GenerateInvoiceActivity extends FragmentActivity {

    TextView generate;
    ViewPager pager;

    public static GenerateInvoiceActivity mInstance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_invoice);

        mInstance = this;
        generate = (TextView) findViewById(R.id.generate);

        pager = (ViewPager) findViewById(R.id.viewPager);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {

            switch(pos) {

                case 0: return PaymentDetailFragment.newInstance("FirstFragment, Instance 1");
                case 1: return InvoiceFragment.newInstance("SecondFragment, Instance 1");
                default: return PaymentDetailFragment.newInstance("ThirdFragment, Default");
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
