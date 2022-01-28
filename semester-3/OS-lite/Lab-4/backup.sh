#!/bin/bash

cd ~

dateNow=$(date +%Y-%m-%d)
datePrev=$(ls | grep "^Backup-" | sort -n | tail -1 | cut -d "-" -f 2,3,4)

if [[ $datePrev = "" || $( echo $(date -d $dateNow +%s) $(date -d $datePrev +%s) | awk '{print ($1 - $2) / 60 / 60 / 24}') -ge 7 ]]
then
	mkdir "Backup-$dateNow"
	cd ~/source
	filesInSource=$(ls)
	for currentFile in $filesInSource
	do
		cp -R $currentFile ~/Backup-$dateNow
	done
	cd ~
	echo "Directory Backup-$dateNow has been created."
	echo "Files: $filesInSource" >> ~/backup-report
else
	cd ~/source
	filesInSource=$(ls)
	cd ~
	echo "Directory Backup-$datePrev has been created."
	echo "Files: $filesInSource" >> ~/backup-report	
	cd ~/Backup-$datePrev
	for currentFile in $filesInSource
	do
		if [[ -f $currentFile ]]
		then
			sizeOfSourceFile=$(ls -l ~/source/$currentFile | awk '{print$5}')
			sizeOfBackupFile=$(ls -l $currentFile | awk '{print$5}')
			if [[ $sizeOfSourceFile -ne $sizeOfBackupFile ]]
			then
				mv $currentFile $currentFile-$dateNow
				cp -R ~/source/$currentFile ~/Backup-$datePrev
				echo "rename $currentFile to $currentFile-$dateNow" >> ~/backup-report
			fi
		else
			cp -R ~/source/$i ~/Backup-$dateFiles
		fi
	done
fi