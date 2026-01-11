package edu.ucsb.cs156.spooler.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import edu.ucsb.cs156.spooler.errors.EntityNotFoundException;
import edu.ucsb.cs156.spooler.models.CurrentUser;
import edu.ucsb.cs156.spooler.services.CurrentUserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class ApiController {

  @Autowired private CurrentUserService currentUserService;

  protected CurrentUser getCurrentUser() {
    return currentUserService.getCurrentUser();
  }

  @ExceptionHandler({EntityNotFoundException.class})
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public Object handleGenericException(Throwable e) {
    return Map.of(
        "type", e.getClass().getSimpleName(),
        "message", e.getMessage());
  }


  @ExceptionHandler({IllegalArgumentException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Object handleIllegalArgumentException(Throwable e) {
    return Map.of(
        "type", e.getClass().getSimpleName(),
        "message", e.getMessage());
  }
}
