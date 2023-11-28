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
