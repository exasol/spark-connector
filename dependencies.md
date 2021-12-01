<!-- @formatter:off -->
# Dependencies

## Compile Dependencies

| Dependency                                  | License                           |
| ------------------------------------------- | --------------------------------- |
| [EXASolution JDBC Driver][0]                | [EXAClient License][1]            |
| [Exasol SQL Statement Builder][2]           | [MIT][3]                          |
| [error-reporting-java][4]                   | [MIT][3]                          |
| [Spark Project Core][6]                     | [Apache 2.0 License][7]           |
| [Spark Project SQL][6]                      | [Apache 2.0 License][7]           |
| [Guava: Google Core Libraries for Java][10] | [Apache License, Version 2.0][11] |

## Test Dependencies

| Dependency                                 | License                                   |
| ------------------------------------------ | ----------------------------------------- |
| [scalatest][12]                            | [the Apache License, ASL Version 2.0][13] |
| [scalatestplus-mockito][14]                | [Apache-2.0][13]                          |
| [mockito-core][16]                         | [The MIT License][17]                     |
| [spark-testing-base][18]                   | [Apache License 2.0][7]                   |
| [Apache Log4j 1.x Compatibility API][20]   | [Apache License, Version 2.0][21]         |
| [Test Database Builder for Java][22]       | [MIT][3]                                  |
| [Matcher for SQL Result Sets][24]          | [MIT][3]                                  |
| [Test containers for Exasol on Docker][26] | [MIT][3]                                  |

## Plugin Dependencies

| Dependency                                              | License                                        |
| ------------------------------------------------------- | ---------------------------------------------- |
| [scala-maven-plugin][28]                                | [Public domain (Unlicense)][29]                |
| [Apache Maven Compiler Plugin][30]                      | [Apache License, Version 2.0][21]              |
| [ScalaTest Maven Plugin][32]                            | [the Apache License, ASL Version 2.0][13]      |
| [Apache Maven Enforcer Plugin][34]                      | [Apache License, Version 2.0][21]              |
| [Apache Maven Deploy Plugin][36]                        | [Apache License, Version 2.0][21]              |
| [Apache Maven GPG Plugin][38]                           | [Apache License, Version 2.0][21]              |
| [Nexus Staging Maven Plugin][40]                        | [Eclipse Public License][41]                   |
| [Apache Maven Source Plugin][42]                        | [Apache License, Version 2.0][21]              |
| [Apache Maven Javadoc Plugin][44]                       | [Apache License, Version 2.0][21]              |
| [Apache Maven JAR Plugin][46]                           | [Apache License, Version 2.0][21]              |
| [Apache Maven Assembly Plugin][48]                      | [Apache License, Version 2.0][21]              |
| [Versions Maven Plugin][50]                             | [Apache License, Version 2.0][21]              |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][52] | [ASL2][11]                                     |
| [Reproducible Build Maven Plugin][54]                   | [Apache 2.0][11]                               |
| [Project keeper maven plugin][56]                       | [MIT][3]                                       |
| [JaCoCo :: Maven Plugin][58]                            | [Eclipse Public License 2.0][59]               |
| [error-code-crawler-maven-plugin][60]                   | [MIT][3]                                       |
| [Artifact reference checker and unifier][62]            | [MIT][3]                                       |
| [OpenFastTrace Maven Plugin][64]                        | [GNU General Public License v3.0][65]          |
| [SCoverage Maven Plugin][66]                            | [The Apache Software License, Version 2.0][11] |
| [Scalastyle Maven Plugin][68]                           | [Apache 2.0][7]                                |
| [spotless-maven-plugin][70]                             | [The Apache Software License, Version 2.0][21] |
| [scalafix-maven-plugin][72]                             | [BSD-3-Clause][73]                             |
| [Apache Maven Clean Plugin][74]                         | [Apache License, Version 2.0][21]              |
| [Apache Maven Resources Plugin][76]                     | [Apache License, Version 2.0][21]              |
| [Maven Surefire Plugin][78]                             | [The Apache Software License, Version 2.0][11] |
| [Apache Maven Install Plugin][80]                       | [Apache License, Version 2.0][11]              |
| [Apache Maven Site Plugin][82]                          | [Apache License, Version 2.0][21]              |

[56]: https://github.com/exasol/project-keeper-maven-plugin
[66]: https://scoverage.github.io/scoverage-maven-plugin/1.4.1/
[14]: https://github.com/scalatest/scalatestplus-mockito
[4]: https://github.com/exasol/error-reporting-java
[11]: http://www.apache.org/licenses/LICENSE-2.0.txt
[68]: http://www.scalastyle.org
[70]: https://github.com/diffplug/spotless
[1]: https://docs.exasol.com/connect_exasol/drivers/jdbc.htm
[3]: https://opensource.org/licenses/MIT
[16]: https://github.com/mockito/mockito
[50]: http://www.mojohaus.org/versions-maven-plugin/
[30]: https://maven.apache.org/plugins/maven-compiler-plugin/
[76]: https://maven.apache.org/plugins/maven-resources-plugin/
[64]: https://github.com/itsallcode/openfasttrace-maven-plugin
[74]: https://maven.apache.org/plugins/maven-clean-plugin/
[59]: https://www.eclipse.org/legal/epl-2.0/
[36]: https://maven.apache.org/plugins/maven-deploy-plugin/
[29]: http://unlicense.org/
[32]: https://www.scalatest.org/user_guide/using_the_scalatest_maven_plugin
[58]: https://www.jacoco.org/jacoco/trunk/doc/maven.html
[17]: https://github.com/mockito/mockito/blob/main/LICENSE
[24]: https://github.com/exasol/hamcrest-resultset-matcher
[54]: http://zlika.github.io/reproducible-build-maven-plugin
[78]: http://maven.apache.org/surefire/maven-surefire-plugin
[73]: https://opensource.org/licenses/BSD-3-Clause
[18]: https://github.com/holdenk/spark-testing-base
[42]: https://maven.apache.org/plugins/maven-source-plugin/
[62]: https://github.com/exasol/artifact-reference-checker-maven-plugin
[72]: https://github.com/evis/scalafix-maven-plugin
[46]: https://maven.apache.org/plugins/maven-jar-plugin/
[13]: http://www.apache.org/licenses/LICENSE-2.0
[10]: https://github.com/google/guava
[40]: http://www.sonatype.com/public-parent/nexus-maven-plugins/nexus-staging/nexus-staging-maven-plugin/
[7]: http://www.apache.org/licenses/LICENSE-2.0.html
[12]: http://www.scalatest.org
[20]: https://logging.apache.org/log4j/2.x/log4j-1.2-api/
[22]: https://github.com/exasol/test-db-builder-java
[2]: https://github.com/exasol/sql-statement-builder
[41]: http://www.eclipse.org/legal/epl-v10.html
[26]: https://github.com/exasol/exasol-testcontainers
[82]: https://maven.apache.org/plugins/maven-site-plugin/
[65]: https://www.gnu.org/licenses/gpl-3.0.html
[21]: https://www.apache.org/licenses/LICENSE-2.0.txt
[34]: https://maven.apache.org/enforcer/maven-enforcer-plugin/
[0]: http://www.exasol.com
[80]: http://maven.apache.org/plugins/maven-install-plugin/
[52]: https://sonatype.github.io/ossindex-maven/maven-plugin/
[38]: https://maven.apache.org/plugins/maven-gpg-plugin/
[28]: http://github.com/davidB/scala-maven-plugin
[6]: http://spark.apache.org/
[44]: https://maven.apache.org/plugins/maven-javadoc-plugin/
[60]: https://github.com/exasol/error-code-crawler-maven-plugin
[48]: https://maven.apache.org/plugins/maven-assembly-plugin/
