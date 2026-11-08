package br.unifil.projmongo.entity;

public class Pet {
    private Integer id;
    private String nome;
    private String raca;
    private String idade;

    public Pet() {}

    public Pet(Integer id, String nome, String raca, String idade) {
        this.id = id;
        this.nome = nome;
        this.raca = raca;
        this.idade = idade;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getRaca() { return raca; }
    public void setRaca(String raca) { this.raca = raca; }

    public String getIdade() { return idade; }
    public void setIdade(String idade) { this.idade = idade; }
}
