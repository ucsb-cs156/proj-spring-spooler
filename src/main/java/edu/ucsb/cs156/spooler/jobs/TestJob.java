package edu.ucsb.cs156.spooler.jobs;

import edu.ucsb.cs156.spooler.services.jobs.JobContext;
import edu.ucsb.cs156.spooler.services.jobs.JobContextConsumer;
import edu.ucsb.cs156.spooler.services.jobs.Sleeper;
import java.util.concurrent.TimeUnit;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Builder
public class TestJob implements JobContextConsumer {

  private boolean fail;
  private int sleepMs;

  @Override
  public void accept(JobContext ctx) throws Exception {
    // Ensure this is not null
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    ctx.log("Hello World! from test job!");
    if (authentication == null) {
      ctx.log("authentication is null");
    } else {
      ctx.log("authentication is not null");
    }
    Sleeper.sleep(sleepMs);
    if (fail) {
      throw new Exception("Fail!");
    }
    ctx.log("Goodbye from test job!");
  }
}
