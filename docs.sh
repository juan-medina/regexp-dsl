#!/bin/sh -

set -o errexit

rm -rf docs
rm -f edit/index.md
cp README.md edit/index.md

mkdir docs

./gradlew dokka

mkdocs build

cd docs
python -m SimpleHTTPServer 8080
cd ..