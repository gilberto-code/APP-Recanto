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
import com.recantodosanimaisong.recantodosanimaisprojeto.Conexao.Links;
import com.recantodosanimaisong.recantodosanimaisprojeto.Model.Usuario;
import com.recantodosanimaisong.recantodosanimaisprojeto.Model.Animal;
import com.recantodosanimaisong.recantodosanimaisprojeto.Model.Mysingleton;
import com.recantodosanimaisong.recantodosanimaisprojeto.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class CadastrarUsuario extends AppCompatActivity {

    private Usuario usuario;
    private EditText ed_nome;
    private EditText ed_telefone;
    private EditText ed_cpf;
    private EditText ed_endereco;
    private EditText ed_email;
    private EditText ed_senha;
    private ImageView imagem_do_usuario;
    private ImageButton btn_adiconar_imagem_camera;
    private ImageButton btn_adiconar_imagem;
    private ImageButton img_btn_remover;
    private Button btnCadastrarUsuario;

    private Bitmap bitmap;
    private Bitmap imagemUsuario;


    private String KEY_IMAGEP = "imagemPrin";

    private int PICK_IMAGE_REQUEST = 1;
    static final int REQUEST_IMAGE_CAPTURE = 2;
    private static final int MY_CAMERA_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_cadastrar_usuario );

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
        ed_endereco = findViewById( R.id.ed_endereco);
        ed_email = findViewById( R.id.ed_email);
        ed_senha = findViewById( R.id.ed_senha);
        imagem_do_usuario = findViewById( R.id.imagem_do_usuario);
        btn_adiconar_imagem_camera = findViewById( R.id.btn_adiconar_imagem_camera);
        btn_adiconar_imagem = findViewById( R.id.btn_adiconar_imagem);
        img_btn_remover = findViewById( R.id.img_btn_remover);
        btnCadastrarUsuario = findViewById( R.id.btnCadastrarUsuario);

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
        btnCadastrarUsuario.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Usuario usuario = coletar_informacao();
               enviarUsuario( usuario );
            }
        } );
        img_btn_remover.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagem_do_usuario.setImageBitmap(null);
            }
        } );

    }
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Selecione uma Imagem"), PICK_IMAGE_REQUEST);
    }

    public void enviarUsuario(final Usuario usuario){
        final ProgressDialog loading = ProgressDialog.show(CadastrarUsuario.this,
                "Cadastrando usuario...","Por favor espere um pouco...",false,false);

        StringRequest stringRequest = new StringRequest( Request.Method.POST, Links.CADASTRO_USER,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        Log.i("aff",response);
                        if(response.contains("Duplicate entry")){
                            Toast.makeText( getApplicationContext(),
                                    "Falha - Email já Cadastrado", Toast.LENGTH_LONG ).show();
                        }else if(response=="1"){
                            Toast.makeText( getApplicationContext(),
                                    "Cadastro Realizado", Toast.LENGTH_SHORT ).show();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText( getApplicationContext(),"Conexão com o Servidor Falhou", Toast.LENGTH_SHORT ).show();
                        loading.dismiss();
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
                Log.i("oque sera" ,gson.toJson(usuario));
                params.put("usuario", gson.toJson(usuario));
                return params;
            }
        };
        Mysingleton.getmInstance( getApplicationContext() ).addTpRequestque( stringRequest );
    }

    public Usuario coletar_informacao(){
        String nome;
        String telefone;
        String CPF;
        String endereco;
        String email;
        String foto_momento;
        String senha;

        nome = ed_nome.getText().toString();
        telefone = ed_telefone.getText().toString();
        CPF = ed_cpf.getText().toString();
        endereco = ed_endereco.getText().toString();
        email = ed_email.getText().toString();
        senha = ed_senha.getText().toString();
        foto_momento = getStringImage(imagemUsuario);
        usuario= new Usuario( nome , telefone ,CPF, endereco, email,senha, foto_momento );
        return usuario;
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
                imagem_do_usuario.setImageBitmap(bitmap);
                imagemUsuario = bitmap;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imagem_do_usuario.setImageBitmap(imageBitmap);
            imagemUsuario = imageBitmap;
        }
    }
    private void dispatchTakePictureIntent(Context context) {
        Intent takePictureIntent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    public String getStringImage(Bitmap bmp){
       try {
           ByteArrayOutputStream baos = new ByteArrayOutputStream();
           bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
           byte[] imageBytes = baos.toByteArray();
           String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
           return encodedImage;
       }catch (Exception e){
           //Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
       }
       return "";
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
