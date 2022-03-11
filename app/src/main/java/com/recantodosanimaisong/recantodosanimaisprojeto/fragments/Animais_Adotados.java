package com.recantodosanimaisong.recantodosanimaisprojeto.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.recantodosanimaisong.recantodosanimaisprojeto.Adapter.AnimalAdapter;
import com.recantodosanimaisong.recantodosanimaisprojeto.Adapter.AnimalAdotadoAdapter;
import com.recantodosanimaisong.recantodosanimaisprojeto.Adapter.RecyclerItemClickListener;
import com.recantodosanimaisong.recantodosanimaisprojeto.Conexao.Links;
import com.recantodosanimaisong.recantodosanimaisprojeto.Model.Animal;
import com.recantodosanimaisong.recantodosanimaisprojeto.Model.Animal_Adotado;
import com.recantodosanimaisong.recantodosanimaisprojeto.Model.Mysingleton;
import com.recantodosanimaisong.recantodosanimaisprojeto.R;
import com.recantodosanimaisong.recantodosanimaisprojeto.activitys.DadosAnimal;
import com.recantodosanimaisong.recantodosanimaisprojeto.activitys.DadosAnimalAdotado;

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
public class Animais_Adotados extends Fragment {


    public Animais_Adotados() {
        // Required empty public constructor
    }

    private RecyclerView recyclerView;
    private ArrayList<Animal> animais = new ArrayList<Animal>(  );
    private Animal_Adotado animal;
    private AnimalAdotadoAdapter animalAdotadoAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_animais__adotados, container, false );
        recyclerView = view.findViewById( R.id.recycler_animais_adotados );
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addOnItemTouchListener( new RecyclerItemClickListener( getContext()
                , recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //Animal animal = animalAdapter.getItem( position );
                Animal_Adotado animal = (Animal_Adotado) animalAdotadoAdapter.getItem( position );
                Intent i = new Intent(getContext(), DadosAnimalAdotado.class);
                i.putExtra("animal_adotado" , animal);
                startActivity(i);

                Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        } ) );
        carregarLista();
        //Log.i( "CARREGAR" ,animais.size()+"size" );
        //Log.i( "CARREGAR", animais.size()+"" );
        return view;
    }
    public void carregarLista(){
        final ProgressDialog loading = ProgressDialog.show(getContext(),
                "Carregando lista...","Espere um segundo...",true,false);
        try {
            StringRequest stringRequest = new StringRequest( Request.Method.GET, Links.PEGAR_ANIMAIS_ADOTADOS, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("supertag","A resposta foi "+response);
                    try {
                        Gson gson = new Gson();
                        JSONArray jsonArray = new JSONArray( response );
                        Log.i( "CARREGAR" , jsonArray.length()+"");
                        for(int a=0;a < jsonArray.length();a++){
                            JSONObject js = jsonArray.getJSONObject(a);
                            animal = gson.fromJson( js.toString() , Animal_Adotado.class );
                            animais.add( animal );
                        }
                        animalAdotadoAdapter =  new AnimalAdotadoAdapter( animais );
                        recyclerView.setAdapter(animalAdotadoAdapter );
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
                    params.put("CHAVE", "ghgfh");
                    return params;
                }
            };
            //Log.i("CARREGAR",animaisTemp.size()+"" );
            Mysingleton.getmInstance( getContext() ).addTpRequestque( stringRequest );
        }catch(Exception ex){
            Toast.makeText( getContext(), "Erro na chamada \n Contate o Administrador", Toast.LENGTH_SHORT ).show();
            loading.dismiss();
        }
    }

    public Bitmap StringToBitMap(String image){
        byte [] encodeByte= Base64.decode(image,Base64.DEFAULT);
        InputStream inputStream  = new ByteArrayInputStream(encodeByte);
        Bitmap bitmap  = BitmapFactory.decodeStream(inputStream);
        return bitmap;
    }


}
