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

# COVERAGE_PATH="development/coverage/$CIRCLE_BRANCH"
# TEST_PATH="development/test-report/$CIRCLE_BRANCH"

# # create code coverage report
# ./gradlew :inventory:createDebugCoverageReport

# # replace .resources with resource because github doesn't support folders with "_" or "." at the beginning
# mv inventory/build/reports/coverage/debug/.resources inventory/build/reports/coverage/debug/resources

# # find and replace links to the old name of file
# grep -rl .resources inventory/build/reports/coverage/debug/ | xargs sed -i 's|.resources|resources|g'

# # replace .sessions
# mv inventory/build/reports/coverage/debug/.sessions.html inventory/build/reports/coverage/debug/sessions.html

# # find and replace links to the old name of file
# grep -rl .sessions.html inventory/build/reports/coverage/debug/ | xargs sed -i 's|.sessions.html|sessions.html|g'

# yarn gh-pages --dist inventory/build/reports/coverage/debug/  --dest $COVERAGE_PATH -m "docs(development): update coverage"

# yarn gh-pages --dist inventory/build/reports/androidTests/connected/ --dest $TEST_PATH -m "docs(development): update coverage"