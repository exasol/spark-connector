<!-- @formatter:off -->
# Dependencies

## Spark Exasol Connector Parent pom

### Plugin Dependencies

| Dependency                                             | License                               |
| ------------------------------------------------------ | ------------------------------------- |
| [SonarQube Scanner for Maven][0]                       | [GNU LGPL 3][1]                       |
| [Apache Maven Compiler Plugin][2]                      | [Apache-2.0][3]                       |
| [Apache Maven Enforcer Plugin][4]                      | [Apache-2.0][3]                       |
| [Maven Flatten Plugin][5]                              | [Apache Software Licenese][3]         |
| [Apache Maven Deploy Plugin][6]                        | [Apache-2.0][3]                       |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][7] | [ASL2][8]                             |
| [Maven Surefire Plugin][9]                             | [Apache-2.0][3]                       |
| [Versions Maven Plugin][10]                            | [Apache License, Version 2.0][3]      |
| [duplicate-finder-maven-plugin Maven Mojo][11]         | [Apache License 2.0][12]              |
| [JaCoCo :: Maven Plugin][13]                           | [Eclipse Public License 2.0][14]      |
| [error-code-crawler-maven-plugin][15]                  | [MIT License][16]                     |
| [Reproducible Build Maven Plugin][17]                  | [Apache 2.0][8]                       |
| [OpenFastTrace Maven Plugin][18]                       | [GNU General Public License v3.0][19] |

## Spark Exasol Connector With Jdbc

### Compile Dependencies

| Dependency                                  | License                                                                                                                                                                                             |
| ------------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| [EXASolution JDBC Driver][20]               | [EXAClient License][21]                                                                                                                                                                             |
| [spark-connector-common-java][22]           | [MIT License][23]                                                                                                                                                                                   |
| [Exasol SQL Statement Builder][24]          | [MIT License][25]                                                                                                                                                                                   |
| [error-reporting-java8][26]                 | [MIT License][27]                                                                                                                                                                                   |
| [Spark Project Core][28]                    | [Apache 2.0 License][12]                                                                                                                                                                            |
| [Spark Project SQL][28]                     | [Apache 2.0 License][12]                                                                                                                                                                            |
| [Guava: Google Core Libraries for Java][29] | [Apache License, Version 2.0][8]                                                                                                                                                                    |
| [Netty/All-in-One][30]                      | [Apache License, Version 2.0][31]                                                                                                                                                                   |
| [jackson-databind][32]                      | [The Apache Software License, Version 2.0][3]                                                                                                                                                       |
| Apache Hadoop Client Aggregator             | [Apache License, Version 2.0][3]                                                                                                                                                                    |
| [jersey-core-common][33]                    | [EPL 2.0][34]; [The GNU General Public License (GPL), Version 2, With Classpath Exception][35]; [Apache License, 2.0][12]; [Public Domain][36]                                                      |
| [jersey-media-jaxb][37]                     | [EPL 2.0][34]; [GPL2 w/ CPE][35]; [EDL 1.0][38]; [BSD 2-Clause][39]; [Apache License, 2.0][12]; [Public Domain][36]; [Modified BSD][40]; [jQuery license][41]; [MIT license][42]; [W3C license][43] |
| [jersey-core-server][44]                    | [EPL 2.0][34]; [The GNU General Public License (GPL), Version 2, With Classpath Exception][35]; [Apache License, 2.0][12]; [Modified BSD][40]                                                       |
| [jersey-core-client][45]                    | [EPL 2.0][34]; [GPL2 w/ CPE][35]; [EDL 1.0][38]; [BSD 2-Clause][39]; [Apache License, 2.0][12]; [Public Domain][36]; [Modified BSD][40]; [jQuery license][41]; [MIT license][42]; [W3C license][43] |
| [Apache Avro Mapred API][46]                | [Apache-2.0][3]                                                                                                                                                                                     |
| [Apache Avro][46]                           | [Apache-2.0][3]                                                                                                                                                                                     |

### Test Dependencies

| Dependency                                 | License                                   |
| ------------------------------------------ | ----------------------------------------- |
| [scalatest][47]                            | [the Apache License, ASL Version 2.0][48] |
| [scalatestplus-mockito][49]                | [Apache-2.0][48]                          |
| [mockito-core][50]                         | [The MIT License][51]                     |
| [mockito-junit-jupiter][50]                | [The MIT License][51]                     |
| [Apache Log4j API][52]                     | [Apache License, Version 2.0][3]          |
| [Apache Log4j 1.x Compatibility API][53]   | [Apache License, Version 2.0][3]          |
| [Apache Log4j Core][54]                    | [Apache License, Version 2.0][3]          |
| [Test Database Builder for Java][55]       | [MIT License][56]                         |
| [Matcher for SQL Result Sets][57]          | [MIT License][58]                         |
| [Test containers for Exasol on Docker][59] | [MIT License][60]                         |

### Plugin Dependencies

| Dependency                                             | License                                       |
| ------------------------------------------------------ | --------------------------------------------- |
| [SonarQube Scanner for Maven][0]                       | [GNU LGPL 3][1]                               |
| [scala-maven-plugin][61]                               | [Public domain (Unlicense)][62]               |
| [Apache Maven Compiler Plugin][2]                      | [Apache-2.0][3]                               |
| [Apache Maven Enforcer Plugin][4]                      | [Apache-2.0][3]                               |
| [Maven Flatten Plugin][5]                              | [Apache Software Licenese][3]                 |
| [Apache Maven Deploy Plugin][6]                        | [Apache-2.0][3]                               |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][7] | [ASL2][8]                                     |
| [Maven Surefire Plugin][9]                             | [Apache-2.0][3]                               |
| [Versions Maven Plugin][10]                            | [Apache License, Version 2.0][3]              |
| [ScalaTest Maven Plugin][63]                           | [the Apache License, ASL Version 2.0][48]     |
| [Apache Maven JAR Plugin][64]                          | [Apache License, Version 2.0][3]              |
| [Apache Maven Shade Plugin][65]                        | [Apache License, Version 2.0][3]              |
| [duplicate-finder-maven-plugin Maven Mojo][11]         | [Apache License 2.0][12]                      |
| [Apache Maven GPG Plugin][66]                          | [Apache-2.0][3]                               |
| [Apache Maven Source Plugin][67]                       | [Apache License, Version 2.0][3]              |
| [Apache Maven Javadoc Plugin][68]                      | [Apache-2.0][3]                               |
| [Nexus Staging Maven Plugin][69]                       | [Eclipse Public License][70]                  |
| [Maven Failsafe Plugin][71]                            | [Apache-2.0][3]                               |
| [JaCoCo :: Maven Plugin][13]                           | [Eclipse Public License 2.0][14]              |
| [error-code-crawler-maven-plugin][15]                  | [MIT License][16]                             |
| [Reproducible Build Maven Plugin][17]                  | [Apache 2.0][8]                               |
| [OpenFastTrace Maven Plugin][18]                       | [GNU General Public License v3.0][19]         |
| [spotless-maven-plugin][72]                            | [The Apache Software License, Version 2.0][3] |
| [scalafix-maven-plugin][73]                            | [BSD-3-Clause][74]                            |

## Spark Exasol Connector With s3

### Compile Dependencies

| Dependency                                  | License                           |
| ------------------------------------------- | --------------------------------- |
| [Scala Library][75]                         | [Apache-2.0][31]                  |
| [spark-connector-common-java][22]           | [MIT License][23]                 |
| [Spark Project Core][28]                    | [Apache 2.0 License][12]          |
| [Spark Project SQL][28]                     | [Apache 2.0 License][12]          |
| Apache Hadoop Client Aggregator             | [Apache License, Version 2.0][3]  |
| [Netty/All-in-One][30]                      | [Apache License, Version 2.0][31] |
| [AWS Java SDK :: Services :: Amazon S3][76] | [Apache License, Version 2.0][77] |
| Apache Hadoop Amazon Web Services support   | [Apache License, Version 2.0][3]  |
| [wildfly-openssl][78]                       | [Apache License 2.0][79]          |
| [Apache Avro][46]                           | [Apache-2.0][3]                   |

### Test Dependencies

| Dependency                                      | License                           |
| ----------------------------------------------- | --------------------------------- |
| [JUnit Jupiter (Aggregator)][80]                | [Eclipse Public License v2.0][81] |
| [JUnit Jupiter API][80]                         | [Eclipse Public License v2.0][81] |
| [junit-pioneer][82]                             | [Eclipse Public License v2.0][81] |
| [Test Database Builder for Java][55]            | [MIT License][56]                 |
| [Test utilities for `java.util.logging`][83]    | [MIT][84]                         |
| [Matcher for SQL Result Sets][57]               | [MIT License][58]                 |
| [Test containers for Exasol on Docker][59]      | [MIT License][60]                 |
| [Testcontainers :: JUnit Jupiter Extension][85] | [MIT][86]                         |
| [mockito-junit-jupiter][50]                     | [The MIT License][51]             |
| [Testcontainers :: Localstack][85]              | [MIT][86]                         |
| [AWS Java SDK for Amazon S3][76]                | [Apache License, Version 2.0][77] |

### Plugin Dependencies

| Dependency                                             | License                               |
| ------------------------------------------------------ | ------------------------------------- |
| [SonarQube Scanner for Maven][0]                       | [GNU LGPL 3][1]                       |
| [Apache Maven Compiler Plugin][2]                      | [Apache-2.0][3]                       |
| [Apache Maven Enforcer Plugin][4]                      | [Apache-2.0][3]                       |
| [Maven Flatten Plugin][5]                              | [Apache Software Licenese][3]         |
| [Apache Maven Deploy Plugin][6]                        | [Apache-2.0][3]                       |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][7] | [ASL2][8]                             |
| [Maven Surefire Plugin][9]                             | [Apache-2.0][3]                       |
| [Versions Maven Plugin][10]                            | [Apache License, Version 2.0][3]      |
| [Apache Maven Shade Plugin][65]                        | [Apache License, Version 2.0][3]      |
| [duplicate-finder-maven-plugin Maven Mojo][11]         | [Apache License 2.0][12]              |
| [Apache Maven GPG Plugin][66]                          | [Apache-2.0][3]                       |
| [Apache Maven Source Plugin][67]                       | [Apache License, Version 2.0][3]      |
| [Apache Maven Javadoc Plugin][68]                      | [Apache-2.0][3]                       |
| [Nexus Staging Maven Plugin][69]                       | [Eclipse Public License][70]          |
| [Maven Failsafe Plugin][71]                            | [Apache-2.0][3]                       |
| [JaCoCo :: Maven Plugin][13]                           | [Eclipse Public License 2.0][14]      |
| [error-code-crawler-maven-plugin][15]                  | [MIT License][16]                     |
| [Reproducible Build Maven Plugin][17]                  | [Apache 2.0][8]                       |
| [OpenFastTrace Maven Plugin][18]                       | [GNU General Public License v3.0][19] |

[0]: http://sonarsource.github.io/sonar-scanner-maven/
[1]: http://www.gnu.org/licenses/lgpl.txt
[2]: https://maven.apache.org/plugins/maven-compiler-plugin/
[3]: https://www.apache.org/licenses/LICENSE-2.0.txt
[4]: https://maven.apache.org/enforcer/maven-enforcer-plugin/
[5]: https://www.mojohaus.org/flatten-maven-plugin/
[6]: https://maven.apache.org/plugins/maven-deploy-plugin/
[7]: https://sonatype.github.io/ossindex-maven/maven-plugin/
[8]: http://www.apache.org/licenses/LICENSE-2.0.txt
[9]: https://maven.apache.org/surefire/maven-surefire-plugin/
[10]: https://www.mojohaus.org/versions/versions-maven-plugin/
[11]: https://basepom.github.io/duplicate-finder-maven-plugin
[12]: http://www.apache.org/licenses/LICENSE-2.0.html
[13]: https://www.jacoco.org/jacoco/trunk/doc/maven.html
[14]: https://www.eclipse.org/legal/epl-2.0/
[15]: https://github.com/exasol/error-code-crawler-maven-plugin/
[16]: https://github.com/exasol/error-code-crawler-maven-plugin/blob/main/LICENSE
[17]: http://zlika.github.io/reproducible-build-maven-plugin
[18]: https://github.com/itsallcode/openfasttrace-maven-plugin
[19]: https://www.gnu.org/licenses/gpl-3.0.html
[20]: http://www.exasol.com
[21]: https://repo1.maven.org/maven2/com/exasol/exasol-jdbc/7.1.20/exasol-jdbc-7.1.20-license.txt
[22]: https://github.com/exasol/spark-connector-common-java/
[23]: https://github.com/exasol/spark-connector-common-java/blob/main/LICENSE
[24]: https://github.com/exasol/sql-statement-builder/
[25]: https://github.com/exasol/sql-statement-builder/blob/main/LICENSE
[26]: https://github.com/exasol/error-reporting-java/
[27]: https://github.com/exasol/error-reporting-java/blob/main/LICENSE
[28]: https://spark.apache.org/
[29]: https://github.com/google/guava
[30]: https://netty.io/index.html
[31]: https://www.apache.org/licenses/LICENSE-2.0
[32]: https://github.com/FasterXML/jackson
[33]: https://projects.eclipse.org/projects/ee4j.jersey/jersey-common
[34]: http://www.eclipse.org/legal/epl-2.0
[35]: https://www.gnu.org/software/classpath/license.html
[36]: https://creativecommons.org/publicdomain/zero/1.0/
[37]: https://eclipse-ee4j.github.io/jersey/
[38]: http://www.eclipse.org/org/documents/edl-v10.php
[39]: https://opensource.org/licenses/BSD-2-Clause
[40]: https://asm.ow2.io/license.html
[41]: https://github.com/jquery/jquery/blob/main/LICENSE.txt
[42]: http://www.opensource.org/licenses/mit-license.php
[43]: https://www.w3.org/Consortium/Legal/copyright-documents-19990405
[44]: https://projects.eclipse.org/projects/ee4j.jersey/jersey-server
[45]: https://projects.eclipse.org/projects/ee4j.jersey/jersey-client
[46]: https://avro.apache.org
[47]: http://www.scalatest.org
[48]: http://www.apache.org/licenses/LICENSE-2.0
[49]: https://github.com/scalatest/scalatestplus-mockito
[50]: https://github.com/mockito/mockito
[51]: https://github.com/mockito/mockito/blob/main/LICENSE
[52]: https://logging.apache.org/log4j/2.x/log4j-api/
[53]: https://logging.apache.org/log4j/2.x/
[54]: https://logging.apache.org/log4j/2.x/log4j-core/
[55]: https://github.com/exasol/test-db-builder-java/
[56]: https://github.com/exasol/test-db-builder-java/blob/main/LICENSE
[57]: https://github.com/exasol/hamcrest-resultset-matcher/
[58]: https://github.com/exasol/hamcrest-resultset-matcher/blob/main/LICENSE
[59]: https://github.com/exasol/exasol-testcontainers/
[60]: https://github.com/exasol/exasol-testcontainers/blob/main/LICENSE
[61]: http://github.com/davidB/scala-maven-plugin
[62]: http://unlicense.org/
[63]: https://www.scalatest.org/user_guide/using_the_scalatest_maven_plugin
[64]: https://maven.apache.org/plugins/maven-jar-plugin/
[65]: https://maven.apache.org/plugins/maven-shade-plugin/
[66]: https://maven.apache.org/plugins/maven-gpg-plugin/
[67]: https://maven.apache.org/plugins/maven-source-plugin/
[68]: https://maven.apache.org/plugins/maven-javadoc-plugin/
[69]: http://www.sonatype.com/public-parent/nexus-maven-plugins/nexus-staging/nexus-staging-maven-plugin/
[70]: http://www.eclipse.org/legal/epl-v10.html
[71]: https://maven.apache.org/surefire/maven-failsafe-plugin/
[72]: https://github.com/diffplug/spotless
[73]: https://github.com/evis/scalafix-maven-plugin
[74]: https://opensource.org/licenses/BSD-3-Clause
[75]: https://www.scala-lang.org/
[76]: https://aws.amazon.com/sdkforjava
[77]: https://aws.amazon.com/apache2.0
[78]: http://www.jboss.org/wildfly-openssl-parent/wildfly-openssl
[79]: http://repository.jboss.org/licenses/apache-2.0.txt
[80]: https://junit.org/junit5/
[81]: https://www.eclipse.org/legal/epl-v20.html
[82]: https://junit-pioneer.org/
[83]: https://github.com/exasol/java-util-logging-testing/
[84]: https://opensource.org/licenses/MIT
[85]: https://java.testcontainers.org
[86]: http://opensource.org/licenses/MIT
