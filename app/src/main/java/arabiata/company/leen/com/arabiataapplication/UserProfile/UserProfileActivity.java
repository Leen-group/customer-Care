package arabiata.company.leen.com.arabiataapplication.UserProfile;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;

import arabiata.company.leen.com.arabiataapplication.Base.BaseActivity;
import arabiata.company.leen.com.arabiataapplication.NetworkChangeReceiver;
import arabiata.company.leen.com.arabiataapplication.R;
import arabiata.company.leen.com.arabiataapplication.Survey.SurveyActivity;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class UserProfileActivity extends BaseActivity<UserProfilePresenter> implements UserProfileView {

    @BindView(R.id.btn_Done)
    Button btn_Done;
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_visit)
    MaterialBetterSpinner et_visit;
    @BindView(R.id.et_comment)
    EditText et_comment;

    @BindString(R.string.missing)
    String missing;
    @BindString(R.string.validmobile)
    String validmobile;


    //spinner visits
    @BindString(R.string.daily)
    String daily;
    @BindString(R.string.weekly)
    String weekly;
    @BindString(R.string.monthly)
    String monthly;
    @BindString(R.string.rarely)
    String rarely;


    int userStatus;
    ArrayList<Integer> arrayListIdsActivity;
    private BroadcastReceiver mNetworkReceiver;
    static TextView tv_check_connection;



    @NonNull
    @Override
    protected UserProfilePresenter createPresenter(@NonNull Context context) {
        return new UserProfilePresenter(this, this);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                Intent intent = new Intent(UserProfileActivity.this, SurveyActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                //   finish();
                break;
        }
        return true;

    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }else{
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            Intent closeDialog = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
            sendBroadcast(closeDialog);

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

/*
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        et_visit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
*/
        tv_check_connection=(TextView) findViewById(R.id.tv_check_connection4);
        mNetworkReceiver = new NetworkChangeReceiver();
        registerNetworkBroadcastForNougat();


        String[] arrayvisits = new String[]{daily, weekly, monthly, rarely};

        ArrayAdapter<String> arrayAdapterCountry = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item, arrayvisits);
        et_visit.setAdapter(arrayAdapterCountry);


        arrayListIdsActivity = new ArrayList<>();
        Intent i = getIntent();
        userStatus = i.getIntExtra("status_key", 5);
        arrayListIdsActivity = i.getIntegerArrayListExtra("arrayListIds");

        // Toast.makeText(this, "user status "+userStatus, Toast.LENGTH_LONG).show();
        //Toast.makeText(this, ""+arrayListIdsActivity.size(), Toast.LENGTH_SHORT).show();

        btn_Done.setEnabled(true);
        btn_Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEntryData();
            }
        });

    }

    public static void dialogProfile(boolean value){

        if(value){
            tv_check_connection.setText("We are back !!!");
            tv_check_connection.setBackgroundColor(Color.GREEN);
            tv_check_connection.setTextColor(Color.WHITE);

            Handler handler = new Handler();
            Runnable delayrunnable = new Runnable() {
                @Override
                public void run() {
                    tv_check_connection.setVisibility(View.GONE);
                }
            };
            handler.postDelayed(delayrunnable, 3000);
        }else {
            tv_check_connection.setVisibility(View.VISIBLE);
            tv_check_connection.setText("Could not Connect to internet");
            tv_check_connection.setBackgroundColor(Color.RED);
            tv_check_connection.setTextColor(Color.WHITE);
        }
    }


    private void registerNetworkBroadcastForNougat() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }

    protected void unregisterNetworkChanges() {
        try {
            unregisterReceiver(mNetworkReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterNetworkChanges();
    }

    @Override
    public void checkEntryData() {
        if (!et_name.getText().toString().isEmpty()
                && !et_phone.getText().toString().isEmpty()
                && !et_visit.getText().toString().isEmpty()
                && !et_comment.getText().toString().isEmpty()) {
            if (et_phone.getText().toString().length() > 9) {

                mPresenter.SetRatingAPI();


/*
                String x = et_phone.getText().toString().substring(0, 1);
                String y = et_phone.getText().toString().substring(1, 2);
                String z = et_phone.getText().toString().substring(2, 3);


                Log.i("TAG", "checkEntryData: " + x);
                Log.i("TAG", "checkEntryData: " + y);

                if (x.equals("0") && y.equals("1")) {
                    mPresenter.SetRatingAPI();
                } else {
                    Toast.makeText(this, validmobile, Toast.LENGTH_SHORT).show();

                }
                if (x.equals("0") && y.equals("1")) {
                    mPresenter.SetRatingAPI();
                } else {
                    Toast.makeText(this, validmobile, Toast.LENGTH_SHORT).show();

                }



*/

            } else {
                Toast.makeText(this, validmobile, Toast.LENGTH_SHORT).show();

            }


        } else {
            Toast.makeText(this, missing, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void NavigateToThankyou() {
        Intent i = new Intent(UserProfileActivity.this, ThankyouActivity.class);
        startActivity(i);
        finish();
    }


}

