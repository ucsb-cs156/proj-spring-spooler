package edu.ucsb.cs156.spooler.repositories;

import edu.ucsb.cs156.spooler.entities.Job;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobsRepository extends JpaRepository<Job, Long> {

  // Get all jobs sorted by id in descending order
  List<Job> findAllByOrderByIdDesc();
}
