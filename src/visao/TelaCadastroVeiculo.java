package visao;

import controle.ExcelExporter;
import controle.MarcaControle;
import controle.ModeloControle;
import controle.PDFExporter;
import controle.ProprietarioControle;
import controle.VeiculoControle;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import modelos.EnumTipoCombustivel;
import modelos.IMarcaCRUD;
import modelos.IModeloVeiculo;
import modelos.IPropietarioCRUD;
import modelos.IVeiculoCRUD;
import modelos.Marca;
import modelos.ModeloVeiculo;
import modelos.Proprietario;
import modelos.Veiculo;

public class TelaCadastroVeiculo extends javax.swing.JFrame {

    //EnumCategoriaCNH enumCategoria = null;
    File origemMarcaGlobal = null;
    File origemVeiculoGlobal = null;
    ArrayList<Marca> listaDeMarcas = null;
    ArrayList<ModeloVeiculo> listaDeModelos = null;
    ArrayList<Proprietario> listaDeProprietarios = null;
    IVeiculoCRUD veiculoCRUD = null;
    IMarcaCRUD marcaCRUD = null;
    IModeloVeiculo modeloCRUD = null;
    IPropietarioCRUD proprietarioCRUD = null;
    Marca marca = null;
    ModeloVeiculo modelo = null;
    Proprietario proprietario = null;


    public TelaCadastroVeiculo() {
        initComponents();
        this.setResizable(false);
        this.setTitle("SCGV - Sistema de Controle de Gastos Veicular - Cadastro de Veiculo");
        this.setLocationRelativeTo(null);
        veiculoCRUD = new VeiculoControle();
        marcaCRUD = new MarcaControle();
        modeloCRUD = new ModeloControle();
        proprietarioCRUD = new ProprietarioControle();
        jButton_Alterar.setEnabled(false);
        iniciaListas();
        preencherComboBoxProprietário();
        preencherComboBoxCombustivel();
        preencherComboBoxMarca();
        preencherComboBoxModelo(null);
        setImagemMarca(".\\src\\img\\logo\\sem-imagem.png");
        setImagemModelo(".\\src\\img\\logo\\sem-imagem.png");
        MostrarVeiculos();
    }

    private void iniciaListas() {
        try {
            listaDeMarcas = marcaCRUD.listagemDeMarcas();
            listaDeModelos = modeloCRUD.listagemDeModelos();
            listaDeProprietarios = proprietarioCRUD.listagemDePropietario();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

    }

    // Metodo para buscar e preencher proprietarios na jTable
    private void MostrarVeiculos() {
        try {
            ArrayList<Veiculo> listaDeVeiculos = veiculoCRUD.listagemDeVeiculos();
            Collections.sort(listaDeVeiculos, (Veiculo o1, Veiculo o2) -> {
                return String.valueOf(o1.getId()).compareTo(String.valueOf(o2.getId()));
            });
            DefaultTableModel model = (DefaultTableModel) jTable_Saida.getModel();
            model.setNumRows(0);
            if (listaDeVeiculos.isEmpty()) {
                return;
            }
            for (Veiculo i : listaDeVeiculos) {
                String[] linha = i.toTabela().split(";");
                model.addRow(linha);
            }

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(rootPane, erro.getMessage());
        }
    }

    // Metodo privado para limpar campos apos inserçao
    private void limparCampos() {
        preencherComboBoxCombustivel();
        preencherComboBoxMarca();
        preencherComboBoxModelo(null);
        preencherComboBoxProprietário();
        jTextField_ID.setEnabled(true);
        jTextField_Placa.setEnabled(true);
        jComboBox_Marca.setEnabled(true);
        jComboBox_Modelo.setEnabled(true);
        jButton_Salvar.setEnabled(true);
        jTextField_ID.setText(null);
        jTextField_KMAtual.setText(null);
        jTextField_Placa.setText(null);
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

    //Metodo privado pra preencher combobox com Enum de categoriaCNH
    private void preencherComboBoxProprietário() {
        jComboBox_Proprietario.removeAllItems();
        jComboBox_Proprietario.addItem("Selecione o proprietario");
        for (Proprietario i : listaDeProprietarios) {
            jComboBox_Proprietario.addItem(i.getNomeCompleto());
            proprietario = i;
        }
        jComboBox_Proprietario.setSelectedIndex(0);
    }

    //Metodo privado pra preencher combobox com Enum de categoriaCNH
    private void preencherComboBoxCombustivel() {
        EnumTipoCombustivel.TipoCombustivel[] tipos = EnumTipoCombustivel.TipoCombustivel.values();
        for (EnumTipoCombustivel.TipoCombustivel tipo : tipos) {
            jComboBox_TipoCombustivel.addItem(tipo.toString());
        }
        jComboBox_TipoCombustivel.setSelectedIndex(0);
    }

    //Metodo privado pra preencher combobox com Enum de DDI
    private void preencherComboBoxMarca() {
        jComboBox_Marca.removeAllItems();
        jComboBox_Marca.addItem("Selecione uma marca.");
        for (Marca i : listaDeMarcas) {
            jComboBox_Marca.addItem(i.getDescricao());
            marca = i;
        }
        jComboBox_Marca.setSelectedIndex(0);
    }

    //Metodo privado pra preencher combobox com Enum de DDI
    private void preencherComboBoxModelo(String marca) {
        jComboBox_Modelo.removeAllItems();
        jComboBox_Modelo.addItem("Selecione o modelo");
        if (marca == null) {
            for (ModeloVeiculo i : listaDeModelos) {
                jComboBox_Modelo.addItem(i.getDescricao());
                modelo = i;
            }
        } else {
            for (ModeloVeiculo i : listaDeModelos) {
                if (marca.equals(i.getNomeMarca())) {
                    jComboBox_Modelo.addItem(i.getDescricao());
                    modelo = i;
                }
            }
        }
        jComboBox_Modelo.setSelectedIndex(0);
    }

    //Metodo para mostrar a imagem da Marca quando for inserida ou selecionada na tabela
    private void setImagemMarca(String caminhoImagem) {
        ImageIcon icon = new ImageIcon(caminhoImagem);
        //definindo um tamanho limite para a imagem 
        ImageIcon resizedIcon = new ImageIcon(icon.getImage().getScaledInstance(210, 190, java.awt.Image.SCALE_SMOOTH));
        jLabel_ImagemMarca.setIcon(resizedIcon);
    }

    //Metodo para mostrar a imagem da CNH quando for inserida ou selecionada na tabela
    private void setImagemModelo(String caminhoImagem) {
        ImageIcon icon = new ImageIcon(caminhoImagem);
        //definindo um tamanho limite para a imagem 
        ImageIcon resizedIcon = new ImageIcon(icon.getImage().getScaledInstance(210, 190, java.awt.Image.SCALE_SMOOTH));
        jLabel_FotoVeiculo.setIcon(resizedIcon);
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
        jButton_exportarExcel1 = new javax.swing.JButton();
        jButton_exportarExcel2 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel_ID5 = new javax.swing.JLabel();
        jLabel_Descricao = new javax.swing.JLabel();
        jTextField_ID = new javax.swing.JTextField();
        jComboBox_Modelo = new javax.swing.JComboBox<>();
        jLabel_ID10 = new javax.swing.JLabel();
        jLabel_ID7 = new javax.swing.JLabel();
        jTextField_KMAtual = new javax.swing.JTextField();
        jButton_Salvar = new javax.swing.JButton();
        jButton_Alterar = new javax.swing.JButton();
        jButton_Limpar = new javax.swing.JButton();
        jLabel_ImagemMarca = new javax.swing.JLabel();
        jLabel_FotoVeiculo = new javax.swing.JLabel();
        jButton_SelecionarFotoVeículo = new javax.swing.JButton();
        jTextField_Placa = new javax.swing.JTextField();
        jComboBox_TipoCombustivel = new javax.swing.JComboBox<>();
        jLabel_Descricao2 = new javax.swing.JLabel();
        jLabel_ID6 = new javax.swing.JLabel();
        jComboBox_Proprietario = new javax.swing.JComboBox<>();
        jComboBox_Marca = new javax.swing.JComboBox<>();

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
                .addGap(404, 404, 404)
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
                "ID", "Proprietário", "Placa", "Marca", "Modelo", "Combustível", "KM Atual"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false, false, false, false, false
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

        jButton_exportarExcel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/pdf.png"))); // NOI18N
        jButton_exportarExcel1.setText("Exportar");

        jButton_exportarExcel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/pdf.png"))); // NOI18N
        jButton_exportarExcel2.setText("Exportar");
        jButton_exportarExcel2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_exportarExcel2ActionPerformed(evt);
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
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 658, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(234, 234, 234)
                        .addComponent(jButton_exportarExcel)
                        .addGap(40, 40, 40)
                        .addComponent(jButton_exportarExcel2)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jButton_exportarExcel1)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 403, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton_exportarExcel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton_exportarExcel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(37, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jButton_exportarExcel1)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jPanel2.setBackground(new java.awt.Color(0, 204, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cadastrar novo Veiculo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(255, 255, 0))); // NOI18N

        jLabel_ID5.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel_ID5.setText("ID:");

        jLabel_Descricao.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel_Descricao.setText("Placa");

        jTextField_ID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_IDKeyTyped(evt);
            }
        });

        jComboBox_Modelo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione o modelo" }));
        jComboBox_Modelo.setToolTipText("");
        jComboBox_Modelo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_ModeloActionPerformed(evt);
            }
        });

        jLabel_ID10.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel_ID10.setText("Combustível");

        jLabel_ID7.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel_ID7.setText("KM atual");

        jTextField_KMAtual.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_KMAtualKeyTyped(evt);
            }
        });

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

        jLabel_ImagemMarca.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_ImagemMarca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logo/sem-imagem.png"))); // NOI18N
        jLabel_ImagemMarca.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3), "Marca", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 16))); // NOI18N
        jLabel_ImagemMarca.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jLabel_ImagemMarcaPropertyChange(evt);
            }
        });

        jLabel_FotoVeiculo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_FotoVeiculo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logo/sem-imagem.png"))); // NOI18N
        jLabel_FotoVeiculo.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3), "Modelo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 16))); // NOI18N

        jButton_SelecionarFotoVeículo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/selecionar-24.png"))); // NOI18N
        jButton_SelecionarFotoVeículo.setText("Selecionar Imagem Veiculo");
        jButton_SelecionarFotoVeículo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_SelecionarFotoVeículoActionPerformed(evt);
            }
        });

        jTextField_Placa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_PlacaKeyTyped(evt);
            }
        });

        jComboBox_TipoCombustivel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        jComboBox_TipoCombustivel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_TipoCombustivelActionPerformed(evt);
            }
        });

        jLabel_Descricao2.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel_Descricao2.setText("Marca e Modelo");

        jLabel_ID6.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel_ID6.setText("Proprietário");

        jComboBox_Proprietario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione o proprietario" }));
        jComboBox_Proprietario.setToolTipText("");
        jComboBox_Proprietario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_ProprietarioActionPerformed(evt);
            }
        });

        jComboBox_Marca.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione uma marca." }));
        jComboBox_Marca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_MarcaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel_ID5)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel_ImagemMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel_FotoVeiculo, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel_ID7)
                            .addComponent(jLabel_ID10))
                        .addContainerGap(20, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel_Descricao)
                                    .addComponent(jLabel_ID6)
                                    .addComponent(jLabel_Descricao2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jTextField_ID, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBox_Proprietario, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(jComboBox_Marca, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jComboBox_Modelo, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jTextField_Placa)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jTextField_KMAtual, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBox_TipoCombustivel, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(20, 20, 20))))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(88, 88, 88)
                .addComponent(jButton_Limpar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_Alterar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_Salvar)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton_SelecionarFotoVeículo)
                .addGap(34, 34, 34))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_ID5)
                    .addComponent(jTextField_ID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_ID6)
                    .addComponent(jComboBox_Proprietario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_Descricao)
                    .addComponent(jTextField_Placa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_Descricao2)
                    .addComponent(jComboBox_Modelo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox_Marca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_ID10)
                    .addComponent(jComboBox_TipoCombustivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_ID7)
                    .addComponent(jTextField_KMAtual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_ImagemMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_FotoVeiculo, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_SelecionarFotoVeículo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_Limpar)
                    .addComponent(jButton_Alterar)
                    .addComponent(jButton_Salvar))
                .addContainerGap(10, Short.MAX_VALUE))
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
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
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
            jButton_Alterar.setEnabled(true);
            jButton_Salvar.setEnabled(false);
            jTextField_ID.setEnabled(false);
            jTextField_Placa.setEnabled(false);
            jComboBox_Marca.setEnabled(false);
            jComboBox_Modelo.setEnabled(false);
            jComboBox_TipoCombustivel.setEnabled(true);
            int linha = jTable_Saida.getSelectedRow();
            Integer id = Integer.valueOf((String) jTable_Saida.getValueAt(linha, 0));
            Veiculo veiculo = veiculoCRUD.consultar(id);
            proprietario = proprietarioCRUD.consultar(veiculo.getProprietario());
            marca = marcaCRUD.consulaMarca(veiculo.getIdMarca());
            modelo = modeloCRUD.consultar(veiculo.getIdModelo());
            jTextField_ID.setText(String.valueOf(veiculo.getId()));
            String nomeProprietario = (String) jTable_Saida.getValueAt(linha, 1);
            for (Proprietario i : listaDeProprietarios) {
                if (nomeProprietario.equals(i.getNomeCompleto())) {
                    proprietario = i;
                }
            }
            preencherComboBoxProprietário();

            jTextField_Placa.setText(veiculo.getPlaca());
            jComboBox_Marca.setSelectedItem(marca.getDescricao());
            jComboBox_Modelo.setSelectedItem(modelo.getDescricao());
            preencherComboBoxCombustivel();
            jTextField_KMAtual.setText(veiculo.getKmAtual());
            origemMarcaGlobal = new File(veiculo.getFotoMarca().getPath());
            origemVeiculoGlobal = new File(veiculo.getFotoVeiculo().getPath());
            setImagemMarca(veiculo.getFotoMarca().getPath());
            setImagemModelo(veiculo.getFotoVeiculo().getPath());
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_jTable_SaidaMouseClicked

    private void jButton_exportarExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_exportarExcelActionPerformed
        ExcelExporter exporter = new ExcelExporter();
        try {
            exporter.exportToExcel(jTable_Saida);
        } catch (Exception ex) {
            Logger.getLogger(TelaCadastroVeiculo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton_exportarExcelActionPerformed

    private void jComboBox_ProprietarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_ProprietarioActionPerformed
        if (jComboBox_Proprietario.getSelectedItem() != null) {
            for (Proprietario i : listaDeProprietarios) {
                if (i.getNomeCompleto().equals(jComboBox_Proprietario.getSelectedItem())) {
                    proprietario = i;
                    break;
                }
            }
        } else {
            preencherComboBoxModelo(null);
            setImagemMarca(".\\src\\img\\logo\\sem-imagem.png");
        }
    }//GEN-LAST:event_jComboBox_ProprietarioActionPerformed

    private void jComboBox_TipoCombustivelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_TipoCombustivelActionPerformed

    }//GEN-LAST:event_jComboBox_TipoCombustivelActionPerformed

    private void jTextField_PlacaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_PlacaKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_PlacaKeyTyped

    private void jButton_SelecionarFotoVeículoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_SelecionarFotoVeículoActionPerformed
        try {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivos de Imagem", "jpg", "jpeg", "png", "gif", "jfif");
            File diretorioInicial = new File(".\\src\\img\\veiculos\\");
            fileChooser.setCurrentDirectory(diretorioInicial);
            fileChooser.setFileFilter(filter);
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                origemVeiculoGlobal = fileChooser.getSelectedFile();
                setImagemModelo(fileChooser.getSelectedFile().getPath());
            }

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, erro.getMessage());
        }

    }//GEN-LAST:event_jButton_SelecionarFotoVeículoActionPerformed

    private void jLabel_ImagemMarcaPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jLabel_ImagemMarcaPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel_ImagemMarcaPropertyChange

    private void jButton_LimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_LimparActionPerformed
        limparCampos();
        jTextField_ID.setEnabled(true);
        jTextField_KMAtual.setEnabled(true);
        jTextField_Placa.setEnabled(true);
        jComboBox_Marca.setEnabled(true);
        jComboBox_Modelo.setEnabled(true);
        jComboBox_Proprietario.setEnabled(true);
        jComboBox_TipoCombustivel.setEnabled(true);
        jButton_Alterar.setEnabled(false);
        jButton_Salvar.setEnabled(true);
        setImagemMarca(".\\src\\img\\cnh\\sem-imagem.png");
        setImagemModelo(".\\src\\img\\cnh\\sem-imagem.png");
    }//GEN-LAST:event_jButton_LimparActionPerformed

    private void jButton_AlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_AlterarActionPerformed
        try {
            String p_nome = "Selecione o proprietario";
            String p_cpf = "";
            if (!p_nome.isBlank()) {
                p_nome = jComboBox_Proprietario.getSelectedItem().toString();
                p_cpf = proprietario.getCpf();
            }

            Veiculo veiculo = new Veiculo(
                    Integer.valueOf(jTextField_ID.getText()),
                    p_cpf,
                    p_nome,
                    jTextField_Placa.getText(),
                    marca.getDescricao(),
                    marca.getId(),
                    modelo.getDescricao(),
                    modelo.getId(),
                    jComboBox_TipoCombustivel.getSelectedItem().toString(),
                    jTextField_KMAtual.getText(),
                    origemMarcaGlobal,
                    origemVeiculoGlobal
            );
            veiculoCRUD.alterar(veiculo);
            origemMarcaGlobal = null;
            origemVeiculoGlobal = null;
            MostrarVeiculos();
            JOptionPane.showMessageDialog(rootPane, "Veiculo alterado com sucesso !");
            limparCampos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage()
            );
        }
    }//GEN-LAST:event_jButton_AlterarActionPerformed

    private void jButton_SalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_SalvarActionPerformed
        try {
            Integer _id = -1;
            if (!jTextField_ID.getText().isBlank()) {
                _id = Integer.valueOf(jTextField_ID.getText());
            }
            String p_nome = "Selecione o proprietario";
            String p_cpf = "";
            if (!p_nome.isBlank()) {
                p_nome = jComboBox_Proprietario.getSelectedItem().toString();
                p_cpf = proprietario.getCpf();
            }
            
            String _marca_descricao = "Selecione uma marca.";
            Integer _marca_id = -1;
            
            if (!jComboBox_Marca.getSelectedItem().equals(_marca_descricao)) {
                _marca_descricao = marca.getDescricao();
                _marca_id = marca.getId();
            }
            String _modelo_descricao = "Selecione um modelo";
            Integer _modelo_id = -1;
            if (!jComboBox_Modelo.getSelectedItem().equals(_modelo_descricao)) {
                _modelo_descricao = modelo.getDescricao();
                _modelo_id = modelo.getId();
            }

            Veiculo novoVeiculo = new Veiculo(
                    _id,
                    p_cpf,
                    p_nome,
                    jTextField_Placa.getText(),
                    _marca_descricao,
                    _marca_id,
                    _modelo_descricao,
                    _modelo_id,
                    jComboBox_TipoCombustivel.getSelectedItem().toString(),
                    jTextField_KMAtual.getText(),
                    origemMarcaGlobal,
                    origemVeiculoGlobal
            );

            veiculoCRUD.incluir(novoVeiculo);
            JOptionPane.showMessageDialog(rootPane, "Veiculo cadastrado com sucesso !");
            limparCampos();
            MostrarVeiculos();
            origemVeiculoGlobal = null;
            origemMarcaGlobal = null;
            setImagemMarca(".\\src\\img\\cnh\\sem-imagem.png");
            setImagemModelo(".\\src\\img\\fotoProprietarios\\sem-imagem.png");

        } catch (Exception erro) {
            erro.printStackTrace(System.out);
            JOptionPane.showMessageDialog(null, erro.getMessage());
        }
    }//GEN-LAST:event_jButton_SalvarActionPerformed

    private void jTextField_KMAtualKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_KMAtualKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_KMAtualKeyTyped

    private void jComboBox_ModeloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_ModeloActionPerformed
        if (jComboBox_Modelo.getSelectedItem() != null) {
            for (ModeloVeiculo i : listaDeModelos) {
                String comboBox = jComboBox_Modelo.getSelectedItem().toString().split(" - ")[0];
                if (i.getDescricao().equals(comboBox)) {
                    setImagemModelo(i.getFotoModelo().getPath());
                    origemVeiculoGlobal = i.getFotoModelo();
                    modelo = i;
                    break;
                }
            }
        } else {
            setImagemMarca(".\\src\\img\\logo\\sem-imagem.png");
        }
    }//GEN-LAST:event_jComboBox_ModeloActionPerformed

    private void jTextField_IDKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_IDKeyTyped
        String caracteres = "0123456789";
        if (!caracteres.contains(evt.getKeyChar() + "")) {
            evt.consume();
        }
    }//GEN-LAST:event_jTextField_IDKeyTyped

    private void jComboBox_MarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_MarcaActionPerformed
        setImagemModelo(".\\src\\img\\logo\\sem-imagem.png");
        preencherComboBoxModelo((String) jComboBox_Marca.getSelectedItem());
        if (jComboBox_Marca.getSelectedItem() != null) {
            for (Marca i : listaDeMarcas) {
                if (i.getDescricao().equals(jComboBox_Marca.getSelectedItem())) {
                    setImagemMarca(i.getLogo().getPath());
                    origemMarcaGlobal = i.getLogo();
                    marca = i;
                    break;
                }
            }
        } else {
            preencherComboBoxModelo(null);
            setImagemMarca(".\\src\\img\\logo\\sem-imagem.png");
        }
    }//GEN-LAST:event_jComboBox_MarcaActionPerformed

    private void jButton_exportarExcel2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_exportarExcel2ActionPerformed
        PDFExporter pdfExporter = new PDFExporter();
        try {
            pdfExporter.exportToPDF(jTable_Saida);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }//GEN-LAST:event_jButton_exportarExcel2ActionPerformed

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
            java.util.logging.Logger.getLogger(TelaCadastroVeiculo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaCadastroVeiculo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaCadastroVeiculo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaCadastroVeiculo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaCadastroVeiculo().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_Alterar;
    private javax.swing.JButton jButton_Limpar;
    private javax.swing.JButton jButton_Salvar;
    private javax.swing.JButton jButton_SelecionarFotoVeículo;
    private javax.swing.JButton jButton_Voltar;
    private javax.swing.JButton jButton_exportarExcel;
    private javax.swing.JButton jButton_exportarExcel1;
    private javax.swing.JButton jButton_exportarExcel2;
    private javax.swing.JComboBox<String> jComboBox_Marca;
    private javax.swing.JComboBox<String> jComboBox_Modelo;
    private javax.swing.JComboBox<String> jComboBox_Proprietario;
    private javax.swing.JComboBox<String> jComboBox_TipoCombustivel;
    private javax.swing.JLabel jLabel_Descricao;
    private javax.swing.JLabel jLabel_Descricao2;
    private javax.swing.JLabel jLabel_EmblemaGrupo;
    private javax.swing.JLabel jLabel_FotoVeiculo;
    private javax.swing.JLabel jLabel_ID10;
    private javax.swing.JLabel jLabel_ID5;
    private javax.swing.JLabel jLabel_ID6;
    private javax.swing.JLabel jLabel_ID7;
    private javax.swing.JLabel jLabel_ImagemMarca;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable_Saida;
    private javax.swing.JTextField jTextField_ID;
    private javax.swing.JTextField jTextField_KMAtual;
    private javax.swing.JTextField jTextField_Placa;
    // End of variables declaration//GEN-END:variables
}
