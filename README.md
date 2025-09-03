# movie-awards-api

Uma **API RESTful** para gerenciar um banco de dados de filmes premiados. Este projeto foi concebido para processar e manipular listas de filmes que receberam prêmios em anos específicos, oferecendo endpoints para operações CRUD completas. É ideal para integrações que necessitam de um backend robusto e escalável para o cadastro de filmes e seus respectivos prêmios.

-----

### Tecnologias

O projeto foi construído utilizando as seguintes tecnologias:

* **Linguagem:** Java 21
* **Gerenciador de Projeto:** Gradle Kotlin
* **Containerização:** Docker

-----

### Arquitetura do Projeto

A arquitetura do projeto segue os princípios da **Clean Architecture**, visando a separação de responsabilidades e a independência das camadas. O design foi dividido em camadas lógicas:

* **Camada de Domínio:** Contém as regras de negócio e as entidades do sistema. É a camada mais interna e não tem dependência de outras camadas.
* **Camada de Aplicação:** Responsável por orquestrar o fluxo de dados e implementar as regras de negócio específicas da aplicação.
* **Camada de Adapters:** Possui regras para direcionamento de usecases.
* **Camada de Infraestrutura:** Lida com detalhes de implementação externos, como acesso ao banco de dados, comunicação com a API e frameworks.

-----

### Como Executar

Você pode executar o projeto de duas formas: usando o **Docker** ou através de uma **IDE**.

#### Execução com Docker

1.  **Pré-requisitos:** Certifique-se de ter o Docker instalado e em execução em sua máquina.

2.  **Construir a imagem Docker:** Navegue até a pasta raiz do projeto e execute o seguinte comando no terminal para criar a imagem da aplicação.

    ```bash
    docker build -t movie-awards-api .
    ```

3.  **Executar o container:** Após a imagem ser construída, execute o comando abaixo para iniciar o container. O servidor da API estará disponível na porta `8080`.

    ```bash
    docker run -p 8080:8080 movie-awards-api
    ```

#### Execução com IDE

1.  **Pré-requisitos:**

    * Java 21 JDK instalado.
    * Uma IDE com suporte a projetos Java, como **IntelliJ IDEA**, **Eclipse** ou **VS Code**.

2.  **Execução via Console:**
    Navegue até o diretório raiz do projeto e execute o seguinte comando no terminal. A API estará disponível em `http://localhost:8080`.

    ```bash
    ./gradlew bootRun
    ```

3.  **Execução de testes automatizados via Console:**
    Navegue até o diretório raiz do projeto e execute o seguinte comando no terminal.

    ```bash
    ./gradlew clean build
    ```
    OU
    ```bash
    ./gradlew test
    ```

4.  **Execução na IDE:**
    Abra sua IDE e importe o projeto como um projeto Gradle. A IDE irá detectar o arquivo `build.gradle.kts` e configurar automaticamente o projeto e suas dependências. Em seguida, localize a classe principal da aplicação (geralmente com o método `main`) e execute-a.

-----

### Endpoints e Exemplos

A API oferece os seguintes endpoints para manipulação dos dados de filmes premiados.

#### **Criação de um novo filme**

```bash
curl --location 'http://localhost:8080/movie-award' \
--header 'Content-Type: application/json' \
--data '{
    "year": 1970,
    "title": "The Formula",
    "studios": "MGM",
    "producers": "Steve Shagan",
    "winner": true
}'
```

#### **Listagem de todos os filmes**

```bash
curl --location 'http://localhost:8080/movie-award'
```

#### **Consulta de um filme por código**

```bash
curl --location 'http://localhost:8080/movie-award/283f16dd-57af-4977-9d4d-e41bde5bf6ed'
```

* **Observação:** Lembre-se de substituir o código `283f16dd-57af-4977-9d4d-e41bde5bf6ed` pelo código de um filme existente em seu banco de dados.

#### **Remoção de um filme por código**

```bash
curl --location --request DELETE 'http://localhost:8080/movie-award/1364cd0b-2734-4e90-824f-be74f5f4b5a6'
```

* **Observação:** Lembre-se de substituir o código `1364cd0b-2734-4e90-824f-be74f5f4b5a6` pelo código de um filme existente em seu banco de dados.

#### **Consulta de intervalo de prêmios**

```bash
curl --location 'http://localhost:8080/movie-award/awards-interval'
```
