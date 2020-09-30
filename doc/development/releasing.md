# Releasing

The Spark Exasol Connector is released to the Sonatype. 

## Prerequisite 

Please make sure that your have a Sonatype account and
[permission](https://issues.sonatype.org/browse/OSSRH-43049) to publish to the
`com.exasol` group.

## GPG Key Preparation

> This step should be setup only once.

It is important to sign every artifact we publish. It ensures that our releases
are not manipulated by third parties.

For that we use GPG. 

We recommend to create a different GPG key for publishing artifacts even if you
already have a personal one.

### Create GPG Key and Files

First create a separate folder for the keys, `mkdir -p gpg-keys/`.

- Change to that directory: `cd gpg-keys/`
- Generate a key: `gpg --homedir . --gen-key`
- Enter your details, please make specific for the project
- Choose a passphrase with good password generator

You have created a key, let us obtain ASCII files that
[sbt-gpg](https://github.com/sbt/sbt-pgp) uses for artifact publishing.

We store the project signing keys in `spark-exasol-connector/project/.gnupg/`
folder.

- Change to that directory: `cd spark-exasol-connector/project/.gnupg/`
- Export the public key from new key: `gpg --homedir ~/gpg-keys -a --export > local.pubring.asc`
- Export the secret key from new key: `gpg --homedir ~/gpg-keys -a --export-secret-keys > local.secring.asc`

Please check out the [detailed manual](https://www.gnupg.org/gph/en/manual.html)
for more information.

Do not forget to save the GPG passphrase and exported files in a password
manager.

### Publish Your GPG Public Key

To publish the artifacts to the Sonatype, you need to publish your GPG public
key so that others can confirm that it really belongs to you.

There are many GPG servers. We recommend the [MIT PGP][mit-pgp] server.

[mit-pgp]: http://pgp.mit.edu/

To publish your GPG public key:

- [Visit the website][mit-pgp]
- Copy and paste the contents of `local.pubring.asc`, in the form with a "Submit a key"
- Click on "Submit this key to the server!" button

If no errors occured, your key should already be published to the public GPG
Server.

## Pre Release Steps

- Make sure that the latest master branch continuous integration is green
- Checkout to the master branch: `git checkout master`
- Pull latest changes from master branch: `git pull origin master`
- Fetch the latest tags from remote: `git fetch --tags`
- Ensure that you have updated `doc/changes/changelog.md` file
- Ensure that you have updated `doc/changes/changes_VERSION.md` file
- Make sure the builds are green
- Run `./scripts/ci.sh` and check that everything works locally
- Add new version as a git tag, for example, `git tag -a 0.3.2 -m "Release version 0.3.2"`
- Push tags to the remote, `git push --tags`

Please make sure that the new version follows the [Semantic Versioning
2.0.0](https://semver.org/).

## Release

Make sure that you do not have any uncommitted or untracked files:

```sh
git status --porcelain
```

First check that you can publish to local repository:

```
./sbtx ";+clean ;+publishLocalSigned"
```

Provide your GPG passphrase when asked. Please notice that I used plus sign
(`+`) with the commands. This publishes artifacts for the cross Scala versions.

Publish to Sonatype:

```
PGP_PASSPHRASE=<GPG_PASSPHRASE> SONATYPE_USERNAME=<USERNAME> SONATYPE_PASSWORD=<PASSWORD> ./sbtx ";+clean ;+publishSigned ;sonatypeReleaseAll"
```

You can use the environment variable to provide the GPG passphrase, Sonatype
username and password.

## Post Release Steps

After successful release, update the Github Release notes. It should be the same
as the pre-release update to the `doc/changes/changes_VERSION.md` file.

Github should already create a draft release from the git tag. Click on the
"Edit release" button on the draft release version on the Github releases page,
and add the release notes.

## Using Release Robot

TODO
