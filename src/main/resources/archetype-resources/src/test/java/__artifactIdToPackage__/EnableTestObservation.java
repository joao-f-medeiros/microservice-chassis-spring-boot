package ${groupId}.${artifactIdToPackageImport};

import ${groupId}.${artifactIdToPackageImport}.core.config.components.ObservationCustomHandler;

import io.micrometer.observation.tck.TestObservationRegistry;
import org.springframework.boot.test.autoconfigure.actuate.observability.AutoConfigureObservability;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import({
  ObservationCustomHandler.class,
  EnableTestObservation.ObservationTestConfiguration.class
})
@AutoConfigureObservability
public @interface EnableTestObservation {

  @TestConfiguration
  class ObservationTestConfiguration {

    @Bean
    TestObservationRegistry observationRegistry() {
      return TestObservationRegistry.create();
    }
  }
}
