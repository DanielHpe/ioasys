package com.example.aluno.ioasys;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.aluno.ioasys.config.SharedPref;
import com.example.aluno.ioasys.dao.UsuarioDAO;
import com.example.aluno.ioasys.entity.Usuario;
import com.example.aluno.ioasys.service.IoasysService;

import cn.pedant.SweetAlert.SweetAlertDialog;
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
    private SweetAlertDialog progress;

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

        email = textLogin.getText().toString().trim();
        senha = textSenha.getText().toString().trim();

        if(email.equalsIgnoreCase("")){
            textLogin.setError("Campo Obrigatório");
            textLogin.requestFocus();
        } else if(senha.equalsIgnoreCase("")){
            textSenha.setError("Campo Obrigatório");
            textSenha.requestFocus();
        } else {
            logarUsuario();
        }

    }

    private void logarUsuario() {

        progress = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        progress.setTitleText("Logando...");
        progress.setCancelable(false);
        progress.show();
        buttonLogin.setEnabled(false);

        IoasysService service = new IoasysService();
        Usuario usuario = new Usuario(email, senha);
        service.getAPI().loginUsuario(usuario).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if(response.isSuccessful()){
                    getHeadersFromResponse(response.headers());
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    buttonLogin.setEnabled(true);
                    textLogin.setText("");
                    textSenha.setText("");
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

        Dialog dialog = new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Erro ao logar!")
                .setContentText("Usuário ou senha incorretos!")
                .setConfirmText("OK");

        progress.dismissWithAnimation();
        dialog.show();
        buttonLogin.setEnabled(true);
        textSenha.setText("");

    }

    @Override
    protected void onRestart() {
        progress.dismiss();
        super.onRestart();
    }
}
