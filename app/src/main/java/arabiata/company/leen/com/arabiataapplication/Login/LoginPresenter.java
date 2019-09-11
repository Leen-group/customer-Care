package arabiata.company.leen.com.arabiataapplication.Login;

import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import arabiata.company.leen.com.arabiataapplication.Admin.AdminHomeActivity.GetBranchesModel.GetBranchesModel;
import arabiata.company.leen.com.arabiataapplication.Base.BasePresenter;
import arabiata.company.leen.com.arabiataapplication.Network.ApiClient;
import arabiata.company.leen.com.arabiataapplication.Network.ApiInterface;
import arabiata.company.leen.com.arabiataapplication.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class LoginPresenter extends BasePresenter {

    LoginActivity context;
    LoginView view;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    List<String> branches;



    public LoginPresenter(LoginActivity context, LoginView view) {
        this.context = context;
        this.view = view;
        prefs = context.getSharedPreferences("MyPREFERENCES", MODE_PRIVATE);
        branches=new ArrayList<>();

    }
    boolean check=false;

    //LoginAPi
    public void LoginApi(final String email, final String password,final  String token) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        //   Log.i("TAG", "LoginApi: "+email);
        // Log.i("TAG", "LoginApi: "+password);

        Call<LoginModel> call = apiInterface.Login(email, password);
        if(branches!=null){
            for (int i = 0; i <branches.size() ; i++) {
                Log.i("TAG", "LoginApi"+branches.get(i));
              //  Toast.makeText(context, ""+branches.get(i), Toast.LENGTH_SHORT).show();
        if (branches.get(i).equals(email.toLowerCase())){
            check=true;
        }
            }

        }

        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                editor = prefs.edit();

                if (response.body() != null && response.body().getSuccess().equals("true")) {
                    if (response.body().getIsAdmin().equals("true")) {

                        editor.putString("admin_token", String.valueOf(response.body().getToken()));
                        editor.putString("otp_login_admin", "true");
                        editor.apply();
                        SendTokenApi(token,response.body().getToken());
                        view.NavigateToAdmin();

                        //  Toast.makeText(context, "Successful Admin", Toast.LENGTH_SHORT).show();

                    } else {

                        if(check){
                            editor.putString("branch_token", String.valueOf(response.body().getToken()));

                            editor.putString("email", String.valueOf(email));
                            editor.putString("password", String.valueOf(password));
                            editor.putString("otp_login_branch", "true");

                            editor.apply();
                            //  Toast.makeText(context, context.getResources().getString(R.string.right_login), Toast.LENGTH_SHORT).show();
                            view.NavigateToMain();

                        }else{
                            Toast.makeText(context, context.getResources().getString(R.string.wrong_login), Toast.LENGTH_SHORT).show();

                        }

                    }

                } else {

                    Toast.makeText(context, context.getResources().getString(R.string.wrong_login), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {

                Toast.makeText(context, "Connection " + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }


    public void SendTokenApi( String token,String admintoken){
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Log.i("TAG", "getTokenApi: "+token);
//header key
        Map<String, String> header = new HashMap<>();
        String x = admintoken;
        String api_key = "'Bearer " + x + "'+";


        Log.i("TAG", "APIkeyAdminKey: " + api_key);
        header.put("Authorization", String.valueOf(api_key));

        Call<TokenApiModel> call = apiInterface.SendTokenApi(header,token);
        call.enqueue(new Callback<TokenApiModel>() {
            @Override
            public void onResponse(Call<TokenApiModel> call, Response<TokenApiModel> response) {
                if(response.body()!=null){
                    Log.i("TAG", "onResponsegettokenresponse: "+response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<TokenApiModel> call, Throwable t) {

            }
        });
    }



    public void GetBranches() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<GetBranchesModel> call = apiInterface.GetBranches();

        call.enqueue(new Callback<GetBranchesModel>() {
            @Override
            public void onResponse(Call<GetBranchesModel> call, Response<GetBranchesModel> response) {

                if (response.body() != null) {
                    for (int i = 0; i < response.body().getData().size(); i++) {

                        branches.add(response.body().getData().get(i).getEmail().toLowerCase());

                    }
                }
            }

            @Override
            public void onFailure(Call<GetBranchesModel> call, Throwable t) {

            }
        });

    }


}