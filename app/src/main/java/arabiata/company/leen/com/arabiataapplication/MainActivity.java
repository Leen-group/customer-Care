package arabiata.company.leen.com.arabiataapplication;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.BatteryManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import arabiata.company.leen.com.arabiataapplication.Admin.AdminHomeActivity.AdminHomeActivity;
import arabiata.company.leen.com.arabiataapplication.Login.LoginActivity;
import arabiata.company.leen.com.arabiataapplication.Survey.SurveyActivity;
import arabiata.company.leen.com.arabiataapplication.UserProfile.ReceiverScreen;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.iv_happy)
    ImageView iv_happy;
    @BindView(R.id.iv_unhappy)
    ImageView iv_unhappy;

    @BindString(R.string.change_lang)
    String change_lang;
    @BindString(R.string.logout)
    String logout;
    @BindString(R.string.Done)
    String Done;
    @BindString(R.string.cancel)
    String cancel;

    EditText et_email_logout;

    EditText et_password_logout;



    int userStatus;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    String otp_login_admin = "false";
    String otp_login_branch = "false";

    @BindString(R.string.survey)
    String survey;
////
@BindString(R.string.validemail)
String validemail;




    String email = "";
    String password = "";
    boolean flag_login_email = false;
    boolean flag_login_password = false;


/*
    @Override
    protected void onResume() {
        super.onResume();

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle(survey);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
// screen and CPU will stay awake during this section
        prefs = getSharedPreferences("MyPREFERENCES", MODE_PRIVATE);
        editor = prefs.edit();


        otp_login_admin = prefs.getString("otp_login_admin", "false");
        otp_login_branch = prefs.getString("otp_login_branch", "false");


        if (otp_login_branch.equals("false")&&otp_login_admin.equals("false")) {
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
            finish();
        }else if(otp_login_admin.equals("true")){
            Intent i = new Intent(this, AdminHomeActivity.class);
            startActivity(i);
            finish();
        }

        Intent intent = new Intent (MainActivity.this, AdminHomeActivity
                .class);
        startActivity(intent);
         // batterystatus();
        iv_happy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             //   onAttachedToWindow();


                userStatus = 1;
                Intent i = new Intent(MainActivity.this, SurveyActivity.class);
                i.putExtra("status_key", userStatus);
                startActivity(i);
            }
        });
        iv_unhappy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userStatus = 0;
                Intent i = new Intent(MainActivity.this, SurveyActivity.class);
                i.putExtra("status_key", userStatus);
                startActivity(i);
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.change_lang:
               // Toast.makeText(this, "lang", Toast.LENGTH_SHORT).show();
                showChangeLangDialog();
                break;
            case R.id.logout:
                logoutDialog();
               // Toast.makeText(this, "logout", Toast.LENGTH_SHORT).show();

                break;
        }

        return super.onOptionsItemSelected(item);
    }


    public void batterystatus() {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = registerReceiver(null, ifilter);
        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        Toast.makeText(this, "" + level, Toast.LENGTH_SHORT).show();

    }

    //change the language of the application
    public void changeLang(Context context, String lang) {
        Locale myLocale = new Locale(lang);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());

        Intent i = new Intent(this, SplashActivity.class);
        startActivity(i);
        finish();
    }

    //function of alert dialog when choose translate icon
    public void showChangeLangDialog() {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        // View dialogView = inflater.inflate(R.layout.logut_dialog, null);
        //dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle(change_lang);


        dialogBuilder.setPositiveButton("English", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                changeLang(getApplicationContext(), "en");
            }
        });


        dialogBuilder.setNegativeButton("Arabic", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int whichButton) {
                changeLang(getApplicationContext(), "ar");
            }
        });

        final AlertDialog b = dialogBuilder.create();
        //2. now setup to change color of the button
        b.setOnShowListener(new DialogInterface.OnShowListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onShow(DialogInterface arg0) {

            }
        });
        b.show();

    }



    //function of alert dialog when choose translate icon
    public void logoutDialog() {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
         View dialogView = inflater.inflate(R.layout.logut_dialog, null);
        dialogBuilder.setView(dialogView);

        et_email_logout = dialogView.findViewById(R.id.et_email_logout);
        et_password_logout = dialogView.findViewById(R.id.et_password_logout);



        dialogBuilder.setTitle(logout);

        final String s_email = prefs.getString("email", "false");
        final String s_password = prefs.getString("password", "false");
        getEmailPassword();

       // Toast.makeText(this, ""+s_email, Toast.LENGTH_SHORT).show();
        dialogBuilder.setPositiveButton(Done, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                getEmailPassword();

                if (flag_login_email &&flag_login_password) {
                    if(email.equals(s_email)&&password.equals(s_password)){
                        Toast.makeText(MainActivity.this, "Successful logout", Toast.LENGTH_SHORT).show();

                        editor.putString("otp_login_branch", "false");
                        editor.apply();

                        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(i);
                        finish();

                        otp_login_branch = prefs.getString("otp_login_branch", "false");

                    }else{
                        Toast.makeText(MainActivity.this, "Logout Failed", Toast.LENGTH_SHORT).show();
                    }
                }
                else{

                  //  Toast.makeText(MainActivity.this, "email null", Toast.LENGTH_SHORT).show();

                }

            }
        });


        dialogBuilder.setNegativeButton(cancel, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int whichButton) {
              //  changeLang(getApplicationContext(), "ar");
            }
        });

        final AlertDialog b = dialogBuilder.create();
        b.show();

    }




    public void getEmailPassword() {

        if (!et_password_logout.getText().toString().isEmpty()) {
            flag_login_password = true;
            password = et_password_logout.getText().toString();
        } else {
            flag_login_password = false;
        }

        et_email_logout.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                //Toast.makeText(LoginActivity.this, "a", Toast.LENGTH_SHORT).show();


                if (isEmailValid(et_email_logout.getText().toString())) {
                    email = et_email_logout.getText().toString();
                    flag_login_email = true;
                } else {
                    et_email_logout.setError(validemail);
                    flag_login_email = false;
                }
            }


            @Override
            public void afterTextChanged(Editable s) {

                if (!et_email_logout.getText().toString().isEmpty()) {

                    if (isEmailValid(et_email_logout.getText().toString())) {
                        email = et_email_logout.getText().toString();
                        flag_login_email = true;
                    }else{
                        et_email_logout.setError(validemail);
                        flag_login_email = false;
                    }
                } else {
                    flag_login_email = false;
                }
            }
        });

    }



    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    @Override
    public void onBackPressed() {

        }


    @Override
    protected void onPause() {
        // when the screen is about to turn off
        if (ReceiverScreen.wasScreenOn) {
            // this is the case when onPause() is called by the system due to a screen state change
            Intent closeDialog = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
            sendBroadcast(closeDialog);

            Log.i("TAG", "harwdarebtns:"+"SCREEN TURNED OFF");
           // System.out.println("SCREEN TURNED OFF");

        } else {
            // this is when onPause() is called when the screen state has not changed
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        // only when screen turns on

        if (!ReceiverScreen.wasScreenOn) {
            // this is when onResume() is called due to a screen state change
            Log.i("TAG", "harwdarebtns:"+"SCREEN TURNED ON");


           // System.out.println("SCREEN TURNED ON");
        } else {
            // this is when onResume() is called when the screen state has not changed
        }
        super.onResume();
    }




    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        if ( (event.getKeyCode() == KeyEvent.KEYCODE_HOME)) {
            return true;
        }
        else
            return super.dispatchKeyEvent(event);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub

        if( (keyCode==KeyEvent.KEYCODE_BACK))
        { return true;
        }else if( (keyCode==KeyEvent.KEYCODE_HOME))
        { return true;
        }

        else
            return super.onKeyDown(keyCode, event);
    }

    /*
    @Override
    public void onAttachedToWindow() {
        this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock lock = keyguardManager.newKeyguardLock(KEYGUARD_SERVICE);
        //lock.disableKeyguard();

    }
*/


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


}
