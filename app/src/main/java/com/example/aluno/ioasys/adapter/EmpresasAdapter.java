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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EmpresasAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private TextView nomeEmpresa;
    private TextView tipoEmpresa;
    private TextView paisEmpresa;
    private ImageView imageEmpresa;
    private Context context;
    private List<Empresas> empresas;
    private ArrayList<Empresas> listEmpresas;

    public EmpresasAdapter(Context context, List<Empresas> empresas){
        this.context = context;
        this.empresas = empresas;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.listEmpresas = new ArrayList<>();
        this.listEmpresas.addAll(empresas);
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
        tipoEmpresa = itemView.findViewById(R.id.tipoEmpresa);
        paisEmpresa = itemView.findViewById(R.id.paisEmpresa);

        nomeEmpresa.setText(empresas.get(i).getEnterprise_name());
        tipoEmpresa.setText(empresas.get(i).getEnterprise_type());
        paisEmpresa.setText(empresas.get(i).getCountry() + " - "
                                + empresas.get(i).getCity());

        String url = IoasysService.BASE_URL + empresas.get(i).getPhoto();
        Picasso.with(context).load(url).fit().into(imageEmpresa);

        return itemView;
    }

    public void filtro(String charSequence){

        charSequence = charSequence.toLowerCase(Locale.getDefault());
        empresas.clear();

        if (charSequence.length() == 0){
            empresas.addAll(listEmpresas);
        } else {
            for(Empresas empresa : listEmpresas){
                if(empresa.getEnterprise_name().toLowerCase(Locale.getDefault())
                        .contains(charSequence)){
                    empresas.add(empresa);
                }
            }
        }
        notifyDataSetChanged();

    }
}
