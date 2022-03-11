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
import com.recantodosanimaisong.recantodosanimaisprojeto.Model.Animal;
import com.recantodosanimaisong.recantodosanimaisprojeto.R;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class AnimalAdotadoAdapter extends RecyclerView.Adapter<AnimalAdotadoAdapter.ViewHolder>{
    Context context;
    private ArrayList<Animal> list_animal = new ArrayList<Animal>(  );
    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textNome;
        private TextView textSexo;
        private TextView textIdade;
        private TextView textCastrado;
        private TextView textDescricao;
        private ImageView imageAnimal;


        public ViewHolder(View v) {
            super(v);
            textNome = itemView.findViewById(R.id.textNome);
            textSexo = itemView.findViewById(R.id.textSexo);
            textCastrado = itemView.findViewById(R.id.textCastrado);
            textDescricao = itemView.findViewById(R.id.textDescricao);
            imageAnimal = itemView.findViewById( R.id.imageView);
            textIdade = itemView.findViewById( R.id.textIdade);
//            itemView.setOnClickListener( (View.OnClickListener) this );
        }
    }
    public void add(int position, Animal animal) {
        list_animal.add(position,animal);
        notifyItemInserted(position);
    }
    public void remove(Animal animal) {
        int position = list_animal.indexOf(animal);
        list_animal.remove(position);
        notifyItemRemoved(position);
    }

    public Animal getItem(int position) {
        return list_animal.get( position);
    }

    public AnimalAdotadoAdapter(ArrayList<Animal> animals) {
        list_animal = animals;
    }

    @Override
    public AnimalAdotadoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_animais, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    private Bitmap imagem;
    @Override
    public void onBindViewHolder(AnimalAdotadoAdapter.ViewHolder holder, int position) {
        holder.textNome.setText(list_animal.get(position).getNome());
        holder.textSexo.setText( list_animal.get(position).getSexo() );
        holder.textIdade.setText( list_animal.get(position).getIdade() + " anos");
        holder.textDescricao.setText( list_animal.get(position).getDescricao());
        if(list_animal.get(position).getCastrado() == 1){
            holder.textCastrado.setText( "Sim");
        }else{
            holder.textCastrado.setText( "Não");
        }
        //Glide.with(holder.imageAnimal.getContext()).load(null).into(holder.imageAnimal);
        imagem = StringToBitMap( list_animal.get( position ).getImagem() );
        Glide.with(holder.imageAnimal.getContext()).load(imagem).into(holder.imageAnimal);
    }
    @Override
    public int getItemCount() {
        return list_animal.size();
    }
    public Bitmap StringToBitMap(String image){

        if(image != null){
            byte [] encodeByte= Base64.decode(image,Base64.DEFAULT);
            InputStream inputStream  = new ByteArrayInputStream(encodeByte);
            Bitmap bitmap  = BitmapFactory.decodeStream(inputStream);
            return bitmap;
        }else{
            return null;
        }
    }
}
