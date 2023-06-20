<!-- @formatter:off -->
# Dependencies

## Spark Exasol Connector With Jdbc

### Compile Dependencies

| Dependency                                  | License                                                                                                                                                                                            |
| ------------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| [EXASolution JDBC Driver][0]                | [EXAClient License][1]                                                                                                                                                                             |
| [Exasol SQL Statement Builder][2]           | [MIT License][3]                                                                                                                                                                                   |
| [error-reporting-java8][4]                  | [MIT License][5]                                                                                                                                                                                   |
| [spark-connector-common-java][6]            | [MIT License][7]                                                                                                                                                                                   |
| [Spark Project Core][8]                     | [Apache 2.0 License][9]                                                                                                                                                                            |
| [Spark Project SQL][8]                      | [Apache 2.0 License][9]                                                                                                                                                                            |
| [Guava: Google Core Libraries for Java][10] | [Apache License, Version 2.0][11]                                                                                                                                                                  |
| [Netty/All-in-One][12]                      | [Apache License, Version 2.0][13]                                                                                                                                                                  |
| [jackson-databind][14]                      | [The Apache Software License, Version 2.0][15]                                                                                                                                                     |
| Apache Hadoop Client Aggregator             | [Apache License, Version 2.0][15]                                                                                                                                                                  |
| [jersey-core-common][16]                    | [EPL 2.0][17]; [The GNU General Public License (GPL), Version 2, With Classpath Exception][18]; [Apache License, 2.0][9]; [Public Domain][19]                                                      |
| [jersey-media-jaxb][20]                     | [EPL 2.0][17]; [GPL2 w/ CPE][18]; [EDL 1.0][21]; [BSD 2-Clause][22]; [Apache License, 2.0][9]; [Public Domain][19]; [Modified BSD][23]; [jQuery license][24]; [MIT license][25]; [W3C license][26] |
| [jersey-core-server][27]                    | [EPL 2.0][17]; [The GNU General Public License (GPL), Version 2, With Classpath Exception][18]; [Apache License, 2.0][9]; [Modified BSD][23]                                                       |
| [jersey-core-client][28]                    | [EPL 2.0][17]; [GPL2 w/ CPE][18]; [EDL 1.0][21]; [BSD 2-Clause][22]; [Apache License, 2.0][9]; [Public Domain][19]; [Modified BSD][23]; [jQuery license][24]; [MIT license][25]; [W3C license][26] |
| [Apache Avro Mapred API][29]                | [Apache License, Version 2.0][15]                                                                                                                                                                  |

### Test Dependencies

| Dependency                                 | License                                   |
| ------------------------------------------ | ----------------------------------------- |
| [scalatest][30]                            | [the Apache License, ASL Version 2.0][31] |
| [scalatestplus-mockito][32]                | [Apache-2.0][31]                          |
| [mockito-core][33]                         | [The MIT License][34]                     |
| [mockito-junit-jupiter][33]                | [The MIT License][34]                     |
| [Apache Log4j API][35]                     | [Apache License, Version 2.0][15]         |
| [Apache Log4j 1.x Compatibility API][36]   | [Apache License, Version 2.0][15]         |
| [Apache Log4j Core][37]                    | [Apache License, Version 2.0][15]         |
| [Test Database Builder for Java][38]       | [MIT License][39]                         |
| [Matcher for SQL Result Sets][40]          | [MIT License][41]                         |
| [Test containers for Exasol on Docker][42] | [MIT License][43]                         |

### Plugin Dependencies

| Dependency                                              | License                                        |
| ------------------------------------------------------- | ---------------------------------------------- |
| [Apache Maven Javadoc Plugin][44]                       | [Apache-2.0][15]                               |
| [ScalaTest Maven Plugin][45]                            | [the Apache License, ASL Version 2.0][31]      |
| [Apache Maven JAR Plugin][46]                           | [Apache License, Version 2.0][15]              |
| [SonarQube Scanner for Maven][47]                       | [GNU LGPL 3][48]                               |
| [scala-maven-plugin][49]                                | [Public domain (Unlicense)][50]                |
| [Apache Maven Compiler Plugin][51]                      | [Apache-2.0][15]                               |
| [Apache Maven Enforcer Plugin][52]                      | [Apache-2.0][15]                               |
| [Maven Flatten Plugin][53]                              | [Apache Software Licenese][15]                 |
| [Apache Maven Deploy Plugin][54]                        | [Apache-2.0][15]                               |
| [Apache Maven Shade Plugin][55]                         | [Apache License, Version 2.0][15]              |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][56] | [ASL2][11]                                     |
| [OpenFastTrace Maven Plugin][57]                        | [GNU General Public License v3.0][58]          |
| [Project keeper maven plugin][59]                       | [The MIT License][60]                          |
| [Maven Surefire Plugin][61]                             | [Apache-2.0][15]                               |
| [Versions Maven Plugin][62]                             | [Apache License, Version 2.0][15]              |
| [duplicate-finder-maven-plugin Maven Mojo][63]          | [Apache License 2.0][9]                        |
| [Maven Failsafe Plugin][64]                             | [Apache-2.0][15]                               |
| [JaCoCo :: Maven Plugin][65]                            | [Eclipse Public License 2.0][66]               |
| [error-code-crawler-maven-plugin][67]                   | [MIT License][68]                              |
| [Reproducible Build Maven Plugin][69]                   | [Apache 2.0][11]                               |
| [spotless-maven-plugin][70]                             | [The Apache Software License, Version 2.0][15] |
| [scalafix-maven-plugin][71]                             | [BSD-3-Clause][72]                             |
| [Maven Clean Plugin][73]                                | [The Apache Software License, Version 2.0][11] |
| [Maven Resources Plugin][74]                            | [The Apache Software License, Version 2.0][11] |
| [Maven Install Plugin][75]                              | [The Apache Software License, Version 2.0][11] |
| [Maven Site Plugin 3][76]                               | [The Apache Software License, Version 2.0][11] |

## Spark Exasol Connector With s3 Storage Layer

### Compile Dependencies

| Dependency                                  | License                           |
| ------------------------------------------- | --------------------------------- |
| [Scala Library][77]                         | [Apache-2.0][13]                  |
| [spark-connector-common-java][6]            | [MIT License][7]                  |
| [Spark Project Core][8]                     | [Apache 2.0 License][9]           |
| [Spark Project SQL][8]                      | [Apache 2.0 License][9]           |
| Apache Hadoop Client Aggregator             | [Apache License, Version 2.0][15] |
| [AWS Java SDK :: Services :: Amazon S3][78] | [Apache License, Version 2.0][79] |
| Apache Hadoop Amazon Web Services support   | [Apache License, Version 2.0][15] |

### Test Dependencies

| Dependency                                      | License                           |
| ----------------------------------------------- | --------------------------------- |
| [JUnit Jupiter (Aggregator)][80]                | [Eclipse Public License v2.0][81] |
| [JUnit Jupiter API][80]                         | [Eclipse Public License v2.0][81] |
| [Test Database Builder for Java][38]            | [MIT License][39]                 |
| [Matcher for SQL Result Sets][40]               | [MIT License][41]                 |
| [Test containers for Exasol on Docker][42]      | [MIT License][43]                 |
| [Testcontainers :: JUnit Jupiter Extension][82] | [MIT][83]                         |
| [Testcontainers :: Localstack][82]              | [MIT][83]                         |
| [AWS Java SDK for Amazon S3][78]                | [Apache License, Version 2.0][79] |

### Plugin Dependencies

| Dependency                                              | License                                        |
| ------------------------------------------------------- | ---------------------------------------------- |
| [Apache Maven JAR Plugin][46]                           | [Apache License, Version 2.0][15]              |
| [SonarQube Scanner for Maven][47]                       | [GNU LGPL 3][48]                               |
| [Apache Maven Compiler Plugin][51]                      | [Apache-2.0][15]                               |
| [Apache Maven Enforcer Plugin][52]                      | [Apache-2.0][15]                               |
| [Maven Flatten Plugin][53]                              | [Apache Software Licenese][15]                 |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][56] | [ASL2][11]                                     |
| [OpenFastTrace Maven Plugin][57]                        | [GNU General Public License v3.0][58]          |
| [Project keeper maven plugin][59]                       | [The MIT License][60]                          |
| [Maven Surefire Plugin][61]                             | [Apache-2.0][15]                               |
| [Versions Maven Plugin][62]                             | [Apache License, Version 2.0][15]              |
| [Apache Maven Shade Plugin][55]                         | [Apache License, Version 2.0][15]              |
| [duplicate-finder-maven-plugin Maven Mojo][63]          | [Apache License 2.0][9]                        |
| [Maven Failsafe Plugin][64]                             | [Apache-2.0][15]                               |
| [JaCoCo :: Maven Plugin][65]                            | [Eclipse Public License 2.0][66]               |
| [error-code-crawler-maven-plugin][67]                   | [MIT License][68]                              |
| [Reproducible Build Maven Plugin][69]                   | [Apache 2.0][11]                               |
| [Maven Clean Plugin][73]                                | [The Apache Software License, Version 2.0][11] |
| [Maven Resources Plugin][74]                            | [The Apache Software License, Version 2.0][11] |
| [Maven Install Plugin][75]                              | [The Apache Software License, Version 2.0][11] |
| [Maven Deploy Plugin][84]                               | [The Apache Software License, Version 2.0][11] |
| [Maven Site Plugin 3][76]                               | [The Apache Software License, Version 2.0][11] |

## Spark Exasol Connector Distribution

### Compile Dependencies

| Dependency                                         | License              |
| -------------------------------------------------- | -------------------- |
| [Spark Exasol Connector with S3 Storage Layer][85] | [Apache License][86] |

### Plugin Dependencies

| Dependency                                              | License                                        |
| ------------------------------------------------------- | ---------------------------------------------- |
| [Apache Maven JAR Plugin][46]                           | [Apache License, Version 2.0][15]              |
| [SonarQube Scanner for Maven][47]                       | [GNU LGPL 3][48]                               |
| [Apache Maven Compiler Plugin][51]                      | [Apache-2.0][15]                               |
| [Apache Maven Enforcer Plugin][52]                      | [Apache-2.0][15]                               |
| [Maven Flatten Plugin][53]                              | [Apache Software Licenese][15]                 |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][56] | [ASL2][11]                                     |
| [OpenFastTrace Maven Plugin][57]                        | [GNU General Public License v3.0][58]          |
| [Project keeper maven plugin][59]                       | [The MIT License][60]                          |
| [Maven Surefire Plugin][61]                             | [Apache-2.0][15]                               |
| [Versions Maven Plugin][62]                             | [Apache License, Version 2.0][15]              |
| [Apache Maven Shade Plugin][55]                         | [Apache License, Version 2.0][15]              |
| [duplicate-finder-maven-plugin Maven Mojo][63]          | [Apache License 2.0][9]                        |
| [Apache Maven Deploy Plugin][54]                        | [Apache-2.0][15]                               |
| [Apache Maven GPG Plugin][87]                           | [Apache License, Version 2.0][15]              |
| [Apache Maven Source Plugin][88]                        | [Apache License, Version 2.0][15]              |
| [Apache Maven Javadoc Plugin][44]                       | [Apache-2.0][15]                               |
| [Nexus Staging Maven Plugin][89]                        | [Eclipse Public License][90]                   |
| [JaCoCo :: Maven Plugin][65]                            | [Eclipse Public License 2.0][66]               |
| [error-code-crawler-maven-plugin][67]                   | [MIT License][68]                              |
| [Reproducible Build Maven Plugin][69]                   | [Apache 2.0][11]                               |
| [Maven Clean Plugin][73]                                | [The Apache Software License, Version 2.0][11] |
| [Maven Resources Plugin][74]                            | [The Apache Software License, Version 2.0][11] |
| [Maven Install Plugin][75]                              | [The Apache Software License, Version 2.0][11] |
| [Maven Site Plugin 3][76]                               | [The Apache Software License, Version 2.0][11] |

[0]: http://www.exasol.com
[1]: https://repo1.maven.org/maven2/com/exasol/exasol-jdbc/7.1.20/exasol-jdbc-7.1.20-license.txt
[2]: https://github.com/exasol/sql-statement-builder/
[3]: https://github.com/exasol/sql-statement-builder/blob/main/LICENSE
[4]: https://github.com/exasol/error-reporting-java/
[5]: https://github.com/exasol/error-reporting-java/blob/main/LICENSE
[6]: https://github.com/exasol/spark-connector-common-java/
[7]: https://github.com/exasol/spark-connector-common-java/blob/main/LICENSE
[8]: https://spark.apache.org/
[9]: http://www.apache.org/licenses/LICENSE-2.0.html
[10]: https://github.com/google/guava
[11]: http://www.apache.org/licenses/LICENSE-2.0.txt
[12]: https://netty.io/index.html
[13]: https://www.apache.org/licenses/LICENSE-2.0
[14]: https://github.com/FasterXML/jackson
[15]: https://www.apache.org/licenses/LICENSE-2.0.txt
[16]: https://projects.eclipse.org/projects/ee4j.jersey/jersey-common
[17]: http://www.eclipse.org/legal/epl-2.0
[18]: https://www.gnu.org/software/classpath/license.html
[19]: https://creativecommons.org/publicdomain/zero/1.0/
[20]: https://eclipse-ee4j.github.io/jersey/
[21]: http://www.eclipse.org/org/documents/edl-v10.php
[22]: https://opensource.org/licenses/BSD-2-Clause
[23]: https://asm.ow2.io/license.html
[24]: https://github.com/jquery/jquery/blob/main/LICENSE.txt
[25]: http://www.opensource.org/licenses/mit-license.php
[26]: https://www.w3.org/Consortium/Legal/copyright-documents-19990405
[27]: https://projects.eclipse.org/projects/ee4j.jersey/jersey-server
[28]: https://projects.eclipse.org/projects/ee4j.jersey/jersey-client
[29]: https://avro.apache.org
[30]: http://www.scalatest.org
[31]: http://www.apache.org/licenses/LICENSE-2.0
[32]: https://github.com/scalatest/scalatestplus-mockito
[33]: https://github.com/mockito/mockito
[34]: https://github.com/mockito/mockito/blob/main/LICENSE
[35]: https://logging.apache.org/log4j/2.x/log4j-api/
[36]: https://logging.apache.org/log4j/2.x/
[37]: https://logging.apache.org/log4j/2.x/log4j-core/
[38]: https://github.com/exasol/test-db-builder-java/
[39]: https://github.com/exasol/test-db-builder-java/blob/main/LICENSE
[40]: https://github.com/exasol/hamcrest-resultset-matcher/
[41]: https://github.com/exasol/hamcrest-resultset-matcher/blob/main/LICENSE
[42]: https://github.com/exasol/exasol-testcontainers/
[43]: https://github.com/exasol/exasol-testcontainers/blob/main/LICENSE
[44]: https://maven.apache.org/plugins/maven-javadoc-plugin/
[45]: https://www.scalatest.org/user_guide/using_the_scalatest_maven_plugin
[46]: https://maven.apache.org/plugins/maven-jar-plugin/
[47]: http://sonarsource.github.io/sonar-scanner-maven/
[48]: http://www.gnu.org/licenses/lgpl.txt
[49]: http://github.com/davidB/scala-maven-plugin
[50]: http://unlicense.org/
[51]: https://maven.apache.org/plugins/maven-compiler-plugin/
[52]: https://maven.apache.org/enforcer/maven-enforcer-plugin/
[53]: https://www.mojohaus.org/flatten-maven-plugin/
[54]: https://maven.apache.org/plugins/maven-deploy-plugin/
[55]: https://maven.apache.org/plugins/maven-shade-plugin/
[56]: https://sonatype.github.io/ossindex-maven/maven-plugin/
[57]: https://github.com/itsallcode/openfasttrace-maven-plugin
[58]: https://www.gnu.org/licenses/gpl-3.0.html
[59]: https://github.com/exasol/project-keeper/
[60]: https://github.com/exasol/project-keeper/blob/main/LICENSE
[61]: https://maven.apache.org/surefire/maven-surefire-plugin/
[62]: https://www.mojohaus.org/versions/versions-maven-plugin/
[63]: https://github.com/basepom/duplicate-finder-maven-plugin
[64]: https://maven.apache.org/surefire/maven-failsafe-plugin/
[65]: https://www.jacoco.org/jacoco/trunk/doc/maven.html
[66]: https://www.eclipse.org/legal/epl-2.0/
[67]: https://github.com/exasol/error-code-crawler-maven-plugin/
[68]: https://github.com/exasol/error-code-crawler-maven-plugin/blob/main/LICENSE
[69]: http://zlika.github.io/reproducible-build-maven-plugin
[70]: https://github.com/diffplug/spotless
[71]: https://github.com/evis/scalafix-maven-plugin
[72]: https://opensource.org/licenses/BSD-3-Clause
[73]: http://maven.apache.org/plugins/maven-clean-plugin/
[74]: http://maven.apache.org/plugins/maven-resources-plugin/
[75]: http://maven.apache.org/plugins/maven-install-plugin/
[76]: http://maven.apache.org/plugins/maven-site-plugin/
[77]: https://www.scala-lang.org/
[78]: https://aws.amazon.com/sdkforjava
[79]: https://aws.amazon.com/apache2.0
[80]: https://junit.org/junit5/
[81]: https://www.eclipse.org/legal/epl-v20.html
[82]: https://testcontainers.org
[83]: http://opensource.org/licenses/MIT
[84]: http://maven.apache.org/plugins/maven-deploy-plugin/
[85]: https://github.com/exasol/spark-connector/
[86]: https://github.com/exasol/spark-connector/blob/main/LICENSE
[87]: https://maven.apache.org/plugins/maven-gpg-plugin/
[88]: https://maven.apache.org/plugins/maven-source-plugin/
[89]: http://www.sonatype.com/public-parent/nexus-maven-plugins/nexus-staging/nexus-staging-maven-plugin/
[90]: http://www.eclipse.org/legal/epl-v10.html
