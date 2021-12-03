<!-- @formatter:off -->
# Dependencies

## Compile Dependencies

| Dependency                                  | License                                        |
| ------------------------------------------- | ---------------------------------------------- |
| [EXASolution JDBC Driver][0]                | [EXAClient License][1]                         |
| [Exasol SQL Statement Builder][2]           | [MIT][3]                                       |
| [error-reporting-java][4]                   | [MIT][3]                                       |
| [Spark Project Core][6]                     | [Apache 2.0 License][7]                        |
| [Spark Project SQL][6]                      | [Apache 2.0 License][7]                        |
| [Guava: Google Core Libraries for Java][10] | [Apache License, Version 2.0][11]              |
| [Netty/All-in-One][12]                      | [Apache License, Version 2.0][13]              |
| [jackson-databind][14]                      | [The Apache Software License, Version 2.0][11] |
| Apache Hadoop Client Aggregator             | [Apache License, Version 2.0][16]              |

## Test Dependencies

| Dependency                                 | License                                   |
| ------------------------------------------ | ----------------------------------------- |
| [scalatest][17]                            | [the Apache License, ASL Version 2.0][18] |
| [scalatestplus-mockito][19]                | [Apache-2.0][18]                          |
| [mockito-core][21]                         | [The MIT License][22]                     |
| [Apache Log4j 1.x Compatibility API][23]   | [Apache License, Version 2.0][16]         |
| [Test Database Builder for Java][25]       | [MIT][3]                                  |
| [Matcher for SQL Result Sets][27]          | [MIT][3]                                  |
| [Test containers for Exasol on Docker][29] | [MIT][3]                                  |

## Plugin Dependencies

| Dependency                                              | License                                        |
| ------------------------------------------------------- | ---------------------------------------------- |
| [scala-maven-plugin][31]                                | [Public domain (Unlicense)][32]                |
| [Apache Maven Compiler Plugin][33]                      | [Apache License, Version 2.0][16]              |
| [ScalaTest Maven Plugin][35]                            | [the Apache License, ASL Version 2.0][18]      |
| [Apache Maven Enforcer Plugin][37]                      | [Apache License, Version 2.0][16]              |
| [Apache Maven Deploy Plugin][39]                        | [Apache License, Version 2.0][16]              |
| [Apache Maven GPG Plugin][41]                           | [Apache License, Version 2.0][16]              |
| [Nexus Staging Maven Plugin][43]                        | [Eclipse Public License][44]                   |
| [Apache Maven Source Plugin][45]                        | [Apache License, Version 2.0][16]              |
| [Apache Maven Javadoc Plugin][47]                       | [Apache License, Version 2.0][16]              |
| [Apache Maven JAR Plugin][49]                           | [Apache License, Version 2.0][16]              |
| [Apache Maven Assembly Plugin][51]                      | [Apache License, Version 2.0][16]              |
| [Versions Maven Plugin][53]                             | [Apache License, Version 2.0][16]              |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][55] | [ASL2][11]                                     |
| [Reproducible Build Maven Plugin][57]                   | [Apache 2.0][11]                               |
| [Project keeper maven plugin][59]                       | [MIT][3]                                       |
| [JaCoCo :: Maven Plugin][61]                            | [Eclipse Public License 2.0][62]               |
| [error-code-crawler-maven-plugin][63]                   | [MIT][3]                                       |
| [Artifact reference checker and unifier][65]            | [MIT][3]                                       |
| [OpenFastTrace Maven Plugin][67]                        | [GNU General Public License v3.0][68]          |
| [Scalastyle Maven Plugin][69]                           | [Apache 2.0][7]                                |
| [spotless-maven-plugin][71]                             | [The Apache Software License, Version 2.0][16] |
| [scalafix-maven-plugin][73]                             | [BSD-3-Clause][74]                             |
| [Apache Maven Clean Plugin][75]                         | [Apache License, Version 2.0][16]              |
| [Apache Maven Resources Plugin][77]                     | [Apache License, Version 2.0][16]              |
| [Maven Surefire Plugin][79]                             | [The Apache Software License, Version 2.0][11] |
| [Apache Maven Install Plugin][81]                       | [Apache License, Version 2.0][11]              |
| [Apache Maven Site Plugin][83]                          | [Apache License, Version 2.0][16]              |

[59]: https://github.com/exasol/project-keeper-maven-plugin
[19]: https://github.com/scalatest/scalatestplus-mockito
[4]: https://github.com/exasol/error-reporting-java
[11]: http://www.apache.org/licenses/LICENSE-2.0.txt
[69]: http://www.scalastyle.org
[71]: https://github.com/diffplug/spotless
[1]: https://docs.exasol.com/connect_exasol/drivers/jdbc.htm
[3]: https://opensource.org/licenses/MIT
[12]: https://netty.io/netty-all/
[21]: https://github.com/mockito/mockito
[53]: http://www.mojohaus.org/versions-maven-plugin/
[33]: https://maven.apache.org/plugins/maven-compiler-plugin/
[77]: https://maven.apache.org/plugins/maven-resources-plugin/
[67]: https://github.com/itsallcode/openfasttrace-maven-plugin
[75]: https://maven.apache.org/plugins/maven-clean-plugin/
[62]: https://www.eclipse.org/legal/epl-2.0/
[14]: http://github.com/FasterXML/jackson
[39]: https://maven.apache.org/plugins/maven-deploy-plugin/
[32]: http://unlicense.org/
[13]: https://www.apache.org/licenses/LICENSE-2.0
[35]: https://www.scalatest.org/user_guide/using_the_scalatest_maven_plugin
[61]: https://www.jacoco.org/jacoco/trunk/doc/maven.html
[22]: https://github.com/mockito/mockito/blob/main/LICENSE
[27]: https://github.com/exasol/hamcrest-resultset-matcher
[57]: http://zlika.github.io/reproducible-build-maven-plugin
[79]: http://maven.apache.org/surefire/maven-surefire-plugin
[74]: https://opensource.org/licenses/BSD-3-Clause
[45]: https://maven.apache.org/plugins/maven-source-plugin/
[65]: https://github.com/exasol/artifact-reference-checker-maven-plugin
[73]: https://github.com/evis/scalafix-maven-plugin
[49]: https://maven.apache.org/plugins/maven-jar-plugin/
[18]: http://www.apache.org/licenses/LICENSE-2.0
[10]: https://github.com/google/guava
[43]: http://www.sonatype.com/public-parent/nexus-maven-plugins/nexus-staging/nexus-staging-maven-plugin/
[7]: http://www.apache.org/licenses/LICENSE-2.0.html
[17]: http://www.scalatest.org
[23]: https://logging.apache.org/log4j/2.x/log4j-1.2-api/
[25]: https://github.com/exasol/test-db-builder-java
[2]: https://github.com/exasol/sql-statement-builder
[44]: http://www.eclipse.org/legal/epl-v10.html
[29]: https://github.com/exasol/exasol-testcontainers
[83]: https://maven.apache.org/plugins/maven-site-plugin/
[68]: https://www.gnu.org/licenses/gpl-3.0.html
[16]: https://www.apache.org/licenses/LICENSE-2.0.txt
[37]: https://maven.apache.org/enforcer/maven-enforcer-plugin/
[0]: http://www.exasol.com
[81]: http://maven.apache.org/plugins/maven-install-plugin/
[55]: https://sonatype.github.io/ossindex-maven/maven-plugin/
[41]: https://maven.apache.org/plugins/maven-gpg-plugin/
[31]: http://github.com/davidB/scala-maven-plugin
[6]: http://spark.apache.org/
[47]: https://maven.apache.org/plugins/maven-javadoc-plugin/
[63]: https://github.com/exasol/error-code-crawler-maven-plugin
[51]: https://maven.apache.org/plugins/maven-assembly-plugin/
