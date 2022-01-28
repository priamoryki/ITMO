#!/bin/bash

new_directory=~/test
report_file=~/report
test_file="$new_directory/$(date '+%d.%m.%y_%H:%M:%S')"
(mkdir $new_directory && echo "catalog test was created successfully" > $report_file && touch $test_file)
(ping -c 1 "www.net_nikogo.ru") || (echo "can't connect to host" >> $report_file)