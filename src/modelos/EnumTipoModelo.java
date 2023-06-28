package modelos;

public class EnumTipoModelo {
    
    public EnumTipoModelo(){
    
    }

    public enum TipoModeloVeiculo {
        SEDAN,
        SUV,
        HATCHBACK,
        COUPE,
        CONVERSÍVEL,
        MINIVAN,
        PICAPE,
        CAMINHONETE,
        CROSSOVER,
        WAGON,
        ESPORTIVO,
        ELÉTRICO,
        HÍBRIDO,
        LIMUSINE,       
        OFF_ROAD,
        FAMILIAR,
        MUSCLE_CAR,
        CARRO_DE_LUXO,
        CARRO_CLÁSSICO,
        MOTO,
        MOTONETA;

        public static TipoModeloVeiculo[] getValores() {
            return TipoModeloVeiculo.values();
        }
    }

}
