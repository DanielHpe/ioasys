package com.example.aluno.ioasys;

import android.app.Activity;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.aluno.ioasys.adapter.EmpresasAdapter;
import com.example.aluno.ioasys.config.SharedPref;
import com.example.aluno.ioasys.entity.Empresas;
import com.example.aluno.ioasys.entity.EmpresasLista;
import com.example.aluno.ioasys.service.IoasysService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends Activity {

    private IoasysService service;
    private ListView listView;
    private EmpresasAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);

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
                    List<Empresas> listEmpresas = empresasLista.getEnterprises();
                    adapter = new EmpresasAdapter(getApplicationContext(), listEmpresas);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<EmpresasLista> call, Throwable t) {

            }
        });
    }

}
