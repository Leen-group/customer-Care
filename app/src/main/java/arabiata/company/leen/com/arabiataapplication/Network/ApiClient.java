package arabiata.company.leen.com.arabiataapplication.Network;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

///
public class ApiClient {
    public static String BASE_URL;

    public static String getBASE_URL() {
        return BASE_URL;
    }

    public static void setBASE_URL(String BASE_URL) {
        ApiClient.BASE_URL = BASE_URL;
    }

    final static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.MINUTES)
            .writeTimeout(20, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.MINUTES)
            .build();


    private static Retrofit retrofit = null;

    public static Retrofit getClient() {

        if (getBASE_URL() != null) {
            Log.i("TAG", "getClient: " + getBASE_URL());

            retrofit = new Retrofit.Builder()
                    .baseUrl(getBASE_URL())
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;

    }
}


