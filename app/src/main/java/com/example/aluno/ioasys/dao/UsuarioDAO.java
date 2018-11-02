package com.example.aluno.ioasys.dao;

import android.content.Context;
import android.util.Log;
import org.json.JSONStringer;

public class UsuarioDAO {

    public Context context;

    public UsuarioDAO(Context context){
        this.context = context;
    }

//    public String convertUsuarioToJson(String email, String senha){
//        String result;
//        try{
//
//            JSONStringer jsonStringer = new JSONStringer().array();
//            jsonStringer.object().key("email").value(email)
//                        .key("senha").value(senha).endObject();
//            jsonStringer.endArray();
//            Log.i("JSONSTRING", jsonStringer.toString());
//
//            result = jsonStringer.toString();
//
//        } catch(Exception e) {
//            e.printStackTrace();
//            result = null;
//        }
//
//        return result;
//    }
}
