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
| [Maven Surefire Plugin][32]                             | [Apache License, Version 2.0][21]              |
| [ScalaTest Maven Plugin][34]                            | [the Apache License, ASL Version 2.0][13]      |
| [Apache Maven Enforcer Plugin][36]                      | [Apache License, Version 2.0][21]              |
| [Apache Maven Deploy Plugin][38]                        | [Apache License, Version 2.0][21]              |
| [Apache Maven GPG Plugin][40]                           | [Apache License, Version 2.0][21]              |
| [Nexus Staging Maven Plugin][42]                        | [Eclipse Public License][43]                   |
| [Apache Maven Source Plugin][44]                        | [Apache License, Version 2.0][21]              |
| [Apache Maven Javadoc Plugin][46]                       | [Apache License, Version 2.0][21]              |
| [Apache Maven JAR Plugin][48]                           | [Apache License, Version 2.0][21]              |
| [Apache Maven Assembly Plugin][50]                      | [Apache License, Version 2.0][21]              |
| [Versions Maven Plugin][52]                             | [Apache License, Version 2.0][21]              |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][54] | [ASL2][11]                                     |
| [Reproducible Build Maven Plugin][56]                   | [Apache 2.0][11]                               |
| [Project keeper maven plugin][58]                       | [MIT][3]                                       |
| [Maven Failsafe Plugin][60]                             | [Apache License, Version 2.0][21]              |
| [JaCoCo :: Maven Plugin][62]                            | [Eclipse Public License 2.0][63]               |
| [error-code-crawler-maven-plugin][64]                   | [MIT][3]                                       |
| [Artifact reference checker and unifier][66]            | [MIT][3]                                       |
| [OpenFastTrace Maven Plugin][68]                        | [GNU General Public License v3.0][69]          |
| [SCoverage Maven Plugin][70]                            | [The Apache Software License, Version 2.0][11] |
| [Scalastyle Maven Plugin][72]                           | [Apache 2.0][7]                                |
| [spotless-maven-plugin][74]                             | [The Apache Software License, Version 2.0][21] |
| [scalafix-maven-plugin][76]                             | [BSD-3-Clause][77]                             |
| [Apache Maven Clean Plugin][78]                         | [Apache License, Version 2.0][21]              |
| [Apache Maven Resources Plugin][80]                     | [Apache License, Version 2.0][21]              |
| [Apache Maven Install Plugin][82]                       | [Apache License, Version 2.0][11]              |
| [Apache Maven Site Plugin][84]                          | [Apache License, Version 2.0][21]              |

[58]: https://github.com/exasol/project-keeper-maven-plugin
[70]: https://scoverage.github.io/scoverage-maven-plugin/1.4.1/
[14]: https://github.com/scalatest/scalatestplus-mockito
[4]: https://github.com/exasol/error-reporting-java
[11]: http://www.apache.org/licenses/LICENSE-2.0.txt
[72]: http://www.scalastyle.org
[32]: https://maven.apache.org/surefire/maven-surefire-plugin/
[74]: https://github.com/diffplug/spotless
[3]: https://opensource.org/licenses/MIT
[16]: https://github.com/mockito/mockito
[52]: http://www.mojohaus.org/versions-maven-plugin/
[30]: https://maven.apache.org/plugins/maven-compiler-plugin/
[80]: https://maven.apache.org/plugins/maven-resources-plugin/
[68]: https://github.com/itsallcode/openfasttrace-maven-plugin
[78]: https://maven.apache.org/plugins/maven-clean-plugin/
[63]: https://www.eclipse.org/legal/epl-2.0/
[38]: https://maven.apache.org/plugins/maven-deploy-plugin/
[29]: http://unlicense.org/
[62]: https://www.jacoco.org/jacoco/trunk/doc/maven.html
[17]: https://github.com/mockito/mockito/blob/main/LICENSE
[24]: https://github.com/exasol/hamcrest-resultset-matcher
[56]: http://zlika.github.io/reproducible-build-maven-plugin
[77]: https://opensource.org/licenses/BSD-3-Clause
[18]: https://github.com/holdenk/spark-testing-base
[44]: https://maven.apache.org/plugins/maven-source-plugin/
[66]: https://github.com/exasol/artifact-reference-checker-maven-plugin
[76]: https://github.com/evis/scalafix-maven-plugin
[48]: https://maven.apache.org/plugins/maven-jar-plugin/
[1]: LICENSE-exasol-jdbc.txt
[13]: http://www.apache.org/licenses/LICENSE-2.0
[10]: https://github.com/google/guava
[42]: http://www.sonatype.com/public-parent/nexus-maven-plugins/nexus-staging/nexus-staging-maven-plugin/
[7]: http://www.apache.org/licenses/LICENSE-2.0.html
[12]: http://www.scalatest.org
[60]: https://maven.apache.org/surefire/maven-failsafe-plugin/
[20]: https://logging.apache.org/log4j/2.x/log4j-1.2-api/
[22]: https://github.com/exasol/test-db-builder-java
[2]: https://github.com/exasol/sql-statement-builder
[43]: http://www.eclipse.org/legal/epl-v10.html
[26]: https://github.com/exasol/exasol-testcontainers
[84]: https://maven.apache.org/plugins/maven-site-plugin/
[69]: https://www.gnu.org/licenses/gpl-3.0.html
[21]: https://www.apache.org/licenses/LICENSE-2.0.txt
[34]: http://nexus.sonatype.org/oss-repository-hosting.html/scalatest-maven-plugin
[36]: https://maven.apache.org/enforcer/maven-enforcer-plugin/
[0]: http://www.exasol.com
[82]: http://maven.apache.org/plugins/maven-install-plugin/
[54]: https://sonatype.github.io/ossindex-maven/maven-plugin/
[40]: https://maven.apache.org/plugins/maven-gpg-plugin/
[28]: http://github.com/davidB/scala-maven-plugin
[6]: http://spark.apache.org/
[46]: https://maven.apache.org/plugins/maven-javadoc-plugin/
[64]: https://github.com/exasol/error-code-crawler-maven-plugin
[50]: https://maven.apache.org/plugins/maven-assembly-plugin/
