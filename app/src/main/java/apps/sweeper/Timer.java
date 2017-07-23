package apps.sweeper;

import android.os.Handler;
import android.widget.TextView;

import java.util.Locale;

/**
 * Created by Jordan on 2017-07-22.
 */

public class Timer {

    protected int theTime;
    TextView tView;
    public Timer (TextView t) {
        theTime = 0;
        tView = t;
        t.setText("0");
    }

    public int getTheTime() { return theTime; }
    public void setTheTime (int n) { theTime = n; }

    Handler h = new Handler();
    Runnable runner = new Runnable() {
        public void run() {
//            System.out.println ("gameState = " + Btn.gameState);
            if (Btn.gameState == 1) {
                tView.setText(String.format(Locale.getDefault(), "%d", ++theTime));
                h.postDelayed (runner, 1000);
            }
        }
    };


    public void stopTimer() {
        h.removeCallbacks (runner, null);
    }
    public void startTimer() {
        h.postDelayed (runner, 1000);
    }

}
