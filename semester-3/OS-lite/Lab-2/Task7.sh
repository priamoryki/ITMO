#!/bin/bash

for pid in $(ps -e -o pid --no-header)
do
    file=/proc/$pid/io
    if [[ -f $file ]]
    then
        read_bytes=$(sudo grep read_bytes $file | awk '{print $2}')
        echo $pid $read_bytes >> temp1
    fi
done

sleep 60

while read line 
do 
    pid=$(echo $line | awk '{print $1}')
    old_read_bytes=$(echo $line | awk '{print $2}')
    file=/proc/$pid/io
    if [[ -f $file ]] 
    then
        new_read_bytes=$(sudo grep read_bytes $file |awk '{print $2}')
	    echo $pid : $(echo $new_read_bytes $old_read_bytes | awk '{print $1-$2}') >> temp2
    fi
done < temp1
sort -n -k3 -r temp2 | head -3 > solution7
sudo rm temp1
sudo rm temp2