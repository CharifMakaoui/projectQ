package com.aqua_society.quotes.Api;

import com.aqua_society.quotes.Modules.AdsObject;
import com.aqua_society.quotes.Modules.Apps;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by MrCharif on 24/12/2016.
 */

public interface MyApi {

    @GET("dataadmob_v_app_serie1.json.txt?dl=1")
    Call<AdsObject> getAdsConfig();

    @GET("MyAppsJson.json?dl=1")
    Call<List<Apps>> getMyApps();
}


