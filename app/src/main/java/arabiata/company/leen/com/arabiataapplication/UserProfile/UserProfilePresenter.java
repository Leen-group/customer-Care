package arabiata.company.leen.com.arabiataapplication.UserProfile;

import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import arabiata.company.leen.com.arabiataapplication.Base.BasePresenter;
import arabiata.company.leen.com.arabiataapplication.Network.ApiClient;
import arabiata.company.leen.com.arabiataapplication.Network.ApiInterface;
import arabiata.company.leen.com.arabiataapplication.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class UserProfilePresenter extends BasePresenter {

    UserProfileActivity context;
    UserProfileView view;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;


    public UserProfilePresenter(UserProfileActivity context, UserProfileView view) {
        this.context = context;
        this.view = view;
        prefs = context.getSharedPreferences("MyPREFERENCES", MODE_PRIVATE);
    }

    //SetRatingAPi
    public void SetRatingAPI() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        HashMap<String, Integer> dynamicIds = new HashMap<>();

//header key
        Map<String, String> header = new HashMap<>();
        String x = prefs.getString("branch_token", "");
        String api_key = "'Bearer " + x + "'";


        Log.i("TAG", "APIkey: " + api_key);
        header.put("Authorization", String.valueOf(api_key));

        String name = context.et_name.getText().toString();
        String phone = context.et_phone.getText().toString();
        String visit = context.et_visit.getText().toString();
        String comment = context.et_comment.getText().toString();
        int happy = context.userStatus;


        Log.i("TAG", "SetRatingAPI():" + api_key);
        Log.i("TAG", "SetRatingAPI():" + context.arrayListIdsActivity.size());
        Log.i("TAG", "SetRatingAPI():" + name);
        Log.i("TAG", "SetRatingAPI():" + phone);
        Log.i("TAG", "SetRatingAPI():" + visit);
        Log.i("TAG", "SetRatingAPI():" + comment);
        Log.i("TAG", "SetRatingAPI():" + happy);


        for (int i = 0; i < context.arrayListIdsActivity.size(); i++) {
            Log.i("TAG", "SetRatingAPI:" + "reasons_ids[" + i + "]" + ":   " + context.arrayListIdsActivity.get(i));
            dynamicIds.put("reasons_ids["+i+"]", context.arrayListIdsActivity.get(i));
        }

        Call<UserProfileModel> call = apiInterface.SetRating(header, dynamicIds, name, phone, visit, comment, happy);
        call.enqueue(new Callback<UserProfileModel>() {
            @Override
            public void onResponse(Call<UserProfileModel> call, Response<UserProfileModel> response) {

                if (response.body() != null) {
                    Toast.makeText(context, "" + context.getResources().getString(R.string.rating), Toast.LENGTH_SHORT).show();
                    view.NavigateToThankyou();

                } else {
                    view.NavigateToThankyou();
                   // Toast.makeText(context,response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                //    Log.i("TAG", "response.errorBody().toString(): "+response.errorBody().toString());
                }


            }

            @Override
            public void onFailure(Call<UserProfileModel> call, Throwable t) {

                Toast.makeText(context, "Connection failed ", Toast.LENGTH_SHORT).show();

            }
        });

context.btn_Done.setEnabled(false);
    }


}
