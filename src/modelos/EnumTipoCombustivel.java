package modelos;

public class EnumTipoCombustivel {
    
    public EnumTipoCombustivel(){
    
    }

    public enum TipoCombustivel {
        
        GASOLINA_COMUM,
        GASOLINA_ADITIVADA,
        ETANOL,
        FLEX,
        DIESEL_COMUM,
        DIESEL_S_10,
        GNV,
        ELETRICIDADE,
        DIESEL_ADITIVADO,
        DIESEL_S_500,
        BIODIESEL,
        DIESEL_B10,
        DIESEL_B20,
        DIESEL_B100,
        GASOLINA_PREMIUM,
        ETANOL_ADITIVADO,
        HIDROGÊNIO;

        public static TipoCombustivel[] getValores() {
            return TipoCombustivel.values();
        }
    }

}
