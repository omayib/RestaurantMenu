package id.technomotion.restaurantmenu;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by omayib on 04/04/16.
 */
public class RestaurantClient {
    private static  final String BASE_URL="http://10.0.3.2:8080";//access PC localhost server from genymotion
    private static  final String API_LOGIN=BASE_URL+"/server.php?action=login";
    private static  final String API_GET_FOODS=BASE_URL+"/server.php?action=foods&token=%s";
    private static  final String API_FOOD_DETAIL=BASE_URL+"/server.php?action=food&id=%s&token=%s";

    public static Call Signin(String email,String password){
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("email",email)
                .add("password",password)
                .build();
        Request request = new Request.Builder()
                .url(API_LOGIN)
                .post(formBody)
                .build();
        System.out.println(request.url().toString());

        return client.newCall(request);
    }

    public static Call Foods(String token){
        String url=String.format(API_GET_FOODS,token);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        System.out.println(request.url().toString());

        return client.newCall(request);
    }
}
