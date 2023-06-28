
package modelos;

import java.io.File;

public class Proprietario {

    // Atributos
    private String cpf = "";             //CHAVE PARA BUSCA DE PROPRIETÁRIO
    private String nomeCompleto = "";
    private String email = "";
    private Telefone fone = null;    
    private String categoriaCnh = "";
    private String numeroCNH = "";
    private File pathCNH = null;
    private File patchImagemProprietario = null;

    // Construtores
    public Proprietario() {
        cpf = "";
        nomeCompleto = "";
        email = "";
        fone = null;
        categoriaCnh = "";
        numeroCNH = null;
        pathCNH = null;
        patchImagemProprietario = null;
    }

    public Proprietario(String cpf, String nomeCompleto, String email, Telefone fone, String numeroCNH, String categoriaCnh, File pathCNH, File patchImagemProprietario) {
        this.cpf = cpf;
        this.nomeCompleto = nomeCompleto;
        this.email = email;
        this.fone = fone;
        this.categoriaCnh = categoriaCnh;
        this.numeroCNH = numeroCNH;
        this.pathCNH = pathCNH;
        this.patchImagemProprietario = patchImagemProprietario;
    }

    @Override
    public String toString() {
        return cpf + ";" + nomeCompleto + ";" + email + ";" + fone.toString() + ";" + numeroCNH +  ";"  + categoriaCnh + ";" + pathCNH.getPath() + ";" + patchImagemProprietario.getPath();
    }
    
    // Getters e Setters
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Telefone getFone() {
        return fone;
    }

    public void setFone(Telefone fone) {
        this.fone = fone;
    }

    public File getPathCNH() {
        return pathCNH;
    }

    public void setPathCNH(File pathCNH) {
        this.pathCNH = pathCNH;
    }

    public String getCategoriaCnh() {
        return categoriaCnh;
    }

    public void setCategoriaCnh(String categoriaCnh) {
        this.categoriaCnh = categoriaCnh;
    }

    public String getNumeroCNH() {
        return numeroCNH;
    }

    public void setNumeroCNH(String numeroCNH) {
        this.numeroCNH = numeroCNH;
    }

    public File getPatchImagemProprietario() {
        return patchImagemProprietario;
    }

    public void setPatchImagemProprietario(File patchImagemProprietario) {
        this.patchImagemProprietario = patchImagemProprietario;
    }
}
