#!/bin/bash

res=1
op="+"
(tail -f channel) | while true;
do
	read line;
	case $line in
		"QUIT")
			kill $(cat pids)
			exit
			;;
		"+")
			op="+"
			;;
		"*")
		 	op="*"
	 		;;
 		[0-9]*)
 			if [[ $op == "+" ]]
 			then
 				res=$(($res+$line))
			else
				res=$(($res*$line))
			fi
			echo $res
			;;
		*)
			echo "Invalid operation" > stderr
			;;
	esac
done