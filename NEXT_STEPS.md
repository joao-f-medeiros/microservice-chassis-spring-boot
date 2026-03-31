# Próximos Passos

> Estado atual: CI/CD integrado ao template corporativo, `distributionManagement` e `settings.xml` configurados para publicar no Nexus.

---

## ✅ 1. Merge Request → `main` — concluído

---

## ✅ 2. Pipeline CI/CD — concluído

`.gitlab-ci.yml` integrado ao template corporativo `common-lib-java` via `include: project:`.

- Usa imagem Java 21: `ci-tools-alpine-java-21:latest` (sobrescreve o default Java 17 do template)
- `compile` sobrescrito: `mvn clean install` (instala o archetype no `.m2` antes do `test`)
- `test` sobrescrito: gera projeto a partir do archetype e roda `mvn test` em `/tmp/archetype-test/test-svc`
- Herda do template: `default` (tags, cache `.m2`), `workflow.rules` (develop + MR), `sonar`, `develop_snapshot`, `branch_snapshot`, `build_e_deploy`, `secret-detection`

---

## ✅ 3. Migrar `@MockBean` → `@MockitoBean` — concluído

Migrado em:
- `src/main/resources/archetype-resources/src/test/java/__artifactIdToPackage__/adapter/inbound/rest/SampleResourceIT.java`
- `skeleton/src/test/java/${{ values.packagePath }}/adapter/inbound/rest/SampleResourceIT.java`

---

## ✅ 4. Publicar archetype em repositório Maven — concluído

`settings.xml` criado com as credenciais dos servidores Nexus (padrão `projuris-domain`). O `build_e_deploy` do template detecta o arquivo automaticamente.

> `distributionManagement` não está no `pom.xml` — deve ser configurado via `settings.xml` ou variável `-DaltDeploymentRepository` na pipeline.

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
