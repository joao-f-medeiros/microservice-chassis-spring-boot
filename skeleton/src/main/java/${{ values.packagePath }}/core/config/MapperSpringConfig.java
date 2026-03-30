package {{ values.groupId }}.{{ values.packageName }}.core.config;

import org.mapstruct.MapperConfig;
import org.mapstruct.extensions.spring.SpringMapperConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperConfig(componentModel = "spring")
@SpringMapperConfig(conversionServiceAdapterPackage = "{{ values.groupId }}.{{ values.packageName }}.adapter")
public class MapperSpringConfig {
}
