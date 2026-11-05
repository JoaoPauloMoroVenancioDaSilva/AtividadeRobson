package br.unifil.projmongo.controller;

import br.unifil.projmongo.entity.Pessoa;
import br.unifil.projmongo.model.AgendamentoModel;
import br.unifil.projmongo.model.PessoaModel;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PessoaController {

    private final PessoaModel model;
    private final AgendamentoModel agendamentoModel;

    public PessoaController(PessoaModel model, AgendamentoModel agendamentoModel) {
        this.model = model;
        this.agendamentoModel = agendamentoModel;
    }

    public List<Document> listarPessoas() {
        return model.listarTodos();
    }

    public void criarPessoa(Pessoa pessoa) {
        model.criarPessoa(pessoa);
    }

    public long atualizarPessoa(int pid, Pessoa dados) {
        dados.setId(pid);
        return model.atualizarPessoa(dados);
    }

    public long deletarPessoa(int pid) {
        return model.deletarPessoa(pid);
    }

    /** True se existir algum agendamento usando este registro. */
    public boolean possuiAgendamentos(int pid) {
        return agendamentoModel.contarPor("pessoaId", pid) > 0;
    }
}
