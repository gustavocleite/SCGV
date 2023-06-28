/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package modelos;

import java.util.ArrayList;

/**
 *
 * @author user
 */
public interface IModeloVeiculo {    
    public void incluir (ModeloVeiculo modelo) throws Exception;
    public void alterar(ModeloVeiculo modelo) throws Exception;
    public ArrayList<ModeloVeiculo> listagemDeModelos() throws Exception;
    public ModeloVeiculo consultar(Integer id) throws Exception;
}
