#!/usr/bin/env bash

## Adapted from Daniel Bast's Spark-Scala-Template,
## https://github.com/dbast/spark-scala-template

set -o errtrace -o nounset -o pipefail -o errexit

# Goto parent (base) directory of this script
BASE_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )"/.. && pwd )"
cd "$BASE_DIR"

MAIN_SCALA_VERSION=2.12.12
MAIN_SPARK_VERSION=2.4.5

if [[ -z "${TRAVIS_SCALA_VERSION:-}" ]]; then
  echo "Environment variable TRAVIS_SCALA_VERSION is not set"
  echo "Using MAIN_SCALA_VERSION: $MAIN_SCALA_VERSION"
  TRAVIS_SCALA_VERSION=$MAIN_SCALA_VERSION
fi

if [[ -z "${SPARK_VERSION:-}" ]]; then
  echo "Environment variable SPARK_VERSION is not set"
  echo "Using MAIN_SPARK_VERSION: $MAIN_SPARK_VERSION"
  SPARK_VERSION=$MAIN_SPARK_VERSION
fi

run_self_check () {
  echo "############################################"
  echo "#                                          #"
  echo "#        Self-check                        #"
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
  ./sbtx \
    -Dspark.currentVersion=$SPARK_VERSION \
    ++$TRAVIS_SCALA_VERSION \
    clean
}

run_unit_tests () {
  echo "############################################"
  echo "#                                          #"
  echo "#        Unit testing                      #"
  echo "#                                          #"
  echo "############################################"
  ./sbtx \
    -Dspark.currentVersion=$SPARK_VERSION \
    ++$TRAVIS_SCALA_VERSION \
    coverage test
}

run_integration_tests () {
  echo "############################################"
  echo "#                                          #"
  echo "#        Integration testing               #"
  echo "#                                          #"
  echo "############################################"
  ./sbtx \
    -Dspark.currentVersion=$SPARK_VERSION \
    ++$TRAVIS_SCALA_VERSION \
    coverage it:test
}

run_coverage_report () {
  echo "############################################"
  echo "#                                          #"
  echo "#        Coverage report                   #"
  echo "#                                          #"
  echo "############################################"
  ./sbtx \
    -Dspark.currentVersion=$SPARK_VERSION \
    ++$TRAVIS_SCALA_VERSION \
    coverageReport
}

run_api_doc () {
  echo "############################################"
  echo "#                                          #"
  echo "#        Generating API documentaion       #"
  echo "#                                          #"
  echo "############################################"
  ./sbtx \
    -Dspark.currentVersion=$SPARK_VERSION \
    ++$TRAVIS_SCALA_VERSION \
    doc
}

run_dependency_info () {
  echo "############################################"
  echo "#                                          #"
  echo "#        Dependency information            #"
  echo "#                                          #"
  echo "############################################"
  ./sbtx \
    -Dspark.currentVersion=$SPARK_VERSION \
    ++$TRAVIS_SCALA_VERSION \
    dependencyUpdates pluginUpdates coursierDependencyTree
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
  ./sbtx \
    -Dspark.currentVersion=$SPARK_VERSION \
    ++$TRAVIS_SCALA_VERSION \
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
