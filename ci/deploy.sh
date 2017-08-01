#!/bin/bash
echo $TRAVIS_BRANCH

# always run unit test
 gradle connectedAndroidTest

if [[ "$TRAVIS_BRANCH" == "develop" && "$TRAVIS_PULL_REQUEST" == "false" ]];
then
    # send to bintray
    gradle install
    gradle bintrayUpload
fi