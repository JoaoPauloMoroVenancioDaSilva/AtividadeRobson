package br.unifil.projmongo.repository;

import br.unifil.projmongo.entity.Pet;
import com.mongodb.client.MongoClient;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class PetRepository extends BaseRepository {

    public PetRepository(MongoClient client, @Value("${app.mongodb.database}") String dbName) {
        super(client, dbName, "Pets");
    }

    public void criarPet(Pet pet) {
        Document doc = new Document("id", nextVal())
                .append("nome", pet.getNome())
                .append("raca", pet.getRaca())
                .append("idade", pet.getIdade());
        collection.insertOne(doc);
    }

    public long atualizarPet(Pet pet) {
        Document set = new Document("nome", pet.getNome())
                .append("raca", pet.getRaca())
                .append("idade", pet.getIdade());
        return collection.updateOne(new Document("id", pet.getId()), new Document("$set", set))
                .getMatchedCount();
    }

    public long deletarPet(int pid) {
        return deletar(pid);
    }
}
