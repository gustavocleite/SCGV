package visao;

import controle.PDFExporter;

import controle.ExcelExporter;
import controle.ProprietarioControle;
import java.awt.HeadlessException;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import modelos.IPropietarioCRUD;
import modelos.EnumCategoriaCNH;
import modelos.EnumDDD;
import modelos.EnumDDD.DDD;
import modelos.Proprietario;
import modelos.Telefone;
import modelos.EnumDDI;
import modelos.EnumDDI.DDI;

public class TelaCadastroProprietario extends javax.swing.JFrame {

    IPropietarioCRUD cadastroProprietarioControle = null;
    //EnumCategoriaCNH enumCategoria = null;
    File origemGlobal = null;
    File origemGlobalFotoProprietario = null;

    /**
     * Creates new form TelaCadastroProprietario
     */
    public TelaCadastroProprietario() {
        initComponents();
        this.setResizable(false);
        this.setTitle("SCGV - Sistema de Controle de Gastos Veicular - Cadastro de Proprietario");
        this.setLocationRelativeTo(null);
        cadastroProprietarioControle = new ProprietarioControle();
        preencherComboBoxCategoriaCnh();
        preencherComboBoxDDI();
        preencherComboBoxDDD();
        MostrarProprietarios();
    }

    // Metodo para buscar e preencher proprietarios na jTable
    private void MostrarProprietarios() {
        try {
            ArrayList<Proprietario> listaDeProprietarios = null;
            listaDeProprietarios = cadastroProprietarioControle.listagemDePropietario();
            Collections.sort(listaDeProprietarios, (Proprietario o1, Proprietario o2) -> {
                return o1.getCpf().compareTo(o2.getCpf());
            });
            DefaultTableModel model = (DefaultTableModel) jTable_Saida.getModel();
            model.setNumRows(0);
            if (listaDeProprietarios.isEmpty()) {
                return;
            }
            for (int pos = 0; pos < listaDeProprietarios.size(); pos++) {
                Proprietario proprietario = listaDeProprietarios.get(pos);
                String[] linha = new String[9];
                linha[0] = colocarMascaraCPF(proprietario.getCpf());
                linha[1] = proprietario.getNomeCompleto();
                linha[2] = proprietario.getEmail();
                linha[3] = proprietario.getFone().toString().replaceAll(";", "") + "";
                linha[4] = proprietario.getNumeroCNH();
                linha[5] = proprietario.getCategoriaCnh();
                linha[6] = proprietario.getPathCNH().getPath();
                linha[7] = proprietario.getPatchImagemProprietario().getPath();
                model.addRow(linha);
            }
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(rootPane, erro.getMessage());
        }
    }

    // Metodo privado para limpar capos apos inserçao
    private void limparCampos() {
        jComboBox_CategoriaCNH.setSelectedIndex(0);
        jComboBox_DDD.setSelectedIndex(0);
        jComboBox_DDI.setSelectedIndex(0);
        jFormattedTextField_CPF.setText("");
        jFormattedTextField_CPF.setEnabled(true);
        jTextField_EMail.setText("");
        jTextField_NomeCompleto.setText("");
        jTextField_NomeCompleto.setEnabled(true);
        jFormattedTextField_NumeroTelefone.setText("");
        jTextField_NumeroCNH.setText(null);
        jButton_Salvar.setEnabled(true);
        jTextField_NumeroCNH.setEnabled(true);
        setFotoProprietario(".\\src\\img\\fotoProprietarios\\sem-imagem.png");
        setImagemCNH(".\\src\\img\\cnh\\sem-imagem.png");
    }

    //Metodo privado pra preencher combobox com Enum de categoriaCNH
    private void preencherComboBoxCategoriaCnh() {
        EnumCategoriaCNH.CategoriaCnh[] categorias = EnumCategoriaCNH.values();
        for (EnumCategoriaCNH.CategoriaCnh categoria : categorias) {
            jComboBox_CategoriaCNH.addItem(categoria.toString());
        }
    }

    //Metodo privado pra preencher combobox com Enum de DDI
    private void preencherComboBoxDDI() {
        ArrayList<String> ddiList = new ArrayList();
        for (EnumDDI.DDI ddi : DDI.values()) {
            ddiList.add(ddi.getCode());
        }
        for (String code : ddiList) {
            jComboBox_DDI.addItem(code);
        }
    }

    //Metodo privado pra preencher combobox com Enum de DDD
    private void preencherComboBoxDDD() {
        ArrayList<String> dddList = new ArrayList();
        for (EnumDDD.DDD ddd : DDD.values()) {
            dddList.add(ddd.getCode());
        }
        for (String code : dddList) {
            jComboBox_DDD.addItem(code);
        }
    }

    //Metodo para mostrar a imagem da CNH quando for inserida ou selecionada na tabela
    private void setImagemCNH(String caminhoLogo) {
        ImageIcon icon = new ImageIcon(caminhoLogo);
        //definindo um tamanho limite para a imagem 
        ImageIcon resizedIcon = new ImageIcon(icon.getImage().getScaledInstance(210, 190, java.awt.Image.SCALE_SMOOTH));
        jLabel_LogoImagem.setIcon(resizedIcon);
//        jButton_Alterar.setEnabled(false);
    }

    //Metodo para mostrar a imagem da CNH quando for inserida ou selecionada na tabela
    private void setFotoProprietario(String caminhoLogo) {
        ImageIcon icon = new ImageIcon(caminhoLogo);
        //definindo um tamanho limite para a imagem 
        ImageIcon resizedIcon = new ImageIcon(icon.getImage().getScaledInstance(210, 190, java.awt.Image.SCALE_SMOOTH));
        jLabel_LogoFotoProprietario.setIcon(resizedIcon);
//        jButton_Alterar.setEnabled(false);
    }

    private String colocarMascaraCPF(String CPF) {
        return CPF.substring(0, 3) + "." + CPF.substring(3, 6)
                + "." + CPF.substring(6, 9) + "-" + CPF.substring(9, 11);
    }

    private String removerMascaraCPF(String CPF) {
        CPF = CPF.replace(".", "");
        CPF = CPF.replace("-", "");
        return CPF;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel_EmblemaGrupo = new javax.swing.JLabel();
        jButton_Voltar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_Saida = new javax.swing.JTable();
        jButton_exportarExcel = new javax.swing.JButton();
        jButton_exportarPDF = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel_ID5 = new javax.swing.JLabel();
        jLabel_Descricao = new javax.swing.JLabel();
        jTextField_NomeCompleto = new javax.swing.JTextField();
        jLabel_ID6 = new javax.swing.JLabel();
        jTextField_EMail = new javax.swing.JTextField();
        jLabel_Descricao1 = new javax.swing.JLabel();
        jComboBox_DDI = new javax.swing.JComboBox<>();
        jComboBox_DDD = new javax.swing.JComboBox<>();
        jLabel_ID10 = new javax.swing.JLabel();
        jLabel_ID7 = new javax.swing.JLabel();
        jTextField_NumeroCNH = new javax.swing.JTextField();
        jLabel_ID8 = new javax.swing.JLabel();
        jComboBox_CategoriaCNH = new javax.swing.JComboBox<>();
        jButton_Salvar = new javax.swing.JButton();
        jButton_Alterar = new javax.swing.JButton();
        jButton_Limpar = new javax.swing.JButton();
        jLabel_LogoImagem = new javax.swing.JLabel();
        jLabel_LogoFotoProprietario = new javax.swing.JLabel();
        jButton_SelecionarCNH = new javax.swing.JButton();
        jButton_SelecionarFotoProprietario = new javax.swing.JButton();
        jFormattedTextField_CPF = new javax.swing.JFormattedTextField();
        jFormattedTextField_NumeroTelefone = new javax.swing.JFormattedTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 204, 255));

        jPanel1.setBackground(new java.awt.Color(0, 204, 255));

        jLabel_EmblemaGrupo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_EmblemaGrupo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/scgv.png"))); // NOI18N
        jLabel_EmblemaGrupo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jButton_Voltar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/voltar-24.png"))); // NOI18N
        jButton_Voltar.setText("Voltar");
        jButton_Voltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_VoltarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton_Voltar)
                .addGap(491, 491, 491)
                .addComponent(jLabel_EmblemaGrupo)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_EmblemaGrupo)
                    .addComponent(jButton_Voltar))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(0, 204, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Proprietários Cadastrados", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(255, 255, 0))); // NOI18N

        jTable_Saida.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CPF", "Nome ", "E-Mail", "Telefone", "CNH", "CategoriaCNH"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable_Saida.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_SaidaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable_Saida);

        jButton_exportarExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/excel.png"))); // NOI18N
        jButton_exportarExcel.setText("Exportar");
        jButton_exportarExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_exportarExcelActionPerformed(evt);
            }
        });

        jButton_exportarPDF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/pdf.png"))); // NOI18N
        jButton_exportarPDF.setText("Exportar");
        jButton_exportarPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_exportarPDFActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 870, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(310, 310, 310)
                        .addComponent(jButton_exportarExcel)
                        .addGap(18, 18, 18)
                        .addComponent(jButton_exportarPDF)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 526, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_exportarPDF)
                    .addComponent(jButton_exportarExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(0, 204, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cadastrar novo Proprietário", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(255, 255, 0))); // NOI18N

        jLabel_ID5.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel_ID5.setText("CPF:");

        jLabel_Descricao.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel_Descricao.setText("Nome Completo:");

        jTextField_NomeCompleto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_NomeCompletoKeyTyped(evt);
            }
        });

        jLabel_ID6.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel_ID6.setText("E-Mail:");

        jLabel_Descricao1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel_Descricao1.setText("DDI + DDD: ");

        jComboBox_DDI.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));

        jComboBox_DDD.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));

        jLabel_ID10.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel_ID10.setText("Telefone:");

        jLabel_ID7.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel_ID7.setText("Número CNH:");

        jTextField_NumeroCNH.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_NumeroCNHKeyTyped(evt);
            }
        });

        jLabel_ID8.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel_ID8.setText("Categoria:");

        jComboBox_CategoriaCNH.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));

        jButton_Salvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/salvar-24.png"))); // NOI18N
        jButton_Salvar.setText("Salvar");
        jButton_Salvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_SalvarActionPerformed(evt);
            }
        });

        jButton_Alterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/alterar-24.png"))); // NOI18N
        jButton_Alterar.setText("Alterar");
        jButton_Alterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_AlterarActionPerformed(evt);
            }
        });

        jButton_Limpar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/limpar-24.png"))); // NOI18N
        jButton_Limpar.setText("Limpar");
        jButton_Limpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_LimparActionPerformed(evt);
            }
        });

        jLabel_LogoImagem.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_LogoImagem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logo/sem-imagem.png"))); // NOI18N
        jLabel_LogoImagem.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3), "Imagem", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 16))); // NOI18N

        jLabel_LogoFotoProprietario.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_LogoFotoProprietario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logo/sem-imagem.png"))); // NOI18N
        jLabel_LogoFotoProprietario.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3), "Imagem", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 16))); // NOI18N

        jButton_SelecionarCNH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/selecionar-24.png"))); // NOI18N
        jButton_SelecionarCNH.setText("Selecionar Imagem CNH");
        jButton_SelecionarCNH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_SelecionarCNHActionPerformed(evt);
            }
        });

        jButton_SelecionarFotoProprietario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/selecionar-24.png"))); // NOI18N
        jButton_SelecionarFotoProprietario.setText("Selecionar Imagem Proprietário");
        jButton_SelecionarFotoProprietario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_SelecionarFotoProprietarioActionPerformed(evt);
            }
        });

        try {
            jFormattedTextField_CPF.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        try {
            jFormattedTextField_NumeroTelefone.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#########")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel_ID8)
                            .addComponent(jLabel_ID7)
                            .addComponent(jLabel_ID10))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jComboBox_CategoriaCNH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(42, 42, 42)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jFormattedTextField_NumeroTelefone)
                                    .addComponent(jTextField_NumeroCNH))))
                        .addGap(13, 13, 13))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel_LogoImagem, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel_LogoFotoProprietario, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jButton_SelecionarCNH)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton_SelecionarFotoProprietario)
                        .addGap(14, 14, 14))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel_ID5)
                            .addComponent(jLabel_Descricao)
                            .addComponent(jLabel_ID6)
                            .addComponent(jLabel_Descricao1))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jComboBox_DDI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jComboBox_DDD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jTextField_NomeCompleto, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextField_EMail, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jFormattedTextField_CPF))
                        .addGap(13, 13, 13))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton_Limpar)
                .addGap(18, 18, 18)
                .addComponent(jButton_Alterar)
                .addGap(18, 18, 18)
                .addComponent(jButton_Salvar)
                .addGap(83, 83, 83))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_ID5)
                    .addComponent(jFormattedTextField_CPF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_Descricao)
                    .addComponent(jTextField_NomeCompleto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_EMail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_ID6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_Descricao1)
                    .addComponent(jComboBox_DDI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox_DDD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_ID10)
                    .addComponent(jFormattedTextField_NumeroTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel_ID7)
                            .addComponent(jTextField_NumeroCNH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel_ID8))
                    .addComponent(jComboBox_CategoriaCNH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_LogoImagem, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_LogoFotoProprietario, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_SelecionarCNH)
                    .addComponent(jButton_SelecionarFotoProprietario))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_Salvar)
                    .addComponent(jButton_Alterar)
                    .addComponent(jButton_Limpar))
                .addContainerGap(59, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_VoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_VoltarActionPerformed
        dispose();
        TelaPrincipal voltaTelaPrincipal = new TelaPrincipal();
        voltaTelaPrincipal.dispose();
        voltaTelaPrincipal.setLocationRelativeTo(this);
        voltaTelaPrincipal.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        voltaTelaPrincipal.setVisible(true);
    }//GEN-LAST:event_jButton_VoltarActionPerformed

    private void jTable_SaidaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_SaidaMouseClicked

        try {
            jTextField_NomeCompleto.setEnabled(false);
            jButton_Alterar.setEnabled(true);
            jButton_Salvar.setEnabled(false);
            jFormattedTextField_CPF.setEnabled(false);
            jTextField_NumeroCNH.setEnabled(false);
            int linha = jTable_Saida.getSelectedRow();
            String cpf = removerMascaraCPF(((String) jTable_Saida.getValueAt(linha, 0)));
            jFormattedTextField_CPF.setText((String) jTable_Saida.getValueAt(linha, 0));
            Proprietario proprietario = cadastroProprietarioControle.consultar(cpf);
            jTextField_NomeCompleto.setText(proprietario.getNomeCompleto());
            jTextField_EMail.setText(proprietario.getEmail());
            jComboBox_DDI.setSelectedItem(proprietario.getFone().getDdi());
            jComboBox_DDD.setSelectedItem(proprietario.getFone().getDdd());
            jFormattedTextField_NumeroTelefone.setText(proprietario.getFone().getNumeroTelefone());
            jTextField_NumeroCNH.setText(proprietario.getNumeroCNH());
            jComboBox_CategoriaCNH.setSelectedItem(proprietario.getCategoriaCnh());
            origemGlobal = new File(proprietario.getPathCNH().getPath());
            origemGlobalFotoProprietario = new File(proprietario.getPatchImagemProprietario().getPath());
            setImagemCNH(proprietario.getPathCNH().getPath());
            setFotoProprietario(proprietario.getPatchImagemProprietario().getPath());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_jTable_SaidaMouseClicked

    private void jButton_LimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_LimparActionPerformed
        jFormattedTextField_CPF.setEnabled(true);
        limparCampos();
        setImagemCNH(".\\src\\img\\cnh\\sem-imagem.png");
    }//GEN-LAST:event_jButton_LimparActionPerformed

    private void jButton_AlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_AlterarActionPerformed
        try {
            Telefone fone = new Telefone(
                    jComboBox_DDI.getSelectedItem().toString(),
                    jComboBox_DDD.getSelectedItem().toString(),
                    jFormattedTextField_NumeroTelefone.getText());
            Proprietario proprietario = new Proprietario(
                    removerMascaraCPF(jFormattedTextField_CPF.getText()),
                    jTextField_NomeCompleto.getText(),
                    jTextField_EMail.getText(),
                    fone,
                    jTextField_NumeroCNH.getText(),
                    jComboBox_CategoriaCNH.getSelectedItem().toString(),
                    origemGlobal,
                    origemGlobalFotoProprietario);
            cadastroProprietarioControle.alterar(proprietario);
            JOptionPane.showMessageDialog(rootPane, "Proprietário alterado com Sucesso!");
            origemGlobal = null;
            origemGlobalFotoProprietario = null;
            MostrarProprietarios();
            limparCampos();
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(this, erro.getMessage());
        }
    }//GEN-LAST:event_jButton_AlterarActionPerformed

    private void jButton_SalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_SalvarActionPerformed
        try {
            Telefone fone = new Telefone(
                    jComboBox_DDI.getSelectedItem().toString(),
                    jComboBox_DDD.getSelectedItem().toString(),
                    jFormattedTextField_NumeroTelefone.getText());
            Proprietario novoProprietario = new Proprietario(
                    removerMascaraCPF(jFormattedTextField_CPF.getText()),
                    jTextField_NomeCompleto.getText(),
                    jTextField_EMail.getText(),
                    fone,
                    jTextField_NumeroCNH.getText(),
                    jComboBox_CategoriaCNH.getSelectedItem().toString(),
                    origemGlobal,
                    origemGlobalFotoProprietario);

            cadastroProprietarioControle.Incluir(novoProprietario);
            JOptionPane.showMessageDialog(rootPane, "Proprietario cadastrado com Sucesso!");
            limparCampos();
            MostrarProprietarios();
            origemGlobalFotoProprietario = null;
            origemGlobal = null;
            setImagemCNH(".\\src\\img\\cnh\\sem-imagem.png");
            setFotoProprietario(".\\src\\img\\fotoProprietarios\\sem-imagem.png");
            limparCampos();
        } catch (Exception erro) {
            erro.printStackTrace(System.out);
            JOptionPane.showMessageDialog(this, erro.getMessage());
        }
    }//GEN-LAST:event_jButton_SalvarActionPerformed

    private void jButton_SelecionarCNHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_SelecionarCNHActionPerformed
        try {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivos de Imagem", "jpg", "jpeg", "png", "gif");
            File diretorioInicial = new File(".\\src\\img\\cnh\\");
            fileChooser.setCurrentDirectory(diretorioInicial);
            fileChooser.setFileFilter(filter);
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                origemGlobal = fileChooser.getSelectedFile();
                setImagemCNH(fileChooser.getSelectedFile().getPath());
            }
        } catch (HeadlessException erro) {
            JOptionPane.showMessageDialog(this, erro.getMessage());
        }

    }//GEN-LAST:event_jButton_SelecionarCNHActionPerformed

    private void jTextField_NumeroCNHKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_NumeroCNHKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_NumeroCNHKeyTyped

    private void jTextField_NomeCompletoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_NomeCompletoKeyTyped
        String caracteres = "0123456789";
        if (caracteres.contains(evt.getKeyChar() + "")) {
            evt.consume();
        }
    }//GEN-LAST:event_jTextField_NomeCompletoKeyTyped

    private void jButton_SelecionarFotoProprietarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_SelecionarFotoProprietarioActionPerformed
        try {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivos de Imagem", "jpg", "jpeg", "png", "gif");
            File diretorioInicial = new File(".\\src\\img\\fotoProprietarios\\");
            fileChooser.setCurrentDirectory(diretorioInicial);
            fileChooser.setFileFilter(filter);
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                origemGlobalFotoProprietario = fileChooser.getSelectedFile();
                setFotoProprietario(fileChooser.getSelectedFile().getPath());
            }

        } catch (HeadlessException erro) {
            JOptionPane.showMessageDialog(this, erro.getMessage());
        }

    }//GEN-LAST:event_jButton_SelecionarFotoProprietarioActionPerformed

    private void jButton_exportarExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_exportarExcelActionPerformed
        ExcelExporter exporter = new ExcelExporter();
        try {
            exporter.exportToExcel(jTable_Saida);
        } catch (Exception ex) {

        }
    }//GEN-LAST:event_jButton_exportarExcelActionPerformed

    private void jButton_exportarPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_exportarPDFActionPerformed
        PDFExporter pdfExporter = new PDFExporter();
        try {
            pdfExporter.exportToPDF(jTable_Saida);
        } catch (Exception ex) {

        }
    }//GEN-LAST:event_jButton_exportarPDFActionPerformed

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
            java.util.logging.Logger.getLogger(TelaCadastroProprietario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaCadastroProprietario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaCadastroProprietario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaCadastroProprietario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaCadastroProprietario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_Alterar;
    private javax.swing.JButton jButton_Limpar;
    private javax.swing.JButton jButton_Salvar;
    private javax.swing.JButton jButton_SelecionarCNH;
    private javax.swing.JButton jButton_SelecionarFotoProprietario;
    private javax.swing.JButton jButton_Voltar;
    private javax.swing.JButton jButton_exportarExcel;
    private javax.swing.JButton jButton_exportarPDF;
    private javax.swing.JComboBox<String> jComboBox_CategoriaCNH;
    private javax.swing.JComboBox<String> jComboBox_DDD;
    private javax.swing.JComboBox<String> jComboBox_DDI;
    private javax.swing.JFormattedTextField jFormattedTextField_CPF;
    private javax.swing.JFormattedTextField jFormattedTextField_NumeroTelefone;
    private javax.swing.JLabel jLabel_Descricao;
    private javax.swing.JLabel jLabel_Descricao1;
    private javax.swing.JLabel jLabel_EmblemaGrupo;
    private javax.swing.JLabel jLabel_ID10;
    private javax.swing.JLabel jLabel_ID5;
    private javax.swing.JLabel jLabel_ID6;
    private javax.swing.JLabel jLabel_ID7;
    private javax.swing.JLabel jLabel_ID8;
    private javax.swing.JLabel jLabel_LogoFotoProprietario;
    private javax.swing.JLabel jLabel_LogoImagem;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable_Saida;
    private javax.swing.JTextField jTextField_EMail;
    private javax.swing.JTextField jTextField_NomeCompleto;
    private javax.swing.JTextField jTextField_NumeroCNH;
    // End of variables declaration//GEN-END:variables
}
