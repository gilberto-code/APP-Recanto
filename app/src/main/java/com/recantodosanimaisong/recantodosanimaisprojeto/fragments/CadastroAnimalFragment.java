//O Gilberto é muito inteligente e a Duda sabe fazer código
package com.recantodosanimaisong.recantodosanimaisprojeto.fragments;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;


import com.recantodosanimaisong.recantodosanimaisprojeto.DAOs.DAO_Animal;
import com.recantodosanimaisong.recantodosanimaisprojeto.Model.Animal;
import com.recantodosanimaisong.recantodosanimaisprojeto.R;
import com.recantodosanimaisong.recantodosanimaisprojeto.activitys.DadosAnimal;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class CadastroAnimalFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    public CadastroAnimalFragment() {
        // Required empty public constructor
    }

     Button cadastrar;
     ImageButton adc_foto;
     ImageButton adc_foto_camera;
     EditText ed_nome;
     Spinner sp_especie;
     SeekBar sk_idade;
     TextView txIdadeSpinner;
     Spinner sp_porte;
     EditText ed_raca;
     EditText ed_cor;
     RadioGroup rg_sexo;
     RadioGroup rg_vacinado;
     RadioGroup rg_castrado;
     RadioGroup rg_doente;
     EditText ed_doente;
     EditText ed_descricao;
     ImageView imageView;

     int PICK_IMAGE_REQUEST = 1;
    static final int REQUEST_IMAGE_CAPTURE = 2;
     static final int MY_CAMERA_REQUEST_CODE = 100;

    Bitmap imagemAnimal;
     DAO_Animal dao_animal = new DAO_Animal();

    public void pedir_permissao (){
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions( getActivity(), new String[] {Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_cadastro_animal, container, false );
        getActivity().setTitle("Cadastro Animal");
        cadastrar= view.findViewById( R.id.btn_cadastrar_animal );
        adc_foto= view.findViewById( R.id.btn_adiconar_imagem );
        adc_foto_camera= view.findViewById( R.id.btn_adiconar_imagem_camera );
        sp_especie= view.findViewById( R.id.spEspecie);
        sk_idade = view.findViewById( R.id.skIdade);
        txIdadeSpinner = view.findViewById( R.id.txIdadeSpinner);
        sp_porte = view.findViewById( R.id.spPorte);
        ed_nome= view.findViewById( R.id.ed_nome_animal);
        ed_raca = view.findViewById( R.id.ed_raca_animal);
        ed_cor = view.findViewById( R.id.ed_cor_animal);
        rg_sexo = view.findViewById( R.id.rg_sexo_animal);
        rg_vacinado = view.findViewById( R.id.rg_vacinado_animal);
        rg_castrado = view.findViewById( R.id.rg_castrado_animal);
        rg_doente = view.findViewById( R.id.rg_doente);
        ed_doente = view.findViewById( R.id.ed_qual_doenca);
        ed_descricao = view.findViewById( R.id.ed_descricao_animal);
        imageView = view.findViewById( R.id.imagem_do_Animal);

        adc_foto.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        } );
        adc_foto_camera.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_DENIED){
                    pedir_permissao();
                }else{
                    dispatchTakePictureIntent(getContext());
                }
            }
        } );

        carregarSpinner( view );
        sk_idade.setOnSeekBarChangeListener( new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txIdadeSpinner.setText( progress + "" );
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        } );

        cadastrar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animal animal = coletarInformacao();
                animal.setImagem(getStringImage(imagemAnimal));
                dao_animal.enviarAnimal( animal, getContext());
                Intent i = new Intent(getContext() , DadosAnimal.class);
                i.putExtra("animal", animal);
                startActivity(i);
            }
        } );

        rg_doente.setOnCheckedChangeListener( new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(R.id.rb_sim_doente == checkedId){
                    ed_doente.setVisibility( View.VISIBLE );
                }else{
                    ed_doente.setVisibility( View.GONE );
                }
            }
        } );

        return view;
    }

    public Animal coletarInformacao(){
        Animal animal;
        String nome;
        String especie;
        int idade;
        String raca;
        String cor;
        String porte;
        String sexo;
        String descricao;
        String temperamento;
        String bairroEncontrado;
        String nomeContato;
        String telefoneContato;
        String dataDeCadastro;
        String qualDoenca;
        String qualAcidente;
        int doente;
        int acidentado;
        int castrado;
        int vacinado;
        int prenha;
        nome = ed_nome.getText().toString();
        especie = sp_especie.getSelectedItem().toString();
        idade  = Integer.parseInt(txIdadeSpinner.getText().toString());
        raca = ed_raca.getText().toString();
        cor = ed_cor.getText().toString();
        porte = sp_porte.getSelectedItem().toString();

        int checkedRadioButtonId = rg_sexo.getCheckedRadioButtonId();
        if(checkedRadioButtonId == R.id.rb_macho){
            sexo = "Macho";
        }else {
            sexo = "Fêmea";
        }

        checkedRadioButtonId = rg_castrado.getCheckedRadioButtonId();
        if(checkedRadioButtonId == R.id.rb_sim_castrado){
            castrado = 1;
        }else {
            castrado = 0;
        }

        descricao = ed_descricao.getText().toString();

        checkedRadioButtonId = rg_vacinado.getCheckedRadioButtonId();
        if(checkedRadioButtonId == R.id.rb_sim_vacinado){
            vacinado = 1;
        }else {
            vacinado = 0;
        }

        checkedRadioButtonId = rg_doente.getCheckedRadioButtonId();
        if(checkedRadioButtonId == R.id.rb_sim_doente){
            doente = 1;
        }else {
            doente = 0;
        }
        qualDoenca = ed_doente.getText().toString();


        Calendar c = Calendar.getInstance();
        int dia = c.get(Calendar.DAY_OF_MONTH);
        int ano = c.get(Calendar.YEAR);
        int mes = c.get(Calendar.MONTH);

        String date = ano+"-"+(mes+1)+"-"+dia;
        dataDeCadastro = date;

        animal = new Animal(nome, especie, idade, raca, cor,
                porte, sexo, descricao,  dataDeCadastro,qualDoenca,
                doente,  castrado,  vacinado );

        return animal;
    }

    public void carregarSpinner(View view){
        Spinner spinner = (Spinner) view.findViewById( R.id.spEspecie);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.especie_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener( this );


        spinner = (Spinner) view.findViewById( R.id.spPorte);
        adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.porte_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(  this );
    }

     void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Selecione uma Imagem"), PICK_IMAGE_REQUEST);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                imagemAnimal = MediaStore.Images.Media.getBitmap(getActivity().
                        getApplicationContext().getContentResolver(), filePath);
                imageView.setImageBitmap(imagemAnimal);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
            imagemAnimal = imageBitmap;
        }
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {}

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

     public void dispatchTakePictureIntent(Context context) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 30, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.NO_WRAP);
        return encodedImage;
    }
}
