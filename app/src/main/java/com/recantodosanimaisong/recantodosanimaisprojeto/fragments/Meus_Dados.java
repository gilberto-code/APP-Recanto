package com.recantodosanimaisong.recantodosanimaisprojeto.fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.recantodosanimaisong.recantodosanimaisprojeto.Conexao.Links;
import com.recantodosanimaisong.recantodosanimaisprojeto.Model.Mysingleton;
import com.recantodosanimaisong.recantodosanimaisprojeto.Model.Usuario;
import com.recantodosanimaisong.recantodosanimaisprojeto.R;
import com.recantodosanimaisong.recantodosanimaisprojeto.activitys.CadastrarUsuario;
import com.recantodosanimaisong.recantodosanimaisprojeto.activitys.MainActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Meus_Dados extends Fragment {

    private int PICK_IMAGE_REQUEST = 1;
    static final int REQUEST_IMAGE_CAPTURE = 2;
    private static final int MY_CAMERA_REQUEST_CODE = 100;


    public Meus_Dados() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private int idUser;
    private EditText ed_nome;
    private EditText ed_telefone;
    private EditText ed_cpf;
    private EditText ed_endereco;
    private EditText ed_email;
    private EditText ed_senha;
    private ImageView imagem_do_usuario_up;
    private ImageButton btn_adiconar_imagem_camera;
    private ImageButton btn_adiconar_imagem;
    private ImageButton img_btn_remover;
    private Button btnAtualizarUsuario;

    private Bitmap bitmap;
    private Bitmap imagemUsuario;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meus__dados, container, false);
        getActivity().setTitle("Meus Dados");

        ed_nome = view.findViewById( R.id.ed_nome);
        ed_telefone = view.findViewById( R.id.ed_telefone);
        ed_cpf = view.findViewById( R.id.ed_cpf);
        ed_endereco = view.findViewById( R.id.ed_endereco);
        ed_email = view.findViewById( R.id.ed_email);
        ed_senha = view.findViewById( R.id.ed_senha);
        imagem_do_usuario_up = view.findViewById( R.id.imagem_do_usuario_up);
        btn_adiconar_imagem_camera = view.findViewById( R.id.btn_adiconar_imagem_camera);
        btn_adiconar_imagem = view.findViewById( R.id.btn_adiconar_imagem);
        img_btn_remover = view.findViewById( R.id.img_btn_remover);
        btnAtualizarUsuario = view.findViewById( R.id.btnAtualizarUsuario);

        btnAtualizarUsuario.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Usuario usuario = coletar_informacao();

                atualizarUsuario(usuario);
            }
        } );
        btn_adiconar_imagem_camera.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pedir_permissao();
                dispatchTakePictureIntent(getContext());
            }
        } );
        btn_adiconar_imagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pedir_permissao();
                showFileChooser();
            }
        });
        img_btn_remover.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagem_do_usuario_up.setImageBitmap(null);
            }
        } );

        SharedPreferences prefs = getActivity().getSharedPreferences(Links.LOGIN_PREFERENCE, 0);
        String email= prefs.getString("email", null);

        Usuario u = new Usuario(email);
        pegarDadosUsuario(u);

        return view;
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Selecione uma Imagem"), PICK_IMAGE_REQUEST);
    }
    public void preencher_campos(Usuario usuario){
        ed_nome.setText(usuario.getNome());
        ed_telefone.setText(usuario.getTelefone());
        ed_cpf.setText(usuario.getCpf());
        ed_endereco.setText(usuario.getEndereco());
        ed_email.setText(usuario.getEmail());
        ed_senha.setText(usuario.getSenha());
        imagem_do_usuario_up.setImageBitmap(StringBitmap(usuario.getFoto_momento()));
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

        Usuario usuario = new Usuario(idUser, nome , telefone ,CPF, endereco, email,senha, foto_momento );
        return usuario;
    }

    public void pegarDadosUsuario(final Usuario usuario){
        final ProgressDialog loading = ProgressDialog.show(getContext(),
                "Carregando...","Por favor espere um pouco...",false,false);

        StringRequest stringRequest = new StringRequest( Request.Method.POST, Links.PEGAR_USER,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        try {
                            Gson gson = new Gson();
                            JSONArray jsonArray = new JSONArray(response);
                            Usuario u;
                            for (int a = 0; a < jsonArray.length(); a++) {
                                JSONObject js = jsonArray.getJSONObject(a);
                                u = gson.fromJson(js.toString(), Usuario.class);
                                idUser = u.getIdUser();
                                preencher_campos(u);
                                break;
                            }
                        } catch (Exception  e) {
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText( getContext(),"Conexão com o Servidor Falhou", Toast.LENGTH_SHORT ).show();
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
                params.put("usuario", gson.toJson(usuario));
                return params;
            }
        };
        Mysingleton.getmInstance( getContext() ).addTpRequestque( stringRequest );
    }

    public void atualizarUsuario(final Usuario usuario){

        final ProgressDialog loading = ProgressDialog.show(getContext(),
                "Cadastrando usuario...","Por favor espere um pouco...",false,false);

        StringRequest stringRequest = new StringRequest( Request.Method.POST, Links.ATUALIZAR_USER,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText( getContext(),"Conexão com o Servidor Falhou", Toast.LENGTH_SHORT ).show();
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
                params.put("usuario", gson.toJson(usuario));
                return params;
            }
        };
        Mysingleton.getmInstance( getContext() ).addTpRequestque( stringRequest );
    }

    public void pedir_permissao (){
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions( getActivity(), new String[] {Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
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
            bmp.compress(Bitmap.CompressFormat.JPEG, 70, baos);
            byte[] imageBytes = baos.toByteArray();
            String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            return encodedImage;
        }catch (Exception e){
            Toast.makeText(getContext(), "Imagem não carregada", Toast.LENGTH_SHORT).show();
        }
        return "";
    }

    public Bitmap StringBitmap(String image){
        if(image!=null){
            byte [] encodeByte= Base64.decode(image,Base64.DEFAULT);
            InputStream inputStream  = new ByteArrayInputStream(encodeByte);
            Bitmap bmp = BitmapFactory.decodeStream(inputStream);
            return bmp;
        }else{
            return null;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == getActivity().RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                imagem_do_usuario_up.setImageBitmap(bitmap);
                imagemUsuario = bitmap;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imagem_do_usuario_up.setImageBitmap(imageBitmap);
            imagemUsuario = imageBitmap;
        }
    }
}