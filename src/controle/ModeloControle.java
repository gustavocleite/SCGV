package controle;

import java.util.ArrayList;
import modelos.ModeloVeiculo;
import modelos.IModeloVeiculo;
import persistencia.ModeloDAO;

/**
 *
 * @author user
 */
public class ModeloControle implements IModeloVeiculo {

    private IModeloVeiculo modelos = null;

    public ModeloControle() {
        modelos = new ModeloDAO();
    }

    @Override
    public void incluir(ModeloVeiculo veiculo) throws Exception {
        try {
            //Valida Campo ID
            if (veiculo.getId() == -1) {
                throw new Exception("Digite um ID!!");
            }

            // verifica se ja existe o ID cadastrado
            if (modelos.consultar(veiculo.getId()) != null) {
                throw new Exception("ID ja cadastrado!");
            }

            //valida campo descrição 
            if (veiculo.getDescricao().isBlank()) {
                throw new Exception("Digite uma Descrição!!");
            }

            //Valida se foi selecionado um campo no combobox Marca
            if (veiculo.getNomeMarca().equals("Selecione uma marca.")) {
                throw new Exception("Selecione uma Marca!!");
            }

            //Valida se foi selecionado um campo no combobox DD
            if (veiculo.getTipoModelo().equals("Selecione o tipo.")) {
                throw new Exception("Selecione um Tipo!!");
            }

            //Valida se foi inserido uma foto 
            if (veiculo.getFotoModelo() == null) {
                throw new Exception("Selecione uma foto do Modelo!!");
            }

            // Validação feita, passa para persistencia.
            modelos.incluir(veiculo);
        } catch (Exception erro) {
            throw new Exception(erro.getMessage());
        }

    }

    @Override
    public void alterar(ModeloVeiculo veiculo) throws Exception {
        try {
            //valida campo descrição 
            if (veiculo.getDescricao().isBlank()) {
                throw new Exception("Digite uma Descrição!!");
            }

            //Valida se foi selecionado um campo no combobox Marca
            if (veiculo.getNomeMarca().equals("Selecione uma marca.")) {
                throw new Exception("Selecione uma Marca!!");
            }

            //Valida se foi selecionado um campo no combobox Tipo.
            if (veiculo.getTipoModelo().equals("Selecione o tipo.")) {
                throw new Exception("Selecione um Tipo!!");
            }

            if (veiculo.getFotoModelo() == null) {
                throw new Exception("Selecione uma foto!!");
            }
            modelos.alterar(veiculo);
        } catch (Exception erro) {
            throw new Exception(erro.getMessage());
        }
    }

    @Override
    public ArrayList<ModeloVeiculo> listagemDeModelos() throws Exception {
        try {
            return modelos.listagemDeModelos();
        } catch (Exception erro) {
             throw new Exception(erro.getMessage());
        }
        
    }

    @Override
    public ModeloVeiculo consultar(Integer id) throws Exception {
        return modelos.consultar(id);
    }

}
