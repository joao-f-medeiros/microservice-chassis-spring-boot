package ${groupId}.${artifactIdToPackageImport}.infrastructure;

import org.mapstruct.MapperConfig;
import org.mapstruct.extensions.spring.SpringMapperConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperConfig(componentModel = "spring")
@SpringMapperConfig(conversionServiceAdapterPackage = "${groupId}.${artifactIdToPackageImport}.adapter")
public class MapperSpringConfig {
}
