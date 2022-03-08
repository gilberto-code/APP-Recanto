package com.recantodosanimaisong.recantodosanimaisprojeto.fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Meus_Dados#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Meus_Dados extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private int PICK_IMAGE_REQUEST = 1;
    static final int REQUEST_IMAGE_CAPTURE = 2;
    private static final int MY_CAMERA_REQUEST_CODE = 100;


    public Meus_Dados() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Meus_Dados.
     */
    // TODO: Rename and change types and number of parameters
    public static Meus_Dados newInstance(String param1, String param2) {
        Meus_Dados fragment = new Meus_Dados();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

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
    private Button btnAtualizarUsuario;

    private Bitmap bitmap;
    private Bitmap imagemUsuario;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meus__dados, container, false);


        ed_nome = view.findViewById( R.id.ed_nome);
        ed_telefone = view.findViewById( R.id.ed_telefone);
        ed_cpf = view.findViewById( R.id.ed_cpf);
        ed_endereco = view.findViewById( R.id.ed_endereco);
        ed_email = view.findViewById( R.id.ed_email);
        ed_senha = view.findViewById( R.id.ed_senha);
        imagem_do_usuario = view.findViewById( R.id.imagem_do_usuario);
        btn_adiconar_imagem_camera = view.findViewById( R.id.btn_adiconar_imagem_camera);
        btn_adiconar_imagem = view.findViewById( R.id.btn_adiconar_imagem);
        img_btn_remover = view.findViewById( R.id.img_btn_remover);
        btnAtualizarUsuario = view.findViewById( R.id.btnAtualizarUsuario);

        btnAtualizarUsuario.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Opa bão?", Toast.LENGTH_SHORT).show();
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
                imagem_do_usuario.setImageBitmap(null);
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

        byte[] imagem = StringByte( usuario.getFoto_momento() );
        Glide.with(getContext()).load(imagem).into(imagem_do_usuario);
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
                                Log.i("aff", u.toString());
                                preencher_campos(u);
                                break;
                            }
                        } catch (Exception  e) {
                            //e.printStackTrace();
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
                Log.i("oque sera" ,gson.toJson(usuario));
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
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            return encodedImage;
        }catch (Exception e){
            Toast.makeText(getContext(), "Ocorreram erros durante o processo", Toast.LENGTH_SHORT).show();
        }
        return "";
    }

    public byte[] StringByte(String image){
        if(image!=null){
            byte [] encodeByte= Base64.decode(image,Base64.DEFAULT);
            InputStream inputStream  = new ByteArrayInputStream(encodeByte);
            return encodeByte;
        }else{
            return null;
        }
    }
}