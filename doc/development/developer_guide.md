# Developer Guide

Please read the general [developer guide for the Scala projects][dev-guide].

## Integration Tests

The integration tests are run using [Docker][docker] containers. The tests use
[exasol-testcontainers][exa-testcontainers] and
[spark-testing-base][spark-testing-base].

[docker]: https://www.docker.com/
[exa-testcontainers]: https://github.com/exasol/exasol-testcontainers/
[spark-testing-base]: https://github.com/holdenk/spark-testing-base
[dev-guide]: https://github.com/exasol/import-export-udf-common-scala/blob/master/doc/development/developer_guide.md

## Release

Currently [release-droid](https://github.com/exasol/release-droid) has some troubles releasing the spark connector.

* **Validation** is not possible and fails with error message `NumberFormatException for input string: "v0"`.<br />
This is because some older tags of spark-connector start with letter `v`, instead of using a plain semantic version number, e.g. `v0.3.1` instead of `0.3.1`.<br />
See also release-droid [issue/245](https://github.com/exasol/release-droid/issues/245).
* **Language Java**: Although spark-connector contains scala code as well, there also is a pom file, though.<br />
In order to publish spark-connector to maven central using language "Java" is correct.
* For **releasing** spark-connector you should use
```
java -jar path/to/release-droid-*.jar" -n spark-connector -lg Java -g release --skipvalidation
```
