package br.unifil.projmongo.controller;

import br.unifil.projmongo.entity.Agendamento;
import br.unifil.projmongo.model.AgendamentoModel;
import br.unifil.projmongo.model.LocalModel;
import br.unifil.projmongo.model.PessoaModel;
import br.unifil.projmongo.model.PetModel;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AgendamentoController {

    private final AgendamentoModel model;
    private final PessoaModel pessoaModel;
    private final PetModel petModel;
    private final LocalModel localModel;

    public AgendamentoController(AgendamentoModel model, PessoaModel pessoaModel,
                                 PetModel petModel, LocalModel localModel) {
        this.model = model;
        this.pessoaModel = pessoaModel;
        this.petModel = petModel;
        this.localModel = localModel;
    }

    /** Lista os agendamentos já com os nomes de Pessoa, Pet e Local (um "join" manual). */
    public List<Document> listarAgendamentos() {
        Map<Object, String> pessoas = nomesPorId(pessoaModel.listarTodos());
        Map<Object, String> pets = nomesPorId(petModel.listarTodos());
        Map<Object, String> locais = nomesPorId(localModel.listarTodos());

        List<Document> lista = model.listarTodos();
        for (Document a : lista) {
            a.append("pessoaNome", pessoas.getOrDefault(a.get("pessoaId"), "(removido)"));
            a.append("petNome", pets.getOrDefault(a.get("petId"), "(removido)"));
            a.append("localNome", locais.getOrDefault(a.get("localId"), "(removido)"));
        }
        return lista;
    }

    private Map<Object, String> nomesPorId(List<Document> docs) {
        Map<Object, String> mapa = new HashMap<>();
        for (Document d : docs) {
            mapa.put(d.get("id"), d.getString("nome"));
        }
        return mapa;
    }

    /** Retorna uma mensagem de erro se alguma referência não existir, ou null se estiver tudo ok. */
    public String validar(Agendamento a) {
        if (!pessoaModel.existe(a.getPessoaId())) return "Pessoa não encontrada!";
        if (!petModel.existe(a.getPetId())) return "Pet não encontrado!";
        if (!localModel.existe(a.getLocalId())) return "Local não encontrado!";
        if (a.getData() == null || a.getData().isBlank()) return "Informe a data do agendamento!";
        return null;
    }

    public void criarAgendamento(Agendamento a) {
        model.criarAgendamento(a);
    }

    public long atualizarAgendamento(int pid, Agendamento dados) {
        dados.setId(pid);
        return model.atualizarAgendamento(dados);
    }

    public long deletarAgendamento(int pid) {
        return model.deletarAgendamento(pid);
    }
}
