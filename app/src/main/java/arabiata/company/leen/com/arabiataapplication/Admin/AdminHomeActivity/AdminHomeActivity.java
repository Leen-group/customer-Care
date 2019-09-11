package arabiata.company.leen.com.arabiataapplication.Admin.AdminHomeActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.anychart.AnyChartView;


import arabiata.company.leen.com.arabiataapplication.Admin.AdminNotification.AdminNotification;
import arabiata.company.leen.com.arabiataapplication.Base.BaseActivity;
import arabiata.company.leen.com.arabiataapplication.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AdminHomeActivity extends BaseActivity<AdminHomePresenter> implements AdminHomeView {
    @BindView(R.id.any_chart_view)
    AnyChartView chartView;
    @BindView(R.id.admin_spinner)
    CalendarView secondCalender;
    @BindView(R.id.admin_spinner_year)
    CalendarView firstCalender;
    @BindView(R.id.AdminNotific)
    ImageView AdminNoti;
    @BindView(R.id.tvspinneryear)
    TextView tvFirstCalender;
    @BindView(R.id.tvspinnerbranch)
    TextView tvSecondCalender;
    private final String TAG = "ADMINACTIVITY";
    static boolean globalflag = false;
    String firstdate = null;
    String seconddate = null;

    @NonNull
    @Override
    protected AdminHomePresenter createPresenter(@NonNull Context context) {
        return new AdminHomePresenter(this, this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title

        setContentView(R.layout.activity_admin_home);
        getSupportActionBar().hide(); //hide the title bar
        ButterKnife.bind(this);
        Log.i(TAG, " firstCalender.getDate(): "+ firstCalender.getDate());
        SharedPreferences pref = getApplicationContext().getSharedPreferences("ChartValues", MODE_PRIVATE);
     //   Toast.makeText(this, "" + getDeviceDensityString(this), Toast.LENGTH_SHORT).show();
        String firstdatepref = pref.getString("firstdate", null);
        String seconddatepref = pref.getString("seconddate", "null");
        boolean flag = pref.getBoolean("flagchart", false);
        Log.i(TAG, "onCreateseconddatepref: "+flag);

        if (flag == true) {
            Log.i(TAG, "seconddatepref: "+seconddatepref);
            if(seconddatepref.equals("samedate")){
                SharedPreferences prefs = getApplicationContext().getSharedPreferences("ChartValues", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
               // Toast.makeText(this, "same date", Toast.LENGTH_SHORT).show();
                mPresenter.GetdateReview(firstdatepref);
                tvFirstCalender.setText(firstdatepref);
                tvSecondCalender.setText(firstdatepref);
                editor.remove("seconddate"); // will delete key key_name4
                editor.remove("flagchart"); // will delete key key_name4

// Save the changes in SharedPreferences
                editor.commit(); // commit changes

            }else{
               // Toast.makeText(this, "flag Equal True", Toast.LENGTH_SHORT).show();

                mPresenter.getReviewPeriod(firstdatepref, seconddatepref);
                Log.i(TAG, "onCreateresult: " + firstdatepref + "second =" + seconddatepref);
                tvFirstCalender.setText(firstdatepref);
                tvSecondCalender.setText(seconddatepref);
                SharedPreferences.Editor editor = pref.edit();

                editor.remove("flagchart"); // will delete key key_name4

            }
        } else {
         //   Toast.makeText(this, "Default Day", Toast.LENGTH_SHORT).show();

            mPresenter.GetReviews();

        }
       /* SharedPreferences pref = getApplicationContext().getSharedPreferences("ChartValues", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("branchposition", branchposition);  // Saving string
        editor.putInt("yearSelectedBranch", myNum);  // Saving string
        editor.putBoolean("flagchart", globalflag);
        editor.putString("chartyear",text);
        editor.putString("branchtext",branchtext);

// Save the changes in SharedPreferences
        editor.commit(); // commit changes
        Intent io = new Intent(AdminHomeActivity.this, AdminHomeActivity.class);
        startActivity(io);
        finish();*/
        ///////////////////////////////////////////
        /*int branchposition = pref.getInt("branchposition", 0);
        int yearSelectedBranch = pref.getInt("yearSelectedBranch", 0);
        boolean flag = pref.getBoolean("flagchart", false);
        String branchyear=pref.getString("chartyear",null);
        String branchtext=pref.getString("branchtext",null);

        Log.i(TAG, "onCreateflagflagflag: " + flag);

        if (flag == false) {
            int branchpostionfinal = branchposition - 1;
            mPresenter.GetBranchesYears();
            mPresenter.GetBranches();
            mPresenter.GetReviews(0, 2019);
        *//*    SharedPreferences.Editor editor = pref.edit();

            editor.clear();
            editor.commit(); // commit changes*//*
        } else {
            tvspinneryear.setText(branchyear);
            tvspinnerbranch.setText(branchtext);
            mPresenter.GetBranchesYears();
            mPresenter.GetBranches();
            Log.i(TAG, "branchpositionbranchposition: "+branchposition);
            *//*spinner.setSelection(branchposition);
            spinneryear.setSelection(0);*//*
            Log.i(TAG, "onCreateyearSelectedBranch: " + yearSelectedBranch);
            int branchpostionfinal = branchposition - 1;
            if (branchpostionfinal < 0) {
                branchpostionfinal = 0;
            }
            Log.i(TAG, "onCreatebranchposition: " + branchpostionfinal);

            mPresenter.SetBranchesReview(branchpostionfinal, yearSelectedBranch);

            globalflag = false;
        }*/

        firstCalender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                //    firstdate = (month + 1) + "/" + dayOfMonth + "/" + year;
                firstdate = year + "/" + (month + 1) + "/" + dayOfMonth;
                tvFirstCalender.setText(firstdate);
            }
        });
        secondCalender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                seconddate = year + "/" + (month + 1) + "/" + dayOfMonth;
                tvSecondCalender.setText(seconddate);
            }
        });
    }

    @OnClick(R.id.AdminNotific)
    public void NotificationButton() {
        NavigateToAdminNotification();
    }

    // Single view button click event
    @OnClick(R.id.btnok)
    public void submit(View view) {
        Log.i(TAG, "firstdate= " + firstdate + "Second Date =" + seconddate);
        globalflag = true;

        if (firstdate == null && !(seconddate == null)) {
         /*   SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
          //  String selectedDate = sdf.format(new Date(firstCalender.getDate()));
            String selectedDate = sdf.format(new Date(firstCalender.getDate()));

            Log.i(TAG, "selectedDate: "+selectedDate);
            SharedPreferences pref = getApplicationContext().getSharedPreferences("ChartValues", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            Log.i(TAG, " String.valueOf(firstCalender.getDate()): " + firstCalender.getDate());
            editor.putString("firstdate", selectedDate);  // Saving string
            editor.putString("seconddate", seconddate);  // Saving string
            editor.putBoolean("flagchart", globalflag);
// Save the changes in SharedPreferences
            editor.commit(); // commit changes
            globalflag = false;

            Intent io = new Intent(AdminHomeActivity.this, AdminHomeActivity.class);
            startActivity(io);
            finish();*/
            Toast.makeText(this,  AdminHomeActivity.this.getString(R.string.pleasechoosedateinthefirstcalender), Toast.LENGTH_LONG).show();

        } else if (seconddate == null && !(firstdate == null)) {
    /*        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd ");
            String selectedDate = sdf.format(new Date(secondCalender.getDate()));
            SharedPreferences pref = getApplicationContext().getSharedPreferences("ChartValues", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            Log.i(TAG, " String.valueOf(secondCalender.getDate()): " + secondCalender.getDate());
            editor.putString("firstdate", selectedDate);  // Saving string
            editor.putString("seconddate", seconddate);  // Saving string
            editor.putBoolean("flagchart", globalflag);
// Save the changes in SharedPreferences
            editor.commit(); // commit changes
            globalflag = false;

            Intent io = new Intent(AdminHomeActivity.this, AdminHomeActivity.class);
            startActivity(io);
            finish();*/
            Toast.makeText(this,  AdminHomeActivity.this.getString(R.string.pleasechoosedateinthefirstcalender), Toast.LENGTH_LONG).show();

        } else if (!(firstdate == null) && !(seconddate == null)) {
            if (firstdate.equals(seconddate)) {
             //   Toast.makeText(this, "moshklaa", Toast.LENGTH_SHORT).show();
                Log.i(TAG, " globalflagglobalflag: " + globalflag);

                SharedPreferences pref = getApplicationContext().getSharedPreferences("ChartValues", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("firstdate", firstdate);  // Saving string
                editor.putString("seconddate", "samedate");  // Saving string
                editor.putBoolean("flagchart", globalflag);
// Save the changes in SharedPreferences
                editor.commit(); // commit changes
                globalflag = false;

                Intent io = new Intent(AdminHomeActivity.this, AdminHomeActivity.class);
                startActivity(io);
                finish();
            } else {
                Log.i(TAG, " globalflagglobalflag: " + globalflag);

                SharedPreferences pref = getApplicationContext().getSharedPreferences("ChartValues", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("firstdate", firstdate);  // Saving string
                editor.putString("seconddate", seconddate);  // Saving string
                editor.putBoolean("flagchart", globalflag);
// Save the changes in SharedPreferences
                editor.commit(); // commit changes
                globalflag = false;

                Intent io = new Intent(AdminHomeActivity.this, AdminHomeActivity.class);
                startActivity(io);
                finish();
            }
        }


    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void NavigateToAdminNotification() {
        Intent Notific = new Intent(AdminHomeActivity.this, AdminNotification.class);
        startActivity(Notific);
        finish();
    }


    public static String getDeviceDensityString(Context context) {
        String x = "";
        switch (context.getResources().getDisplayMetrics().densityDpi) {
            case DisplayMetrics.DENSITY_LOW:
                return "ldpi";
            case DisplayMetrics.DENSITY_MEDIUM:
                return "mdpi";
            case DisplayMetrics.DENSITY_TV:
            case DisplayMetrics.DENSITY_HIGH:
                return "hdpi";
            case DisplayMetrics.DENSITY_260:
            case DisplayMetrics.DENSITY_280:
            case DisplayMetrics.DENSITY_300:
            case DisplayMetrics.DENSITY_XHIGH:
                return "xhdpi";
            case DisplayMetrics.DENSITY_340:
            case DisplayMetrics.DENSITY_360:
            case DisplayMetrics.DENSITY_400:
            case DisplayMetrics.DENSITY_420:
            case DisplayMetrics.DENSITY_440:
            case DisplayMetrics.DENSITY_XXHIGH:
                return "xxhdpi";
            case DisplayMetrics.DENSITY_560:
            case DisplayMetrics.DENSITY_XXXHIGH:
                return "xxxhdpi";
        }

        return x;
    }


}
