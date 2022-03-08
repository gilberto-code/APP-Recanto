package com.recantodosanimaisong.recantodosanimaisprojeto.Conexao;

public interface Links {
    public static final String SERVER_ID = "15.228.147.23";
    public static final String ENVIO_ANIMAL = "http://"+SERVER_ID+"/php/inserir_animal.php";
    public static final String ENVIO_IMAGEM = "http://"+SERVER_ID+"/php/envio_imagem.php";
    public static final String PEGAR_ANIMAIS = "http://"+SERVER_ID+"/php/pegar_animais.php";
    public static final String PESQUISA_NOME = "http://"+SERVER_ID+"/php/pegar_animais_like.php";
    public static final String LOGIN = "http://"+SERVER_ID+"/php/login.php";
    public static final String CADASTRO_USER = "http://"+SERVER_ID+"/php/cadastro_user.php";
    public static final String ATUALIZAR_USER = "http://"+SERVER_ID+"/php/atualizar_user.php";
    public static final String PEGAR_USER = "http://"+SERVER_ID+"/php/pegar_user.php";

    public static final String LOGIN_PREFERENCE = "LOGIN";


}
