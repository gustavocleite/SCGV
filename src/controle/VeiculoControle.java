/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controle;

import java.util.ArrayList;
import modelos.IVeiculoCRUD;
import modelos.Veiculo;
import persistencia.VeiculoDAO;

/**
 *
 * @author Gustavo Camargo
 */
public class VeiculoControle implements IVeiculoCRUD {

    IVeiculoCRUD veiculoDAO = null;
    RedimensionarImagem operacoes = null;

    public VeiculoControle() {
        veiculoDAO = new VeiculoDAO();
        operacoes = new RedimensionarImagem();
    }

    @Override
    public void incluir(Veiculo veiculo) throws Exception {
        try {
            if (veiculoDAO.consultar(veiculo.getId()) != null) {
                throw new Exception("ID ja cadastrado!");
            }
            if (veiculo.getId() == -1) {
                throw new Exception("Digite um ID!");
            }
            if (veiculo.getProprietario().equals("Selecione o proprietario")) {
                throw new Exception("Selecione um proprietario!");
            }
            if (veiculo.getProprietario().equals("")) {
                throw new Exception("Selecione um proprietario!");
            }
            if (veiculo.getPlaca().isBlank()) {
                throw new Exception("Digite uma placa!!");
            }
            String placa = veiculo.getPlaca();
            String placaSemPontuacao = placa.replaceAll("[^a-zA-Z0-9]", "");
            if (!operacoes.validarPlaca(placaSemPontuacao)) {
                throw new Exception("Placa inválida");
            }
            if (veiculo.getMarca().equals("Selecione uma marca.")) {
                throw new Exception("Selecione uma marca!");
            }
            if (veiculo.getModelo().equals("Selecione o modelo")) {
                throw new Exception("Selecione o modelo!");
            }

            if (veiculo.getTipoCombustivel().equals("")) {
                throw new Exception("Selecione tipo de combustível!");
            }
            if (veiculo.getKmAtual().isBlank()) {
                throw new Exception("Digite o KM atual!");
            }
            if (veiculo.getFotoMarca() == null) {
                throw new Exception("Marca sem foto!!");
            }
            if (veiculo.getFotoVeiculo() == null) {
                throw new Exception("Selecione um modelo, caso não tenha, favor criar de acordo com a marca!");
            }
            veiculoDAO.incluir(veiculo);
        } catch (Exception erro) {
            throw new Exception(erro.getMessage());
        }

    }

    @Override
    public void alterar(Veiculo veiculo) throws Exception {
        try {
            if (veiculo.getProprietario().equals("Selecione o proprietario")) {
                throw new Exception("Selecione um proprietario!");
            }
            if (veiculo.getTipoCombustivel().equals("")) {
                throw new Exception("Selecione tipo de combustível!");
            }
            veiculoDAO.alterar(veiculo);
        } catch (Exception erro) {
            throw new Exception(erro.getMessage());
        }

    }

    @Override
    public ArrayList<Veiculo> listagemDeVeiculos() throws Exception {
        return veiculoDAO.listagemDeVeiculos();
    }

    @Override
    public Veiculo consultar(Integer id) throws Exception {
        return veiculoDAO.consultar(id);
    }

}
