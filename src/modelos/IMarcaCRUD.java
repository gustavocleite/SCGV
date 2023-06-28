package modelos;


import java.util.ArrayList;

public interface IMarcaCRUD {
    public void incluir(Marca marca) throws Exception;
    public void alterar(Marca marca) throws Exception;
    public ArrayList<Marca> listagemDeMarcas()  throws Exception;
    public Marca consulaMarca(Integer id) throws Exception;
}
