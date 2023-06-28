/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

import java.io.File;

/**
 *
 * @author Gustavo Camargo
 */
public class TipoGastos {
    private Integer id = 0;
    private String nome = "";
    private String descricao = "";
    private File iconGasto;
    
    public TipoGastos() {
        this.id = 0;
        this.nome = "";
        this.descricao = "";
        iconGasto = null;
    }
    
    public TipoGastos(Integer id, String nome, String descricao, File iconGasto) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.iconGasto = iconGasto;
    }

    @Override
    public String toString() {
        return id + ";" + nome + ";" + descricao + ";" + iconGasto.getPath();
    }

    public File getIconGasto() {
        return iconGasto;
    }

    public void setIconGasto(File iconGasto) {
        this.iconGasto = iconGasto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
}
