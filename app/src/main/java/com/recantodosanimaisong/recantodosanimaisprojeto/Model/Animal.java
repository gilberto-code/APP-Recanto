package com.recantodosanimaisong.recantodosanimaisprojeto.Model;

import android.graphics.Bitmap;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.BitSet;

public class Animal implements Serializable {
    private String nome;
    private String especie;
    private int idade;
    private String raca;
    private String cor;
    private String porte;
    private String sexo;
    private String descricao;
    private String temperamento;
    private String bairroEncontrado;
    private String nomeContato;
    private String telefoneContato;
    private String dataDeCadastro;
    private String qualDoenca;
    private String qualAcidente;
    private int doente;
    private int acidentado;
    private int castrado;
    private int vacinado;
    private int prenha;
    private int idAnimal;
    private String imagem;


    public Animal(String nome, String especie, int idade, String raca,
                  String cor, String porte, String sexo, String descricao, String temperamento,
                  String bairroEncontrado, String nomeContato, String telefoneContato, String dataDeCadastro,
                  String qualDoenca, String qualAcidente, int doente, int acidentado, int castrado, int vacinado, int prenha) {
        this.nome = nome;
        this.especie = especie;
        this.idade = idade;
        this.raca = raca;
        this.cor = cor;
        this.porte = porte;
        this.sexo = sexo;
        this.descricao = descricao;
        this.temperamento = temperamento;
        this.bairroEncontrado = bairroEncontrado;
        this.nomeContato = nomeContato;
        this.telefoneContato = telefoneContato;
        this.dataDeCadastro = dataDeCadastro;
        this.qualDoenca = qualDoenca;
        this.qualAcidente = qualAcidente;
        this.doente = doente;
        this.acidentado = acidentado;
        this.castrado = castrado;
        this.vacinado = vacinado;
        this.prenha = prenha;
    }

    public Animal(String nome, String especie, int idade, String raca, String cor, String porte, String sexo, String descricao, String temperamento, String bairroEncontrado, String nomeContato, String telefoneContato, String dataDeCadastro, String qualDoenca, String qualAcidente, int doente, int acidentado, int castrado, int vacinado, int prenha, int idAnimal, String imagem) {
        this.nome = nome;
        this.especie = especie;
        this.idade = idade;
        this.raca = raca;
        this.cor = cor;
        this.porte = porte;
        this.sexo = sexo;
        this.descricao = descricao;
        this.temperamento = temperamento;
        this.bairroEncontrado = bairroEncontrado;
        this.nomeContato = nomeContato;
        this.telefoneContato = telefoneContato;
        this.dataDeCadastro = dataDeCadastro;
        this.qualDoenca = qualDoenca;
        this.qualAcidente = qualAcidente;
        this.doente = doente;
        this.acidentado = acidentado;
        this.castrado = castrado;
        this.vacinado = vacinado;
        this.prenha = prenha;
        this.idAnimal = idAnimal;
        this.imagem = imagem;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getPorte() {
        return porte;
    }

    public void setPorte(String porte) {
        this.porte = porte;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTemperamento() {
        return temperamento;
    }

    public void setTemperamento(String temperamento) {
        this.temperamento = temperamento;
    }

    public String getBairroEncontrado() {
        return bairroEncontrado;
    }

    public void setBairroEncontrado(String bairroEncontrado) {
        this.bairroEncontrado = bairroEncontrado;
    }

    public String getNomeContato() {
        return nomeContato;
    }

    public void setNomeContato(String nomeContato) {
        this.nomeContato = nomeContato;
    }

    public String getTelefoneContato() {
        return telefoneContato;
    }

    public void setTelefoneContato(String telefoneContato) {
        this.telefoneContato = telefoneContato;
    }

    public String getDataDeCadastro() {
        return dataDeCadastro;
    }

    public void setDataDeCadastro(String dataDeCadastro) {
        this.dataDeCadastro = dataDeCadastro;
    }

    public String getQualDoenca() {
        return qualDoenca;
    }

    public void setQualDoenca(String qualDoenca) {
        this.qualDoenca = qualDoenca;
    }

    public String getQualAcidente() {
        return qualAcidente;
    }

    public void setQualAcidente(String qualAcidente) {
        this.qualAcidente = qualAcidente;
    }

    public int getDoente() {
        return doente;
    }

    public void setDoente(int doente) {
        this.doente = doente;
    }

    public int getAcidentado() {
        return acidentado;
    }

    public void setAcidentado(int acidentado) {
        this.acidentado = acidentado;
    }

    public int getCastrado() {
        return castrado;
    }

    public void setCastrado(int castrado) {
        this.castrado = castrado;
    }

    public int getVacinado() {
        return vacinado;
    }

    public void setVacinado(int vacinado) {
        this.vacinado = vacinado;
    }

    public int getPrenha() {
        return prenha;
    }

    public void setPrenha(int prenha) {
        this.prenha = prenha;
    }

    public int getIdAnimal() {
        return idAnimal;
    }

    public void setIdAnimal(int idAnimal) {
        this.idAnimal = idAnimal;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "nome='" + nome + '\'' +
                ", especie='" + especie + '\'' +
                ", idade=" + idade +
                ", raca='" + raca + '\'' +
                ", cor='" + cor + '\'' +
                ", porte='" + porte + '\'' +
                ", sexo='" + sexo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", temperamento='" + temperamento + '\'' +
                ", bairroEncontrado='" + bairroEncontrado + '\'' +
                ", nomeContato='" + nomeContato + '\'' +
                ", telefoneContato='" + telefoneContato + '\'' +
                ", dataDeCadastro='" + dataDeCadastro + '\'' +
                ", qualDoenca='" + qualDoenca + '\'' +
                ", qualAcidente='" + qualAcidente + '\'' +
                ", doente=" + doente +
                ", acidentado=" + acidentado +
                ", castrado=" + castrado +
                ", vacinado=" + vacinado +
                ", prenha=" + prenha +
                ", idAnimal=" + idAnimal +
                ", imagem='" + imagem + '\'' +
                '}';
    }
}