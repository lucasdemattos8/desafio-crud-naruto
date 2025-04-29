CREATE TABLE jutsu (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    custo_chakra INT NOT NULL,
    personagem_id BIGINT,
    FOREIGN KEY (personagem_id) REFERENCES personagem(id)
);