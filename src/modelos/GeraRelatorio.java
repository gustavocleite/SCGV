/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author user
 */
public class GeraRelatorio {
    
    SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
    private Integer IDGasto = null;
    private String proprietario = null;
    private String veiculo = null;
    private Date dataGasto = null;
    private String tipoDeGasto = null;
    private Float valorGasto = null;

    public GeraRelatorio() {
    }
    
    public GeraRelatorio(Integer IDGasto, String proprietario, String veiculo,String data, String tipoDeGasto ,Float valorGasto) throws ParseException {
        this.IDGasto = IDGasto;
        this.proprietario = proprietario;
        this.veiculo = veiculo;
        dataGasto = formatador.parse(data);
        this.tipoDeGasto = tipoDeGasto;
        this.valorGasto = valorGasto;
    }

    public String getTipoDeGasto() {
        return tipoDeGasto;
    }

    public void setTipoDeGasto(String tipoDeGasto) {
        this.tipoDeGasto = tipoDeGasto;
    }

    public String getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(String veiculo) {
        this.veiculo = veiculo;
    }
      
    public SimpleDateFormat getFormatador() {
        return formatador;
    }

    public void setFormatador(SimpleDateFormat formatador) {
        this.formatador = formatador;
    }

    public Integer getIDGasto() {
        return IDGasto;
    }

    public void setIDGasto(Integer IDGasto) {
        this.IDGasto = IDGasto;
    }

    public String getProprietario() {
        return proprietario;
    }

    public void setProprietario(String proprietario) {
        this.proprietario = proprietario;
    }

    public Date getDataGasto() {
        return dataGasto;
    }

    public void setDataGasto(String dataGasto) throws ParseException {
        this.dataGasto = formatador.parse(dataGasto);
    }

    public Float getValorGasto() {
        return valorGasto;
    }

    public void setValorGasto(Float valorGasto) {
        this.valorGasto = valorGasto;
    }
    
    @Override
    public String toString(){
        return IDGasto + ";" + proprietario + ";" + veiculo +";" + formatador.format(dataGasto) + ";" + tipoDeGasto + ";" + valorGasto;
    }
}
