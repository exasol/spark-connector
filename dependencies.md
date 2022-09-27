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

## Test Dependencies

| Dependency                                 | License                                   |
| ------------------------------------------ | ----------------------------------------- |
| [scalatest][28]                            | [the Apache License, ASL Version 2.0][29] |
| [scalatestplus-mockito][30]                | [Apache-2.0][29]                          |
| [mockito-core][31]                         | [The MIT License][32]                     |
| [Apache Log4j 1.x Compatibility API][33]   | [Apache License, Version 2.0][27]         |
| [Apache Log4j Core][34]                    | [Apache License, Version 2.0][27]         |
| [Test Database Builder for Java][35]       | [MIT License][36]                         |
| [Matcher for SQL Result Sets][37]          | [MIT License][38]                         |
| [Test containers for Exasol on Docker][39] | [MIT License][40]                         |

## Plugin Dependencies

| Dependency                                              | License                                        |
| ------------------------------------------------------- | ---------------------------------------------- |
| [SonarQube Scanner for Maven][41]                       | [GNU LGPL 3][42]                               |
| [scala-maven-plugin][43]                                | [Public domain (Unlicense)][44]                |
| [Apache Maven Compiler Plugin][45]                      | [Apache License, Version 2.0][27]              |
| [Apache Maven Enforcer Plugin][46]                      | [Apache License, Version 2.0][27]              |
| [Maven Flatten Plugin][47]                              | [Apache Software Licenese][10]                 |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][48] | [ASL2][10]                                     |
| [Maven Surefire Plugin][49]                             | [Apache License, Version 2.0][27]              |
| [Versions Maven Plugin][50]                             | [Apache License, Version 2.0][27]              |
| [Apache Maven Deploy Plugin][51]                        | [Apache License, Version 2.0][27]              |
| [Apache Maven GPG Plugin][52]                           | [Apache License, Version 2.0][27]              |
| [Apache Maven Source Plugin][53]                        | [Apache License, Version 2.0][27]              |
| [Apache Maven Javadoc Plugin][54]                       | [Apache License, Version 2.0][27]              |
| [Nexus Staging Maven Plugin][55]                        | [Eclipse Public License][56]                   |
| [ScalaTest Maven Plugin][57]                            | [the Apache License, ASL Version 2.0][29]      |
| [Apache Maven JAR Plugin][58]                           | [Apache License, Version 2.0][27]              |
| [Apache Maven Shade Plugin][59]                         | [Apache License, Version 2.0][27]              |
| [Maven Failsafe Plugin][60]                             | [Apache License, Version 2.0][27]              |
| [Project keeper maven plugin][61]                       | [The MIT License][62]                          |
| [JaCoCo :: Maven Plugin][63]                            | [Eclipse Public License 2.0][64]               |
| [error-code-crawler-maven-plugin][65]                   | [MIT License][66]                              |
| [Reproducible Build Maven Plugin][67]                   | [Apache 2.0][10]                               |
| [Artifact reference checker and unifier][68]            | [MIT][5]                                       |
| [OpenFastTrace Maven Plugin][69]                        | [GNU General Public License v3.0][70]          |
| [Scalastyle Maven Plugin][71]                           | [Apache 2.0][8]                                |
| [spotless-maven-plugin][72]                             | [The Apache Software License, Version 2.0][27] |
| [scalafix-maven-plugin][73]                             | [BSD-3-Clause][74]                             |
| [Maven Clean Plugin][75]                                | [The Apache Software License, Version 2.0][10] |
| [Maven Resources Plugin][76]                            | [The Apache Software License, Version 2.0][10] |
| [Maven Install Plugin][77]                              | [The Apache Software License, Version 2.0][10] |
| [Maven Site Plugin 3][78]                               | [The Apache Software License, Version 2.0][10] |

[0]: https://www.scala-lang.org/
[1]: https://www.apache.org/licenses/LICENSE-2.0
[2]: http://www.exasol.com
[3]: https://docs.exasol.com/connect_exasol/drivers/jdbc.htm
[4]: https://github.com/exasol/sql-statement-builder
[5]: https://opensource.org/licenses/MIT
[6]: https://github.com/exasol/error-reporting-java
[7]: http://spark.apache.org/
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
[28]: http://www.scalatest.org
[29]: http://www.apache.org/licenses/LICENSE-2.0
[30]: https://github.com/scalatest/scalatestplus-mockito
[31]: https://github.com/mockito/mockito
[32]: https://github.com/mockito/mockito/blob/main/LICENSE
[33]: https://logging.apache.org/log4j/2.x/log4j-1.2-api/
[34]: https://logging.apache.org/log4j/2.x/log4j-core/
[35]: https://github.com/exasol/test-db-builder-java/
[36]: https://github.com/exasol/test-db-builder-java/blob/main/LICENSE
[37]: https://github.com/exasol/hamcrest-resultset-matcher/
[38]: https://github.com/exasol/hamcrest-resultset-matcher/blob/main/LICENSE
[39]: https://github.com/exasol/exasol-testcontainers/
[40]: https://github.com/exasol/exasol-testcontainers/blob/main/LICENSE
[41]: http://sonarsource.github.io/sonar-scanner-maven/
[42]: http://www.gnu.org/licenses/lgpl.txt
[43]: http://github.com/davidB/scala-maven-plugin
[44]: http://unlicense.org/
[45]: https://maven.apache.org/plugins/maven-compiler-plugin/
[46]: https://maven.apache.org/enforcer/maven-enforcer-plugin/
[47]: https://www.mojohaus.org/flatten-maven-plugin/
[48]: https://sonatype.github.io/ossindex-maven/maven-plugin/
[49]: https://maven.apache.org/surefire/maven-surefire-plugin/
[50]: http://www.mojohaus.org/versions-maven-plugin/
[51]: https://maven.apache.org/plugins/maven-deploy-plugin/
[52]: https://maven.apache.org/plugins/maven-gpg-plugin/
[53]: https://maven.apache.org/plugins/maven-source-plugin/
[54]: https://maven.apache.org/plugins/maven-javadoc-plugin/
[55]: http://www.sonatype.com/public-parent/nexus-maven-plugins/nexus-staging/nexus-staging-maven-plugin/
[56]: http://www.eclipse.org/legal/epl-v10.html
[57]: https://www.scalatest.org/user_guide/using_the_scalatest_maven_plugin
[58]: https://maven.apache.org/plugins/maven-jar-plugin/
[59]: https://maven.apache.org/plugins/maven-shade-plugin/
[60]: https://maven.apache.org/surefire/maven-failsafe-plugin/
[61]: https://github.com/exasol/project-keeper/
[62]: https://github.com/exasol/project-keeper/blob/main/LICENSE
[63]: https://www.jacoco.org/jacoco/trunk/doc/maven.html
[64]: https://www.eclipse.org/legal/epl-2.0/
[65]: https://github.com/exasol/error-code-crawler-maven-plugin/
[66]: https://github.com/exasol/error-code-crawler-maven-plugin/blob/main/LICENSE
[67]: http://zlika.github.io/reproducible-build-maven-plugin
[68]: https://github.com/exasol/artifact-reference-checker-maven-plugin
[69]: https://github.com/itsallcode/openfasttrace-maven-plugin
[70]: https://www.gnu.org/licenses/gpl-3.0.html
[71]: http://www.scalastyle.org
[72]: https://github.com/diffplug/spotless
[73]: https://github.com/evis/scalafix-maven-plugin
[74]: https://opensource.org/licenses/BSD-3-Clause
[75]: http://maven.apache.org/plugins/maven-clean-plugin/
[76]: http://maven.apache.org/plugins/maven-resources-plugin/
[77]: http://maven.apache.org/plugins/maven-install-plugin/
[78]: http://maven.apache.org/plugins/maven-site-plugin/
