package ${groupId}.${artifactIdToPackageImport}.core.config;

import io.micrometer.core.instrument.config.MeterFilter;
import io.micrometer.datadog.DatadogMeterRegistry;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnBean(DatadogMeterRegistry.class)
public class MetricsConfig {

  @Bean
  InitializingBean forceDatadogPostProcessor(BeanPostProcessor meterRegistryPostProcessor, DatadogMeterRegistry registry) {
    return () -> meterRegistryPostProcessor.postProcessAfterInitialization(registry, "");
  }

  @Bean
  public MeterRegistryCustomizer<DatadogMeterRegistry> metricsFilter() {
    return registry -> registry.config()
      .meterFilter(MeterFilter.denyNameStartsWith("http.server"))
      .meterFilter(MeterFilter.denyNameStartsWith("spring.data.repository.invocations"))
      .meterFilter(MeterFilter.denyNameStartsWith("spring.integration.send"))
      .meterFilter(MeterFilter.denyNameStartsWith("lettuce.command"))
      .meterFilter(MeterFilter.denyNameStartsWith("logback.events"))
      .meterFilter(MeterFilter.denyNameStartsWith("executor"))
      .meterFilter(MeterFilter.denyNameStartsWith("ZUUL_EXCEPTION"));
  }

}
