package com.dsige.sapia.context.retrofit.apiInterfaces;

import com.dsige.sapia.model.Migracion;
import com.dsige.sapia.model.Usuario;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface SapiaInterfaces {

    @Headers("Cache-Control: no-cache")
    @POST("Sapia/GetUsuario")
    Observable<Usuario> getUser(@Body RequestBody filtro);

    @Headers("Cache-Control: no-cache")
    @POST("Sapia/GetSincronizacion")
    Observable<Migracion> getSincronation();
    //    @Body RequestBody filtro
}