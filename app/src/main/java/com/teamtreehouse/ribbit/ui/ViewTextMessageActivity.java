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

public class ViewTextMessageActivity extends Activity {
    TextView mTimerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_message);

        setupActionBar();

        mTimerTextView = (TextView) findViewById(R.id.timerTextView);
        TextView messageTextView = (TextView) findViewById(R.id.messageTextView);

        String message = getIntent().getStringExtra(InboxFragment.TEXT_MESSAGE);
        messageTextView.setText(message);

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

