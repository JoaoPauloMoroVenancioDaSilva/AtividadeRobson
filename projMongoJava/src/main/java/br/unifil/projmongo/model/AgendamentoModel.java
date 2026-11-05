package br.unifil.projmongo.model;

import br.unifil.projmongo.entity.Agendamento;
import com.mongodb.client.MongoClient;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

/** Persistência de Agendamento no MongoDB (collection "Agendamentos"). */
@Repository
public class AgendamentoModel extends BaseModel {

    public AgendamentoModel(MongoClient client, @Value("${app.mongodb.database}") String dbName) {
        super(client, dbName, "Agendamentos");
    }

    private Document campos(Agendamento a) {
        return new Document("pessoaId", a.getPessoaId())
                .append("petId", a.getPetId())
                .append("localId", a.getLocalId())
                .append("data", a.getData())
                .append("descricao", a.getDescricao());
    }

    public void criarAgendamento(Agendamento a) {
        Document doc = new Document("id", nextVal());
        doc.putAll(campos(a));
        collection.insertOne(doc);
    }

    public long atualizarAgendamento(Agendamento a) {
        return collection.updateOne(new Document("id", a.getId()), new Document("$set", campos(a)))
                .getMatchedCount();
    }

    public long deletarAgendamento(int pid) {
        return deletar(pid);
    }
}
