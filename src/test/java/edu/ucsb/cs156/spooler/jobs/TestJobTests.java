package edu.ucsb.cs156.spooler.jobs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;

import edu.ucsb.cs156.spooler.entities.Job;
import edu.ucsb.cs156.spooler.services.jobs.JobContext;
import edu.ucsb.cs156.spooler.services.jobs.Sleeper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
public class TestJobTests {

  @Test
  void test_log_output_with_no_user() throws Exception {

    // Arrange

    Job jobStarted = Job.builder().build();

    JobContext ctx = new JobContext(null, jobStarted);

    // Act
    TestJob testJob = TestJob.builder().sleepMs(0).fail(false).build();
    testJob.accept(ctx);

    String expected =
        """
            Hello World! from test job!
            authentication is null
            Goodbye from test job!""";
    // Assert
    assertEquals(expected, jobStarted.getLog());
  }

  @Test
  @WithMockUser(roles = {"ADMIN"})
  void test_log_output_with_mock_user() throws Exception {
    // Arrange

    Job jobStarted = Job.builder().build();
    JobContext ctx = new JobContext(null, jobStarted);

    // Act
    TestJob testJob = TestJob.builder().sleepMs(0).fail(false).build();
    testJob.accept(ctx);

    String expected =
        """
                Hello World! from test job!
                authentication is not null
                Goodbye from test job!""";
    // Assert
    assertEquals(expected, jobStarted.getLog());
  }

  @Test
  void callsSleep() throws Exception {
    try (MockedStatic<Sleeper> mock = Mockito.mockStatic(Sleeper.class)) {
      mock.when(() -> Sleeper.sleep(anyLong())).thenAnswer(invocation -> null);
      TestJob testJob = TestJob.builder().sleepMs(1000).fail(false).build();
      testJob.accept(new JobContext(null, Job.builder().build()));
      mock.verify(() -> Sleeper.sleep(anyLong()), times(1));
    }
  }

  @Test
  void test_failing_job() throws Exception {
    TestJob testJob = TestJob.builder().sleepMs(0).fail(true).build();
    JobContext ctx = new JobContext(null, Job.builder().build());
    assertThrows(Exception.class, () -> testJob.accept(ctx));
  }
}
