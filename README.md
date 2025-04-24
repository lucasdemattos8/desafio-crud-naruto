# API CRUD Naruto

Uma API RESTful construída com Spring Boot implementando operações CRUD e mecânicas especiais de jutsu para personagens de Naruto.

## 🚀 Funcionalidades

- Operações CRUD completas para personagens ninja
- Autenticação JWT
- Suporte à paginação e ordenação
- Sistema de execução de jutsus
- Habilidades específicas por tipo de ninja (Ninjutsu, Genjutsu, Taijutsu)

## 🛠 Tecnologias Utilizadas

- Java 21
- Spring Boot 3.3.4
- Spring Security + JWT
- PostgreSQL
- H2 Database (testes)
- JUnit 5
- Mockito
- Maven

## 📋 Pré-requisitos

- Java 17+
- Maven
- PostgreSQL

## 🔧 Instalação

1. Clone o repositório:

```bash
git clone https://github.com/LucasMirandaIT/desafio-crud-naruto.git
```

2. Configure o banco de dados em `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/naruto
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

3. Construa o projeto:

```bash
mvn clean install
```

4. Execute a aplicação:

```bash
mvn spring-boot:run
```

## 🎯 Endpoints da API

### Autenticação

- `POST /api/v1/auth` - Gerar token JWT

### Personagens

- `GET /api/v1/personagens` - Listar todos os personagens (paginado)
- `GET /api/v1/personagens/{id}` - Buscar personagem por ID
- `POST /api/v1/personagens` - Criar novo personagem
- `PUT /api/v1/personagens/{id}` - Atualizar personagem
- `DELETE /api/v1/personagens/{id}` - Deletar personagem
- `POST /api/v1/personagens/{id}/jutsu` - Executar jutsu do personagem

## 📝 Exemplos de Requisições

### Criar Personagem

```json
{
  "nome": "Naruto Uzumaki",
  "idade": 16,
  "aldeia": "Konoha",
  "tipoNinja": "NINJUTSU",
  "chakra": 100,
  "jutsus": ["Rasengan", "Kage Bunshin no Jutsu"]
}
```

### Executar Jutsu

```bash
curl -X POST "http://localhost:8080/api/v1/personagens/1/jutsu?desviar=false" \
     -H "Authorization: Bearer seu_token"
```

## 🧪 Testes

Execute os testes usando:

```bash
mvn test
```

O projeto inclui:

- Testes unitários
- Testes de integração
- Testes com mock
- Banco de dados H2 em memória para testes

## 🏗 Arquitetura

O projeto segue a Arquitetura Hexagonal (Ports & Adapters) com:

- Design orientado a domínio
- Clara separação de responsabilidades
- Portas de entrada/saída
- Padrão de casos de uso
- Princípios SOLID

## 📦 Estrutura do Projeto

```
src
├── main
│   ├── java
│   │   └── com.db.desafio_naruto
│   │       ├── application
│   │       │   ├── port
│   │       │   │   ├── in
│   │       │   │   └── out
│   │       │   └── service
│   │       ├── domain
│   │       │   └── model
│   │       └── infrastructure
│   │           ├── adapter
│   │           │   ├── in
│   │           │   └── out
│   │           └── config
│   └── resources
└── test
    └── java
        └── com.db.desafio_naruto
```
