package br.unifil.projmongo.service;

import br.unifil.projmongo.entity.Pessoa;
import br.unifil.projmongo.repository.AgendamentoRepository;
import br.unifil.projmongo.repository.PessoaRepository;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PessoaService {

    private final PessoaRepository model;
    private final AgendamentoRepository agendamentoRepository;

    public PessoaService(PessoaRepository model, AgendamentoRepository agendamentoRepository) {
        this.model = model;
        this.agendamentoRepository = agendamentoRepository;
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

    public boolean possuiAgendamentos(int pid) {
        return agendamentoRepository.contarPor("pessoaId", pid) > 0;
    }
}