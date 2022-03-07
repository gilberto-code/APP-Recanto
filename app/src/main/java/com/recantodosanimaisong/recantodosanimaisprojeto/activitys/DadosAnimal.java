package com.recantodosanimaisong.recantodosanimaisprojeto.activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
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
import com.recantodosanimaisong.recantodosanimaisprojeto.Model.Animal;
import com.recantodosanimaisong.recantodosanimaisprojeto.Model.Pedido_Adocao;
import com.recantodosanimaisong.recantodosanimaisprojeto.R;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class DadosAnimal extends AppCompatActivity {

    private Animal animal;
    private TextView text_nome;
    private TextView text_especie;
    private TextView text_idade;
    private TextView text_raca;
    private TextView text_cor;
    private TextView text_porte;
    private TextView text_sexo;
    private TextView text_prenha;
    private TextView text_temperamento;
    private TextView text_bairro_encontrado;
    private TextView text_vacinado;
    private TextView text_doencas;
    private TextView text_acidentado;
    private TextView text_nome_contato;
    private TextView text_telefone;
    private TextView text_castrado;
    private TextView text_descricao;

    private String ENVIAR_PEDIDO_ADOCAO = "http://200.18.128.55/gilberto/banco_ong/enviar_pedido_adocao.php";

    private ImageView imagemAnimalDesc;
    private Button adotarButton;
    private Button btn_quero_adotar;

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
        text_prenha = findViewById( R.id.prenhaAnimalDesc);
        text_temperamento = findViewById( R.id.temperamentoAnimalDesc);
        text_bairro_encontrado = findViewById( R.id.bairroAnimalDesc);
        text_vacinado = findViewById( R.id.vacinadoAnimalDesc);
        text_acidentado = findViewById( R.id.acidentadoAnimalDesc);
        text_doencas = findViewById( R.id.doencasAnimalDesc);
        text_nome_contato = findViewById( R.id.nomeContatoAnimalDesc);
        text_telefone = findViewById( R.id.telefoneAnimalDesc);
        text_descricao = findViewById( R.id.descricaoAnimalDesc);
        imagemAnimalDesc = findViewById( R.id.imagemAnimalDesc);
        adotarButton = findViewById( R.id.btn_adotar);
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
        if(animal.getPrenha() == 1){
            text_prenha.setText("Sim");
        }else{
            text_prenha.setText("Não");
        }
        text_temperamento.setText( animal.getTemperamento());
        text_bairro_encontrado.setText( animal.getBairroEncontrado());
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
        if(animal.getAcidentado() == 1){
            text_acidentado.setText("Sim");
        }else{
            text_acidentado.setText("Não");
        }
        text_doencas.setText( animal.getQualDoenca());
        text_acidentado.setText( animal.getQualAcidente());
        text_nome_contato.setText( animal.getNomeContato());
        text_telefone.setText( animal.getTelefoneContato());
        text_descricao.setText( animal.getDescricao());
        adotarButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(getApplicationContext(), AdotarActivity.class);
//                //i.putExtra("animal" , animal);
//                i.putExtra("animal" , animal);
//                startActivity(i);
            }
        } );
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

        // ProgressDialog loading = ProgressDialog.show(getApplicationContext(),
        //       "Carregando lista...","Espere um segundo...",true,false);
        // loading.dismiss();


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle( "Pedido_Adocao de adoção" );

        builder.setMessage("Você deseja enviar um pedido de adoção ONG");

        builder.setPositiveButton( "Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText( AdotarActivity.this, "O animal foi adotado", Toast.LENGTH_SHORT ).show();
                //loading.show();
                //Toast.makeText(DadosAnimal.this, "Oi", Toast.LENGTH_SHORT).show();
                Pedido_Adocao pedido_adocao = new Pedido_Adocao("uma desc qualquer", animal.getIdAnimal() ,4);
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
        //Showing the progress dialog
/*        final ProgressDialog loading = ProgressDialog.show(getApplicationContext(),
                "Enviando pedido...","Por favor espere um pouco...",false,false);*/
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ENVIAR_PEDIDO_ADOCAO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        //loading.dismiss();
                        Toast.makeText(getApplicationContext(), "Pedido enviado com sucesso", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //loading.dismiss();
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
