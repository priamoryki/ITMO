#!/bin/bash

currentFile=$1
trashLog=~/trash.log
line=1
a=$(cat $trashLog)

for string in $(cat $trashLog)
do
	if [[ $string =~ $currentFile ]]
	then
		echo "Do you want to restore the $string? (yes/no)"
		read answer
		if [[ $answer == "yes" ]]
		then
			cd ~/trash
			fileInTrash="$( echo "$string" | cut -d "=" -f 2 )"
			mycd="$(echo "$string" | cut -d "=" -f 1 | grep -oP ".*/")"
			if ! [[ -d $mycd ]]
			then
				ln -P $fileInTrash ~/$currentFile
				rm $fileInTrash
			else
				if [[ -f $mycd$currentFile ]]
				then
					echo "This file already exists. Do you want to rename and restore? (yes/no)"
					read answer
					if [[ $answer == "yes" ]]
					then
						read currentFile
					fi
				fi
                ln -P "$fileInTrash" "$mycd$currentFile"
				rm "$fileInTrash"
			fi
			sed -i "${line}d" $trashLog
		fi
	fi
	let line+=1
done