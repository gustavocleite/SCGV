/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controle;

import java.util.ArrayList;
import modelos.RegistraGastos;
import persistencia.RegistraGastoDAO;
import modelos.IRegistraGastosCRUD;
import controle.ValidaData;

/**
 *
 * @author Gustavo Camargo
 */
public class RegistraGastoControle implements IRegistraGastosCRUD {

    IRegistraGastosCRUD registraGastoPersistencia = null;
    ValidaData validaData = null;

    public RegistraGastoControle() {
        registraGastoPersistencia = new RegistraGastoDAO();
        validaData = new ValidaData();
    }

    @Override
    public void incluir(RegistraGastos objRegistroGasto) throws Exception {
        try {
            if (objRegistroGasto.getNomeProprietario().isBlank()) {
                throw new Exception("Selecione um proprietario!");
            }
            if (objRegistroGasto.getCPFProprietario().isBlank()) {
                throw new Exception("Selecione um proprietario!");
            }
            if (objRegistroGasto.getIdVeiculo() == -1) {
                throw new Exception("Selecione um Veículo!");
            }
            if (objRegistroGasto.getVeiculo().isBlank()) {
                throw new Exception("Selecione um Veículo!");
            }
            if (objRegistroGasto.getIdTipoGasto() == -1) {
                throw new Exception("Selecione um Tipo de Gasto!");
            }
            if (objRegistroGasto.getTipoDeGasto().isBlank()) {
                throw new Exception("Selecione um Tipo de Gasto!");
            }
            if (!validaData.validarData(objRegistroGasto.getData())) {
                throw new Exception("Data invalida!");
            }
            if (objRegistroGasto.getId() == -1) {
                throw new Exception("Digite um ID!");
            }
            if (registraGastoPersistencia.consultar(objRegistroGasto.getId()) != null) {
                throw new Exception("ID ja registrado!!");
            }
            if (objRegistroGasto.getValorGasto() == 0.0f) {
                throw new Exception("Digite um Valor Gasto!");
            }
            if (objRegistroGasto.getDescricao().isBlank()) {
                throw new Exception("Digite uma descrição!");
            }

            registraGastoPersistencia.incluir(objRegistroGasto);
        } catch (Exception erro) {
            throw new Exception(erro.getMessage());
        }

    }

    @Override
    public ArrayList<RegistraGastos> listagemRegistroDeGastos() throws Exception {
        return registraGastoPersistencia.listagemRegistroDeGastos();
    }

    @Override
    public void alterar(RegistraGastos objRegistraGastos) throws Exception {
        try {
            if (!validaData.validarData(objRegistraGastos.getData())) {
                throw new Exception("Data invalida!");
            }
            registraGastoPersistencia.alterar(objRegistraGastos);
        } catch (Exception erro) {
            throw new Exception(erro.getMessage());
        }
    }

    @Override
    public RegistraGastos consultar(Integer id) throws Exception {
        return registraGastoPersistencia.consultar(id);
    }
}
