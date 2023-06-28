package visao;

import modelos.*;
import controle.*;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class TelaConsultarProprietario extends javax.swing.JFrame {

    IPropietarioCRUD consultarProprietario = null;

    public TelaConsultarProprietario() {
        consultarProprietario = new ProprietarioControle();
        initComponents();
        this.setLocationRelativeTo(null);
        this.setTitle("SCGV - Sistema de Controle de Gastos Veicular -Consultar proprietário");
        //Bloqueia mover as colunas da tabela
        jTable_Saida.getTableHeader().setReorderingAllowed(false);
        // Bloqueia edição da tabela
        jTable_Saida.setEnabled(false);

    }

    //Metodo para imagem do proprietario
    private void setFotoProprietario(String caminhoLogo) {
        ImageIcon icon = new ImageIcon(caminhoLogo);
        //definindo um tamanho limite para a imagem 
        ImageIcon resizedIcon = new ImageIcon(icon.getImage().getScaledInstance(210, 190, java.awt.Image.SCALE_SMOOTH));
        jLabel_LogoFotoProprietario.setIcon(resizedIcon);
    }
    //Metodo para mostrar a imagem da CNH 

    private void setImagemCNH(String caminhoLogo) {
        ImageIcon icon = new ImageIcon(caminhoLogo);
        //definindo um tamanho limite para a imagem 
        ImageIcon resizedIcon = new ImageIcon(icon.getImage().getScaledInstance(210, 190, java.awt.Image.SCALE_SMOOTH));
        jLabel_LogoImagem.setIcon(resizedIcon);
    }
    //Colocar Mascara no cpf

    private String colocarMascaraCPF(String CPF) {
        return CPF.substring(0, 3) + "." + CPF.substring(3, 6)
                + "." + CPF.substring(6, 9) + "-" + CPF.substring(9, 11);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel_ID5 = new javax.swing.JLabel();
        jFormattedTextField_CPF = new javax.swing.JFormattedTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_Saida = new javax.swing.JTable();
        jButton_Consultar = new javax.swing.JButton();
        jLabel_LogoFotoProprietario = new javax.swing.JLabel();
        jButton_Voltar = new javax.swing.JButton();
        jLabel_LogoImagem = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 204, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Consultar Proprietário", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(255, 255, 0))); // NOI18N

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/scgv.png"))); // NOI18N

        jLabel_ID5.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel_ID5.setText("CPF:");

        try {
            jFormattedTextField_CPF.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jTable_Saida.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "CPF", "NOME COMPLETO", "E-MAIL", "TELEFONE", "CNH", "CATEGORIA"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable_Saida);

        jButton_Consultar.setText("CONSULTAR");
        jButton_Consultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_ConsultarActionPerformed(evt);
            }
        });

        jLabel_LogoFotoProprietario.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_LogoFotoProprietario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logo/sem-imagem.png"))); // NOI18N
        jLabel_LogoFotoProprietario.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3), "Imagem", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 16))); // NOI18N

        jButton_Voltar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/voltar-24.png"))); // NOI18N
        jButton_Voltar.setText("Voltar");
        jButton_Voltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_VoltarActionPerformed(evt);
            }
        });

        jLabel_LogoImagem.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_LogoImagem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logo/sem-imagem.png"))); // NOI18N
        jLabel_LogoImagem.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3), "Imagem", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 16))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jButton_Voltar)
                .addGap(357, 357, 357)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel_ID5)
                        .addGap(18, 18, 18)
                        .addComponent(jFormattedTextField_CPF, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton_Consultar)
                        .addGap(91, 91, 91)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel_LogoFotoProprietario, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(51, 51, 51)
                        .addComponent(jLabel_LogoImagem, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(156, 156, 156))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 825, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jButton_Voltar))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel_LogoFotoProprietario, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel_LogoImagem, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel_ID5)
                            .addComponent(jFormattedTextField_CPF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jButton_Consultar)))
                .addContainerGap(31, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_ConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ConsultarActionPerformed
        try {
            String cpf = jFormattedTextField_CPF.getText();

            cpf = cpf.replaceAll("[^0-9]", ""); // remove caracteres não-numéricos
            Proprietario proprietario = null;
            if (consultarProprietario.consultar(cpf) != null) {
                proprietario = consultarProprietario.consultar(cpf);
            } else {
                throw new Exception("Verificar o CPF para a busca!");
            }
            //Busca e lista Proprietario
            String[] colunas = {"CPF", "NOME COMPLETO", "E-MAIL", "TELEFONE", "CNH", "CATEGORIA"};
            DefaultTableModel modeloTabela = new DefaultTableModel(colunas, 0);
            Object[] linha = {colocarMascaraCPF(proprietario.getCpf()),
                proprietario.getNomeCompleto(), proprietario.getEmail(), proprietario.getFone().toString().replaceAll(";", "") + "",
                proprietario.getNumeroCNH(), proprietario.getCategoriaCnh()};
            modeloTabela.addRow(linha);

            jTable_Saida.setModel(modeloTabela);// Saida de dados

            //Busca imagem do proprietario
            setFotoProprietario(proprietario.getPatchImagemProprietario().getPath());
            //Busca imagem da cnh
            setImagemCNH(proprietario.getPathCNH().getPath());
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(this, erro.getMessage());
        }
    }//GEN-LAST:event_jButton_ConsultarActionPerformed

    private void jButton_VoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_VoltarActionPerformed
        dispose();
        TelaPrincipal voltaTelaPrincipal = new TelaPrincipal();
        voltaTelaPrincipal.dispose();
        voltaTelaPrincipal.setLocationRelativeTo(this);
        voltaTelaPrincipal.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        voltaTelaPrincipal.setVisible(true);
    }//GEN-LAST:event_jButton_VoltarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaConsultarProprietario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaConsultarProprietario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaConsultarProprietario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaConsultarProprietario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaConsultarProprietario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_Consultar;
    private javax.swing.JButton jButton_Voltar;
    private javax.swing.JFormattedTextField jFormattedTextField_CPF;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel_ID5;
    private javax.swing.JLabel jLabel_LogoFotoProprietario;
    private javax.swing.JLabel jLabel_LogoImagem;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable_Saida;
    // End of variables declaration//GEN-END:variables
}
