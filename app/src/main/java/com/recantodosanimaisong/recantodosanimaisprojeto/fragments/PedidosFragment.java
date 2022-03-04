package com.recantodosanimaisong.recantodosanimaisprojeto.fragments;


import android.app.ProgressDialog;
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
import com.recantodosanimaisong.recantodosanimaisprojeto.Adapter.PedidoAdapter;
import com.recantodosanimaisong.recantodosanimaisprojeto.Adapter.RecyclerItemClickListener;
import com.recantodosanimaisong.recantodosanimaisprojeto.Model.Mysingleton;
import com.recantodosanimaisong.recantodosanimaisprojeto.Model.Pedido_Adocao;
import com.recantodosanimaisong.recantodosanimaisprojeto.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

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
    private String URL = "http://200.18.128.55/gilberto/banco_ong/pegar_pedidos.php";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pedidos, container, false);

        recyclerPedido = view.findViewById(R.id.recyclerPedido);
        recyclerPedido.setLayoutManager(new LinearLayoutManager(getContext()));

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
        carregarLista();
        return  view;
    }

    public void carregarLista(){
        final ProgressDialog loading = ProgressDialog.show(getContext(),
                "Carregando lista...","Espere um segundo...",true,false);
        try {
            StringRequest stringRequest = new StringRequest( Request.Method.GET, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("supertag","A resposta foi "+response);
                    try {
                        Gson gson = new Gson();
                        JSONArray jsonArray = new JSONArray( response );
                        for(int a=0;a < jsonArray.length();a++){
                            JSONObject js = jsonArray.getJSONObject(a);
                            pedidoAdocao = gson.fromJson( js.toString() , Pedido_Adocao.class );
                            Log.i( "CARREGAR" , pedidoAdocao.toString());
                            pedidoAdocaos.add(pedidoAdocao);
                        }
                        pedidoAdapter =  new PedidoAdapter(pedidoAdocaos);
                        recyclerPedido.setAdapter(pedidoAdapter );
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
            });
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