#!/bin/bash

currentFile=$1
hiddenDir=~/trash

if ! [[ -d $hiddenDir ]]
then
	mkdir $hiddenDir
fi

currentLink=$(date +"%F...%T")

ln -P $currentFile "$hiddenDir/$currentLink"
rm $currentFile

currentDir=$(pwd)
echo "$currentDir/$currentFile=$currentLink" >> ~/trash.log