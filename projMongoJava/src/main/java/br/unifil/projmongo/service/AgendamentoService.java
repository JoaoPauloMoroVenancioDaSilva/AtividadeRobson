package br.unifil.projmongo.service;

import br.unifil.projmongo.entity.Agendamento;
import br.unifil.projmongo.repository.AgendamentoRepository;
import br.unifil.projmongo.repository.LocalRepository;
import br.unifil.projmongo.repository.PessoaRepository;
import br.unifil.projmongo.repository.PetRepository;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final PessoaRepository pessoaRepository;
    private final PetRepository petRepository;
    private final LocalRepository localRepository;

    public AgendamentoService(AgendamentoRepository agendamentoRepository, PessoaRepository pessoaRepository,
                              PetRepository petRepository, LocalRepository localRepository) {
        this.agendamentoRepository = agendamentoRepository;
        this.pessoaRepository = pessoaRepository;
        this.petRepository = petRepository;
        this.localRepository = localRepository;
    }

    public List<Document> listarAgendamentos() {
        Map<Integer, String> pessoas = nomesPorId(pessoaRepository.listarTodos());
        Map<Integer, String> pets = nomesPorId(petRepository.listarTodos());
        Map<Integer, String> locais = nomesPorId(localRepository.listarTodos());

        List<Document> lista = agendamentoRepository.listarTodos();
        for (Document a : lista) {
            Integer pessoaId = toInt(a.get("pessoaId"));
            Integer petId = toInt(a.get("petId"));
            Integer localId = toInt(a.get("localId"));

            a.append("pessoaNome", pessoaId != null ? pessoas.getOrDefault(pessoaId, "(removido)") : "(removido)");
            a.append("petNome", petId != null ? pets.getOrDefault(petId, "(removido)") : "(removido)");
            a.append("localNome", localId != null ? locais.getOrDefault(localId, "(removido)") : "(removido)");
        }
        return lista;
    }

    private Integer toInt(Object val) {
        return (val instanceof Number num) ? num.intValue() : null;
    }

    private Map<Integer, String> nomesPorId(List<Document> docs) {
        Map<Integer, String> mapa = new HashMap<>();
        for (Document d : docs) {
            Object idObj = d.get("id");
            if (idObj instanceof Number num) {
                mapa.put(num.intValue(), d.getString("nome"));
            }
        }
        return mapa;
    }
    public String validar(Agendamento a) {
        if (a == null) return "Dados do agendamento não fornecidos!";
        if (a.getPessoaId() == null || !pessoaRepository.existe(a.getPessoaId())) return "Pessoa não encontrada!";
        if (a.getPetId() == null || !petRepository.existe(a.getPetId())) return "Pet não encontrado!";
        if (a.getLocalId() == null || !localRepository.existe(a.getLocalId())) return "Local não encontrado!";
        if (a.getData() == null || a.getData().isBlank()) return "Informe a data do agendamento!";
        return null;
    }

    public void criarAgendamento(Agendamento a) {
        agendamentoRepository.criarAgendamento(a);
    }

    public long atualizarAgendamento(int pid, Agendamento dados) {
        dados.setId(pid);
        return agendamentoRepository.atualizarAgendamento(dados);
    }

    public long deletarAgendamento(int pid) {
        return agendamentoRepository.deletarAgendamento(pid);
    }
}
