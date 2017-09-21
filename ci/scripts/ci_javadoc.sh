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
# Generate javadoc this folder must be on .gitignore
javadoc -d ./reports$1/javadoc -sourcepath ./inventory/src/main/java -subpackages . -nonavbar

# delete the index.html file
sudo rm ./reports$1/javadoc/index.html

# rename the overview-summary.html file toindex.html
mv ./reports$1/javadoc/overview-summary.html ./reports$1/javadoc/index.html

# add header
ruby ci/scripts/add_javadoc_header.rb

# remove default stylesheet.css
sudo rm ./reports$1/javadoc/stylesheet.css

# get stylesheet.css template
wget -qO- https://gist.githubusercontent.com/flyve-mdm-bot/78014d4ffe3d5d70585a7b538f7eb84c/raw/fd12955bc582d968472a6d7f8b78ca5b8d4b8a23/stylesheet.css -O ./reports$1/javadoc/stylesheet.css

# add reports
git add reports$1 -f

# create commit with temporary report folder
git commit -m "tmp report commit"

# get gh-pages branch
git fetch origin gh-pages

# move to gh-pages
git checkout gh-pages

# delete old javadoc folder
sudo rm -R reports$1/javadoc

# get fresh javadoc folder
git checkout $CIRCLE_BRANCH reports$1/javadoc

# git add javadoc folder
git add reports$1/javadoc

# create commit for documentation
git commit -m "docs(javadoc): update javadoc"

# push to branch
git push origin gh-pages

# got back to original branch
git checkout $CIRCLE_BRANCH