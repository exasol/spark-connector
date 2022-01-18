#!/usr/bin/env bash

if ! command -v xmlstarlet &> /dev/null
then
    echo "xmlstarlet tool is not available, please install it to continue."
    exit 1
fi

echo "Copying original POM file"
cp pom.xml release.xml

CURRENT_VERSION=$(mvn --file release.xml -q -Dexec.executable="echo" -Dexec.args='${project.version}' --non-recursive exec:exec)

echo "Updating artifactId value for release POM"
xmlstarlet edit -L --ps -N pom="http://maven.apache.org/POM/4.0.0" \
    --update "/pom:project/pom:artifactId" \
    --value "spark-connector_\${scala.compat.version}" release.xml

echo "Updating version value for release POM"
xmlstarlet edit -L --ps -N pom="http://maven.apache.org/POM/4.0.0" \
    --update "/pom:project/pom:version" \
    --value "$CURRENT_VERSION-spark-\${spark.version}" release.xml
