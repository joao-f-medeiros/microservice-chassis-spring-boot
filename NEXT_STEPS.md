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

O `template.yaml` e o skeleton foram validados localmente via `test-skeleton.sh` (simula o `fetch:template` do Backstage). Falta concluir a validação via Scaffolder real.

**Checklist:**
- [x] Simular geração com `groupId=com.example` e `artifactId=my-test-service` — `test-skeleton.sh`
- [x] Verificar pacote gerado: `com.example.mytestservice` (sem duplicação) — OK
- [x] Confirmar que o projeto gerado compila e os testes passam — 19 testes passando
- [x] Corrigir `YAMLParseError` no `template.yaml` — descrições com `(ex: ...)` precisam de aspas
- [ ] Push das correções e re-registrar no Backstage de staging
- [ ] Preencher o formulário do Scaffolder e validar a geração completa

### Como testar no Backstage local (porta 3000)

**Pré-requisito:** integração GitLab configurada no `app-config.yaml` do Backstage para o step `publish:gitlab` funcionar:
```yaml
integrations:
  gitlab:
    - host: gitlab.com
      token: ${GITLAB_TOKEN}
```

**1. Fazer push das correções:**
```bash
git add template.yaml
git commit -m "fix: quote YAML descriptions containing colons"
git push origin <branch>
```

**2. Registrar o template no Backstage:**
- Acesse http://localhost:3000 → **Create** → **Register Existing Component**
- Cole a URL raw do `template.yaml` no GitHub:
  ```
  https://raw.githubusercontent.com/joao-f-medeiros/microservice-chassis-spring-boot/<branch>/template.yaml
  ```
- Clique em **Analyze** → **Import**

**3. Executar o Scaffolder:**
- Volte para **Create** → selecione **"Microservice Spring Boot (Hexagonal)"**
- Preencha com valores de teste:
  - Artifact ID: `my-test-service`
  - Group ID: `com.example`
  - Java Version: `21`
  - Repo URL: `gitlab.com` + qualquer owner/repo de teste

**4. O que validar:**
- Formulário renderiza sem erros de YAML
- Skeleton é gerado com `package com.example.mytestservice` (sem duplicação)
- Step `publish:gitlab` cria o repositório com sucesso

### Erros conhecidos encontrados

| Erro | Causa | Correção |
|------|-------|----------|
| `YAMLParseError: Nested mappings are not allowed in compact mappings` | `description:` com `(ex: ...)` — o `: ` é interpretado como mapeamento YAML | Colocar o valor entre aspas duplas |

---

## 6. Criar release estável

O archetype está em `0.0.1-SNAPSHOT`. Definir e publicar a primeira versão estável.

```shell
mvn release:prepare   # define versão, cria tag git
mvn release:perform   # publica no repositório Maven
```
