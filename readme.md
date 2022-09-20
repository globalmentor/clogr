# Clogr

_Csar Logging Registration_ ([**Clogr**](https://clogr.io/)) simplifies SLF4J logging while providing compartmentalized logging configurations via [Csar](https://csar.io/).

Clogr is not yet another logging facade. Rather Clogr facilitates access to SLF4J loggers; while improving SLF4J logging configuration, allowing various compartmentalized log settings. The `LoggingConcern` is accessed using Csar's concern registration mechanism. The primary logging access method `Clogr.getLoggingConcern()` will return the logging concern registered with Csar. The default, unconfigured state will still merely delegate the configured SLF4J logger factory, exactly as could be done dynamically directly from SLF4J. The benefit is that multiple logging configurations can separately be compartmentalized using Csar, and code that accesses loggers via Clogr will be routed to use the correct log configuration automatically. 

Moreover classes needing access to a logger may implement `Clogged` for simplified retrieval of an SLF4J logger. 

Clogr is based [upon SLF4J 2.x](https://www.slf4j.org/faq.html#changesInVersion200). If an application depends on the legacy SLF4J 1.x class `StaticLoggerBinder`, Clogr provides the [`slf4j1-shim`](slf4j1-shim/readme.md) library, which may be used as a stop-gap solution to allow such applications to work with SLF4J 2.x.

## Download

Clogr is available in the Maven Central Repository in group [io.clogr](https://search.maven.org/search?q=g:io.clogr).

## Issues

Issues tracked by [JIRA](https://globalmentor.atlassian.net/projects/CLOGR).

## Changelog

- 0.8.2: SLF4J 1.x bridge for legacy application support.
- 0.8.1: Updated dependencies.
- 0.8.0: SLF4J 2.x support.
- 0.7.0: First release.
