# Microservice Chassis Spring Boot Archetype

Baixe o projeto e, no diretório raiz, execute o comando abaixo para compilar e instalar o archetype no repositório local Maven:

```shell
mvn clean install
```

Fora do diretório do projeto, gere um novo microserviço com o comando:

```shell
mvn archetype:generate \
  -DarchetypeGroupId=com.microservice \
  -DarchetypeArtifactId=archetype--microservice \
  -DarchetypeVersion=0.0.1-SNAPSHOT \
  -DgroupId=com.projuris \
  -DartifactId=payment-service \
  -Dversion=0.0.1-SNAPSHOT \
  -Dpackage=com.projuris \
  -DinteractiveMode=false
```

> **Importante**: o parâmetro `-Dpackage` deve receber **somente o `groupId`** (ex: `com.projuris`). O sufixo do pacote é derivado automaticamente do `artifactId`.

Descritivo de cada propriedade:

* **archetypeGroupId**: groupId do arquétipo Maven a ser utilizado como base
* **archetypeArtifactId**: artifactId do arquétipo Maven a ser utilizado como base
* **archetypeVersion**: versão do arquétipo Maven a ser utilizado
* **groupId**: groupId que será utilizado no projeto criado
* **artifactId**: artifactId que será utilizado no projeto criado (ex: `payment-service`)
* **version**: versão inicial do projeto gerado
* **package**: deve ser igual ao `groupId`. O pacote raiz do projeto será composto por `<package>.<artifactId-formatado>`, onde o artifactId é normalizado (somente letras minúsculas)

# Estrutura gerada

Dado `groupId=com.projuris` e `artifactId=payment-service`, o projeto gerado terá:

* **Pacote raiz**: `com.projuris.paymentservice`
* **Estrutura de diretórios**:

```
📦 src
┣ 📦 main
┃ ┣ 📦 java
┃ ┃ ┗ 📦 com.projuris.paymentservice
┃ ┃   ┣ 📂 adapter
┃ ┃   ┃ ┣ 📂 inbound        (REST controllers, DTOs, mappers, config)
┃ ┃   ┃ ┗ 📂 outbound       (repositórios, integrações, config)
┃ ┃   ┣ 📂 core
┃ ┃   ┃ ┣ 📂 config         (configurações Spring)
┃ ┃   ┃ ┗ 📂 domain
┃ ┃   ┃   ┣ 📂 entities
┃ ┃   ┃   ┣ 📂 services
┃ ┃   ┃   ┣ 📂 repositories
┃ ┃   ┃   ┗ 📂 integrations
┃ ┃   ┗ 📜 Application.java
┣ 📦 main/resources
┃ ┣ 📜 bootstrap.yml
┃ ┗ 📜 logback-spring.xml
┗ 📦 test/java
  ┗ 📦 com.projuris.paymentservice
    ┣ 📂 adapter/inbound/rest  (testes de integração MockMvc)
    ┗ 📂 architecture          (testes ArchUnit - validação hexagonal)
```

# Backstage / Scaffolder

O arquivo `template.yaml` contém a definição do template para uso no **Backstage Scaffolder**. Ele usa o diretório `./skeleton` como base e substitui as variáveis de template (`{{ values.* }}`).

Parâmetros requeridos pelo template Backstage:

| Parâmetro | Descrição | Exemplo |
|---|---|---|
| `artifactId` | Nome do artefato Maven | `payment-service` |
| `groupId` | Group ID Maven | `com.projuris` |
| `description` | Descrição do serviço | - |
| `squad` | Squad responsável | - |
| `javaVersion` | Versão do JDK (`17` ou `21`) | `21` |
| `repoUrl` | URL do repositório GitLab | - |
