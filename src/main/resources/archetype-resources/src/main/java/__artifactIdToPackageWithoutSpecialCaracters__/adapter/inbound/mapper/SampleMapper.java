package ${groupId}.${artifactIdToPackageImport}.adapter.inbound.mapper;

import ${groupId}.${artifactIdToPackageImport}.core.domain.entities.Sample;
import ${groupId}.${artifactIdToPackageImport}.adapter.inbound.dto.SampleResponse;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.extensions.spring.AdapterMethodName;
import org.mapstruct.extensions.spring.DelegatingConverter;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
@AdapterMethodName("convert")
public interface SampleMapper extends Converter<Sample, SampleResponse>{

  @Mapping(source = "description", target = "customDescription")
  SampleResponse convert(Sample sample);

  @InheritInverseConfiguration
  @DelegatingConverter
  @Mapping(source = "customDescription", target = "description")
  Sample invertConvert(SampleResponse sampleResponse);

}

