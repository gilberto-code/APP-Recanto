package com.recantodosanimaisong.recantodosanimaisprojeto.Model;

public class Animal_Adotado extends Animal {

    private int idAdotante;
    private String dataAdocao;
    private int idAnimalAdotado;

    /*public Animal_Adotado(String nome, String especie, int idade, String raca, String cor,
                          String porte, String sexo, String descricao, String temperamento,
                          String bairroEncontrado, String nomeContato, String telefoneContato, String dataDeCadastro,
                          String qualDoenca, String qualAcidente, int doente, int acidentado, int castrado, int vacinado, int prenha) {
        super(nome, especie, idade,
                raca, cor, porte, sexo, descricao, temperamento, bairroEncontrado, nomeContato, telefoneContato,
                dataDeCadastro, qualDoenca, qualAcidente, doente, acidentado, castrado, vacinado, prenha);

    }*/

    public Animal_Adotado(String nome, String especie, int idade, String raca,
                          String cor, String porte, String sexo,
                          String descricao, String temperamento, String bairroEncontrado,
                          String nomeContato, String telefoneContato,
                          String dataDeCadastro, String qualDoenca, String qualAcidente,
                          int doente, int acidentado, int castrado, int vacinado, int prenha,
                          int idAdotante, String dataAdocao, int idAnimalAdotado) {
        super(nome, especie, idade, raca, cor, porte, sexo, descricao,
                dataDeCadastro,
                qualDoenca, doente, castrado, vacinado);
        this.idAdotante = idAdotante;
        this.dataAdocao = dataAdocao;
        this.idAnimalAdotado = idAnimalAdotado;
    }

    public int getIdAdotante() {
        return idAdotante;
    }

    public void setIdAdotante(int idAdotante) {
        this.idAdotante = idAdotante;
    }

    public String getDataAdocao() {
        return dataAdocao;
    }

    public void setDataAdocao(String dataAdocao) {
        this.dataAdocao = dataAdocao;
    }

    public int getIdAnimalAdotado() {
        return idAnimalAdotado;
    }

    public void setIdAnimalAdotado(int idAnimalAdotado) {
        this.idAnimalAdotado = idAnimalAdotado;
    }
}
