# Clogr

_Csar Logging Registration_ ([**Clogr**](http://clogr.io/)) simplifies SLF4J logging while providing compartmentalized logging configurations via [Csar](http://csar.io/).

Clogr is not yet another logging facade. Rather Clogr facilitates access to SLF4J loggers; while improving SLF4J logging configuration, allowing various compartmentalized log settings. The `LoggingConcern` is accessed using Csar's concern registration mechanism. The primary logging access method `Clogr.getLoggingConcern()` will return the logging concern registered with Csar. The default, unconfigured state will still merely delegate the configured SLF4J logger factory, exactly as could be done dynamically directly from SLF4J. The benefit is that multiple logging configurations can separately be compartmentalized using Csar, and code that accesses loggers via Clogr will be routed to use the correct log configuration automatically. 

Moreover classes needing access to a logger may implement `Clogged` for simplified retrieval of an SLF4J logger. 

## Download

Clogr is available in the Maven Central Repository in group [io.clogr](http://search.maven.org/#search|ga|1|g%3A%22io.clogr%22).

**Important:** Clogr only declares the `org.slf4j:slf4j-api` dependency for compilation and testing. You must manually provide a dependency to SLF4J in your own build configuration. Because Clogr provides convenience access to SLF4J, it is expected that SLF4J versions will change much more rapidly than that of Clogr; there furthermore is no need of Clogr to change with general SLF4J modifications improvements. Requiring manual specification of an SLF4J version in a consuming project allows the project to determine its own preferred SLF4J without a complex mechanism of transitive dependency exclusion and replacement.

## Issues

Issues tracked by [JIRA](https://globalmentor.atlassian.net/browse/CLOGR).

## Changelog

- 0.7.0: First release.
