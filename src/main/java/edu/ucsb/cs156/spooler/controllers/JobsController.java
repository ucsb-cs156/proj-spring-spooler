package edu.ucsb.cs156.spooler.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ucsb.cs156.spooler.entities.Job;
import edu.ucsb.cs156.spooler.errors.EntityNotFoundException;
import edu.ucsb.cs156.spooler.repositories.JobsRepository;
import edu.ucsb.cs156.spooler.services.jobs.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Jobs")
@RequestMapping("/api/jobs")
@RestController
@Slf4j
public class JobsController extends ApiController {
  @Autowired private JobsRepository jobsRepository;

  @Autowired private JobService jobService;

  @Autowired ObjectMapper mapper;

  @Operation(summary = "List all jobs")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @GetMapping("/all")
  public Iterable<Job> allJobs() {
    Iterable<Job> jobs = jobsRepository.findAllByOrderByIdDesc();
    return jobs;
  }

  @Operation(summary = "Delete all job records")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @DeleteMapping("/all")
  public Map<String, String> deleteAllJobs() {
    jobsRepository.deleteAll();
    return Map.of("message", "All jobs deleted");
  }

  @Operation(summary = "Get a specific Job Log by ID if it is in the database")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @GetMapping("")
  public Job getJobLogById(
      @Parameter(name = "id", description = "ID of the job") @RequestParam Long id)
      throws JsonProcessingException {

    Job job =
        jobsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Job.class, id));

    return job;
  }

  @Operation(summary = "Delete specific job record")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @DeleteMapping("")
  public Map<String, String> deleteAllJobs(@Parameter(name = "id") @RequestParam Long id) {
    if (!jobsRepository.existsById(id)) {
      return Map.of("message", String.format("Job with id %d not found", id));
    }
    jobsRepository.deleteById(id);
    return Map.of("message", String.format("Job with id %d deleted", id));
  }

  @Operation(summary = "Get long job logs")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @GetMapping("/logs/{id}")
  public String getJobLogs(@Parameter(name = "id", description = "Job ID") @PathVariable Long id) {

    return jobService.getLongJob(id);
  }

  @Operation(summary = "Get paginated jobs")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @GetMapping(value = "/paginated", produces = "application/json")
  public Page<Job> getJobs(
      @Parameter(
              name = "page",
              description = "what page of the data",
              example = "0",
              required = true)
          @RequestParam
          int page,
      @Parameter(
              name = "pageSize",
              description = "size of each page",
              example = "10",
              required = true)
          @RequestParam
          int pageSize,
      @Parameter(
              name = "sortField",
              description = "sort field",
              example = "createdAt",
              required = false)
          @RequestParam(defaultValue = "status")
          String sortField,
      @Parameter(
              name = "sortDirection",
              description = "sort direction",
              example = "ASC",
              required = false)
          @RequestParam(defaultValue = "DESC")
          String sortDirection) {

    List<String> allowedSortFields = Arrays.asList("createdBy", "status", "createdAt", "updatedAt");

    if (!allowedSortFields.contains(sortField)) {
      throw new IllegalArgumentException(
          String.format(
              "%s is not a valid sort field. Valid values are %s", sortField, allowedSortFields));
    }

    List<String> allowedSortDirections = Arrays.asList("ASC", "DESC");
    if (!allowedSortDirections.contains(sortDirection)) {
      throw new IllegalArgumentException(
          String.format(
              "%s is not a valid sort direction. Valid values are %s",
              sortDirection, allowedSortDirections));
    }

    Direction sortDirectionObject = Direction.DESC;
    if (sortDirection.equals("ASC")) {
      sortDirectionObject = Direction.ASC;
    }

    PageRequest pageRequest = PageRequest.of(page, pageSize, sortDirectionObject, sortField);
    return jobsRepository.findAll(pageRequest);
  }
}
