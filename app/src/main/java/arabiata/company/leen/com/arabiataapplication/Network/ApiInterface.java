package arabiata.company.leen.com.arabiataapplication.Network;

import java.util.Map;

import arabiata.company.leen.com.arabiataapplication.Admin.AdminHomeActivity.GetBranchesModel.DailyReviewModel;
import arabiata.company.leen.com.arabiataapplication.Admin.AdminHomeActivity.GetBranchesModel.GetBranchesModel;
import arabiata.company.leen.com.arabiataapplication.Admin.AdminNotification.NotificationModel.DeleteNotificationModel;
import arabiata.company.leen.com.arabiataapplication.Admin.AdminNotification.NotificationModel.MyNotificationsModel;
import arabiata.company.leen.com.arabiataapplication.Login.LoginModel;
import arabiata.company.leen.com.arabiataapplication.Login.TokenApiModel;
import arabiata.company.leen.com.arabiataapplication.Survey.Model.SurveyModel;
import arabiata.company.leen.com.arabiataapplication.UserProfile.UserProfileModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("login")
    @FormUrlEncoded
    Call<LoginModel> Login(@Field("email") String email, @Field("password") String password);

    @GET("reasons/get/")
    Call<SurveyModel> getSurvey();

    @Headers({
            "x-api-key: k4o8ocs8skg8os88o8k4kc0kcgosc8cwkkcc4gsc",
            "Content-Type: application/x-www-form-urlencoded"})

    @POST("rating/set")
    @FormUrlEncoded
    Call<UserProfileModel> SetRating(@HeaderMap Map<String, String> Authorization,
                                     @FieldMap Map<String, Integer> array_resonsids, @Field("name") String name,
                                     @Field("phone") String phone,
                                     @Field("frequency") String frequency,
                                     @Field("comment") String comment,
                                     @Field("happy") Integer happy
    );

    //BattaryService
    @POST("add/status")
    @FormUrlEncoded
    Call<UserProfileModel> SendStatus (@HeaderMap Map<String, String> Authorization, @Field("status") Integer status,@Field("phone_status") String phone_status);
//////////////////////////////////////////////////////apis fesh/////

    @POST("add/status ")
    @FormUrlEncoded
    Call<UserProfileModel> SendStatus(@HeaderMap Map<String, String> Authorization, @Field("status") Integer status);

    //////////////////////////////////////////////////////apis fesh/////
    @POST("feedback/branches_app/period")
    @FormUrlEncoded
    Call<DailyReviewModel> GetReviews(@Field("from") String from, @Field("to") String to);
    ////////////////////////
    @GET("feedback/branches_app")
    Call<DailyReviewModel> GetDailyReviews();
    ///////////////////////////////////////////
    @Headers({
            "Authorization: 'Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC8xOTIuMTY4LjEuMTA3OjgwMDBcL2FwaVwvbG9naW4iLCJpYXQiOjE1NDkyNzEyNDUsIm5iZiI6MTU0OTI3MTI0NSwianRpIjoiSEpRRnhDREZoVkozNXJoUCIsInN1YiI6MywicHJ2IjoiODdlMGFmMWVmOWZkMTU4MTJmZGVjOTcxNTNhMTRlMGIwNDc1NDZhYSJ9.bL_blVouiqP0QBwF4ZuMeVf0Ev52Nb4fGRagBEFukXQ'",
            "Content-Type: application/x-www-form-urlencoded"})
    @POST("feedback_day/branches_app")
    @FormUrlEncoded
    Call<DailyReviewModel> GetDayReview(@Field("date") String date);

    //
    @GET("branches/get")
    Call<GetBranchesModel>  GetBranches();
    ////////////////////////////////////////////////////////
    @POST("notifications/get")
    @FormUrlEncoded
    Call<MyNotificationsModel> Getmynotifications(@Field("page") int page);

    ////////////////////////////////////////////////////////////////////
    @POST("add/key")
    @FormUrlEncoded
    Call<TokenApiModel> SendTokenApi(@HeaderMap Map<String, String> Authorization, @Field("key") String token);


    //delete notification
    @Headers({
            "Authorization: 'Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC8xOTIuMTY4LjEuMTA3OjgwMDBcL2FwaVwvbG9naW4iLCJpYXQiOjE1NDkyNzEyNDUsIm5iZiI6MTU0OTI3MTI0NSwianRpIjoiSEpRRnhDREZoVkozNXJoUCIsInN1YiI6MywicHJ2IjoiODdlMGFmMWVmOWZkMTU4MTJmZGVjOTcxNTNhMTRlMGIwNDc1NDZhYSJ9.bL_blVouiqP0QBwF4ZuMeVf0Ev52Nb4fGRagBEFukXQ'",
            "Content-Type: application/x-www-form-urlencoded"})
    @POST("notifications/clean")
    Call<DeleteNotificationModel> DeleteNotification();
}
