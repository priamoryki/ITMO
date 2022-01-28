#!/bin/bash

./inf_while.sh &
a=$!
./inf_while.sh &
b=$!
sleep 5
kill $b
cpulimit -p $a --limit=10 &