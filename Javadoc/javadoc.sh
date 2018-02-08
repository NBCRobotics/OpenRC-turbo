#!/bin/bash

set -e

files=$(find .. -name *.java | tr '\n' ' ')

echo "              FOUND THESE FILES               "
echo $files

javadoc -d . $files