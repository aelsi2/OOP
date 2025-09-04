#!/bin/bash

PROJECT_DIR=$(dirname $(realpath $0))
SRC_DIR=$PROJECT_DIR/src/main/java
BUILD_DIR=$PROJECT_DIR/build/custom
CLASS_DIR=$BUILD_DIR/classes
DOC_DIR=$BUILD_DIR/docs
JAR=$BUILD_DIR/App.jar
JAVA_FILES=$(find $SRC_DIR -name '*.java')

javac $JAVA_FILES -d $CLASS_DIR
cd $CLASS_DIR
jar cfe $JAR ru.nsu.aeliseev2.task111.App $(find . -name '*.class')
javadoc $JAVA_FILES -d $DOC_DIR
java -jar $JAR

