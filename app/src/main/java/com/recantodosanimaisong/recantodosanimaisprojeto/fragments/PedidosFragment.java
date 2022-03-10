package com.recantodosanimaisong.recantodosanimaisprojeto.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.recantodosanimaisong.recantodosanimaisprojeto.Adapter.PedidoAdapter;
import com.recantodosanimaisong.recantodosanimaisprojeto.Adapter.RecyclerItemClickListener;
import com.recantodosanimaisong.recantodosanimaisprojeto.Conexao.Links;
import com.recantodosanimaisong.recantodosanimaisprojeto.DAOs.DAO_Animal;
import com.recantodosanimaisong.recantodosanimaisprojeto.Model.Animal;
import com.recantodosanimaisong.recantodosanimaisprojeto.Model.Mysingleton;
import com.recantodosanimaisong.recantodosanimaisprojeto.Model.Pedido_Adocao;
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
public class PedidosFragment extends Fragment {


    public PedidosFragment() {
        // Required empty public constructor
    }

    private RecyclerView recyclerPedido;
    private ArrayList<Pedido_Adocao> pedidoAdocaos = new ArrayList<Pedido_Adocao>(  );
    private Pedido_Adocao pedidoAdocao;
    private PedidoAdapter pedidoAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pedidos, container, false);

        recyclerPedido = view.findViewById(R.id.recyclerPedido);
        recyclerPedido.setLayoutManager(new LinearLayoutManager(getContext()));

        final DAO_Animal dao_animal =  new DAO_Animal();

        recyclerPedido.addOnItemTouchListener(
                new RecyclerItemClickListener( getContext(), recyclerPedido, new RecyclerItemClickListener.OnItemClickListener() {
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


        recyclerPedido.addOnItemTouchListener(
                new RecyclerItemClickListener( getContext(), recyclerPedido,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            }
                            @Override
                            public void onItemClick(View view, int position) {
                                //Animal animal = animalAdapter.getItem( position );

                                //Toast.makeText(getContext(), "ss", Toast.LENGTH_SHORT).show();
                                /*Pedido_Adocao pedidoAdocao = pedidoAdapter.getItem( position );
                                Intent i = new Intent(getContext(), DadosAnimal.class);
                                Log.i("testando", pedidoAdocao.toString());
                                i.putExtra("animal" , pe);
                                startActivity(i);*/
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
        dao_animal.carregarPedidos(recyclerPedido, getContext());
        return  view;
    }

    private void carregarLista() {
        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(getContext(),
                "Carregando Lista...", "Por favor espere um pouco...", false, false);
        final ArrayList<Animal> animais = new ArrayList<Animal>();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Links.PEGAR_PEDIDOS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.i("supertag", "A resposta foi " + s);
                        try {
                            Gson gson = new Gson();
                            JSONArray jsonArray = new JSONArray( s );
                            for(int a=0;a < jsonArray.length();a++){
                                JSONObject js = jsonArray.getJSONObject(a);
                                pedidoAdocao = gson.fromJson( js.toString() , Pedido_Adocao.class );
                                Log.i( "CARREGAR" , pedidoAdocao.toString());
                                pedidoAdocaos.add(pedidoAdocao);
                            }
                            pedidoAdapter =  new PedidoAdapter(pedidoAdocaos);
                            recyclerPedido.setAdapter(pedidoAdapter );
                            loading.dismiss();
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
                SharedPreferences prefs = getActivity().getSharedPreferences(Links.LOGIN_PREFERENCE, 0);
                int idUser = prefs.getInt("idUser", 0);

                params.put("idUser", idUser+"");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        //Adding request to the queue
        requestQueue.add(stringRequest);
    }


    public Bitmap StringToBitMap(String image){
        byte [] encodeByte= Base64.decode(image,Base64.DEFAULT);
        InputStream inputStream  = new ByteArrayInputStream(encodeByte);
        Bitmap bitmap  = BitmapFactory.decodeStream(inputStream);
        return bitmap;
    }
}