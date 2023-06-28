
package modelos;

public class Telefone {

    private String ddi = "";
    private String ddd = "";
    private String numeroTelefone = "";

    public Telefone() {
        ddi = "";
        ddd = "";
        numeroTelefone = "";
    }

    public Telefone(String ddi, String ddd, String numeroTelefone) {
        this.ddi = ddi;
        this.ddd = ddd;
        this.numeroTelefone = numeroTelefone;

    }
    
    @Override
    public String toString() {
        return ddi + ";" + ddd + ";" + numeroTelefone;
    }
    
    public String getDdi() {
        return ddi;
    }

    public void setDdi(String ddi) {
        this.ddi = ddi;
    }

    public String getDdd() {
        return ddd;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public String getNumeroTelefone() {
        return numeroTelefone;
    }

    public void setNumeroTelefone(String numeroTelefone) {
        this.numeroTelefone = numeroTelefone;
    }



}
