package modelos;

import java.io.File;

public class Marca {
    
    private Integer id = 0;
    private String descricao = "";
    private File logo = null;
    
    public Marca (){
        this.id = 0;
        this.descricao = "";
        this.logo = null;
    }
    
    public Marca(Integer id, String descricao, File logo){
        this.id = id;
        this.descricao = descricao;
        this.logo = logo;
    }
    
    @Override
    public String toString(){
        return id + ";" + descricao + ";" + logo.getPath();
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

    public File getLogo() {
        return logo;
    }

    public void setLogo(File logo) {
        this.logo = logo;
    }

    
}

