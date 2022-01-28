#!/bin/bash

for pid in $(ps -e -o pid --no-headers)
do
    file1=/proc/$pid/status
    file2=/proc/$pid/sched
    ppid=$(grep -s PPid $file1 | awk '{print $2}')
    sum_exec_runtime=$(grep -s se.sum_exec_runtime $file2 | awk '{print $3}')
    nr_switches=$(grep -s nr_switches $file2 | awk '{print $3}')
    if [[ $ppid != "" && $sum_exec_runtime != "" && $nr_switches != "" ]]
    then
        art=$(echo $sum_exec_runtime $nr_switches | awk '{print $1/$2}')
        echo "ProcessID= $pid : Parent_ProcessID= $ppid : Average_Running_Time= $art" >> temp
    fi
done
cat temp | sort -n --key=5 > solution4
rm temp