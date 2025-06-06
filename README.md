# Microservice Chassis Spring Boot Archetype

Primeiramente baixe o projeto. Logo após baixa-lo, no diretório raiz do projeto, execute o comando a seguir para que o
projeto seja compilado e adicionado no seu repositorio local.

```shell
mvn clean install
```

Agora fora do diretório do projeto(em qualquer outro), com o comando a seguir podemos gerar um novo projeto no modo
interativo com base no archetype construido acima.

```shell
mvn -B archetype:generate \
	-DarchetypeGroupId=com.microservice \
	-DarchetypeArtifactId=archetype--microservice \
	-DarchetypeVersion=0.0.1-SNAPSHOT \
	-DgroupId=com.projuris \
	-DartifactId=permission-service \
	-Dversion=0.0.1-SNAPSHOT \
	-DartifactIdToPackage=permissionservice
```

Descritivo de cada propriedade:

* **archetypeGroupId**: groupId do arquétipo maven a ser utilizado como base
* **archetypeArtifactId**: artifactId do arquétipo maven a ser utilizado como base
* **archetypeVersion**: versão do arquétipo maven a ser utilizado
* **groupId**: groupId que será utilizado no projeto criado a partir do arquétipo
* **artifactId**: artifactId que será utilizado no projeto criado a partir do arquétipo
* **version**: versão inicial a ser utilizado no projeto cirado a partir do arquétipo
* **artifactIdToPackage**: nome do pacote raiz a ser utilizado(veja abaixo os exemplos 1, 2 e 3).
    * Por padrão este é um parâmetro obrigatório, caso nenhum valor for inserido, o nome do pacote raiz será o
      artifactId(formatado contendo somente letras)
    * Caso deseje criar subpackage para categorizar, utilize "/", ex: architecture/netflixhexagonal

# Estrutura do Projeto do Archetype

A estrutura do projeto do archetype em si é organizada da seguinte forma:

```
📦 archetype-microservice
┣ 📦 src
┃ ┣ 📦 main
┃ ┃ ┣ 📦 resources
┃ ┃ ┃ ┣ 📦 archetype-resources
┃ ┃ ┃ ┃ ┣ 📜 Dockerfile
┃ ┃ ┃ ┃ ┣ 📜 README.MD
┃ ┃ ┃ ┃ ┣ 📜 .gitignore
┃ ┃ ┃ ┃ ┗ pom.xml
┃ ┃ ┃ ┣ 📦 META-INF
┃ ┃ ┃ ┃ ┗ 📦 maven
┃ ┃ ┃ ┃   ┗ 📜 archetype-metadata.xml
┣ 📜 .gitignore
┣ 📜 pom.xml
┗ 📜 README.md
```

Principais arquivos e diretórios:

* **pom.xml**: Arquivo de configuração principal do Maven para o projeto do archetype.
* **src/main/resources/archetype-resources**: Contém os recursos que serão copiados para o projeto gerado. Isso inclui o `pom.xml` do projeto gerado, `Dockerfile`, `README.MD`, `.gitignore`, etc.
* **src/main/resources/META-INF/maven/archetype-metadata.xml**: Define como o archetype deve gerar projetos, incluindo variáveis e arquivos a serem processados.

# Exemplos

**Exemplo 1**: Utilizando `-DartifactIdToPackage=netflixhexagonalarchitecture`

```markdown
📦 src
┣ 📦 main
┃ ┣ 📦 java
┃ ┃ ┣ 📦 dev
┃ ┃ ┃ ┣ 📦 zevolution
┃ ┃ ┃ ┃ ┣ 📦 netflixhexagonalarchitecture
┃ ┃ ┃ ┃ ┃ ┣ 📂 adapter
┃ ┃ ┃ ┃ ┃ ┣ 📂 bootstrap
┃ ┃ ┃ ┃ ┃ ┣ 📂 internal
┃ ┃ ┃ ┃ ┃ ┗ 📜 Application.java
┣ 📦 main/resources
┣ 📜 .gitignore
┣ 📜 Dockerfile
┣ 📜 pom.xml
┗ 📜 README.MD
```

**Exemplo 2**: Utilizando `-DartifactIdToPackage=architecture/netflixhexagonal`

```markdown
📦 src
┣ 📦 main
┃ ┣ 📦 java
┃ ┃ ┣ 📦 dev
┃ ┃ ┃ ┣ 📦 zevolution
┃ ┃ ┃ ┃ ┣ 📦 architecture
┃ ┃ ┃ ┃ ┃ ┣ 📦 netflixhexagonal
┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂 adapter
┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂 bootstrap
┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂 internal
┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜 Application.java
┣ 📦 main/resources
┣ 📜 .gitignore
┣ 📜 Dockerfile
┣ 📜 pom.xml
┗ 📜 README.MD
```

**Exemplo 3** : NÃO utlizando a propriedade `-DartifactIdToPackage` e passando `artifactId`
como `-DartifactId=api--teste`. Veja que qualquer caracter diferente de a-z, será removido

```markdown
📦 src
┣ 📦 main
┃ ┣ 📦 java
┃ ┃ ┣ 📦 dev
┃ ┃ ┃ ┣ 📦 zevolution
┃ ┃ ┃ ┃ ┣ 📦 apiteste
┃ ┃ ┃ ┃ ┃ ┣ 📂 adapter
┃ ┃ ┃ ┃ ┃ ┣ 📂 bootstrap
┃ ┃ ┃ ┃ ┃ ┣ 📂 internal
┃ ┃ ┃ ┃ ┃ ┗ 📜 Application.java
┣ 📦 main/resources
┣ 📜 .gitignore
┣ 📜 Dockerfile
┣ 📜 pom.xml
┗ 📜 README.MD
```