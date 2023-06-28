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
import javax.swing.JOptionPane;
import modelos.IPropietarioCRUD;
import modelos.Proprietario;
import modelos.Telefone;
import java.util.List;

public class ProprietarioDAO implements IPropietarioCRUD {

    private String nomeDoArquivo = null;
    private String destinoImagensCNH = null;
    private String destinoFotoProprietario = null;

    public ProprietarioDAO() {
        nomeDoArquivo = ".\\src\\dados\\propietariosBD.txt";
        destinoImagensCNH = ".\\src\\img\\cnh\\";
        destinoFotoProprietario = ".\\src\\img\\fotoProprietarios\\";
    }

    @Override
    public void Incluir(Proprietario objPropietario) throws Exception {

        try {
            // file da CNH
            File source = objPropietario.getPathCNH();
            String[] nomeArquivo = source.getName().split("\\.");
            File destinationFile = new File(destinoImagensCNH + objPropietario.getCpf() + "." + nomeArquivo[1]);
            Files.copy(source.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            objPropietario.setPathCNH(destinationFile);
            source.delete();
            // File foto proprietario;
            File sourceFotoProprietario = objPropietario.getPatchImagemProprietario();
            String[] nomeArquivoFotoProprietario = sourceFotoProprietario.getName().split("\\.");
            File destinationFileFotoProprietario = new File(destinoFotoProprietario + objPropietario.getCpf() + "." + nomeArquivoFotoProprietario[1]);
            Files.copy(sourceFotoProprietario.toPath(), destinationFileFotoProprietario.toPath(), StandardCopyOption.REPLACE_EXISTING);
            objPropietario.setPatchImagemProprietario(destinationFileFotoProprietario);
            source.delete();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao copiar o arquivo.");
        }
        try ( BufferedWriter buffWrite = new BufferedWriter(new FileWriter(nomeDoArquivo, true))) {
            buffWrite.append(objPropietario.toString() + "\n");
            buffWrite.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    @Override
    public void alterar(Proprietario proprietario) throws Exception {
        int novaLinha = 0; //linha que será alterada
        try {
            FileReader fr = new FileReader(nomeDoArquivo);
            try ( BufferedReader br = new BufferedReader(fr)) {
                String linha = "";
                while ((linha = br.readLine()) != null) {
                    String[] dados = linha.split(";");
                    System.out.println("Valor atual de novaLinha -> " + novaLinha);
                    if (proprietario.getCpf().equals(dados[0])) {
                        System.out.println("ENCONTRADO");
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
        try {
            // File CNH
            File source = proprietario.getPathCNH();
            String[] nomeArquivo = source.getName().split("\\.");
            File destinationFile = new File(destinoImagensCNH + proprietario.getCpf() + "." + nomeArquivo[1]);
            Files.copy(source.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            proprietario.setPathCNH(destinationFile);
           source.delete();
            // File foto proprietario;
            File sourceFotoProprietario = proprietario.getPatchImagemProprietario();
            String[] nomeArquivoFotoProprietario = sourceFotoProprietario.getName().split("\\.");
            File destinationFileFotoProprietario = new File(destinoFotoProprietario + proprietario.getCpf() + "." + nomeArquivoFotoProprietario[1]);
            Files.copy(sourceFotoProprietario.toPath(), destinationFileFotoProprietario.toPath(), StandardCopyOption.REPLACE_EXISTING);
            proprietario.setPatchImagemProprietario(destinationFileFotoProprietario);
            source.delete();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao copiar o arquivo.");
        }
        try {
            Path arquivoPath = Path.of(nomeDoArquivo);
            List<String> linhas = Files.readAllLines(arquivoPath, StandardCharsets.UTF_8);
            linhas.set(novaLinha, proprietario.toString()); // Altera a linha desejada
            Files.write(arquivoPath, linhas, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new Exception("Não foi possível alterar o arquivo");
        }
    }

    @Override
    public ArrayList<Proprietario> listagemDePropietario() throws Exception {
        ArrayList<Proprietario> listaDeProprietarios = new ArrayList<>();
        try ( BufferedReader br = new BufferedReader(new FileReader(nomeDoArquivo))) {
            String linha = "";
            while ((linha = br.readLine()) != null) {
                String vetorStr[] = linha.split(";");
                String cpf = vetorStr[0];
                String nomeCompleto = vetorStr[1];
                String email = vetorStr[2];
                String ddi = vetorStr[3]; // Extrair o valor do DDI como inteiro
                String ddd = vetorStr[4]; // Extrair o valor do DDD como inteiro
                String numeroTelefone = vetorStr[5]; // Extrair o número do telefone como inteiro
                Telefone fone = new Telefone(ddi, ddd, numeroTelefone); // Criar o objeto Telefone com os valores extraídos
                String numeroCNH = vetorStr[6];
                String categoriaCNH = vetorStr[7];
                File file = new File(vetorStr[8]);
                File fileFotoProprietario = new File(vetorStr[9]);
                Proprietario objProprietario = new Proprietario(cpf, nomeCompleto, email, fone, numeroCNH, categoriaCNH, file,fileFotoProprietario);
                listaDeProprietarios.add(objProprietario);
            }
        } catch (Exception erro) {
            throw new Exception("Erro na Listagem de proprietários - " + erro.getMessage());
        }
        return listaDeProprietarios;
    }

    @Override
    public Proprietario consultar(String cpf) throws Exception {
        try {
            FileReader fr = new FileReader(nomeDoArquivo);
            try ( BufferedReader br = new BufferedReader(fr)) {
                Proprietario proprietario = null;
                String linha = "";
                while ((linha = br.readLine()) != null) {
                    String[] dados = linha.split(";");
                    if (cpf.equals(dados[0])) {
                        //System.out.println("ENCONTRADO");
                        br.close();
                        String cpf1 = dados[0];
                        String nome = dados[1];
                        String email = dados[2];
                        String ddi = dados[3];
                        String ddd = dados[4];
                        String numeroTelefone = dados[5];
                        String numeroCNH = dados[6];
                        String categoriaCNH = dados[7];
                        File file = new File(dados[8]);
                        File fileFotoProprietario = new File(dados[9]);
                        Telefone telefone = new Telefone(ddi, ddd, numeroTelefone);
                        proprietario = new Proprietario(cpf1, nome, email, telefone, numeroCNH, categoriaCNH, file, fileFotoProprietario);
                        return proprietario;
                    }
                }
                br.close();
                return null;
            }
        } catch (IOException erro) {
            throw new Exception("Nao foi possivel consultar o proprietario" + erro.getMessage());
        }
    }
}