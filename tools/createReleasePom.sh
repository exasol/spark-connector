#!/usr/bin/env bash

set -o errexit
set -o nounset
set -o pipefail
set -o xtrace

if ! command -v xmlstarlet &>/dev/null; then
	echo "xmlstarlet tool is not available, please install it to continue."
	exit 1
fi

if [[ $# -eq 0 ]]; then
	echo "Please provide the pom build profile (e.g, '-Pspark3.3')"
	exit 1
fi

PROFILE="$1"

echo "Copying original POM file"
cp pom.xml release.xml

PROJECT_VERSION=$(mvn --file release.xml -q -Dexec.executable="echo" -Dexec.args='${project.version}' --non-recursive exec:exec "$PROFILE")
SPARK_VERSION=$(mvn --file release.xml -q -Dexec.executable="echo" -Dexec.args='${spark.version}' --non-recursive exec:exec "$PROFILE")
SCALA_COMPAT_VERSION=$(mvn --file release.xml -q -Dexec.executable="echo" -Dexec.args='${scala.compat.version}' --non-recursive exec:exec "$PROFILE")

echo "Updating artifactId value for release POM"
xmlstarlet edit -L --ps -N pom="http://maven.apache.org/POM/4.0.0" \
	--update "/pom:project/pom:artifactId" \
	--value "spark-connector_$SCALA_COMPAT_VERSION" release.xml

echo "Updating version value for release POM"
xmlstarlet edit -L --ps -N pom="http://maven.apache.org/POM/4.0.0" \
	--update "/pom:project/pom:version" \
	--value "$PROJECT_VERSION-spark-$SPARK_VERSION" release.xml
