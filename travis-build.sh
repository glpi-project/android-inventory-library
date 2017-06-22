#!/bin/bash
set -ev

if [ "${TRAVIS_PULL_REQUEST}" = "false" ]; then
    gradle clean gnagCheck
else
    gradle clean gnagReport -PauthToken="${GNAG_AUTH_TOKEN}" -PissueNumber="${TRAVIS_PULL_REQUEST}" --stacktrace
fi