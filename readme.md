# Clogr Logback Provider

Clogr (with Csar provider) support for Logback.

This implementation includes `io.clogr:clogr.logback` as a dependency.

## Issues

Issues tracked by [JIRA](https://globalmentor.atlassian.net/browse/CLOGR).

## Build

The standard Maven build only creates the `clogr-logback` artifacts. The files necessary for the `clogr-logback-provider` artifacts are contained in this same repository, so as not to maintain a separate repository just for a single provider file. To build the `clogr-logback-provider` artifacts, separately clean and build using `provider-pom.xml`:

    mvn clean install -f provider-pom.xml

**You _must_ include the `clean` lifecycle so that the resulting artifacts do not include the source code.**

## Changelog

- 0.8.0:
	* [CLOGR-3](https://globalmentor.atlassian.net/browse/CLOGR-3): First release; Created this project as separated `Csar` concern provider for `clogr`.
