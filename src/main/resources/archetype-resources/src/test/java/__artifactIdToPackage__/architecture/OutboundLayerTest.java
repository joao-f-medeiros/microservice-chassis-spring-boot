package ${groupId}.${artifactIdToPackageImport}.architecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.springframework.stereotype.Repository;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = "${groupId}")
public class OutboundLayerTest extends ArchitectureTest{

  @ArchTest
  public static final ArchRule repositoryClassesShouldBeAnnotatedWithRepositoryAnnotation = classes()
    .that()
    .resideInAPackage(OUTBOUND_ADAPTERS_PACKAGES)
    .and()
    .haveSimpleNameEndingWith("Repository")
    .and()
    .areNotInterfaces()
    .should()
    .beAnnotatedWith(Repository.class);

  @ArchTest
  public static final ArchRule noClassesWithRepositoryAnnotationShouldResideOutsideOfOutboundAdaptersPackages = noClasses()
    .that()
    .resideInAPackage(OUTBOUND_ADAPTERS_PACKAGES)
    .and()
    .areAnnotatedWith(Repository.class)
    .should()
    .resideOutsideOfPackage(OUTBOUND_ADAPTERS_PACKAGES);
}
