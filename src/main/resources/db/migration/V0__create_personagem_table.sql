CREATE TABLE personagem (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    idade INTEGER NOT NULL,
    aldeia VARCHAR(255) NOT NULL,
    tipo_ninja VARCHAR(50) NOT NULL,
    chakra INTEGER NOT NULL
);

CREATE TABLE personagem_jutsus (
    personagem_entity_id BIGINT NOT NULL,
    jutsus VARCHAR(255),
    FOREIGN KEY (personagem_entity_id) REFERENCES personagem(id)
);