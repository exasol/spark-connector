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
| [Matcher for SQL Result Sets][37]          | [MIT][5]                                  |
| [Test containers for Exasol on Docker][38] | [MIT][5]                                  |

## Plugin Dependencies

| Dependency                                              | License                                        |
| ------------------------------------------------------- | ---------------------------------------------- |
| [SonarQube Scanner for Maven][39]                       | [GNU LGPL 3][40]                               |
| [scala-maven-plugin][41]                                | [Public domain (Unlicense)][42]                |
| [Apache Maven Compiler Plugin][43]                      | [Apache License, Version 2.0][27]              |
| [ScalaTest Maven Plugin][44]                            | [the Apache License, ASL Version 2.0][29]      |
| [Apache Maven Enforcer Plugin][45]                      | [Apache License, Version 2.0][27]              |
| [Maven Flatten Plugin][46]                              | [Apache Software Licenese][10]                 |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][47] | [ASL2][10]                                     |
| [Reproducible Build Maven Plugin][48]                   | [Apache 2.0][10]                               |
| [Maven Surefire Plugin][49]                             | [Apache License, Version 2.0][27]              |
| [Versions Maven Plugin][50]                             | [Apache License, Version 2.0][27]              |
| [Apache Maven Deploy Plugin][51]                        | [Apache License, Version 2.0][27]              |
| [Apache Maven GPG Plugin][52]                           | [Apache License, Version 2.0][27]              |
| [Apache Maven Source Plugin][53]                        | [Apache License, Version 2.0][27]              |
| [Apache Maven Javadoc Plugin][54]                       | [Apache License, Version 2.0][27]              |
| [Nexus Staging Maven Plugin][55]                        | [Eclipse Public License][56]                   |
| [Apache Maven JAR Plugin][57]                           | [Apache License, Version 2.0][27]              |
| [Apache Maven Shade Plugin][58]                         | [Apache License, Version 2.0][27]              |
| [Maven Failsafe Plugin][59]                             | [Apache License, Version 2.0][27]              |
| [Project keeper maven plugin][60]                       | [The MIT License][61]                          |
| [JaCoCo :: Maven Plugin][62]                            | [Eclipse Public License 2.0][63]               |
| [error-code-crawler-maven-plugin][64]                   | [MIT][5]                                       |
| [Artifact reference checker and unifier][65]            | [MIT][5]                                       |
| [OpenFastTrace Maven Plugin][66]                        | [GNU General Public License v3.0][67]          |
| [Scalastyle Maven Plugin][68]                           | [Apache 2.0][8]                                |
| [spotless-maven-plugin][69]                             | [The Apache Software License, Version 2.0][27] |
| [scalafix-maven-plugin][70]                             | [BSD-3-Clause][71]                             |
| [Apache Maven Clean Plugin][72]                         | [Apache License, Version 2.0][27]              |
| [Apache Maven Resources Plugin][73]                     | [Apache License, Version 2.0][27]              |
| [Apache Maven Install Plugin][74]                       | [Apache License, Version 2.0][10]              |
| [Apache Maven Site Plugin][75]                          | [Apache License, Version 2.0][27]              |

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
[37]: https://github.com/exasol/hamcrest-resultset-matcher
[38]: https://github.com/exasol/exasol-testcontainers
[39]: http://sonarsource.github.io/sonar-scanner-maven/
[40]: http://www.gnu.org/licenses/lgpl.txt
[41]: http://github.com/davidB/scala-maven-plugin
[42]: http://unlicense.org/
[43]: https://maven.apache.org/plugins/maven-compiler-plugin/
[44]: https://www.scalatest.org/user_guide/using_the_scalatest_maven_plugin
[45]: https://maven.apache.org/enforcer/maven-enforcer-plugin/
[46]: https://www.mojohaus.org/flatten-maven-plugin/
[47]: https://sonatype.github.io/ossindex-maven/maven-plugin/
[48]: http://zlika.github.io/reproducible-build-maven-plugin
[49]: https://maven.apache.org/surefire/maven-surefire-plugin/
[50]: http://www.mojohaus.org/versions-maven-plugin/
[51]: https://maven.apache.org/plugins/maven-deploy-plugin/
[52]: https://maven.apache.org/plugins/maven-gpg-plugin/
[53]: https://maven.apache.org/plugins/maven-source-plugin/
[54]: https://maven.apache.org/plugins/maven-javadoc-plugin/
[55]: http://www.sonatype.com/public-parent/nexus-maven-plugins/nexus-staging/nexus-staging-maven-plugin/
[56]: http://www.eclipse.org/legal/epl-v10.html
[57]: https://maven.apache.org/plugins/maven-jar-plugin/
[58]: https://maven.apache.org/plugins/maven-shade-plugin/
[59]: https://maven.apache.org/surefire/maven-failsafe-plugin/
[60]: https://github.com/exasol/project-keeper/
[61]: https://github.com/exasol/project-keeper/blob/main/LICENSE
[62]: https://www.jacoco.org/jacoco/trunk/doc/maven.html
[63]: https://www.eclipse.org/legal/epl-2.0/
[64]: https://github.com/exasol/error-code-crawler-maven-plugin
[65]: https://github.com/exasol/artifact-reference-checker-maven-plugin
[66]: https://github.com/itsallcode/openfasttrace-maven-plugin
[67]: https://www.gnu.org/licenses/gpl-3.0.html
[68]: http://www.scalastyle.org
[69]: https://github.com/diffplug/spotless
[70]: https://github.com/evis/scalafix-maven-plugin
[71]: https://opensource.org/licenses/BSD-3-Clause
[72]: https://maven.apache.org/plugins/maven-clean-plugin/
[73]: https://maven.apache.org/plugins/maven-resources-plugin/
[74]: http://maven.apache.org/plugins/maven-install-plugin/
[75]: https://maven.apache.org/plugins/maven-site-plugin/
