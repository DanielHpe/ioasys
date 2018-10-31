package com.example.aluno.ioasys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.aluno.ioasys.config.SharedPref;
import com.example.aluno.ioasys.entity.Empresas;
import com.example.aluno.ioasys.entity.EmpresasLista;
import com.example.aluno.ioasys.service.IoasysService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private IoasysService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        service = new IoasysService();
        SharedPref.init(MainActivity.this);
        getDataFromServer();

    }

    public void getDataFromServer(){

        String token = SharedPref.readString("token", "");
        String client = SharedPref.readString("client", "");
        String uid = SharedPref.readString("uid", "");

        service.getAPI().getEmpresas(token, client, uid).enqueue(new Callback<EmpresasLista>() {
            @Override
            public void onResponse(Call<EmpresasLista> call, Response<EmpresasLista> response) {
                if(response.isSuccessful()){
                    EmpresasLista empresasLista = response.body();
                    int a = 1;
                }
            }

            @Override
            public void onFailure(Call<EmpresasLista> call, Throwable t) {

            }
        });

    }
}
