package com.example.aluno.ioasys.config;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by danih on 02/11/2018.
 */

public class Conexao {

    public static boolean verificaConexao(Context context) {

        boolean conectado = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        try{
            if (connectivityManager.getActiveNetworkInfo() != null
                    && connectivityManager.getActiveNetworkInfo().isAvailable()
                    && connectivityManager.getActiveNetworkInfo().isConnected()) {
                conectado = true;
            }
        } catch (Exception e){
            throw new RuntimeException(e);
        }

        return conectado;
    }

}
