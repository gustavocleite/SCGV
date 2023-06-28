/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelos.TipoGastos;
import modelos.ITipoGastosCRUD;


/**
 *
 * @author Gustavo Camargo
 */
public class TipoGastosDAO implements ITipoGastosCRUD{
    private String nomeDoArquivo = null;
    private String destinoImagens = null;
    
    public TipoGastosDAO() {
        nomeDoArquivo = ".\\src\\dados\\TipoDeGastos.txt";
        destinoImagens = ".\\src\\img\\tipoGastos\\";
    }
    @Override
    public void incluir(TipoGastos objTipoGastos) throws Exception {
        try {
            File source = objTipoGastos.getIconGasto();
            String[] nomeArquivo = source.getName().split("\\.");
            File destinationFile = new File(destinoImagens + objTipoGastos.getId()+ "." + nomeArquivo[1]);
            try {
                Files.copy(source.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                objTipoGastos.setIconGasto(destinationFile);
                source.delete();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Erro ao copiar o arquivo.");
            }
            try (BufferedWriter buffWrite = new BufferedWriter(new FileWriter(nomeDoArquivo, true))) {
                buffWrite.append(objTipoGastos.toString() + "\n");
                buffWrite.close();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        } catch (Exception erro) {
            String msg = "Metodo Incluir TipoGastosDAO - " + erro.getMessage();
            throw new Exception(msg);
        }
    }

    @Override
    public ArrayList<TipoGastos> listagemDeGastos() throws Exception {
        ArrayList<TipoGastos> gastos = new ArrayList();
        try {
            TipoGastos tipoGastos;
            FileReader fr = new FileReader(nomeDoArquivo);
            BufferedReader br  = new BufferedReader(fr);
            String linha = "";
            while((linha=br.readLine())!=null) {
                String tipoGasto[] = linha.split(";");
                File file = new File(tipoGasto[3]);
                tipoGastos = new TipoGastos(Integer.valueOf(tipoGasto[0]),tipoGasto[1], tipoGasto[2], file);
                gastos.add(tipoGastos);
            }
            return gastos;
         } catch(IOException | NumberFormatException erro) {
            String msg = "Metodo listagemDeGastos TipoGastosDAO - "+erro.getMessage();
            throw new Exception(msg);
        }
    }

    @Override
    public void alterar(TipoGastos objTipoGastos) throws Exception {
        int novaLinha = 0; //linha que será alterada
        try {
            FileReader fr = new FileReader(nomeDoArquivo);
            try (BufferedReader br = new BufferedReader(fr)) {
                String linha = "";
                while ((linha = br.readLine()) != null) {
                    String[] dados = linha.split(";");
                    if (objTipoGastos.getId().equals(Integer.valueOf(dados[0]))) {
                        break;
                    } else {
                        novaLinha++;
                    }
                }
                br.close();
            }
        } catch (IOException e) {
            throw new Exception("Não foi possível abrir o arquivo");
        }
        File source = objTipoGastos.getIconGasto();
        
        String[] nomeArquivo = source.getName().split("\\.");
        
        File destinationFile = new File(destinoImagens + objTipoGastos.getId()+ "." + nomeArquivo[1]);
        try {
            Files.copy(source.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            objTipoGastos.setIconGasto(destinationFile);
            source.delete();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao copiar o arquivo.");
        }
        try {
            Path arquivoPath = Path.of(nomeDoArquivo);
            List<String> linhas = Files.readAllLines(arquivoPath, StandardCharsets.UTF_8);
            linhas.set(novaLinha, objTipoGastos.toString()); // Altera a linha desejada
            Files.write(arquivoPath, linhas, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new Exception("Não foi possível alterar o arquivo");
        }
    }

    @Override
    public TipoGastos consultar(Integer id) throws Exception {
              try {
            FileReader fr = new FileReader(nomeDoArquivo);
            try (BufferedReader br = new BufferedReader(fr)) {
                TipoGastos tipoDeGastos = null;
                String linha = "";
                while ((linha = br.readLine()) != null) {
                    String[] dados = linha.split(";");
                    if (id.equals(Integer.valueOf(dados[0]))) {
                        br.close();                     
                        File file = new File(dados[3]);
                        tipoDeGastos = new TipoGastos(Integer.valueOf(dados[0]), dados[1], dados[2], file);
                        return tipoDeGastos;
                    }
                }
                br.close();
                return tipoDeGastos;
            }
        } catch (IOException erro) {
            throw new Exception("Nao foi possivel consultar o proprietario" + erro.getMessage());
        }
    }

//    @Override
//    public TipoGastos buscar(Integer id) throws Exception {
//        try {
//            FileReader fr = new FileReader(nomeDoArquivo);   //fr recebe o caminho do arquivo no disco
//            try ( BufferedReader br = new BufferedReader(fr)) {
//                String linha = "";
//                while ((linha = br.readLine()) != null) {
//                    String[] dados = linha.split(";");
//                    if (id.equals(Integer.valueOf(dados[0]))) {
//                        br.close();
//                        File file = new File(dados[3]);
//                        return (new TipoGastos(Integer.valueOf(dados[0]), dados[1], dados[2],file));
//                    }
//                }
//                br.close();
//                return null;
//            }
//        } catch (IOException erro) {
//            throw new Exception("Nao foi possivel listar os tipos de Gastos " + erro.getMessage());
//        }
//    }
    
}
