package ${groupId}.${artifactIdToPackageImport}.architecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;

@AnalyzeClasses(packages = "${groupId}")
public class InboundLayerTest extends ArchitectureTest {

  @ArchTest
  public static final ArchRule resourceClassesShouldBeAnnotatedWithControllerOrRestControllerAnnotation = classes()
    .that()
    .resideInAPackage(DOMAIN_LAYER_PACKAGES)
    .and()
    .haveSimpleNameEndingWith("Resource")
    .should()
    .beAnnotatedWith(Controller.class)
    .orShould()
    .beAnnotatedWith(RestController.class);

  @ArchTest
  public static final ArchRule noClassesWithControllerOrRestControllerAnnotationShouldResideOutsideOfInboundAdaptersPackages = noClasses()
    .that().areAnnotatedWith(Controller.class)
    .or().areAnnotatedWith(RestController.class)
    .should().resideOutsideOfPackage(INBOUND_ADAPTERS_PACKAGES);

  @ArchTest
  public static final ArchRule resourceClassesShouldNotDependOnEachOther = noClasses()
    .that()
    .resideInAPackage(INBOUND_ADAPTERS_PACKAGES)
    .and()
    .haveSimpleNameEndingWith("Resource")
    .should().dependOnClassesThat()
    .haveSimpleNameEndingWith("Resource");

  @ArchTest
  public static final ArchRule publicControllerMethodsShouldBeAnnotatedWithAValidAnnotation = methods()
      .that().arePublic()
      .and().areDeclaredInClassesThat().resideInAPackage(INBOUND_ADAPTERS_PACKAGES)
      .and().areDeclaredInClassesThat().haveSimpleNameEndingWith("Resource")
      .and().areDeclaredInClassesThat().areAnnotatedWith(RestController.class)
      .should().beAnnotatedWith(RequestMapping.class)
      .orShould().beAnnotatedWith(GetMapping.class)
      .orShould().beAnnotatedWith(PostMapping.class)
      .orShould().beAnnotatedWith(PatchMapping.class)
      .orShould().beAnnotatedWith(DeleteMapping.class);

}
