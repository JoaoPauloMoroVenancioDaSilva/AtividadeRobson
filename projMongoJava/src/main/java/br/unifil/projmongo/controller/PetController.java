package br.unifil.projmongo.controller;

import br.unifil.projmongo.service.PetService;
import br.unifil.projmongo.entity.Pet;
import org.bson.Document;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pets")
public class PetController {

    private final PetService controller;

    public PetController(PetService controller) {
        this.controller = controller;
    }

    @GetMapping
    public ResponseEntity<List<Document>> listar() {
        return ResponseEntity.ok(controller.listarPets());
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> criar(@RequestBody Pet pet) {
        controller.criarPet(pet);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("mensagem", "Pet inserido com sucesso!"));
    }

    @PutMapping("/{pid}")
    public ResponseEntity<Map<String, String>> atualizar(@PathVariable int pid, @RequestBody Pet pet) {
        if (controller.atualizarPet(pid, pet) == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", "Pet não encontrado!"));
        }
        return ResponseEntity.ok(Map.of("mensagem", "Pet atualizado com sucesso!"));
    }

    @DeleteMapping("/{pid}")
    public ResponseEntity<Map<String, String>> deletar(@PathVariable int pid) {
        if (controller.possuiAgendamentos(pid)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("erro", "Não é possível excluir: existem agendamentos vinculados."));
        }
        if (controller.deletarPet(pid) == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", "Pet não encontrado!"));
        }
        return ResponseEntity.ok(Map.of("mensagem", "Pet deletado com sucesso!"));
    }
}
