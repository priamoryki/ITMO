#!/bin/bash

echo $$ > pids
while true
do
	read line
	echo "$line" >> channel
done