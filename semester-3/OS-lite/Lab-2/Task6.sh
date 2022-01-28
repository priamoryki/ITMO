#!/bin/bash

result_pid=""
max_value=0
for pid in $(ps -e -o pid --no-header)
do
    file=/proc/$pid/status
    if [[ -f $file ]]
    then
        current_value=$(grep VmSize $file | awk '{print $2}')
        if [[ $current_value -gt $max_value ]]
        then
            result_pid=$pid
            max_value=$current_value
        fi
    fi
    done
echo $result_pid $max_value