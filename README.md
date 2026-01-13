# proj-spring-spooler

This project is an attempt to factor out some common code from several 
Spring Boot projects used in the CMPSC 156 course at UCSB
into a common module that can be included as a dependency in the pom.xml
using Maven.

Specifically, we are factoring out the code for managing asynchronous jobs.

In addition to the benefits of having to maintain this code only in one place,
we also hope to speed up our testing by not having to test this code
with every build.   This is especially important because a few of the tests
for code in this module:
* take at least an order of magnitude longer to run than the rest of the tests in the code base
* are especially prone to being flaky tests

## Road Map

We started this code base by:

* Copying the backend from <https://github.com/ucsb-cs156/proj-courses>
* Removing all application specific code and retaining only the code for the jobs system, user authentication, and other generic functions that tend to be common across all of our applications

The next steps are these (they may have been taken yet--this README should be updated after they have been):

* Determine how to create a file that can be uploaded to Maven that contains only the classes that are needed for the jobs system
* Add github actions to publish the module to Maven Central
* Make a branch of proj-courses that uses this as the job system instead of the one built into proj-courses
* Add that into dining, happycows, and frontiers as well

## Thoughts
Maybe the minimum that's needed in this package is

* job entity
* the four clases under services/jobs

And then everything in a client application that is using this module would create their own classes that implement JobContextConsumer.  And that's where the application specific stuff goes.

We might also be able to standardize some of the feeatures of the jobs controller..but we would split what's in JobsController in course right now into two parts:  
* The parts that launch application specific jobs
* The parts that are generic (e.g. the parts for getting job entities from the database and paginating them)

