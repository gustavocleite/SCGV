/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controle;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author gustavocamargo.adm
 */
public class ValidarNome {

    public boolean validarNome(String nome) {
        if (nome == null || nome.isEmpty()) {
            return false; // Verifica se o nome é nulo ou vazio
        }

        String regex = "^[\\p{L} .'-]+$"; // Regex melhorada para aceitar caracteres acentuados

        Pattern pattern = Pattern.compile(regex, Pattern.UNICODE_CHARACTER_CLASS);
        Matcher matcher = pattern.matcher(nome);

        if (!matcher.matches()) {
            return false; // Verifica se o nome não corresponde ao padrão
        }

        String[] partesNome = nome.split("\\s+");

        if (partesNome.length < 2) {
            return false; // Verifica se o nome tem pelo menos um sobrenome
        }

        for (String parte : partesNome) {
            if (parte.length() < 2) {
                return false; // Verifica se cada parte do nome tem pelo menos duas letras
            }
        }

        return true; // O nome passou em todas as validações
    }

}
