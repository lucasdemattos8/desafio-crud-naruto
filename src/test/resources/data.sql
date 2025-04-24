INSERT INTO personagem (nome, idade, aldeia, tipo_ninja, chakra) VALUES 
('Naruto Uzumaki', 16, 'Konoha', 'NINJUTSU', 100),
('Sasuke Uchiha', 16, 'Konoha', 'NINJUTSU', 95),
('Sakura Haruno', 16, 'Konoha', 'TAIJUTSU', 70);

INSERT INTO personagem_jutsus (personagem_entity_id, jutsus) 
SELECT id, 'Rasengan,Kage Bunshin no Jutsu' FROM personagem WHERE nome = 'Naruto Uzumaki';

INSERT INTO personagem_jutsus (personagem_entity_id, jutsus)
SELECT id, 'Chidori,Sharingan' FROM personagem WHERE nome = 'Sasuke Uchiha';

INSERT INTO personagem_jutsus (personagem_entity_id, jutsus)
SELECT id, 'Chakra no Mesu,Okasho' FROM personagem WHERE nome = 'Sakura Haruno';