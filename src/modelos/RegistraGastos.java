/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

/**
 *
 * @author Gustavo Camargo
 */
public class RegistraGastos {

    private Integer id = 0;
    private String data = "";
    private float valorGasto = 0;
    private String descricao = "";
    private String CPFProprietario = null;
    private String nomeProprietario = "";
    private Integer idVeiculo = null;
    private String veiculo = "";
    private Integer idTipoGasto = null;
    private String tipoDeGasto = "";

    public RegistraGastos() {
        this.id = 0;
        this.data = "";
        this.valorGasto = 0;
        this.descricao = "";
        this.CPFProprietario = null;
        this.nomeProprietario = "";
        this.idVeiculo = null;
        this.veiculo = "";
        this.idTipoGasto = null;
        this.tipoDeGasto = "";
    }

    public RegistraGastos(Integer id, String data, float valorGasto ,String descricao, String CPFProprietario,String nomeProprietario, Integer idVeiculo, String veiculo, Integer idTipoGasto,String tipoDeGasto) {
        this.id = id;
        this.data = data;
        this.valorGasto = valorGasto;
        this.descricao = descricao;
        this.CPFProprietario = CPFProprietario;
        this.nomeProprietario = nomeProprietario;
        this.idVeiculo = idVeiculo;
        this.veiculo = veiculo;
        this.idTipoGasto = idTipoGasto;
        this.tipoDeGasto = tipoDeGasto;
    }

    @Override
    public String toString() {
        return  id + ";" + data + ";" + valorGasto + ";" + descricao + ";" + CPFProprietario + ";" + nomeProprietario + ";" + idVeiculo + ";" + veiculo + ";" + idTipoGasto + ";" + tipoDeGasto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public float getValorGasto() {
        return valorGasto;
    }

    public void setValorGasto(float valorGasto) {
        this.valorGasto = valorGasto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCPFProprietario() {
        return CPFProprietario;
    }

    public void setCPFProprietario(String CPFProprietario) {
        this.CPFProprietario = CPFProprietario;
    }

    public String getNomeProprietario() {
        return nomeProprietario;
    }

    public void setNomeProprietario(String nomeProprietario) {
        this.nomeProprietario = nomeProprietario;
    }

    public Integer getIdVeiculo() {
        return idVeiculo;
    }

    public void setIdVeiculo(Integer idVeiculo) {
        this.idVeiculo = idVeiculo;
    }

    public String getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(String veiculo) {
        this.veiculo = veiculo;
    }

    public Integer getIdTipoGasto() {
        return idTipoGasto;
    }

    public void setIdTipoGasto(Integer idTipoGasto) {
        this.idTipoGasto = idTipoGasto;
    }

    public String getTipoDeGasto() {
        return tipoDeGasto;
    }

    public void setTipoDeGasto(String tipoDeGasto) {
        this.tipoDeGasto = tipoDeGasto;
    }
}
