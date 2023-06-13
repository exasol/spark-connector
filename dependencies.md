<!-- @formatter:off -->
# Dependencies

## Spark Exasol Connector Parent pom

### Plugin Dependencies

| Dependency                                             | License                                       |
| ------------------------------------------------------ | --------------------------------------------- |
| [SonarQube Scanner for Maven][0]                       | [GNU LGPL 3][1]                               |
| [scala-maven-plugin][2]                                | [Public domain (Unlicense)][3]                |
| [Apache Maven Compiler Plugin][4]                      | [Apache-2.0][5]                               |
| [Apache Maven Enforcer Plugin][6]                      | [Apache-2.0][5]                               |
| [Maven Flatten Plugin][7]                              | [Apache Software Licenese][5]                 |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][8] | [ASL2][9]                                     |
| [Maven Surefire Plugin][10]                            | [Apache-2.0][5]                               |
| [Versions Maven Plugin][11]                            | [Apache License, Version 2.0][5]              |
| [duplicate-finder-maven-plugin Maven Mojo][12]         | [Apache License 2.0][13]                      |
| [Apache Maven Deploy Plugin][14]                       | [Apache-2.0][5]                               |
| [Apache Maven GPG Plugin][15]                          | [Apache License, Version 2.0][5]              |
| [Apache Maven Source Plugin][16]                       | [Apache License, Version 2.0][5]              |
| [Apache Maven Javadoc Plugin][17]                      | [Apache-2.0][5]                               |
| [Nexus Staging Maven Plugin][18]                       | [Eclipse Public License][19]                  |
| [ScalaTest Maven Plugin][20]                           | [the Apache License, ASL Version 2.0][21]     |
| [Apache Maven JAR Plugin][22]                          | [Apache License, Version 2.0][5]              |
| [Apache Maven Shade Plugin][23]                        | [Apache License, Version 2.0][5]              |
| [Maven Failsafe Plugin][24]                            | [Apache-2.0][5]                               |
| [JaCoCo :: Maven Plugin][25]                           | [Eclipse Public License 2.0][26]              |
| [error-code-crawler-maven-plugin][27]                  | [MIT License][28]                             |
| [Reproducible Build Maven Plugin][29]                  | [Apache 2.0][9]                               |
| [Project keeper maven plugin][30]                      | [The MIT License][31]                         |
| [Artifact reference checker and unifier][32]           | [MIT License][33]                             |
| [OpenFastTrace Maven Plugin][34]                       | [GNU General Public License v3.0][35]         |
| [spotless-maven-plugin][36]                            | [The Apache Software License, Version 2.0][5] |
| [scalafix-maven-plugin][37]                            | [BSD-3-Clause][38]                            |
| [Maven Clean Plugin][39]                               | [The Apache Software License, Version 2.0][9] |
| [Maven Install Plugin][40]                             | [The Apache Software License, Version 2.0][9] |
| [Maven Site Plugin 3][41]                              | [The Apache Software License, Version 2.0][9] |

## Spark Exasol Connector With Jdbc

### Compile Dependencies

| Dependency                                  | License                                                                                                                                                                                             |
| ------------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| [Scala Library][42]                         | [Apache-2.0][43]                                                                                                                                                                                    |
| [EXASolution JDBC Driver][44]               | [EXAClient License][45]                                                                                                                                                                             |
| [Exasol SQL Statement Builder][46]          | [MIT License][47]                                                                                                                                                                                   |
| [error-reporting-java8][48]                 | [MIT License][49]                                                                                                                                                                                   |
| [spark-connector-common-java][50]           | [MIT License][51]                                                                                                                                                                                   |
| [Spark Project Core][52]                    | [Apache 2.0 License][13]                                                                                                                                                                            |
| [Spark Project SQL][52]                     | [Apache 2.0 License][13]                                                                                                                                                                            |
| [Guava: Google Core Libraries for Java][53] | [Apache License, Version 2.0][9]                                                                                                                                                                    |
| [Netty/All-in-One][54]                      | [Apache License, Version 2.0][43]                                                                                                                                                                   |
| [jackson-databind][55]                      | [The Apache Software License, Version 2.0][5]                                                                                                                                                       |
| [jersey-core-common][56]                    | [EPL 2.0][57]; [The GNU General Public License (GPL), Version 2, With Classpath Exception][58]; [Apache License, 2.0][13]; [Public Domain][59]                                                      |
| [jersey-media-jaxb][60]                     | [EPL 2.0][57]; [GPL2 w/ CPE][58]; [EDL 1.0][61]; [BSD 2-Clause][62]; [Apache License, 2.0][13]; [Public Domain][59]; [Modified BSD][63]; [jQuery license][64]; [MIT license][65]; [W3C license][66] |
| [jersey-core-server][67]                    | [EPL 2.0][57]; [The GNU General Public License (GPL), Version 2, With Classpath Exception][58]; [Apache License, 2.0][13]; [Modified BSD][63]                                                       |
| [jersey-core-client][68]                    | [EPL 2.0][57]; [GPL2 w/ CPE][58]; [EDL 1.0][61]; [BSD 2-Clause][62]; [Apache License, 2.0][13]; [Public Domain][59]; [Modified BSD][63]; [jQuery license][64]; [MIT license][65]; [W3C license][66] |
| [Apache Avro Mapred API][69]                | [Apache License, Version 2.0][5]                                                                                                                                                                    |
| Apache Hadoop Client Aggregator             | [Apache License, Version 2.0][5]                                                                                                                                                                    |
| [Protocol Buffers [Core]][70]               | [BSD-3-Clause][38]                                                                                                                                                                                  |
| [Apache Commons Text][71]                   | [Apache License, Version 2.0][5]                                                                                                                                                                    |
| [Woodstox][72]                              | [The Apache License, Version 2.0][9]                                                                                                                                                                |
| [AWS Java SDK :: Services :: Amazon S3][73] | [Apache License, Version 2.0][74]                                                                                                                                                                   |
| Apache Hadoop Amazon Web Services support   | [Apache License, Version 2.0][5]                                                                                                                                                                    |

### Test Dependencies

| Dependency                                      | License                                   |
| ----------------------------------------------- | ----------------------------------------- |
| [scalatest][75]                                 | [the Apache License, ASL Version 2.0][21] |
| [scalatestplus-mockito][76]                     | [Apache-2.0][21]                          |
| [mockito-core][77]                              | [The MIT License][78]                     |
| [mockito-junit-jupiter][77]                     | [The MIT License][78]                     |
| [JUnit Jupiter (Aggregator)][79]                | [Eclipse Public License v2.0][80]         |
| [JUnit Jupiter API][79]                         | [Eclipse Public License v2.0][80]         |
| [Apache Log4j API][81]                          | [Apache License, Version 2.0][5]          |
| [Apache Log4j 1.x Compatibility API][82]        | [Apache License, Version 2.0][5]          |
| [Apache Log4j Core][83]                         | [Apache License, Version 2.0][5]          |
| [Test Database Builder for Java][84]            | [MIT License][85]                         |
| [Matcher for SQL Result Sets][86]               | [MIT License][87]                         |
| [Test containers for Exasol on Docker][88]      | [MIT License][89]                         |
| [Testcontainers :: JUnit Jupiter Extension][90] | [MIT][91]                                 |
| [Testcontainers :: Localstack][90]              | [MIT][91]                                 |
| [AWS Java SDK for Amazon S3][73]                | [Apache License, Version 2.0][74]         |
| [EqualsVerifier | release normal jar][92]       | [Apache License, Version 2.0][5]          |

### Plugin Dependencies

| Dependency                                             | License                                       |
| ------------------------------------------------------ | --------------------------------------------- |
| [SonarQube Scanner for Maven][0]                       | [GNU LGPL 3][1]                               |
| [scala-maven-plugin][2]                                | [Public domain (Unlicense)][3]                |
| [Apache Maven Compiler Plugin][4]                      | [Apache-2.0][5]                               |
| [Apache Maven Enforcer Plugin][6]                      | [Apache-2.0][5]                               |
| [Maven Flatten Plugin][7]                              | [Apache Software Licenese][5]                 |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][8] | [ASL2][9]                                     |
| [Maven Surefire Plugin][10]                            | [Apache-2.0][5]                               |
| [Versions Maven Plugin][11]                            | [Apache License, Version 2.0][5]              |
| [duplicate-finder-maven-plugin Maven Mojo][12]         | [Apache License 2.0][13]                      |
| [Apache Maven Deploy Plugin][14]                       | [Apache-2.0][5]                               |
| [Apache Maven GPG Plugin][15]                          | [Apache License, Version 2.0][5]              |
| [Apache Maven Source Plugin][16]                       | [Apache License, Version 2.0][5]              |
| [Apache Maven Javadoc Plugin][17]                      | [Apache-2.0][5]                               |
| [Nexus Staging Maven Plugin][18]                       | [Eclipse Public License][19]                  |
| [ScalaTest Maven Plugin][20]                           | [the Apache License, ASL Version 2.0][21]     |
| [Apache Maven JAR Plugin][22]                          | [Apache License, Version 2.0][5]              |
| [Apache Maven Shade Plugin][23]                        | [Apache License, Version 2.0][5]              |
| [Maven Failsafe Plugin][24]                            | [Apache-2.0][5]                               |
| [JaCoCo :: Maven Plugin][25]                           | [Eclipse Public License 2.0][26]              |
| [error-code-crawler-maven-plugin][27]                  | [MIT License][28]                             |
| [Reproducible Build Maven Plugin][29]                  | [Apache 2.0][9]                               |
| [Project keeper maven plugin][30]                      | [The MIT License][31]                         |
| [Artifact reference checker and unifier][32]           | [MIT License][33]                             |
| [OpenFastTrace Maven Plugin][34]                       | [GNU General Public License v3.0][35]         |
| [spotless-maven-plugin][36]                            | [The Apache Software License, Version 2.0][5] |
| [scalafix-maven-plugin][37]                            | [BSD-3-Clause][38]                            |
| [Maven Clean Plugin][39]                               | [The Apache Software License, Version 2.0][9] |
| [Maven Resources Plugin][93]                           | [The Apache Software License, Version 2.0][9] |
| [Maven Install Plugin][40]                             | [The Apache Software License, Version 2.0][9] |
| [Maven Site Plugin 3][41]                              | [The Apache Software License, Version 2.0][9] |

## Spark Exasol Connector With s3 Storage Layer

### Compile Dependencies

| Dependency          | License          |
| ------------------- | ---------------- |
| [Scala Library][42] | [Apache-2.0][43] |

### Test Dependencies

| Dependency                                      | License                                   |
| ----------------------------------------------- | ----------------------------------------- |
| [scalatest][75]                                 | [the Apache License, ASL Version 2.0][21] |
| [scalatestplus-mockito][76]                     | [Apache-2.0][21]                          |
| [mockito-core][77]                              | [The MIT License][78]                     |
| [mockito-junit-jupiter][77]                     | [The MIT License][78]                     |
| [JUnit Jupiter (Aggregator)][79]                | [Eclipse Public License v2.0][80]         |
| [JUnit Jupiter API][79]                         | [Eclipse Public License v2.0][80]         |
| [Apache Log4j API][81]                          | [Apache License, Version 2.0][5]          |
| [Apache Log4j 1.x Compatibility API][82]        | [Apache License, Version 2.0][5]          |
| [Apache Log4j Core][83]                         | [Apache License, Version 2.0][5]          |
| [Test Database Builder for Java][84]            | [MIT License][85]                         |
| [Matcher for SQL Result Sets][86]               | [MIT License][87]                         |
| [Test containers for Exasol on Docker][88]      | [MIT License][89]                         |
| [Testcontainers :: JUnit Jupiter Extension][90] | [MIT][91]                                 |
| [Testcontainers :: Localstack][90]              | [MIT][91]                                 |
| [AWS Java SDK for Amazon S3][73]                | [Apache License, Version 2.0][74]         |
| [EqualsVerifier | release normal jar][92]       | [Apache License, Version 2.0][5]          |

### Plugin Dependencies

| Dependency                                             | License                                       |
| ------------------------------------------------------ | --------------------------------------------- |
| [SonarQube Scanner for Maven][0]                       | [GNU LGPL 3][1]                               |
| [scala-maven-plugin][2]                                | [Public domain (Unlicense)][3]                |
| [Apache Maven Compiler Plugin][4]                      | [Apache-2.0][5]                               |
| [Apache Maven Enforcer Plugin][6]                      | [Apache-2.0][5]                               |
| [Maven Flatten Plugin][7]                              | [Apache Software Licenese][5]                 |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][8] | [ASL2][9]                                     |
| [Maven Surefire Plugin][10]                            | [Apache-2.0][5]                               |
| [Versions Maven Plugin][11]                            | [Apache License, Version 2.0][5]              |
| [duplicate-finder-maven-plugin Maven Mojo][12]         | [Apache License 2.0][13]                      |
| [Apache Maven Deploy Plugin][14]                       | [Apache-2.0][5]                               |
| [Apache Maven GPG Plugin][15]                          | [Apache License, Version 2.0][5]              |
| [Apache Maven Source Plugin][16]                       | [Apache License, Version 2.0][5]              |
| [Apache Maven Javadoc Plugin][17]                      | [Apache-2.0][5]                               |
| [Nexus Staging Maven Plugin][18]                       | [Eclipse Public License][19]                  |
| [ScalaTest Maven Plugin][20]                           | [the Apache License, ASL Version 2.0][21]     |
| [Apache Maven JAR Plugin][22]                          | [Apache License, Version 2.0][5]              |
| [Apache Maven Shade Plugin][23]                        | [Apache License, Version 2.0][5]              |
| [Maven Failsafe Plugin][24]                            | [Apache-2.0][5]                               |
| [JaCoCo :: Maven Plugin][25]                           | [Eclipse Public License 2.0][26]              |
| [error-code-crawler-maven-plugin][27]                  | [MIT License][28]                             |
| [Reproducible Build Maven Plugin][29]                  | [Apache 2.0][9]                               |
| [Project keeper maven plugin][30]                      | [The MIT License][31]                         |
| [Artifact reference checker and unifier][32]           | [MIT License][33]                             |
| [OpenFastTrace Maven Plugin][34]                       | [GNU General Public License v3.0][35]         |
| [spotless-maven-plugin][36]                            | [The Apache Software License, Version 2.0][5] |
| [scalafix-maven-plugin][37]                            | [BSD-3-Clause][38]                            |
| [Maven Clean Plugin][39]                               | [The Apache Software License, Version 2.0][9] |
| [Maven Resources Plugin][93]                           | [The Apache Software License, Version 2.0][9] |
| [Maven Install Plugin][40]                             | [The Apache Software License, Version 2.0][9] |
| [Maven Site Plugin 3][41]                              | [The Apache Software License, Version 2.0][9] |

[0]: http://sonarsource.github.io/sonar-scanner-maven/
[1]: http://www.gnu.org/licenses/lgpl.txt
[2]: http://github.com/davidB/scala-maven-plugin
[3]: http://unlicense.org/
[4]: https://maven.apache.org/plugins/maven-compiler-plugin/
[5]: https://www.apache.org/licenses/LICENSE-2.0.txt
[6]: https://maven.apache.org/enforcer/maven-enforcer-plugin/
[7]: https://www.mojohaus.org/flatten-maven-plugin/
[8]: https://sonatype.github.io/ossindex-maven/maven-plugin/
[9]: http://www.apache.org/licenses/LICENSE-2.0.txt
[10]: https://maven.apache.org/surefire/maven-surefire-plugin/
[11]: https://www.mojohaus.org/versions/versions-maven-plugin/
[12]: https://github.com/basepom/duplicate-finder-maven-plugin
[13]: http://www.apache.org/licenses/LICENSE-2.0.html
[14]: https://maven.apache.org/plugins/maven-deploy-plugin/
[15]: https://maven.apache.org/plugins/maven-gpg-plugin/
[16]: https://maven.apache.org/plugins/maven-source-plugin/
[17]: https://maven.apache.org/plugins/maven-javadoc-plugin/
[18]: http://www.sonatype.com/public-parent/nexus-maven-plugins/nexus-staging/nexus-staging-maven-plugin/
[19]: http://www.eclipse.org/legal/epl-v10.html
[20]: https://www.scalatest.org/user_guide/using_the_scalatest_maven_plugin
[21]: http://www.apache.org/licenses/LICENSE-2.0
[22]: https://maven.apache.org/plugins/maven-jar-plugin/
[23]: https://maven.apache.org/plugins/maven-shade-plugin/
[24]: https://maven.apache.org/surefire/maven-failsafe-plugin/
[25]: https://www.jacoco.org/jacoco/trunk/doc/maven.html
[26]: https://www.eclipse.org/legal/epl-2.0/
[27]: https://github.com/exasol/error-code-crawler-maven-plugin/
[28]: https://github.com/exasol/error-code-crawler-maven-plugin/blob/main/LICENSE
[29]: http://zlika.github.io/reproducible-build-maven-plugin
[30]: https://github.com/exasol/project-keeper/
[31]: https://github.com/exasol/project-keeper/blob/main/LICENSE
[32]: https://github.com/exasol/artifact-reference-checker-maven-plugin/
[33]: https://github.com/exasol/artifact-reference-checker-maven-plugin/blob/main/LICENSE
[34]: https://github.com/itsallcode/openfasttrace-maven-plugin
[35]: https://www.gnu.org/licenses/gpl-3.0.html
[36]: https://github.com/diffplug/spotless
[37]: https://github.com/evis/scalafix-maven-plugin
[38]: https://opensource.org/licenses/BSD-3-Clause
[39]: http://maven.apache.org/plugins/maven-clean-plugin/
[40]: http://maven.apache.org/plugins/maven-install-plugin/
[41]: http://maven.apache.org/plugins/maven-site-plugin/
[42]: https://www.scala-lang.org/
[43]: https://www.apache.org/licenses/LICENSE-2.0
[44]: http://www.exasol.com
[45]: https://repo1.maven.org/maven2/com/exasol/exasol-jdbc/7.1.20/exasol-jdbc-7.1.20-license.txt
[46]: https://github.com/exasol/sql-statement-builder/
[47]: https://github.com/exasol/sql-statement-builder/blob/main/LICENSE
[48]: https://github.com/exasol/error-reporting-java/
[49]: https://github.com/exasol/error-reporting-java/blob/main/LICENSE
[50]: https://github.com/exasol/spark-connector-common-java/
[51]: https://github.com/exasol/spark-connector-common-java/blob/main/LICENSE
[52]: https://spark.apache.org/
[53]: https://github.com/google/guava
[54]: https://netty.io/index.html
[55]: https://github.com/FasterXML/jackson
[56]: https://projects.eclipse.org/projects/ee4j.jersey/jersey-common
[57]: http://www.eclipse.org/legal/epl-2.0
[58]: https://www.gnu.org/software/classpath/license.html
[59]: https://creativecommons.org/publicdomain/zero/1.0/
[60]: https://eclipse-ee4j.github.io/jersey/
[61]: http://www.eclipse.org/org/documents/edl-v10.php
[62]: https://opensource.org/licenses/BSD-2-Clause
[63]: https://asm.ow2.io/license.html
[64]: https://github.com/jquery/jquery/blob/main/LICENSE.txt
[65]: http://www.opensource.org/licenses/mit-license.php
[66]: https://www.w3.org/Consortium/Legal/copyright-documents-19990405
[67]: https://projects.eclipse.org/projects/ee4j.jersey/jersey-server
[68]: https://projects.eclipse.org/projects/ee4j.jersey/jersey-client
[69]: https://avro.apache.org
[70]: https://developers.google.com/protocol-buffers/docs/javatutorial
[71]: https://commons.apache.org/proper/commons-text
[72]: https://github.com/FasterXML/woodstox
[73]: https://aws.amazon.com/sdkforjava
[74]: https://aws.amazon.com/apache2.0
[75]: http://www.scalatest.org
[76]: https://github.com/scalatest/scalatestplus-mockito
[77]: https://github.com/mockito/mockito
[78]: https://github.com/mockito/mockito/blob/main/LICENSE
[79]: https://junit.org/junit5/
[80]: https://www.eclipse.org/legal/epl-v20.html
[81]: https://logging.apache.org/log4j/2.x/log4j-api/
[82]: https://logging.apache.org/log4j/2.x/
[83]: https://logging.apache.org/log4j/2.x/log4j-core/
[84]: https://github.com/exasol/test-db-builder-java/
[85]: https://github.com/exasol/test-db-builder-java/blob/main/LICENSE
[86]: https://github.com/exasol/hamcrest-resultset-matcher/
[87]: https://github.com/exasol/hamcrest-resultset-matcher/blob/main/LICENSE
[88]: https://github.com/exasol/exasol-testcontainers/
[89]: https://github.com/exasol/exasol-testcontainers/blob/main/LICENSE
[90]: https://testcontainers.org
[91]: http://opensource.org/licenses/MIT
[92]: https://www.jqno.nl/equalsverifier
[93]: http://maven.apache.org/plugins/maven-resources-plugin/
