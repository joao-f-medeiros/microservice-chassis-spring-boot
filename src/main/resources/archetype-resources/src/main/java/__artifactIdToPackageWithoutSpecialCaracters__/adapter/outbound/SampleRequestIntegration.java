package ${groupId}.${artifactIdToPackageImport}.adapter.outbound;

import ${groupId}.${artifactIdToPackageImport}.domain.integrations.SampleIntegration;
import io.micrometer.observation.annotation.Observed;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@Observed(name = "SampleIntegration")
@HttpExchange(accept = MediaType.APPLICATION_JSON_VALUE, url = "https://www.google.com/finance/quote")
public interface SampleRequestIntegration extends SampleIntegration {

  @Override
  @GetExchange("/{code}:BVMF?hl=pt")
  String searchByField(@PathVariable String code);
}