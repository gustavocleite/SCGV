/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controle;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author brunocoronha.adm
 */
public class RedimensionarImagem {

    public RedimensionarImagem() {

    }

    public String copiarImagens() {       
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivos de Imagem", "jpg", "jpeg", "png", "gif");
        fileChooser.setFileFilter(filter);
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) { 
            File source = fileChooser.getSelectedFile();
            String destinationPath = ".\\src\\img\\logo"; // Coloque o caminho de destino desejado aqui
            String[] nomeArquivo = source.getName().split("\\.");
            System.out.println("Extensão: " + nomeArquivo[1]);
            File destinationFile = new File(destinationPath + File.separator + source.getName());
            try {
                Files.copy(source.toPath(), destinationFile.toPath());
                JOptionPane.showMessageDialog(null, "Arquivo copiado com sucesso!");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Erro ao copiar o arquivo.");
            }            
        } 
        return fileChooser.getSelectedFile().getAbsolutePath();
    }
    
    public boolean validarPlaca(String placa){
        
        String padraoMercosul = "^[A-Z]{3}\\d[A-Z]\\d{2}$";
        Pattern patternMercosul = Pattern.compile(padraoMercosul);
        Matcher matcherMercosul = patternMercosul.matcher(placa);
        
        String padraoBR = "^[A-Z]{3}\\d{4}$";
        Pattern patternBrasileiro = Pattern.compile(padraoBR);
        Matcher matcherBrasil = patternBrasileiro.matcher(placa);
        
        return (matcherMercosul.matches() || matcherBrasil.matches());
    }
    
     public File redimensionarImagem(File imagemOriginal) {

           // Carrega a imagem original
        BufferedImage originalImage = null;
        try {
            originalImage = ImageIO.read(imagemOriginal);
        } catch (IOException ex) {
            Logger.getLogger(RedimensionarImagem.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Define as dimensões do novo tamanho
        int newWidth = 180;
        int newHeight = 180;
        // Redimensiona a imagem
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, newWidth, newHeight);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(originalImage, 0, 0, newWidth, newHeight, null);        g2d.dispose();

        // Define o novo nome e formato do arquivo
        String outputFileName = imagemOriginal.getName().replaceAll("\\.(?i)(jpg|jpeg|png|gif|bmp)$", "") + ".jpg";
        // Cria o novo arquivo de imagem
        File outputFile = new File(outputFileName);
        try {
            ImageIO.write(resizedImage, "jpg", outputFile);
        } catch (IOException ex) {
            Logger.getLogger(RedimensionarImagem.class.getName()).log(Level.SEVERE, null, ex);
        }
        return outputFile;
    }

   

}
