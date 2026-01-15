package edu.ucsb.cs156.spooler.integration;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.ucsb.cs156.spooler.entities.Job;
import edu.ucsb.cs156.spooler.repositories.JobsRepository;
import edu.ucsb.cs156.spooler.services.jobs.JobContextFactory;
import edu.ucsb.cs156.spooler.services.jobs.JobService;
import edu.ucsb.cs156.spooler.testconfig.TestConfig;
import edu.ucsb.cs156.spooler.testconfig.TestJob;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
@Import(TestConfig.class)
public class AsyncJobTestsIT {

  @Autowired
  private JobService jobService;

  @Autowired
  private JobContextFactory contextFactory;

  @MockitoBean
  private JobsRepository jobsRepository;

  @Test
  void async_job_actually_runs_asynchronously(){
    TestJob testJob = TestJob.builder().fail(false).sleepMs(2000).build();
    Job job = jobService.runAsJob(testJob);
    assertEquals("running", job.getStatus(), "Job should be running");
    await()
        .atMost(10, TimeUnit.SECONDS)
        .untilAsserted(() -> assertEquals("complete", job.getStatus(), "Job should be complete"));
  }
}
