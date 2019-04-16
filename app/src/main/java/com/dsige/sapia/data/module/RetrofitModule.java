package com.dsige.sapia.data.module;

import com.dsige.sapia.data.local.AppDataBase;
import com.dsige.sapia.data.local.repository.ApiService;
import com.dsige.sapia.data.local.repository.AppRepoImp;
import com.dsige.sapia.data.local.repository.AppRepository;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = ViewModelModule.class)
public class RetrofitModule {

    private final static String BASE_URL = "http://192.168.0.3:8085/api/";

    @Provides
    @Singleton
    Retrofit providesRetrofit(GsonConverterFactory gsonFactory, RxJava2CallAdapterFactory rxJava, OkHttpClient client) {
        return new Retrofit.Builder().baseUrl(BASE_URL)
                .addCallAdapterFactory(rxJava)
                .addConverterFactory(gsonFactory)
                .client(client)
                .build();
    }

    @Provides
    @Singleton
    OkHttpClient providesOkHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .build();
    }

    @Provides
    @Singleton
    GsonConverterFactory providesGsonConverterFactory() {
        return GsonConverterFactory.create();
    }

    @Provides
    @Singleton
    RxJava2CallAdapterFactory providesRxJavaCallAdapterFactory() {
        return RxJava2CallAdapterFactory.create();
    }

    @Singleton
    @Provides
    ApiService provideService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

    @Provides
    AppRepository provideRepository(ApiService apiService, AppDataBase database) {
        return new AppRepoImp(apiService, database);
    }
}