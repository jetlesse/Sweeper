package apps.sweeper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import android.widget.TextView;
import android.widget.Toast;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class MainScreen extends AppCompatActivity {
    private View mContentView;
    private View mControlsView;


    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_main_screen);

        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.gridview);

        TextView tView = (TextView) findViewById(R.id.gameInfo);
        tView.setText("");
        Timer timer = new Timer((TextView) findViewById(R.id.clock));
        Btn.emptyObjects();
        Btn.timer = timer;
        Btn.tv = tView;
        Btn.sp = getSharedPreferences("Jordan-Sweeper", Context.MODE_PRIVATE);

        GridView gridview = (GridView) mContentView;
        gridview.setStretchMode(GridView.NO_STRETCH);
        gridview.setNumColumns(9);
        gridview.setColumnWidth(80);
        gridview.setAdapter(new ImageAdapter(this));

        Button stats = (Button) findViewById(R.id.stats);
        stats.setOnClickListener (new View.OnClickListener() {
            public void onClick (View view) {
                // change to the statistics activity, load statistics from shared preferences file.
                if (Btn.gameState == 0 || Btn.gameState == 2) {
                    Btn.gameState = 0;
                    statScreen();
                }
            }
        });

    }

    public void statScreen() {
        Intent intent = new Intent(this, StatisticsScreen.class);
        startActivity(intent);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }

}
