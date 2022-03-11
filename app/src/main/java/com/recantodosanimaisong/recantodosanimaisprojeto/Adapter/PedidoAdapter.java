package com.recantodosanimaisong.recantodosanimaisprojeto.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.recantodosanimaisong.recantodosanimaisprojeto.Model.Pedido_Adocao;
import com.recantodosanimaisong.recantodosanimaisprojeto.R;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class PedidoAdapter extends RecyclerView.Adapter<PedidoAdapter.ViewHolder> {

    private ArrayList<Pedido_Adocao> pedidoAdocaos = new ArrayList<Pedido_Adocao>(  );

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textNomeAdotate;
        private TextView textDescricao;
        private ImageView imageAnimal;

        public ViewHolder(View v) {
            super(v);
            textNomeAdotate = itemView.findViewById(R.id.txt_nome_adotante);
            textDescricao = itemView.findViewById( R.id.txt_descricao);
            imageAnimal = itemView.findViewById( R.id.img_animal);
        }
    }
    public void add(int position, Pedido_Adocao pedidoAdocao) {
        pedidoAdocaos.add(position, pedidoAdocao);
        notifyItemInserted(position);
    }
    public Pedido_Adocao getItem(int position) {
        return pedidoAdocaos.get( position);
    }
    public PedidoAdapter(ArrayList<Pedido_Adocao> pedidoAdocaos) {
        this.pedidoAdocaos = pedidoAdocaos;
    }

    @Override
    public PedidoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_pedido, parent, false);
        PedidoAdapter.ViewHolder vh = new PedidoAdapter.ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(PedidoAdapter.ViewHolder holder, int position) {
        holder.textNomeAdotate.setText(pedidoAdocaos.get(position).getNome());
        holder.textDescricao.setText( pedidoAdocaos.get(position).getDescricao() );
        //Glide.with(holder.imageAnimal.getContext()).load(null).into(holder.imageAnimal);
        Bitmap imagem = StringToBitMap( pedidoAdocaos.get( position ).getImagem() );
        Glide.with(holder.imageAnimal.getContext()).load(imagem).into(holder.imageAnimal);
    }
    @Override
    public int getItemCount() {
        return pedidoAdocaos.size();
    }
    public Bitmap StringToBitMap(String image){
        byte [] encodeByte= Base64.decode(image,Base64.DEFAULT);
        InputStream inputStream  = new ByteArrayInputStream(encodeByte);
        Bitmap bitmap  = BitmapFactory.decodeStream(inputStream);
        return bitmap;
    }
}
