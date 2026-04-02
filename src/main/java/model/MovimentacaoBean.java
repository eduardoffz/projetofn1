package model;

import java.math.BigDecimal;
import java.sql.Date;


public class MovimentacaoBean {
    private int id;
    private String descricao;
    private String tipo;       // "Venda" ou "Despesa"
    private BigDecimal valor;
    private Date data;
    private int idUsuario;

    public MovimentacaoBean() { }

    public MovimentacaoBean(int id, String descricao, String tipo,
                             BigDecimal valor, Date data, int idUsuario) {
        this.id         = id;
        this.descricao  = descricao;
        this.tipo       = tipo;
        this.valor      = valor;
        this.data       = data;
        this.idUsuario  = idUsuario;
    }

    public int getId()                   { return id; }
    public void setId(int id)            { this.id = id; }

    public String getDescricao()         { return descricao; }
    public void setDescricao(String d)   { this.descricao = d; }

    public String getTipo()              { return tipo; }
    public void setTipo(String t)        { this.tipo = t; }

    public BigDecimal getValor()         { return valor; }
    public void setValor(BigDecimal v)   { this.valor = v; }

    public Date getData()                { return data; }
    public void setData(Date d)          { this.data = d; }

    public int getIdUsuario()            { return idUsuario; }
    public void setIdUsuario(int u)      { this.idUsuario = u; }
}
