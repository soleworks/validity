#!/usr/bin/env bash
# Waits until a version of every published module can be downloaded from Maven Central. The publish task only hands the
# bundle to the Central Portal, which validates it and syncs it to Maven Central minutes later, or rejects it without
# failing the task. So nothing tells a reader about a version before this script has seen it.
#
# Usage: wait-for-maven-central.sh <version> [<minutes>]
set -euo pipefail

version=$1
minutes=${2:-60}
modules='validity-core validity-phone'
deadline=$((SECONDS + minutes * 60))

for module in $modules; do
    url="https://repo1.maven.org/maven2/io/github/soleworks/$module/$version/$module-$version.pom"
    until curl --silent --fail --head --output /dev/null "$url"; do
        if [ $SECONDS -ge $deadline ]; then
            echo "::error::$module $version is not on Maven Central after $minutes minutes. See the deployment on https://central.sonatype.com/publishing/deployments."
            exit 1
        fi
        sleep 30
    done
    echo "$module $version is on Maven Central."
done
