/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import javax.swing.ImageIcon;

public class ImageRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value instanceof ImageIcon) {
            // Cria um rótulo para exibir a imagem
            JLabel imageLabel = new JLabel((ImageIcon) value);
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

            // Define a cor de fundo e seleção da célula
            if (isSelected) {
                imageLabel.setOpaque(true);
                imageLabel.setBackground(table.getSelectionBackground());
                imageLabel.setForeground(table.getSelectionForeground());
            } else {
                imageLabel.setOpaque(false);
                imageLabel.setForeground(table.getForeground());
            }

            return imageLabel;
        }

        // Se não for um ImageIcon, usa o renderizador padrão
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }

}

