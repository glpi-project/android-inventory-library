#!/usr/bin/env bash

# Push commits and tags to origin branch
git push --follow-tags origin $CIRCLE_BRANCH

# Merge back the develop branch step

# go to develop
git checkout develop

# rebase with master
git rebase master

# push develop
git push origin develop

# return to master
git checkout master