# Microservice Chassis Spring Boot

Este projeto é um microservice chassis projetado para servir como base na criação de novos microserviços. Ele incorpora padrões modernos de arquitetura, como a Arquitetura Hexagonal, para garantir escalabilidade, testabilidade e independência tecnológica.

## Arquitetura

Este chassis segue o padrão de Arquitetura Hexagonal (Ports and Adapters), que separa claramente as responsabilidades entre domínio, interfaces de entrada e saída. Isso facilita a manutenção, o entendimento do código e a inclusão de novas funcionalidades. Saiba mais sobre o padrão [aqui](https://netflixtechblog.com/ready-for-changes-with-hexagonal-architecture-b315ec967749).

### Estrutura de Pacotes

A estrutura do projeto foi ajustada para refletir os princípios da Arquitetura Gritante. Nomes simples e descritivos ajudam a acelerar o entendimento do que é importante, eliminando ambiguidades.

```markdown
📦 src
┣ 📦 main/java/com/projuris
┃ ┣ 📂 bootstrap: Configuração e inicialização da aplicação.
┃ ┃ ┣ 📂 exceptions: Exceções reutilizáveis entre camadas.
┃ ┃ ┗ 📜 Application.java: Classe inicializadora.
┃ ┣ 📂 core: Contém toda a lógica de negócio e abstrações do domínio.
┃ ┃ ┣ 📂 domain: Classes de domínio, comandos e eventos do sistema.
┃ ┃ ┣ 📂 usecases: Casos de uso representando operações do sistema.
┃ ┃ ┣ 📂 gateways: Interfaces que definem interações com recursos externos.
┃ ┃ ┗ 📂 repositories: Repositórios abstratos para persistência.
┃ ┣ 📂 inbound: Interfaces de entrada, como controladores e listeners.
┃ ┃ ┣ 📂 rest: APIs REST e controladores HTTP.
┃ ┃ ┗ 📂 messagebrokers: Consumers e listeners de mensagens.
┃ ┗ 📂 outbound: Implementações de acesso a recursos externos.
┃ ┃ ┣ 📂 persistence: Comunicação com bancos de dados.
┃ ┃ ┗ 📂 messageproducers: Producers de mensagens.
┣ 📦 main/resources
┃ ┣ 📜 application.yml: Configurações da aplicação.
┃ ┗ 📜 openapi.yml: Especificação OpenAPI para geração de stubs.
┣ 📜 .gitignore
┣ 📜 Dockerfile
┣ 📜 pom.xml
┗ 📜 README.md
```

## Tecnologias Utilizadas

O chassis utiliza as seguintes tecnologias:

- **Java 21**: Linguagem principal do projeto.
- **Spring Boot 3.2.5**: Framework para criação de aplicações Java modernas.
- **Spring Cloud 2023.0.0**: Ferramentas para microsserviços distribuídos.
- **OpenAPI Generator**: Geração automática de código a partir de especificações.
- **MapStruct**: Mapeamento de objetos eficiente e simples.
- **Micrometer**: Monitoramento de métricas com suporte ao Prometheus.
- **Logbook**: Captura e registro de logs HTTP detalhados.
- **Resilience4j**: Implementação de padrões de resiliência como circuit breakers.
- **JUnit 5**: Testes unitários e de integração.

## Build

Antes de iniciar a aplicação, execute os seguintes comandos para garantir que as dependências estejam atualizadas e os stubs gerados:

```bash
mvn clean install
mvn clean generate-sources
```

## URLs de Referência

- **Swagger**: Documentação interativa do OpenAPI: `http://localhost:8080/requisicao-service/swagger-ui/`
- **Spring Actuator**: Endpoints de monitoramento: `http://localhost:9090/actuator`
- **Métricas Prometheus**: `http://localhost:9090/actuator/prometheus`

## Importância da Arquitetura Gritante

A Arquitetura Gritante foca na clareza e na simplicidade, permitindo que desenvolvedores entendam rapidamente o objetivo do sistema e onde cada funcionalidade reside. Ao usar nomes diretos e intuitivos, reduz-se o tempo necessário para explorar e alterar o código, aumentando a produtividade e diminuindo erros.

## Exemplos de Implementação

Consulte os seguintes exemplos para ajudar na implementação:

- **REST API e Datasource Feign Client**: [Exemplo no GitHub](https://github.com/zevolution/netflix-hexagonal-architecture)

---

Este chassis foi projetado para ser flexível e modular. Personalize-o conforme as necessidades específicas do seu projeto, mantendo os princípios de design modular e acoplamento fraco.
