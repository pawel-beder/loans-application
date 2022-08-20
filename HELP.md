# Read Me First
The following was discovered as part of building this project:

* The original package name 'pl.beder.loans-application' is invalid and this project uses 'pl.beder.loansapplication' instead.

# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.7.3/gradle-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.7.3/gradle-plugin/reference/html/#build-image)

### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

# Read Me First
Some assumptions made to simplify code during development
* All dates/dateTime ale calculated against Europe/Warsaw timezone
* Loan's amount is stored with value and currency but thresholds are checked just against value ignoring currency
* Money object is created with PLN as default currency when it's not provided

## Getting started
* build -> gradle build
* tests -> gradle test
