package com.recantodosanimaisong.recantodosanimaisprojeto.activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.recantodosanimaisong.recantodosanimaisprojeto.Conexao.Links;
import com.recantodosanimaisong.recantodosanimaisprojeto.DAOs.DAO_Usuario;
import com.recantodosanimaisong.recantodosanimaisprojeto.R;


import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class Abertura extends AppCompatActivity {

     ImageView imageAbertura;
     TextView textAbertura;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_abertura );

        imageAbertura = findViewById( R.id.imageAbertura );
        textAbertura = findViewById( R.id.textAbertura );

        final Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {

                Random r = new Random(  );
                int x = r.nextInt(7);

            }
        }, 2000);


        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences prefs = getSharedPreferences(Links.LOGIN_PREFERENCE, 0);
                String email= prefs.getString("email", null);

                if(email == null){
                    Intent i = new Intent(getApplicationContext(), TelaLogin.class);
                    startActivity(i);
                    finish();
                }else{
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        }, 0);

    }
}
