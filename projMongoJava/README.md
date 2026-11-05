# projMongoJava

Conversão do projeto Python (Flask + pymongo) para Java (Spring Boot + MongoDB driver).

## Como rodar
Requisitos: Java 17+, Maven e MongoDB rodando em `localhost:27017`.

    mvn spring-boot:run

Acesse http://localhost:85  (porta e banco `Aula04` em `src/main/resources/application.properties`).

## Correspondência Python -> Java

| Python                    | Java                                           |
|---------------------------|------------------------------------------------|
| app.py                    | App.java + config/ViewConfig.java              |
| route.py (blueprints)     | route/*Route.java                              |
| CONTROLLER/*.py           | controller/*Controller.java                    |
| MODEL/* (classe entidade) | entity/*.java                                  |
| MODEL/* (classe Model)    | model/*Model.java (herdam de BaseModel)        |
| templates/*.html          | resources/static/index.html e static/views/    |
| static/js, static/img     | resources/static/static/js e static/img        |

## Endpoints
`GET/POST /pessoas`, `PUT/DELETE /pessoas/{id}` — idem para `/pets`, `/locais` e `/agendamentos`.

## Entidades
| Entidade    | Collection   | Campos                                                    |
|-------------|--------------|-----------------------------------------------------------|
| Pessoa      | Pessoas      | id, nome, fone, aniversario                               |
| Pet         | Pets         | id, nome, fone, aniversario                               |
| Local       | Locais       | id, nome, endereco, cidade                                |
| Agendamento | Agendamentos | id, pessoaId, petId, localId, data, descricao             |

**Agendamento** relaciona as outras três: uma Pessoa leva um Pet a um Local numa data
(ex.: banho e tosa, consulta, passeio). Guarda só os ids (referências) e:
- no POST/PUT valida se a Pessoa, o Pet e o Local existem (senão responde 400);
- no GET devolve também `pessoaNome`, `petNome` e `localNome` (join manual no controller);
- Pessoa, Pet ou Local com agendamento vinculado não podem ser excluídos (409).

## Diferenças em relação ao original
- **Personagem** foi substituído por **Local**, e foi criada a entidade **Agendamento**.
- Corrigido: o DELETE de pet chamava `deletar_pessoa` (método inexistente no PetController).
- PUT usa `matchedCount` em vez de `modified_count`: salvar sem alterar nada não retorna mais 404.
- Lógica repetida (`listar_todos`, `obter_maior_id`, `next_val`, delete) centralizada em `BaseModel`.
- Removido `$("#formX")[0].reset()` dos JS (o form não existe e gerava erro no console).
