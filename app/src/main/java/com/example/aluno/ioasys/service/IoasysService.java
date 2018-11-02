package com.example.aluno.ioasys.service;

import com.example.aluno.ioasys.entity.Usuario;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.*;

public class IoasysService {

    public static final String BASE_URL = "http://empresas.ioasys.com.br";
    public static String API_BASE_URL = "http://empresas.ioasys.com.br/api/v1/";

    public IoasysApi getAPI(){
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS);

        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client.build())
                .build();

        return retrofit.create(IoasysApi.class);
    }

    public interface IoasysApi {

        @POST("users/auth/sign_in")
        Call<Usuario> loginUsuario(@Body Usuario usuario);

        @GET("enterprises/")
        Call<Object> getEmpresas(@Header("access-token") String token,
                                        @Header("client") String client,
                                        @Header("uid") String uid);

    }
}
