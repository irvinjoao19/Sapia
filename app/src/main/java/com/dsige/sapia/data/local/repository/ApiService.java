package com.dsige.sapia.data.local.repository;

import com.dsige.sapia.data.local.model.Personal;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {

    @Headers("Cache-Control: no-cache")
    @POST("Sapia/GetUsuario")
    Observable<Personal> getUser(@Body RequestBody filtro);

}