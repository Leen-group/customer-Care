package arabiata.company.leen.com.arabiataapplication;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.BatteryManager;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

import arabiata.company.leen.com.arabiataapplication.Network.ApiClient;
import arabiata.company.leen.com.arabiataapplication.Network.ApiInterface;
import arabiata.company.leen.com.arabiataapplication.UserProfile.UserProfileModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BatteryService extends Service {
    Timer timer;
    private int i = 0;
    SharedPreferences prefs;
    ApiClient apiClient;

//

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
          Toast.makeText(this, "Service created!", Toast.LENGTH_LONG).show();
        loop();

    }

    @Override
    public void onDestroy() {
        //  Toast.makeText(this, "Service stopped", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStart(Intent intent, int startid) {
        //    Toast.makeText(this, "Service started by user.", Toast.LENGTH_LONG).show();
    }


    public void timer() {

        new CountDownTimer(300000, 1000) {
            public void onTick(long millisUntilFinished) {
            }
            public void onFinish()
            {
                loop();
            }

        }.start();


    }

    public void loop() {
        sendBatteryStatusAPI();
        Log.i("TAG", "onStartCommand: "+"awellooop");

       //Toast.makeText(BatteryService.this, "test battery", Toast.LENGTH_SHORT).show();
        timer();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
      /*  loop();
        Log.i("TAG", "onStartCommand: "+"ar2asaaaaaaaaa");
        stopSelf();
*/
        return START_STICKY;
    }

    public void sendBatteryStatusAPI() {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = registerReceiver(null, ifilter);
        final int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int deviceStatus = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS,-1);
        String device_status="";

        if(deviceStatus == BatteryManager.BATTERY_STATUS_CHARGING){

            device_status="Charging";
         //   Toast.makeText(getApplicationContext(), ""+device_status, Toast.LENGTH_SHORT).show();

        }

        if (deviceStatus == BatteryManager.BATTERY_STATUS_DISCHARGING){
            device_status="Not Charging";

       //     Toast.makeText(getApplicationContext(), ""+device_status, Toast.LENGTH_SHORT).show();

        }

        prefs = getSharedPreferences("MyPREFERENCES", MODE_PRIVATE);
        apiClient.setBASE_URL("https://arabiata-app.com/api/en/");
        String phoneLang = getResources().getString(R.string.lang);
        Log.i("TAG", "language: "+ phoneLang);


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

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
//header key
        Map<String, String> header = new HashMap<>();
        String x = prefs.getString("branch_token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC8xOTIuMTY4LjEuMTA4OjgwMDBcL2FwaVwvZW5cL2xvZ2luIiwiaWF0IjoxNTQ5NTM4NDU4LCJuYmYiOjE1NDk1Mzg0NTgsImp0aSI6InB0VjVLbFFXekhXMVlNdGciLCJzdWIiOjMsInBydiI6Ijg3ZTBhZjFlZjlmZDE1ODEyZmRlYzk3MTUzYTE0ZTBiMDQ3NTQ2YWEifQ.8sDjAo_4_TYngaUia_q7D-MIf7LaLbNYDpuSilHDyig");
        String api_key = "'Bearer " + x + "'";
        Log.i("TAG", "APIkey: " + api_key);
        header.put("Authorization", String.valueOf(api_key));

        Call<UserProfileModel> call = apiInterface.SendStatus(header, level,device_status);
        call.enqueue(new Callback<UserProfileModel>() {
            @Override
            public void onResponse(Call<UserProfileModel> call, Response<UserProfileModel> response) {

                if (response.body() != null) {
                    Toast.makeText(getApplicationContext(), "Send Status Battery  "+ level, Toast.LENGTH_SHORT).show();
                } else {
                   // Toast.makeText(getApplicationContext(), "Conn", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<UserProfileModel> call, Throwable t) {

                Toast.makeText(getApplicationContext(), "Connection failed" , Toast.LENGTH_SHORT).show();

            }
        });


    }


}


