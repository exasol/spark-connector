<!-- @formatter:off -->
# Dependencies

## Compile Dependencies

| Dependency                                  | License                                        |
| ------------------------------------------- | ---------------------------------------------- |
| [Scala Library][0]                          | [Apache-2.0][1]                                |
| [EXASolution JDBC Driver][2]                | [EXAClient License][3]                         |
| [Exasol SQL Statement Builder][4]           | [MIT][5]                                       |
| [error-reporting-java][6]                   | [MIT][5]                                       |
| [Spark Project Core][8]                     | [Apache 2.0 License][9]                        |
| [Spark Project SQL][8]                      | [Apache 2.0 License][9]                        |
| [Guava: Google Core Libraries for Java][12] | [Apache License, Version 2.0][13]              |
| [Netty/All-in-One][14]                      | [Apache License, Version 2.0][1]               |
| [jackson-databind][16]                      | [The Apache Software License, Version 2.0][13] |
| Apache Hadoop Client Aggregator             | [Apache License, Version 2.0][18]              |

## Test Dependencies

| Dependency                                 | License                                   |
| ------------------------------------------ | ----------------------------------------- |
| [scalatest][19]                            | [the Apache License, ASL Version 2.0][20] |
| [scalatestplus-mockito][21]                | [Apache-2.0][20]                          |
| [mockito-core][23]                         | [The MIT License][24]                     |
| [Apache Log4j 1.x Compatibility API][25]   | [Apache License, Version 2.0][18]         |
| [Test Database Builder for Java][27]       | [MIT][5]                                  |
| [Matcher for SQL Result Sets][29]          | [MIT][5]                                  |
| [Test containers for Exasol on Docker][31] | [MIT][5]                                  |

## Plugin Dependencies

| Dependency                                              | License                                        |
| ------------------------------------------------------- | ---------------------------------------------- |
| [scala-maven-plugin][33]                                | [Public domain (Unlicense)][34]                |
| [Apache Maven Compiler Plugin][35]                      | [Apache License, Version 2.0][18]              |
| [ScalaTest Maven Plugin][37]                            | [the Apache License, ASL Version 2.0][20]      |
| [Apache Maven Enforcer Plugin][39]                      | [Apache License, Version 2.0][18]              |
| [Apache Maven Deploy Plugin][41]                        | [Apache License, Version 2.0][18]              |
| [Apache Maven GPG Plugin][43]                           | [Apache License, Version 2.0][18]              |
| [Nexus Staging Maven Plugin][45]                        | [Eclipse Public License][46]                   |
| [Apache Maven Source Plugin][47]                        | [Apache License, Version 2.0][18]              |
| [Apache Maven Javadoc Plugin][49]                       | [Apache License, Version 2.0][18]              |
| [Apache Maven JAR Plugin][51]                           | [Apache License, Version 2.0][18]              |
| [Apache Maven Assembly Plugin][53]                      | [Apache License, Version 2.0][18]              |
| [Versions Maven Plugin][55]                             | [Apache License, Version 2.0][18]              |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][57] | [ASL2][13]                                     |
| [Reproducible Build Maven Plugin][59]                   | [Apache 2.0][13]                               |
| [Project keeper maven plugin][61]                       | [MIT][5]                                       |
| [JaCoCo :: Maven Plugin][63]                            | [Eclipse Public License 2.0][64]               |
| [error-code-crawler-maven-plugin][65]                   | [MIT][5]                                       |
| [Artifact reference checker and unifier][67]            | [MIT][5]                                       |
| [OpenFastTrace Maven Plugin][69]                        | [GNU General Public License v3.0][70]          |
| [Scalastyle Maven Plugin][71]                           | [Apache 2.0][9]                                |
| [spotless-maven-plugin][73]                             | [The Apache Software License, Version 2.0][18] |
| [scalafix-maven-plugin][75]                             | [BSD-3-Clause][76]                             |
| [Apache Maven Clean Plugin][77]                         | [Apache License, Version 2.0][18]              |
| [Apache Maven Resources Plugin][79]                     | [Apache License, Version 2.0][18]              |
| [Maven Surefire Plugin][81]                             | [The Apache Software License, Version 2.0][13] |
| [Apache Maven Install Plugin][83]                       | [Apache License, Version 2.0][13]              |
| [Apache Maven Site Plugin][85]                          | [Apache License, Version 2.0][18]              |

[61]: https://github.com/exasol/project-keeper-maven-plugin
[21]: https://github.com/scalatest/scalatestplus-mockito
[6]: https://github.com/exasol/error-reporting-java
[13]: http://www.apache.org/licenses/LICENSE-2.0.txt
[71]: http://www.scalastyle.org
[73]: https://github.com/diffplug/spotless
[3]: https://docs.exasol.com/connect_exasol/drivers/jdbc.htm
[5]: https://opensource.org/licenses/MIT
[23]: https://github.com/mockito/mockito
[55]: http://www.mojohaus.org/versions-maven-plugin/
[35]: https://maven.apache.org/plugins/maven-compiler-plugin/
[79]: https://maven.apache.org/plugins/maven-resources-plugin/
[69]: https://github.com/itsallcode/openfasttrace-maven-plugin
[77]: https://maven.apache.org/plugins/maven-clean-plugin/
[64]: https://www.eclipse.org/legal/epl-2.0/
[16]: http://github.com/FasterXML/jackson
[41]: https://maven.apache.org/plugins/maven-deploy-plugin/
[34]: http://unlicense.org/
[1]: https://www.apache.org/licenses/LICENSE-2.0
[37]: https://www.scalatest.org/user_guide/using_the_scalatest_maven_plugin
[63]: https://www.jacoco.org/jacoco/trunk/doc/maven.html
[24]: https://github.com/mockito/mockito/blob/main/LICENSE
[29]: https://github.com/exasol/hamcrest-resultset-matcher
[59]: http://zlika.github.io/reproducible-build-maven-plugin
[81]: http://maven.apache.org/surefire/maven-surefire-plugin
[76]: https://opensource.org/licenses/BSD-3-Clause
[47]: https://maven.apache.org/plugins/maven-source-plugin/
[67]: https://github.com/exasol/artifact-reference-checker-maven-plugin
[75]: https://github.com/evis/scalafix-maven-plugin
[51]: https://maven.apache.org/plugins/maven-jar-plugin/
[20]: http://www.apache.org/licenses/LICENSE-2.0
[14]: https://netty.io/index.html
[12]: https://github.com/google/guava
[45]: http://www.sonatype.com/public-parent/nexus-maven-plugins/nexus-staging/nexus-staging-maven-plugin/
[9]: http://www.apache.org/licenses/LICENSE-2.0.html
[19]: http://www.scalatest.org
[25]: https://logging.apache.org/log4j/2.x/log4j-1.2-api/
[27]: https://github.com/exasol/test-db-builder-java
[4]: https://github.com/exasol/sql-statement-builder
[0]: https://www.scala-lang.org/
[46]: http://www.eclipse.org/legal/epl-v10.html
[31]: https://github.com/exasol/exasol-testcontainers
[85]: https://maven.apache.org/plugins/maven-site-plugin/
[70]: https://www.gnu.org/licenses/gpl-3.0.html
[18]: https://www.apache.org/licenses/LICENSE-2.0.txt
[39]: https://maven.apache.org/enforcer/maven-enforcer-plugin/
[2]: http://www.exasol.com
[83]: http://maven.apache.org/plugins/maven-install-plugin/
[57]: https://sonatype.github.io/ossindex-maven/maven-plugin/
[43]: https://maven.apache.org/plugins/maven-gpg-plugin/
[33]: http://github.com/davidB/scala-maven-plugin
[8]: http://spark.apache.org/
[49]: https://maven.apache.org/plugins/maven-javadoc-plugin/
[65]: https://github.com/exasol/error-code-crawler-maven-plugin
[53]: https://maven.apache.org/plugins/maven-assembly-plugin/
