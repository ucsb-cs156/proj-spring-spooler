package edu.ucsb.cs156.spooler.config;

import java.time.ZonedDateTime;
import java.util.Optional;
import org.springframework.context.annotation.Bean;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing(dateTimeProviderRef = "utcDateTimeProvider")
public class JpaAuditingConfig {

  @Bean
  public DateTimeProvider utcDateTimeProvider() {
    return () -> Optional.of(ZonedDateTime.now());
  }
}
