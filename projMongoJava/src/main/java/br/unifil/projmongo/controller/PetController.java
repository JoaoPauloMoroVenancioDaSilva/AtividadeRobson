package br.unifil.projmongo.controller;

import br.unifil.projmongo.entity.Pet;
import br.unifil.projmongo.model.AgendamentoModel;
import br.unifil.projmongo.model.PetModel;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetController {

    private final PetModel model;
    private final AgendamentoModel agendamentoModel;

    public PetController(PetModel model, AgendamentoModel agendamentoModel) {
        this.model = model;
        this.agendamentoModel = agendamentoModel;
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

    /** True se existir algum agendamento usando este registro. */
    public boolean possuiAgendamentos(int pid) {
        return agendamentoModel.contarPor("petId", pid) > 0;
    }
}
