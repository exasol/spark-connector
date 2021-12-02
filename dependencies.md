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
| [Apache Log4j 1.x Compatibility API][18]   | [Apache License, Version 2.0][19]         |
| [Test Database Builder for Java][20]       | [MIT][3]                                  |
| [Matcher for SQL Result Sets][22]          | [MIT][3]                                  |
| [Test containers for Exasol on Docker][24] | [MIT][3]                                  |

## Plugin Dependencies

| Dependency                                              | License                                        |
| ------------------------------------------------------- | ---------------------------------------------- |
| [scala-maven-plugin][26]                                | [Public domain (Unlicense)][27]                |
| [Apache Maven Compiler Plugin][28]                      | [Apache License, Version 2.0][19]              |
| [ScalaTest Maven Plugin][30]                            | [the Apache License, ASL Version 2.0][13]      |
| [Apache Maven Enforcer Plugin][32]                      | [Apache License, Version 2.0][19]              |
| [Apache Maven Deploy Plugin][34]                        | [Apache License, Version 2.0][19]              |
| [Apache Maven GPG Plugin][36]                           | [Apache License, Version 2.0][19]              |
| [Nexus Staging Maven Plugin][38]                        | [Eclipse Public License][39]                   |
| [Apache Maven Source Plugin][40]                        | [Apache License, Version 2.0][19]              |
| [Apache Maven Javadoc Plugin][42]                       | [Apache License, Version 2.0][19]              |
| [Apache Maven JAR Plugin][44]                           | [Apache License, Version 2.0][19]              |
| [Apache Maven Assembly Plugin][46]                      | [Apache License, Version 2.0][19]              |
| [Versions Maven Plugin][48]                             | [Apache License, Version 2.0][19]              |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][50] | [ASL2][11]                                     |
| [Reproducible Build Maven Plugin][52]                   | [Apache 2.0][11]                               |
| [Project keeper maven plugin][54]                       | [MIT][3]                                       |
| [JaCoCo :: Maven Plugin][56]                            | [Eclipse Public License 2.0][57]               |
| [error-code-crawler-maven-plugin][58]                   | [MIT][3]                                       |
| [Artifact reference checker and unifier][60]            | [MIT][3]                                       |
| [OpenFastTrace Maven Plugin][62]                        | [GNU General Public License v3.0][63]          |
| [Scalastyle Maven Plugin][64]                           | [Apache 2.0][7]                                |
| [spotless-maven-plugin][66]                             | [The Apache Software License, Version 2.0][19] |
| [scalafix-maven-plugin][68]                             | [BSD-3-Clause][69]                             |
| [Apache Maven Clean Plugin][70]                         | [Apache License, Version 2.0][19]              |
| [Apache Maven Resources Plugin][72]                     | [Apache License, Version 2.0][19]              |
| [Maven Surefire Plugin][74]                             | [The Apache Software License, Version 2.0][11] |
| [Apache Maven Install Plugin][76]                       | [Apache License, Version 2.0][11]              |
| [Apache Maven Site Plugin][78]                          | [Apache License, Version 2.0][19]              |

[54]: https://github.com/exasol/project-keeper-maven-plugin
[14]: https://github.com/scalatest/scalatestplus-mockito
[4]: https://github.com/exasol/error-reporting-java
[11]: http://www.apache.org/licenses/LICENSE-2.0.txt
[64]: http://www.scalastyle.org
[66]: https://github.com/diffplug/spotless
[1]: https://docs.exasol.com/connect_exasol/drivers/jdbc.htm
[3]: https://opensource.org/licenses/MIT
[16]: https://github.com/mockito/mockito
[48]: http://www.mojohaus.org/versions-maven-plugin/
[28]: https://maven.apache.org/plugins/maven-compiler-plugin/
[72]: https://maven.apache.org/plugins/maven-resources-plugin/
[62]: https://github.com/itsallcode/openfasttrace-maven-plugin
[70]: https://maven.apache.org/plugins/maven-clean-plugin/
[57]: https://www.eclipse.org/legal/epl-2.0/
[34]: https://maven.apache.org/plugins/maven-deploy-plugin/
[27]: http://unlicense.org/
[30]: https://www.scalatest.org/user_guide/using_the_scalatest_maven_plugin
[56]: https://www.jacoco.org/jacoco/trunk/doc/maven.html
[17]: https://github.com/mockito/mockito/blob/main/LICENSE
[22]: https://github.com/exasol/hamcrest-resultset-matcher
[52]: http://zlika.github.io/reproducible-build-maven-plugin
[74]: http://maven.apache.org/surefire/maven-surefire-plugin
[69]: https://opensource.org/licenses/BSD-3-Clause
[40]: https://maven.apache.org/plugins/maven-source-plugin/
[60]: https://github.com/exasol/artifact-reference-checker-maven-plugin
[68]: https://github.com/evis/scalafix-maven-plugin
[44]: https://maven.apache.org/plugins/maven-jar-plugin/
[13]: http://www.apache.org/licenses/LICENSE-2.0
[10]: https://github.com/google/guava
[38]: http://www.sonatype.com/public-parent/nexus-maven-plugins/nexus-staging/nexus-staging-maven-plugin/
[7]: http://www.apache.org/licenses/LICENSE-2.0.html
[12]: http://www.scalatest.org
[18]: https://logging.apache.org/log4j/2.x/log4j-1.2-api/
[20]: https://github.com/exasol/test-db-builder-java
[2]: https://github.com/exasol/sql-statement-builder
[39]: http://www.eclipse.org/legal/epl-v10.html
[24]: https://github.com/exasol/exasol-testcontainers
[78]: https://maven.apache.org/plugins/maven-site-plugin/
[63]: https://www.gnu.org/licenses/gpl-3.0.html
[19]: https://www.apache.org/licenses/LICENSE-2.0.txt
[32]: https://maven.apache.org/enforcer/maven-enforcer-plugin/
[0]: http://www.exasol.com
[76]: http://maven.apache.org/plugins/maven-install-plugin/
[50]: https://sonatype.github.io/ossindex-maven/maven-plugin/
[36]: https://maven.apache.org/plugins/maven-gpg-plugin/
[26]: http://github.com/davidB/scala-maven-plugin
[6]: http://spark.apache.org/
[42]: https://maven.apache.org/plugins/maven-javadoc-plugin/
[58]: https://github.com/exasol/error-code-crawler-maven-plugin
[46]: https://maven.apache.org/plugins/maven-assembly-plugin/
