package ${groupId}.${artifactIdToPackageImport}.adapter.outbound;

import ${groupId}.${artifactIdToPackageImport}.core.domain.entities.Sample;
import ${groupId}.${artifactIdToPackageImport}.core.config.exceptions.NotFoundException;
import ${groupId}.${artifactIdToPackageImport}.core.domain.repositories.SampleRepository;
import io.micrometer.observation.annotation.Observed;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;

@Repository
@Observed(name = "SampleRepository")
public class SampleDataRepository implements SampleRepository {

  private static final Set<Integer> validValues = new HashSet<>(asList(1, 2));

  @Override
  public Sample getSample(Integer id) {
    if (!validValues.contains(id)) {
      throw new NotFoundException("Sample not found");
    }

    Sample child = new Sample("Child");

    if (id.equals(1)) {
      Sample sample = new Sample("First Example");
      sample.setChild(child);
      return sample;
    }
    return new Sample("Second Example");
  }

}
