package view;

import java.awt.*;
import java.math.BigDecimal;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import model.MovimentacaoBean;
import model.MovimentacaoDAO;
import model.UsuarioBean;

/**
 * Tela principal do GestorCaixa.
 *
 * Responsabilidades:
 *  - Exibir cards de resumo: Saldo, Entradas (Vendas) e Saídas (Despesas).
 *  - Listar todas as movimentações do usuário em uma tabela.
 *  - Oferecer ações: nova Venda, nova Despesa, Editar, Excluir e Relatório.
 *  - Atualizar tudo automaticamente após qualquer operação.
 *
 * Navegação:
 *  - Sair    → volta para a tela de Login e fecha o Dashboard.
 *  - Venda / Despesa → abre FormularioMovimentacao em modo criação.
 *  - Editar  → abre FormularioMovimentacao em modo edição (requer seleção na tabela).
 *  - Excluir → confirma e remove o registro selecionado.
 *  - Relatório → abre a tela Relatorio filtrada por período.
 */
public class Dashboard extends JFrame {

    // ---------------------------------------------------------------
    // Estado da tela
    // ---------------------------------------------------------------

    /** Modelo da tabela — gerencia as linhas exibidas em tempo real. */
    private DefaultTableModel tableModel;

    /** DAO de movimentações — faz todas as operações no banco. */
    private MovimentacaoDAO dao = new MovimentacaoDAO();

    /** Usuário autenticado — mantido para filtrar dados e passar às sub-telas. */
    private UsuarioBean usuario;

    // ---------------------------------------------------------------
    // Labels dos cards de resumo (atualizados por atualizarTudo())
    // ---------------------------------------------------------------

    /** Exibe o saldo total (vendas - despesas). Cor muda conforme positivo/negativo. */
    private JLabel lblSaldo    = new JLabel("R$ 0,00");

    /** Exibe o total de entradas (Vendas). */
    private JLabel lblVendas   = new JLabel("R$ 0,00");

    /** Exibe o total de saídas (Despesas). */
    private JLabel lblDespesas = new JLabel("R$ 0,00");

    /** Tabela de movimentações — coluna ID fica oculta (usada internamente). */
    private JTable tabela;

    // ---------------------------------------------------------------
    // Construtor
    // ---------------------------------------------------------------

    /**
     * Inicializa o Dashboard para o usuário informado.
     *
     * @param usuario Usuário autenticado, recebido da tela de Login.
     */
    public Dashboard(UsuarioBean usuario) {
        this.usuario = usuario;
        build();          // monta a interface
        atualizarTudo();  // carrega dados do banco na primeira exibição
    }

    // ---------------------------------------------------------------
    // Construção da interface
    // ---------------------------------------------------------------

    /**
     * Monta toda a estrutura visual da janela:
     *  - Topo: título e botão Sair.
     *  - Cards: Saldo, Entradas, Saídas.
     *  - Tabela: lista de movimentações.
     *  - Rodapé: botões de ação.
     */
    private void build() {
        setTitle("GestorCaixa");
        setSize(900, 580);
        setLocationRelativeTo(null);               // centraliza na tela
        setDefaultCloseOperation(EXIT_ON_CLOSE);   // encerra o programa ao fechar
        setLayout(new BorderLayout());

        // ── TOPO ─────────────────────────────────────────────────────
        JPanel topo = new JPanel(new BorderLayout());
        topo.setBackground(new Color(27, 94, 32)); // verde escuro
        topo.setBorder(new EmptyBorder(8, 16, 8, 16));

        JLabel titulo = new JLabel("GestorCaixa");
        titulo.setFont(new Font("Tahoma", Font.BOLD, 18));
        titulo.setForeground(Color.WHITE);

        // Saudação com o nome do usuário logado.
        JLabel ola = new JLabel("Ola, " + usuario.getNome());
        ola.setFont(new Font("Tahoma", Font.PLAIN, 12));
        ola.setForeground(new Color(180, 220, 180));

        // Botão Sair: reabre o Login e fecha esta janela.
        JButton btnSair = btn("Sair", new Color(180, 30, 30));
        btnSair.setPreferredSize(new Dimension(70, 30));
        btnSair.addActionListener(e -> {
            new Login().setVisible(true);
            dispose(); // fecha o Dashboard sem encerrar o processo
        });

        JPanel topoDir = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        topoDir.setOpaque(false);
        topoDir.add(ola);
        topoDir.add(btnSair);

        topo.add(titulo, BorderLayout.WEST);
        topo.add(topoDir, BorderLayout.EAST);

        // ── CARDS de resumo ──────────────────────────────────────────
        JPanel cards = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 12));
        cards.setBackground(new Color(248, 248, 248));
        cards.setBorder(new EmptyBorder(0, 8, 0, 0));
        cards.add(card("Saldo",    lblSaldo,    new Color(27, 94, 32)));   // verde escuro
        cards.add(card("Entradas", lblVendas,   new Color(46, 125, 50)));  // verde médio
        cards.add(card("Saidas",   lblDespesas, new Color(198, 40, 40)));  // vermelho

        // ── TABELA de movimentações ───────────────────────────────────
        // Colunas: ID (oculto), Data, Descrição, Tipo, Valor.
        tableModel = new DefaultTableModel(
            new Object[]{"ID", "Data", "Descricao", "Tipo", "Valor"}, 0) {
            // Impede edição direta das células pelo usuário.
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabela = new JTable(tableModel);
        tabela.setFont(new Font("Tahoma", Font.PLAIN, 13));
        tabela.setRowHeight(26);
        tabela.setGridColor(new Color(235, 235, 235));
        tabela.setSelectionBackground(new Color(200, 230, 200));

        tabela.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 13));
        tabela.getTableHeader().setBackground(new Color(27, 94, 32));
        tabela.getTableHeader().setForeground(Color.WHITE);
        tabela.getTableHeader().setReorderingAllowed(false); // impede arrastar colunas

        // Oculta a coluna ID (índice 0): usada internamente para editar/excluir.
        tabela.getColumnModel().getColumn(0).setMinWidth(0);
        tabela.getColumnModel().getColumn(0).setMaxWidth(0);

        // Centraliza as colunas Tipo (3) e Valor (4).
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(JLabel.CENTER);
        tabela.getColumnModel().getColumn(3).setCellRenderer(center);
        tabela.getColumnModel().getColumn(4).setCellRenderer(center);

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

        // ── BOTÕES do rodapé ─────────────────────────────────────────
        JButton btnVenda   = btn("+ Venda",   new Color(27, 94, 32));
        JButton btnDespesa = btn("+ Despesa", new Color(198, 40, 40));
        JButton btnEditar  = btn("Editar",    new Color(25, 118, 210));
        JButton btnExcluir = btn("Excluir",   new Color(100, 50, 50));
        JButton btnRelat   = btn("Relatorio", new Color(200, 100, 0));

        // Abre formulário de nova Venda (id=-1 indica criação).
        btnVenda.addActionListener(e ->
            new FormularioMovimentacao(this, usuario, "Venda", -1).setVisible(true));

        // Abre formulário de nova Despesa (id=-1 indica criação).
        btnDespesa.addActionListener(e ->
            new FormularioMovimentacao(this, usuario, "Despesa", -1).setVisible(true));

        // Editar: exige linha selecionada; busca a movimentação pelo ID oculto.
        btnEditar.addActionListener(e -> {
            int row = tabela.getSelectedRow();
            if (row < 0) { aviso("Selecione uma movimentacao para editar."); return; }
            int id = (int) tableModel.getValueAt(row, 0); // ID da coluna oculta
            MovimentacaoBean m = dao.obterPorId(id);
            new FormularioMovimentacao(this, usuario, m.getTipo(), id).setVisible(true);
        });

        // Excluir: exige seleção e confirmação antes de deletar.
        btnExcluir.addActionListener(e -> {
            int row = tabela.getSelectedRow();
            if (row < 0) { aviso("Selecione uma movimentacao para excluir."); return; }
            if (JOptionPane.showConfirmDialog(this, "Excluir este registro?",
                    "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                int id = (int) tableModel.getValueAt(row, 0);
                if (dao.deletar(id)) atualizarTudo(); // recarrega após exclusão
                else JOptionPane.showMessageDialog(this, "Erro ao excluir.");
            }
        });

        // Abre a tela de Relatório passando o usuário atual.
        btnRelat.addActionListener(e -> new Relatorio(usuario).setVisible(true));

        // Rodapé com todos os botões alinhados à esquerda.
        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        rodape.setBackground(Color.WHITE);
        rodape.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(220, 220, 220)));
        for (JButton b : new JButton[]{btnVenda, btnDespesa, btnEditar, btnExcluir, btnRelat})
            rodape.add(b);

        // ── MONTAGEM final ────────────────────────────────────────────
        JPanel centro = new JPanel(new BorderLayout());
        centro.setBackground(Color.WHITE);
        centro.setBorder(new EmptyBorder(0, 14, 0, 14));
        centro.add(scroll,  BorderLayout.CENTER);
        centro.add(rodape,  BorderLayout.SOUTH);

        JPanel corpo = new JPanel(new BorderLayout());
        corpo.setBackground(Color.WHITE);
        corpo.add(cards,  BorderLayout.NORTH);
        corpo.add(centro, BorderLayout.CENTER);

        add(topo,  BorderLayout.NORTH);
        add(corpo, BorderLayout.CENTER);
    }

    // ---------------------------------------------------------------
    // Helpers de construção de componentes
    // ---------------------------------------------------------------

    /**
     * Cria um card de resumo com título e valor formatado.
     *
     * @param titulo   Texto do rótulo superior (ex: "Saldo").
     * @param lblValor Label cujo texto será atualizado dinamicamente.
     * @param cor      Cor usada na borda e no texto do valor.
     * @return Painel pronto para adicionar ao layout.
     */
    private JPanel card(String titulo, JLabel lblValor, Color cor) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(cor, 1),
            new EmptyBorder(8, 14, 8, 14)));
        p.setPreferredSize(new Dimension(240, 62));

        JLabel lTit = new JLabel(titulo.toUpperCase());
        lTit.setFont(new Font("Tahoma", Font.PLAIN, 10));
        lTit.setForeground(new Color(120, 120, 120));

        lblValor.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblValor.setForeground(cor);

        p.add(lTit);
        p.add(Box.createVerticalStrut(4));
        p.add(lblValor);
        return p;
    }

    /**
     * Cria um botão padronizado com cor de fundo e texto branco.
     *
     * @param texto Rótulo do botão.
     * @param cor   Cor de fundo.
     * @return JButton estilizado.
     */
    private JButton btn(String texto, Color cor) {
        JButton b = new JButton(texto);
        b.setBackground(cor);
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Tahoma", Font.PLAIN, 12));
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return b;
    }

    /**
     * Exibe uma caixa de aviso simples com tom de alerta.
     *
     * @param msg Mensagem a exibir.
     */
    private void aviso(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Atencao", JOptionPane.WARNING_MESSAGE);
    }

    // ---------------------------------------------------------------
    // Atualização dos dados
    // ---------------------------------------------------------------

    /**
     * Recarrega do banco todos os dados exibidos na tela:
     *  1. Recalcula saldo, total de vendas e total de despesas via DAO.
     *  2. Atualiza os cards de resumo (texto e cor do saldo).
     *  3. Recarrega a tabela com todas as movimentações do usuário.
     *
     * Chamado automaticamente ao abrir a janela e após cada operação
     * (criação, edição ou exclusão de movimentação).
     */
    public void atualizarTudo() {
        // Consultas agregadas ao banco (uma por indicador).
        BigDecimal saldo    = dao.calcularSaldo(usuario.getId());
        BigDecimal vendas   = dao.calcularTotalVendas(usuario.getId());
        BigDecimal despesas = dao.calcularTotalDespesas(usuario.getId());

        // Atualiza textos dos cards.
        lblSaldo.setText("R$ "    + String.format("%.2f", saldo));
        lblVendas.setText("R$ "   + String.format("%.2f", vendas));
        lblDespesas.setText("R$ " + String.format("%.2f", despesas));

        // Saldo em verde se positivo, vermelho se negativo.
        lblSaldo.setForeground(saldo.compareTo(BigDecimal.ZERO) >= 0
            ? new Color(27, 94, 32) : new Color(198, 40, 40));

        // Recarrega a tabela: limpa e preenche com dados frescos do banco.
        tableModel.setRowCount(0);
        for (MovimentacaoBean m : dao.listar(usuario.getId())) {
            tableModel.addRow(new Object[]{
                m.getId(),        // coluna oculta — usada para editar/excluir
                m.getData(),
                m.getDescricao(),
                m.getTipo(),
                "R$ " + String.format("%.2f", m.getValor())
            });
        }
    }
}
