package ${groupId}.${artifactIdToPackageImport}.infrastructure.interceptors;

import ${groupId}.${artifactIdToPackageImport}.infrastructure.components.MultiTenantContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class MultiTenantInterceptor implements HandlerInterceptor {

  private static final String TENANT_ID_HEADER = "tenantId";
  private static final String TENANT_NAME_HEADER = "tenantName";

  @Override
  public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
    String tenantId = request.getHeader(TENANT_ID_HEADER);
    if(ObjectUtils.isNotEmpty(tenantId)) {
      if(!StringUtils.isNumeric(tenantId)) {
        log.warn("Invalid tenant id - {}", tenantId);
        throw new IllegalArgumentException("Invalid tenant id - " + tenantId);
      }
      log.debug("Setting request tenant id - {}", tenantId);
      MultiTenantContext.setCurrentTenant(Long.valueOf(tenantId));
    }
    String tenantName = request.getHeader(TENANT_NAME_HEADER);
    if(ObjectUtils.isNotEmpty(tenantId)) {
      log.debug("Setting request tenant name - {}", tenantName);
      MultiTenantContext.setCurrentTenantName(tenantName);
    }
    return true;
  }

  @Override
  public void afterCompletion(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, Exception ex) {
    MultiTenantContext.clear();
  }
}
