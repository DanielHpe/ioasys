package com.example.aluno.ioasys;


import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.aluno.ioasys.adapter.EmpresasAdapter;
import com.example.aluno.ioasys.config.SharedPref;
import com.example.aluno.ioasys.dao.EmpresasDAO;
import com.example.aluno.ioasys.entity.Empresas;
import com.example.aluno.ioasys.service.IoasysService;
import com.google.gson.Gson;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private IoasysService service;
    private ListView listView;
    private EmpresasAdapter adapter;
    private SweetAlertDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);

        progress = new SweetAlertDialog( MainActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        progress.setTitleText("Carregando empresas...");
        progress.setCancelable(false);
        progress.show();

        service = new IoasysService();
        SharedPref.init(MainActivity.this);
        getDataFromServer();

    }

    public void getDataFromServer(){

        String token = SharedPref.readString("token", "");
        String client = SharedPref.readString("client", "");
        String uid = SharedPref.readString("uid", "");

        Call call = service.getAPI().getEmpresas(token, client, uid);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Gson gson = new Gson();
                EmpresasDAO empresasDAO = new EmpresasDAO();
                String json = gson.toJson(response.body());
                List<Empresas> listEmpresas = empresasDAO.parseJsonEmpresas(json);
                adapter = new EmpresasAdapter(getApplicationContext(), listEmpresas);
                listView.setAdapter(adapter);
                progress.dismissWithAnimation();
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                erroCarregarEmpresas();
            }
        });

    }

    private void erroCarregarEmpresas() {

        Dialog dialog = new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Erro ao carregar empresas!")
                .setContentText("Confira a conexão com a internet!")
                .setConfirmText("OK");

        progress.dismissWithAnimation();
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) myActionMenuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if(TextUtils.isEmpty(newText)){
                    adapter.filtro("");
                    listView.clearTextFilter();
                } else {
                    adapter.filtro(newText);
                }

                return true;
            }
        });
        return true;
    }

}
