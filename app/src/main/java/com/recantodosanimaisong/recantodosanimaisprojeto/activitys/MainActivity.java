package com.recantodosanimaisong.recantodosanimaisprojeto.activitys;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.recantodosanimaisong.recantodosanimaisprojeto.Conexao.BaseAppCompatActivity;
import com.recantodosanimaisong.recantodosanimaisprojeto.Conexao.MyUncaughtException;
import com.recantodosanimaisong.recantodosanimaisprojeto.R;
import com.recantodosanimaisong.recantodosanimaisprojeto.fragments.AnimaisFragment;
import com.recantodosanimaisong.recantodosanimaisprojeto.fragments.Animais_Adotados;
import com.recantodosanimaisong.recantodosanimaisprojeto.fragments.CadastroAnimalFragment;
import com.recantodosanimaisong.recantodosanimaisprojeto.fragments.CadastroFragment;
import com.recantodosanimaisong.recantodosanimaisprojeto.fragments.PedidosFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );

        //ACRESCENTEI ESSAS DUAS LINHAS - PENDENTE - TESTAR - CHECAR - VERIFICAR
        //Thread estaThread = Thread.currentThread();
        //estaThread.setUncaughtExceptionHandler(new MyUncaughtException());

        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle( this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.addDrawerListener( toggle );
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById( R.id.nav_view );
        navigationView.setNavigationItemSelectedListener( this );
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        if (drawer.isDrawerOpen( GravityCompat.START )) {
            drawer.closeDrawer( GravityCompat.START );
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.main, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected( item );
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
//            Intent i = new Intent(getApplicationContext(), AdotarActivity.class);
//            startActivity(i);
        }
        else if (id == R.id.nav_animais) {
            //Toast.makeText( getApplicationContext(), "Adoção", Toast.LENGTH_SHORT ).show();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            AnimaisFragment animaisFragment = new AnimaisFragment();
            fragmentTransaction.replace( R.id.fragment_container, animaisFragment );
            fragmentTransaction.commit();
        }else if (id == R.id.nav_animais_adotados) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            Animais_Adotados animaisFragment = new Animais_Adotados();
            fragmentTransaction.replace( R.id.fragment_container, animaisFragment );
            fragmentTransaction.commit();
        }else if (id == R.id.nav_pedidos) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            PedidosFragment pedidosFragment = new PedidosFragment();
            fragmentTransaction.replace( R.id.fragment_container, pedidosFragment );
            fragmentTransaction.commit();
        }
        else if (id == R.id.nav_site) {
            //Toast.makeText( getApplicationContext() ,"Site",Toast.LENGTH_SHORT ).show();
            String url = "http://recantoanimaiscont.wixsite.com";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData( Uri.parse(url));
            startActivity(i);
        }else if (id == R.id.nav_facebook) {
            //Toast.makeText( getApplicationContext() ,"Facebook",Toast.LENGTH_SHORT ).show();
            String url = "https://www.facebook.com/recantodosanimaisourobranco/";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData( Uri.parse(url));
            startActivity(i);
        }else if (id == R.id.nav_mensagem_ong) {
            //funcao( this );
        }else if (id == R.id.nav_cadastrar){
            //Toast.makeText( getApplicationContext() ,"NAV_CADASTRAR",Toast.LENGTH_SHORT ).show();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            CadastroFragment cadastroFragment = new CadastroFragment();
            fragmentTransaction.replace(R.id.fragment_container,cadastroFragment );
            fragmentTransaction.commit();
        }else if (id == R.id.nav_conta) {
            //funcao( this );
        }
        else if (id == R.id.nav_cadastrar_ainimal) {
            //Toast.makeText( getApplicationContext() ,"Cadastrar Animal",Toast.LENGTH_SHORT ).show();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            CadastroAnimalFragment cadastroAnimalFragment = new CadastroAnimalFragment();
            fragmentTransaction.replace(R.id.fragment_container, cadastroAnimalFragment );
            fragmentTransaction.commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        drawer.closeDrawer( GravityCompat.START );
        return true;
    }
}
