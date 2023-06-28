
package modelos;

import java.util.ArrayList;

public interface IPropietarioCRUD {
    public void Incluir(Proprietario objPropietario) throws Exception;

    public void alterar(Proprietario proprietario) throws Exception;

    public ArrayList<Proprietario> listagemDePropietario() throws Exception;

    public Proprietario consultar(String cpf) throws Exception;
    //public Proprietario buscar(String cpf) throws Exception;
}
