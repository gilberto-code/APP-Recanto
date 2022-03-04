package com.recantodosanimaisong.recantodosanimaisprojeto.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.recantodosanimaisong.recantodosanimaisprojeto.DAOs.DAO_Usuario;
import com.recantodosanimaisong.recantodosanimaisprojeto.Model.Usuario;
import com.recantodosanimaisong.recantodosanimaisprojeto.R;
/**
 * A simple {@link Fragment} subclass.
 */
public class CadastroFragment extends Fragment {
    public CadastroFragment() {}

    private Button cadastrar;
    private Button buttonLogin;
    private EditText ed_nome;
    private EditText ed_email;
    private EditText ed_password;
    private EditText ed_confim_password;
    private DAO_Usuario dao_usuario = new DAO_Usuario();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.activity_cadastro, container, false );
        cadastrar = view.findViewById( R.id.buttonCadastrarUsuario );
        ed_nome = view.findViewById( R.id.ed_nome );
        ed_email = view.findViewById( R.id.ed_email );
        ed_password = view.findViewById( R.id.ed_password );
        ed_confim_password = view.findViewById( R.id.ed_confim_password );
        buttonLogin = view.findViewById(R.id.buttonLogin);

        cadastrar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = ed_nome.getText().toString();
                String email = ed_email.getText().toString();
                String password = ed_password.getText().toString();
                String conf_password = ed_confim_password.getText().toString();
                if(isPasswordValid( password , conf_password , v)
                        && isEmailValid( email , v)) {
                    Usuario usuario = new Usuario(nome,password,email);
                    dao_usuario.envio_usuario(usuario , getContext());
                }
                }
        } );
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent();
            Intent i = new Intent(getContext(), TelaLogin .class);
            startActivity(i);
            }
        });
        return view;
    }

    private boolean isPasswordValid(String password ,String conf_password  , View view) {
        if(!(password.length() > 6 )){
            Snackbar.make(view , "Senha inválida", Snackbar.LENGTH_LONG ).setAction( "Action", null ).show();
            return false;
        }else if(!conf_password.equals( password )){
            Snackbar.make(view , "As senhas não são iguais", Snackbar.LENGTH_LONG ).setAction( "Action", null ).show();
            return false;
        }
        return true;
    }
    private boolean isEmailValid(String email , View view) {
        //TODO: Replace this with your own logic
        if(!email.contains( "@" )){
            Snackbar.make(view , "E-mail inválido", Snackbar.LENGTH_LONG ).setAction( "Action", null ).show();
            return false;
        }
        return true;
    }
}
