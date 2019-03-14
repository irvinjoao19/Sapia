package com.dsige.sapia.context.retrofit;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRetrofit {

    private String BASE_URL_LOCAL = "http://192.168.0.6:8085/api/";

    private Retrofit retrofit = null;
    private GsonConverterFactory gsonFactory = GsonConverterFactory.create();

    private OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(2, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .build();

    public Retrofit getAPI() {
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL_LOCAL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(gsonFactory)
                .build();

        return retrofit;
    }

}