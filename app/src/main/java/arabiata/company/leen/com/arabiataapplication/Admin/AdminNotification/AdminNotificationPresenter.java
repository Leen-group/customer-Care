package arabiata.company.leen.com.arabiataapplication.Admin.AdminNotification;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import arabiata.company.leen.com.arabiataapplication.Admin.AdminHomeActivity.AdminHomeActivity;
import arabiata.company.leen.com.arabiataapplication.Admin.AdminNotification.NotificationModel.DeleteNotificationModel;
import arabiata.company.leen.com.arabiataapplication.Admin.AdminNotification.NotificationModel.MyNotificationsModel;
import arabiata.company.leen.com.arabiataapplication.Base.BasePresenter;
import arabiata.company.leen.com.arabiataapplication.Network.ApiClient;
import arabiata.company.leen.com.arabiataapplication.Network.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminNotificationPresenter  extends BasePresenter {
    AdminNotification context;
    AdminNotificationInterface view;
    ApiClient apiClient;

    int pagenumber=1;
    public AdminNotificationPresenter(AdminNotification context, AdminNotificationInterface view) {
        this.context = context;
        this.view = view;
    }

    public AdminNotificationPresenter() {
    }

    public void getMyNotificationsAPI(final List<MyNotificationsModel> cartList, final MyNotiAdapter adapter) {
        apiClient.setBASE_URL("http://arabiata-app.com/api/en/");

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<MyNotificationsModel> callFile = apiInterface.Getmynotifications( pagenumber);
        callFile.enqueue(new Callback<MyNotificationsModel>() {
            @Override
            public void onResponse(Call<MyNotificationsModel> call, Response<MyNotificationsModel> response) {
                if(response.body()!=null){
                    if(response.body().getSuccess()){
                        Log.i("TAG", "onResponsegetData: "+response.body().getData());
                        List<MyNotificationsModel> recipes = response.body().getData();

                        if(response.body().getMessage().equals("found data")){
                            // adding recipes to cart list
                            cartList.addAll(recipes);
                            // refreshing recycler view
                            adapter.notifyDataSetChanged();
                            pagenumber++;
                            getMyNotificationsAPI(cartList,adapter);
                       }else {

                       }
                    }

                }
            }

            @Override
            public void onFailure(Call<MyNotificationsModel> call, Throwable t) {
                Toast.makeText(context, "Slow connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deleteMyNotificationsAPI() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<DeleteNotificationModel> callFile = apiInterface.DeleteNotification( );
        callFile.enqueue(new Callback<DeleteNotificationModel>() {
            @Override
            public void onResponse(Call<DeleteNotificationModel> call, Response<DeleteNotificationModel> response) {
                if(response.body()!=null){
                    Toast.makeText(context, "deleted successfully", Toast.LENGTH_SHORT).show();
                    Intent admin=new Intent(context, AdminHomeActivity.class);
                    context.startActivity(admin);
                    context.finish();
                 //   getMyNotificationsAPI(context.cartList,context.Adapter);

                }
            }

            @Override
            public void onFailure(Call<DeleteNotificationModel> call, Throwable t) {

            }
        });
    }

}
