<!-- @formatter:off -->
# Dependencies

## Compile Dependencies

| Dependency                                 | License                                                                                                                                                                                            |
| ------------------------------------------ | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| [Scala Library][0]                         | [Apache-2.0][1]                                                                                                                                                                                    |
| [EXASolution JDBC Driver][2]               | [EXAClient License][3]                                                                                                                                                                             |
| [Exasol SQL Statement Builder][4]          | [MIT][5]                                                                                                                                                                                           |
| [error-reporting-java][6]                  | [MIT][5]                                                                                                                                                                                           |
| [Spark Project Core][7]                    | [Apache 2.0 License][8]                                                                                                                                                                            |
| [Spark Project SQL][7]                     | [Apache 2.0 License][8]                                                                                                                                                                            |
| [Guava: Google Core Libraries for Java][9] | [Apache License, Version 2.0][10]                                                                                                                                                                  |
| [Netty/All-in-One][11]                     | [Apache License, Version 2.0][1]                                                                                                                                                                   |
| [jackson-databind][12]                     | [The Apache Software License, Version 2.0][10]                                                                                                                                                     |
| [jersey-core-common][13]                   | [EPL 2.0][14]; [The GNU General Public License (GPL), Version 2, With Classpath Exception][15]; [Apache License, 2.0][8]; [Public Domain][16]                                                      |
| [jersey-media-jaxb][17]                    | [EPL 2.0][14]; [GPL2 w/ CPE][15]; [EDL 1.0][18]; [BSD 2-Clause][19]; [Apache License, 2.0][8]; [Public Domain][16]; [Modified BSD][20]; [jQuery license][21]; [MIT license][22]; [W3C license][23] |
| [jersey-core-server][24]                   | [EPL 2.0][14]; [The GNU General Public License (GPL), Version 2, With Classpath Exception][15]; [Apache License, 2.0][8]; [Modified BSD][20]                                                       |
| [jersey-core-client][25]                   | [EPL 2.0][14]; [GPL2 w/ CPE][15]; [EDL 1.0][18]; [BSD 2-Clause][19]; [Apache License, 2.0][8]; [Public Domain][16]; [Modified BSD][20]; [jQuery license][21]; [MIT license][22]; [W3C license][23] |
| [Apache Avro Mapred API][26]               | [Apache License, Version 2.0][27]                                                                                                                                                                  |
| Apache Hadoop Client Aggregator            | [Apache License, Version 2.0][27]                                                                                                                                                                  |
| [Protocol Buffers [Core]][28]              | [BSD-3-Clause][29]                                                                                                                                                                                 |
| [Apache Commons Text][30]                  | [Apache License, Version 2.0][27]                                                                                                                                                                  |

## Test Dependencies

| Dependency                                 | License                                   |
| ------------------------------------------ | ----------------------------------------- |
| [scalatest][31]                            | [the Apache License, ASL Version 2.0][32] |
| [scalatestplus-mockito][33]                | [Apache-2.0][32]                          |
| [mockito-core][34]                         | [The MIT License][35]                     |
| [Apache Log4j API][36]                     | [Apache License, Version 2.0][27]         |
| [Apache Log4j 1.x Compatibility API][37]   | [Apache License, Version 2.0][27]         |
| [Apache Log4j Core][38]                    | [Apache License, Version 2.0][27]         |
| [Test Database Builder for Java][39]       | [MIT License][40]                         |
| [Matcher for SQL Result Sets][41]          | [MIT License][42]                         |
| [Test containers for Exasol on Docker][43] | [MIT License][44]                         |

## Plugin Dependencies

| Dependency                                              | License                                        |
| ------------------------------------------------------- | ---------------------------------------------- |
| [SonarQube Scanner for Maven][45]                       | [GNU LGPL 3][46]                               |
| [scala-maven-plugin][47]                                | [Public domain (Unlicense)][48]                |
| [Apache Maven Compiler Plugin][49]                      | [Apache License, Version 2.0][27]              |
| [Apache Maven Enforcer Plugin][50]                      | [Apache License, Version 2.0][27]              |
| [Maven Flatten Plugin][51]                              | [Apache Software Licenese][10]                 |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][52] | [ASL2][10]                                     |
| [Maven Surefire Plugin][53]                             | [Apache License, Version 2.0][27]              |
| [Versions Maven Plugin][54]                             | [Apache License, Version 2.0][27]              |
| [Apache Maven Deploy Plugin][55]                        | [Apache License, Version 2.0][27]              |
| [Apache Maven GPG Plugin][56]                           | [Apache License, Version 2.0][27]              |
| [Apache Maven Source Plugin][57]                        | [Apache License, Version 2.0][27]              |
| [Apache Maven Javadoc Plugin][58]                       | [Apache License, Version 2.0][27]              |
| [Nexus Staging Maven Plugin][59]                        | [Eclipse Public License][60]                   |
| [ScalaTest Maven Plugin][61]                            | [the Apache License, ASL Version 2.0][32]      |
| [Apache Maven JAR Plugin][62]                           | [Apache License, Version 2.0][27]              |
| [Apache Maven Shade Plugin][63]                         | [Apache License, Version 2.0][27]              |
| [Maven Failsafe Plugin][64]                             | [Apache License, Version 2.0][27]              |
| [Project keeper maven plugin][65]                       | [The MIT License][66]                          |
| [JaCoCo :: Maven Plugin][67]                            | [Eclipse Public License 2.0][68]               |
| [error-code-crawler-maven-plugin][69]                   | [MIT License][70]                              |
| [Reproducible Build Maven Plugin][71]                   | [Apache 2.0][10]                               |
| [Artifact reference checker and unifier][72]            | [MIT][5]                                       |
| [OpenFastTrace Maven Plugin][73]                        | [GNU General Public License v3.0][74]          |
| [Scalastyle Maven Plugin][75]                           | [Apache 2.0][8]                                |
| [spotless-maven-plugin][76]                             | [The Apache Software License, Version 2.0][27] |
| [scalafix-maven-plugin][77]                             | [BSD-3-Clause][29]                             |
| [Maven Clean Plugin][78]                                | [The Apache Software License, Version 2.0][10] |
| [Maven Resources Plugin][79]                            | [The Apache Software License, Version 2.0][10] |
| [Maven Install Plugin][80]                              | [The Apache Software License, Version 2.0][10] |
| [Maven Site Plugin 3][81]                               | [The Apache Software License, Version 2.0][10] |

[0]: https://www.scala-lang.org/
[1]: https://www.apache.org/licenses/LICENSE-2.0
[2]: http://www.exasol.com
[3]: https://docs.exasol.com/connect_exasol/drivers/jdbc.htm
[4]: https://github.com/exasol/sql-statement-builder
[5]: https://opensource.org/licenses/MIT
[6]: https://github.com/exasol/error-reporting-java
[7]: https://spark.apache.org/
[8]: http://www.apache.org/licenses/LICENSE-2.0.html
[9]: https://github.com/google/guava
[10]: http://www.apache.org/licenses/LICENSE-2.0.txt
[11]: https://netty.io/index.html
[12]: http://github.com/FasterXML/jackson
[13]: https://projects.eclipse.org/projects/ee4j.jersey/jersey-common
[14]: http://www.eclipse.org/legal/epl-2.0
[15]: https://www.gnu.org/software/classpath/license.html
[16]: https://creativecommons.org/publicdomain/zero/1.0/
[17]: https://projects.eclipse.org/projects/ee4j.jersey/project/jersey-media-jaxb
[18]: http://www.eclipse.org/org/documents/edl-v10.php
[19]: https://opensource.org/licenses/BSD-2-Clause
[20]: https://asm.ow2.io/license.html
[21]: https://github.com/jquery/jquery/blob/main/LICENSE.txt
[22]: http://www.opensource.org/licenses/mit-license.php
[23]: https://www.w3.org/Consortium/Legal/copyright-documents-19990405
[24]: https://projects.eclipse.org/projects/ee4j.jersey/jersey-server
[25]: https://projects.eclipse.org/projects/ee4j.jersey/jersey-client
[26]: https://avro.apache.org
[27]: https://www.apache.org/licenses/LICENSE-2.0.txt
[28]: https://developers.google.com/protocol-buffers/protobuf-java/
[29]: https://opensource.org/licenses/BSD-3-Clause
[30]: https://commons.apache.org/proper/commons-text
[31]: http://www.scalatest.org
[32]: http://www.apache.org/licenses/LICENSE-2.0
[33]: https://github.com/scalatest/scalatestplus-mockito
[34]: https://github.com/mockito/mockito
[35]: https://github.com/mockito/mockito/blob/main/LICENSE
[36]: https://logging.apache.org/log4j/2.x/log4j-api/
[37]: https://logging.apache.org/log4j/2.x/log4j-1.2-api/
[38]: https://logging.apache.org/log4j/2.x/log4j-core/
[39]: https://github.com/exasol/test-db-builder-java/
[40]: https://github.com/exasol/test-db-builder-java/blob/main/LICENSE
[41]: https://github.com/exasol/hamcrest-resultset-matcher/
[42]: https://github.com/exasol/hamcrest-resultset-matcher/blob/main/LICENSE
[43]: https://github.com/exasol/exasol-testcontainers/
[44]: https://github.com/exasol/exasol-testcontainers/blob/main/LICENSE
[45]: http://sonarsource.github.io/sonar-scanner-maven/
[46]: http://www.gnu.org/licenses/lgpl.txt
[47]: http://github.com/davidB/scala-maven-plugin
[48]: http://unlicense.org/
[49]: https://maven.apache.org/plugins/maven-compiler-plugin/
[50]: https://maven.apache.org/enforcer/maven-enforcer-plugin/
[51]: https://www.mojohaus.org/flatten-maven-plugin/
[52]: https://sonatype.github.io/ossindex-maven/maven-plugin/
[53]: https://maven.apache.org/surefire/maven-surefire-plugin/
[54]: http://www.mojohaus.org/versions-maven-plugin/
[55]: https://maven.apache.org/plugins/maven-deploy-plugin/
[56]: https://maven.apache.org/plugins/maven-gpg-plugin/
[57]: https://maven.apache.org/plugins/maven-source-plugin/
[58]: https://maven.apache.org/plugins/maven-javadoc-plugin/
[59]: http://www.sonatype.com/public-parent/nexus-maven-plugins/nexus-staging/nexus-staging-maven-plugin/
[60]: http://www.eclipse.org/legal/epl-v10.html
[61]: https://www.scalatest.org/user_guide/using_the_scalatest_maven_plugin
[62]: https://maven.apache.org/plugins/maven-jar-plugin/
[63]: https://maven.apache.org/plugins/maven-shade-plugin/
[64]: https://maven.apache.org/surefire/maven-failsafe-plugin/
[65]: https://github.com/exasol/project-keeper/
[66]: https://github.com/exasol/project-keeper/blob/main/LICENSE
[67]: https://www.jacoco.org/jacoco/trunk/doc/maven.html
[68]: https://www.eclipse.org/legal/epl-2.0/
[69]: https://github.com/exasol/error-code-crawler-maven-plugin/
[70]: https://github.com/exasol/error-code-crawler-maven-plugin/blob/main/LICENSE
[71]: http://zlika.github.io/reproducible-build-maven-plugin
[72]: https://github.com/exasol/artifact-reference-checker-maven-plugin
[73]: https://github.com/itsallcode/openfasttrace-maven-plugin
[74]: https://www.gnu.org/licenses/gpl-3.0.html
[75]: http://www.scalastyle.org
[76]: https://github.com/diffplug/spotless
[77]: https://github.com/evis/scalafix-maven-plugin
[78]: http://maven.apache.org/plugins/maven-clean-plugin/
[79]: http://maven.apache.org/plugins/maven-resources-plugin/
[80]: http://maven.apache.org/plugins/maven-install-plugin/
[81]: http://maven.apache.org/plugins/maven-site-plugin/
