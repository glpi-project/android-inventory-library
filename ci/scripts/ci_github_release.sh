#!/bin/bash
#
#  Copyright (C) 2017 Teclib'
#
#  This file is part of Flyve MDM Inventory Android.
#
#  Flyve MDM Inventory Android is a subproject of Flyve MDM. Flyve MDM is a mobile
#  device management software.
#
#  Flyve MDM Android is free software: you can redistribute it and/or
#  modify it under the terms of the GNU General Public License
#  as published by the Free Software Foundation; either version 3
#  of the License, or (at your option) any later version.
#
#  Flyve MDM Inventory Android is distributed in the hope that it will be useful,
#  but WITHOUT ANY WARRANTY; without even the implied warranty of
#  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#  GNU General Public License for more details.
#  ------------------------------------------------------------------------------
#  @author    Rafael Hernandez - rafaelje
#  @copyright Copyright (c) 2017 Flyve MDM
#  @license   GPLv3 https://www.gnu.org/licenses/gpl-3.0.html
#  @link      https://github.com/flyve-mdm/flyve-mdm-android-inventory
#  @link      http://www.glpi-project.org/
#  @link      https://flyve-mdm.com/
#  ------------------------------------------------------------------------------
#

GIT_TAG=$(jq -r ".version" package.json)

# Push commits and tags to origin branch
# git push --follow-tags origin $CIRCLE_BRANCH

# push tag to github
conventional-github-releaser -t $GH_TOKEN 2> /dev/null || true

# Create zip example code
sudo zip -r $CIRCLE_ARTIFACTS/java_example_code.zip examples/java*
sudo zip -r $CIRCLE_ARTIFACTS/kotlin_example_code.zip examples/kotlin*

# Update release name
github-release edit \
--user $CIRCLE_PROJECT_USERNAME \
--repo $CIRCLE_PROJECT_REPONAME \
--tag ${GIT_TAG} \
--name "Inventory Engine v${GIT_TAG}" \
--description "$(git log -1 --pretty=%B)"

# Upload example code release
github-release upload \
--user $CIRCLE_PROJECT_USERNAME \
--repo $CIRCLE_PROJECT_REPONAME \
--tag ${GIT_TAG} \
--name "java_example.zip" \
--file $CIRCLE_ARTIFACTS/java_example_code.zip

# Upload example code release
github-release upload \
--user $CIRCLE_PROJECT_USERNAME \
--repo $CIRCLE_PROJECT_REPONAME \
--tag ${GIT_TAG} \
--name "kotlin_example.zip" \
--file $CIRCLE_ARTIFACTS/kotlin_example_code.zip