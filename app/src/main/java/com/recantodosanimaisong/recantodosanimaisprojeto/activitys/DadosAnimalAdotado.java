package com.recantodosanimaisong.recantodosanimaisprojeto.activitys;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.recantodosanimaisong.recantodosanimaisprojeto.Model.Animal;
import com.recantodosanimaisong.recantodosanimaisprojeto.Model.Animal_Adotado;
import com.recantodosanimaisong.recantodosanimaisprojeto.R;

import java.io.ByteArrayInputStream;
import java.io.InputStream;


public class DadosAnimalAdotado extends AppCompatActivity {

    private Animal_Adotado animal_adotado;
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
    private ImageView imagemAnimalDesc;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_animal_adotado);


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


        Intent intent = getIntent( );
        animal_adotado = (Animal_Adotado) intent.getSerializableExtra( "animal_adotado" );


        text_nome.setText( animal_adotado.getNome() );
        text_especie.setText( animal_adotado.getEspecie() );
        text_idade.setText( animal_adotado.getIdade()+"" );
        text_raca.setText( animal_adotado.getRaca());
        text_porte.setText( animal_adotado.getPorte());
        text_cor.setText( animal_adotado.getCor());
        text_sexo.setText( animal_adotado.getSexo());
        if(animal_adotado.getPrenha() == 1){
            text_prenha.setText("Sim");
        }else{
            text_prenha.setText("Não");
        }
        text_temperamento.setText( animal_adotado.getTemperamento());
        text_bairro_encontrado.setText( animal_adotado.getBairroEncontrado());
        if(animal_adotado.getVacinado() == 1){
            text_vacinado.setText("Sim");
        }else{
            text_vacinado.setText("Não");
        }
        if(animal_adotado.getCastrado() ==1 ){
            text_castrado.setText("Sim");
        }else{
            text_castrado.setText("Não");
        }
        if(animal_adotado.getAcidentado() == 1){
            text_acidentado.setText("Sim");
        }else{
            text_acidentado.setText("Não");
        }
        text_doencas.setText( animal_adotado.getQualDoenca());
        text_acidentado.setText( animal_adotado.getQualAcidente());
        text_nome_contato.setText( animal_adotado.getNomeContato());
        text_telefone.setText( animal_adotado.getTelefoneContato());
        text_descricao.setText( animal_adotado.getDescricao());


        Glide.with(getApplicationContext()).load(StringToBitMap( animal_adotado.getImagem() )).into(imagemAnimalDesc);
    }
    public Bitmap StringToBitMap(String image){
        byte [] encodeByte= Base64.decode(image,Base64.DEFAULT);
        InputStream inputStream  = new ByteArrayInputStream(encodeByte);
        Bitmap bitmap  = BitmapFactory.decodeStream(inputStream);
        return bitmap;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }
}
