# Próximos Passos

> Estado atual: `main` com CI/CD configurado, `@MockitoBean` migrado. Build e 19 testes passando.

---

## ✅ 1. Merge Request → `main` — concluído

---

## ✅ 2. Pipeline CI/CD — concluído

`.gitlab-ci.yml` criado com stages `build` e `test-generate`. Validado localmente: build OK, 19/19 testes passando no projeto gerado.

---

## ✅ 3. Migrar `@MockBean` → `@MockitoBean` — concluído

Migrado em:
- `src/main/resources/archetype-resources/src/test/java/__artifactIdToPackage__/adapter/inbound/rest/SampleResourceIT.java`
- `skeleton/src/test/java/${{ values.packagePath }}/adapter/inbound/rest/SampleResourceIT.java`

---

## 4. Publicar archetype em repositório Maven

Hoje o archetype só funciona após `mvn install` local. Para uso em CI e no Backstage, precisa estar em um repositório remoto (Nexus/Artifactory).

Adicionar ao `pom.xml` raiz:

```xml
<distributionManagement>
  <repository>
    <id>releases</id>
    <url>https://<seu-nexus>/repository/maven-releases/</url>
  </repository>
  <snapshotRepository>
    <id>snapshots</id>
    <url>https://<seu-nexus>/repository/maven-snapshots/</url>
  </snapshotRepository>
</distributionManagement>
```

---

## 5. Validar template Backstage em ambiente real

O `template.yaml` e o skeleton foram corrigidos e estão logicamente consistentes, mas não foram testados via Backstage Scaffolder. Validar antes de disponibilizar para os times.

**Checklist:**
- [ ] Importar `template.yaml` no Backstage de staging
- [ ] Executar geração com `groupId=com.example` e `artifactId=my-test-service`
- [ ] Verificar pacote gerado: deve ser `com.example.mytestservice` (sem duplicação)
- [ ] Confirmar que o projeto gerado compila e os testes passam

---

## 6. Criar release estável

O archetype está em `0.0.1-SNAPSHOT`. Definir e publicar a primeira versão estável.

```shell
mvn release:prepare   # define versão, cria tag git
mvn release:perform   # publica no repositório Maven
```
