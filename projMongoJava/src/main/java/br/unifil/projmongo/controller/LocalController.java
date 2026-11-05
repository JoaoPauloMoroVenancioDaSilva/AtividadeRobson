package br.unifil.projmongo.controller;

import br.unifil.projmongo.entity.Local;
import br.unifil.projmongo.model.AgendamentoModel;
import br.unifil.projmongo.model.LocalModel;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocalController {

    private final LocalModel model;
    private final AgendamentoModel agendamentoModel;

    public LocalController(LocalModel model, AgendamentoModel agendamentoModel) {
        this.model = model;
        this.agendamentoModel = agendamentoModel;
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

    /** True se existir algum agendamento usando este registro. */
    public boolean possuiAgendamentos(int pid) {
        return agendamentoModel.contarPor("localId", pid) > 0;
    }
}
