package com.recantodosanimaisong.recantodosanimaisprojeto.activitys;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.recantodosanimaisong.recantodosanimaisprojeto.Model.Adotante;
import com.recantodosanimaisong.recantodosanimaisprojeto.Model.Animal;
import com.recantodosanimaisong.recantodosanimaisprojeto.Model.Mysingleton;
import com.recantodosanimaisong.recantodosanimaisprojeto.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class CadastrarAdotante extends AppCompatActivity {

    private Adotante adotante;
    private EditText ed_nome;
    private EditText ed_telefone;
    private EditText ed_cpf;
    private EditText ed_data_nascimento;
    private EditText ed_endereco;
    private EditText ed_email;
    private ImageView imagem_do_adotante;
    private ImageButton btn_adiconar_imagem_camera;
    private ImageButton btn_adiconar_imagem;
    private ImageButton img_btn_remover;
    private Button btnCadastrarAdotante;

    private Bitmap bitmap;
    private Bitmap imagemAdotante;


    private String KEY_IMAGEP = "imagemPrin";

    private int PICK_IMAGE_REQUEST = 1;
    static final int REQUEST_IMAGE_CAPTURE = 2;
    private static final int MY_CAMERA_REQUEST_CODE = 100;

    private String URL = "http://200.18.128.55/gilberto/banco_ong/enviar_adotante.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_cadastrar_adotante );

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        ed_nome = findViewById( R.id.ed_nome);
        ed_telefone = findViewById( R.id.ed_telefone);
        ed_cpf = findViewById( R.id.ed_cpf);
        ed_data_nascimento = findViewById( R.id.ed_data_nascimento);
        ed_endereco = findViewById( R.id.ed_endereco);
        ed_email = findViewById( R.id.ed_email);
        imagem_do_adotante = findViewById( R.id.imagem_do_adotante);
        btn_adiconar_imagem_camera = findViewById( R.id.btn_adiconar_imagem_camera);
        btn_adiconar_imagem = findViewById( R.id.btn_adiconar_imagem);
        img_btn_remover = findViewById( R.id.img_btn_remover);
        btnCadastrarAdotante = findViewById( R.id.btnCadastrarAdotante);

        btn_adiconar_imagem_camera.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               pedir_permissao();
            }
        } );
        btn_adiconar_imagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });
        btnCadastrarAdotante.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Adotante adotante = coletar_informacao();
               enviarAdotante( adotante );
            }
        } );
        img_btn_remover.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagem_do_adotante.setImageBitmap(null);
            }
        } );

    }
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Selecione uma Imagem"), PICK_IMAGE_REQUEST);
    }

    public void enviarAdotante(final Adotante adotante){
        final ProgressDialog loading = ProgressDialog.show(CadastrarAdotante.this,
                "Cadastrando adotante...","Por favor espere um pouco...",false,false);

        StringRequest stringRequest = new StringRequest( Request.Method.POST, URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        Toast.makeText( getApplicationContext(), "Adotante Cadastrado", Toast.LENGTH_SHORT ).show();
                        //Toast.makeText( getApplicationContext(), response, Toast.LENGTH_SHORT ).show();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText( getApplicationContext(),"Erro", Toast.LENGTH_SHORT ).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                Gson gson = new GsonBuilder()
                        .disableHtmlEscaping()
                        .setFieldNamingPolicy( FieldNamingPolicy.UPPER_CAMEL_CASE)
                        .setPrettyPrinting()
                        .serializeNulls()
                        .create();
                Log.i("oque sera" ,gson.toJson(adotante));
                params.put("adotante", gson.toJson(adotante));
                return params;
            }
        };
        Mysingleton.getmInstance( getApplicationContext() ).addTpRequestque( stringRequest );
    }

    public Adotante coletar_informacao(){
        String nome;
        String telefone;
        String CPF;
        String data_nascimento;
        String endereco;
        String email;
        String foto_momento;

        nome = ed_nome.getText().toString();
        telefone = ed_telefone.getText().toString();
        CPF = ed_cpf.getText().toString();
        data_nascimento = ed_data_nascimento.getText().toString();
        endereco = ed_endereco.getText().toString();
        email = ed_email.getText().toString();
        foto_momento = getStringImage(imagemAdotante);
        adotante= new Adotante( nome , telefone ,CPF,data_nascimento, endereco, email, foto_momento );
        return adotante;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                imagem_do_adotante.setImageBitmap(bitmap);
                imagemAdotante = bitmap;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imagem_do_adotante.setImageBitmap(imageBitmap);
            imagemAdotante = imageBitmap;
        }
    }
    private void dispatchTakePictureIntent(Context context) {
        Intent takePictureIntent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    public void pedir_permissao (){
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions( this, new String[] {Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
        }
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