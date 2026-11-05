package br.unifil.projmongo.model;

import br.unifil.projmongo.entity.Pet;
import com.mongodb.client.MongoClient;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

/** Persistência de Pet no MongoDB (collection "Pets"). */
@Repository
public class PetModel extends BaseModel {

    public PetModel(MongoClient client, @Value("${app.mongodb.database}") String dbName) {
        super(client, dbName, "Pets");
    }

    public void criarPet(Pet pet) {
        Document doc = new Document("id", nextVal())
                .append("nome", pet.getNome())
                .append("fone", pet.getFone())
                .append("aniversario", pet.getAniversario());
        collection.insertOne(doc);
    }

    /** Retorna quantos documentos foram encontrados (0 ou 1). */
    public long atualizarPet(Pet pet) {
        Document set = new Document("nome", pet.getNome())
                .append("fone", pet.getFone())
                .append("aniversario", pet.getAniversario());
        return collection.updateOne(new Document("id", pet.getId()), new Document("$set", set))
                .getMatchedCount();
    }

    public long deletarPet(int pid) {
        return deletar(pid);
    }
}
