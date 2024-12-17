package ${groupId}.${artifactIdToPackageImport}.core.domain.services;

import ${groupId}.${artifactIdToPackageImport}.core.domain.entities.Sample;
import ${groupId}.${artifactIdToPackageImport}.core.domain.repositories.SampleRepository;
import ${groupId}.${artifactIdToPackageImport}.core.domain.integrations.SampleIntegration;
import io.micrometer.observation.annotation.Observed;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Observed(name = "SampleService")
public class SampleService {

  private final SampleRepository sampleRepository;

  private final SampleIntegration sampleIntegration;

  public Sample execute(Integer id) {
    String field = "BBAS3";
    String result = sampleIntegration.searchByField(field);
    Sample sample = sampleRepository.getSample(id);
    sample.setDescription(result.substring(result.indexOf(field), result.indexOf(field)+5));
    return sample;
  }

  public Sample executeAsync(Integer id) {
    try {
      Thread.sleep(5000);
    } catch (InterruptedException ignore) {
    }
    return execute(id);
  }

}