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
import modelos.IVeiculoCRUD;
import modelos.Veiculo;
import controle.RedimensionarImagem;

public class VeiculoDAO implements IVeiculoCRUD {

    private String nomeDoArquivo = null;
    private String destinoImagensVeiculo = null;
    private String destinoFotoProprietario = null;
    private RedimensionarImagem redimensionarImagem = null;

    public VeiculoDAO() {
        nomeDoArquivo = ".\\src\\dados\\veiculosBD.txt";
        destinoImagensVeiculo = ".\\src\\img\\veiculos\\";
        redimensionarImagem = new RedimensionarImagem();
    }

    @Override
    public void incluir(Veiculo veiculo) throws Exception {

        try {
            // Copia imagem do veiculo do diretório informado para o diretorio do sistema
            File source = redimensionarImagem.redimensionarImagem(veiculo.getFotoVeiculo());
            String[] nomeArquivo = source.getName().split("\\.");
            File destinationFile = new File(destinoImagensVeiculo + veiculo.getId() + "." + nomeArquivo[1]);
            Files.copy(source.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            veiculo.setFotoVeiculo(destinationFile);
            source.delete();
        } catch (IOException e) {
            throw new Exception("DAO VEICULO - Erro ao copiar o arquivo.");
        }
        try ( BufferedWriter buffWrite = new BufferedWriter(new FileWriter(nomeDoArquivo, true))) {
            buffWrite.append(veiculo.toString() + "\n");
            buffWrite.close();
        } catch (IOException e) {
            throw new Exception("DAO VEICULO -  erro ao gravar dados no BD");
        }
    }

    @Override
    public void alterar(Veiculo veiculo) throws Exception {
        int novaLinha = 0; //linha que será alterada
        try {
            FileReader fr = new FileReader(nomeDoArquivo);
            try ( BufferedReader br = new BufferedReader(fr)) {
                String linha = "";
                while ((linha = br.readLine()) != null) {
                    String[] vetorStr = linha.split(";");
                    if (veiculo.getId().equals(Integer.valueOf(vetorStr[0]))) {

                        break;
                    } else {
                        novaLinha++;
                    }
                }
                br.close();
            }
        } catch (IOException e) {
            throw new Exception("ERRO veiculoDAO -> Não foi possível abrir o arquivo");
        }
        try {
            // File IMAGEM VEICULO
            File source = redimensionarImagem.redimensionarImagem(veiculo.getFotoVeiculo());
            String[] nomeArquivo = source.getName().split("\\.");
            File destinationFile = new File(destinoImagensVeiculo + veiculo.getId() + "." + nomeArquivo[1]);
            Files.copy(source.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            veiculo.setFotoVeiculo(destinationFile);
            source.delete();
        } catch (IOException e) {
            throw new Exception("ERRO veiculoDAO -> Não foi possível substituir a imagem");
        }
        try {
            Path arquivoPath = Path.of(nomeDoArquivo);
            List<String> linhas = Files.readAllLines(arquivoPath, StandardCharsets.UTF_8);
            linhas.set(novaLinha, veiculo.toString()); // Altera a linha desejada
            Files.write(arquivoPath, linhas, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new Exception("ERRO veiculoDAO -> Não foi possível alterar o arquivo");
        }
    }

    @Override
    public ArrayList<Veiculo> listagemDeVeiculos() throws Exception {
        ArrayList<Veiculo> listaDeVeiculos = new ArrayList<>();
        try ( BufferedReader br = new BufferedReader(new FileReader(nomeDoArquivo))) {
            String linha = "";
            while ((linha = br.readLine()) != null) {
                String vetorStr[] = linha.split(";");
                Integer id = Integer.valueOf(vetorStr[0]);
                String CPFProprietario = vetorStr[1];
                String proprietario = vetorStr[2];
                String placa = vetorStr[3];
                String marca = vetorStr[4];
                Integer idMarca = Integer.valueOf(vetorStr[5]);
                String modelo = vetorStr[6];
                Integer idModelo = Integer.valueOf(vetorStr[7]);
                String combustivel = vetorStr[8];
                String kmAtual = vetorStr[9];
                File fotoMarca = new File(vetorStr[10]);
                File fotoModelo = new File(vetorStr[11]);
                Veiculo veiculo = new Veiculo(id, CPFProprietario, proprietario, placa, marca, idMarca,
                        modelo, idModelo, combustivel, kmAtual, fotoMarca, fotoModelo);
                listaDeVeiculos.add(veiculo);
            }
            return listaDeVeiculos;
        } catch (Exception erro) {
            throw new Exception("ERRO veiculoDAO -> Erro na Listagem de Veiculos" + erro.getMessage());
        }
    }

    @Override
    public Veiculo consultar(Integer id) throws Exception {
        try {
            FileReader fr = new FileReader(nomeDoArquivo);
            try ( BufferedReader br = new BufferedReader(fr)) {
                Veiculo veiculo = null;
                String linha = "";
                while ((linha = br.readLine()) != null) {
                    String[] vetorStr = linha.split(";");
                    if (id.equals(Integer.valueOf(vetorStr[0]))) {
                        br.close();
                        Integer idVet = Integer.valueOf(vetorStr[0]);
                        String CPFProprietario = vetorStr[1];
                        String proprietario = vetorStr[2];
                        String placa = vetorStr[3];
                        String marca = vetorStr[4];
                        Integer idMarca = Integer.valueOf(vetorStr[5]);
                        String modelo = vetorStr[6];
                        Integer idModelo = Integer.valueOf(vetorStr[7]);
                        String combustivel = vetorStr[8];
                        String kmAtual = vetorStr[9];
                        File fotoMarca = new File(vetorStr[10]);
                        File fotoModelo = new File(vetorStr[11]);
                        veiculo = new Veiculo(idVet, CPFProprietario, proprietario, placa, marca, idMarca,
                                modelo, idModelo, combustivel, kmAtual, fotoMarca, fotoModelo);
                        return veiculo;
                    }
                }
                br.close();
                return null;
            }
        } catch (IOException erro) {
            throw new Exception("ERRO veiculoDAO -> Nao foi possivel consultar o Veiculo" + erro.getMessage());
        }
    }
}
