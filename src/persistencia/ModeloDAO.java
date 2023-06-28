/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import controle.RedimensionarImagem;
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
import modelos.ModeloVeiculo;
import modelos.IModeloVeiculo;

/**
 *
 * @author user
 */
public class ModeloDAO implements IModeloVeiculo {

    private String nomeDoArquivoNoDisco = null;
    private RedimensionarImagem redimensionarImagem = null;

    public ModeloDAO() {
        nomeDoArquivoNoDisco = ".\\src\\dados\\modelos.txt";
        redimensionarImagem = new RedimensionarImagem();
    }

    @Override
    public void incluir(ModeloVeiculo modelo) throws Exception {
        File source = redimensionarImagem.redimensionarImagem(modelo.getFotoModelo());
        //modeloVeiculo.txt 
        String[] nomeArquivo = source.getName().split("\\.");
        //System.out.println("Extensão: " + nomeArquivo[1]);
        File destinationFile = new File(".\\src\\img\\modeloVeiculo\\" + modelo.getId() + "." + nomeArquivo[1]);
        try {
            Files.copy(source.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            
            modelo.setFotoModelo(destinationFile);
            source.delete();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao copiar o arquivo.");
        }

        try ( BufferedWriter buffWrite = new BufferedWriter(new FileWriter(nomeDoArquivoNoDisco, true))) {
            //marca.setLogo(destinationPath + File.separator + marca.toString().split(";")[0] + "." + nomeArquivo[1]);
            buffWrite.append(modelo.toString() + "\n");
            buffWrite.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    @Override
    public void alterar(ModeloVeiculo modelo) throws Exception {
        int novaLinha = 0; //linha que será alterada
        try {
            FileReader fr = new FileReader(nomeDoArquivoNoDisco);
            try ( BufferedReader br = new BufferedReader(fr)) {
                String linha = "";
                while ((linha = br.readLine()) != null) {
                    String[] dados = linha.split(";");
                    if (modelo.getId().equals(Integer.valueOf(dados[0]))) {
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
        //aqui eh feita a copia da imagem referente a CNH do proprietário
        File source = redimensionarImagem.redimensionarImagem(modelo.getFotoModelo());
        String[] nomeArquivo = source.getName().split("\\.");
        File destinationFile = new File(".\\src\\img\\modeloVeiculo\\" + modelo.getId() + "." + nomeArquivo[1]);
        try {
            Files.copy(source.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            modelo.setFotoModelo(destinationFile);
            source.delete();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao copiar o arquivo.");
        }
        //aqui eh feita a substituição da linha antiga pela linha nova com os dados atualizados
        try {
            Path arquivoPath = Path.of(nomeDoArquivoNoDisco);
            List<String> linhas = Files.readAllLines(arquivoPath, StandardCharsets.UTF_8);
            linhas.set(novaLinha, modelo.toString()); // Altera a linha desejada
            Files.write(arquivoPath, linhas, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new Exception("Não foi possível alterar o arquivo");
        }
    }

    @Override
    public ArrayList<ModeloVeiculo> listagemDeModelos() throws Exception {
        ArrayList<ModeloVeiculo> listaDeModelos = new ArrayList<>();
        ModeloVeiculo modelo = null;
        try {
            FileReader fr = new FileReader(nomeDoArquivoNoDisco);
            try ( BufferedReader br = new BufferedReader(fr)) {
                String linha = "";
                while ((linha = br.readLine()) != null) {
                    String[] dados = linha.split(";");
                    File file = new File(dados[4]);
                    modelo = new ModeloVeiculo(Integer.valueOf(dados[0]),
                            dados[1], dados[2], dados[3], file, Integer.valueOf(dados[5]));
                    listaDeModelos.add(modelo);
                }
                br.close();
            }
        } catch (IOException e) {
            throw new Exception("Não foi possível abrir o arquivo");
        }
        return listaDeModelos;
    }

    @Override
    public ModeloVeiculo consultar(Integer id) throws Exception {
        try {
            FileReader fr = new FileReader(nomeDoArquivoNoDisco);   //fr recebe o caminho do arquivo no disco
            try ( BufferedReader br = new BufferedReader(fr)) {
                String linha = "";
                while ((linha = br.readLine()) != null) {
                    String[] dados = linha.split(";");
                    if (id.equals(Integer.valueOf(dados[0]))) {
                        br.close();
                        File file = new File(dados[4]);
                        return (new ModeloVeiculo(Integer.valueOf(dados[0]), dados[1],
                                dados[2], dados[3], file, Integer.valueOf(dados[5])));
                    }
                }
                br.close();
                return null;
            }
        } catch (IOException erro) {
            throw new Exception("Nao foi possivel listar os modelos" + erro.getMessage());
        }
    }
}
