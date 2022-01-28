#!/bin/bash

echo $$ > pids
res=1
op="+"

sigterm()
{
	exit
}

usr1()
{
	op="+"
}

usr2()
{
	op="*"
}

trap 'sigterm' SIGTERM
trap 'usr1' USR1
trap 'usr2' USR2

while true;
do
	case $op in
	"+")
		res=$(($res+2))
		;;
	"*")
		res=$(($res*2))
		;;
	esac
	echo $res
	sleep 1
done