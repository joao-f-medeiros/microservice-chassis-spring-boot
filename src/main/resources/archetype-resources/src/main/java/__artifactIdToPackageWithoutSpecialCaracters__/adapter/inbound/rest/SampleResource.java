package ${groupId}.${artifactIdToPackageImport}.adapter.inbound.rest;

import ${groupId}.${artifactIdToPackageImport}.adapter.ConversionServiceAdapter;
import ${groupId}.${artifactIdToPackageImport}.adapter.inbound.dto.SampleResponse;
import ${groupId}.${artifactIdToPackageImport}.core.domain.entities.Sample;
import ${groupId}.${artifactIdToPackageImport}.core.domain.services.SampleService;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/sample")
@RequiredArgsConstructor
@Observed(name = "SampleResource")
public class SampleResource {

  private final SampleService sampleService;

  private final ConversionService conversionService;

  private final ConversionServiceAdapter conversionServiceAdapter;

  @GetMapping("/{id}")
  public SampleResponse getSample(@PathVariable Integer id) {
    var sample = sampleService.execute(id);
    SampleResponse sampleResponse = conversionServiceAdapter.convert(sample);
    conversionServiceAdapter.mapSampleResponseToSample(sampleResponse);
    conversionService.convert(sampleResponse, Sample.class);
    return conversionService.convert(sample, SampleResponse.class);
  }

  @GetMapping("/{id}/circuit-breaker")
  @CircuitBreaker(name = "CircuitBreakerService")
  public SampleResponse circuitBreakerSample(@PathVariable Integer id) {
    return getSampleResponse(id);
  }

  @GetMapping("/{id}/retry")
  @Retry(name = "retryApi", fallbackMethod = "fallbackAfterRetry")
  public SampleResponse retrySample(@PathVariable Integer id) {
    return getSampleResponse(id);
  }

  @GetMapping("/{id}/time-limiter")
  @TimeLimiter(name = "timeLimiterApi")
  public CompletableFuture<SampleResponse> timeLimiterSample(@PathVariable Integer id) {
    return CompletableFuture.supplyAsync(() -> {
      Sample sample = sampleService.executeAsync(id);
      return conversionService.convert(sample, SampleResponse.class);
    });
  }

  @GetMapping("/{id}/bulkhead")
  @Bulkhead(name = "bulkheadApi")
  public SampleResponse bulkheadSample(@PathVariable Integer id) {
    return getSampleResponse(id);
  }

  @GetMapping("/{id}/rate-limiter")
  @RateLimiter(name = "rateLimiterApi")
  public SampleResponse rateLimitSample(@PathVariable Integer id) {
    return getSampleResponse(id);
  }

  private SampleResponse getSampleResponse(Integer id) {
    Sample sample = sampleService.execute(id);
    return conversionService.convert(sample, SampleResponse.class);
  }

  private SampleResponse fallbackAfterRetry(Integer id, Throwable ex) {
    return new SampleResponse("Fallback Sample", null);
  }
}