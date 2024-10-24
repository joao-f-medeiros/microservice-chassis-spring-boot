package ${groupId}.${artifactIdToPackageImport}.infrastructure;

import ${groupId}.${artifactIdToPackageImport}.infrastructure.interceptors.MultiTenantInterceptor;
import ${groupId}.${artifactIdToPackageImport}.infrastructure.interceptors.TracingInterceptor;
import io.micrometer.observation.ObservationRegistry;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@AllArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

  private final ObservationRegistry observationRegistry;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new MultiTenantInterceptor()).order(Ordered.HIGHEST_PRECEDENCE);
    registry.addInterceptor(new TracingInterceptor(observationRegistry)).order(Ordered.LOWEST_PRECEDENCE);
  }

}
