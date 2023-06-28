/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import modelos.RegistraGastos;
import modelos.IRegistraGastosCRUD;

/**
 *
 * @author Gustavo Camargo
 */
public class RegistraGastoDAO implements IRegistraGastosCRUD {

    private String nomeDoArquivoNoDisco = null;

    public RegistraGastoDAO() {
        nomeDoArquivoNoDisco = ".\\src\\dados\\RegistroDeGastos.txt";
    }

    @Override
    public void incluir(RegistraGastos objRegistroGasto) throws Exception {
        try {
            //System.out.println(objRegistroGasto.toString());
            //cria o arquivo
            FileWriter fw = new FileWriter(nomeDoArquivoNoDisco, true);
            //Criar o buffer do arquivo
            BufferedWriter bw = new BufferedWriter(fw);
            //Escreve no arquivo
            bw.write(objRegistroGasto.toString() + "\n");
            //fecha o arquivo
            bw.close();
        } catch (IOException erro) {
            String msg = "Metodo Incluir RegistraGastoDAO - " + erro.getMessage();
            throw new Exception(msg);
        }
    }

    @Override
    public ArrayList<RegistraGastos> listagemRegistroDeGastos() throws Exception {
        ArrayList<RegistraGastos> gastos = new ArrayList();
        try {
            RegistraGastos registraGastos;
            FileReader fr = new FileReader(nomeDoArquivoNoDisco);
            BufferedReader br = new BufferedReader(fr);
            String linha = "";
            while ((linha = br.readLine()) != null) {
                String gasto[] = linha.split(";");
                //                 id + ";" + data + ";" + valorGasto + ";" + descricao + ";" + CPFProprietario + ";" + nomeProprietario + ";" + idVeiculo + ";" + veiculo + ";" + idTipoGasto + ";" + tipoDeGasto;
                registraGastos = new RegistraGastos(Integer.valueOf(gasto[0]),
                        gasto[1],
                        Float.parseFloat(gasto[2]),
                        gasto[3],
                        gasto[4],
                        gasto[5],
                        Integer.valueOf(gasto[6]),
                        gasto[7],
                        Integer.valueOf(gasto[8]),
                        gasto[9]);
                gastos.add(registraGastos);
            }
            return gastos;
        } catch (IOException | NumberFormatException erro) {
            String msg = "Metodo listagemDeGastos RegistraGastoDAO - " + erro.getMessage();
            throw new Exception(msg);
        }
    }

    @Override
    public void alterar(RegistraGastos objRegistraGastos) throws Exception {
        int novaLinha = 0; //linha que será alterada
        try {
            FileReader fr = new FileReader(nomeDoArquivoNoDisco);
            try ( BufferedReader br = new BufferedReader(fr)) {
                String linha = "";
                while ((linha = br.readLine()) != null) {
                    String[] dados = linha.split(";");
                    System.out.println("Valor atual de novaLinha -> " + novaLinha);
                    if (objRegistraGastos.getId().equals(Integer.valueOf(dados[0]))) {
                        System.out.println("Objeto a ser trocado -> " + objRegistraGastos.toString());
                        System.out.println("ENCONTRADO - teste");
                        System.out.println("Objeto a ser trocado -> " + objRegistraGastos.toString());
                        Path arquivoPath = Path.of(nomeDoArquivoNoDisco);
                        List<String> linhas = Files.readAllLines(arquivoPath, StandardCharsets.UTF_8);
                        linhas.set(novaLinha, objRegistraGastos.toString()); // Altera a linha desejada
                        Files.write(arquivoPath, linhas, StandardCharsets.UTF_8);
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
    }

    @Override
    public RegistraGastos consultar(Integer id) throws Exception {
        try {
            FileReader fr = new FileReader(nomeDoArquivoNoDisco);
            BufferedReader br = new BufferedReader(fr);
            RegistraGastos registraGastos = null;
            String linha = "";
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                if (id.equals(Integer.valueOf(dados[0]))) {
                    br.close();
                   // id + ";" + data + ";" + valorGasto + ";" + descricao + ";" + CPFProprietario + ";" + nomeProprietario + ";" + idVeiculo + ";" + veiculo + ";" + idTipoGasto + ";" + tipoDeGasto;
                    registraGastos = new RegistraGastos(Integer.valueOf(dados[0]), 
                                                        dados[1],
                                                       Float.parseFloat(dados[2]),
                                                        dados[3],
                                                        dados[4],
                                                        dados[5],
                                                        Integer.valueOf(dados[6]),
                                                        dados[7],
                                                        Integer.valueOf(dados[8]),
                                                        dados[9]);
                                                        return registraGastos;
                }
            }
            br.close();
            return null;
        } catch (IOException erro) {
            throw new Exception(erro.getMessage());
        }
    }
}
