<!-- @formatter:off -->
# Dependencies

## Spark Exasol Connector Parent pom

### Plugin Dependencies

| Dependency                                             | License                                       |
| ------------------------------------------------------ | --------------------------------------------- |
| [SonarQube Scanner for Maven][0]                       | [GNU LGPL 3][1]                               |
| [Apache Maven Compiler Plugin][2]                      | [Apache-2.0][3]                               |
| [Apache Maven Enforcer Plugin][4]                      | [Apache-2.0][3]                               |
| [Maven Flatten Plugin][5]                              | [Apache Software Licenese][3]                 |
| [Apache Maven Deploy Plugin][6]                        | [Apache-2.0][3]                               |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][7] | [ASL2][8]                                     |
| [Maven Surefire Plugin][9]                             | [Apache-2.0][3]                               |
| [Versions Maven Plugin][10]                            | [Apache License, Version 2.0][3]              |
| [duplicate-finder-maven-plugin Maven Mojo][11]         | [Apache License 2.0][12]                      |
| [JaCoCo :: Maven Plugin][13]                           | [Eclipse Public License 2.0][14]              |
| [error-code-crawler-maven-plugin][15]                  | [MIT License][16]                             |
| [Reproducible Build Maven Plugin][17]                  | [Apache 2.0][8]                               |
| [OpenFastTrace Maven Plugin][18]                       | [GNU General Public License v3.0][19]         |
| [Maven Clean Plugin][20]                               | [The Apache Software License, Version 2.0][8] |
| [Maven Install Plugin][21]                             | [The Apache Software License, Version 2.0][8] |
| [Maven Site Plugin 3][22]                              | [The Apache Software License, Version 2.0][8] |

## Spark Exasol Connector With Jdbc

### Compile Dependencies

| Dependency                                  | License                                                                                                                                                                                             |
| ------------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| [EXASolution JDBC Driver][23]               | [EXAClient License][24]                                                                                                                                                                             |
| [spark-connector-common-java][25]           | [MIT License][26]                                                                                                                                                                                   |
| [Exasol SQL Statement Builder][27]          | [MIT License][28]                                                                                                                                                                                   |
| [error-reporting-java8][29]                 | [MIT License][30]                                                                                                                                                                                   |
| [Spark Project Core][31]                    | [Apache 2.0 License][12]                                                                                                                                                                            |
| [Spark Project SQL][31]                     | [Apache 2.0 License][12]                                                                                                                                                                            |
| [Guava: Google Core Libraries for Java][32] | [Apache License, Version 2.0][8]                                                                                                                                                                    |
| [Netty/All-in-One][33]                      | [Apache License, Version 2.0][34]                                                                                                                                                                   |
| [jackson-databind][35]                      | [The Apache Software License, Version 2.0][3]                                                                                                                                                       |
| Apache Hadoop Client Aggregator             | [Apache License, Version 2.0][3]                                                                                                                                                                    |
| [jersey-core-common][36]                    | [EPL 2.0][37]; [The GNU General Public License (GPL), Version 2, With Classpath Exception][38]; [Apache License, 2.0][12]; [Public Domain][39]                                                      |
| [jersey-media-jaxb][40]                     | [EPL 2.0][37]; [GPL2 w/ CPE][38]; [EDL 1.0][41]; [BSD 2-Clause][42]; [Apache License, 2.0][12]; [Public Domain][39]; [Modified BSD][43]; [jQuery license][44]; [MIT license][45]; [W3C license][46] |
| [jersey-core-server][47]                    | [EPL 2.0][37]; [The GNU General Public License (GPL), Version 2, With Classpath Exception][38]; [Apache License, 2.0][12]; [Modified BSD][43]                                                       |
| [jersey-core-client][48]                    | [EPL 2.0][37]; [GPL2 w/ CPE][38]; [EDL 1.0][41]; [BSD 2-Clause][42]; [Apache License, 2.0][12]; [Public Domain][39]; [Modified BSD][43]; [jQuery license][44]; [MIT license][45]; [W3C license][46] |
| [Apache Avro Mapred API][49]                | [Apache-2.0][3]                                                                                                                                                                                     |
| [Apache Avro][49]                           | [Apache-2.0][3]                                                                                                                                                                                     |

### Test Dependencies

| Dependency                                 | License                                   |
| ------------------------------------------ | ----------------------------------------- |
| [scalatest][50]                            | [the Apache License, ASL Version 2.0][51] |
| [scalatestplus-mockito][52]                | [Apache-2.0][51]                          |
| [mockito-core][53]                         | [The MIT License][54]                     |
| [mockito-junit-jupiter][53]                | [The MIT License][54]                     |
| [Apache Log4j API][55]                     | [Apache License, Version 2.0][3]          |
| [Apache Log4j 1.x Compatibility API][56]   | [Apache License, Version 2.0][3]          |
| [Apache Log4j Core][57]                    | [Apache License, Version 2.0][3]          |
| [Test Database Builder for Java][58]       | [MIT License][59]                         |
| [Matcher for SQL Result Sets][60]          | [MIT License][61]                         |
| [Test containers for Exasol on Docker][62] | [MIT License][63]                         |

### Plugin Dependencies

| Dependency                                             | License                                       |
| ------------------------------------------------------ | --------------------------------------------- |
| [SonarQube Scanner for Maven][0]                       | [GNU LGPL 3][1]                               |
| [scala-maven-plugin][64]                               | [Public domain (Unlicense)][65]               |
| [Apache Maven Compiler Plugin][2]                      | [Apache-2.0][3]                               |
| [Apache Maven Enforcer Plugin][4]                      | [Apache-2.0][3]                               |
| [Maven Flatten Plugin][5]                              | [Apache Software Licenese][3]                 |
| [Apache Maven Deploy Plugin][6]                        | [Apache-2.0][3]                               |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][7] | [ASL2][8]                                     |
| [Maven Surefire Plugin][9]                             | [Apache-2.0][3]                               |
| [Versions Maven Plugin][10]                            | [Apache License, Version 2.0][3]              |
| [ScalaTest Maven Plugin][66]                           | [the Apache License, ASL Version 2.0][51]     |
| [Apache Maven JAR Plugin][67]                          | [Apache License, Version 2.0][3]              |
| [Apache Maven Shade Plugin][68]                        | [Apache License, Version 2.0][3]              |
| [duplicate-finder-maven-plugin Maven Mojo][11]         | [Apache License 2.0][12]                      |
| [Apache Maven GPG Plugin][69]                          | [Apache-2.0][3]                               |
| [Apache Maven Source Plugin][70]                       | [Apache License, Version 2.0][3]              |
| [Apache Maven Javadoc Plugin][71]                      | [Apache-2.0][3]                               |
| [Nexus Staging Maven Plugin][72]                       | [Eclipse Public License][73]                  |
| [Maven Failsafe Plugin][74]                            | [Apache-2.0][3]                               |
| [JaCoCo :: Maven Plugin][13]                           | [Eclipse Public License 2.0][14]              |
| [error-code-crawler-maven-plugin][15]                  | [MIT License][16]                             |
| [Reproducible Build Maven Plugin][17]                  | [Apache 2.0][8]                               |
| [OpenFastTrace Maven Plugin][18]                       | [GNU General Public License v3.0][19]         |
| [spotless-maven-plugin][75]                            | [The Apache Software License, Version 2.0][3] |
| [scalafix-maven-plugin][76]                            | [BSD-3-Clause][77]                            |
| [Maven Clean Plugin][20]                               | [The Apache Software License, Version 2.0][8] |
| [Maven Resources Plugin][78]                           | [The Apache Software License, Version 2.0][8] |
| [Maven Install Plugin][21]                             | [The Apache Software License, Version 2.0][8] |
| [Maven Site Plugin 3][22]                              | [The Apache Software License, Version 2.0][8] |

## Spark Exasol Connector With s3

### Compile Dependencies

| Dependency                                  | License                           |
| ------------------------------------------- | --------------------------------- |
| [Scala Library][79]                         | [Apache-2.0][34]                  |
| [spark-connector-common-java][25]           | [MIT License][26]                 |
| [Spark Project Core][31]                    | [Apache 2.0 License][12]          |
| [Spark Project SQL][31]                     | [Apache 2.0 License][12]          |
| Apache Hadoop Client Aggregator             | [Apache License, Version 2.0][3]  |
| [Netty/All-in-One][33]                      | [Apache License, Version 2.0][34] |
| [AWS Java SDK :: Services :: Amazon S3][80] | [Apache License, Version 2.0][81] |
| Apache Hadoop Amazon Web Services support   | [Apache License, Version 2.0][3]  |
| [wildfly-openssl][82]                       | [Apache License 2.0][83]          |
| [Apache Avro][49]                           | [Apache-2.0][3]                   |

### Test Dependencies

| Dependency                                      | License                           |
| ----------------------------------------------- | --------------------------------- |
| [JUnit Jupiter (Aggregator)][84]                | [Eclipse Public License v2.0][85] |
| [JUnit Jupiter API][84]                         | [Eclipse Public License v2.0][85] |
| [junit-pioneer][86]                             | [Eclipse Public License v2.0][85] |
| [Test Database Builder for Java][58]            | [MIT License][59]                 |
| [Test utilities for `java.util.logging`][87]    | [MIT][88]                         |
| [Matcher for SQL Result Sets][60]               | [MIT License][61]                 |
| [Test containers for Exasol on Docker][62]      | [MIT License][63]                 |
| [Testcontainers :: JUnit Jupiter Extension][89] | [MIT][90]                         |
| [mockito-junit-jupiter][53]                     | [The MIT License][54]             |
| [Testcontainers :: Localstack][89]              | [MIT][90]                         |
| [AWS Java SDK for Amazon S3][80]                | [Apache License, Version 2.0][81] |

### Plugin Dependencies

| Dependency                                             | License                                       |
| ------------------------------------------------------ | --------------------------------------------- |
| [SonarQube Scanner for Maven][0]                       | [GNU LGPL 3][1]                               |
| [Apache Maven Compiler Plugin][2]                      | [Apache-2.0][3]                               |
| [Apache Maven Enforcer Plugin][4]                      | [Apache-2.0][3]                               |
| [Maven Flatten Plugin][5]                              | [Apache Software Licenese][3]                 |
| [Apache Maven Deploy Plugin][6]                        | [Apache-2.0][3]                               |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][7] | [ASL2][8]                                     |
| [Maven Surefire Plugin][9]                             | [Apache-2.0][3]                               |
| [Versions Maven Plugin][10]                            | [Apache License, Version 2.0][3]              |
| [Apache Maven Shade Plugin][68]                        | [Apache License, Version 2.0][3]              |
| [duplicate-finder-maven-plugin Maven Mojo][11]         | [Apache License 2.0][12]                      |
| [Apache Maven GPG Plugin][69]                          | [Apache-2.0][3]                               |
| [Apache Maven Source Plugin][70]                       | [Apache License, Version 2.0][3]              |
| [Apache Maven Javadoc Plugin][71]                      | [Apache-2.0][3]                               |
| [Nexus Staging Maven Plugin][72]                       | [Eclipse Public License][73]                  |
| [Maven Failsafe Plugin][74]                            | [Apache-2.0][3]                               |
| [JaCoCo :: Maven Plugin][13]                           | [Eclipse Public License 2.0][14]              |
| [error-code-crawler-maven-plugin][15]                  | [MIT License][16]                             |
| [Reproducible Build Maven Plugin][17]                  | [Apache 2.0][8]                               |
| [OpenFastTrace Maven Plugin][18]                       | [GNU General Public License v3.0][19]         |
| [Maven Clean Plugin][20]                               | [The Apache Software License, Version 2.0][8] |
| [Maven Resources Plugin][78]                           | [The Apache Software License, Version 2.0][8] |
| [Maven JAR Plugin][91]                                 | [The Apache Software License, Version 2.0][8] |
| [Maven Install Plugin][21]                             | [The Apache Software License, Version 2.0][8] |
| [Maven Site Plugin 3][22]                              | [The Apache Software License, Version 2.0][8] |

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
[20]: http://maven.apache.org/plugins/maven-clean-plugin/
[21]: http://maven.apache.org/plugins/maven-install-plugin/
[22]: http://maven.apache.org/plugins/maven-site-plugin/
[23]: http://www.exasol.com
[24]: https://repo1.maven.org/maven2/com/exasol/exasol-jdbc/7.1.20/exasol-jdbc-7.1.20-license.txt
[25]: https://github.com/exasol/spark-connector-common-java/
[26]: https://github.com/exasol/spark-connector-common-java/blob/main/LICENSE
[27]: https://github.com/exasol/sql-statement-builder/
[28]: https://github.com/exasol/sql-statement-builder/blob/main/LICENSE
[29]: https://github.com/exasol/error-reporting-java/
[30]: https://github.com/exasol/error-reporting-java/blob/main/LICENSE
[31]: https://spark.apache.org/
[32]: https://github.com/google/guava
[33]: https://netty.io/index.html
[34]: https://www.apache.org/licenses/LICENSE-2.0
[35]: https://github.com/FasterXML/jackson
[36]: https://projects.eclipse.org/projects/ee4j.jersey/jersey-common
[37]: http://www.eclipse.org/legal/epl-2.0
[38]: https://www.gnu.org/software/classpath/license.html
[39]: https://creativecommons.org/publicdomain/zero/1.0/
[40]: https://eclipse-ee4j.github.io/jersey/
[41]: http://www.eclipse.org/org/documents/edl-v10.php
[42]: https://opensource.org/licenses/BSD-2-Clause
[43]: https://asm.ow2.io/license.html
[44]: https://github.com/jquery/jquery/blob/main/LICENSE.txt
[45]: http://www.opensource.org/licenses/mit-license.php
[46]: https://www.w3.org/Consortium/Legal/copyright-documents-19990405
[47]: https://projects.eclipse.org/projects/ee4j.jersey/jersey-server
[48]: https://projects.eclipse.org/projects/ee4j.jersey/jersey-client
[49]: https://avro.apache.org
[50]: http://www.scalatest.org
[51]: http://www.apache.org/licenses/LICENSE-2.0
[52]: https://github.com/scalatest/scalatestplus-mockito
[53]: https://github.com/mockito/mockito
[54]: https://github.com/mockito/mockito/blob/main/LICENSE
[55]: https://logging.apache.org/log4j/2.x/log4j-api/
[56]: https://logging.apache.org/log4j/2.x/
[57]: https://logging.apache.org/log4j/2.x/log4j-core/
[58]: https://github.com/exasol/test-db-builder-java/
[59]: https://github.com/exasol/test-db-builder-java/blob/main/LICENSE
[60]: https://github.com/exasol/hamcrest-resultset-matcher/
[61]: https://github.com/exasol/hamcrest-resultset-matcher/blob/main/LICENSE
[62]: https://github.com/exasol/exasol-testcontainers/
[63]: https://github.com/exasol/exasol-testcontainers/blob/main/LICENSE
[64]: http://github.com/davidB/scala-maven-plugin
[65]: http://unlicense.org/
[66]: https://www.scalatest.org/user_guide/using_the_scalatest_maven_plugin
[67]: https://maven.apache.org/plugins/maven-jar-plugin/
[68]: https://maven.apache.org/plugins/maven-shade-plugin/
[69]: https://maven.apache.org/plugins/maven-gpg-plugin/
[70]: https://maven.apache.org/plugins/maven-source-plugin/
[71]: https://maven.apache.org/plugins/maven-javadoc-plugin/
[72]: http://www.sonatype.com/public-parent/nexus-maven-plugins/nexus-staging/nexus-staging-maven-plugin/
[73]: http://www.eclipse.org/legal/epl-v10.html
[74]: https://maven.apache.org/surefire/maven-failsafe-plugin/
[75]: https://github.com/diffplug/spotless
[76]: https://github.com/evis/scalafix-maven-plugin
[77]: https://opensource.org/licenses/BSD-3-Clause
[78]: http://maven.apache.org/plugins/maven-resources-plugin/
[79]: https://www.scala-lang.org/
[80]: https://aws.amazon.com/sdkforjava
[81]: https://aws.amazon.com/apache2.0
[82]: http://www.jboss.org/wildfly-openssl-parent/wildfly-openssl
[83]: http://repository.jboss.org/licenses/apache-2.0.txt
[84]: https://junit.org/junit5/
[85]: https://www.eclipse.org/legal/epl-v20.html
[86]: https://junit-pioneer.org/
[87]: https://github.com/exasol/java-util-logging-testing/
[88]: https://opensource.org/licenses/MIT
[89]: https://java.testcontainers.org
[90]: http://opensource.org/licenses/MIT
[91]: http://maven.apache.org/plugins/maven-jar-plugin/
