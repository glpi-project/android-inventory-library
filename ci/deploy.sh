#!/bin/bash
echo $TRAVIS_BRANCH

# always run unit test
./gradlew connectedAndroidTest

if [[ "$TRAVIS_BRANCH" == "develop" && "$TRAVIS_PULL_REQUEST" == "false" ]];
then
    # send to bintray
    ./gradlew install
    ./gradlew bintrayUpload
fi