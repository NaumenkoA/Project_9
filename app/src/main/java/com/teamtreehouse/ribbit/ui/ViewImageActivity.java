package com.teamtreehouse.ribbit.ui;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.teamtreehouse.ribbit.R;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ViewImageActivity extends Activity {
    TextView mTimerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);
        // Show the Up button in the action bar.
        setupActionBar();

        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        mTimerTextView = (TextView) findViewById(R.id.timerTextView);

        Uri imageUri = getIntent().getData();

        Picasso.with(this).load(imageUri.toString()).into(imageView);

        startNewCountdown (10*1000);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                finish();
            }
        }, 10 * 1000);
    }

    private void startNewCountdown(final int countdownTimeInMillis) {
           new CountDownTimer(countdownTimeInMillis + 1000, 1000) {

            public void onTick(long millisUntilFinished) {
                mTimerTextView.setText(Long.toString(millisUntilFinished / 1000));
            }

            public void onFinish() {
                mTimerTextView.setText (getString(R.string.zero));
                }
        }.start();
                }



    /**
     * Set up the {@link android.app.ActionBar}.
     */
    private void setupActionBar() {

        getActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. Use NavUtils to allow users
                // to navigate up one level in the application structure. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back
                //
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
