<!-- @formatter:off -->
# Dependencies

## Compile Dependencies

| Dependency                                 | License                                        |
| ------------------------------------------ | ---------------------------------------------- |
| [Scala Library][0]                         | [Apache-2.0][1]                                |
| [EXASolution JDBC Driver][2]               | [EXAClient License][3]                         |
| [Exasol SQL Statement Builder][4]          | [MIT][5]                                       |
| [error-reporting-java][6]                  | [MIT][5]                                       |
| [Spark Project Core][7]                    | [Apache 2.0 License][8]                        |
| [Spark Project SQL][7]                     | [Apache 2.0 License][8]                        |
| [Guava: Google Core Libraries for Java][9] | [Apache License, Version 2.0][10]              |
| [Netty/All-in-One][11]                     | [Apache License, Version 2.0][1]               |
| [jackson-databind][12]                     | [The Apache Software License, Version 2.0][10] |
| Apache Hadoop Client Aggregator            | [Apache License, Version 2.0][13]              |

## Test Dependencies

| Dependency                                 | License                                   |
| ------------------------------------------ | ----------------------------------------- |
| [scalatest][14]                            | [the Apache License, ASL Version 2.0][15] |
| [scalatestplus-mockito][16]                | [Apache-2.0][15]                          |
| [mockito-core][17]                         | [The MIT License][18]                     |
| [Apache Log4j 1.x Compatibility API][19]   | [Apache License, Version 2.0][13]         |
| [Apache Log4j Core][20]                    | [Apache License, Version 2.0][13]         |
| [Test Database Builder for Java][21]       | [MIT License][22]                         |
| [Matcher for SQL Result Sets][23]          | [MIT][5]                                  |
| [Test containers for Exasol on Docker][24] | [MIT][5]                                  |

## Plugin Dependencies

| Dependency                                              | License                                        |
| ------------------------------------------------------- | ---------------------------------------------- |
| [SonarQube Scanner for Maven][25]                       | [GNU LGPL 3][26]                               |
| [scala-maven-plugin][27]                                | [Public domain (Unlicense)][28]                |
| [Apache Maven Compiler Plugin][29]                      | [Apache License, Version 2.0][13]              |
| [ScalaTest Maven Plugin][30]                            | [the Apache License, ASL Version 2.0][15]      |
| [Apache Maven Enforcer Plugin][31]                      | [Apache License, Version 2.0][13]              |
| [Maven Flatten Plugin][32]                              | [Apache Software Licenese][10]                 |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][33] | [ASL2][10]                                     |
| [Reproducible Build Maven Plugin][34]                   | [Apache 2.0][10]                               |
| [Maven Surefire Plugin][35]                             | [Apache License, Version 2.0][13]              |
| [Versions Maven Plugin][36]                             | [Apache License, Version 2.0][13]              |
| [Apache Maven Deploy Plugin][37]                        | [Apache License, Version 2.0][13]              |
| [Apache Maven GPG Plugin][38]                           | [Apache License, Version 2.0][13]              |
| [Apache Maven Source Plugin][39]                        | [Apache License, Version 2.0][13]              |
| [Apache Maven Javadoc Plugin][40]                       | [Apache License, Version 2.0][13]              |
| [Nexus Staging Maven Plugin][41]                        | [Eclipse Public License][42]                   |
| [Apache Maven JAR Plugin][43]                           | [Apache License, Version 2.0][13]              |
| [Apache Maven Shade Plugin][44]                         | [Apache License, Version 2.0][13]              |
| [Maven Failsafe Plugin][45]                             | [Apache License, Version 2.0][13]              |
| [Project keeper maven plugin][46]                       | [The MIT License][47]                          |
| [JaCoCo :: Maven Plugin][48]                            | [Eclipse Public License 2.0][49]               |
| [error-code-crawler-maven-plugin][50]                   | [MIT][5]                                       |
| [Artifact reference checker and unifier][51]            | [MIT][5]                                       |
| [OpenFastTrace Maven Plugin][52]                        | [GNU General Public License v3.0][53]          |
| [Scalastyle Maven Plugin][54]                           | [Apache 2.0][8]                                |
| [spotless-maven-plugin][55]                             | [The Apache Software License, Version 2.0][13] |
| [scalafix-maven-plugin][56]                             | [BSD-3-Clause][57]                             |
| [Apache Maven Clean Plugin][58]                         | [Apache License, Version 2.0][13]              |
| [Apache Maven Resources Plugin][59]                     | [Apache License, Version 2.0][13]              |
| [Apache Maven Install Plugin][60]                       | [Apache License, Version 2.0][10]              |
| [Apache Maven Site Plugin][61]                          | [Apache License, Version 2.0][13]              |

[0]: https://www.scala-lang.org/
[1]: https://www.apache.org/licenses/LICENSE-2.0
[2]: http://www.exasol.com
[3]: https://docs.exasol.com/connect_exasol/drivers/jdbc.htm
[4]: https://github.com/exasol/sql-statement-builder
[5]: https://opensource.org/licenses/MIT
[6]: https://github.com/exasol/error-reporting-java
[7]: http://spark.apache.org/
[8]: http://www.apache.org/licenses/LICENSE-2.0.html
[9]: https://github.com/google/guava
[10]: http://www.apache.org/licenses/LICENSE-2.0.txt
[11]: https://netty.io/index.html
[12]: http://github.com/FasterXML/jackson
[13]: https://www.apache.org/licenses/LICENSE-2.0.txt
[14]: http://www.scalatest.org
[15]: http://www.apache.org/licenses/LICENSE-2.0
[16]: https://github.com/scalatest/scalatestplus-mockito
[17]: https://github.com/mockito/mockito
[18]: https://github.com/mockito/mockito/blob/main/LICENSE
[19]: https://logging.apache.org/log4j/2.x/log4j-1.2-api/
[20]: https://logging.apache.org/log4j/2.x/log4j-core/
[21]: https://github.com/exasol/test-db-builder-java/
[22]: https://github.com/exasol/test-db-builder-java/blob/main/LICENSE
[23]: https://github.com/exasol/hamcrest-resultset-matcher
[24]: https://github.com/exasol/exasol-testcontainers
[25]: http://sonarsource.github.io/sonar-scanner-maven/
[26]: http://www.gnu.org/licenses/lgpl.txt
[27]: http://github.com/davidB/scala-maven-plugin
[28]: http://unlicense.org/
[29]: https://maven.apache.org/plugins/maven-compiler-plugin/
[30]: https://www.scalatest.org/user_guide/using_the_scalatest_maven_plugin
[31]: https://maven.apache.org/enforcer/maven-enforcer-plugin/
[32]: https://www.mojohaus.org/flatten-maven-plugin/
[33]: https://sonatype.github.io/ossindex-maven/maven-plugin/
[34]: http://zlika.github.io/reproducible-build-maven-plugin
[35]: https://maven.apache.org/surefire/maven-surefire-plugin/
[36]: http://www.mojohaus.org/versions-maven-plugin/
[37]: https://maven.apache.org/plugins/maven-deploy-plugin/
[38]: https://maven.apache.org/plugins/maven-gpg-plugin/
[39]: https://maven.apache.org/plugins/maven-source-plugin/
[40]: https://maven.apache.org/plugins/maven-javadoc-plugin/
[41]: http://www.sonatype.com/public-parent/nexus-maven-plugins/nexus-staging/nexus-staging-maven-plugin/
[42]: http://www.eclipse.org/legal/epl-v10.html
[43]: https://maven.apache.org/plugins/maven-jar-plugin/
[44]: https://maven.apache.org/plugins/maven-shade-plugin/
[45]: https://maven.apache.org/surefire/maven-failsafe-plugin/
[46]: https://github.com/exasol/project-keeper/
[47]: https://github.com/exasol/project-keeper/blob/main/LICENSE
[48]: https://www.jacoco.org/jacoco/trunk/doc/maven.html
[49]: https://www.eclipse.org/legal/epl-2.0/
[50]: https://github.com/exasol/error-code-crawler-maven-plugin
[51]: https://github.com/exasol/artifact-reference-checker-maven-plugin
[52]: https://github.com/itsallcode/openfasttrace-maven-plugin
[53]: https://www.gnu.org/licenses/gpl-3.0.html
[54]: http://www.scalastyle.org
[55]: https://github.com/diffplug/spotless
[56]: https://github.com/evis/scalafix-maven-plugin
[57]: https://opensource.org/licenses/BSD-3-Clause
[58]: https://maven.apache.org/plugins/maven-clean-plugin/
[59]: https://maven.apache.org/plugins/maven-resources-plugin/
[60]: http://maven.apache.org/plugins/maven-install-plugin/
[61]: https://maven.apache.org/plugins/maven-site-plugin/
