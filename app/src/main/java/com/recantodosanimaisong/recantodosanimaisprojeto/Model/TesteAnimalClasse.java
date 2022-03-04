package com.recantodosanimaisong.recantodosanimaisprojeto.Model;

public class TesteAnimalClasse {
    String nome ;
  String cor;
 String raca;
    String porte;
    String descricao;
    String especie;
     int idade;
    String sexo;
     String castrado;

    public TesteAnimalClasse(String nome, String cor, String raca, String porte, String descricao, String especie, int idade, String sexo, String castrado) {
        this.nome = nome;
        this.cor = cor;
        this.raca = raca;
        this.porte = porte;
        this.descricao = descricao;
        this.especie = especie;
        this.idade = idade;
        this.sexo = sexo;
        this.castrado = castrado;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public String getPorte() {
        return porte;
    }

    public void setPorte(String porte) {
        this.porte = porte;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String isCastrado() {
        return castrado;
    }

    public void isCastrado(String castrado) {
        this.castrado = castrado;
    }

    @Override
    public String toString() {
        return "TesteAnimalClasse{" + "nome='" + nome + '\'' + ", cor='" + cor + '\'' + ", raca='" + raca + '\'' + ", porte='" + porte + '\'' + ", descricao='" + descricao + '\'' + ", especie='" + especie + '\'' + ", idade=" + idade + ", sexo='" + sexo + '\'' + ", castrado='" + castrado + '\'' + '}';
    }
}
