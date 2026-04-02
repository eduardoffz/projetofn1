package view;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.swing.JOptionPane;
import model.MovimentacaoBean;
import model.MovimentacaoDAO;
import model.UsuarioBean;

/**
 * Formulário de Cadastro e Edição de Movimentações.
 * Equivalente ao Formulario.java do projeto do professor, adaptado para o Gestor de Caixa.
 */
public class FormularioMovimentacao extends javax.swing.JFrame {

    private MovimentacaoDAO dao = new MovimentacaoDAO();
    private Dashboard dashboard;
    private UsuarioBean usuarioLogado;
    private String tipoInicial;
    private int idEditando;   // -1 = novo, >0 = editando

    /**
     * @param dashboard     Tela principal (para atualizar após salvar)
     * @param usuario       Usuário logado
     * @param tipo          "Venda" ou "Despesa"
     * @param idMovimentacao -1 para novo registro, ID para edição
     */
    public FormularioMovimentacao(Dashboard dashboard, UsuarioBean usuario, String tipo, int idMovimentacao) {
        this.dashboard     = dashboard;
        this.usuarioLogado = usuario;
        this.tipoInicial   = tipo;
        this.idEditando    = idMovimentacao;
        initComponents();

        if (idMovimentacao == -1) {
            // Modo: Novo registro
            setTitle("Gestor de Caixa - Nova " + tipo);
            jLabelFormTitulo.setText("Registrar " + tipo);
            combTipo.setSelectedItem(tipo);
            txtData.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        } else {
            // Modo: Edição
            setTitle("Gestor de Caixa - Editar Movimentação");
            jLabelFormTitulo.setText("Editar Movimentação");
            carregarDados(idMovimentacao);
        }
    }

    /** Carrega os dados da movimentação no formulário para edição */
    private void carregarDados(int id) {
        MovimentacaoBean m = dao.obterPorId(id);
        if (m != null) {
            txtDescricao.setText(m.getDescricao());
            txtValor.setText(m.getValor().toPlainString());
            combTipo.setSelectedItem(m.getTipo());
            // Formata data de SQL para dd/MM/yyyy
            LocalDate ld = m.getData().toLocalDate();
            txtData.setText(ld.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelFundo = new javax.swing.JPanel();
        jLabelFormTitulo = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabelDescricao = new javax.swing.JLabel();
        txtDescricao = new javax.swing.JTextField();
        jLabelValor = new javax.swing.JLabel();
        txtValor = new javax.swing.JTextField();
        jLabelTipo = new javax.swing.JLabel();
        combTipo = new javax.swing.JComboBox<>();
        jLabelData = new javax.swing.JLabel();
        txtData = new javax.swing.JTextField();
        jLabelDataDica = new javax.swing.JLabel();
        btnSalvar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanelFundo.setBackground(new java.awt.Color(255, 255, 255));
        jPanelFundo.setPreferredSize(new java.awt.Dimension(440, 380));
        jPanelFundo.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 30, 20, 30));

        jLabelFormTitulo.setFont(new java.awt.Font("Tahoma", 1, 18));
        jLabelFormTitulo.setForeground(new java.awt.Color(27, 94, 32));
        jLabelFormTitulo.setText("Registrar Movimentação");

        jSeparator1.setForeground(new java.awt.Color(27, 94, 32));

        jLabelDescricao.setFont(new java.awt.Font("Tahoma", 0, 13));
        jLabelDescricao.setText("Descrição:");

        txtDescricao.setFont(new java.awt.Font("Tahoma", 0, 13));
        txtDescricao.setPreferredSize(new java.awt.Dimension(64, 32));
        txtDescricao.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(new java.awt.Color(200, 200, 200), 1),
            javax.swing.BorderFactory.createEmptyBorder(4, 8, 4, 8)));

        jLabelValor.setFont(new java.awt.Font("Tahoma", 0, 13));
        jLabelValor.setText("Valor (R$):");

        txtValor.setFont(new java.awt.Font("Tahoma", 0, 13));
        txtValor.setPreferredSize(new java.awt.Dimension(64, 32));
        txtValor.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(new java.awt.Color(200, 200, 200), 1),
            javax.swing.BorderFactory.createEmptyBorder(4, 8, 4, 8)));

        jLabelTipo.setFont(new java.awt.Font("Tahoma", 0, 13));
        jLabelTipo.setText("Tipo:");

        combTipo.setFont(new java.awt.Font("Tahoma", 0, 13));
        combTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Venda", "Despesa" }));
        combTipo.setPreferredSize(new java.awt.Dimension(64, 32));

        jLabelData.setFont(new java.awt.Font("Tahoma", 0, 13));
        jLabelData.setText("Data:");

        txtData.setFont(new java.awt.Font("Tahoma", 0, 13));
        txtData.setPreferredSize(new java.awt.Dimension(64, 32));
        txtData.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(new java.awt.Color(200, 200, 200), 1),
            javax.swing.BorderFactory.createEmptyBorder(4, 8, 4, 8)));

        jLabelDataDica.setFont(new java.awt.Font("Tahoma", 0, 11));
        jLabelDataDica.setForeground(new java.awt.Color(150, 150, 150));
        jLabelDataDica.setText("Formato: dd/MM/yyyy");

        btnSalvar.setBackground(new java.awt.Color(27, 94, 32));
        btnSalvar.setFont(new java.awt.Font("Tahoma", 1, 14));
        btnSalvar.setForeground(new java.awt.Color(255, 255, 255));
        btnSalvar.setText("Salvar");
        btnSalvar.setFocusPainted(false);
        btnSalvar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        btnCancelar.setBackground(new java.awt.Color(200, 200, 200));
        btnCancelar.setFont(new java.awt.Font("Tahoma", 1, 14));
        btnCancelar.setForeground(new java.awt.Color(60, 60, 60));
        btnCancelar.setText("Cancelar");
        btnCancelar.setFocusPainted(false);
        btnCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelFundoLayout = new javax.swing.GroupLayout(jPanelFundo);
        jPanelFundo.setLayout(jPanelFundoLayout);
        jPanelFundoLayout.setHorizontalGroup(
            jPanelFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelFormTitulo)
            .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
            .addComponent(jLabelDescricao)
            .addComponent(txtDescricao, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
            .addGroup(jPanelFundoLayout.createSequentialGroup()
                .addGroup(jPanelFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabelValor)
                    .addComponent(txtValor, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE))
                .addGap(20, 20, 20)
                .addGroup(jPanelFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabelTipo)
                    .addComponent(combTipo, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)))
            .addComponent(jLabelData)
            .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jLabelDataDica)
            .addGroup(jPanelFundoLayout.createSequentialGroup()
                .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanelFundoLayout.setVerticalGroup(
            jPanelFundoLayout.createSequentialGroup()
            .addComponent(jLabelFormTitulo)
            .addGap(8)
            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(12)
            .addComponent(jLabelDescricao)
            .addGap(4)
            .addComponent(txtDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(12)
            .addGroup(jPanelFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelFundoLayout.createSequentialGroup()
                    .addComponent(jLabelValor)
                    .addGap(4)
                    .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanelFundoLayout.createSequentialGroup()
                    .addComponent(jLabelTipo)
                    .addGap(4)
                    .addComponent(combTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGap(12)
            .addComponent(jLabelData)
            .addGap(4)
            .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(2)
            .addComponent(jLabelDataDica)
            .addGap(20)
            .addGroup(jPanelFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelFundo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelFundo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(dashboard);
    }// </editor-fold>//GEN-END:initComponents

    // ===== AÇÃO: Salvar =====
    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {
        String descricao = txtDescricao.getText().trim();
        String valorStr  = txtValor.getText().trim().replace(",", ".");
        String tipo      = (String) combTipo.getSelectedItem();
        String dataStr   = txtData.getText().trim();

        // Validações
        if (descricao.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha a Descrição!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        BigDecimal valor;
        try {
            valor = new BigDecimal(valorStr);
            if (valor.compareTo(BigDecimal.ZERO) <= 0) {
                JOptionPane.showMessageDialog(this, "O valor deve ser maior que zero!", "Atenção", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Valor inválido! Use ponto ou vírgula (ex: 25.90)", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Date data;
        try {
            LocalDate ld = LocalDate.parse(dataStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            data = Date.valueOf(ld);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Data inválida! Use o formato dd/MM/yyyy", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        MovimentacaoBean m = new MovimentacaoBean();
        m.setDescricao(descricao);
        m.setValor(valor);
        m.setTipo(tipo);
        m.setData(data);
        m.setIdUsuario(usuarioLogado.getId());

        boolean ok;
        if (idEditando == -1) {
            ok = dao.criar(m);
            if (ok) JOptionPane.showMessageDialog(this, tipo + " registrada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } else {
            m.setId(idEditando);
            ok = dao.atualizar(m);
            if (ok) JOptionPane.showMessageDialog(this, "Movimentação atualizada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }

        if (ok) {
            dashboard.atualizarTudo();
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao salvar. Verifique a conexão com o banco.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ===== AÇÃO: Cancelar =====
    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JComboBox<String> combTipo;
    private javax.swing.JLabel jLabelData;
    private javax.swing.JLabel jLabelDataDica;
    private javax.swing.JLabel jLabelDescricao;
    private javax.swing.JLabel jLabelFormTitulo;
    private javax.swing.JLabel jLabelTipo;
    private javax.swing.JLabel jLabelValor;
    private javax.swing.JPanel jPanelFundo;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField txtData;
    private javax.swing.JTextField txtDescricao;
    private javax.swing.JTextField txtValor;
    // End of variables declaration//GEN-END:variables
}
