package com.recantodosanimaisong.recantodosanimaisprojeto.activitys;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.recantodosanimaisong.recantodosanimaisprojeto.R;


import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class Abertura extends AppCompatActivity {

    //private FirebaseAuth authusuario = FirebaseAuth.getInstance();
    private ImageView imageAbertura;
    private TextView textAbertura;
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

                int img = 0 ;
                img = R.drawable.a;



            }
        }, 2500);


        final boolean usuarioCadastrado = true;
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(usuarioCadastrado){

                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    // Fecha esta activity
                    finish();
                }else{


                    //Intent i = new Intent(getApplicationContext(), Login.class);
                    //startActivity(i);
                    // Fecha esta activity
                    //finish();

                }
            }
        }, 3500);

    }
}
