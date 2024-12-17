package ${groupId}.${artifactIdToPackageImport}.architecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

@AnalyzeClasses(packages = "${groupId}")
public class HexagonalTest extends ArchitectureTest {

  public static final String DOMAIN_LAYER = "Domain";
  public static final String ADAPTER_LAYER = "Adapter";
  public static final String ENTITIES_LAYER = "Entities";
  public static final String SERVICES_LAYER = "Services";
  public static final String REPOSITORIES_LAYER = "Repositories";
  public static final String INTEGRATIONS_LAYER = "Integrations";
  public static final String OUTBOUND_LAYER = "Outbound";
  public static final String INBOUND_LAYER = "Inbound";
  public static final String CONFIG_LAYER = "Config";

  @ArchTest
  public static final ArchRule layersValidator = layeredArchitecture()
    .layer(DOMAIN_LAYER).definedBy(DOMAIN_LAYER_PACKAGES)
    .layer(ADAPTER_LAYER).definedBy(ADAPTERS_LAYER_PACKAGES)
    .layer(ENTITIES_LAYER).definedBy(ENTITIES_DOMAIN_PACKAGES)
    .layer(SERVICES_LAYER).definedBy(SERVICES_DOMAIN_PACKAGES)
    .layer(REPOSITORIES_LAYER).definedBy(REPOSITORIES_DOMAIN_PACKAGES)
    .layer(INTEGRATIONS_LAYER).definedBy(INTEGRATIONS_DOMAIN_PACKAGES)
    .layer(OUTBOUND_LAYER).definedBy(OUTBOUND_ADAPTERS_PACKAGES)
    .layer(INBOUND_LAYER).definedBy(INBOUND_ADAPTERS_PACKAGES)
    .layer(CONFIG_LAYER).definedBy(CORE_CONFIG_LAYER_PACKAGES)

    .whereLayer(SERVICES_LAYER).mayOnlyBeAccessedByLayers(INBOUND_LAYER, OUTBOUND_LAYER)
    .whereLayer(ENTITIES_LAYER).mayOnlyBeAccessedByLayers(ADAPTER_LAYER, DOMAIN_LAYER)
    .whereLayer(REPOSITORIES_LAYER).mayOnlyBeAccessedByLayers(SERVICES_LAYER, OUTBOUND_LAYER)
    .whereLayer(OUTBOUND_LAYER).mayOnlyBeAccessedByLayers(ADAPTER_LAYER, CONFIG_LAYER)
    .whereLayer(INBOUND_LAYER).mayOnlyBeAccessedByLayers(ADAPTER_LAYER);

}
