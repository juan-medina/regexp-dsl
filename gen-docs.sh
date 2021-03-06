#!/bin/sh -

set -o errexit

rm -rf docs
rm -f src/docs/index.md
rm -rf src/docs/api
cp README.md src/docs/index.md

mkdir docs

./gradlew dokka

mkdocs build

cd docs
python -m SimpleHTTPServer 8080
cd ..