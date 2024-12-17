package ${groupId}.${artifactIdToPackageImport}.core.domain.entities;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class Sample {

  private int id;

  @NonNull
  @Setter
  private String description;

  @Setter
  private Sample child;

}