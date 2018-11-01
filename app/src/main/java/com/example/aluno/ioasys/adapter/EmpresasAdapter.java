package com.example.aluno.ioasys.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.aluno.ioasys.R;
import com.example.aluno.ioasys.entity.Empresas;
import com.example.aluno.ioasys.service.IoasysService;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EmpresasAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private TextView nomeEmpresa;
    private TextView descricaoEmpresa;
    private TextView paisEmpresa;
    private ImageView imageEmpresa;
    private Context context;
    private List<Empresas> empresas;

    public EmpresasAdapter(Context context, List<Empresas> empresas){
        this.context = context;
        this.empresas = empresas;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return empresas.size();
    }

    @Override
    public Object getItem(int i) {
        return empresas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View itemView = view;
        itemView = (itemView == null) ? inflater.inflate(R.layout.model_text, null) : itemView;

        imageEmpresa = itemView.findViewById(R.id.imageEmpresa);
        nomeEmpresa = itemView.findViewById(R.id.nomeEmpresa);
        descricaoEmpresa = itemView.findViewById(R.id.tipoEmpresa);
        paisEmpresa = itemView.findViewById(R.id.paisEmpresa);

        nomeEmpresa.setText("Empresa " + (i+1));
        descricaoEmpresa.setText(empresas.get(i).description.substring(1, 15) + "...");
        paisEmpresa.setText(empresas.get(i).country);

        String url = IoasysService.BASE_URL + empresas.get(i).photo;
        Picasso.with(context).load(url).fit().into(imageEmpresa);

        return itemView;
    }
}
