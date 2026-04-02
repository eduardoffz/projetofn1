package view;

import javax.swing.JOptionPane;
import model.UsuarioBean;
import model.UsuarioDAO;

/**
 * Tela de Cadastro de Usuário - JFrame com GroupLayout (padrão NetBeans).
 */
public class Cadastro3 extends javax.swing.JFrame {

    public Cadastro3() {
        initComponents();
        setTitle("Gestor de Caixa - Cadastro");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelFundo = new javax.swing.JPanel();
        jPanelLateral = new javax.swing.JPanel();
        jLabelTitulo = new javax.swing.JLabel();
        jLabelSubtitulo = new javax.swing.JLabel();
        jPanelFormulario = new javax.swing.JPanel();
        jLabelCadastroTitulo = new javax.swing.JLabel();
        jLabelNome = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        jLabelUsuario = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        jLabelSenha = new javax.swing.JLabel();
        txtSenha = new javax.swing.JPasswordField();
        jLabelConfSenha = new javax.swing.JLabel();
        txtConfSenha = new javax.swing.JPasswordField();
        btnCadastrar = new javax.swing.JButton();
        jLabelVoltar = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        // === Painel Fundo ===
        jPanelFundo.setBackground(new java.awt.Color(255, 255, 255));
        jPanelFundo.setPreferredSize(new java.awt.Dimension(820, 520));

        // === Painel Lateral ===
        jPanelLateral.setBackground(new java.awt.Color(27, 94, 32));

        jLabelTitulo.setFont(new java.awt.Font("Tahoma", 1, 28));
        jLabelTitulo.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTitulo.setText("💰 GestorCaixa");

        jLabelSubtitulo.setFont(new java.awt.Font("Tahoma", 0, 13));
        jLabelSubtitulo.setForeground(new java.awt.Color(200, 230, 200));
        jLabelSubtitulo.setText("<html><center>Crie sua conta e comece<br>a controlar seu caixa!</center></html>");

        javax.swing.GroupLayout jPanelLateralLayout = new javax.swing.GroupLayout(jPanelLateral);
        jPanelLateral.setLayout(jPanelLateralLayout);
        jPanelLateralLayout.setHorizontalGroup(
            jPanelLateralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
            .addGroup(jPanelLateralLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanelLateralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabelTitulo)
                    .addComponent(jLabelSubtitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30))
        );
        jPanelLateralLayout.setVerticalGroup(
            jPanelLateralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLateralLayout.createSequentialGroup()
                .addGap(200, 200, 200)
                .addComponent(jLabelTitulo)
                .addGap(18, 18, 18)
                .addComponent(jLabelSubtitulo)
                .addContainerGap(200, Short.MAX_VALUE))
        );

        // === Painel Formulário ===
        jPanelFormulario.setBackground(new java.awt.Color(255, 255, 255));

        jLabelCadastroTitulo.setFont(new java.awt.Font("Tahoma", 1, 20));
        jLabelCadastroTitulo.setForeground(new java.awt.Color(27, 94, 32));
        jLabelCadastroTitulo.setText("Criar Conta");

        jLabelNome.setFont(new java.awt.Font("Tahoma", 0, 13));
        jLabelNome.setText("Nome completo:");

        txtNome.setFont(new java.awt.Font("Tahoma", 0, 13));
        txtNome.setPreferredSize(new java.awt.Dimension(64, 32));
        txtNome.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(new java.awt.Color(27, 94, 32), 1),
            javax.swing.BorderFactory.createEmptyBorder(4, 8, 4, 8)));

        jLabelUsuario.setFont(new java.awt.Font("Tahoma", 0, 13));
        jLabelUsuario.setText("Usuário (login):");

        txtUsuario.setFont(new java.awt.Font("Tahoma", 0, 13));
        txtUsuario.setPreferredSize(new java.awt.Dimension(64, 32));
        txtUsuario.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(new java.awt.Color(27, 94, 32), 1),
            javax.swing.BorderFactory.createEmptyBorder(4, 8, 4, 8)));

        jLabelSenha.setFont(new java.awt.Font("Tahoma", 0, 13));
        jLabelSenha.setText("Senha:");

        txtSenha.setFont(new java.awt.Font("Tahoma", 0, 13));
        txtSenha.setPreferredSize(new java.awt.Dimension(64, 32));
        txtSenha.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(new java.awt.Color(27, 94, 32), 1),
            javax.swing.BorderFactory.createEmptyBorder(4, 8, 4, 8)));

        jLabelConfSenha.setFont(new java.awt.Font("Tahoma", 0, 13));
        jLabelConfSenha.setText("Confirmar Senha:");

        txtConfSenha.setFont(new java.awt.Font("Tahoma", 0, 13));
        txtConfSenha.setPreferredSize(new java.awt.Dimension(64, 32));
        txtConfSenha.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(new java.awt.Color(27, 94, 32), 1),
            javax.swing.BorderFactory.createEmptyBorder(4, 8, 4, 8)));

        btnCadastrar.setBackground(new java.awt.Color(27, 94, 32));
        btnCadastrar.setFont(new java.awt.Font("Tahoma", 1, 15));
        btnCadastrar.setForeground(new java.awt.Color(255, 255, 255));
        btnCadastrar.setText("Cadastrar");
        btnCadastrar.setFocusPainted(false);
        btnCadastrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadastrarActionPerformed(evt);
            }
        });

        jLabelVoltar.setFont(new java.awt.Font("Tahoma", 1, 13));
        jLabelVoltar.setForeground(new java.awt.Color(27, 94, 32));
        jLabelVoltar.setText("← Voltar para Login");
        jLabelVoltar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabelVoltar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelVoltarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabelVoltar.setForeground(new java.awt.Color(76, 175, 80));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabelVoltar.setForeground(new java.awt.Color(27, 94, 32));
            }
        });

        javax.swing.GroupLayout jPanelFormularioLayout = new javax.swing.GroupLayout(jPanelFormulario);
        jPanelFormulario.setLayout(jPanelFormularioLayout);
        jPanelFormularioLayout.setHorizontalGroup(
            jPanelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelFormularioLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabelVoltar)
                    .addComponent(jLabelCadastroTitulo)
                    .addComponent(jLabelNome)
                    .addComponent(txtNome, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                    .addComponent(jLabelUsuario)
                    .addComponent(txtUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                    .addComponent(jLabelSenha)
                    .addComponent(txtSenha, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                    .addComponent(jLabelConfSenha)
                    .addComponent(txtConfSenha, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                    .addComponent(btnCadastrar, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE))
                .addGap(40, 40, 40))
        );
        jPanelFormularioLayout.setVerticalGroup(
            jPanelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelFormularioLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabelVoltar)
                .addGap(16, 16, 16)
                .addComponent(jLabelCadastroTitulo)
                .addGap(20, 20, 20)
                .addComponent(jLabelNome)
                .addGap(5, 5, 5)
                .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabelUsuario)
                .addGap(5, 5, 5)
                .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabelSenha)
                .addGap(5, 5, 5)
                .addComponent(txtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabelConfSenha)
                .addGap(5, 5, 5)
                .addComponent(txtConfSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        // === Layout principal ===
        javax.swing.GroupLayout jPanelFundoLayout = new javax.swing.GroupLayout(jPanelFundo);
        jPanelFundo.setLayout(jPanelFundoLayout);
        jPanelFundoLayout.setHorizontalGroup(
            jPanelFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelFundoLayout.createSequentialGroup()
                .addComponent(jPanelLateral, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jPanelFormulario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelFundoLayout.setVerticalGroup(
            jPanelFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelLateral, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanelFormulario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // ===== AÇÃO: Botão Cadastrar =====
    private void btnCadastrarActionPerformed(java.awt.event.ActionEvent evt) {
        String nome    = txtNome.getText().trim();
        String usuario = txtUsuario.getText().trim();
        String senha   = new String(txtSenha.getPassword()).trim();
        String conf    = new String(txtConfSenha.getPassword()).trim();

        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha o Nome!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (usuario.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha o Usuário!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha a Senha!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!senha.equals(conf)) {
            JOptionPane.showMessageDialog(this, "As senhas não coincidem!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        UsuarioDAO dao = new UsuarioDAO();
        if (dao.usuarioExiste(usuario)) {
            JOptionPane.showMessageDialog(this,
                "Este nome de usuário já está em uso. Escolha outro.", "Usuário já existe", JOptionPane.WARNING_MESSAGE);
            return;
        }

        UsuarioBean novoUsuario = new UsuarioBean();
        novoUsuario.setNome(nome);
        novoUsuario.setUsuario(usuario);
        novoUsuario.setSenha(senha);

        if (dao.cadastrar(novoUsuario)) {
            JOptionPane.showMessageDialog(this,
                "Conta criada com sucesso! Faça login para continuar.", "Sucesso!", JOptionPane.INFORMATION_MESSAGE);
            new Login().setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                "Erro ao cadastrar. Verifique a conexão com o banco.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ===== AÇÃO: Link Voltar =====
    private void jLabelVoltarMouseClicked(java.awt.event.MouseEvent evt) {
        new Login().setVisible(true);
        this.dispose();
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new Cadastro3().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCadastrar;
    private javax.swing.JLabel jLabelCadastroTitulo;
    private javax.swing.JLabel jLabelConfSenha;
    private javax.swing.JLabel jLabelNome;
    private javax.swing.JLabel jLabelSenha;
    private javax.swing.JLabel jLabelSubtitulo;
    private javax.swing.JLabel jLabelTitulo;
    private javax.swing.JLabel jLabelUsuario;
    private javax.swing.JLabel jLabelVoltar;
    private javax.swing.JPanel jPanelFormulario;
    private javax.swing.JPanel jPanelFundo;
    private javax.swing.JPanel jPanelLateral;
    private javax.swing.JPasswordField txtConfSenha;
    private javax.swing.JTextField txtNome;
    private javax.swing.JPasswordField txtSenha;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
