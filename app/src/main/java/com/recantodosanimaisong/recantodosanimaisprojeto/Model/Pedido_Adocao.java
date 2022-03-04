package com.recantodosanimaisong.recantodosanimaisprojeto.Model;

public class Pedido_Adocao {
    private int numPedido;
    private String descricao;
    private int idAnimal;
    private int idUsuario;
    private String nome;
    private String imagem;

    public Pedido_Adocao(int numPedido, String descricao, int idAnimal, int idUsuario, String nome, String imagem) {
        this.numPedido = numPedido;
        this.descricao = descricao;
        this.idAnimal = idAnimal;
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.imagem = imagem;
    }

    public Pedido_Adocao(String descricao, int idAnimal, int idUsuario) {
        this.descricao = descricao;
        this.idAnimal = idAnimal;
        this.idUsuario = idUsuario;
    }

    public int getNumPedido() {
        return numPedido;
    }

    public void setNumPedido(int numPedido) {
        this.numPedido = numPedido;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getIdAnimal() {
        return idAnimal;
    }

    public void setIdAnimal(int idAnimal) {
        this.idAnimal = idAnimal;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    @Override
    public String toString() {
        return "Pedido_Adocao{" +
                "numPedido=" + numPedido +
                ", descricao='" + descricao + '\'' +
                ", idAnimal=" + idAnimal +
                ", idUsuario=" + idUsuario +
                ", nome='" + nome + '\'' +
                ", imagem='" + imagem + '\'' +
                '}';
    }
}
