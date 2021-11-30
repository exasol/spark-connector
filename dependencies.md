<!-- @formatter:off -->
# Dependencies

## Compile Dependencies

| Dependency                        | License                 |
| --------------------------------- | ----------------------- |
| [EXASolution JDBC Driver][0]      | [EXAClient License][1]  |
| [Exasol SQL Statement Builder][2] | [MIT][3]                |
| [error-reporting-java][4]         | [MIT][3]                |
| [Spark Project Core][6]           | [Apache 2.0 License][7] |
| [Spark Project SQL][6]            | [Apache 2.0 License][7] |

## Test Dependencies

| Dependency                                 | License                                   |
| ------------------------------------------ | ----------------------------------------- |
| [scalatest][10]                            | [the Apache License, ASL Version 2.0][11] |
| [scalatestplus-mockito][12]                | [Apache-2.0][11]                          |
| [mockito-core][14]                         | [The MIT License][15]                     |
| [spark-testing-base][16]                   | [Apache License 2.0][7]                   |
| [Test Database Builder for Java][18]       | [MIT][3]                                  |
| [Matcher for SQL Result Sets][20]          | [MIT][3]                                  |
| [Test containers for Exasol on Docker][22] | [MIT][3]                                  |

## Plugin Dependencies

| Dependency                                              | License                                        |
| ------------------------------------------------------- | ---------------------------------------------- |
| [scala-maven-plugin][24]                                | [Public domain (Unlicense)][25]                |
| [Apache Maven Compiler Plugin][26]                      | [Apache License, Version 2.0][27]              |
| [Maven Surefire Plugin][28]                             | [Apache License, Version 2.0][27]              |
| [ScalaTest Maven Plugin][30]                            | [the Apache License, ASL Version 2.0][11]      |
| [Apache Maven Assembly Plugin][32]                      | [Apache License, Version 2.0][27]              |
| [Versions Maven Plugin][34]                             | [Apache License, Version 2.0][27]              |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][36] | [ASL2][37]                                     |
| [Apache Maven Enforcer Plugin][38]                      | [Apache License, Version 2.0][27]              |
| [OpenFastTrace Maven Plugin][40]                        | [GNU General Public License v3.0][41]          |
| [Apache Maven GPG Plugin][42]                           | [Apache License, Version 2.0][27]              |
| [Apache Maven Deploy Plugin][44]                        | [Apache License, Version 2.0][27]              |
| [Nexus Staging Maven Plugin][46]                        | [Eclipse Public License][47]                   |
| [Apache Maven Source Plugin][48]                        | [Apache License, Version 2.0][27]              |
| [Apache Maven Javadoc Plugin][50]                       | [Apache License, Version 2.0][27]              |
| [Reproducible Build Maven Plugin][52]                   | [Apache 2.0][37]                               |
| [error-code-crawler-maven-plugin][54]                   | [MIT][3]                                       |
| [Project keeper maven plugin][56]                       | [MIT][3]                                       |
| [SCoverage Maven Plugin][58]                            | [The Apache Software License, Version 2.0][37] |
| [Scalastyle Maven Plugin][60]                           | [Apache 2.0][7]                                |
| [spotless-maven-plugin][62]                             | [The Apache Software License, Version 2.0][27] |
| [scalafix-maven-plugin][64]                             | [BSD-3-Clause][65]                             |
| [JaCoCo :: Maven Plugin][66]                            | [Eclipse Public License 2.0][67]               |
| [Artifact reference checker and unifier][68]            | [MIT][3]                                       |
| [Maven Failsafe Plugin][70]                             | [Apache License, Version 2.0][27]              |
| [Apache Maven JAR Plugin][72]                           | [Apache License, Version 2.0][27]              |
| [Apache Maven Clean Plugin][74]                         | [Apache License, Version 2.0][27]              |
| [Apache Maven Resources Plugin][76]                     | [Apache License, Version 2.0][27]              |
| [Apache Maven Install Plugin][78]                       | [Apache License, Version 2.0][37]              |
| [Apache Maven Site Plugin][80]                          | [Apache License, Version 2.0][27]              |

[56]: https://github.com/exasol/project-keeper-maven-plugin
[58]: https://scoverage.github.io/scoverage-maven-plugin/1.4.1/
[12]: https://github.com/scalatest/scalatestplus-mockito
[4]: https://github.com/exasol/error-reporting-java
[37]: http://www.apache.org/licenses/LICENSE-2.0.txt
[60]: http://www.scalastyle.org
[28]: https://maven.apache.org/surefire/maven-surefire-plugin/
[62]: https://github.com/diffplug/spotless
[3]: https://opensource.org/licenses/MIT
[14]: https://github.com/mockito/mockito
[34]: http://www.mojohaus.org/versions-maven-plugin/
[26]: https://maven.apache.org/plugins/maven-compiler-plugin/
[76]: https://maven.apache.org/plugins/maven-resources-plugin/
[40]: https://github.com/itsallcode/openfasttrace-maven-plugin
[74]: https://maven.apache.org/plugins/maven-clean-plugin/
[67]: https://www.eclipse.org/legal/epl-2.0/
[44]: https://maven.apache.org/plugins/maven-deploy-plugin/
[25]: http://unlicense.org/
[66]: https://www.jacoco.org/jacoco/trunk/doc/maven.html
[15]: https://github.com/mockito/mockito/blob/main/LICENSE
[20]: https://github.com/exasol/hamcrest-resultset-matcher
[52]: http://zlika.github.io/reproducible-build-maven-plugin
[65]: https://opensource.org/licenses/BSD-3-Clause
[16]: https://github.com/holdenk/spark-testing-base
[48]: https://maven.apache.org/plugins/maven-source-plugin/
[68]: https://github.com/exasol/artifact-reference-checker-maven-plugin
[64]: https://github.com/evis/scalafix-maven-plugin
[72]: https://maven.apache.org/plugins/maven-jar-plugin/
[1]: LICENSE-exasol-jdbc.txt
[11]: http://www.apache.org/licenses/LICENSE-2.0
[46]: http://www.sonatype.com/public-parent/nexus-maven-plugins/nexus-staging/nexus-staging-maven-plugin/
[7]: http://www.apache.org/licenses/LICENSE-2.0.html
[10]: http://www.scalatest.org
[70]: https://maven.apache.org/surefire/maven-failsafe-plugin/
[18]: https://github.com/exasol/test-db-builder-java
[2]: https://github.com/exasol/sql-statement-builder
[47]: http://www.eclipse.org/legal/epl-v10.html
[22]: https://github.com/exasol/exasol-testcontainers
[80]: https://maven.apache.org/plugins/maven-site-plugin/
[41]: https://www.gnu.org/licenses/gpl-3.0.html
[27]: https://www.apache.org/licenses/LICENSE-2.0.txt
[30]: http://nexus.sonatype.org/oss-repository-hosting.html/scalatest-maven-plugin
[38]: https://maven.apache.org/enforcer/maven-enforcer-plugin/
[0]: http://www.exasol.com
[78]: http://maven.apache.org/plugins/maven-install-plugin/
[36]: https://sonatype.github.io/ossindex-maven/maven-plugin/
[42]: https://maven.apache.org/plugins/maven-gpg-plugin/
[24]: http://github.com/davidB/scala-maven-plugin
[6]: http://spark.apache.org/
[50]: https://maven.apache.org/plugins/maven-javadoc-plugin/
[54]: https://github.com/exasol/error-code-crawler-maven-plugin
[32]: https://maven.apache.org/plugins/maven-assembly-plugin/
