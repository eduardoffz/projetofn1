package view;

import javax.swing.*;
import model.UsuarioBean;
import model.UsuarioDAO;

public class Login extends javax.swing.JFrame {

    public Login() {
        initComponents();
        setTitle("GestorCaixa - Login");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelFundo     = new javax.swing.JPanel();
        jPanelLateral   = new javax.swing.JPanel();
        jLabelTitulo    = new javax.swing.JLabel();
        jLabelSubtitulo = new javax.swing.JLabel();
        jPanelForm      = new javax.swing.JPanel();
        jLabelBemVindo  = new javax.swing.JLabel();
        jLabelUsuario   = new javax.swing.JLabel();
        txtUsuario      = new javax.swing.JTextField();
        jLabelSenha     = new javax.swing.JLabel();
        txtSenha        = new javax.swing.JPasswordField();
        btnEntrar       = new javax.swing.JButton();
        jSeparator1     = new javax.swing.JSeparator();
        jLabelCadastro  = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanelFundo.setBackground(new java.awt.Color(255, 255, 255));
        jPanelFundo.setPreferredSize(new java.awt.Dimension(760, 440));

        // painel verde esquerdo
        jPanelLateral.setBackground(new java.awt.Color(27, 94, 32));

        jLabelTitulo.setFont(new java.awt.Font("Tahoma", 1, 26));
        jLabelTitulo.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTitulo.setText("GestorCaixa");

        jLabelSubtitulo.setFont(new java.awt.Font("Tahoma", 0, 13));
        jLabelSubtitulo.setForeground(new java.awt.Color(180, 220, 180));
        jLabelSubtitulo.setText("<html><center>Controle simples<br>para o seu comercio</center></html>");

        javax.swing.GroupLayout lateralLayout = new javax.swing.GroupLayout(jPanelLateral);
        jPanelLateral.setLayout(lateralLayout);
        lateralLayout.setHorizontalGroup(
            lateralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
            .addGroup(lateralLayout.createSequentialGroup()
                .addGap(30)
                .addGroup(lateralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabelTitulo)
                    .addComponent(jLabelSubtitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30))
        );
        lateralLayout.setVerticalGroup(
            lateralLayout.createSequentialGroup()
                .addGap(160)
                .addComponent(jLabelTitulo)
                .addGap(14)
                .addComponent(jLabelSubtitulo)
                .addContainerGap(160, Short.MAX_VALUE)
        );

        // painel formulario direito
        jPanelForm.setBackground(new java.awt.Color(255, 255, 255));

        jLabelBemVindo.setFont(new java.awt.Font("Tahoma", 1, 20));
        jLabelBemVindo.setForeground(new java.awt.Color(27, 94, 32));
        jLabelBemVindo.setText("Bem-vindo!");

        jLabelUsuario.setFont(new java.awt.Font("Tahoma", 0, 13));
        jLabelUsuario.setText("Usuario:");

        txtUsuario.setFont(new java.awt.Font("Tahoma", 0, 13));
        txtUsuario.setPreferredSize(new java.awt.Dimension(64, 30));
        txtUsuario.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(new java.awt.Color(27, 94, 32)),
            javax.swing.BorderFactory.createEmptyBorder(3, 7, 3, 7)));

        jLabelSenha.setFont(new java.awt.Font("Tahoma", 0, 13));
        jLabelSenha.setText("Senha:");

        txtSenha.setFont(new java.awt.Font("Tahoma", 0, 13));
        txtSenha.setPreferredSize(new java.awt.Dimension(64, 30));
        txtSenha.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(new java.awt.Color(27, 94, 32)),
            javax.swing.BorderFactory.createEmptyBorder(3, 7, 3, 7)));

        btnEntrar.setBackground(new java.awt.Color(27, 94, 32));
        btnEntrar.setFont(new java.awt.Font("Tahoma", 1, 14));
        btnEntrar.setForeground(new java.awt.Color(255, 255, 255));
        btnEntrar.setText("Entrar");
        btnEntrar.setFocusPainted(false);
        btnEntrar.setBorderPainted(false);
        btnEntrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEntrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEntrarActionPerformed(evt);
            }
        });

        jLabelCadastro.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabelCadastro.setForeground(new java.awt.Color(27, 94, 32));
        jLabelCadastro.setText("Nao tem conta? Cadastre-se");
        jLabelCadastro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabelCadastro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                new Cadastro3().setVisible(true);
                dispose();
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabelCadastro.setForeground(new java.awt.Color(76, 175, 80));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabelCadastro.setForeground(new java.awt.Color(27, 94, 32));
            }
        });

        javax.swing.GroupLayout formLayout = new javax.swing.GroupLayout(jPanelForm);
        jPanelForm.setLayout(formLayout);
        formLayout.setHorizontalGroup(
            formLayout.createSequentialGroup()
                .addGap(36)
                .addGroup(formLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabelBemVindo)
                    .addComponent(jLabelUsuario)
                    .addComponent(txtUsuario,     javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                    .addComponent(jLabelSenha)
                    .addComponent(txtSenha,       javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                    .addComponent(btnEntrar,       javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                    .addComponent(jSeparator1,     javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                    .addComponent(jLabelCadastro,  javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE))
                .addGap(36)
        );
        formLayout.setVerticalGroup(
            formLayout.createSequentialGroup()
                .addGap(90)
                .addComponent(jLabelBemVindo)
                .addGap(26)
                .addComponent(jLabelUsuario)
                .addGap(5)
                .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14)
                .addComponent(jLabelSenha)
                .addGap(5)
                .addComponent(txtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18)
                .addComponent(btnEntrar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8)
                .addComponent(jLabelCadastro)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        // layout fundo
        javax.swing.GroupLayout fundoLayout = new javax.swing.GroupLayout(jPanelFundo);
        jPanelFundo.setLayout(fundoLayout);
        fundoLayout.setHorizontalGroup(
            fundoLayout.createSequentialGroup()
                .addComponent(jPanelLateral, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jPanelForm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        fundoLayout.setVerticalGroup(
            fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanelLateral, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanelForm,    javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup().addComponent(jPanelFundo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup().addComponent(jPanelFundo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnEntrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEntrarActionPerformed
        String usuario = txtUsuario.getText().trim();
        String senha   = new String(txtSenha.getPassword()).trim();

        if (usuario.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha usuario e senha.", "Atencao", JOptionPane.WARNING_MESSAGE);
            return;
        }

        UsuarioDAO dao = new UsuarioDAO();
        UsuarioBean user = dao.logar(usuario, senha);

        if (user.getId() > 0) {
            new Dashboard(user).setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Usuario ou senha incorretos.", "Erro", JOptionPane.ERROR_MESSAGE);
            txtSenha.setText("");
        }
    }//GEN-LAST:event_btnEntrarActionPerformed

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(() -> new Login().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEntrar;
    private javax.swing.JLabel jLabelBemVindo;
    private javax.swing.JLabel jLabelCadastro;
    private javax.swing.JLabel jLabelSenha;
    private javax.swing.JLabel jLabelSubtitulo;
    private javax.swing.JLabel jLabelTitulo;
    private javax.swing.JLabel jLabelUsuario;
    private javax.swing.JPanel jPanelForm;
    private javax.swing.JPanel jPanelFundo;
    private javax.swing.JPanel jPanelLateral;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPasswordField txtSenha;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
