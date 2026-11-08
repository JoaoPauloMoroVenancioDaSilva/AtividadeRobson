package br.unifil.projmongo.service;

import br.unifil.projmongo.entity.Pet;
import br.unifil.projmongo.repository.AgendamentoRepository;
import br.unifil.projmongo.repository.PetRepository;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {

    private final PetRepository model;
    private final AgendamentoRepository agendamentoRepository;

    public PetService(PetRepository model, AgendamentoRepository agendamentoRepository) {
        this.model = model;
        this.agendamentoRepository = agendamentoRepository;
    }

    public List<Document> listarPets() {
        return model.listarTodos();
    }

    public void criarPet(Pet pet) {
        model.criarPet(pet);
    }

    public long atualizarPet(int pid, Pet dados) {
        dados.setId(pid);
        return model.atualizarPet(dados);
    }

    public long deletarPet(int pid) {
        return model.deletarPet(pid);
    }

    public boolean possuiAgendamentos(int pid) {
        return agendamentoRepository.contarPor("petId", pid) > 0;
    }
}
