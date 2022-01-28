#!/bin/bash

fun() {
    M=$(echo $total_time $process_counter | awk '{print $1/$2}')
    echo "Average_Running_Children_of_ParentID=$prev_ppid is $M" >> solution5
    process_counter=0
    total_time=0
}

bash Task4.sh
rm solution5
prev_ppid=0
process_counter=0
total_time=0
while read line
do
    ppid=$(echo $line | awk '{print $5}')
    process_time=$(echo $line | awk '{print $8}')
    if (( $ppid != $prev_ppid ))
    then
        fun
    fi
    echo $line >> solution5
    let process_counter+=1
    total_time=$(echo $total_time $process_time | awk '{print $1+$2}')
    prev_ppid=$ppid
done < solution4
fun