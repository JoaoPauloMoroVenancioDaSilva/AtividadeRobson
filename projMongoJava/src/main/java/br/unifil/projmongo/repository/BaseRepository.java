package br.unifil.projmongo.repository;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Código comum a todos os Models (no Python estava repetido em cada classe):
 * conexão com a collection, listar_todos, obter_maior_id e next_val.
 */
public abstract class BaseRepository {

    protected final MongoCollection<Document> collection;

    protected BaseRepository(MongoClient client, String dbName, String collectionName) {
        this.collection = client.getDatabase(dbName).getCollection(collectionName);
    }

    /** find({}, {"_id": 0}) */
    public List<Document> listarTodos() {
        return collection.find()
                .projection(Projections.excludeId())
                .into(new ArrayList<>());
    }

    public int obterMaiorId() {
        Document doc = collection.find()
                .projection(Projections.fields(Projections.excludeId(), Projections.include("id")))
                .sort(Sorts.descending("id"))
                .limit(1)
                .first();
        if (doc == null || doc.get("id") == null) {
            return 0;
        }
        return ((Number) doc.get("id")).intValue();
    }

    public Document buscarPorId(int pid) {
        return collection.find(new Document("id", pid))
                .projection(Projections.excludeId())
                .first();
    }

    public boolean existe(Integer pid) {
        return pid != null && buscarPorId(pid) != null;
    }

    public long contarPor(String campo, int valor) {
        return collection.countDocuments(new Document(campo, valor));
    }

    public int nextVal() {
        return obterMaiorId() + 1;
    }

    public long deletar(int pid) {
        return collection.deleteOne(new Document("id", pid)).getDeletedCount();
    }
}
