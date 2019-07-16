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

# create enviroment vars to work with fastlane telegram
echo TELEGRAM_WEBHOOKS=$TELEGRAM_WEBHOOKS > .env
echo GIT_REPO=$CIRCLE_REPOSITORY_URL >> .env
echo GIT_BRANCH=$CIRCLE_BRANCH >> .env

# Maintenance commands
sudo apt-get update

# install ruby
sudo apt-get install ruby-full build-essential

# install yarn
curl -sS https://dl.yarnpkg.com/debian/pubkey.gpg | sudo apt-key add -
echo "deb https://dl.yarnpkg.com/debian/ stable main" | sudo tee /etc/apt/sources.list.d/yarn.list

# install without Node since it'll be installed below
sudo apt-get update && sudo apt-get install --no-install-recommends yarn

# install Node.js v8 (version required by yarn)
curl -sL https://deb.nodesource.com/setup_8.x | sudo -E bash -
sudo sudo apt-get install -y nodejs

# install node package available on package.json
yarn install

# config git
git config --global user.email $GH_EMAIL
git config --global user.name "Teclib"
git remote remove origin
git remote add origin https://$GH_USER:$GH_TOKEN@github.com/$CIRCLE_PROJECT_USERNAME/$CIRCLE_PROJECT_REPONAME.git
