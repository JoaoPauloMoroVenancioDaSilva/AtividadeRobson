package br.unifil.projmongo.controller;

import br.unifil.projmongo.service.AgendamentoService;
import br.unifil.projmongo.entity.Agendamento;
import org.bson.Document;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/agendamentos")
public class AgendamentoController {

    private final AgendamentoService controller;

    public AgendamentoController(AgendamentoService controller) {
        this.controller = controller;
    }

    @GetMapping
    public ResponseEntity<List<Document>> listar() {
        return ResponseEntity.ok(controller.listarAgendamentos());
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> criar(@RequestBody Agendamento a) {
        String erro = controller.validar(a);
        if (erro != null) {
            return ResponseEntity.badRequest().body(Map.of("erro", erro));
        }
        controller.criarAgendamento(a);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("mensagem", "Agendamento inserido com sucesso!"));
    }

    @PutMapping("/{pid}")
    public ResponseEntity<Map<String, String>> atualizar(@PathVariable int pid, @RequestBody Agendamento a) {
        String erro = controller.validar(a);
        if (erro != null) {
            return ResponseEntity.badRequest().body(Map.of("erro", erro));
        }
        if (controller.atualizarAgendamento(pid, a) == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", "Agendamento não encontrado!"));
        }
        return ResponseEntity.ok(Map.of("mensagem", "Agendamento atualizado com sucesso!"));
    }

    @DeleteMapping("/{pid}")
    public ResponseEntity<Map<String, String>> deletar(@PathVariable int pid) {
        if (controller.deletarAgendamento(pid) == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", "Agendamento não encontrado!"));
        }
        return ResponseEntity.ok(Map.of("mensagem", "Agendamento deletado com sucesso!"));
    }
}
