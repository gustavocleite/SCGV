package controle;

import java.awt.Desktop;
import java.io.File;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableModel;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelExporter {

    public void exportToExcel(JTable table) throws Exception {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Salvar arquivo Excel");

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivos Excel (*.xlsx)", "xlsx");
        fileChooser.setFileFilter(filter);

        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath() + ".xlsx";

            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("Sheet1");

                TableModel model = table.getModel();
                int rowCount = model.getRowCount();
                int columnCount = model.getColumnCount();

                Row headerRow = sheet.createRow(0);
                for (int j = 0; j < columnCount; j++) {
                    String columnName = model.getColumnName(j);
                    headerRow.createCell(j).setCellValue(columnName);
                }

                for (int i = 0; i < rowCount; i++) {
                    Row row = sheet.createRow(i + 1);
                    for (int j = 0; j < columnCount; j++) {
                        Object value = model.getValueAt(i, j);
                        if (value != null) {
                            row.createCell(j).setCellValue(value.toString());
                        }
                    }
                }

                try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
                    workbook.write(outputStream);
                }

                System.out.println("Dados exportados com sucesso para o arquivo: " + filePath);
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(new File(filePath));
                }
            } catch (IOException e) {
                throw new Exception("Erro ao exportar Excel");
            }
        }
    }
}
