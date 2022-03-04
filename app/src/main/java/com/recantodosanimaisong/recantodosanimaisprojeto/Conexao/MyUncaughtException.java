package com.recantodosanimaisong.recantodosanimaisprojeto.Conexao;

import android.util.Log;

public class MyUncaughtException implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        Log.e("Erro","" + thread.getName() + "erro: "+throwable.getMessage(),throwable);
    }
}
