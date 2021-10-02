#!/bin/bash -e
# Need to source sdk init first to use it within the script.
. ~/.sdkman/bin/sdkman-init.sh
#
SNAPSHOT="https://repository.apache.org/content/repositories/snapshots/org/apache/maven/apache-maven/4.0.0-alpha-1-SNAPSHOT/"
URL="https://repo1.maven.org/maven2/org/apache/maven/apache-maven"
VERSIONS=('3.0.5' '3.1.1' '3.2.5' '3.3.9' '3.5.4' '3.6.3' '3.8.1' '3.8.2' '3.8.3' '4.0.0-alpha-1-SNAPSHOT')
JDKS=( '8.0.302-tem' '11.0.12-tem' '16.0.2-tem' '17.0.0-tem' )
for i in "${VERSIONS[@]}"; do
	if [ -f "apache-maven-$i-bin.tar.gz" ]; then
		echo "File apache-maven-$i-bin.tar.gz already downloaded."
	else
		wget $URL/$i/apache-maven-$i-bin.tar.gz
	fi
done
# Unpack
for i in "${VERSIONS[@]}"; do
	if [ -d "apache-maven-$i" ]; then
		echo "Directory apache-maven-$i exists."
	else
		tar -zxf apache-maven-$i-bin.tar.gz
	fi
done

COMMAND=()
for i in "${VERSIONS[@]}"; do
	COMMAND+=( "../apache-maven-$i/bin/mvn clean" )
done

for jdk in "${JDKS[@]}"; do
	echo "Running with JDK: $jdk"
#	sdk use java $jdk
	cd reactor
#	hyperfine --warmup 5 --export-markdown ../results-$jdk.md --parameter-list version ${VERSIONS[@]} '../apache-maven-{version}/bin/mvn clean'
	hyperfine --warmup 5 --export-markdown ../results-$jdk.md "${COMMAND[@]}"
	cd ..
done
