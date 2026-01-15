package edu.ucsb.cs156.spooler.services.jobs;

import edu.ucsb.cs156.spooler.entities.Job;
import edu.ucsb.cs156.spooler.repositories.JobsRepository;
import org.springframework.stereotype.Component;

@Component
public class JobContextFactory {
  private final JobsRepository repository;

  public JobContextFactory(JobsRepository repository) {
    this.repository = repository;
  }

  public JobContext createContext(Job job) {
    return new JobContext(repository, job);
  }
}
