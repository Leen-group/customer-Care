package arabiata.company.leen.com.arabiataapplication.Login;

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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;
import com.subhrajyoti.passwordview.PasswordView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import arabiata.company.leen.com.arabiataapplication.Admin.AdminHomeActivity.AdminHomeActivity;
import arabiata.company.leen.com.arabiataapplication.Base.BaseActivity;
import arabiata.company.leen.com.arabiataapplication.MainActivity;
import arabiata.company.leen.com.arabiataapplication.NetworkChangeReceiver;
import arabiata.company.leen.com.arabiataapplication.R;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginView {

    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.et_email)
    EditText et_email;
    @BindView(R.id.et_password)
    PasswordView et_password;
    private FirebaseAuth mAuth;


    @BindString(R.string.validemail)
    String validemail;
    @BindString(R.string.validpassword)
    String validpassword;
    @BindString(R.string.missing)
    String missing;

    String email = "";
    String password = "";
    boolean flag_login_email = false;
    boolean flag_login_password = false;
    private BroadcastReceiver mNetworkReceiver;
    static TextView tv_check_connection;

    //
    @NonNull
    @Override
    protected LoginPresenter createPresenter(@NonNull Context context) {
        return new LoginPresenter(this, this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        setContentView(R.layout.activity_login);
        //
        ButterKnife.bind(this);
        //  I can't seem to stop the SoftKeyboard from appearing when an EditText gets focus
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //
        getEmailPassword();


        mPresenter.GetBranches(); // Login Presenter

        mAuth = FirebaseAuth.getInstance();
        final String token = FirebaseInstanceId.getInstance().getToken();
        tv_check_connection=(TextView) findViewById(R.id.tv_check_connection);

        mNetworkReceiver = new NetworkChangeReceiver();

        registerNetworkBroadcastForNougat();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getEmailPassword();

                if (flag_login_email && flag_login_password) {
                    mPresenter.LoginApi(email, password,token);
                } else {
                    Toast.makeText(LoginActivity.this, missing, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public static void dialog(boolean value){

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
    public void getEmailPassword() {

        if (!et_password.getText().toString().isEmpty()) {
            flag_login_password = true;
            password = et_password.getText().toString();
        } else {
            flag_login_password = false;
        }

        et_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                //Toast.makeText(LoginActivity.this, "a", Toast.LENGTH_SHORT).show();
                if (isEmailValid(et_email.getText().toString())) {
                    email = et_email.getText().toString();
                    flag_login_email = true;
                } else {
                    et_email.setError(validemail);
                    flag_login_email = false;
                }
            }


            @Override
            public void afterTextChanged(Editable s) {

                if (!et_email.getText().toString().isEmpty()) {

                    if (isEmailValid(et_email.getText().toString())) {
                        email = et_email.getText().toString();
                        flag_login_email = true;
                    } else {
                        et_email.setError(validemail);
                        flag_login_email = false;
                    }
                } else {
                    flag_login_email = false;
                }
            }
        });

    }


    @Override
    public void NavigateToMain() {
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void NavigateToAdmin() {
        Intent i = new Intent(LoginActivity.this, AdminHomeActivity.class);
        startActivity(i);
        finish();

    }


    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


}