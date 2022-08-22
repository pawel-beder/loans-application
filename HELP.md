# Read Me First
Some assumptions made to simplify code during development
* All dates/dateTime ale calculated against Europe/Warsaw timezone
* Loan's amount is stored with value and currency but thresholds are checked just against value ignoring currency
* Money object is created with PLN as default currency when it's not provided
* Integration test is really basic and should be refactored but I've ran out of time
* Integration tests use Testcontainers, so a docker installation is required in order to run full build

## Getting started
* build -> gradle build
* unit tests -> gradle test
* integration tests -> gradle integrationTest
