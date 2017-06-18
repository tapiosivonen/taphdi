#!/bin/sh

rm -r bin/*.class
javac -sourcepath src/ -classpath lib/commons-math3-3.6.1.jar:bin/ src/taphdi/HDITapFitter.java -d bin/
