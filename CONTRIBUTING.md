# Contributing

Contributions to Spark Exasol Connector project are very welcome!

Please feel free to report a bug, suggest an idea for a feature, or ask a
question about the code.

You can create an issue using [Github issues][exa-issues] or follow a standard
[fork and pull][fork-and-pull] process to contribute a code via [Github pull
requests][exa-pulls].

Please keep in mind that contributions are not only pull requests. They can be
any helpful comment on issues, improving documentation, enchaning build process
and many other tasks.

If you do not know where to start, please have a look at [open
issues][open-issues]. You can choose the ones that interest you the most. If you
are new to the project, checkout the issues labeled as
[good-first-issue][first-issue].

## Building the project

The only prerequisites to build the project are Java and [Docker][docker]. You
can find instructions on how to install Docker [here][docker-install].

First clone a local copy of the repository:

```bash
git clone https://github.com/exasol/spark-exasol-connector.git
```

Then enter into it and run:

```bash
./sbtx
```

From there on you can run several `sbt` commands. Here are some of them to get
you started.

- `clean`: cleans previously compiled outputs; to start clean again.
- `compile`: compiles the source files.
- `test`: run all the unit tests.
- `it:test`: run all the integration tests using docker.

Additionally, you can run `testOnly filename` or `it:testOnly filename` commands
to only run single file tests.

**REMARK** In order to run integration tests please first create a separate
docker network. For example:

```bash
docker network create -d bridge --subnet 192.168.0.0/24 --gateway 192.168.0.1 dockernet
```

## Submit a pull request

Once you have found an interesting feature or issue to contribute, you can follow
steps below to submit your patches.

- Fork the repository,

  ```bash
  git clone git@github.com:YOUR-USERNAME/exasol/spark-exasol-connector.git
  ```

- Create a new feature branch, `git checkout -b "cool-new-feature"`
- Code
- Write tests for changes
- Update documentation if needed
- **Make sure everything is working**, run `./scripts/ci.sh`
- If everything is okay, commit and push to your fork
- [Submit a pull request][submit-pr]
- Let's work together to get your changes reviewed
- Merge into origin

If your commit fixes any particular issue, please specify it in your commit
message as `Fixes issue [issue number]`. For example, `Fixes issue #29`.

Some best practices when creating a pull request:

- Rebase or update
- Squash your commits
- Reword your commits
- Write clear commit messages

You can read more [here][do-pr1] and [here][do-pr2].

[exa-issues]: https://github.com/exasol/spark-exasol-connector/issues
[fork-and-pull]: https://help.github.com/articles/using-pull-requests/
[exa-pulls]: https://github.com/exasol/spark-exasol-connector/pulls
[docker]: https://www.docker.com/
[docker-install]: https://docs.docker.com/install/
[open-issues]: https://github.com/exasol/spark-exasol-connector/issues
[first-issue]: https://github.com/exasol/spark-exasol-connector/issues?q=is%3Aissue+is%3Aopen+label%3A%22good+first+issue%22
[submit-pr]: https://github.com/exasol/spark-exasol-connector/compare
[do-pr1]: https://www.digitalocean.com/community/tutorials/how-to-create-a-pull-request-on-github
[do-pr2]: https://www.digitalocean.com/community/tutorials/how-to-rebase-and-update-a-pull-request
