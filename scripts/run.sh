#!/bin/sh

cat data/hditap.csv | java -classpath bin/:lib/commons-math3-3.6.1.jar taphdi.HDITapFitter
