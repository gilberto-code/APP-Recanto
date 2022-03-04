package com.recantodosanimaisong.recantodosanimaisprojeto.Model;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.security.SecureRandom;
/**
 *
 * @author gilberto
 */
public class RandomString {
    private static final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-_";
    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * Generates random string of given length from Base65 alphabet (numbers, lowercase letters, uppercase letters).
     *
     * @param count length
     * @return random string of given length
     */
    public String generate(int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; ++i) {
            sb.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return sb.toString();
    }
}
