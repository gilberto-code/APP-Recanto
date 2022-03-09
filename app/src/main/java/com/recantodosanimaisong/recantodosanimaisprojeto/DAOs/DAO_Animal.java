package com.recantodosanimaisong.recantodosanimaisprojeto.DAOs;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.recantodosanimaisong.recantodosanimaisprojeto.Adapter.AnimalAdapter;
import com.recantodosanimaisong.recantodosanimaisprojeto.Conexao.Links;
import com.recantodosanimaisong.recantodosanimaisprojeto.Model.Animal;
import com.recantodosanimaisong.recantodosanimaisprojeto.Model.Mysingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class DAO_Animal {
    private AnimalAdapter animalAdapter;
    final String KEY_IMAGE = "imagem";
    private Animal animal;
    final String KEY_ID = "id";

    public void enviarAnimal(final Animal animal , final Context context){
        final ProgressDialog loading = ProgressDialog.show(context,
                "Cadastrando animal...","Por favor espere um pouco...",false,false);
        StringRequest stringRequest = new StringRequest( Request.Method.POST, Links.ENVIO_ANIMAL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        /*Toast.makeText( getContext(), "Animal Cadastrado", Toast.LENGTH_SHORT ).show();*/
                        Log.i("loginho", response);
                        loading.dismiss();
                        uploadImage(animal.getImagem() , response , context);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        Toast.makeText( context, "Algo deu errado, favor verificar sua conexão", Toast.LENGTH_SHORT ).show();
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
                params.put("animal", gson.toJson(animal));
                Log.i("loginho",gson.toJson(animal));

                return params;
            }
        };
        Mysingleton.getmInstance( context ).addTpRequestque( stringRequest );
    }
    private void uploadImage(final String img, final String id , final Context context){
        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(context,
                "Enviando imagem...","Por favor espere um pouco...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Links.ENVIO_IMAGEM,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        loading.dismiss();
                        Toast.makeText(context, "Imagens Enviadas", Toast.LENGTH_SHORT).show();
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
                Map<String,String> params = new Hashtable<String, String>();
                params.put(KEY_IMAGE, img);
                params.put("id", id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        //Adding request to the queue
        requestQueue.add(stringRequest);
    }
    public void carregarLista(final RecyclerView recyclerView, final Context context ) {
        final ArrayList<Animal> animais = new ArrayList<Animal>();
        final ProgressDialog loading = ProgressDialog.show(context,
                "Carregando lista...","Espere um segundo...",true,false);
        try {
            StringRequest stringRequest =
                    new StringRequest(Request.Method.GET, Links.PEGAR_ANIMAIS, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("CARREGAR", response );
                    try {
                        Gson gson = new Gson();
                        JSONArray jsonArray = new JSONArray(response);
                        for (int a = 0; a < jsonArray.length(); a++) {
                            JSONObject js = jsonArray.getJSONObject(a);
                            animal = gson.fromJson(js.toString(), Animal.class);
                            animais.add(animal);
                        }
                        animalAdapter =  new AnimalAdapter( animais );
                        recyclerView.setAdapter(animalAdapter );
                        loading.dismiss();

                    }   catch (JSONException e) {
                        loading.dismiss();
                        Toast.makeText(context, "Erro no acesso ao Banco \n Contate o Administrador", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    loading.dismiss();
                    Toast.makeText(context, "Erro no acesso ao Banco \n Contate o Administrador", Toast.LENGTH_LONG).show();
                }
            });
            //Log.i("CARREGAR",animaisTemp.size()+"" );
            Mysingleton.getmInstance(context).addTpRequestque(stringRequest);
        }catch (Exception ex) {
            Toast.makeText(context, "Erro na chamada \n Contate o Administrador", Toast.LENGTH_SHORT).show();
        }
    }

    public AnimalAdapter getAnimalAdapter() {
        return animalAdapter;
    }

    public void setAnimalAdapter(AnimalAdapter animalAdapter) {
        this.animalAdapter = animalAdapter;
    }
}
