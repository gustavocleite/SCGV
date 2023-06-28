/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controle;

/**
 *
 * @author brunocoronha.adm
 */
public class ValidarCNH {
    
public boolean validarCNH(String numeroCNH) {
        // Expressão regular para validar o número da CNH
        String regex = "^[0-9]{11}$";

        if (numeroCNH.matches(regex)) {
            // Verifica a validade do número da CNH
            int[] numeros = new int[11];
            for (int i = 0; i < 11; i++) {
                numeros[i] = Character.getNumericValue(numeroCNH.charAt(i));
            }

            // Verifica se todos os dígitos são iguais
            boolean digitosIguais = true;
            for (int i = 1; i < 11; i++) {
                if (numeros[i] != numeros[i - 1]) {
                    digitosIguais = false;
                    break;
                }
            }

            if (!digitosIguais) {
                // Verifica o dígito verificador
                int soma = 0;
                int peso = 9;
                for (int i = 0; i < 9; i++) {
                    soma += numeros[i] * peso;
                    peso--;
                }

                int resto = soma % 11;
                int digitoVerificador1 = (resto >= 10) ? 0 : resto;

                soma = 0;
                peso = 1;
                for (int i = 0; i < 9; i++) {
                    soma += numeros[i] * peso;
                    peso++;
                }

                resto = soma % 11;
                int digitoVerificador2 = (resto >= 10) ? 0 : resto;

                return (digitoVerificador1 == numeros[9] && digitoVerificador2 == numeros[10]);
            }
        }

        return false;
    }
}
    
