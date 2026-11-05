package br.unifil.projmongo.route;

import br.unifil.projmongo.controller.LocalController;
import br.unifil.projmongo.entity.Local;
import org.bson.Document;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/** Rotas de Local */
@RestController
@RequestMapping("/locais")
public class LocalRoute {

    private final LocalController controller;

    public LocalRoute(LocalController controller) {
        this.controller = controller;
    }

    @GetMapping
    public ResponseEntity<List<Document>> listar() {
        return ResponseEntity.ok(controller.listarLocais());
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> criar(@RequestBody Local local) {
        controller.criarLocal(local);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("mensagem", "Local inserido com sucesso!"));
    }

    @PutMapping("/{pid}")
    public ResponseEntity<Map<String, String>> atualizar(@PathVariable int pid, @RequestBody Local local) {
        if (controller.atualizarLocal(pid, local) == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", "Local não encontrado!"));
        }
        return ResponseEntity.ok(Map.of("mensagem", "Local atualizado com sucesso!"));
    }

    @DeleteMapping("/{pid}")
    public ResponseEntity<Map<String, String>> deletar(@PathVariable int pid) {
        if (controller.possuiAgendamentos(pid)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("erro", "Não é possível excluir: existem agendamentos vinculados."));
        }
        if (controller.deletarLocal(pid) == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", "Local não encontrado!"));
        }
        return ResponseEntity.ok(Map.of("mensagem", "Local deletado com sucesso!"));
    }
}
