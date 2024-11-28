#!/bin/bash

# Example of creation of a HDT file from an RDF dump containing different files
#
# If it causes OOMs, you can edit ./hdt-java-package-3.0.9/bin/javaenv.sh,
# and increases Java's max heap size under JAVA_OPTIONS
# Example
#if [ "$JAVA_OPTIONS" = "" ] ; then
#   JAVA_OPTIONS="-Xmx16g"
#fi

# Requires a compiled hdt-java-package from https://github.com/rdfhdt/hdt-java
RDF2HDT_LOCATION="./hdt-java-package-3.0.9/bin/rdf2hdt.sh"

# Create the .hdt files
FILES="./*.bz2" # or .ttl, etc.
for f in $FILES
do
    $RDF2HDT_LOCATION -cattree "$f" "$f.hdt" 
done

# Concatenate them
hdts=($( ls *.hdt ))
hdtsJoined=$(IFS=' ' ; echo "${hdts[*]}")
./hdt-java-package-3.0.9/bin/hdtCat.sh "$hdtsJoined" endpoint.hdt
