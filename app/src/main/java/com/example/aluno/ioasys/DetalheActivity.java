package com.example.aluno.ioasys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aluno.ioasys.service.IoasysService;
import com.squareup.picasso.Picasso;

public class DetalheActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView imageDetalheEmpresa;
    private TextView detalheEmpresa;
    private String descricao;
    private String imageLink = "";
    private String nomeEmpresa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);

        imageDetalheEmpresa = findViewById(R.id.imageDetalheEmpresa);
        detalheEmpresa = findViewById(R.id.textDescricaoEmpresa);

        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        Bundle b = getIntent().getExtras();
        nomeEmpresa = b.getString("nomeEmpresa");
        descricao = b.getString("descricao");
        imageLink = b.getString("imageLink");

        getSupportActionBar().setTitle(nomeEmpresa);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if(imageLink == null || imageLink.equalsIgnoreCase("null")){
            imageLink = getResources().getString(R.string.linkNoIMage);
        } else {
            imageLink = IoasysService.BASE_URL + imageLink;
        }

        Picasso.with(DetalheActivity.this)
                .load(imageLink)
                .fit()
                .into(imageDetalheEmpresa);

        detalheEmpresa.setText(descricao);

    }
}
