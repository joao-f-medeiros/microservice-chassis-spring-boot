package {{ values.groupId }}.{{ values.packageName }}.architecture;

public abstract class ArchitectureTest {

  static final String DOMAIN_LAYER_PACKAGES = "{{ values.groupId }}.{{ values.packageName }}.core.domain..";
  static final String CORE_CONFIG_LAYER_PACKAGES = "{{ values.groupId }}.{{ values.packageName }}.core.config..";
  static final String ADAPTER_CONFIG_LAYER_PACKAGES = "{{ values.groupId }}.{{ values.packageName }}.adapter.config..";
  static final String ADAPTER_INBOUND_CONFIG_LAYER_PACKAGES = "{{ values.groupId }}.{{ values.packageName }}.adapter.inbound.config..";
  static final String ADAPTER_OUTBOUND_CONFIG_LAYER_PACKAGES = "{{ values.groupId }}.{{ values.packageName }}.adapter.outbound.config..";
  static final String ADAPTERS_LAYER_PACKAGES = "{{ values.groupId }}.{{ values.packageName }}.adapter..";
  static final String INBOUND_ADAPTERS_PACKAGES = "{{ values.groupId }}.{{ values.packageName }}.adapter.inbound..";
  static final String OUTBOUND_ADAPTERS_PACKAGES = "{{ values.groupId }}.{{ values.packageName }}.adapter.outbound..";
  static final String ENTITIES_DOMAIN_PACKAGES = "{{ values.groupId }}.{{ values.packageName }}.core.domain.entities..";
  static final String SERVICES_DOMAIN_PACKAGES = "{{ values.groupId }}.{{ values.packageName }}.core.domain.services..";
  static final String REPOSITORIES_DOMAIN_PACKAGES = "{{ values.groupId }}.{{ values.packageName }}.core.domain.repositories..";
  static final String INTEGRATIONS_DOMAIN_PACKAGES = "{{ values.groupId }}.{{ values.packageName }}.core.domain.integrations..";

}
