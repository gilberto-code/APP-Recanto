package com.recantodosanimaisong.recantodosanimaisprojeto.activitys;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.recantodosanimaisong.recantodosanimaisprojeto.Conexao.Links;
import com.recantodosanimaisong.recantodosanimaisprojeto.DAOs.DAO_Usuario;
import com.recantodosanimaisong.recantodosanimaisprojeto.R;

import java.util.HashMap;
import java.util.Map;

public class TelaLogin extends AppCompatActivity {

    private Button buttonLogin;
    private EditText ed_email;
    private EditText ed_password;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_login);

        ed_email = findViewById( R.id.ed_email_login );
        ed_password = findViewById( R.id.ed_password_login );
        buttonLogin = findViewById(R.id.btn_login_login);

        context= this;

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = ed_email.getText().toString();
                final String senha =  ed_password.getText().toString();

                final ProgressDialog loading = ProgressDialog.show
                        (context,"Analisando dados","Por favor espere um pouco...",true,false);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Links.LOGIN,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String s) {
                                loading.dismiss();
                                Log.i("aff", email+senha);
                                if(Integer.parseInt(s) == 1){
                                    Toast.makeText(context, "Logado", Toast.LENGTH_SHORT).show();

                                    SharedPreferences.Editor editor =
                                            context.getSharedPreferences(Links.LOGIN_PREFERENCE, 0).edit();
                                    editor.putString("email", email);
                                    editor.putString("senha", senha );
                                    editor.commit();

                                    Intent i = new Intent(context ,MainActivity.class);
                                    context.startActivity(i);
                                    finish();
                                }else{
                                    Log.i("aff", "111"+email+senha);
                                    Toast.makeText(context, "Dados incorretos", Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                loading.dismiss();
                                Toast.makeText(context, volleyError.getMessage(), Toast.LENGTH_LONG).show();
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
                RequestQueue requestQueue = Volley.newRequestQueue(context);
                //Adding request to the queue
                requestQueue.add(stringRequest);
            }
        });
    }
}
