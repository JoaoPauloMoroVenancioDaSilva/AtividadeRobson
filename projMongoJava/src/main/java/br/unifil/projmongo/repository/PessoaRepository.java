package br.unifil.projmongo.repository;

import br.unifil.projmongo.entity.Pessoa;
import com.mongodb.client.MongoClient;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class PessoaRepository extends BaseRepository {

    public PessoaRepository(MongoClient client, @Value("${app.mongodb.database}") String dbName) {
        super(client, dbName, "Pessoas");
    }

    public void criarPessoa(Pessoa pessoa) {
        Document doc = new Document("id", nextVal())
                .append("nome", pessoa.getNome())
                .append("fone", pessoa.getFone())
                .append("aniversario", pessoa.getAniversario());
        collection.insertOne(doc);
    }

    public long atualizarPessoa(Pessoa pessoa) {
        Document set = new Document("nome", pessoa.getNome())
                .append("fone", pessoa.getFone())
                .append("aniversario", pessoa.getAniversario());
        
        return collection.updateOne(new Document("id", pessoa.getId()), new Document("$set", set))
                .getMatchedCount();
    }

    public long deletarPessoa(int pid) {
        return deletar(pid);
    }
}