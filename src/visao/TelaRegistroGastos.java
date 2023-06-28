/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package visao;

import controle.ExcelExporter;
import controle.PDFExporter;
import controle.ProprietarioControle;
import controle.RegistraGastoControle;
import controle.TipoGastosControle;
import controle.VeiculoControle;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelos.IPropietarioCRUD;
import modelos.RegistraGastos;
import modelos.IRegistraGastosCRUD;
import modelos.ITipoGastosCRUD;
import modelos.IVeiculoCRUD;
import modelos.Proprietario;
import modelos.TipoGastos;
import modelos.Veiculo;


public class TelaRegistroGastos extends javax.swing.JFrame {

    IRegistraGastosCRUD registroGastosControle = null;
    DefaultTableModel model;
    ArrayList<Proprietario> listaDeProprietarios = null;
    ArrayList<Veiculo> listaDeVeiculos = null;
    ArrayList<TipoGastos> listaDeGastos = null;
    IPropietarioCRUD proprietario = null;
    IVeiculoCRUD veiculo = null;
    ITipoGastosCRUD tipoGastos = null;
    Proprietario proprietarioSelect = null;
    TipoGastos tipoGastosSelect = null;
    Veiculo veiculoSelect = null;

 
    public TelaRegistroGastos() {
        initComponents();
        this.setResizable(false);
        this.setTitle("SCGV - Sistema de Controle de Gastos Veicular - Registrar gastos");
        this.setLocationRelativeTo(null);
        registroGastosControle = new RegistraGastoControle();
        proprietario = new ProprietarioControle();
        veiculo = new VeiculoControle();
        tipoGastos = new TipoGastosControle();
        iniciaListas();
        preencherComboBoxTipoGastos();
        preencherComboBoxProprietario();
        MostrarGastos();
    }

    // Inicia as listas 
    private void iniciaListas() {
        try {
            listaDeProprietarios = proprietario.listagemDePropietario();
            listaDeVeiculos = veiculo.listagemDeVeiculos();
            listaDeGastos = tipoGastos.listagemDeGastos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    // Preenche  ComboBox Proprietario
    private void preencherComboBoxProprietario() {
        jComboBox_SelecionaProprietario1.removeAllItems();
        jComboBox_SelecionaProprietario1.addItem("Selecione um proprietário");
        for (Proprietario i : listaDeProprietarios) {
            jComboBox_SelecionaProprietario1.addItem(i.getNomeCompleto());
        }
    }

    //Metodo privado pra preencher combobox Veiculo recebendo uma String de proprietario
    private void preencherComboBoxVeiculo(String proprietario) {
        jComboBox_SelecionaVeiculo1.removeAllItems();
        jComboBox_SelecionaVeiculo1.addItem("");
        if (proprietario == null) {
            for (Veiculo i : listaDeVeiculos) {
                jComboBox_SelecionaVeiculo1.addItem(i.getModelo());
                veiculoSelect = i;
            }
        } else {
            System.out.println("entrou else - > " + proprietario);
            for (Veiculo i : listaDeVeiculos) {
                if (proprietario.equals(i.getCPFProprietario())) {
                    jComboBox_SelecionaVeiculo1.addItem(i.getModelo());
                    veiculoSelect = i;
                }
            }
        }
    }

    // Preenche  ComboBox TipoGastos
    private void preencherComboBoxTipoGastos() {
        try {
            jComboBox_SelecionaTipoDeGasto.removeAllItems();
            jComboBox_SelecionaTipoDeGasto.addItem("Selecione um tipo de Gasto");
            for (TipoGastos i : listaDeGastos) {
                jComboBox_SelecionaTipoDeGasto.addItem(i.getNome());
            }
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(this, erro.getMessage());
        }
    }

    // Limpa todos os campos
    private void limparCampos() {
        preencherComboBoxProprietario();
        preencherComboBoxTipoGastos();
        jComboBox_SelecionaVeiculo1.removeAllItems();
        jComboBox_SelecionaVeiculo1.addItem("");
        jComboBox_SelecionaProprietario1.setEnabled(true);
        jTextField_ID.setEnabled(true);
        jTextField_ID.setText(null);
        jTextField_ValorGasto.setText(null);
        jTextArea_Descricao.setText(null);
        jButton_SalvarGastos.setEnabled(true);
        // Obter a data atual
        LocalDate dataAtual = LocalDate.now();
        // Formatar a data no formato dd/mm/aaaa
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFormatada = dataAtual.format(formato);
        jTextField_Data.setText(dataFormatada);
    }

    // Adiciona / mostra gastos na tabela.
    private void MostrarGastos() {
        try {
            ArrayList<RegistraGastos> listaDeGastos = null;
            listaDeGastos = registroGastosControle.listagemRegistroDeGastos();
            DefaultTableModel model = (DefaultTableModel) jTable_SaidaGastos.getModel();
            model.setNumRows(0);
            if (listaDeGastos.isEmpty()) {
                return;
            }
            for (int pos = 0; pos < listaDeGastos.size(); pos++) {
                RegistraGastos gasto = listaDeGastos.get(pos);
                String[] linha = new String[7];
                linha[0] = gasto.getId() + "";
                linha[1] = gasto.getNomeProprietario();
                linha[2] = gasto.getVeiculo();
                linha[3] = gasto.getTipoDeGasto();
                linha[4] = gasto.getData() + "";
                linha[5] = gasto.getValorGasto() + "";
                linha[6] = gasto.getDescricao();
                model.addRow(linha);
            }
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(rootPane, erro.getMessage());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dateChooser_Data = new com.raven.datechooser.DateChooser();
        jPanel1 = new javax.swing.JPanel();
        jButton_Voltar = new javax.swing.JButton();
        jLabel_EmblemaGrupo = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jComboBox_SelecionaProprietario1 = new javax.swing.JComboBox<>();
        jLabel_ID2 = new javax.swing.JLabel();
        jLabel_ID3 = new javax.swing.JLabel();
        jComboBox_SelecionaVeiculo1 = new javax.swing.JComboBox<>();
        jComboBox_SelecionaTipoDeGasto = new javax.swing.JComboBox<>();
        jLabel_ID4 = new javax.swing.JLabel();
        jButton_CadastrarTipoGasto = new javax.swing.JButton();
        jLabel_ID5 = new javax.swing.JLabel();
        jTextField_ID = new javax.swing.JTextField();
        jTextField_ValorGasto = new javax.swing.JTextField();
        jLabel_Descricao = new javax.swing.JLabel();
        jLabel_Descricao1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea_Descricao = new javax.swing.JTextArea();
        jButton_LimparCampos = new javax.swing.JButton();
        jButton_SalvarGastos = new javax.swing.JButton();
        jTextField_Data = new javax.swing.JTextField();
        jLabel_ID6 = new javax.swing.JLabel();
        jButton_Alterar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_SaidaGastos = new javax.swing.JTable();
        jButton_exportarExcel = new javax.swing.JButton();
        jButton_exportarExcel1 = new javax.swing.JButton();

        dateChooser_Data.setForeground(new java.awt.Color(0, 0, 204));
        dateChooser_Data.setDateFormat("dd/MM/yyyy");
        dateChooser_Data.setTextRefernce(jTextField_Data);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 204, 255));

        jButton_Voltar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/voltar-24.png"))); // NOI18N
        jButton_Voltar.setText("Voltar");
        jButton_Voltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_VoltarActionPerformed(evt);
            }
        });

        jLabel_EmblemaGrupo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_EmblemaGrupo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/scgv.png"))); // NOI18N
        jLabel_EmblemaGrupo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton_Voltar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(509, 509, 509)
                    .addComponent(jLabel_EmblemaGrupo)
                    .addContainerGap(509, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jButton_Voltar)
                .addContainerGap(70, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(11, 11, 11)
                    .addComponent(jLabel_EmblemaGrupo)
                    .addContainerGap(11, Short.MAX_VALUE)))
        );

        jPanel2.setBackground(new java.awt.Color(0, 204, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cadastrar novo Gasto", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(255, 255, 0))); // NOI18N

        jComboBox_SelecionaProprietario1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        jComboBox_SelecionaProprietario1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_SelecionaProprietario1ActionPerformed(evt);
            }
        });

        jLabel_ID2.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel_ID2.setText("Proprietario:");

        jLabel_ID3.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel_ID3.setText("Veiculo:");

        jComboBox_SelecionaVeiculo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_SelecionaVeiculo1ActionPerformed(evt);
            }
        });

        jComboBox_SelecionaTipoDeGasto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        jComboBox_SelecionaTipoDeGasto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_SelecionaTipoDeGastoActionPerformed(evt);
            }
        });

        jLabel_ID4.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel_ID4.setText("Tipo de Gasto:");

        jButton_CadastrarTipoGasto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/inserir-24.png"))); // NOI18N
        jButton_CadastrarTipoGasto.setText("Cadastrar Tipo de Gastos");
        jButton_CadastrarTipoGasto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_CadastrarTipoGastoActionPerformed(evt);
            }
        });

        jLabel_ID5.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel_ID5.setText("ID:");

        jTextField_ID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_IDActionPerformed(evt);
            }
        });
        jTextField_ID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_IDKeyTyped(evt);
            }
        });

        jTextField_ValorGasto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_ValorGastoActionPerformed(evt);
            }
        });
        jTextField_ValorGasto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_ValorGastoKeyTyped(evt);
            }
        });

        jLabel_Descricao.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel_Descricao.setText("Valor Gasto:");

        jLabel_Descricao1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel_Descricao1.setText("Descrição:");

        jTextArea_Descricao.setColumns(20);
        jTextArea_Descricao.setRows(5);
        jScrollPane2.setViewportView(jTextArea_Descricao);

        jButton_LimparCampos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/limpar-24.png"))); // NOI18N
        jButton_LimparCampos.setText("Limpar");
        jButton_LimparCampos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_LimparCamposActionPerformed(evt);
            }
        });

        jButton_SalvarGastos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/salvar-24.png"))); // NOI18N
        jButton_SalvarGastos.setText("Salvar");
        jButton_SalvarGastos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_SalvarGastosActionPerformed(evt);
            }
        });

        jLabel_ID6.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel_ID6.setText("Data:");

        jButton_Alterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/alterar-24.png"))); // NOI18N
        jButton_Alterar.setText("Alterar");
        jButton_Alterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_AlterarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGap(284, 284, 284)
                        .addComponent(jTextField_Data, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton_CadastrarTipoGasto)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel_ID3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jComboBox_SelecionaVeiculo1, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel_ID2)
                                        .addGap(18, 18, 18)
                                        .addComponent(jComboBox_SelecionaProprietario1, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGap(12, 12, 12)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel_ID6)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addComponent(jLabel_ID4)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jComboBox_SelecionaTipoDeGasto, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addComponent(jLabel_ID5)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jTextField_ID, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jLabel_Descricao))))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jButton_LimparCampos)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton_Alterar)
                                .addGap(18, 18, 18)
                                .addComponent(jButton_SalvarGastos))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel_Descricao1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField_ValorGasto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addGap(20, 20, 20))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox_SelecionaProprietario1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_ID2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox_SelecionaVeiculo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_ID3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox_SelecionaTipoDeGasto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_ID4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton_CadastrarTipoGasto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_Data, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_ID6))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_ID5)
                    .addComponent(jTextField_ID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_Descricao)
                    .addComponent(jTextField_ValorGasto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_Descricao1)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_SalvarGastos)
                    .addComponent(jButton_LimparCampos)
                    .addComponent(jButton_Alterar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(0, 204, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Gastos Registrados", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(255, 255, 0))); // NOI18N

        jTable_SaidaGastos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Proprietario", "Veiculo", "Tipo de Gasto", "Data", "Valor Gasto", "Descrição"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable_SaidaGastos.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jTable_SaidaGastosAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jTable_SaidaGastos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_SaidaGastosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable_SaidaGastos);

        jButton_exportarExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/excel.png"))); // NOI18N
        jButton_exportarExcel.setText("Exportar");
        jButton_exportarExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_exportarExcelActionPerformed(evt);
            }
        });

        jButton_exportarExcel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/pdf.png"))); // NOI18N
        jButton_exportarExcel1.setText("Exportar");
        jButton_exportarExcel1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_exportarExcel1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 818, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton_exportarExcel)
                .addGap(111, 111, 111)
                .addComponent(jButton_exportarExcel1)
                .addGap(239, 239, 239))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_exportarExcel1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_exportarExcel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(23, 23, 23))
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

    private void jButton_CadastrarTipoGastoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_CadastrarTipoGastoActionPerformed
        dispose();
        TelaRegistroTipoGastos registroTipoGastos = new TelaRegistroTipoGastos();
        registroTipoGastos.dispose();
        registroTipoGastos.setLocationRelativeTo(this);
        registroTipoGastos.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        registroTipoGastos.setVisible(true);
    }//GEN-LAST:event_jButton_CadastrarTipoGastoActionPerformed

    private void jComboBox_SelecionaProprietario1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_SelecionaProprietario1ActionPerformed
        try {
            if (jComboBox_SelecionaProprietario1.getSelectedIndex() == 0) {
                jComboBox_SelecionaVeiculo1.setEnabled(true);
            } else {
                jComboBox_SelecionaVeiculo1.setEnabled(true);
                for (Proprietario i : listaDeProprietarios) {
                    if (i.getNomeCompleto().equals(jComboBox_SelecionaProprietario1.getSelectedItem())) {
                        proprietarioSelect = i;
                        preencherComboBoxVeiculo(proprietarioSelect.getCpf());
                        break;
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        }

    }//GEN-LAST:event_jComboBox_SelecionaProprietario1ActionPerformed

    private void jButton_VoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_VoltarActionPerformed
        dispose();
        TelaPrincipal voltaTelaPrincipal = new TelaPrincipal();
        voltaTelaPrincipal.dispose();
        voltaTelaPrincipal.setLocationRelativeTo(this);
        voltaTelaPrincipal.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        voltaTelaPrincipal.setVisible(true);
    }//GEN-LAST:event_jButton_VoltarActionPerformed

    private void jComboBox_SelecionaTipoDeGastoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_SelecionaTipoDeGastoActionPerformed
        try {
            for (TipoGastos i : listaDeGastos) {
                if (i.getNome().equals(jComboBox_SelecionaTipoDeGasto.getSelectedItem())) {
                    tipoGastosSelect = i;
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        }

    }//GEN-LAST:event_jComboBox_SelecionaTipoDeGastoActionPerformed

    private void jTable_SaidaGastosAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jTable_SaidaGastosAncestorAdded

    }//GEN-LAST:event_jTable_SaidaGastosAncestorAdded

    private void jButton_SalvarGastosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_SalvarGastosActionPerformed
        try {
            // Verifica se os campos estão vazio, caso esteja passa por parametro algum valor tratado no controle de Registro de Gastos
            String _cpf_proprietario = "";
            String _nome_completo = "";
            if (proprietarioSelect != null) {
                _cpf_proprietario = proprietarioSelect.getCpf();
                _nome_completo = proprietarioSelect.getNomeCompleto();
            }

            Integer _veiculo_id = -1;
            String _modelo_veiculo = "";
            if (veiculoSelect != null) {
                _veiculo_id = veiculoSelect.getId();
                _modelo_veiculo = veiculoSelect.getModelo();
            }

            Integer _tipo_de_gasto_id = -1;
            String _tipo_de_gasto = "";
            if (tipoGastosSelect != null) {
                _tipo_de_gasto_id = tipoGastosSelect.getId();
                _tipo_de_gasto = tipoGastosSelect.getNome();
            }

            Integer _id = -1;
            if (!jTextField_ID.getText().isBlank()) {
                _id = Integer.valueOf(jTextField_ID.getText());
            }

            Float _valor_gasto = 0.0f;
            if (!jTextField_ValorGasto.getText().isBlank()) {
                _valor_gasto = Float.valueOf(jTextField_ValorGasto.getText());
            }
            RegistraGastos gasto = new RegistraGastos(_id,
                    jTextField_Data.getText(),
                    _valor_gasto,
                    jTextArea_Descricao.getText(),
                    _cpf_proprietario,
                    _nome_completo,
                    _veiculo_id,
                    _modelo_veiculo,
                    _tipo_de_gasto_id,
                    _tipo_de_gasto
            );
            registroGastosControle.incluir(gasto);
            limparCampos();
            MostrarGastos();
            JOptionPane.showMessageDialog(rootPane, "Gasto cadastrado com sucesso !");
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        }
    }//GEN-LAST:event_jButton_SalvarGastosActionPerformed

    private void jButton_LimparCamposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_LimparCamposActionPerformed
        limparCampos();
    }//GEN-LAST:event_jButton_LimparCamposActionPerformed

    private void jTextField_ValorGastoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_ValorGastoKeyTyped
        // Evento para aceitar somente numeros e . no textfield
        String caracteres = "0123456789.";
        if (!caracteres.contains(evt.getKeyChar() + "")) {
            evt.consume();
        }
    }//GEN-LAST:event_jTextField_ValorGastoKeyTyped

    private void jTextField_ValorGastoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_ValorGastoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_ValorGastoActionPerformed

    private void jComboBox_SelecionaVeiculo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_SelecionaVeiculo1ActionPerformed
        try {
            if (jComboBox_SelecionaVeiculo1.getSelectedItem() != null) {
                for (Veiculo i : listaDeVeiculos) {

                    if (i.getModelo().equals(jComboBox_SelecionaVeiculo1.getSelectedItem())) {
                        veiculoSelect = i;
                        break;
                    }
                }
            }
        } catch (Exception erro) {
            erro.printStackTrace(System.out);
            JOptionPane.showMessageDialog(rootPane, erro.getMessage());
        }

    }//GEN-LAST:event_jComboBox_SelecionaVeiculo1ActionPerformed

    private void jTable_SaidaGastosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_SaidaGastosMouseClicked
        try {
            jTextField_ID.setEnabled(false);
            int linha = jTable_SaidaGastos.getSelectedRow();
            // Pega valor proprietario
            String nomeProprietario = (String) jTable_SaidaGastos.getValueAt(linha, 1);
            for (Proprietario i : listaDeProprietarios) {
                if (nomeProprietario.equals(i.getNomeCompleto())) {
                    proprietarioSelect = i;
                }
            }
            jComboBox_SelecionaProprietario1.removeAllItems();
            jComboBox_SelecionaProprietario1.addItem(proprietarioSelect.getNomeCompleto());
            jComboBox_SelecionaProprietario1.setEnabled(false);
            // pega valor da tabela "Veiculo"
            String nomeVeiculo = (String) jTable_SaidaGastos.getValueAt(linha, 2);
            for (Veiculo i : listaDeVeiculos) {
                if (nomeVeiculo.equals(i.getModelo())) {
                    veiculoSelect = i;
                }
            }
            jComboBox_SelecionaVeiculo1.removeAllItems();
            jComboBox_SelecionaVeiculo1.addItem(veiculoSelect.getModelo());
            jComboBox_SelecionaVeiculo1.setEnabled(false);
            jComboBox_SelecionaTipoDeGasto.removeAllItems();
            preencherComboBoxTipoGastos();
            jTextField_ID.setText((String) jTable_SaidaGastos.getValueAt(linha, 0));;
            jTextField_Data.setText((String) jTable_SaidaGastos.getValueAt(linha, 4));
            jTextField_ValorGasto.setText((String) jTable_SaidaGastos.getValueAt(linha, 5));
            jTextArea_Descricao.setText((String) jTable_SaidaGastos.getValueAt(linha, 6));
            jButton_SalvarGastos.setEnabled(false);
        } catch (Exception erro) {
            JOptionPane.showConfirmDialog(this, erro.getMessage());
        }
    }//GEN-LAST:event_jTable_SaidaGastosMouseClicked

    private void jButton_AlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_AlterarActionPerformed
        try {
            if (jComboBox_SelecionaTipoDeGasto.getSelectedIndex() == 0) {
                throw new Exception("Nenhum Tipo de Gasto selecionado!!!");
            }

            RegistraGastos gastos = new RegistraGastos(Integer.valueOf(jTextField_ID.getText()),
                    jTextField_Data.getText(),
                    Float.parseFloat(jTextField_ValorGasto.getText()),
                    jTextArea_Descricao.getText(),
                    proprietarioSelect.getCpf(),
                    proprietarioSelect.getNomeCompleto(),
                    veiculoSelect.getId(),
                    veiculoSelect.getModelo(),
                    tipoGastosSelect.getId(),
                    tipoGastosSelect.getNome()
            );
            registroGastosControle.alterar(gastos);
            preencherComboBoxProprietario();
            preencherComboBoxTipoGastos();
            limparCampos();
            MostrarGastos();
            JOptionPane.showMessageDialog(rootPane, "Gasto alterado com sucesso !");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }//GEN-LAST:event_jButton_AlterarActionPerformed

    private void jTextField_IDKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_IDKeyTyped
        // Evento para aceitar somente numeros no textfield
        String caracteres = "0123456789";
        if (!caracteres.contains(evt.getKeyChar() + "")) {
            evt.consume();
        }
    }//GEN-LAST:event_jTextField_IDKeyTyped

    private void jTextField_IDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_IDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_IDActionPerformed

    private void jButton_exportarExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_exportarExcelActionPerformed
        // Botao Exportar em excel
        ExcelExporter exporter = new ExcelExporter();
        try {
            exporter.exportToExcel(jTable_SaidaGastos);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }//GEN-LAST:event_jButton_exportarExcelActionPerformed

    private void jButton_exportarExcel1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_exportarExcel1ActionPerformed
        // Botao Exportar em PDF
        PDFExporter pdfExporter = new PDFExporter();
        try {
            pdfExporter.exportToPDF(jTable_SaidaGastos);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }//GEN-LAST:event_jButton_exportarExcel1ActionPerformed

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
            java.util.logging.Logger.getLogger(TelaRegistroGastos.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaRegistroGastos.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaRegistroGastos.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaRegistroGastos.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaRegistroGastos().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.raven.datechooser.DateChooser dateChooser_Data;
    private javax.swing.JButton jButton_Alterar;
    private javax.swing.JButton jButton_CadastrarTipoGasto;
    private javax.swing.JButton jButton_LimparCampos;
    private javax.swing.JButton jButton_SalvarGastos;
    private javax.swing.JButton jButton_Voltar;
    private javax.swing.JButton jButton_exportarExcel;
    private javax.swing.JButton jButton_exportarExcel1;
    private javax.swing.JComboBox<String> jComboBox_SelecionaProprietario1;
    private javax.swing.JComboBox<String> jComboBox_SelecionaTipoDeGasto;
    private javax.swing.JComboBox<String> jComboBox_SelecionaVeiculo1;
    private javax.swing.JLabel jLabel_Descricao;
    private javax.swing.JLabel jLabel_Descricao1;
    private javax.swing.JLabel jLabel_EmblemaGrupo;
    private javax.swing.JLabel jLabel_ID2;
    private javax.swing.JLabel jLabel_ID3;
    private javax.swing.JLabel jLabel_ID4;
    private javax.swing.JLabel jLabel_ID5;
    private javax.swing.JLabel jLabel_ID6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable_SaidaGastos;
    private javax.swing.JTextArea jTextArea_Descricao;
    private javax.swing.JTextField jTextField_Data;
    private javax.swing.JTextField jTextField_ID;
    private javax.swing.JTextField jTextField_ValorGasto;
    // End of variables declaration//GEN-END:variables
}
