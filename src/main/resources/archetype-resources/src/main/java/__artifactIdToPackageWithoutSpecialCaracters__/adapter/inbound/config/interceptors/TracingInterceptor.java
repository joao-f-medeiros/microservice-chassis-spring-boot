package ${groupId}.${artifactIdToPackageImport}.adapter.inbound.config.interceptors;

import ${groupId}.${artifactIdToPackageImport}.core.config.components.MultiTenantContext;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import io.opentracing.Span;
import io.opentracing.util.GlobalTracer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
public class TracingInterceptor implements HandlerInterceptor {

  private static final String CURRENT_TENANT_ID = "current_tenant_id";
  private static final String CURRENT_TENANT_NAME = "current_tenant_name";

  private final ObservationRegistry observationRegistry;

  @Override
  public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
    Observation currentObservation = observationRegistry.getCurrentObservation();
    if(Objects.nonNull(currentObservation)) {
      currentObservation.lowCardinalityKeyValue(CURRENT_TENANT_ID, Optional.ofNullable(MultiTenantContext.getCurrentTenant()).map(Object::toString).orElse(""));
      currentObservation.lowCardinalityKeyValue(CURRENT_TENANT_NAME, Optional.ofNullable(MultiTenantContext.getCurrentTenantName()).orElse(""));

      final Span span = GlobalTracer.get().activeSpan();
      if (span != null) {
        span.setTag(CURRENT_TENANT_ID, Optional.ofNullable(MultiTenantContext.getCurrentTenant()).map(Object::toString).orElse(""));
        span.setTag(CURRENT_TENANT_NAME, Optional.ofNullable(MultiTenantContext.getCurrentTenantName()).orElse(""));
      }
    }

    return true;
  }

}
