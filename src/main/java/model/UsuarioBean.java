package model;


 
public class UsuarioBean {
    private int id;
    private String nome;
    private String usuario;
    private String senha;
    private boolean admin;

    public UsuarioBean() { }

    public UsuarioBean(int id, String nome, String usuario, String senha, boolean admin) {
        this.id      = id;
        this.nome    = nome;
        this.usuario = usuario;
        this.senha   = senha;
        this.admin   = admin;
    }

    public int getId()              { return id; }
    public void setId(int id)       { this.id = id; }

    public String getNome()         { return nome; }
    public void setNome(String n)   { this.nome = n; }

    public String getUsuario()      { return usuario; }
    public void setUsuario(String u){ this.usuario = u; }

    public String getSenha()        { return senha; }
    public void setSenha(String s)  { this.senha = s; }

    public boolean isAdmin()        { return admin; }
    public void setAdmin(boolean a) { this.admin = a; }
}
