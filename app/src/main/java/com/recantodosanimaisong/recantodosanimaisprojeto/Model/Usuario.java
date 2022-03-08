package com.recantodosanimaisong.recantodosanimaisprojeto.Model;

public class Usuario {
    private  int idUser ;
    private String nome ;
    private String  telefone;
    private String cpf;
    private String  endereco ;
    private String  email ;
    private String  senha ;
    private String  foto_momento ;

    public Usuario(String email) {
        this.email = email;
    }

    public Usuario(int idUser, String nome, String telefone, String cpf, String endereco, String email, String senha, String foto_momento) {
        this.idUser = idUser;
        this.nome = nome;
        this.telefone = telefone;
        this.cpf = cpf;
        this.endereco = endereco;
        this.email = email;
        this.senha = senha;
        this.foto_momento = foto_momento;
    }

    public Usuario(String nome, String telefone, String cpf, String endereco, String email, String senha, String foto_momento) {
        this.nome = nome;
        this.telefone = telefone;
        this.cpf = cpf;
        this.endereco = endereco;
        this.email = email;
        this.senha = senha;
        this.foto_momento = foto_momento;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFoto_momento() {
        return foto_momento;
    }

    public void setFoto_momento(String foto_momento) {
        this.foto_momento = foto_momento;
    }

    @Override
    public String toString() {
//        return "Adotante{" + "id=" +idAdotante + ", nome='" +
//                nome + '\'' + ", telefone='" + telefone + '\'' + ", CPF='" + CPF
//                + '\'' + ", data_nascimento='" + data_nascimento + '\'' +
//                ", endereco='" + endereco + '\'' + ", email='" + email +
//                '\'' + ", foto_momento='" + foto_momento + '\'' + '}';

        return "Usuario{" + "id=" +idUser + ", nome='" +
                nome + '\'' + ", telefone='" + telefone + '\'' + ", CPF='" + cpf +  '\'' +
                ", endereco='" + endereco +
                '\'' + ", email='" + email + '\'' +
                '\'' + ", senha='" + senha + '\'' +
                ", foto_momento='" + foto_momento + '\'' + '}';
    }
}
