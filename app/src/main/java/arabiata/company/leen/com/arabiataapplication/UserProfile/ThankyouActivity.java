package arabiata.company.leen.com.arabiataapplication.UserProfile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

import arabiata.company.leen.com.arabiataapplication.MainActivity;
import arabiata.company.leen.com.arabiataapplication.R;

public class ThankyouActivity extends AppCompatActivity {

    private Timer timer;
    // private ProgressBar progressBar;
    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thankyou);


        final long period = 30;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //this repeats every 100 ms
                if (i < 100) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //  textView.setText(String.valueOf(i)+"%");
                        }
                    });
                    //          progressBar.setProgress(i);
                    i++;
                } else {
                    //closing the timer
                    timer.cancel();
                    Intent intent = new Intent(ThankyouActivity.this, MainActivity
                            .class);
                    startActivity(intent);
                    // close this activity
                    finish();
                }
            }
        }, 0, period);


    }
}
