package com.recantodosanimaisong.recantodosanimaisprojeto.DAOs;

import android.app.ProgressDialog;
import android.content.Context;
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

import java.util.HashMap;
import java.util.Map;

public class DAO_Usuario {
    private String URL_ENVIO_USUARIO = "http://200.18.128.55/gilberto/banco_ong/enviar_usuario.php";

    public void envio_usuario(final Usuario usuario, final Context context){
        final ProgressDialog loading = ProgressDialog.show(context,"Cadastrando Usuário...",
                "Por favor espere um pouco...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ENVIO_USUARIO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        loading.dismiss();
                        Toast.makeText(context, "Usuário Cadastrado", Toast.LENGTH_SHORT).show();
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
                Gson gson = new GsonBuilder()
                        .disableHtmlEscaping()
                        .setFieldNamingPolicy( FieldNamingPolicy.UPPER_CAMEL_CASE)
                        .setPrettyPrinting()
                        .serializeNulls()
                        .create();
                params.put("usuario",  gson.toJson(usuario));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        //Adding request to the queue
        requestQueue.add(stringRequest);
    }
}
