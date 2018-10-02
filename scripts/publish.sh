#!/usr/bin/env bash

# An example setting to run locally
#
# RELEASE=true TRAVIS_PULL_REQUEST=false TRAVIS_SECURE_ENV_VARS=true ./scripts/publish.sh

set -eu

if [[ "$TRAVIS_PULL_REQUEST" == "true" ]]; then
  echo "Skipping publish in pull requests"
  exit 0
fi

if [[ "$TRAVIS_SECURE_ENV_VARS" == "false" ]]; then
  echo "Skipping publish, no secure environment variables are set"
  exit 0
fi

if [ -z "$RELEASE" ] || [ "$RELEASE" == "false" ]; then
  echo "Skipping publish, not in a release stage"
  exit 0
fi

if [[ "$(git status --porcelain)" ]]; then
  echo "Skipping publish, there are uncommitted / untracked files"
  exit 0
fi

extract_secure_vars () {
  if [[ -z "${CI:-}" ]]; then
    echo "[SKIP] Secure variables are only extracted in Travis CI builds"
    return 0
  fi

  echo "Extracting secure environment variables"
  openssl aes-256-cbc -k "$ENCRYPTION_KEY" \
    -in project/.gnupg/rings.tar.enc \
    -out project/.gnupg/local.rings.tar \
    -d

  tar xv -C project/.gnupg/ -f project/.gnupg/local.rings.tar
}

sonatype_release () {
  echo "Publishing..."
  if [ -n "${TRAVIS_TAG:-}" ] || git describe --tags --exact-match HEAD ; then
    echo "Tag was pushed, publishing to Sonatype release"
    ./sbtx ";reload ;clean ;publishSigned ;sonatypeReleaseAll"
  else
    echo "No tag was pushed, publishing to Sonatype snapshot"
    ./sbtx ";reload ;clean ;publishSigned"
  fi
}

extract_secure_vars
sonatype_release
