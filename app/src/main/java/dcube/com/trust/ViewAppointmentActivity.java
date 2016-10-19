package dcube.com.trust;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import dcube.com.trust.utils.FollowupListAdapter;

public class ViewAppointmentActivity extends Activity {

    ListView followUpList;
    FollowupListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_appointment);

        followUpList = (ListView) findViewById(R.id.followuplist);
        adapter = new FollowupListAdapter(this);

        followUpList.setAdapter(adapter);

    }
}
