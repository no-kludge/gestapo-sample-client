#!/bin/bash


echo "Clearing target folder..."
rm -rf target
mkdir log

echo "Packaging..."
mvn package spring-boot:repackage >> log/StartUp.log

echo "Starting Up..."
nohup java -Dserver.port=9988 -jar target/NodeViz-0.0.1-SNAPSHOT.jar >> log/ServiceDepdency.log &

echo "Started Up. Access page on <your IP>:9988/index"
echo "Use ./stop.sh to stop"
echo "Goto log folder for logs"

#ps -ax | grep target/NodeViz-0.0.1-SNAPSHOT.jar
#http://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html