package com.example.aluno.ioasys.dao;

import com.example.aluno.ioasys.entity.Empresas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danih on 01/11/2018.
 */

public class EmpresasDAO {

    public List<Empresas> parseJsonEmpresas(String json){
        List<Empresas> empresas = new ArrayList<>();
        try {

            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("enterprises");
            JSONObject objEmpresa;

            for(int i = 0; i < jsonArray.length(); i++){
                objEmpresa = new JSONObject(jsonArray.getString(i));
                Empresas empresa = new Empresas();
                empresa.setId(Integer.parseInt(objEmpresa.getString("id")));
                empresa.setEnterprise_name(objEmpresa.getString("enterprise_name"));
                empresa.setCountry(objEmpresa.getString("country"));
                if(objEmpresa.has("photo")){
                    empresa.setPhoto(objEmpresa.getString("photo"));
                }
                empresa.setDescription(objEmpresa.getString("description"));
                empresa.setCity(objEmpresa.getString("city"));
                String type = objEmpresa.getJSONObject("enterprise_type")
                        .getString("enterprise_type_name");
                empresa.setEnterprise_type(type);
                empresas.add(empresa);
            }
        } catch(JSONException e) {
            e.printStackTrace();
        }

        return empresas;

    }
}
