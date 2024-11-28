#!/bin/bash

mvn package
rm ./target/endpoint-1.0.jar
mv ./target/endpoint-1.0-Main.jar ./endpoint.jar
