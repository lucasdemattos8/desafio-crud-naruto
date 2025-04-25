# 🐱‍👤 API CRUD Naruto

Uma API RESTful construída com Spring Boot e Arquitetura Hexagonal implementando operações CRUD e mecânicas especiais de jutsu para personagens de Naruto.

## 📑 Índice

1. [Funcionalidades](#-funcionalidades)
2. [Tecnologias](#-tecnologias-utilizadas)
3. [Pré-requisitos](#-pré-requisitos)
4. [Instalação e Execução](#-instalação-e-execução)
   - [Rodando com Maven (H2)](#-rodando-com-maven-h2)
   - [Rodando com Docker (PostgreSQL)](#-rodando-com-docker-postgresql)
5. [Endpoints da API](#-endpoints-da-api)
6. [Testes](#-testes)
7. [Arquitetura](#-arquitetura)
8. [Estrutura do Projeto](#-estrutura-do-projeto)
9. [Screenshots](#-screenshots)

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
- PostgreSQL / H2 Database
- Docker & Docker Compose
- JUnit 5 & Mockito
- Maven

## 📋 Pré-requisitos

Para Maven:

- Java 21
- Maven 3.8+

Para Docker:

- Docker
- Docker Compose

## 🔧 Instalação e Execução

### 🎯 Rodando com Maven (H2)

Ideal para desenvolvimento rápido e testes. Utiliza banco H2 em memória.

1. Clone o repositório:

```bash
git clone https://github.com/lucasdemattos8/desafio-crud-naruto.git
```

2. Execute a aplicação:

```bash
mvn spring-boot:run
```

> ℹ️ A aplicação estará disponível em `http://localhost:8080`

### 🐳 Rodando com Docker (PostgreSQL)

Recomendado para ambiente mais próximo ao de produção.

1. Clone o repositório:

```bash
git clone https://github.com/lucasdemattos8/desafio-crud-naruto.git
```

2. Execute com Docker Compose:

```bash
docker-compose up --build
```

> ℹ️ A aplicação estará disponível em `http://localhost:8080`

#### Diferenças entre as Execuções

| Característica | Maven (H2)      | Docker (PostgreSQL) |
| -------------- | --------------- | ------------------- |
| Banco de Dados | H2 (em memória) | PostgreSQL          |
| Persistência   | Temporária      | Permanente          |
| Inicialização  | Mais rápida     | Mais robusta        |
| Uso            | Desenvolvimento | Prod-like           |

# 🐘 Acessando o pgAdmin e conectando ao serviço `postgresdb` via Docker

Este tutorial ensina como acessar o pgAdmin em um ambiente Docker e adicionar um servidor para se conectar ao serviço PostgreSQL (`postgresdb`).

---

## ✅ Pré-requisitos

- Docker e Docker Compose instalados
- Um ambiente com `docker-compose.yml` rodando com `pgadmin` e `postgresdb`
- Variáveis de ambiente configuradas corretamente (`.env`)

---

## 1. Inicie o ambiente com Docker Compose

Se ainda não iniciou os containers, use:

```bash
   docker-compose up --build
```

---

## 2. Acesse o pgAdmin no navegador

Abra o navegador e vá para:

```
http://localhost:5050
```

![image](https://github.com/user-attachments/assets/ca0efa12-995c-4ac4-9a2b-9406a973dc97)

---

## 3. Faça login no pgAdmin

Use as credenciais definidas nas variáveis de ambiente:

- **Email:** `${PGADMIN_DEFAULT_EMAIL}`
- **Senha:** `${PGADMIN_DEFAULT_PASSWORD}`

![image](https://github.com/user-attachments/assets/f771b7ae-9217-46d7-8427-f3836e79364e)

---

## 4. Adicione um novo servidor

Depois de logado:

1. Clique com o botão direito em "**Servers**" > "**Register**" > "**Server...**"
   
![image](https://github.com/user-attachments/assets/c8594cd7-0872-47e1-b8a1-11524e86dbfa)

---

## 5. Configurar o servidor

### Aba **General**

- **Name:** `postgresdb` (ou qualquer nome que quiser)

![image](https://github.com/user-attachments/assets/c90c7914-657a-4b5d-8a07-411b0531ba91)

---

### Aba **Connection**

Preencha com as seguintes informações:

- **Host name/address:** `postgresdb`  
  > *(Este é o nome do serviço do Postgres no docker-compose, que funciona como hostname dentro da rede Docker)*
- **Port:** `5432`
- **Maintenance database:** `${DB_NAME}`  
  *(Ou `postgres` se estiver em dúvida)*
- **Username:** `${DB_USERNAME}`
- **Password:** `${DB_PASSWORD}`
- ✅ Marque a opção “Save Password”

![image](https://github.com/user-attachments/assets/493e2b5b-676d-4c35-bae2-45fe48cf23fa)

---

## 6. Concluir e conectar

Clique em **Save**.  
Se tudo estiver correto, o servidor será adicionado e aparecerá listado na sidebar.

![image](https://github.com/user-attachments/assets/b208b3ca-8628-4e1c-be8e-91be2fd39a8b)

---

## ✅ Pronto!

Agora você pode explorar as bases de dados, rodar queries e gerenciar tudo diretamente pelo pgAdmin 🎉

---

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

## 📸 Screenshots

### Swagger UI

![Screenshot da interface Swagger](https://github.com/user-attachments/assets/a4329530-8c15-46c7-8265-2eaaa5e75183)

> 🖼️ _Descrição: Screenshot da demonstração da interface_

### Execução de Jutsu

![image](https://github.com/user-attachments/assets/27827329-2d8f-4a5e-8adf-fc974bfbe557)

> 🖼️ _Descrição: Screenshot da execução de um jutsu_

### Listagem de Personagens

![image](https://github.com/user-attachments/assets/671edd71-2708-45c2-a866-aff5ea699be0)

> 🖼️ _Descrição: Screenshot da listagem de personagens_

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

## 🏛 Arquitetura

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

## 🤝 Contribuindo

1. Faça um Fork do projeto
2. Crie sua Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a Branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 👥 Autores

- **Lucas Miranda** - _Desenvolvedor_ - [Lucas Miranda](https://github.com/lucasdemattos8)

---

Feito com dedicação e esforço por [Lucas Miranda](https://github.com/lucasdemattos8) 😊
