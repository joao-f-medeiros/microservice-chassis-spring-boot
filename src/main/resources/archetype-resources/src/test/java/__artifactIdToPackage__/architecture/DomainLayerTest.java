package ${groupId}.${artifactIdToPackageImport}.architecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = "${groupId}")
public class DomainLayerTest extends ArchitectureTest {

  @ArchTest
  public static final ArchRule noDomainUsesValidator = noClasses()
    .that()
    .resideInAPackage(DOMAIN_LAYER_PACKAGES)
    .should()
    .dependOnClassesThat()
    .resideInAnyPackage(ADAPTERS_LAYER_PACKAGES, INFRASTRUCTURE_LAYER_PACKAGES);

  @ArchTest
  public static final ArchRule noDomainUsedByAllowedLayers = classes()
    .that()
    .resideInAPackage(DOMAIN_LAYER_PACKAGES)
    .should().onlyBeAccessed()
    .byAnyPackage(DOMAIN_LAYER_PACKAGES, ADAPTERS_LAYER_PACKAGES);
}
