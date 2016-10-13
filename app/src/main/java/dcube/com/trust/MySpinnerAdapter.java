package dcube.com.trust;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by yadi on 13/10/16.
 */

class MySpinnerAdapter extends ArrayAdapter<String> {
    // Initialise custom font, for example:
    Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/myriad_thin.ttf");

    Context context;

    public MySpinnerAdapter(Context context, int resource, String[] items) {
        super(context, resource, items);
        this.context = context;
    }

    // Affects default (closed) state of the spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView view = (TextView) super.getView(position, convertView, parent);
        view.setTypeface(font);
        view.setTextSize(20);
        view.setTextColor(getContext().getResources().getColor(R.color.textColor));

        return view;
    }

    // Affects opened state of the spinner
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView view = (TextView) super.getDropDownView(position, convertView, parent);
        view.setTypeface(font);

        if (position == 0)
        {
            view.setBackgroundColor(context.getResources().getColor(R.color.textColor));
            view.setTextColor(context.getResources().getColor(R.color.white));
        }

        return view;
    }
}
