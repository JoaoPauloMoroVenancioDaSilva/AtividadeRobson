package br.unifil.projmongo.controller;

import br.unifil.projmongo.service.PessoaService;
import br.unifil.projmongo.entity.Pessoa;
import org.bson.Document;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    private final PessoaService controller;

    public PessoaController(PessoaService controller) {
        this.controller = controller;
    }

    @GetMapping
    public ResponseEntity<List<Document>> listar() {
        return ResponseEntity.ok(controller.listarPessoas());
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> criar(@RequestBody Pessoa pessoa) {
        controller.criarPessoa(pessoa);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("mensagem", "Pessoa inserida com sucesso!"));
    }

    @PutMapping("/{pid}")
    public ResponseEntity<Map<String, String>> atualizar(@PathVariable int pid, @RequestBody Pessoa pessoa) {
        if (controller.atualizarPessoa(pid, pessoa) == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", "Pessoa não encontrada!"));
        }
        return ResponseEntity.ok(Map.of("mensagem", "Pessoa atualizada com sucesso!"));
    }

    @DeleteMapping("/{pid}")
    public ResponseEntity<Map<String, String>> deletar(@PathVariable int pid) {
        if (controller.possuiAgendamentos(pid)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("erro", "Não é possível excluir: existem agendamentos vinculados."));
        }
        if (controller.deletarPessoa(pid) == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", "Pessoa não encontrada!"));
        }
        return ResponseEntity.ok(Map.of("mensagem", "Pessoa deletada com sucesso!"));
    }
}