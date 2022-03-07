//package com.recantodosanimaisong.recantodosanimaisprojeto.Adapter;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.support.v7.widget.RecyclerView;
//import android.util.Base64;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.bumptech.glide.Glide;
//import com.recantodosanimaisong.recantodosanimaisprojeto.Model.Usuario;
//import com.recantodosanimaisong.recantodosanimaisprojeto.Model.Animal;
//import com.recantodosanimaisong.recantodosanimaisprojeto.R;
//
//import java.io.ByteArrayInputStream;
//import java.io.InputStream;
//import java.util.ArrayList;
//
//
//public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.ViewHolder> {
//    private Context context;
//    private ArrayList<Usuario> list_Usuario = new ArrayList<Usuario>(  );
//    public class ViewHolder extends RecyclerView.ViewHolder {
//
//        private TextView textNome;
//        private TextView textCPF;
//        private ImageView imagemUsuario;
//
//
//        public ViewHolder(View v) {
//            super(v);
//            textNome = itemView.findViewById( R.id.textNome);
//            textCPF = itemView.findViewById(R.id.textCPF);
//            imagemUsuario = itemView.findViewById( R.id.imagemUsuario );
////            itemView.setOnClickListener( (View.OnClickListener) this );
//        }
//    }
//    public void add(int position, Usuario Usuario) {
//        list_Usuario.add(position,Usuario);
//        notifyItemInserted(position);
//    }
//    public void remove(Usuario Usuario) {
//        int position = list_Usuario.indexOf(Usuario);
//        list_Usuario.remove(position);
//        notifyItemRemoved(position);
//    }
//
//    public Usuario getItem(int position) {
//        return list_Usuario.get( position);
//    }
//
//    public UsuarioAdapter(ArrayList<Usuario> Usuarios , Context context) {
//        list_Usuario = Usuarios;
//        this.context = context;
//    }
//
//    @Override
//    public UsuarioAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        // create a new view
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_Usuario, parent, false);
//        ViewHolder vh = new ViewHolder(v);
//        return vh;
//    }
//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//        holder.textNome.setText(list_Usuario.get(position).getNome());
//        holder.textCPF.setText( list_Usuario.get(position).getCPF() );
//        Bitmap imagem = StringToBitMap( list_Usuario.get( position ).getFoto_momento() );
//        Glide.with(context).load(imagem).into(holder.imagemUsuario);
//    }
//    @Override
//    public int getItemCount() {
//        return list_Usuario.size();
//    }
//
//    public Bitmap StringToBitMap(String image){
//        byte [] encodeByte = Base64.decode(image,Base64.DEFAULT);
//        InputStream inputStream  = new ByteArrayInputStream(encodeByte);
//        Bitmap bitmap  = BitmapFactory.decodeStream(inputStream);
//        return bitmap;
//    }
//}
