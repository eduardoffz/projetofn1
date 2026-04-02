package view;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.MovimentacaoBean;
import model.MovimentacaoDAO;
import model.UsuarioBean;

/**
 * Tela de Relatório por Período.
 *
 * Exibe as movimentações (vendas e despesas) de um usuário dentro
 * de um intervalo de datas escolhido. O relatório é gerado
 * automaticamente ao abrir a janela e sempre que o usuário alterar
 * qualquer um dos campos de data (sem necessidade de clicar em botão).
 *
 * Regras de negócio:
 *  - Período padrão: primeiro dia do mês atual até hoje.
 *  - Totais calculados diretamente na tela (sem consulta extra ao BD).
 *  - Saldo exibido em verde se >= 0, vermelho se negativo.
 */
public class Relatorio extends javax.swing.JFrame {

    // ---------------------------------------------------------------
    // Dependências e estado
    // ---------------------------------------------------------------

    /** Responsável por todas as consultas ao banco de movimentações. */
    private MovimentacaoDAO dao = new MovimentacaoDAO();

    /** Usuário autenticado — usado para filtrar os dados pelo id. */
    private UsuarioBean usuarioLogado;

    /** Modelo da tabela — permite adicionar/remover linhas programaticamente. */
    private DefaultTableModel tableModel;

    // ---------------------------------------------------------------
    // Construtor
    // ---------------------------------------------------------------

    /**
     * Cria a janela de relatório para o usuário informado.
     *
     * @param usuario Usuário logado (fornecido pelo Dashboard).
     */
    public Relatorio(UsuarioBean usuario) {
        this.usuarioLogado = usuario;

        // Monta todos os componentes visuais (método gerado pelo NetBeans).
        initComponents();

        // Título dinâmico com o nome do usuário logado.
        setTitle("Relatório - " + usuario.getNome());

        // Preenche os campos de data com o período padrão (mês corrente).
        LocalDate hoje      = LocalDate.now();
        LocalDate inicioMes = hoje.withDayOfMonth(1);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        txtDataInicio.setText(inicioMes.format(fmt));
        txtDataFim.setText(hoje.format(fmt));

        /*
         * Listener que dispara gerarRelatorio() toda vez que o usuário
         * termina de editar qualquer campo de data (FocusLost).
         * Isso elimina a necessidade do botão "Gerar".
         */
        txtDataInicio.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                gerarRelatorio(); // atualiza ao sair do campo início
            }
        });
        txtDataFim.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                gerarRelatorio(); // atualiza ao sair do campo fim
            }
        });

        // Gera o relatório imediatamente ao abrir a janela.
        gerarRelatorio();
    }

    // ---------------------------------------------------------------
    // Lógica principal
    // ---------------------------------------------------------------

    /**
     * Consulta o banco de dados e preenche a tabela e os totais.
     *
     * Fluxo:
     *  1. Lê e valida as datas digitadas pelo usuário.
     *  2. Busca as movimentações via DAO.
     *  3. Popula a tabela linha por linha.
     *  4. Acumula totais de vendas e despesas.
     *  5. Atualiza os labels de resumo (totais + saldo + cor).
     */
    private void gerarRelatorio() {
        String inicioStr = txtDataInicio.getText().trim();
        String fimStr    = txtDataFim.getText().trim();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // --- 1. Validação e conversão das datas ---
        Date dataInicio, dataFim;
        try {
            dataInicio = Date.valueOf(LocalDate.parse(inicioStr, fmt));
            dataFim    = Date.valueOf(LocalDate.parse(fimStr,    fmt));
        } catch (DateTimeParseException e) {
            // Só exibe aviso se os campos não estiverem vazios (evita erro na inicialização).
            if (!inicioStr.isEmpty() && !fimStr.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Data inválida! Use o formato: dd/MM/yyyy",
                    "Atenção", JOptionPane.WARNING_MESSAGE);
            }
            return;
        }

        // --- 2. Consulta ao banco ---
        List<MovimentacaoBean> lista =
            dao.listarPorPeriodo(usuarioLogado.getId(), dataInicio, dataFim);

        // --- 3. Popula a tabela ---
        tableModel = (DefaultTableModel) tabelaRelatorio.getModel();
        tableModel.setRowCount(0); // limpa linhas anteriores antes de recarregar

        // Acumuladores para os totais (calculados sem consulta extra ao BD).
        BigDecimal totalVendas   = BigDecimal.ZERO;
        BigDecimal totalDespesas = BigDecimal.ZERO;

        for (MovimentacaoBean m : lista) {
            // Cada linha: Data | Descrição | Tipo | Valor formatado
            Object[] linha = {
                m.getData(),
                m.getDescricao(),
                m.getTipo(),
                "R$ " + String.format("%.2f", m.getValor())
            };
            tableModel.addRow(linha);

            // --- 4. Acumula totais por tipo ---
            if ("Venda".equals(m.getTipo())) {
                totalVendas   = totalVendas.add(m.getValor());
            } else {
                totalDespesas = totalDespesas.add(m.getValor());
            }
        }

        // --- 5. Atualiza painel de resumo ---
        BigDecimal saldo = totalVendas.subtract(totalDespesas);

        jLabelTotalVendasValor.setText("R$ "   + String.format("%.2f", totalVendas));
        jLabelTotalDespesasValor.setText("R$ " + String.format("%.2f", totalDespesas));
        jLabelSaldoValor.setText("R$ "         + String.format("%.2f", saldo));

        // Cor do saldo: verde para positivo, vermelho para negativo.
        if (saldo.compareTo(BigDecimal.ZERO) >= 0) {
            jLabelSaldoValor.setForeground(new java.awt.Color(27, 94, 32));
        } else {
            jLabelSaldoValor.setForeground(new java.awt.Color(198, 40, 40));
        }
    }

    // ---------------------------------------------------------------
    // Componentes visuais (gerado pelo NetBeans Form Designer)
    // ---------------------------------------------------------------

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        // Painel raiz branco que envolve tudo com margem interna.
        jPanelFundo = new javax.swing.JPanel();

        // Título da tela.
        jLabelTitulo = new javax.swing.JLabel();

        // Linha separadora abaixo do título.
        jSeparator1 = new javax.swing.JSeparator();

        // Rótulos e campos do filtro de período.
        jLabelPeriodo  = new javax.swing.JLabel();
        jLabelDe       = new javax.swing.JLabel();
        txtDataInicio  = new javax.swing.JTextField();
        jLabelAte      = new javax.swing.JLabel();
        txtDataFim     = new javax.swing.JTextField();

        // Área de rolagem que contém a tabela de movimentações.
        jScrollPane1      = new javax.swing.JScrollPane();
        tabelaRelatorio   = new javax.swing.JTable();

        // Painel de resumo com totais e saldo.
        jPanelTotais             = new javax.swing.JPanel();
        jLabelTotalVendas        = new javax.swing.JLabel();
        jLabelTotalVendasValor   = new javax.swing.JLabel();
        jLabelTotalDespesas      = new javax.swing.JLabel();
        jLabelTotalDespesasValor = new javax.swing.JLabel();
        jSeparator2              = new javax.swing.JSeparator();
        jLabelSaldo              = new javax.swing.JLabel();
        jLabelSaldoValor         = new javax.swing.JLabel();

        // Botão para fechar a janela.
        btnFechar = new javax.swing.JButton();

        // Fecha apenas esta janela; o Dashboard continua aberto.
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        // --- Painel fundo ---
        jPanelFundo.setBackground(new java.awt.Color(255, 255, 255));
        jPanelFundo.setPreferredSize(new java.awt.Dimension(750, 530));
        jPanelFundo.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 25, 20, 25));

        // --- Título ---
        jLabelTitulo.setFont(new java.awt.Font("Tahoma", 1, 18));
        jLabelTitulo.setForeground(new java.awt.Color(27, 94, 32));
        jLabelTitulo.setText("📊 Relatório por Período");

        // Separador verde sob o título.
        jSeparator1.setForeground(new java.awt.Color(27, 94, 32));

        // --- Filtro de período ---
        jLabelPeriodo.setFont(new java.awt.Font("Tahoma", 1, 13));
        jLabelPeriodo.setText("Selecione o período:");

        jLabelDe.setFont(new java.awt.Font("Tahoma", 0, 13));
        jLabelDe.setText("De:");

        // Campo data início — FocusLost dispara gerarRelatorio() (ver construtor).
        txtDataInicio.setFont(new java.awt.Font("Tahoma", 0, 13));
        txtDataInicio.setPreferredSize(new java.awt.Dimension(64, 32));
        txtDataInicio.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(new java.awt.Color(200, 200, 200), 1),
            javax.swing.BorderFactory.createEmptyBorder(4, 8, 4, 8)));

        jLabelAte.setFont(new java.awt.Font("Tahoma", 0, 13));
        jLabelAte.setText("Até:");

        // Campo data fim — FocusLost dispara gerarRelatorio() (ver construtor).
        txtDataFim.setFont(new java.awt.Font("Tahoma", 0, 13));
        txtDataFim.setPreferredSize(new java.awt.Dimension(64, 32));
        txtDataFim.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(new java.awt.Color(200, 200, 200), 1),
            javax.swing.BorderFactory.createEmptyBorder(4, 8, 4, 8)));

        // --- Tabela de movimentações ---
        // Colunas: Data, Descrição, Tipo, Valor — todas não editáveis.
        tabelaRelatorio.setModel(new javax.swing.table.DefaultTableModel(
            new Object[][] { },
            new String[] { "Data", "Descrição", "Tipo", "Valor" }
        ) {
            boolean[] canEdit = new boolean[]{ false, false, false, false };
            public boolean isCellEditable(int r, int c) { return canEdit[c]; }
        });
        tabelaRelatorio.setRowHeight(26);
        tabelaRelatorio.setFont(new java.awt.Font("Tahoma", 0, 13));
        tabelaRelatorio.getTableHeader().setFont(new java.awt.Font("Tahoma", 1, 13));
        tabelaRelatorio.getTableHeader().setBackground(new java.awt.Color(27, 94, 32));
        tabelaRelatorio.getTableHeader().setForeground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setViewportView(tabelaRelatorio);

        // --- Painel de Totais ---
        // Fundo cinza claro com borda sutil para destacar o resumo financeiro.
        jPanelTotais.setBackground(new java.awt.Color(245, 245, 245));
        jPanelTotais.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(new java.awt.Color(220, 220, 220)),
            javax.swing.BorderFactory.createEmptyBorder(10, 15, 10, 15)));

        jLabelTotalVendas.setFont(new java.awt.Font("Tahoma", 0, 13));
        jLabelTotalVendas.setText("Total de Vendas:");

        // Valor de vendas em verde.
        jLabelTotalVendasValor.setFont(new java.awt.Font("Tahoma", 1, 13));
        jLabelTotalVendasValor.setForeground(new java.awt.Color(46, 125, 50));
        jLabelTotalVendasValor.setText("R$ 0,00");

        jLabelTotalDespesas.setFont(new java.awt.Font("Tahoma", 0, 13));
        jLabelTotalDespesas.setText("Total de Despesas:");

        // Valor de despesas em vermelho.
        jLabelTotalDespesasValor.setFont(new java.awt.Font("Tahoma", 1, 13));
        jLabelTotalDespesasValor.setForeground(new java.awt.Color(198, 40, 40));
        jLabelTotalDespesasValor.setText("R$ 0,00");

        // Separador entre despesas e saldo final.
        jSeparator2.setForeground(new java.awt.Color(180, 180, 180));

        jLabelSaldo.setFont(new java.awt.Font("Tahoma", 1, 14));
        jLabelSaldo.setText("Saldo do Período:");

        // Cor do saldo definida dinamicamente em gerarRelatorio().
        jLabelSaldoValor.setFont(new java.awt.Font("Tahoma", 1, 16));
        jLabelSaldoValor.setForeground(new java.awt.Color(27, 94, 32));
        jLabelSaldoValor.setText("R$ 0,00");

        // Layout do painel de totais: duas colunas (rótulo | valor).
        javax.swing.GroupLayout jPanelTotaisLayout = new javax.swing.GroupLayout(jPanelTotais);
        jPanelTotais.setLayout(jPanelTotaisLayout);
        jPanelTotaisLayout.setHorizontalGroup(
            jPanelTotaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTotaisLayout.createSequentialGroup()
                .addGroup(jPanelTotaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelTotalVendas)
                    .addComponent(jLabelTotalDespesas)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                    .addComponent(jLabelSaldo))
                .addGap(20, 20, 20)
                .addGroup(jPanelTotaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelTotalVendasValor)
                    .addComponent(jLabelTotalDespesasValor)
                    .addComponent(jLabelSaldoValor)))
        );
        jPanelTotaisLayout.setVerticalGroup(
            jPanelTotaisLayout.createSequentialGroup()
            .addGroup(jPanelTotaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabelTotalVendas)
                .addComponent(jLabelTotalVendasValor))
            .addGap(6)
            .addGroup(jPanelTotaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabelTotalDespesas)
                .addComponent(jLabelTotalDespesasValor))
            .addGap(6)
            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(4)
            .addGroup(jPanelTotaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabelSaldo)
                .addComponent(jLabelSaldoValor))
        );

        // --- Botão Fechar ---
        // Chama dispose() para encerrar apenas esta janela (não o Dashboard).
        btnFechar.setBackground(new java.awt.Color(100, 100, 100));
        btnFechar.setFont(new java.awt.Font("Tahoma", 1, 13));
        btnFechar.setForeground(new java.awt.Color(255, 255, 255));
        btnFechar.setText("Fechar");
        btnFechar.setFocusPainted(false);
        btnFechar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnFechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                // Fecha a janela de relatório sem encerrar o programa.
                dispose();
            }
        });

        // --- Layout principal do painel fundo ---
        // NOTA: btnGerar REMOVIDO. O relatório atualiza via FocusLost nos campos de data.
        // btnFechar alinhado à DIREITA.
        javax.swing.GroupLayout jPanelFundoLayout = new javax.swing.GroupLayout(jPanelFundo);
        jPanelFundo.setLayout(jPanelFundoLayout);
        jPanelFundoLayout.setHorizontalGroup(
            jPanelFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelTitulo)
            .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
            // Linha do filtro de período (sem btnGerar).
            .addGroup(jPanelFundoLayout.createSequentialGroup()
                .addComponent(jLabelPeriodo)
                .addGap(15)
                .addComponent(jLabelDe)
                .addGap(5)
                .addComponent(txtDataInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15)
                .addComponent(jLabelAte)
                .addGap(5)
                .addComponent(txtDataFim, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
            .addComponent(jPanelTotais, javax.swing.GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
            // btnFechar alinhado à direita.
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                jPanelFundoLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(btnFechar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanelFundoLayout.setVerticalGroup(
            jPanelFundoLayout.createSequentialGroup()
            .addComponent(jLabelTitulo)
            .addGap(6)
            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(12)
            // Linha de filtro sem o botão Gerar.
            .addGroup(jPanelFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                .addComponent(jLabelPeriodo)
                .addComponent(jLabelDe)
                .addComponent(txtDataInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabelAte)
                .addComponent(txtDataFim, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(12)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(12)
            .addComponent(jPanelTotais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(12)
            .addComponent(btnFechar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        // Encaixa o painel fundo no ContentPane da JFrame.
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
        setLocationRelativeTo(null); // centraliza na tela
    }// </editor-fold>//GEN-END:initComponents

    // ---------------------------------------------------------------
    // Declaração de variáveis — não modificar (referenciado pelo Designer)
    // ---------------------------------------------------------------
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFechar;
    // btnGerar REMOVIDO — relatório atualiza automaticamente via FocusLost.
    private javax.swing.JLabel jLabelAte;
    private javax.swing.JLabel jLabelDe;
    private javax.swing.JLabel jLabelPeriodo;
    private javax.swing.JLabel jLabelSaldo;
    private javax.swing.JLabel jLabelSaldoValor;
    private javax.swing.JLabel jLabelTitulo;
    private javax.swing.JLabel jLabelTotalDespesas;
    private javax.swing.JLabel jLabelTotalDespesasValor;
    private javax.swing.JLabel jLabelTotalVendas;
    private javax.swing.JLabel jLabelTotalVendasValor;
    private javax.swing.JPanel jPanelFundo;
    private javax.swing.JPanel jPanelTotais;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabelaRelatorio;
    private javax.swing.JTextField txtDataFim;
    private javax.swing.JTextField txtDataInicio;
    // End of variables declaration//GEN-END:variables
}
