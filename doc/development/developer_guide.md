# Developer Guide

Please read the general [developer guide for the Scala projects][dev-guide].

## Integration Tests

The integration tests are run using [Docker][docker] containers. The tests use
[exasol/docker-db][exa-docker-db], [testcontainers][testcontainers] and
[spark-testing-base][spark-testing-base].

To run integration tests, a separate docker network should be created first:

```bash
docker network create -d bridge --subnet 192.168.0.0/24 --gateway 192.168.0.1 dockernet
```

The docker network is required since we connect to the Exasol docker container
using an internal IPv4 address.

[dev-guide]: https://github.com/exasol/import-export-udf-common-scala/blob/master/doc/development/developer_guide.md
[docker]: https://www.docker.com/
[exa-docker-db]: https://hub.docker.com/r/exasol/docker-db/
[testcontainers]: https://www.testcontainers.org/
[spark-testing-base]: https://github.com/holdenk/spark-testing-base
