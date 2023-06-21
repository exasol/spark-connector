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
| [Apache Maven Toolchains Plugin][6]                    | [Apache License, Version 2.0][3]              |
| [Apache Maven Deploy Plugin][7]                        | [Apache-2.0][3]                               |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][8] | [ASL2][9]                                     |
| [Maven Surefire Plugin][10]                            | [Apache-2.0][3]                               |
| [Versions Maven Plugin][11]                            | [Apache License, Version 2.0][3]              |
| [duplicate-finder-maven-plugin Maven Mojo][12]         | [Apache License 2.0][13]                      |
| [JaCoCo :: Maven Plugin][14]                           | [Eclipse Public License 2.0][15]              |
| [error-code-crawler-maven-plugin][16]                  | [MIT License][17]                             |
| [Reproducible Build Maven Plugin][18]                  | [Apache 2.0][9]                               |
| [OpenFastTrace Maven Plugin][19]                       | [GNU General Public License v3.0][20]         |
| [Maven Clean Plugin][21]                               | [The Apache Software License, Version 2.0][9] |
| [Maven Install Plugin][22]                             | [The Apache Software License, Version 2.0][9] |
| [Maven Site Plugin 3][23]                              | [The Apache Software License, Version 2.0][9] |

## Spark Exasol Connector With Jdbc

### Compile Dependencies

| Dependency                                  | License                                                                                                                                                                                             |
| ------------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| [EXASolution JDBC Driver][24]               | [EXAClient License][25]                                                                                                                                                                             |
| [Exasol SQL Statement Builder][26]          | [MIT License][27]                                                                                                                                                                                   |
| [error-reporting-java8][28]                 | [MIT License][29]                                                                                                                                                                                   |
| [spark-connector-common-java][30]           | [MIT License][31]                                                                                                                                                                                   |
| [Spark Project Core][32]                    | [Apache 2.0 License][13]                                                                                                                                                                            |
| [Spark Project SQL][32]                     | [Apache 2.0 License][13]                                                                                                                                                                            |
| [Guava: Google Core Libraries for Java][33] | [Apache License, Version 2.0][9]                                                                                                                                                                    |
| [Netty/All-in-One][34]                      | [Apache License, Version 2.0][35]                                                                                                                                                                   |
| [jackson-databind][36]                      | [The Apache Software License, Version 2.0][3]                                                                                                                                                       |
| Apache Hadoop Client Aggregator             | [Apache License, Version 2.0][3]                                                                                                                                                                    |
| [jersey-core-common][37]                    | [EPL 2.0][38]; [The GNU General Public License (GPL), Version 2, With Classpath Exception][39]; [Apache License, 2.0][13]; [Public Domain][40]                                                      |
| [jersey-media-jaxb][41]                     | [EPL 2.0][38]; [GPL2 w/ CPE][39]; [EDL 1.0][42]; [BSD 2-Clause][43]; [Apache License, 2.0][13]; [Public Domain][40]; [Modified BSD][44]; [jQuery license][45]; [MIT license][46]; [W3C license][47] |
| [jersey-core-server][48]                    | [EPL 2.0][38]; [The GNU General Public License (GPL), Version 2, With Classpath Exception][39]; [Apache License, 2.0][13]; [Modified BSD][44]                                                       |
| [jersey-core-client][49]                    | [EPL 2.0][38]; [GPL2 w/ CPE][39]; [EDL 1.0][42]; [BSD 2-Clause][43]; [Apache License, 2.0][13]; [Public Domain][40]; [Modified BSD][44]; [jQuery license][45]; [MIT license][46]; [W3C license][47] |
| [Apache Avro Mapred API][50]                | [Apache License, Version 2.0][3]                                                                                                                                                                    |

### Test Dependencies

| Dependency                                 | License                                   |
| ------------------------------------------ | ----------------------------------------- |
| [scalatest][51]                            | [the Apache License, ASL Version 2.0][52] |
| [scalatestplus-mockito][53]                | [Apache-2.0][52]                          |
| [mockito-core][54]                         | [The MIT License][55]                     |
| [mockito-junit-jupiter][54]                | [The MIT License][55]                     |
| [Apache Log4j API][56]                     | [Apache License, Version 2.0][3]          |
| [Apache Log4j 1.x Compatibility API][57]   | [Apache License, Version 2.0][3]          |
| [Apache Log4j Core][58]                    | [Apache License, Version 2.0][3]          |
| [Test Database Builder for Java][59]       | [MIT License][60]                         |
| [Matcher for SQL Result Sets][61]          | [MIT License][62]                         |
| [Test containers for Exasol on Docker][63] | [MIT License][64]                         |

### Plugin Dependencies

| Dependency                                             | License                                       |
| ------------------------------------------------------ | --------------------------------------------- |
| [SonarQube Scanner for Maven][0]                       | [GNU LGPL 3][1]                               |
| [scala-maven-plugin][65]                               | [Public domain (Unlicense)][66]               |
| [Apache Maven Compiler Plugin][2]                      | [Apache-2.0][3]                               |
| [Apache Maven Enforcer Plugin][4]                      | [Apache-2.0][3]                               |
| [Maven Flatten Plugin][5]                              | [Apache Software Licenese][3]                 |
| [Apache Maven Toolchains Plugin][6]                    | [Apache License, Version 2.0][3]              |
| [Apache Maven Javadoc Plugin][67]                      | [Apache-2.0][3]                               |
| [ScalaTest Maven Plugin][68]                           | [the Apache License, ASL Version 2.0][52]     |
| [Apache Maven JAR Plugin][69]                          | [Apache License, Version 2.0][3]              |
| [Apache Maven Deploy Plugin][7]                        | [Apache-2.0][3]                               |
| [Apache Maven Shade Plugin][70]                        | [Apache License, Version 2.0][3]              |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][8] | [ASL2][9]                                     |
| [Maven Surefire Plugin][10]                            | [Apache-2.0][3]                               |
| [Versions Maven Plugin][11]                            | [Apache License, Version 2.0][3]              |
| [duplicate-finder-maven-plugin Maven Mojo][12]         | [Apache License 2.0][13]                      |
| [Maven Failsafe Plugin][71]                            | [Apache-2.0][3]                               |
| [JaCoCo :: Maven Plugin][14]                           | [Eclipse Public License 2.0][15]              |
| [error-code-crawler-maven-plugin][16]                  | [MIT License][17]                             |
| [Reproducible Build Maven Plugin][18]                  | [Apache 2.0][9]                               |
| [OpenFastTrace Maven Plugin][19]                       | [GNU General Public License v3.0][20]         |
| [spotless-maven-plugin][72]                            | [The Apache Software License, Version 2.0][3] |
| [scalafix-maven-plugin][73]                            | [BSD-3-Clause][74]                            |
| [Maven Clean Plugin][21]                               | [The Apache Software License, Version 2.0][9] |
| [Maven Resources Plugin][75]                           | [The Apache Software License, Version 2.0][9] |
| [Maven Install Plugin][22]                             | [The Apache Software License, Version 2.0][9] |
| [Maven Site Plugin 3][23]                              | [The Apache Software License, Version 2.0][9] |

## Spark Exasol Connector With s3

### Compile Dependencies

| Dependency                                  | License                           |
| ------------------------------------------- | --------------------------------- |
| [Scala Library][76]                         | [Apache-2.0][35]                  |
| [spark-connector-common-java][30]           | [MIT License][31]                 |
| [Spark Project Core][32]                    | [Apache 2.0 License][13]          |
| [Spark Project SQL][32]                     | [Apache 2.0 License][13]          |
| Apache Hadoop Client Aggregator             | [Apache License, Version 2.0][3]  |
| [AWS Java SDK :: Services :: Amazon S3][77] | [Apache License, Version 2.0][78] |
| Apache Hadoop Amazon Web Services support   | [Apache License, Version 2.0][3]  |

### Test Dependencies

| Dependency                                      | License                           |
| ----------------------------------------------- | --------------------------------- |
| [JUnit Jupiter (Aggregator)][79]                | [Eclipse Public License v2.0][80] |
| [JUnit Jupiter API][79]                         | [Eclipse Public License v2.0][80] |
| [Test Database Builder for Java][59]            | [MIT License][60]                 |
| [Matcher for SQL Result Sets][61]               | [MIT License][62]                 |
| [Test containers for Exasol on Docker][63]      | [MIT License][64]                 |
| [Testcontainers :: JUnit Jupiter Extension][81] | [MIT][82]                         |
| [Testcontainers :: Localstack][81]              | [MIT][82]                         |
| [AWS Java SDK for Amazon S3][77]                | [Apache License, Version 2.0][78] |

### Plugin Dependencies

| Dependency                                             | License                                       |
| ------------------------------------------------------ | --------------------------------------------- |
| [SonarQube Scanner for Maven][0]                       | [GNU LGPL 3][1]                               |
| [Apache Maven Compiler Plugin][2]                      | [Apache-2.0][3]                               |
| [Apache Maven Enforcer Plugin][4]                      | [Apache-2.0][3]                               |
| [Maven Flatten Plugin][5]                              | [Apache Software Licenese][3]                 |
| [Apache Maven Toolchains Plugin][6]                    | [Apache License, Version 2.0][3]              |
| [Apache Maven Deploy Plugin][7]                        | [Apache-2.0][3]                               |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][8] | [ASL2][9]                                     |
| [Maven Surefire Plugin][10]                            | [Apache-2.0][3]                               |
| [Versions Maven Plugin][11]                            | [Apache License, Version 2.0][3]              |
| [Apache Maven Shade Plugin][70]                        | [Apache License, Version 2.0][3]              |
| [duplicate-finder-maven-plugin Maven Mojo][12]         | [Apache License 2.0][13]                      |
| [Maven Failsafe Plugin][71]                            | [Apache-2.0][3]                               |
| [JaCoCo :: Maven Plugin][14]                           | [Eclipse Public License 2.0][15]              |
| [error-code-crawler-maven-plugin][16]                  | [MIT License][17]                             |
| [Reproducible Build Maven Plugin][18]                  | [Apache 2.0][9]                               |
| [OpenFastTrace Maven Plugin][19]                       | [GNU General Public License v3.0][20]         |
| [Maven Clean Plugin][21]                               | [The Apache Software License, Version 2.0][9] |
| [Maven Resources Plugin][75]                           | [The Apache Software License, Version 2.0][9] |
| [Maven JAR Plugin][83]                                 | [The Apache Software License, Version 2.0][9] |
| [Maven Install Plugin][22]                             | [The Apache Software License, Version 2.0][9] |
| [Maven Site Plugin 3][23]                              | [The Apache Software License, Version 2.0][9] |

[0]: http://sonarsource.github.io/sonar-scanner-maven/
[1]: http://www.gnu.org/licenses/lgpl.txt
[2]: https://maven.apache.org/plugins/maven-compiler-plugin/
[3]: https://www.apache.org/licenses/LICENSE-2.0.txt
[4]: https://maven.apache.org/enforcer/maven-enforcer-plugin/
[5]: https://www.mojohaus.org/flatten-maven-plugin/
[6]: https://maven.apache.org/plugins/maven-toolchains-plugin/
[7]: https://maven.apache.org/plugins/maven-deploy-plugin/
[8]: https://sonatype.github.io/ossindex-maven/maven-plugin/
[9]: http://www.apache.org/licenses/LICENSE-2.0.txt
[10]: https://maven.apache.org/surefire/maven-surefire-plugin/
[11]: https://www.mojohaus.org/versions/versions-maven-plugin/
[12]: https://github.com/basepom/duplicate-finder-maven-plugin
[13]: http://www.apache.org/licenses/LICENSE-2.0.html
[14]: https://www.jacoco.org/jacoco/trunk/doc/maven.html
[15]: https://www.eclipse.org/legal/epl-2.0/
[16]: https://github.com/exasol/error-code-crawler-maven-plugin/
[17]: https://github.com/exasol/error-code-crawler-maven-plugin/blob/main/LICENSE
[18]: http://zlika.github.io/reproducible-build-maven-plugin
[19]: https://github.com/itsallcode/openfasttrace-maven-plugin
[20]: https://www.gnu.org/licenses/gpl-3.0.html
[21]: http://maven.apache.org/plugins/maven-clean-plugin/
[22]: http://maven.apache.org/plugins/maven-install-plugin/
[23]: http://maven.apache.org/plugins/maven-site-plugin/
[24]: http://www.exasol.com
[25]: https://repo1.maven.org/maven2/com/exasol/exasol-jdbc/7.1.20/exasol-jdbc-7.1.20-license.txt
[26]: https://github.com/exasol/sql-statement-builder/
[27]: https://github.com/exasol/sql-statement-builder/blob/main/LICENSE
[28]: https://github.com/exasol/error-reporting-java/
[29]: https://github.com/exasol/error-reporting-java/blob/main/LICENSE
[30]: https://github.com/exasol/spark-connector-common-java/
[31]: https://github.com/exasol/spark-connector-common-java/blob/main/LICENSE
[32]: https://spark.apache.org/
[33]: https://github.com/google/guava
[34]: https://netty.io/index.html
[35]: https://www.apache.org/licenses/LICENSE-2.0
[36]: https://github.com/FasterXML/jackson
[37]: https://projects.eclipse.org/projects/ee4j.jersey/jersey-common
[38]: http://www.eclipse.org/legal/epl-2.0
[39]: https://www.gnu.org/software/classpath/license.html
[40]: https://creativecommons.org/publicdomain/zero/1.0/
[41]: https://eclipse-ee4j.github.io/jersey/
[42]: http://www.eclipse.org/org/documents/edl-v10.php
[43]: https://opensource.org/licenses/BSD-2-Clause
[44]: https://asm.ow2.io/license.html
[45]: https://github.com/jquery/jquery/blob/main/LICENSE.txt
[46]: http://www.opensource.org/licenses/mit-license.php
[47]: https://www.w3.org/Consortium/Legal/copyright-documents-19990405
[48]: https://projects.eclipse.org/projects/ee4j.jersey/jersey-server
[49]: https://projects.eclipse.org/projects/ee4j.jersey/jersey-client
[50]: https://avro.apache.org
[51]: http://www.scalatest.org
[52]: http://www.apache.org/licenses/LICENSE-2.0
[53]: https://github.com/scalatest/scalatestplus-mockito
[54]: https://github.com/mockito/mockito
[55]: https://github.com/mockito/mockito/blob/main/LICENSE
[56]: https://logging.apache.org/log4j/2.x/log4j-api/
[57]: https://logging.apache.org/log4j/2.x/
[58]: https://logging.apache.org/log4j/2.x/log4j-core/
[59]: https://github.com/exasol/test-db-builder-java/
[60]: https://github.com/exasol/test-db-builder-java/blob/main/LICENSE
[61]: https://github.com/exasol/hamcrest-resultset-matcher/
[62]: https://github.com/exasol/hamcrest-resultset-matcher/blob/main/LICENSE
[63]: https://github.com/exasol/exasol-testcontainers/
[64]: https://github.com/exasol/exasol-testcontainers/blob/main/LICENSE
[65]: http://github.com/davidB/scala-maven-plugin
[66]: http://unlicense.org/
[67]: https://maven.apache.org/plugins/maven-javadoc-plugin/
[68]: https://www.scalatest.org/user_guide/using_the_scalatest_maven_plugin
[69]: https://maven.apache.org/plugins/maven-jar-plugin/
[70]: https://maven.apache.org/plugins/maven-shade-plugin/
[71]: https://maven.apache.org/surefire/maven-failsafe-plugin/
[72]: https://github.com/diffplug/spotless
[73]: https://github.com/evis/scalafix-maven-plugin
[74]: https://opensource.org/licenses/BSD-3-Clause
[75]: http://maven.apache.org/plugins/maven-resources-plugin/
[76]: https://www.scala-lang.org/
[77]: https://aws.amazon.com/sdkforjava
[78]: https://aws.amazon.com/apache2.0
[79]: https://junit.org/junit5/
[80]: https://www.eclipse.org/legal/epl-v20.html
[81]: https://testcontainers.org
[82]: http://opensource.org/licenses/MIT
[83]: http://maven.apache.org/plugins/maven-jar-plugin/
