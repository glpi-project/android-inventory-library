#!/bin/bash
echo $TRAVIS_BRANCH

# always run unit test
gradle connectedAndroidTest

if [[ "$TRAVIS_BRANCH" == "develop" && "$TRAVIS_PULL_REQUEST" == "false" ]];
then
    #create file with credential to upload bintray
    echo "bintray.user="$BT_USER > "local.properties"
    echo "bintray.apikey="$BT_USER > "local.properties"

    # send to bintray
    gradle install
    gradle bintrayUpload
fi

if [[ "$TRAVIS_BRANCH" == "feature/package" ]];
then
    echo "bintray.user="$BT_USER > "local.properties"
    echo "bintray.apikey="$BT_USER > "local.properties"

    gradle install
    gradle bintrayUpload
fi