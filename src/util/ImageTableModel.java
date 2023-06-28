/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author gustavocamargo.adm
 */
public class ImageTableModel extends DefaultTableModel {
     public ImageTableModel(Object[][] data, String[] columnNames) {
        super(data, columnNames);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 2) {
            return ImageIcon.class; // Define a classe da coluna de imagem como ImageIcon
        }
        return super.getColumnClass(columnIndex);
    }

}
