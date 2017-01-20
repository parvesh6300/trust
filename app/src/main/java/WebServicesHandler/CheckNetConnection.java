package WebServicesHandler;

import android.content.Context;

import java.io.IOException;

/**
 * Created by Sagar on 03/01/17.
 */
public class CheckNetConnection
{
    Context mContext;

    public CheckNetConnection(Context mContext) {
        this.mContext = mContext;
    }


    public boolean isNetConnected() {

        Runtime runtime = Runtime.getRuntime();

        try {

            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);

        } catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }

        return false;
    }





}
