#!/bin/bash

#   Copyright © 2017 Teclib. All rights reserved.
#
#   This file is part of flyve-mdm-android-agent
#
# flyve-mdm-android-agent is a subproject of Flyve MDM. Flyve MDM is a mobile
# device management software.
#
# Flyve MDM is free software: you can redistribute it and/or
# modify it under the terms of the GNU General Public License
# as published by the Free Software Foundation; either version 3
# of the License, or (at your option) any later version.
#
# Flyve MDM is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
# ------------------------------------------------------------------------------
# @author    Rafael Hernandez - rafaelje
# @date      24/9/17
# @copyright Copyright © 2017 Teclib. All rights reserved.
# @license   GPLv3 https://www.gnu.org/licenses/gpl-3.0.html
# @link      https://github.com/flyve-mdm/flyve-mdm-android-agent
# @link      https://flyve-mdm.com
# ------------------------------------------------------------------------------

# Store information on gh-pages branch
# - Create documentation with javadoc
# - Create Coverage Reporte
# - Create Changelog
# - Push this file to gh-pages branch
updateGHPAGES() {
    # Generate javadoc this folder must be on .gitignore
    javadoc -d ./javadoc -sourcepath ./app/src/main/java -subpackages .

    # Generate code coverage
    gradle createDebugCoverageReport

    # get
    git fetch origin gh-pages

    # move to gh-pages
    git checkout gh-pages

    # add javadoc folder
    git add javadoc

    # create commit for documentation
    git commit -m "docs(javadoc): update javadoc with version ${GIT_TAG}"

    # add code coverage
    git add app/build/reports/coverage

    # replace .resources with resource because github don't support folders with "_" or "." at the beginning
    mv app/build/reports/coverage/debug/.resources app/build/reports/coverage/debug/resources

    index=$(<app/build/reports/coverage/debug/index.html)
    newindex="${index//.resources/resources}"
    echo $newindex > app/build/reports/coverage/debug/index.html

    # add new files processes
    git add app/build/reports/coverage/debug/resources
    git add app/build/reports/coverage/debug/index.html

    # add Android Tests
    git add app/build/reports/androidTests

    # create commit
    git commit -m "docs(coverage): update code coverage with version ${GIT_TAG}"

    # clean unstage file on gh-pages to remove all others files gets on checkout
    git clean -fdx

    # get changelog from branch
    git checkout $TRAVIS_BRANCH CHANGELOG.md

    # Create header content to work with gh-pages templates
    HEADER="---"$'\r'"layout: modal"$'\r'"title: changelog"$'\r'"---"$'\r\r'

    # Duplicate CHANGELOG.md
    cp CHANGELOG.md CHANGELOG_COPY.md

    # Add header to CHANGELOG.md
    (echo $HEADER ; cat CHANGELOG_COPY.md) > CHANGELOG.md

    # Remove CHANGELOG_COPY.md
    rm CHANGELOG_COPY.md

    # add
    git add CHANGELOG.md

    # create commit
    git commit -m "docs(changelog): update changelog with version ${GIT_TAG}"

    # push to branch
    git push origin gh-pages --force
}

# Configure git with Flyve bot user to make commits on repositories
# and add new origin with Flyve bot credentials
configGit() {
    # config git
    git config --global user.email $GH_EMAIL
    git config --global user.name "Flyve MDM"
    git remote remove origin
    git remote add origin https://$GH_USER:$GH_TOKEN@github.com/$TRAVIS_REPO_SLUG.git
}


if [[ ("$TRAVIS_BRANCH" == "master" || "$TRAVIS_BRANCH" == "develop") && "$TRAVIS_PULL_REQUEST" == "false" && "$TRAVIS_RUN" == "true" ]]; then

    configGit

    updateGHPAGES

    # send to bintray
    gradle install
    gradle bintrayUpload
fi