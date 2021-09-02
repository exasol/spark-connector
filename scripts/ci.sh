#!/usr/bin/env bash

## Adapted from Daniel Bast's Spark-Scala-Template,
## https://github.com/dbast/spark-scala-template

set -o errtrace -o nounset -o pipefail -o errexit

# Goto parent (base) directory of this script
BASE_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )"/.. && pwd )"
cd "$BASE_DIR"

DEFAULT_SCALA_VERSION=2.12.14
DEFAULT_SPARK_VERSION=3.1.2

if [[ -z "${SCALA_VERSION:-}" ]]; then
  echo "Environment variable SCALA_VERSION is not set"
  echo "Using DEFAULT_SCALA_VERSION: $DEFAULT_SCALA_VERSION"
  SCALA_VERSION=$DEFAULT_SCALA_VERSION
fi

if [[ -z "${SPARK_VERSION:-}" ]]; then
  echo "Environment variable SPARK_VERSION is not set"
  echo "Using DEFAULT_SPARK_VERSION: $DEFAULT_SPARK_VERSION"
  SPARK_VERSION=$DEFAULT_SPARK_VERSION
fi

run_self_check () {
  echo "############################################"
  echo "#                                          #"
  echo "#        Self Script Check                 #"
  echo "#                                          #"
  echo "############################################"
  # Don't fail here, failing later at the end when all shell scripts are checked anyway.
  shellcheck "$BASE_DIR"/scripts/ci.sh \
    && echo "Self-check succeeded!" || echo "Self-check failed!"
}

run_cleaning () {
  echo "############################################"
  echo "#                                          #"
  echo "#        Cleaning                          #"
  echo "#                                          #"
  echo "############################################"
  sbt \
    -Dspark.currentVersion=$SPARK_VERSION \
    ++$SCALA_VERSION \
    clean
}

run_unit_tests () {
  echo "############################################"
  echo "#                                          #"
  echo "#        Unit testing                      #"
  echo "#                                          #"
  echo "############################################"
  sbt \
    -Dspark.currentVersion=$SPARK_VERSION \
    ++$SCALA_VERSION \
    coverage test
}

run_integration_tests () {
  echo "############################################"
  echo "#                                          #"
  echo "#        Integration testing               #"
  echo "#                                          #"
  echo "############################################"
  sbt \
    -Dspark.currentVersion=$SPARK_VERSION \
    ++$SCALA_VERSION \
    coverage it:test
}

run_coverage_report () {
  echo "############################################"
  echo "#                                          #"
  echo "#        Coverage report                   #"
  echo "#                                          #"
  echo "############################################"
  sbt \
    -Dspark.currentVersion=$SPARK_VERSION \
    ++$SCALA_VERSION \
    coverageReport
}

run_api_doc () {
  echo "############################################"
  echo "#                                          #"
  echo "#        Generating API documentaion       #"
  echo "#                                          #"
  echo "############################################"
  sbt \
    -Dspark.currentVersion=$SPARK_VERSION \
    ++$SCALA_VERSION \
    doc
}

run_dependency_info () {
  echo "############################################"
  echo "#                                          #"
  echo "#        Dependency information            #"
  echo "#                                          #"
  echo "############################################"
  sbt \
    -Dspark.currentVersion=$SPARK_VERSION \
    ++$SCALA_VERSION \
    dependencyUpdates pluginUpdates dependencyTree
}

run_shell_check () {
  echo "############################################"
  echo "#                                          #"
  echo "#        Shellcheck                        #"
  echo "#                                          #"
  echo "############################################"
  find . -name "*.sh" -print0 | xargs -n 1 -0 shellcheck
}

run_assembly () {
  echo "############################################"
  echo "#                                          #"
  echo "#        Assembling binary artifact        #"
  echo "#                                          #"
  echo "############################################"
  sbt \
    -Dspark.currentVersion=$SPARK_VERSION \
    ++$SCALA_VERSION \
    assembly
}

run_clean_worktree_check () {
  echo "############################################"
  echo "#                                          #"
  echo "#        Check for clean worktree          #"
  echo "#                                          #"
  echo "############################################"
  # To be executed after all other steps, to ensures that there is no uncommitted code and there
  # are no untracked files, which means .gitignore is complete and all code is part of a
  # reviewable commit.
  GIT_STATUS="$(git status --porcelain)"
  if [[ $GIT_STATUS ]]; then
    echo "Your worktree is not clean,"
    echo "there is either uncommitted code or there are untracked files:"
    echo "${GIT_STATUS}"
    exit 1
  fi
}

run_self_check
run_cleaning
run_unit_tests
run_integration_tests
run_coverage_report
run_api_doc
run_dependency_info
run_shell_check
run_assembly
run_clean_worktree_check
