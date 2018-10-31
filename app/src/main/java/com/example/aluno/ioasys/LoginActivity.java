package com.example.aluno.ioasys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.aluno.ioasys.config.SharedPref;
import com.example.aluno.ioasys.dao.UsuarioDAO;
import com.example.aluno.ioasys.entity.Usuario;
import com.example.aluno.ioasys.service.IoasysService;

import okhttp3.Headers;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Header;

public class LoginActivity extends Activity {

    private EditText textLogin;
    private EditText textSenha;
    private Button buttonLogin;
    private String email;
    private String senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textLogin = findViewById(R.id.txtLogin);
        textSenha = findViewById(R.id.txtSenha);
        buttonLogin = findViewById(R.id.btnLogin);

        textLogin.setText("testeapple@ioasys.com.br");
        textSenha.setText("12341234");

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logar();
            }
        });

    }

    public void logar(){

        IoasysService service = new IoasysService();

        email = textLogin.getText().toString().trim();
        senha = textSenha.getText().toString().trim();

        Usuario usuario = new Usuario(email, senha);

        service.getAPI().loginUsuario(usuario).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if(response.isSuccessful()){
                    getHeadersFromResponse(response.headers());
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    erroLogin();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                erroLogin();
            }
        });

    }

    private void getHeadersFromResponse(Headers headers) {

        String token = headers.get("access-token");
        String client = headers.get("client");
        String uid = headers.get("uid");

        SharedPref.init(LoginActivity.this);

        SharedPref.writeString("token", token);
        SharedPref.writeString("client", client);
        SharedPref.writeString("uid", uid);

    }


    public void erroLogin(){

    }
}
