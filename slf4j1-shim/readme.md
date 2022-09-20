# SLF4J 1.x Shim

Legacy support shim for SLF4J 1.x.

This library supplies a fake stand-in implementation of `org.slf4j.impl.StaticLoggerBinder` so that SLF4J 2.x may be used even with applications that rely on `StaticLoggerBinder`. For such applications, simply include this library as a dependency; no further changes are needed.

This library may be used standalone to provide legacy SLF4J 1.x support to any application. It does not rely on Clogr; indeed the Clogr API must be added as a separate dependency.

_This library uses the `org.slf4j.impl` package, which represets a domain controlled by another entity. Normally this is against convention, but in this case it is necessary, as some legacy access requires that particular class to be present in that particular package. Moreover this usage will likely break Java module support when used with actual the SLF4J API distribution; however applications that rely on SLF4J 1.x `StaticLoggerBinder` would break the module system anyway. This implementation is being supplied merely as a stop-gap solution until applications remove their reliance on legacy SLF4J 1.x features._


## Download

Clogr's SLF4J 1.x Shim is available in the [Maven Central Repository](https://search.maven.org/search?q=g:io.clogr%20AND%20a:slf4j1-shim).

## Issues

Issues tracked by [JIRA](https://globalmentor.atlassian.net/projects/CLOGR).

## Changelog

- 0.8.3: Renamed shim library and added to BOM.
- 0.8.2: First release.
