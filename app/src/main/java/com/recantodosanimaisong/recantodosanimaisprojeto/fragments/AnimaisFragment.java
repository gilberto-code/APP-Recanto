
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


    private DAO_Animal dao_animal = new DAO_Animal();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate( R.layout.fragment_animais, container, false );
        getActivity().setTitle("Animais para Ado????o");
        recyclerView = view.findViewById( R.id.recycler_animais );

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener( getContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
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

        carregarLista();
        return view;
    }
    public void carregarLista(){
        dao_animal.carregarLista(recyclerView , getContext());
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
                        try {
                            Gson gson = new Gson();
                            JSONArray jsonArray = new JSONArray(s);
                            for (int a = 0; a < jsonArray.length(); a++) {
                                JSONObject js = jsonArray.getJSONObject(a);
                                animal = gson.fromJson(js.toString(), Animal.class);
                                animais.add(animal);
                            }
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
    }
}


