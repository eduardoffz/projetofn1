package model;

import conexao.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO responsável pelas operações de banco de dados do Usuário.
 */
public class UsuarioDAO {

    /** Cadastra um novo usuário no banco. */
    public boolean cadastrar(UsuarioBean usuario) {
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO usuarios (nome, usuario, senha, admin) VALUES (?,?,?,?)");
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getUsuario());
            stmt.setString(3, usuario.getSenha());
            stmt.setBoolean(4, false);
            stmt.executeUpdate();
            stmt.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Verifica login e retorna o usuário logado (id > 0 = sucesso). */
    public UsuarioBean logar(String usuario, String senha) {
        UsuarioBean user = new UsuarioBean();
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM usuarios WHERE usuario = ? AND senha = ?");
            stmt.setString(1, usuario);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                user.setId(rs.getInt("id"));
                user.setNome(rs.getString("nome"));
                user.setUsuario(rs.getString("usuario"));
                user.setSenha(rs.getString("senha"));
                user.setAdmin(rs.getBoolean("admin"));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    /** Verifica se um nome de usuário já existe no banco. */
    public boolean usuarioExiste(String usuario) {
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(
                "SELECT id FROM usuarios WHERE usuario = ?");
            stmt.setString(1, usuario);
            ResultSet rs = stmt.executeQuery();
            boolean existe = rs.next();
            rs.close();
            stmt.close();
            return existe;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
