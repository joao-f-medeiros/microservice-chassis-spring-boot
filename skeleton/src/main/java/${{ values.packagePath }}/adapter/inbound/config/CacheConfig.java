package {{ values.groupId }}.{{ values.packageName }}.adapter.inbound.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@EnableCaching
@Configuration
public class CacheConfig {

}
