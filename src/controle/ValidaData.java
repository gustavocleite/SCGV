package controle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ValidaData {


    public  boolean validarData(String dataString) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        format.setLenient(false);

        try {
            Date dataInserida = format.parse(dataString);
            Date dataAtual = new Date(); // Obtém a data atual

            // Compara as datas
            if (dataInserida.after(dataAtual)) {
                return false; // A data inserida é uma data futura
            } else {
                return true; // A data inserida é válida
            }
        } catch (ParseException e) {
            return false; // A data não está no formato correto
        }
    }
}
