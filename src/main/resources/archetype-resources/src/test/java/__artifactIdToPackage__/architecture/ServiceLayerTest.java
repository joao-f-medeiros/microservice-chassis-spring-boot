package ${groupId}.${artifactIdToPackageImport}.architecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.springframework.stereotype.Service;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses(packages = "${groupId}")
public class ServiceLayerTest extends ArchitectureTest {

  @ArchTest
  public static final ArchRule serviceClassesShouldHaveServiceAnnotation = classes()
    .that()
    .resideInAPackage(SERVICES_DOMAIN_PACKAGES)
    .and()
    .haveSimpleNameEndingWith("Service")
    .should()
    .beAnnotatedWith(Service.class);

}
