package com.example.aluno.ioasys;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {

    private EditText textLogin;
    private EditText textSenha;
    private Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textLogin = findViewById(R.id.txtLogin);
        textSenha = findViewById(R.id.txtSenha);
        buttonLogin = findViewById(R.id.btnLogin);
    }
}
