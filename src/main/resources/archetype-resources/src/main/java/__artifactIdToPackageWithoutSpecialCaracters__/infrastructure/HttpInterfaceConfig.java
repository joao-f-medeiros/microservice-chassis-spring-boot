package ${groupId}.${artifactIdToPackageImport}.infrastructure;

import ${groupId}.${artifactIdToPackageImport}.adapter.outbound.SampleRequestIntegration;
import io.micrometer.observation.ObservationRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Slf4j
@Configuration
public class HttpInterfaceConfig {

  @Bean
  HttpServiceProxyFactory getHttpServiceProxyFactory(ObservationRegistry observationRegistry) {
    var restClient = RestClient.builder().observationRegistry(observationRegistry).build();
    var adapter = RestClientAdapter.create(restClient);
    return HttpServiceProxyFactory.builderFor(adapter).build();
  }

  @Bean
  SampleRequestIntegration getSampleRequestIntegration(HttpServiceProxyFactory httpServiceProxyFactory) {
    return httpServiceProxyFactory.createClient(SampleRequestIntegration.class);
  }

}
