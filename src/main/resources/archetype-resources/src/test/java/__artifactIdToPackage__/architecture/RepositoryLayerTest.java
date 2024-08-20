package ${groupId}.${artifactIdToPackageImport}.architecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.springframework.stereotype.Repository;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = "${groupId}")
public class RepositoryLayerTest extends ArchitectureTest {

  @ArchTest
  public static final ArchRule implementsRepository = noClasses()
    .that()
    .resideInAPackage(SERVICES_DOMAIN_PACKAGES)
    .and()
    .resideInAPackage(REPOSITORIES_DOMAIN_PACKAGES)
    .should().beAnnotatedWith(Repository.class);
}
