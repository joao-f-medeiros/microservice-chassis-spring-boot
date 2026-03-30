package {{ values.groupId }}.{{ values.packageName }}.core.domain.repositories;

import {{ values.groupId }}.{{ values.packageName }}.core.domain.entities.Sample;

public interface SampleRepository {

  Sample getSample(Integer id);

}
