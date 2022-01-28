#!/bin/bash

cd ~

if ! [[ -d restore ]]
then
	mkdir restore
fi

cd ~/$(ls | grep '^Backup-' | sort -n | tail -1)

for currentFile in $(ls)
do
	str=$(echo $currentFile | grep -E -o '^[A-Za-z0-9]+-[0-9]+-[0-9]+-[0-9]+')
	if [[ $currentFile != $str ]]
	then
		cp -R $currentFile ~/restore
	fi
done	