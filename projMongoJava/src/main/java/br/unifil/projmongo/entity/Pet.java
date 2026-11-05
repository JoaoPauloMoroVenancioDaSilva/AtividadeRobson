package br.unifil.projmongo.entity;

/** Entidade que representa um Pet. */
public class Pet {
    private Integer id;
    private String nome;
    private String fone;
    private String aniversario; // formato YYYY-MM-DD (igual ao front-end)

    public Pet() {}

    public Pet(Integer id, String nome, String fone, String aniversario) {
        this.id = id;
        this.nome = nome;
        this.fone = fone;
        this.aniversario = aniversario;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getFone() { return fone; }
    public void setFone(String fone) { this.fone = fone; }
    public String getAniversario() { return aniversario; }
    public void setAniversario(String aniversario) { this.aniversario = aniversario; }
}
