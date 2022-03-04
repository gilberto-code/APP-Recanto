package com.recantodosanimaisong.recantodosanimaisprojeto.Model;

public class Adotante {
    private  int idAdotante ;
    private String nome ;
    private String  telefone;
    private String  CPF ;
    private String  data_nascimento;
    private String  endereco ;
    private String  email ;
    private String  foto_momento ;
    //private String  foto_doc ;

    public Adotante(int idAdotante, String nome, String telefone,
                    String CPF, String data_nascimento, String endereco, String email, String foto_momento) {
        this.idAdotante =idAdotante;
        this.nome = nome;
        this.telefone = telefone;
        this.CPF = CPF;
        this.data_nascimento = data_nascimento;
        this.endereco = endereco;
        this.email = email;
        this.foto_momento = foto_momento;
    }

    public Adotante(String nome, String telefone, String CPF, String data_nascimento, String endereco, String email, String foto_momento) {
        this.nome = nome;
        this.telefone = telefone;
        this.CPF = CPF;
        this.data_nascimento = data_nascimento;
        this.endereco = endereco;
        this.email = email;
        this.foto_momento = foto_momento;
    }

    public int getId() {
        return idAdotante;
    }

    public void setId(int idAdotante) {
        this.idAdotante =idAdotante;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getData_nascimento() {
        return data_nascimento;
    }

    public void setData_nascimento(String data_nascimento) {
        this.data_nascimento = data_nascimento;
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
        return "Adotante{" + "id=" +idAdotante + ", nome='" +
                nome + '\'' + ", telefone='" + telefone + '\'' + ", CPF='" + CPF
                + '\'' + ", data_nascimento='" + data_nascimento + '\'' +
                ", endereco='" + endereco + '\'' + ", email='" + email +
                '\'' + ", foto_momento='" + foto_momento + '\'' + '}';
    }
}
