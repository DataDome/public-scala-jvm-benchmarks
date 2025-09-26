#!/bin/bash

echo ""
echo "#################################################################"
echo "#######       JVM Performance Benchmarks Test Suite       #######"
echo "#######     Setup OS Configuration for the benchmarks     #######"
echo "#################################################################"
DRY_RUN="$1"
if [ "$DRY_RUN" == "--dry-run" ]; then
  echo ""
  echo "+==========================================================+"
  echo "| Running in dry-run mode. No settings will be changed.    |"
  echo "+==========================================================+"
fi

echo ""
echo "+=======================+"
echo "| [1/8] Load Properties |"
echo "+=======================+"
. ./scripts/shell/load-properties.sh

echo ""
echo "+=============================+"
echo "| [2/8] Hardware Architecture |"
echo "+=============================+"
. ./scripts/shell/configure-arch.sh

echo ""
echo "+========================+"
echo "| [3/8] OS Configuration |"
echo "+========================+"
. ./scripts/shell/configure-os.sh || exit 1
. ./scripts/shell/configure-os-$OS.sh "$DRY_RUN"
