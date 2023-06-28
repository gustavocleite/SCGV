/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controle;

import java.util.ArrayList;
import modelos.TipoGastos;
import persistencia.TipoGastosDAO;
import modelos.ITipoGastosCRUD;

/**
 *
 * @author Gustavo Camargo
 */
public class TipoGastosControle implements ITipoGastosCRUD {

    ITipoGastosCRUD tipoGastosPersistencia = null;

    public TipoGastosControle() {
        tipoGastosPersistencia = new TipoGastosDAO();
    }

    @Override
    public void incluir(TipoGastos objTipoGastos) throws Exception {
        try {
            if (objTipoGastos.getId() == -1) {
                throw new Exception("Preencha o campo ID!!");
            }
            // Verifica se ja existe o ID no BD
            if (consultar(objTipoGastos.getId()) != null) {
                throw new Exception("ID ja registrado");
            }

            // Verifica se o campo nome esta vazio.
            if (objTipoGastos.getNome().isBlank()) {
                throw new Exception("Preencha o campo Nome do gasto!!");
            }

            // Verifica se o campo descrição esta vazio
            if (objTipoGastos.getDescricao().isBlank()) {
                throw new Exception("Preencha o campo descrição!!");
            }

            // Valida se foi selecionado uma foto
            if (objTipoGastos.getIconGasto() == null) {
                throw new Exception("Selecione uma foto!!");
            }

            tipoGastosPersistencia.incluir(objTipoGastos);
        } catch (Exception erro) {
            throw new Exception(erro.getMessage());
        }
    }

    @Override
    public ArrayList<TipoGastos> listagemDeGastos() throws Exception {
        return tipoGastosPersistencia.listagemDeGastos();
    }

    @Override
    public void alterar(TipoGastos objTipoGastos) throws Exception {
        try {
            // Verifica se o campo nome esta vazio.
            if (objTipoGastos.getNome().isBlank()) {
                throw new Exception("CAMPO OBRIGATORIO! Preencha o campo Nome do gasto!!");
            }

            // Verifica se o campo descrição esta vazio
            if (objTipoGastos.getDescricao().isBlank()) {
                throw new Exception("CAMPO OBRIGATORIO! Preencha o campo descrição!!");
            }

            tipoGastosPersistencia.alterar(objTipoGastos);
        } catch (Exception erro) {
            String msg = "Metodo alterar TipoGastosControle - " + erro.getMessage();
            throw new Exception(msg);
        }

    }

    @Override
    public TipoGastos consultar(Integer id) throws Exception {
        try {
            if (id == null) {
                throw new Exception("Tipo de Gastos não existe!");
            }
            // Verifica se ja existe o ID no BD
            //if( tipoGastosPersistencia.consultar(id) != null) throw new Exception("ID ja registrado");
            return tipoGastosPersistencia.consultar(id);
        } catch (Exception erro) {
            String msg = "Metodo consultar TipoGastosControle - " + erro.getMessage();
            throw new Exception(msg);
        }
    }

//    @Override
//    public TipoGastos buscar(Integer id) throws Exception {
//        try {
//            if ( id == null ) throw new Exception("CAMPO OBRIGATORIO! Preencha o campo ID!!");
//            // Verifica se ja existe o ID no BD
//            return tipoGastosPersistencia.consultar(id);          
//        } catch (Exception erro) {
//            String msg = "Metodo buscar TipoGastosControle - " +erro.getMessage();
//            throw new Exception(msg);
//        }
//        
//    }
}
