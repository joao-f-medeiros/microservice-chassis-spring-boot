package ${groupId}.${artifactIdToPackageImport}.infrastructure.components;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MultiTenantContext {

  private static final ThreadLocal<Long> currentTenant = new ThreadLocal<>();
  private static final ThreadLocal<String> currentTenantName = new ThreadLocal<>();

  public static void clear() {
    currentTenant.remove();
    currentTenantName.remove();
  }

  public static Long getCurrentTenant() {
    return currentTenant.get();
  }

  public static void setCurrentTenant(Long tenant) {
    currentTenant.set(tenant);
  }

  public static String getCurrentTenantName() {
    return currentTenantName.get();
  }

  public static void setCurrentTenantName(String tenant) {
    currentTenantName.set(tenant);
  }

}
