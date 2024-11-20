## Version: [v3.0.5](https://github.com/newrelic-experimental/newrelic-java-camel/releases/tag/v3.0.5) | Created: 2024-11-20
### Bug Fixes
- Fixed incorrect verify intervals
- Fixed verify interval problems
- Fixed verify interval problems
- Merge pull request #26 from newrelic-experimental/fix_verify_intervals

## Version: [v3.0.4](https://github.com/newrelic-experimental/newrelic-java-camel/releases/tag/v3.0.4) | Created: 2024-07-03
### Bug Fixes
- Added camel-kafka-3.22.0 and fixed build for camel-core-3.1
- Ixed merge problem

## Version: [v3.0.3](https://github.com/newrelic-experimental/newrelic-java-camel/releases/tag/v3.0.3) | Created: 2024-01-29
### Bug Fixes
- Ixed further problems with distributed tracing headers
- Ixed further problems with distributed tracing headers

## Version: [v3.0.2](https://github.com/newrelic-experimental/newrelic-java-camel/releases/tag/v3.0.2) | Created: 2024-01-25
### Bug Fixes
- Ixed problems with distributed tracing headers
- Ixed problems with camel netty instrumentation
- Ixed problems with camel kafka instrumentation
- Ixed problems with camel kafka instrumentation

## Version: [v3.0.1](https://github.com/newrelic-experimental/newrelic-java-camel/releases/tag/v3.0.1) | Created: 2024-01-08
### Bug Fixes
- Ixes
- Ixes
- Erge pull request #19 from newrelic-experimental/merge-fixes
- Ixes
- Ixes
- Erge pull request #20 from newrelic-experimental/merge-fixes

## Version: [v3.0.0](https://github.com/newrelic-experimental/newrelic-java-camel/releases/tag/v3.0.0) | Created: 2023-11-28
### Features
- Added enhancement for Camel core for the version 3.9 and above

### Enhancements
- Added support for Camel Netty and Camel Kafka

### Bug Fixes
- Merge pull request [#18](https://github.com/newrelic-experimental/newrelic-java-camel/pull/18) from newrelic-experimental/merge-fixes


## Version: [v2.2.1](https://github.com/newrelic-experimental/newrelic-java-camel/releases/tag/v2.2.1) | Created: 2023-11-02
### Bug Fixes
- Updated title information


### Enhancements
  - Added Support for Camel 4.0 Framework

## Installation

To install:

1. Download the latest release jar files.
2. In the New Relic Java directory (the one containing newrelic.jar), create a directory named extensions if it does not already exist.
3. Copy the downloaded jars into the extensions directory.
4. Restart the application.   

## Java 17 Support

To ensure compatibility with Java 17, please use New Relic Java Agent version 7.4.0 or higher.
If you're not using Java 17, you can safely remove camel-core-jms-4.0.0.jar from the extensions directory.
Failure to follow these recommendations may lead to application crashes.
