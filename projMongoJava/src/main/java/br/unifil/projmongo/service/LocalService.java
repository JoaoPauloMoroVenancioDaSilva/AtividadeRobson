package br.unifil.projmongo.service;

import br.unifil.projmongo.entity.Local;
import br.unifil.projmongo.repository.AgendamentoRepository;
import br.unifil.projmongo.repository.LocalRepository;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocalService {

    private final LocalRepository model;
    private final AgendamentoRepository agendamentoRepository;

    public LocalService(LocalRepository model, AgendamentoRepository agendamentoRepository) {
        this.model = model;
        this.agendamentoRepository = agendamentoRepository;
    }

    public List<Document> listarLocais() {
        return model.listarTodos();
    }

    public void criarLocal(Local local) {
        model.criarLocal(local);
    }

    public long atualizarLocal(int pid, Local dados) {
        dados.setId(pid);
        return model.atualizarLocal(dados);
    }

    public long deletarLocal(int pid) {
        return model.deletarLocal(pid);
    }

    public boolean possuiAgendamentos(int pid) {
        return agendamentoRepository.contarPor("localId", pid) > 0;
    }
}
