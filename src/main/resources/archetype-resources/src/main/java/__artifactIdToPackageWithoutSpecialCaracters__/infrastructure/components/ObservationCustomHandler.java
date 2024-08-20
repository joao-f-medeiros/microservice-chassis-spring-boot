package ${groupId}.${artifactIdToPackageImport}.infrastructure.components;

import io.micrometer.common.KeyValue;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationHandler;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class ObservationCustomHandler implements ObservationHandler<Observation.Context> {

  private static final String CURRENT_TENANT_ID = "current_tenant_id";
  private static final String CURRENT_TENANT_NAME = "current_tenant_name";

  @Override
  public void onStart(Observation.Context context) {
    context.addLowCardinalityKeyValue(KeyValue.of(CURRENT_TENANT_ID, Optional.ofNullable(MultiTenantContext.getCurrentTenant()).map(Object::toString).orElse("")));
    context.addLowCardinalityKeyValue(KeyValue.of(CURRENT_TENANT_NAME, Optional.ofNullable(MultiTenantContext.getCurrentTenantName()).orElse("")));
  }

  @Override
  public boolean supportsContext(@NonNull Observation.Context context) {
    return Objects.isNull(context.getLowCardinalityKeyValue(CURRENT_TENANT_ID));
  }
}
