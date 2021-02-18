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
