#!/usr/bin/env bash

set -e

if [! $PWD = "/projects/OpenRC-turbo/javadoc"]
then
    echo "cd into the javadoc folder"
    exit 1
fi

files=$(find .. -name *.java | tr '\n' ' ')

echo "              FOUND THESE FILES               "
echo $files

javadoc -d . $files