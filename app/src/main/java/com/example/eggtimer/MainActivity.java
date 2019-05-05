package com.example.eggtimer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * @author Pedro Marques
 */
public class MainActivity extends AppCompatActivity {

    SeekBar seekBar;
    TextView time;
    Button button;
    CountDownTimer countDownTimer;
    MediaPlayer mediaPlayer;

    boolean counterActive = false;

    public void changeTimerText(int seconds) {

        int min = seconds / 60;
        int sec = seconds - (min * 60);

        time.setText(min + ":" + sec);

        if (sec < 9)
            time.setText(min + ":0" + sec);
    }

    public void resertTimer() {

        counterActive = false;
        seekBar.setEnabled(true);
        seekBar.setProgress(30);
        time.setText("0:30");
        button.setText("RESTART");
    }

    public void startTimer(final View view) {

        if (counterActive) {

            resertTimer();
            countDownTimer.cancel();

        } else {

            counterActive = true;
            seekBar.setEnabled(false);
            button.setText("STOP");

            countDownTimer = new CountDownTimer(seekBar.getProgress() * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                    changeTimerText((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {

                    mediaPlayer.start();
                    resertTimer();
                }
            }.start();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = findViewById(R.id.seekBar);
        time = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.moo);

        seekBar.setMax(600);
        seekBar.setProgress(30);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                changeTimerText(progress);
                button.setText("START");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
