package apps.sweeper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Locale;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class StatisticsScreen extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private View mControlsView;

    private boolean mVisible;

    @Override
    protected void onResume() { //(Bundle savedInstanceState) {
        super.onResume(); //(savedInstanceState);

        setContentView(R.layout.activity_statistics_screen);

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
//        mContentView = findViewById(R.id.fullscreen_content);

        Button reset = (Button) findViewById(R.id.resetButton);
        Button rtn = (Button) findViewById(R.id.mainScreen);
        SharedPreferences shpr = getSharedPreferences("Jordan-Sweeper", Context.MODE_PRIVATE);

        TextView gamesWon = (TextView) findViewById(R.id.GamesWonValue);
        TextView gamesPlayed = (TextView) findViewById(R.id.GamesPlayedValue);
        TextView winPerc = (TextView) findViewById(R.id.WinPercentValue);
        TextView fastest = (TextView) findViewById(R.id.FastestValue);
        int winVal = shpr.getInt("Games_Won", 0);
        int playVal = shpr.getInt("Games_Played", 0);
        gamesWon.setText(String.format(Locale.getDefault(), "%d", winVal));
        gamesPlayed.setText(String.format(Locale.getDefault(), "%d", playVal));

        winPerc.setText(String.format(Locale.getDefault(), "%.2f", 100 * (float)winVal / (float)Math.max(playVal, 1)));

        int fastTime = shpr.getInt("Fastest_Win", 0);
        if (fastTime >= 99) {
            fastest.setText("N/A");
        } else {
            fastest.setText(String.format(Locale.getDefault(), "%d", fastTime));
        }

        reset.setOnLongClickListener (new View.OnLongClickListener() {
            public boolean onLongClick (View view) {
                SharedPreferences sp = getSharedPreferences("Jordan-Sweeper", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = sp.edit();

                edit.putInt("Games_Won", 0);
                edit.putInt("Games_Played", 0);
                edit.putInt("Fastest_Win", 99);
                edit.apply();
                return true;
            }
        });

        rtn.setOnClickListener (new View.OnClickListener() {
            public void onClick (View view) {
                rtnMainScreen (view);
            }
        });
    }

    public void rtnMainScreen (View view) {
        Intent intent = new Intent(this, MainScreen.class);
        startActivity(intent);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

}
