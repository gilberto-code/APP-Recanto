package com.recantodosanimaisong.recantodosanimaisprojeto.fragments;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.recantodosanimaisong.recantodosanimaisprojeto.Model.Usuario;
import com.recantodosanimaisong.recantodosanimaisprojeto.R;

import java.util.HashMap;
import java.util.Map;

public class TelaLogin extends AppCompatActivity {

    private Button buttonLogin;
    private EditText ed_email;
    private EditText ed_password;
    private String senha_login;
    private String PEGAR_DADOS = "http://200.18.128.55/gilberto/banco_ong/dados_login.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_login);

        ed_email = findViewById( R.id.ed_email_login );
        ed_password = findViewById( R.id.ed_password_login );
        buttonLogin = findViewById(R.id.btn_login_login);



        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(ed_email.getText().toString() ,
                        ed_password.getText().toString());
            }
        });

    }
    private void login(final String email ,final String senha){
        //Showing the progress dialog
//        final ProgressDialog loading = ProgressDialog.show(getApplicationContext(),"Analisando dados","Por favor espere um pouco...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, PEGAR_DADOS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        //loading.dismiss();
                        if(Integer.parseInt(s) == 1){
                            Toast.makeText(TelaLogin.this, "Logado", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(TelaLogin.this, "Dados incorretos", Toast.LENGTH_SHORT).show();
                        }
                     //Toast.makeText(getApplicationContext(), "Usuário Cadastrado", Toast.LENGTH_SHORT).show();
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
                params.put("email",email);
                params.put("senha",senha);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        //Adding request to the queue
        requestQueue.add(stringRequest);
    }
}
