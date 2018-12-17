# Continuous Integration script and files

Here is placed the bash scripts required to build, test and deploy the app.

## Workflow description

### On feature branch

- run Build
- run Test Instrumentation

### On develop

- Create the code documentation and send it to gh-pages

### On master

- Send the LTS version to Bintray
- Create the code documentation and send it to gh-pages

## Environment variables

On this project we use the following variables:

- BUILD_TOOL -> Used to build the application, set to 26.0.0
- $KEYSTORE -> Key store for apk signing
- $ALIAS    -> The alias of the certificate to sign the apk
- $TELEGRAM_WEBHOOKS -> Used to send notifications to Telegram
- $THESTRALBOT_URL   -> URL to Thestralbot
- $ENCRYPTED_KEY     -> Used to desencrypt the key to sign the APK
- $GITHUB_EMAIL      -> GitHub Email
- $GITHUB_USER       -> GitHub User
- $GITHUB_TOKEN      -> GitHub Token
- $TRANSIFEX_USER      -> User of Transifex
- $TRANSIFEX_API_TOKEN -> API Token of Transifex
- $BT_USER   -> Bintray User
- $BT_APIKEY -> Bintray API Key

## Libraries

We use the following:

- [conventional-github-releaser](https://github.com/conventional-changelog/releaser-tools)
- [gh-pages](https://github.com/tschaub/gh-pages)
- [node-github-releaser](https://github.com/miyajan/node-github-release)
- [standard-version](https://github.com/conventional-changelog/standard-version)