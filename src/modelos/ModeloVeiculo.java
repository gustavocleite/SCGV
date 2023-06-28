package modelos;

import java.io.File;

public class ModeloVeiculo {

    private Integer id = 0;
    private String descricao = "";
    private String nomeMarca = "";
    private String tipoModelo = "";
    private Integer idMarca = 0;
    private File fotoModelo = null;

    public ModeloVeiculo() {
        id = null;
        descricao = "";
        nomeMarca = "";
        tipoModelo = "";
        idMarca = null;
        fotoModelo = null;
    }

    public ModeloVeiculo(Integer id, String descricao, String nomeMarca, String tipoModelo, File fotoVeiculo, Integer idMarca) {
        this.id = id;
        this.descricao = descricao;
        this.nomeMarca = nomeMarca;
        this.tipoModelo = tipoModelo;
        this.fotoModelo = fotoVeiculo;
        this.idMarca = idMarca;
    }

    @Override
    public String toString() {
        return id + ";" + descricao + ";" + nomeMarca + ";" + tipoModelo + ";" + fotoModelo.getPath() + ";" + idMarca;
    }

    public String saidaTabela() {
        return id + ";" + descricao + ";" + nomeMarca + ";" + tipoModelo;
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

    public String getNomeMarca() {
        return nomeMarca;
    }

    public void setNomeMarca(String nomeMarca) {
        this.nomeMarca = nomeMarca;
    }

    public String getTipoModelo() {
        return tipoModelo;
    }

    public void setTipoModelo(String tipoModelo) {
        this.tipoModelo = tipoModelo;
    }

    public Integer getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(Integer idMarca) {
        this.idMarca = idMarca;
    }

    public File getFotoModelo() {
        return fotoModelo;
    }

    public void setFotoModelo(File fotoModelo) {
        this.fotoModelo = fotoModelo;
    }
}
