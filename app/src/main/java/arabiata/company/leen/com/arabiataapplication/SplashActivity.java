package arabiata.company.leen.com.arabiataapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

import arabiata.company.leen.com.arabiataapplication.Network.ApiClient;

public class SplashActivity extends AppCompatActivity {
    private Timer timer;
    // private ProgressBar progressBar;
    private int i = 0;
    private ImageView imglogo;
    ApiClient apiClient;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_spalsh);

        apiClient.setBASE_URL("https://arabiata-app.com/api/en/");
         String phoneLang = getResources().getString(R.string.lang);
        Log.i("TAG", "language: "+ phoneLang);
        startService(new Intent(this, BatteryService.class));

        switch (phoneLang) {
            case "eng":
                apiClient.setBASE_URL("https://arabiata-app.com/api/en/");
                break;
            case "ar":
                apiClient.setBASE_URL("https://arabiata-app.com/api/ar/");
                break;
            default:
                apiClient.setBASE_URL("https://arabiata-app.com/api/en/");
        }


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
                        Intent intent = new Intent(SplashActivity.this, MainActivity
                                .class);
                        startActivity(intent);
                        // close this activity
                        finish();
                }
            }
        }, 0, period);
    }

}