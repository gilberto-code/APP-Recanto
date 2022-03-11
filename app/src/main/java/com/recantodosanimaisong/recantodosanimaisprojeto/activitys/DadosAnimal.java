package com.recantodosanimaisong.recantodosanimaisprojeto.activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.recantodosanimaisong.recantodosanimaisprojeto.Conexao.Links;
import com.recantodosanimaisong.recantodosanimaisprojeto.Model.Animal;
import com.recantodosanimaisong.recantodosanimaisprojeto.Model.Pedido_Adocao;
import com.recantodosanimaisong.recantodosanimaisprojeto.R;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class DadosAnimal extends AppCompatActivity {

    Animal animal;
     TextView text_nome;
     TextView text_especie;
     TextView text_idade;
     TextView text_raca;
     TextView text_cor;
     TextView text_porte;
     TextView text_sexo;
     TextView text_vacinado;
     TextView text_doencas;
     TextView text_castrado;
     TextView text_descricao;

     ImageView imagemAnimalDesc;
     Button btn_quero_adotar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_dados_animal );

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        text_nome= findViewById( R.id.textNomeAnimalDesc);
        text_especie= findViewById( R.id.especieAnimalDesc);
        text_idade = findViewById( R.id.idadeAnimalDesc);
        text_porte = findViewById( R.id.porteAnimalDesc);
        text_raca = findViewById( R.id.racaAnimalDesc);
        text_cor = findViewById( R.id.corAnimalDesc);
        text_sexo = findViewById( R.id.sexoAnimalDesc);
        text_castrado = findViewById( R.id.castradoAnimalDesc);
        text_vacinado = findViewById( R.id.vacinadoAnimalDesc);
        text_doencas = findViewById( R.id.doencasAnimalDesc);
        text_descricao = findViewById( R.id.descricaoAnimalDesc);
        imagemAnimalDesc = findViewById( R.id.imagemAnimalDesc);
        btn_quero_adotar = findViewById( R.id.btn_quero_adotar);

        Intent intent = getIntent( );
        animal = (Animal) intent.getSerializableExtra( "animal" );


        text_nome.setText( animal.getNome() );
        text_especie.setText( animal.getEspecie() );
        text_idade.setText( animal.getIdade()+"" );
        text_raca.setText( animal.getRaca());
        text_porte.setText( animal.getPorte());
        text_cor.setText( animal.getCor());
        text_sexo.setText( animal.getSexo());


        if(animal.getVacinado() == 1){
            text_vacinado.setText("Sim");
        }else{
            text_vacinado.setText("Não");
        }
        if(animal.getCastrado() ==1 ){
            text_castrado.setText("Sim");
        }else{
            text_castrado.setText("Não");
        }

        text_doencas.setText( animal.getQualDoenca());
        text_descricao.setText( animal.getDescricao());
        btn_quero_adotar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialogButtonClicked(animal);
            }
        });

        Glide.with(getApplicationContext()).load(StringToBitMap( animal.getImagem() )).into(imagemAnimalDesc);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }
    public Bitmap StringToBitMap(String image){
        byte [] encodeByte= Base64.decode(image,Base64.DEFAULT);
        InputStream inputStream  = new ByteArrayInputStream(encodeByte);
        Bitmap bitmap  = BitmapFactory.decodeStream(inputStream);
        return bitmap;
    }
    public void showAlertDialogButtonClicked(final Animal animal) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle( "Pedido Adoção" );

        builder.setMessage("Você deseja enviar um pedido de adoção ONG");

        builder.setPositiveButton( "Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences prefs = getSharedPreferences(Links.LOGIN_PREFERENCE, 0);
                int idUser = prefs.getInt("idUser", 0);
                Pedido_Adocao pedido_adocao = new Pedido_Adocao("Quero adotar",
                        animal.getIdAnimal() , idUser);
                envio_pedido(pedido_adocao);
            }
        } );
        builder.setNegativeButton( "Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        } );
        builder.show();
    }
    private void envio_pedido(final Pedido_Adocao pedido_adocao){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Links.ENVIAR_PEDIDO_ADOCAO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Toast.makeText(getApplicationContext(), "Pedido enviado com sucesso", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getApplicationContext(), volleyError.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                Gson gson = new GsonBuilder()
                        .disableHtmlEscaping()
                        .setFieldNamingPolicy( FieldNamingPolicy.UPPER_CAMEL_CASE)
                        .setPrettyPrinting()
                        .serializeNulls()
                        .create();
                params.put("pedido",  gson.toJson(pedido_adocao));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        //Adding request to the queue
        requestQueue.add(stringRequest);
    }
}
