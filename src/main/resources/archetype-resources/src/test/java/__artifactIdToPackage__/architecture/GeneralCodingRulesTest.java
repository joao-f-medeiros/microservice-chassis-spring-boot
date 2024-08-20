package ${groupId}.${artifactIdToPackageImport}.architecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.GeneralCodingRules;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = "${groupId}")
public class GeneralCodingRulesTest {

  @ArchTest
  public static final ArchRule noClassesShouldUseStandardStreams = noClasses()
    .should(GeneralCodingRules.ACCESS_STANDARD_STREAMS);

  @ArchTest
  public static final ArchRule noClassesShouldUseStandardLogging = noClasses()
    .should(GeneralCodingRules.USE_JAVA_UTIL_LOGGING);

}
