package br.unifil.projmongo.entity;

/**
 * Entidade de relacionamento: uma Pessoa leva um Pet a um Local em uma data.
 * Guarda apenas os ids (referências) das outras entidades.
 */
public class Agendamento {
    private Integer id;
    private Integer pessoaId;
    private Integer petId;
    private Integer localId;
    private String data;       // formato YYYY-MM-DDTHH:MM (input datetime-local)
    private String descricao;  // ex.: "Banho e tosa", "Consulta", "Passeio"

    public Agendamento() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getPessoaId() { return pessoaId; }
    public void setPessoaId(Integer pessoaId) { this.pessoaId = pessoaId; }
    public Integer getPetId() { return petId; }
    public void setPetId(Integer petId) { this.petId = petId; }
    public Integer getLocalId() { return localId; }
    public void setLocalId(Integer localId) { this.localId = localId; }
    public String getData() { return data; }
    public void setData(String data) { this.data = data; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
}
