package model;

import conexao.Conexao;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class MovimentacaoDAO {

    public boolean criar(MovimentacaoBean mov) {
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO movimentacoes (descricao, tipo, valor, data, id_usuario) " +
                "VALUES (?, ?, ?, ?, ?)");
            stmt.setString(1, mov.getDescricao());
            stmt.setString(2, mov.getTipo());
            stmt.setBigDecimal(3, mov.getValor());
            stmt.setDate(4, mov.getData());
            stmt.setInt(5, mov.getIdUsuario());
            stmt.executeUpdate();
            stmt.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<MovimentacaoBean> listar(int idUsuario) {
        List<MovimentacaoBean> lista = new ArrayList<>();
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM movimentacoes WHERE id_usuario = ? ORDER BY data DESC, id DESC");
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                MovimentacaoBean m = new MovimentacaoBean();
                m.setId(rs.getInt("id"));
                m.setDescricao(rs.getString("descricao"));
                m.setTipo(rs.getString("tipo"));
                m.setValor(rs.getBigDecimal("valor"));
                m.setData(rs.getDate("data"));
                m.setIdUsuario(rs.getInt("id_usuario"));
                lista.add(m);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<MovimentacaoBean> listarPorPeriodo(int idUsuario, Date dataInicio, Date dataFim) {
        List<MovimentacaoBean> lista = new ArrayList<>();
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM movimentacoes WHERE id_usuario = ? " +
                "AND data BETWEEN ? AND ? ORDER BY data DESC");
            stmt.setInt(1, idUsuario);
            stmt.setDate(2, dataInicio);
            stmt.setDate(3, dataFim);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                MovimentacaoBean m = new MovimentacaoBean();
                m.setId(rs.getInt("id"));
                m.setDescricao(rs.getString("descricao"));
                m.setTipo(rs.getString("tipo"));
                m.setValor(rs.getBigDecimal("valor"));
                m.setData(rs.getDate("data"));
                m.setIdUsuario(rs.getInt("id_usuario"));
                lista.add(m);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public MovimentacaoBean obterPorId(int id) {
        MovimentacaoBean m = null;
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM movimentacoes WHERE id = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                m = new MovimentacaoBean();
                m.setId(rs.getInt("id"));
                m.setDescricao(rs.getString("descricao"));
                m.setTipo(rs.getString("tipo"));
                m.setValor(rs.getBigDecimal("valor"));
                m.setData(rs.getDate("data"));
                m.setIdUsuario(rs.getInt("id_usuario"));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return m;
    }

    /** UPDATE - Atualiza uma movimentação existente */
    public boolean atualizar(MovimentacaoBean mov) {
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(
                "UPDATE movimentacoes SET descricao=?, tipo=?, valor=?, data=? WHERE id=?");
            stmt.setString(1, mov.getDescricao());
            stmt.setString(2, mov.getTipo());
            stmt.setBigDecimal(3, mov.getValor());
            stmt.setDate(4, mov.getData());
            stmt.setInt(5, mov.getId());
            stmt.executeUpdate();
            stmt.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** DELETE - Remove uma movimentação pelo ID */
    public boolean deletar(int id) {
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(
                "DELETE FROM movimentacoes WHERE id = ?");
            stmt.setInt(1, id);
            stmt.executeUpdate();
            stmt.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Retorna o saldo total (vendas - despesas) do usuário */
    public BigDecimal calcularSaldo(int idUsuario) {
        BigDecimal saldo = BigDecimal.ZERO;
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(
                "SELECT " +
                "  COALESCE(SUM(CASE WHEN tipo='Venda' THEN valor ELSE 0 END), 0) AS total_vendas, " +
                "  COALESCE(SUM(CASE WHEN tipo='Despesa' THEN valor ELSE 0 END), 0) AS total_despesas " +
                "FROM movimentacoes WHERE id_usuario = ?");
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                BigDecimal vendas   = rs.getBigDecimal("total_vendas");
                BigDecimal despesas = rs.getBigDecimal("total_despesas");
                saldo = vendas.subtract(despesas);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return saldo;
    }

    /** Retorna total de vendas do usuário */
    public BigDecimal calcularTotalVendas(int idUsuario) {
        BigDecimal total = BigDecimal.ZERO;
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(
                "SELECT COALESCE(SUM(valor), 0) AS total FROM movimentacoes " +
                "WHERE id_usuario = ? AND tipo = 'Venda'");
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) total = rs.getBigDecimal("total");
            rs.close();
            stmt.close();
        } catch (SQLException e) { e.printStackTrace(); }
        return total;
    }

    /** Retorna total de despesas do usuário */
    public BigDecimal calcularTotalDespesas(int idUsuario) {
        BigDecimal total = BigDecimal.ZERO;
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(
                "SELECT COALESCE(SUM(valor), 0) AS total FROM movimentacoes " +
                "WHERE id_usuario = ? AND tipo = 'Despesa'");
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) total = rs.getBigDecimal("total");
            rs.close();
            stmt.close();
        } catch (SQLException e) { e.printStackTrace(); }
        return total;
    }
}
