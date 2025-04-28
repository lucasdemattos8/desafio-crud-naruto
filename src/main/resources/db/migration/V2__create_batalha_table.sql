CREATE TABLE batalha (
    id BIGSERIAL PRIMARY KEY,
    ninja1_id BIGINT NOT NULL,
    ninja2_id BIGINT NOT NULL,
    finalizada BOOLEAN NOT NULL DEFAULT FALSE,
    turno_atual INT NOT NULL DEFAULT 1,
    ninja_atual_id BIGINT NOT NULL,
    vida_ninja1 INT NOT NULL DEFAULT 100,
    vida_ninja2 INT NOT NULL DEFAULT 100,
    chakra_ninja1 INT NOT NULL DEFAULT 100,
    chakra_ninja2 INT NOT NULL DEFAULT 100,
    ataque_ninja_id BIGINT,
    ataque_jutsu VARCHAR(255),
    ataque_dano_base INTEGER,
    FOREIGN KEY (ninja1_id) REFERENCES personagem(id),
    FOREIGN KEY (ninja2_id) REFERENCES personagem(id),
    FOREIGN KEY (ninja_atual_id) REFERENCES personagem(id),
    FOREIGN KEY (ataque_ninja_id) REFERENCES personagem(id)
);