package com.recantodosanimaisong.recantodosanimaisprojeto.Adapter;

import android.content.Context;
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
import com.recantodosanimaisong.recantodosanimaisprojeto.Model.Adotante;
import com.recantodosanimaisong.recantodosanimaisprojeto.Model.Animal;
import com.recantodosanimaisong.recantodosanimaisprojeto.R;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;


public class AdotanteAdapter extends RecyclerView.Adapter<AdotanteAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Adotante> list_adotante = new ArrayList<Adotante>(  );
    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textNome;
        private TextView textCPF;
        private ImageView imagemAdotante;


        public ViewHolder(View v) {
            super(v);
            textNome = itemView.findViewById( R.id.textNome);
            textCPF = itemView.findViewById(R.id.textCPF);
            imagemAdotante = itemView.findViewById( R.id.imagemAdotante );
//            itemView.setOnClickListener( (View.OnClickListener) this );
        }
    }
    public void add(int position, Adotante adotante) {
        list_adotante.add(position,adotante);
        notifyItemInserted(position);
    }
    public void remove(Adotante adotante) {
        int position = list_adotante.indexOf(adotante);
        list_adotante.remove(position);
        notifyItemRemoved(position);
    }

    public Adotante getItem(int position) {
        return list_adotante.get( position);
    }

    public AdotanteAdapter(ArrayList<Adotante> adotantes , Context context) {
        list_adotante = adotantes;
        this.context = context;
    }

    @Override
    public AdotanteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_adotante, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textNome.setText(list_adotante.get(position).getNome());
        holder.textCPF.setText( list_adotante.get(position).getCPF() );
        Bitmap imagem = StringToBitMap( list_adotante.get( position ).getFoto_momento() );
        Glide.with(context).load(imagem).into(holder.imagemAdotante);
    }
    @Override
    public int getItemCount() {
        return list_adotante.size();
    }

    public Bitmap StringToBitMap(String image){
        byte [] encodeByte = Base64.decode(image,Base64.DEFAULT);
        InputStream inputStream  = new ByteArrayInputStream(encodeByte);
        Bitmap bitmap  = BitmapFactory.decodeStream(inputStream);
        return bitmap;
    }
}
