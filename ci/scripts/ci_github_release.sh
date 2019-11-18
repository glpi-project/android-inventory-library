#!/usr/bin/env bash
#
#  LICENSE
#
#  This file is part of Flyve MDM Inventory Library for Android.
#
#  Inventory Library for Android is a subproject of Flyve MDM. 
#  Flyve MDM is a mobile device management software.
#
#  Flyve MDM is free software: you can redistribute it and/or
#  modify it under the terms of the GNU General Public License
#  as published by the Free Software Foundation; either version 3
#  of the License, or (at your option) any later version.
#
#  Flyve MDM is distributed in the hope that it will be useful,
#  but WITHOUT ANY WARRANTY; without even the implied warranty of
#  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#  GNU General Public License for more details.
#  ---------------------------------------------------------------------
#  @copyright Copyright © 2018 Teclib. All rights reserved.
#  @license   GPLv3 https://www.gnu.org/licenses/gpl-3.0.html
#  @link      https://github.com/flyve-mdm/android-inventory-library/
#  @link      http://flyve.org/android-inventory-library/
#  @link      https://flyve-mdm.com/
#  ---------------------------------------------------------------------
#

GIT_TAG=$(jq -r ".version" package.json)
export GITHUB_TOKEN=$GH_TOKEN

# push tag to github
yarn conventional-github-releaser -p angular -t $GH_TOKEN 2> /dev/null || true

# Create zip example code
sudo zip -r $CIRCLE_ARTIFACTS/java_example_code.zip example_java*
sudo zip -r $CIRCLE_ARTIFACTS/kotlin_example_code.zip example_kotlin*

# Update release name
yarn github-release edit \
--user $CIRCLE_PROJECT_USERNAME \
--repo $CIRCLE_PROJECT_REPONAME \
--tag ${GIT_TAG} \
--name "Inventory Engine v${GIT_TAG}" \

# Upload example code release
yarn github-release upload \
--user $CIRCLE_PROJECT_USERNAME \
--repo $CIRCLE_PROJECT_REPONAME \
--tag ${GIT_TAG} \
--name "java_example.zip" \
--file $CIRCLE_ARTIFACTS/java_example_code.zip

# Upload example code release
yarn github-release upload \
--user $CIRCLE_PROJECT_USERNAME \
--repo $CIRCLE_PROJECT_REPONAME \
--tag ${GIT_TAG} \
--name "kotlin_example.zip" \
--file $CIRCLE_ARTIFACTS/kotlin_example_code.zip