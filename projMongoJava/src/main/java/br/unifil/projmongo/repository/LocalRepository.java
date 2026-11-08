package br.unifil.projmongo.repository;

import br.unifil.projmongo.entity.Local;
import com.mongodb.client.MongoClient;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class LocalRepository extends BaseRepository {

    public LocalRepository(MongoClient client, @Value("${app.mongodb.database}") String dbName) {
        super(client, dbName, "Locais");
    }

    public void criarLocal(Local local) {
        Document doc = new Document("id", nextVal())
                .append("nome", local.getNome())
                .append("endereco", local.getEndereco())
                .append("cidade", local.getCidade());
        collection.insertOne(doc);
    }

    public long atualizarLocal(Local local) {
        Document set = new Document("nome", local.getNome())
                .append("endereco", local.getEndereco())
                .append("cidade", local.getCidade());
        return collection.updateOne(new Document("id", local.getId()), new Document("$set", set))
                .getMatchedCount();
    }

    public long deletarLocal(int pid) {
        return deletar(pid);
    }
}
