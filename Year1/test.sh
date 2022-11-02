#!/bin/bash


word=Washington
files=`ls -F ./test | grep ^List | grep -v /$`

for file in $files; do
	grep $word ./test/$file >> ./output.txt
done
