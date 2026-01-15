package edu.ucsb.cs156.spooler.startup;

import edu.ucsb.cs156.spooler.jobs.TestJob;
import edu.ucsb.cs156.spooler.services.jobs.JobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/** This class contains a `run` method that is called once at application startup time. */
@Slf4j
@Component
public class CoursesStartup {

  @Autowired private JobService jobService;

  /**
   * Called once at application startup time . Put code here if you want it to run once each time
   * the Spring Boot application starts up in all environments.
   */
  public void alwaysRunOnStartup() {
    log.info("alwaysRunOnStartup called");

    TestJob testJob = TestJob.builder().fail(false).sleepMs(1000).build();
    jobService.runAsJob(testJob);

    log.info("alwaysRunOnStartup: launched testJob");
  }

  /**
   * Called once at application startup time . Put code here if you want it to run once each time
   * the Spring Boot application starts up but only in production.
   */
  public void runOnStartupInProductionOnly() {
    log.info("runOnStartupInProductionOnly called");

    TestJob testJob = TestJob.builder().fail(false).sleepMs(1000).build();
    jobService.runAsJob(testJob);

    log.info("runOnStartupInProductionOnly: launched testJob");
  }
}
