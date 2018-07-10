# Continuous Integration script and files

Here is placed the bash scripts required to build, test and deploy the app.

## Workflow description

### On feature branch

- run Build
- run Test Api v16

### On develop

There is a main script which include the commit validation to avoid repeated workflows, and contain the scripts related to its process.

- Setup environment (ci_setup.sh)
- Documentation (ci_documentation.sh)
  - Create Coverage, Test reports and Code Documentation (ci_generate_documentation.sh)
  - Update headers and styles for proper display on project site

### On master

There is one main script that includes the commit validation to avoid repeated workflows, and contains the scripts related to the process.

- Setup environment (ci_setup.sh)
- Deploy Production (ci_deploy_production.sh)
  - Update version and code, generate Changelog (ci_updateversion.sh)
  - Push new tags and update develop branch (ci_push_changes.sh)
  - Create a GitHub release (ci_github_release.sh)
  - Send to Bintray (ci_bintray.sh)

## Environment variables

On this project we use the following variables:

- BUILD_TOOL -> Used to build the application, set to 26.0.0

- ci_github_release
  - $GITHUB_TOKEN -> GitHub Token
- ci_setup
  - $TELEGRAM_WEBHOOKS -> Used to send notifications to Telegram
  - $GITHUB_EMAIL      -> GitHub Email
  - $GITHUB_USER       -> GitHub User
  - $GITHUB_TOKEN      -> GitHub Token
- ci_bintray
  - $BT_USER   -> Bintray User
  - $BT_APIKEY -> Bintray API Key

## Libraries

We use the following:

- [conventional-github-releaser](https://github.com/conventional-changelog/releaser-tools)
- [gh-pages](https://github.com/tschaub/gh-pages)
- [node-github-releaser](https://github.com/miyajan/node-github-release)
- [standard-version](https://github.com/conventional-changelog/standard-version)