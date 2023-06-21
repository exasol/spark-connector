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
| [Project keeper maven plugin][21]                      | [The MIT License][22]                         |
| [Maven Clean Plugin][23]                               | [The Apache Software License, Version 2.0][9] |
| [Maven Install Plugin][24]                             | [The Apache Software License, Version 2.0][9] |
| [Maven Site Plugin 3][25]                              | [The Apache Software License, Version 2.0][9] |

## Spark Exasol Connector With Jdbc

### Compile Dependencies

| Dependency                                  | License                                                                                                                                                                                             |
| ------------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| [EXASolution JDBC Driver][26]               | [EXAClient License][27]                                                                                                                                                                             |
| [Exasol SQL Statement Builder][28]          | [MIT License][29]                                                                                                                                                                                   |
| [error-reporting-java8][30]                 | [MIT License][31]                                                                                                                                                                                   |
| [spark-connector-common-java][32]           | [MIT License][33]                                                                                                                                                                                   |
| [Spark Project Core][34]                    | [Apache 2.0 License][13]                                                                                                                                                                            |
| [Spark Project SQL][34]                     | [Apache 2.0 License][13]                                                                                                                                                                            |
| [Guava: Google Core Libraries for Java][35] | [Apache License, Version 2.0][9]                                                                                                                                                                    |
| [Netty/All-in-One][36]                      | [Apache License, Version 2.0][37]                                                                                                                                                                   |
| [jackson-databind][38]                      | [The Apache Software License, Version 2.0][3]                                                                                                                                                       |
| Apache Hadoop Client Aggregator             | [Apache License, Version 2.0][3]                                                                                                                                                                    |
| [jersey-core-common][39]                    | [EPL 2.0][40]; [The GNU General Public License (GPL), Version 2, With Classpath Exception][41]; [Apache License, 2.0][13]; [Public Domain][42]                                                      |
| [jersey-media-jaxb][43]                     | [EPL 2.0][40]; [GPL2 w/ CPE][41]; [EDL 1.0][44]; [BSD 2-Clause][45]; [Apache License, 2.0][13]; [Public Domain][42]; [Modified BSD][46]; [jQuery license][47]; [MIT license][48]; [W3C license][49] |
| [jersey-core-server][50]                    | [EPL 2.0][40]; [The GNU General Public License (GPL), Version 2, With Classpath Exception][41]; [Apache License, 2.0][13]; [Modified BSD][46]                                                       |
| [jersey-core-client][51]                    | [EPL 2.0][40]; [GPL2 w/ CPE][41]; [EDL 1.0][44]; [BSD 2-Clause][45]; [Apache License, 2.0][13]; [Public Domain][42]; [Modified BSD][46]; [jQuery license][47]; [MIT license][48]; [W3C license][49] |
| [Apache Avro Mapred API][52]                | [Apache License, Version 2.0][3]                                                                                                                                                                    |

### Test Dependencies

| Dependency                                 | License                                   |
| ------------------------------------------ | ----------------------------------------- |
| [scalatest][53]                            | [the Apache License, ASL Version 2.0][54] |
| [scalatestplus-mockito][55]                | [Apache-2.0][54]                          |
| [mockito-core][56]                         | [The MIT License][57]                     |
| [mockito-junit-jupiter][56]                | [The MIT License][57]                     |
| [Apache Log4j API][58]                     | [Apache License, Version 2.0][3]          |
| [Apache Log4j 1.x Compatibility API][59]   | [Apache License, Version 2.0][3]          |
| [Apache Log4j Core][60]                    | [Apache License, Version 2.0][3]          |
| [Test Database Builder for Java][61]       | [MIT License][62]                         |
| [Matcher for SQL Result Sets][63]          | [MIT License][64]                         |
| [Test containers for Exasol on Docker][65] | [MIT License][66]                         |

### Plugin Dependencies

| Dependency                                             | License                                       |
| ------------------------------------------------------ | --------------------------------------------- |
| [SonarQube Scanner for Maven][0]                       | [GNU LGPL 3][1]                               |
| [scala-maven-plugin][67]                               | [Public domain (Unlicense)][68]               |
| [Apache Maven Compiler Plugin][2]                      | [Apache-2.0][3]                               |
| [Apache Maven Enforcer Plugin][4]                      | [Apache-2.0][3]                               |
| [Maven Flatten Plugin][5]                              | [Apache Software Licenese][3]                 |
| [Apache Maven Toolchains Plugin][6]                    | [Apache License, Version 2.0][3]              |
| [Apache Maven Javadoc Plugin][69]                      | [Apache-2.0][3]                               |
| [ScalaTest Maven Plugin][70]                           | [the Apache License, ASL Version 2.0][54]     |
| [Apache Maven JAR Plugin][71]                          | [Apache License, Version 2.0][3]              |
| [Apache Maven Deploy Plugin][7]                        | [Apache-2.0][3]                               |
| [Apache Maven Shade Plugin][72]                        | [Apache License, Version 2.0][3]              |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][8] | [ASL2][9]                                     |
| [Maven Surefire Plugin][10]                            | [Apache-2.0][3]                               |
| [Versions Maven Plugin][11]                            | [Apache License, Version 2.0][3]              |
| [duplicate-finder-maven-plugin Maven Mojo][12]         | [Apache License 2.0][13]                      |
| [Maven Failsafe Plugin][73]                            | [Apache-2.0][3]                               |
| [JaCoCo :: Maven Plugin][14]                           | [Eclipse Public License 2.0][15]              |
| [error-code-crawler-maven-plugin][16]                  | [MIT License][17]                             |
| [Reproducible Build Maven Plugin][18]                  | [Apache 2.0][9]                               |
| [OpenFastTrace Maven Plugin][19]                       | [GNU General Public License v3.0][20]         |
| [Project keeper maven plugin][21]                      | [The MIT License][22]                         |
| [spotless-maven-plugin][74]                            | [The Apache Software License, Version 2.0][3] |
| [scalafix-maven-plugin][75]                            | [BSD-3-Clause][76]                            |
| [Maven Clean Plugin][23]                               | [The Apache Software License, Version 2.0][9] |
| [Maven Resources Plugin][77]                           | [The Apache Software License, Version 2.0][9] |
| [Maven Install Plugin][24]                             | [The Apache Software License, Version 2.0][9] |
| [Maven Site Plugin 3][25]                              | [The Apache Software License, Version 2.0][9] |

## Spark Exasol Connector With s3

### Compile Dependencies

| Dependency                                  | License                           |
| ------------------------------------------- | --------------------------------- |
| [Scala Library][78]                         | [Apache-2.0][37]                  |
| [spark-connector-common-java][32]           | [MIT License][33]                 |
| [Spark Project Core][34]                    | [Apache 2.0 License][13]          |
| [Spark Project SQL][34]                     | [Apache 2.0 License][13]          |
| Apache Hadoop Client Aggregator             | [Apache License, Version 2.0][3]  |
| [AWS Java SDK :: Services :: Amazon S3][79] | [Apache License, Version 2.0][80] |
| Apache Hadoop Amazon Web Services support   | [Apache License, Version 2.0][3]  |

### Test Dependencies

| Dependency                                      | License                           |
| ----------------------------------------------- | --------------------------------- |
| [JUnit Jupiter (Aggregator)][81]                | [Eclipse Public License v2.0][82] |
| [JUnit Jupiter API][81]                         | [Eclipse Public License v2.0][82] |
| [Test Database Builder for Java][61]            | [MIT License][62]                 |
| [Matcher for SQL Result Sets][63]               | [MIT License][64]                 |
| [Test containers for Exasol on Docker][65]      | [MIT License][66]                 |
| [Testcontainers :: JUnit Jupiter Extension][83] | [MIT][84]                         |
| [Testcontainers :: Localstack][83]              | [MIT][84]                         |
| [AWS Java SDK for Amazon S3][79]                | [Apache License, Version 2.0][80] |

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
| [Apache Maven Shade Plugin][72]                        | [Apache License, Version 2.0][3]              |
| [duplicate-finder-maven-plugin Maven Mojo][12]         | [Apache License 2.0][13]                      |
| [Maven Failsafe Plugin][73]                            | [Apache-2.0][3]                               |
| [JaCoCo :: Maven Plugin][14]                           | [Eclipse Public License 2.0][15]              |
| [error-code-crawler-maven-plugin][16]                  | [MIT License][17]                             |
| [Reproducible Build Maven Plugin][18]                  | [Apache 2.0][9]                               |
| [OpenFastTrace Maven Plugin][19]                       | [GNU General Public License v3.0][20]         |
| [Project keeper maven plugin][21]                      | [The MIT License][22]                         |
| [Maven Clean Plugin][23]                               | [The Apache Software License, Version 2.0][9] |
| [Maven Resources Plugin][77]                           | [The Apache Software License, Version 2.0][9] |
| [Maven JAR Plugin][85]                                 | [The Apache Software License, Version 2.0][9] |
| [Maven Install Plugin][24]                             | [The Apache Software License, Version 2.0][9] |
| [Maven Site Plugin 3][25]                              | [The Apache Software License, Version 2.0][9] |

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
[21]: https://github.com/exasol/project-keeper/
[22]: https://github.com/exasol/project-keeper/blob/main/LICENSE
[23]: http://maven.apache.org/plugins/maven-clean-plugin/
[24]: http://maven.apache.org/plugins/maven-install-plugin/
[25]: http://maven.apache.org/plugins/maven-site-plugin/
[26]: http://www.exasol.com
[27]: https://repo1.maven.org/maven2/com/exasol/exasol-jdbc/7.1.20/exasol-jdbc-7.1.20-license.txt
[28]: https://github.com/exasol/sql-statement-builder/
[29]: https://github.com/exasol/sql-statement-builder/blob/main/LICENSE
[30]: https://github.com/exasol/error-reporting-java/
[31]: https://github.com/exasol/error-reporting-java/blob/main/LICENSE
[32]: https://github.com/exasol/spark-connector-common-java/
[33]: https://github.com/exasol/spark-connector-common-java/blob/main/LICENSE
[34]: https://spark.apache.org/
[35]: https://github.com/google/guava
[36]: https://netty.io/index.html
[37]: https://www.apache.org/licenses/LICENSE-2.0
[38]: https://github.com/FasterXML/jackson
[39]: https://projects.eclipse.org/projects/ee4j.jersey/jersey-common
[40]: http://www.eclipse.org/legal/epl-2.0
[41]: https://www.gnu.org/software/classpath/license.html
[42]: https://creativecommons.org/publicdomain/zero/1.0/
[43]: https://eclipse-ee4j.github.io/jersey/
[44]: http://www.eclipse.org/org/documents/edl-v10.php
[45]: https://opensource.org/licenses/BSD-2-Clause
[46]: https://asm.ow2.io/license.html
[47]: https://github.com/jquery/jquery/blob/main/LICENSE.txt
[48]: http://www.opensource.org/licenses/mit-license.php
[49]: https://www.w3.org/Consortium/Legal/copyright-documents-19990405
[50]: https://projects.eclipse.org/projects/ee4j.jersey/jersey-server
[51]: https://projects.eclipse.org/projects/ee4j.jersey/jersey-client
[52]: https://avro.apache.org
[53]: http://www.scalatest.org
[54]: http://www.apache.org/licenses/LICENSE-2.0
[55]: https://github.com/scalatest/scalatestplus-mockito
[56]: https://github.com/mockito/mockito
[57]: https://github.com/mockito/mockito/blob/main/LICENSE
[58]: https://logging.apache.org/log4j/2.x/log4j-api/
[59]: https://logging.apache.org/log4j/2.x/
[60]: https://logging.apache.org/log4j/2.x/log4j-core/
[61]: https://github.com/exasol/test-db-builder-java/
[62]: https://github.com/exasol/test-db-builder-java/blob/main/LICENSE
[63]: https://github.com/exasol/hamcrest-resultset-matcher/
[64]: https://github.com/exasol/hamcrest-resultset-matcher/blob/main/LICENSE
[65]: https://github.com/exasol/exasol-testcontainers/
[66]: https://github.com/exasol/exasol-testcontainers/blob/main/LICENSE
[67]: http://github.com/davidB/scala-maven-plugin
[68]: http://unlicense.org/
[69]: https://maven.apache.org/plugins/maven-javadoc-plugin/
[70]: https://www.scalatest.org/user_guide/using_the_scalatest_maven_plugin
[71]: https://maven.apache.org/plugins/maven-jar-plugin/
[72]: https://maven.apache.org/plugins/maven-shade-plugin/
[73]: https://maven.apache.org/surefire/maven-failsafe-plugin/
[74]: https://github.com/diffplug/spotless
[75]: https://github.com/evis/scalafix-maven-plugin
[76]: https://opensource.org/licenses/BSD-3-Clause
[77]: http://maven.apache.org/plugins/maven-resources-plugin/
[78]: https://www.scala-lang.org/
[79]: https://aws.amazon.com/sdkforjava
[80]: https://aws.amazon.com/apache2.0
[81]: https://junit.org/junit5/
[82]: https://www.eclipse.org/legal/epl-v20.html
[83]: https://testcontainers.org
[84]: http://opensource.org/licenses/MIT
[85]: http://maven.apache.org/plugins/maven-jar-plugin/
