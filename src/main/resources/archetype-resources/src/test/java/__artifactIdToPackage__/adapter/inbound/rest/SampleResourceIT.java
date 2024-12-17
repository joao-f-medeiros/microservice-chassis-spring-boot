package ${groupId}.${artifactIdToPackageImport}.adapter.inbound.rest;

import ${groupId}.${artifactIdToPackageImport}.EnableTestObservation;
import ${groupId}.${artifactIdToPackageImport}.core.config.exceptions.NotFoundException;
import ${groupId}.${artifactIdToPackageImport}.core.domain.integrations.SampleIntegration;
import io.micrometer.observation.tck.TestObservationRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

import static io.micrometer.observation.tck.TestObservationRegistryAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@EnableAutoConfiguration
@EnableTestObservation
class SampleResourceIT {

  private final MockMvc mockMvc;

  private final TestObservationRegistry registry;

  @MockBean
  private SampleIntegration sampleIntegration;

  @Autowired
  public SampleResourceIT(MockMvc mockMvc, TestObservationRegistry testObservationRegistry) {
    this.mockMvc = mockMvc;
    this.registry = testObservationRegistry;
  }

  @BeforeEach
  void setUp() {
    registry.clear();
  }

  @Test
  void getSample_observabilityExecution() throws Exception {
    when(sampleIntegration.searchByField(anyString())).thenReturn("BBAS3");

    mockMvc.perform(get("/sample/{id}", 1)
        .header("tenantId", "1")
        .header("tenantName", "principal")
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.custom_description", is("BBAS3")));

    assertThat(registry)
      .doesNotHaveAnyRemainingCurrentObservation()
      .hasNumberOfObservationsEqualTo(4)
      .hasObservationWithNameEqualTo("SampleRepository")
      .that()
      .hasLowCardinalityKeyValue("current_tenant_id", "1")
      .hasLowCardinalityKeyValue("current_tenant_name", "principal")
      .hasBeenStarted()
      .hasBeenStopped();
  }

  @Test
  void getSample_WithId1() throws Exception {
    when(sampleIntegration.searchByField(anyString())).thenReturn("BBAS3");

    mockMvc.perform(get("/sample/{id}", 1)
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.custom_description", is("BBAS3")));
  }

  @Test
  void getSample_WithId2() throws Exception {
    when(sampleIntegration.searchByField(anyString())).thenReturn("BBAS3");

    mockMvc.perform(get("/sample/{id}", 2)
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.custom_description", is("BBAS3")));
  }

  @Test
  void getSample_NotFound() throws Exception {
    mockMvc.perform(get("/sample/{id}", 3)
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isNotFound())
      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON))
      .andExpect(jsonPath("$.status", is(404)));
  }

  @Test
  void getSample_withCircuitBreaker() throws Exception {
    doThrow(NotFoundException.class).when(sampleIntegration).searchByField(anyString());

    IntStream.rangeClosed(1, 5).forEach(i -> {
      try {
        mockMvc.perform(get("/sample/{id}/circuit-breaker", 3)
          .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isNotFound())
          .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON));
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    });
    mockMvc.perform(get("/sample/{id}/circuit-breaker", 3)
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isServiceUnavailable())
      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON));
  }

  @Test
  void getSample_withRetry() throws Exception {
    doThrow(NotFoundException.class).when(sampleIntegration).searchByField(anyString());

    mockMvc.perform(get("/sample/{id}/retry", 3)
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

    verify(sampleIntegration, times(3)).searchByField(anyString());
  }

  @Test
  void getSample_withBulkhead() {
    when(sampleIntegration.searchByField(anyString())).thenReturn("BBAS3");

    Map<Integer, Integer> responseStatusCount = new ConcurrentHashMap<>();

    IntStream.rangeClosed(1, 20)
      .parallel()
      .forEach(i -> {
        try {
          MvcResult mvcResult = mockMvc.perform(get("/sample/{id}/bulkhead", 1)
            .contentType(MediaType.APPLICATION_JSON))
            .andReturn();
          int status = mvcResult.getResponse().getStatus();
          responseStatusCount.put(status, responseStatusCount.getOrDefault(status, 0) + 1);
        } catch (Exception e) {
          throw new RuntimeException(e);
        }
      });

    assertEquals(2, responseStatusCount.keySet().size());
    assertTrue(responseStatusCount.containsKey(BANDWIDTH_LIMIT_EXCEEDED.value()));
    assertTrue(responseStatusCount.containsKey(OK.value()));
  }

  @Test
  void getSample_withTimeLimiter() throws Exception {
    MvcResult mvcResult = mockMvc.perform(get("/sample/{id}/time-limiter", 3)
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(request().asyncStarted())
      .andDo(MockMvcResultHandlers.log())
      .andReturn();

    mockMvc.perform(asyncDispatch(mvcResult))
      .andExpect(status().isRequestTimeout())
      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON));
  }

  @Test
  void getSample_withRateLimit() {
    when(sampleIntegration.searchByField(anyString())).thenReturn("BBAS3");

    Map<Integer, Integer> responseStatusCount = new ConcurrentHashMap<>();

    IntStream.rangeClosed(1, 50)
      .parallel()
      .forEach(i -> {
        try {
          MvcResult mvcResult = mockMvc.perform(get("/sample/{id}/rate-limiter", 1)
              .contentType(MediaType.APPLICATION_JSON))
            .andReturn();
          int status = mvcResult.getResponse().getStatus();
          responseStatusCount.put(status, responseStatusCount.getOrDefault(status, 0) + 1);
        } catch (Exception e) {
          throw new RuntimeException(e);
        }
      });

    assertEquals(2, responseStatusCount.keySet().size());
    assertTrue(responseStatusCount.containsKey(TOO_MANY_REQUESTS.value()));
    assertTrue(responseStatusCount.containsKey(OK.value()));
  }

}