
package com.recantodosanimaisong.recantodosanimaisprojeto.fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.recantodosanimaisong.recantodosanimaisprojeto.Adapter.RecyclerItemClickListener;
import com.recantodosanimaisong.recantodosanimaisprojeto.DAOs.DAO_Animal;
import com.recantodosanimaisong.recantodosanimaisprojeto.Model.Animal;
import com.recantodosanimaisong.recantodosanimaisprojeto.R;
import com.recantodosanimaisong.recantodosanimaisprojeto.activitys.DadosAnimal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class AnimaisFragment extends Fragment {

    public AnimaisFragment() {}
    private RecyclerView recyclerView;
    private Animal animal;
    private Button btn_filtrar_animais;

    private DAO_Animal dao_animal = new DAO_Animal();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate( R.layout.fragment_animais, container, false );
        recyclerView = view.findViewById( R.id.recycler_animais );
        btn_filtrar_animais = view.findViewById( R.id.btn_filtrar_animais );


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener( getContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //Animal animal = animalAdapter.getItem( position );

                Animal animal = dao_animal.getAnimalAdapter().getItem( position );
                Intent i = new Intent(getContext(), DadosAnimal.class);
                Log.i("testando", animal.toString());
                i.putExtra("animal" , animal);
                startActivity(i);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        } ) );
        btn_filtrar_animais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialogButtonClicked();
            }
        });
        carregarLista();
        //Log.i( "CARREGAR" ,animais.size()+"size" );
        //Log.i( "CARREGAR", animais.size()+"" );
        return view;
    }
    public void carregarLista(){
        dao_animal.carregarLista(recyclerView , getContext());
        /*try {
            StringRequest stringRequest = new StringRequest( Request.Method.GET, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("supertag","A resposta foi "+response);
                    try {
                        Gson gson = new Gson();
                        JSONArray jsonArray = new JSONArray( response );
                        Log.i( "CARREGAR" , jsonArray.length()+"");
                        for(int a=0;a < jsonArray.length();a++){
                            JSONObject js = jsonArray.getJSONObject(a);
                            animal = gson.fromJson( js.toString() , Animal.class );
                            animais.add( animal );
                        }
                        animalAdapter =  new AnimalAdapter( animais );
                        recyclerView.setAdapter(animalAdapter );
                        loading.dismiss();
                    } catch (JSONException e) {
                        loading.dismiss();
                        Toast.makeText( getContext(), "Erro no acesso ao Banco \n Contate o Administrador", Toast.LENGTH_LONG ).show();
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText( getContext(), "Erro no acesso ao Banco \n Contate o Administrador", Toast.LENGTH_LONG ).show();
                    loading.dismiss();
                }
            } ){
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String>  params = new HashMap<String, String>();
                    params.put("ID", animal.getIdAnimal()+"");
                    return params;
                }
            };
            //Log.i("CARREGAR",animaisTemp.size()+"" );
            Mysingleton.getmInstance( getContext() ).addTpRequestque( stringRequest );
        }catch(Exception ex){
            Toast.makeText( getContext(), "Erro na chamada \n Contate o Administrador", Toast.LENGTH_SHORT ).show();
            loading.dismiss();
        }*/
    }

    public Bitmap StringToBitMap(String image){
        byte [] encodeByte= Base64.decode(image,Base64.DEFAULT);
        InputStream inputStream  = new ByteArrayInputStream(encodeByte);
        Bitmap bitmap  = BitmapFactory.decodeStream(inputStream);
        return bitmap;
    }
    public void showAlertDialogButtonClicked() {
        LayoutInflater inflater = getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.pesquisa_alert_custumer, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialoglayout);
        builder.setTitle( "Pesquisa" );

        final TextView txt_pesq_nome = dialoglayout.findViewById( R.id.text_pesq_nome );
        final TextView txt_pesq_idade = dialoglayout.findViewById( R.id.text_pesq_idade );

        builder.setPositiveButton( "Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nome ,idade;
                nome = txt_pesq_nome.getText().toString();
                idade = txt_pesq_idade.getText().toString();

                //Toast.makeText( AdotarActivity.this, "O animal foi adotado", Toast.LENGTH_SHORT ).show();
                //loading.show();
                //Toast.makeText(DadosAnimal.this, "Oi", Toast.LENGTH_SHORT).show();
                Toast.makeText(getContext(), nome, Toast.LENGTH_SHORT).show();
                pegar_animais_pesquisa(nome, idade);

            }
        } );
        builder.setNegativeButton( "Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        } );
        builder.show();
    }
    private void pegar_animais_pesquisa(final String nome , final String idade) {
        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(getContext(),
                "Carregando Lista...", "Por favor espere um pouco...", false, false);
        final ArrayList<Animal> animais = new ArrayList<Animal>();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "pesquisa",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.i("supertag", "A resposta foi " + s);
                        try {
                            Gson gson = new Gson();
                            JSONArray jsonArray = new JSONArray(s);
                            Log.i("CARREGAR", jsonArray.length() + "");
                            for (int a = 0; a < jsonArray.length(); a++) {
                                JSONObject js = jsonArray.getJSONObject(a);
                                animal = gson.fromJson(js.toString(), Animal.class);
                                animais.add(animal);
                            }
                            //animalAdapter = new AnimalAdapter(animais);
                            //recyclerView.setAdapter(animalAdapter);
                            loading.dismiss();
                        } catch (JSONException e) {
                            loading.dismiss();
                            Toast.makeText(getContext(), "Erro no acesso ao Banco \n Contate o Administrador", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        loading.dismiss();
                        Toast.makeText(getContext(), volleyError.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("nome", nome);
                params.put("idade", idade);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        //Adding request to the queue
        requestQueue.add(stringRequest);
    }
    @CallSuper
    public void onDetach() {
        super.onDetach();
        Log.i("fui", "legal");
    }
}


