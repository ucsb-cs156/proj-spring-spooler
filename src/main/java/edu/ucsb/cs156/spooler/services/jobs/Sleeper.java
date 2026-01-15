package edu.ucsb.cs156.spooler.services.jobs;

public class Sleeper {
  public static void sleep(long millis) throws InterruptedException {
    Thread.sleep(millis);
  }
}
