<!-- @formatter:off -->
# Dependencies

## Spark Exasol Connector Parent pom

### Plugin Dependencies

| Dependency                                             | License                               |
| ------------------------------------------------------ | ------------------------------------- |
| [SonarQube Scanner for Maven][0]                       | [GNU LGPL 3][1]                       |
| [Apache Maven Toolchains Plugin][2]                    | [Apache License, Version 2.0][3]      |
| [Apache Maven Compiler Plugin][4]                      | [Apache-2.0][3]                       |
| [Apache Maven Enforcer Plugin][5]                      | [Apache-2.0][3]                       |
| [Maven Flatten Plugin][6]                              | [Apache Software Licenese][3]         |
| [Apache Maven Deploy Plugin][7]                        | [Apache-2.0][3]                       |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][8] | [ASL2][9]                             |
| [Maven Surefire Plugin][10]                            | [Apache-2.0][3]                       |
| [Versions Maven Plugin][11]                            | [Apache License, Version 2.0][3]      |
| [duplicate-finder-maven-plugin Maven Mojo][12]         | [Apache License 2.0][13]              |
| [JaCoCo :: Maven Plugin][14]                           | [Eclipse Public License 2.0][15]      |
| [error-code-crawler-maven-plugin][16]                  | [MIT License][17]                     |
| [Reproducible Build Maven Plugin][18]                  | [Apache 2.0][9]                       |
| [OpenFastTrace Maven Plugin][19]                       | [GNU General Public License v3.0][20] |

## Spark Exasol Connector With Jdbc

### Compile Dependencies

| Dependency                                  | License                                                                                                                                                                                             |
| ------------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| [Exasol JDBC Driver][21]                    | [EXAClient License][22]                                                                                                                                                                             |
| [spark-connector-common-java][23]           | [MIT License][24]                                                                                                                                                                                   |
| [Exasol SQL Statement Builder][25]          | [MIT License][26]                                                                                                                                                                                   |
| [error-reporting-java8][27]                 | [MIT License][28]                                                                                                                                                                                   |
| [Spark Project Core][29]                    | [Apache 2.0 License][13]                                                                                                                                                                            |
| [Spark Project SQL][29]                     | [Apache 2.0 License][13]                                                                                                                                                                            |
| [Guava: Google Core Libraries for Java][30] | [Apache License, Version 2.0][9]                                                                                                                                                                    |
| [Netty/All-in-One][31]                      | [Apache License, Version 2.0][32]                                                                                                                                                                   |
| [jackson-databind][33]                      | [The Apache Software License, Version 2.0][3]                                                                                                                                                       |
| Apache Hadoop Client Aggregator             | [Apache License, Version 2.0][3]                                                                                                                                                                    |
| [jersey-core-common][34]                    | [EPL 2.0][35]; [The GNU General Public License (GPL), Version 2, With Classpath Exception][36]; [Apache License, 2.0][13]; [Public Domain][37]                                                      |
| [jersey-media-jaxb][38]                     | [EPL 2.0][35]; [GPL2 w/ CPE][36]; [EDL 1.0][39]; [BSD 2-Clause][40]; [Apache License, 2.0][13]; [Public Domain][37]; [Modified BSD][41]; [jQuery license][42]; [MIT license][43]; [W3C license][44] |
| [jersey-core-server][45]                    | [EPL 2.0][35]; [The GNU General Public License (GPL), Version 2, With Classpath Exception][36]; [Apache License, 2.0][13]; [Modified BSD][41]                                                       |
| [jersey-core-client][46]                    | [EPL 2.0][35]; [GPL2 w/ CPE][36]; [EDL 1.0][39]; [BSD 2-Clause][40]; [Apache License, 2.0][13]; [Public Domain][37]; [Modified BSD][41]; [jQuery license][42]; [MIT license][43]; [W3C license][44] |
| [Apache Avro Mapred API][47]                | [Apache-2.0][3]                                                                                                                                                                                     |
| [Apache Avro][47]                           | [Apache-2.0][3]                                                                                                                                                                                     |

### Test Dependencies

| Dependency                                 | License                                   |
| ------------------------------------------ | ----------------------------------------- |
| [scalatest][48]                            | [the Apache License, ASL Version 2.0][49] |
| [scalatestplus-mockito][50]                | [Apache-2.0][49]                          |
| [mockito-core][51]                         | [The MIT License][52]                     |
| [mockito-junit-jupiter][51]                | [The MIT License][52]                     |
| [Apache Log4j API][53]                     | [Apache License, Version 2.0][3]          |
| [Apache Log4j 1.x Compatibility API][54]   | [Apache License, Version 2.0][3]          |
| [Apache Log4j Core][55]                    | [Apache License, Version 2.0][3]          |
| [Test Database Builder for Java][56]       | [MIT License][57]                         |
| [Matcher for SQL Result Sets][58]          | [MIT License][59]                         |
| [Test containers for Exasol on Docker][60] | [MIT License][61]                         |

### Plugin Dependencies

| Dependency                                             | License                                       |
| ------------------------------------------------------ | --------------------------------------------- |
| [SonarQube Scanner for Maven][0]                       | [GNU LGPL 3][1]                               |
| [Apache Maven Toolchains Plugin][2]                    | [Apache License, Version 2.0][3]              |
| [scala-maven-plugin][62]                               | [Public domain (Unlicense)][63]               |
| [Apache Maven Compiler Plugin][4]                      | [Apache-2.0][3]                               |
| [Apache Maven Enforcer Plugin][5]                      | [Apache-2.0][3]                               |
| [Maven Flatten Plugin][6]                              | [Apache Software Licenese][3]                 |
| [Apache Maven Deploy Plugin][7]                        | [Apache-2.0][3]                               |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][8] | [ASL2][9]                                     |
| [Maven Surefire Plugin][10]                            | [Apache-2.0][3]                               |
| [Versions Maven Plugin][11]                            | [Apache License, Version 2.0][3]              |
| [ScalaTest Maven Plugin][64]                           | [the Apache License, ASL Version 2.0][49]     |
| [Apache Maven JAR Plugin][65]                          | [Apache License, Version 2.0][3]              |
| [Apache Maven Shade Plugin][66]                        | [Apache License, Version 2.0][3]              |
| [duplicate-finder-maven-plugin Maven Mojo][12]         | [Apache License 2.0][13]                      |
| [Apache Maven GPG Plugin][67]                          | [Apache-2.0][3]                               |
| [Apache Maven Source Plugin][68]                       | [Apache License, Version 2.0][3]              |
| [Apache Maven Javadoc Plugin][69]                      | [Apache-2.0][3]                               |
| [Nexus Staging Maven Plugin][70]                       | [Eclipse Public License][71]                  |
| [Maven Failsafe Plugin][72]                            | [Apache-2.0][3]                               |
| [JaCoCo :: Maven Plugin][14]                           | [Eclipse Public License 2.0][15]              |
| [error-code-crawler-maven-plugin][16]                  | [MIT License][17]                             |
| [Reproducible Build Maven Plugin][18]                  | [Apache 2.0][9]                               |
| [OpenFastTrace Maven Plugin][19]                       | [GNU General Public License v3.0][20]         |
| [spotless-maven-plugin][73]                            | [The Apache Software License, Version 2.0][3] |
| [scalafix-maven-plugin][74]                            | [BSD-3-Clause][75]                            |

## Spark Exasol Connector With s3

### Compile Dependencies

| Dependency                                  | License                           |
| ------------------------------------------- | --------------------------------- |
| [Scala Library][76]                         | [Apache-2.0][32]                  |
| [spark-connector-common-java][23]           | [MIT License][24]                 |
| [Spark Project Core][29]                    | [Apache 2.0 License][13]          |
| [Spark Project SQL][29]                     | [Apache 2.0 License][13]          |
| Apache Hadoop Client Aggregator             | [Apache License, Version 2.0][3]  |
| [Netty/All-in-One][31]                      | [Apache License, Version 2.0][32] |
| [AWS Java SDK :: Services :: Amazon S3][77] | [Apache License, Version 2.0][78] |
| Apache Hadoop Amazon Web Services support   | [Apache License, Version 2.0][3]  |
| [wildfly-openssl][79]                       | [Apache License 2.0][80]          |
| [Apache Avro][47]                           | [Apache-2.0][3]                   |

### Test Dependencies

| Dependency                                      | License                           |
| ----------------------------------------------- | --------------------------------- |
| [JUnit Jupiter (Aggregator)][81]                | [Eclipse Public License v2.0][82] |
| [JUnit Jupiter API][81]                         | [Eclipse Public License v2.0][82] |
| [junit-pioneer][83]                             | [Eclipse Public License v2.0][82] |
| [Test Database Builder for Java][56]            | [MIT License][57]                 |
| [Test utilities for `java.util.logging`][84]    | [MIT][85]                         |
| [Matcher for SQL Result Sets][58]               | [MIT License][59]                 |
| [Test containers for Exasol on Docker][60]      | [MIT License][61]                 |
| [Testcontainers :: JUnit Jupiter Extension][86] | [MIT][87]                         |
| [mockito-junit-jupiter][51]                     | [The MIT License][52]             |
| [Testcontainers :: Localstack][86]              | [MIT][87]                         |
| [AWS Java SDK for Amazon S3][77]                | [Apache License, Version 2.0][78] |

### Plugin Dependencies

| Dependency                                             | License                               |
| ------------------------------------------------------ | ------------------------------------- |
| [SonarQube Scanner for Maven][0]                       | [GNU LGPL 3][1]                       |
| [Apache Maven Toolchains Plugin][2]                    | [Apache License, Version 2.0][3]      |
| [Apache Maven Compiler Plugin][4]                      | [Apache-2.0][3]                       |
| [Apache Maven Enforcer Plugin][5]                      | [Apache-2.0][3]                       |
| [Maven Flatten Plugin][6]                              | [Apache Software Licenese][3]         |
| [Apache Maven Deploy Plugin][7]                        | [Apache-2.0][3]                       |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][8] | [ASL2][9]                             |
| [Maven Surefire Plugin][10]                            | [Apache-2.0][3]                       |
| [Versions Maven Plugin][11]                            | [Apache License, Version 2.0][3]      |
| [Apache Maven Shade Plugin][66]                        | [Apache License, Version 2.0][3]      |
| [duplicate-finder-maven-plugin Maven Mojo][12]         | [Apache License 2.0][13]              |
| [Apache Maven GPG Plugin][67]                          | [Apache-2.0][3]                       |
| [Apache Maven Source Plugin][68]                       | [Apache License, Version 2.0][3]      |
| [Apache Maven Javadoc Plugin][69]                      | [Apache-2.0][3]                       |
| [Nexus Staging Maven Plugin][70]                       | [Eclipse Public License][71]          |
| [Maven Failsafe Plugin][72]                            | [Apache-2.0][3]                       |
| [JaCoCo :: Maven Plugin][14]                           | [Eclipse Public License 2.0][15]      |
| [error-code-crawler-maven-plugin][16]                  | [MIT License][17]                     |
| [Reproducible Build Maven Plugin][18]                  | [Apache 2.0][9]                       |
| [OpenFastTrace Maven Plugin][19]                       | [GNU General Public License v3.0][20] |

[0]: http://sonarsource.github.io/sonar-scanner-maven/
[1]: http://www.gnu.org/licenses/lgpl.txt
[2]: https://maven.apache.org/plugins/maven-toolchains-plugin/
[3]: https://www.apache.org/licenses/LICENSE-2.0.txt
[4]: https://maven.apache.org/plugins/maven-compiler-plugin/
[5]: https://maven.apache.org/enforcer/maven-enforcer-plugin/
[6]: https://www.mojohaus.org/flatten-maven-plugin/
[7]: https://maven.apache.org/plugins/maven-deploy-plugin/
[8]: https://sonatype.github.io/ossindex-maven/maven-plugin/
[9]: http://www.apache.org/licenses/LICENSE-2.0.txt
[10]: https://maven.apache.org/surefire/maven-surefire-plugin/
[11]: https://www.mojohaus.org/versions/versions-maven-plugin/
[12]: https://basepom.github.io/duplicate-finder-maven-plugin
[13]: http://www.apache.org/licenses/LICENSE-2.0.html
[14]: https://www.jacoco.org/jacoco/trunk/doc/maven.html
[15]: https://www.eclipse.org/legal/epl-2.0/
[16]: https://github.com/exasol/error-code-crawler-maven-plugin/
[17]: https://github.com/exasol/error-code-crawler-maven-plugin/blob/main/LICENSE
[18]: http://zlika.github.io/reproducible-build-maven-plugin
[19]: https://github.com/itsallcode/openfasttrace-maven-plugin
[20]: https://www.gnu.org/licenses/gpl-3.0.html
[21]: http://www.exasol.com/
[22]: https://repo1.maven.org/maven2/com/exasol/exasol-jdbc/24.1.2/exasol-jdbc-24.1.2-license.txt
[23]: https://github.com/exasol/spark-connector-common-java/
[24]: https://github.com/exasol/spark-connector-common-java/blob/main/LICENSE
[25]: https://github.com/exasol/sql-statement-builder/
[26]: https://github.com/exasol/sql-statement-builder/blob/main/LICENSE
[27]: https://github.com/exasol/error-reporting-java/
[28]: https://github.com/exasol/error-reporting-java/blob/main/LICENSE
[29]: https://spark.apache.org/
[30]: https://github.com/google/guava
[31]: https://netty.io/index.html
[32]: https://www.apache.org/licenses/LICENSE-2.0
[33]: https://github.com/FasterXML/jackson
[34]: https://projects.eclipse.org/projects/ee4j.jersey/jersey-common
[35]: http://www.eclipse.org/legal/epl-2.0
[36]: https://www.gnu.org/software/classpath/license.html
[37]: https://creativecommons.org/publicdomain/zero/1.0/
[38]: https://eclipse-ee4j.github.io/jersey/
[39]: http://www.eclipse.org/org/documents/edl-v10.php
[40]: https://opensource.org/licenses/BSD-2-Clause
[41]: https://asm.ow2.io/license.html
[42]: https://github.com/jquery/jquery/blob/main/LICENSE.txt
[43]: http://www.opensource.org/licenses/mit-license.php
[44]: https://www.w3.org/Consortium/Legal/copyright-documents-19990405
[45]: https://projects.eclipse.org/projects/ee4j.jersey/jersey-server
[46]: https://projects.eclipse.org/projects/ee4j.jersey/jersey-client
[47]: https://avro.apache.org
[48]: http://www.scalatest.org
[49]: http://www.apache.org/licenses/LICENSE-2.0
[50]: https://github.com/scalatest/scalatestplus-mockito
[51]: https://github.com/mockito/mockito
[52]: https://github.com/mockito/mockito/blob/main/LICENSE
[53]: https://logging.apache.org/log4j/2.x/log4j-api/
[54]: https://logging.apache.org/log4j/2.x/
[55]: https://logging.apache.org/log4j/2.x/log4j-core/
[56]: https://github.com/exasol/test-db-builder-java/
[57]: https://github.com/exasol/test-db-builder-java/blob/main/LICENSE
[58]: https://github.com/exasol/hamcrest-resultset-matcher/
[59]: https://github.com/exasol/hamcrest-resultset-matcher/blob/main/LICENSE
[60]: https://github.com/exasol/exasol-testcontainers/
[61]: https://github.com/exasol/exasol-testcontainers/blob/main/LICENSE
[62]: http://github.com/davidB/scala-maven-plugin
[63]: http://unlicense.org/
[64]: https://www.scalatest.org/user_guide/using_the_scalatest_maven_plugin
[65]: https://maven.apache.org/plugins/maven-jar-plugin/
[66]: https://maven.apache.org/plugins/maven-shade-plugin/
[67]: https://maven.apache.org/plugins/maven-gpg-plugin/
[68]: https://maven.apache.org/plugins/maven-source-plugin/
[69]: https://maven.apache.org/plugins/maven-javadoc-plugin/
[70]: http://www.sonatype.com/public-parent/nexus-maven-plugins/nexus-staging/nexus-staging-maven-plugin/
[71]: http://www.eclipse.org/legal/epl-v10.html
[72]: https://maven.apache.org/surefire/maven-failsafe-plugin/
[73]: https://github.com/diffplug/spotless
[74]: https://github.com/evis/scalafix-maven-plugin
[75]: https://opensource.org/licenses/BSD-3-Clause
[76]: https://www.scala-lang.org/
[77]: https://aws.amazon.com/sdkforjava
[78]: https://aws.amazon.com/apache2.0
[79]: http://www.jboss.org/wildfly-openssl-parent/wildfly-openssl
[80]: http://repository.jboss.org/licenses/apache-2.0.txt
[81]: https://junit.org/junit5/
[82]: https://www.eclipse.org/legal/epl-v20.html
[83]: https://junit-pioneer.org/
[84]: https://github.com/exasol/java-util-logging-testing/
[85]: https://opensource.org/licenses/MIT
[86]: https://java.testcontainers.org
[87]: http://opensource.org/licenses/MIT
