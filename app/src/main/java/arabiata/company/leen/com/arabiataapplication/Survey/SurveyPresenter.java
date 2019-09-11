package arabiata.company.leen.com.arabiataapplication.Survey;

import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import arabiata.company.leen.com.arabiataapplication.Base.BasePresenter;
import arabiata.company.leen.com.arabiataapplication.Network.ApiClient;
import arabiata.company.leen.com.arabiataapplication.Network.ApiInterface;
import arabiata.company.leen.com.arabiataapplication.R;
import arabiata.company.leen.com.arabiataapplication.Survey.Model.SurveyModel;
import arabiata.company.leen.com.arabiataapplication.Survey.Model.SurveyModelItem;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SurveyPresenter extends BasePresenter {

    SurveyActivity context;
    SurveyView view;
    ApiClient apiClient;


    List<SurveyModelItem> arraySurvey;

    public SurveyPresenter(SurveyActivity context, SurveyView view) {
        this.context = context;
        this.view = view;
        arraySurvey=new ArrayList<SurveyModelItem>();
    }

    //LoginAPi
    public void GetSurveyApi() {

        apiClient.setBASE_URL("https://arabiata-app.com/api/en/");
        String phoneLang = context.getResources().getString(R.string.lang);
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
        Call<SurveyModel> call = apiInterface.getSurvey();
        call.enqueue(new Callback<SurveyModel>() {
            @Override
            public void onResponse(Call<SurveyModel> call, Response<SurveyModel> response) {

                if (response.body() != null ) {
                    if(response.body().getSuccess()) {

                        arraySurvey = response.body().getData();

                        context.cartList.addAll(arraySurvey);
                        // refreshing survey adapter inside recycler view
                        context.surveyAdapter.notifyDataSetChanged();
                        //view.NavigateToMain();
                    }

                } else {

                    Toast.makeText(context, "Check connection", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<SurveyModel> call, Throwable t) {

                Toast.makeText(context, "Check connection", Toast.LENGTH_SHORT).show();

            }
        });


    }


}
