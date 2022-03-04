package com.recantodosanimaisong.recantodosanimaisprojeto.activitys;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.recantodosanimaisong.recantodosanimaisprojeto.Adapter.AdotanteAdapter;
import com.recantodosanimaisong.recantodosanimaisprojeto.Adapter.RecyclerItemClickListener;
import com.recantodosanimaisong.recantodosanimaisprojeto.Conexao.BaseAppCompatActivity;
import com.recantodosanimaisong.recantodosanimaisprojeto.Model.Adotante;
import com.recantodosanimaisong.recantodosanimaisprojeto.Model.Animal;
import com.recantodosanimaisong.recantodosanimaisprojeto.Model.Mysingleton;
import com.recantodosanimaisong.recantodosanimaisprojeto.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdotarActivity extends AppCompatActivity {

    private LinearLayout layout_adotante;


    private RecyclerView recyclerView;
    private ArrayList<Adotante> adotantes = new ArrayList<Adotante>(  );
    private Adotante adotante;
    private AdotanteAdapter adotanteAdapter;
    private Button buttonCadastrarAdotante;
    private Animal animal;
    private String pegar_adotantes = "http://200.18.128.55/gilberto/banco_ong/pegar_adotantes.php";
    private String adotar_animal = "http://200.18.128.55/gilberto/banco_ong/adotar_animal.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_adotar );

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        Intent intent = getIntent();
        this.animal = (Animal) intent.getSerializableExtra( "animal" );
        //Toast.makeText( this, animal.getId()+"", Toast.LENGTH_SHORT ).show();

        buttonCadastrarAdotante = findViewById( R.id.buttonCadastrarAdotante );
        buttonCadastrarAdotante.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), CadastrarAdotante.class);
                startActivity(i);
            }
        } );


        recyclerView = findViewById( R.id.recyclerAdotantes );
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        recyclerView.addOnItemTouchListener( new RecyclerItemClickListener( AdotarActivity.this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //Animal animal = animalAdapter.getItem( position );

                Adotante adotante = adotanteAdapter.getItem( position );
                showAlertDialogButtonClicked(adotante ,animal );
                //Intent i = new Intent(AdotarActivity.this, DadosAnimal.class);
                //Toast.makeText( AdotarActivity.this, adotante.toString(), Toast.LENGTH_SHORT ).show();
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        } ) );
        carregarLista();
        //layout_adotante = findViewById( R.id.layoutAdotante);

    }
    public void carregarLista(){
        final ProgressDialog loading = ProgressDialog.show(AdotarActivity.this,
                "Carregando lista...","Espere um segundo...",true,false);
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, pegar_adotantes, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("supertag","A resposta foi "+response);
                    try {
                        Gson gson = new Gson();
                        JSONArray jsonArray = new JSONArray( response );
                        Log.i( "CARREGAR" , jsonArray.length()+"");
                        for(int a=0;a < jsonArray.length();a++){
                            JSONObject js = jsonArray.getJSONObject(a);
                            adotante = gson.fromJson( js.toString() , Adotante.class );
                            Log.i( "quem" , adotante.toString() );
                            adotantes.add( adotante );
                        }
                        adotanteAdapter =  new AdotanteAdapter( adotantes , AdotarActivity.this );
                        recyclerView.setAdapter(adotanteAdapter );
                        loading.dismiss();
                    } catch (JSONException e) {
                        loading.dismiss();
                        ///Toast.makeText( getApplicationContext(), "Erro no acesso ao Banco \n Contate o Administrador", Toast.LENGTH_LONG ).show();
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText( getApplicationContext(), "Erro no acesso ao Banco \n Contate o Administrador", Toast.LENGTH_LONG ).show();
                    loading.dismiss();
                }
            } );
            //Log.i("CARREGAR",animaisTemp.size()+"" );
            Mysingleton.getmInstance( getApplicationContext() ).addTpRequestque( stringRequest );
        }catch(Exception ex){
            Toast.makeText( getApplicationContext(), "Erro na chamada \n Contate o Administrador", Toast.LENGTH_SHORT ).show();
            loading.dismiss();
        }
    }

    public Bitmap StringToBitMap(String image){
        byte [] encodeByte= Base64.decode(image,Base64.DEFAULT);
        InputStream inputStream  = new ByteArrayInputStream(encodeByte);
        Bitmap bitmap  = BitmapFactory.decodeStream(inputStream);
        return bitmap;
    }
    public void showAlertDialogButtonClicked(final Adotante adotante , final Animal animal) {

        // ProgressDialog loading = ProgressDialog.show(getApplicationContext(),
         //       "Carregando lista...","Espere um segundo...",true,false);
       // loading.dismiss();


        LayoutInflater inflater = getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.layout_alert_custumer, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialoglayout);
        builder.setTitle( "Adotante selecionado" );

        TextView textNome = dialoglayout.findViewById( R.id.textNomeAdotante );
        TextView textCPF = dialoglayout.findViewById( R.id.textCPFAdotante );
        TextView textTelefone = dialoglayout.findViewById( R.id.textTelefoneAdotante );
        TextView textData = dialoglayout.findViewById( R.id.textDataAdotante );
        TextView textEmail = dialoglayout.findViewById( R.id.textEmailAdotante );
        ImageView imagem = dialoglayout.findViewById( R.id.imagemAdotante );

        textNome.setText( adotante.getNome() );
        textCPF.setText( adotante.getCPF() );
        textTelefone.setText( adotante.getTelefone() );
        textData.setText( adotante.getData_nascimento() );
        textEmail.setText( adotante.getEmail() );
        imagem.setImageBitmap(StringToBitMap(adotante.getFoto_momento()));

        builder.setPositiveButton( "Confirmar adoção", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText( AdotarActivity.this, "O animal foi adotado", Toast.LENGTH_SHORT ).show();
                //loading.show();
                final ProgressDialog loading = ProgressDialog.show(AdotarActivity.this,
                              "Carregando lista...","Espere um segundo...",true,false);
                try {
                    final StringRequest stringRequest = new StringRequest( Request.Method.POST, adotar_animal, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //Toast.makeText( AdotarActivity.this, "Animal adotado com sucesso", Toast.LENGTH_SHORT ).show();
                            loading.dismiss();
                            Toast.makeText( AdotarActivity.this, response, Toast.LENGTH_SHORT ).show();

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            loading.dismiss();
                            Toast.makeText( getApplicationContext(),
                                    "Erro no acesso ao Banco \n" +
                                            "Contate o Administrador", Toast.LENGTH_LONG ).show();
                            Toast.makeText( getApplicationContext(),
                                    error.getMessage(), Toast.LENGTH_LONG ).show();

                        }
                    } ){
                        @Override
                        protected Map<String, String> getParams()
                        {
                            Map<String, String>  params = new HashMap<String, String>();
                            //Log.i("oque sera" ,gson.toJson(adotante));
                            params.put("idAnimal", animal.getIdAnimal()+"");
                            //Toast.makeText( AdotarActivity.this, "entoa"+animal.getIdAnimal(), Toast.LENGTH_SHORT ).show();
                            Log.i("blzz",animal.getIdAnimal()+"a");
                            Log.i("blzz",adotante.getId()+"b");
                            params.put("idAdotante", adotante.getId()+"");
                            return params;
                        }
                    };
                    Mysingleton.getmInstance( getApplicationContext() ).addTpRequestque( stringRequest );
                }catch(Exception ex){

                }
            }
        } );
        builder.setNegativeButton( "Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        } );
        builder.show();
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
