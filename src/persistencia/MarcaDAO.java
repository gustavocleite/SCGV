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
import modelos.IMarcaCRUD;
import modelos.Marca;

public class MarcaDAO implements IMarcaCRUD {

    private String nomeDoArquivoNoDisco = null;
    private RedimensionarImagem redimensionarImagem = null;

    public MarcaDAO() {
        nomeDoArquivoNoDisco = ".\\src\\dados\\marcas.txt";
        redimensionarImagem = new RedimensionarImagem();
    }

    @Override
    public void incluir(Marca marca) throws Exception {
        //                              Inclusão                                                        //
        //----------------------------------------------------------------------------------------------//
        //                              Inclusão                                                        //
        //----------------------------------------------------------------------------------------------//
        File source = redimensionarImagem.redimensionarImagem(marca.getLogo());
        String[] nomeArquivo = source.getName().split("\\.");
        //System.out.println("Extensão: " + nomeArquivo[1]);
        File destinationFile = new File(".\\src\\img\\logo\\" + marca.getId() + "." + nomeArquivo[1]);
        try {
            Files.copy(source.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            JOptionPane.showMessageDialog(null, "Marca carregada com sucesso!");
            marca.setLogo(destinationFile);
            source.delete();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao copiar o arquivo.");
        }

        //  Este método recebe um objeto do tipo contato e o
        //  adiciona na lista de contato e já o grava no arquivo .txt
        try ( BufferedWriter buffWrite = new BufferedWriter(new FileWriter(nomeDoArquivoNoDisco, true))) {
            //marca.setLogo(destinationPath + File.separator + marca.toString().split(";")[0] + "." + nomeArquivo[1]);
            buffWrite.append(marca.toString() + "\n");
            buffWrite.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    @Override
    public void alterar(Marca marca) throws Exception {
        //Neste trecho e feita a pesquisa pra saber em qual linha esta
        //o proprietario que tera seus dados alterados
        int novaLinha = 0; //linha que será alterada
        try {
            FileReader fr = new FileReader(nomeDoArquivoNoDisco);
            try ( BufferedReader br = new BufferedReader(fr)) {
                String linha = "";
                while ((linha = br.readLine()) != null) {
                    String[] dados = linha.split(";");
                    System.out.println("Valor atual de novaLinha -> " + novaLinha);
                    if (marca.getId().equals(Integer.valueOf(dados[0]))) {
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
        //aqui eh feita a copia da imagem referente a CNH do proprietário
        File source = redimensionarImagem.redimensionarImagem(marca.getLogo());
        String[] nomeArquivo = source.getName().split("\\.");
        File destinationFile = new File(".\\src\\img\\logo\\" + marca.getId() + "." + nomeArquivo[1]);
        try {
            Files.copy(source.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            marca.setLogo(destinationFile);
            source.delete();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao copiar o arquivo.");
        }
        //aqui eh feita a substituição da linha antiga pela linha nova com os dados atualizados
        try {
            Path arquivoPath = Path.of(nomeDoArquivoNoDisco);
            List<String> linhas = Files.readAllLines(arquivoPath, StandardCharsets.UTF_8);
            linhas.set(novaLinha, marca.toString()); // Altera a linha desejada
            Files.write(arquivoPath, linhas, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new Exception("Não foi possível alterar o arquivo");
        }
    }

    @Override
    public ArrayList<Marca> listagemDeMarcas() throws Exception {
        //Este método retorna uma lista do tipo Contato
        //contendo todos contatos lidos do arquivo texto
        try {
            ArrayList<Marca> lista = new ArrayList<>();
            FileReader fr = new FileReader(nomeDoArquivoNoDisco);   //fr recebe o caminho do arquivo no disco
            try ( BufferedReader br = new BufferedReader(fr)) {
                String linha = "";
                while ((linha = br.readLine()) != null) {
                    String[] dados = linha.split(";");
                    File file = new File(dados[2]);
                    Marca marca = new Marca(Integer.valueOf(dados[0]), dados[1], file);
                    lista.add(marca);
                }
                br.close();
            }
            return lista;
        } catch (IOException erro) {
            throw new Exception("Nao foi possivel listar as marcas " + erro.getMessage());
        }

    }

    @Override
    public Marca consulaMarca(Integer id) throws Exception {
        try {
            FileReader fr = new FileReader(nomeDoArquivoNoDisco);   //fr recebe o caminho do arquivo no disco
            try ( BufferedReader br = new BufferedReader(fr)) {
                String linha = "";
                while ((linha = br.readLine()) != null) {
                    String[] dados = linha.split(";");
                    if (id.equals(Integer.valueOf(dados[0]))) {
                        br.close();
                        File file = new File(dados[2]);
                        return (new Marca(Integer.valueOf(dados[0]), dados[1], file));
                    }
                }
                br.close();
                return null;
            }
        } catch (IOException erro) {
            throw new Exception("Nao foi possivel listar as marcas " + erro.getMessage());
        }
    }
}
