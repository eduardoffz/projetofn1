package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
.
 */
public class Conexao {
    private static Connection conn = null;

    private static final String URL  = "jdbc:mysql://localhost:3337/gestor_caixa";
    private static final String USER = "root";
    private static final String SENHA = "";

    private Conexao() { }

    public static synchronized Connection conectar() {
        try {
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(URL, USER, SENHA);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                "Erro ao conectar ao banco de dados!\n" + e.getMessage(),
                "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return conn;
    }
}
