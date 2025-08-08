#!/bin/bash

# Loads the properties files. Expects to run from the root of the project.
CURRENT_PATH="$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )"
ROOT=$CURRENT_PATH/../..

if [ -f $ROOT/settings/config.properties ]; then
  source $ROOT/settings/config.properties
  echo "Configuration properties have been successfully loaded from the './settings/config.properties' file."
else
  echo "ERROR: File './settings/config.properties' not found. Unable to continue!"
  exit 1
fi

if [ -f $ROOT/settings/constants.properties ]; then
  source $ROOT/settings/constants.properties
  echo "Constant properties have been successfully loaded from the './settings/constants.properties' file."
else
  echo "ERROR: File './settings/constants.properties' not found. Unable to continue!"
  exit 1
fi
