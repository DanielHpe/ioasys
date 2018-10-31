package com.example.aluno.ioasys.service;

import android.content.Context;

import com.example.aluno.ioasys.entity.Empresas;
import com.example.aluno.ioasys.entity.EmpresasLista;
import com.example.aluno.ioasys.entity.Usuario;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.*;

public class IoasysService {

    public static String baseURL = "http://empresas.ioasys.com.br/";
    public static String API_BASE_URL = "http://empresas.ioasys.com.br/api/v1/";

    public SyncpriceAPI getAPI(){
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

        return retrofit.create(SyncpriceAPI.class);
    }

    public interface SyncpriceAPI {

        @POST("users/auth/sign_in")
        Call<Usuario> loginUsuario(@Body Usuario usuario);

        @GET("enterprises")
        Call<EmpresasLista> getEmpresas(@Header("access-token") String token,
                                        @Header("client") String client,
                                        @Header("uid") String uid);

    }
}
