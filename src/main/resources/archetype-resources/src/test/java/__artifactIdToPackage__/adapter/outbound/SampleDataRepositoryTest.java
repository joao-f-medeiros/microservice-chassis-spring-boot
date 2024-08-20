package ${groupId}.${artifactIdToPackageImport}.adapter.outbound;

import ${groupId}.${artifactIdToPackageImport}.domain.entities.Sample;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SampleDataRepositoryTest {

  @Test
  void shouldReturnFirstExample() {
    SampleDataRepository sampleDataSource = new SampleDataRepository();
    Sample sample = sampleDataSource.getSample(1);
    assertEquals("First Example", sample.getDescription());
  }

  @Test
  void shouldReturnSecondExample() {
    SampleDataRepository sampleDataSource = new SampleDataRepository();
    Sample sample = sampleDataSource.getSample(2);
    assertEquals("Second Example", sample.getDescription());
  }

}