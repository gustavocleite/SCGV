/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

/**
 *
 * @author Gustavo Camargo
 */
public class EnumDDI {

    public enum DDI {
        ESTADOS_UNIDOS("+1"),
        ESPANHA("+34"),
        MEXICO("+52"),
        BRASIL("+55"),
        ARGENTINA("+54"),
        PARAGUAI("+595"),
        URUGUAI("+598");

        private final String code;

        DDI(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }
}
