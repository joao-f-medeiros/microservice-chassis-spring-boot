package ${groupId}.${artifactIdToPackageImport}.infrastructure;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
  
  @Value("${info.app.name}")
  private String name;

  @Value("${info.app.description}")
  private String description;

  @Value("${info.app.version}")
  private String version;

  @Bean
  public OpenAPI springOpenAPI() {
    return new OpenAPI()
      .info(new Info().title(name)
        .description(description)
        .version(version));
  }
}
