package ${groupId}.${artifactIdToPackageImport}.adapter.inbound.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SampleResponse {

  private String customDescription;

  private SampleResponse child;
}