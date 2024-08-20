package ${groupId}.${artifactIdToPackageImport}.architecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.springframework.context.annotation.Configuration;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = "${groupId}")
public class InfrastructurePackageTest extends ArchitectureTest {

  @ArchTest
  public static final ArchRule configurationClassesShouldHaveConfigurationAnnotation = classes()
    .that()
    .resideInAPackage(DOMAIN_LAYER_PACKAGES)
    .and()
    .haveSimpleNameEndingWith("Config")
    .should()
    .beAnnotatedWith(Configuration.class);

  @ArchTest
  public static final ArchRule noConfigurationClassShouldResideOutsideOfInfrastructureLayer = noClasses()
    .that()
    .areAnnotatedWith(Configuration.class)
    .should()
    .resideOutsideOfPackage(INFRASTRUCTURE_LAYER_PACKAGES);
}