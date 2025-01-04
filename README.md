# RunTheBank

## Sobre o Projeto

**RunTheBank** é um projeto Java que simula operações bancárias. O objetivo é fornecer uma plataforma simples para execução de transações financeiras e gestão de contas bancárias, utilizando boas práticas de desenvolvimento e o Maven como ferramenta de build.

## Estrutura do Projeto

A estrutura do projeto segue o padrão de aplicações Java baseadas no Maven:

```
RunTheBank/
├── .mvn/              # Arquivos do Maven Wrapper
├── data/              # Dados utilizados pela aplicação
├── src/               # Código-fonte
├── .gitignore         # Arquivo para ignoração de arquivos pelo Git
├── mvnw               # Script Maven Wrapper para sistemas Unix/Linux
├── mvnw.cmd           # Script Maven Wrapper para sistemas Windows
├── pom.xml            # Configurações do Maven
```

### Principais Componentes

- **`.mvn/wrapper/`**: Contém arquivos para execução do Maven Wrapper, permitindo que o projeto seja construído sem necessidade de uma instalação prévia do Maven.
- **`data/`**: Diretório reservado para armazenar arquivos de dados (se necessário).
- **`src/`**: Diretório principal do código-fonte da aplicação.
- **`pom.xml`**: Arquivo de configuração do Maven, onde estão definidas as dependências e as configurações do projeto.

## Requisitos

Antes de construir e executar o projeto, certifique-se de ter instalado:

- **JDK (Java Development Kit)** 8 ou superior
- **Git** para clonar o repositório
- **Maven** (opcional, pois o Maven Wrapper está incluído no projeto)

## Como Executar

Siga os passos abaixo para clonar, construir e executar o projeto:

1. **Clone o Repositório**

   ```bash
   git clone https://github.com/infowap/RunTheBank.git
   ```

2. **Navegue até o Diretório do Projeto**

   ```bash
   cd RunTheBank
   ```

3. **Construa o Projeto com o Maven Wrapper**

   - Em sistemas Unix/Linux:

     ```bash
     ./mvnw clean install
     ```

   - Em sistemas Windows:

     ```bash
     mvnw.cmd clean install
     ```

4. **Execute a Aplicação**

   Após a construção, execute o arquivo JAR gerado no diretório `target`:

   ```bash
   java -jar target/<nome-do-arquivo>.jar
   ```

   Substitua `<nome-do-arquivo>` pelo nome real do arquivo JAR gerado.

## Documentação da API (Postman Collection)

A coleção do Postman para este projeto pode ser utilizada para testar as funcionalidades da API. 

### Endpoints

#### 1. **POST - Cadastro de Clientes**

**URL**: `http://localhost:8080/customer`

**Exemplo de Corpo da Requisição**:
```json
{
    "name": "Adão Wapnyk Filho",
    "document": "12365478933",
    "address": "Cidade de Pallet",
    "password": "StrongPassword952!#@",
    "type": "PJ"
}
```

#### 2. **POST - Abertura de Conta**

**URL**: `http://localhost:8080/account`

**Exemplo de Corpo da Requisição**:
```json
{
    "customerId": 3,
    "agency": "0001",
    "initialBalance": 10000.00
}
```

#### 3. **GET - Detalhes de uma Conta**

**URL**: `http://localhost:8080/account/2`

#### 4. **POST - Transferência entre Contas**

**URL**: `http://localhost:8080/transfer`

**Exemplo de Corpo da Requisição**:
```json
{
    "fromAccountId": 1,
    "toAccountId": 2,
    "amount": 200.00
}
```

#### 5. **GET - Detalhes de um Cliente**

**URL**: `http://localhost:8080/customer/1`

## Contribuição

Se você deseja contribuir para este projeto, siga estas etapas:

1. Realize um fork do repositório
2. Crie um branch para sua feature ou correção:

   ```bash
   git checkout -b minha-feature
   ```

3. Faça suas alterações e commit:

   ```bash
   git commit -m "Adiciona minha nova feature"
   ```

4. Envie suas alterações para o repositório remoto:

   ```bash
   git push origin minha-feature
   ```

5. Abra um Pull Request na página do GitHub.

## Licença

Este projeto está licenciado sob os termos da [MIT License](LICENSE).

## Contato

Para mais informações, entre em contato pelo [repositório oficial no GitHub](https://github.com/infowap/RunTheBank).
