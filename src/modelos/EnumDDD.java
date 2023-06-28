/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

/**
 *
 * @author Gustavo Camargo
 */
public class EnumDDD {

    public enum DDD {
        SP("(11)"),
        RJ("(21)"),
        ES("(27)"),
        MG("(31)"),
        PR("(41)"),
        SC("(47)"),
        RS("(51)"),
        DF("(61)"),
        GO("(62)"),
        TO("(63)"),
        MT("(65)"),
        MS("(67)"),
        AC("(68)"),
        RO("(69)"),
        BA("(71)"),
        SE("(79)"),
        PE("(81)"),
        AL("(82)"),
        PB("(83)"),
        RN("(84)"),
        CE("(85)"),
        PA("(91)"),
        AM("(92)"),
        RR("(95)"),
        AP("(96)"),
        MA("(98)");

        private final String code;

        DDD(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

}
