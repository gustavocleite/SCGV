package modelos;

import java.io.File;

public class Veiculo {

    private Integer id = null;
    private String CPFProprietario = "";
    private String proprietario = null;
    private String placa = "";
    private String marca = "";
    private Integer idMarca = null;
    private String modelo = "";
    private Integer idModelo = null;
    private String tipoCombustivel = "";
    private String kmAtual = "";
    private File fotoMarca = null;
    private File fotoVeiculo = null;

    public Veiculo() {
        id = null;
        CPFProprietario = "";
        proprietario = null;
        placa = "";
        marca = "";
        idMarca = null;
        modelo = "";
        idModelo = null;
        tipoCombustivel = "";
        kmAtual = "";
        fotoMarca = null;
        fotoVeiculo = null;
    }
    
    public Veiculo(Integer id,String CPFProprietario,String proprietario,String placa,String marca,Integer idMarca,String modelo,Integer idModelo,String tipoCombustivel,String kmAtual,File fotoMarca,File fotoVeiculo) {
        this.id = id;
        this.CPFProprietario = CPFProprietario;
        this.proprietario = proprietario;
        this.placa = placa;
        this.marca = marca;
        this.idMarca = idMarca;
        this.modelo = modelo;
        this.idModelo = idModelo;
        this.tipoCombustivel = tipoCombustivel;
        this.kmAtual = kmAtual;
        this.fotoMarca = fotoMarca;
        this.fotoVeiculo = fotoVeiculo;
    }

    @Override
    public String toString() {
        return  id + ";" + CPFProprietario + ";" + proprietario + ";" + placa + ";" + marca + ";" + idMarca + ";" + modelo + ";" + idModelo + ";" + tipoCombustivel + ";" + kmAtual + ";" + fotoMarca.getPath() + ";" + fotoVeiculo.getPath();
    }
    
    public String toTabela(){
        return  id + ";" + proprietario + ";" + placa + ";" + marca + ";" + modelo + ";" + tipoCombustivel + ";" + kmAtual;
    }

    public String getCPFProprietario() {
        return CPFProprietario;
    }

    public void setCPFProprietario(String CPFProprietario) {
        this.CPFProprietario = CPFProprietario;
    }
    
    public Integer getId() {
        return id;
    }

    public String getProprietario() {
        return proprietario;
    }

    public void setProprietario(String proprietario) {
        this.proprietario = proprietario;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Integer getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(Integer idMarca) {
        this.idMarca = idMarca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Integer getIdModelo() {
        return idModelo;
    }

    public void setIdModelo(Integer idModelo) {
        this.idModelo = idModelo;
    }

    public String getTipoCombustivel() {
        return tipoCombustivel;
    }

    public void setTipoCombustivel(String tipoCombustivel) {
        this.tipoCombustivel = tipoCombustivel;
    }

    public String getKmAtual() {
        return kmAtual;
    }

    public void setKmAtual(String kmAtual) {
        this.kmAtual = kmAtual;
    }

    public File getFotoMarca() {
        return fotoMarca;
    }

    public void setFotoMarca(File fotoMarca) {
        this.fotoMarca = fotoMarca;
    }

    public File getFotoVeiculo() {
        return fotoVeiculo;
    }

    public void setFotoVeiculo(File fotoVeiculo) {
        this.fotoVeiculo = fotoVeiculo;
    }
    
    
}
