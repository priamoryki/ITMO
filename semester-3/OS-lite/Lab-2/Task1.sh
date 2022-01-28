#!/bin/bash

ps -F -u $USER --no-headers > temp
wc -l temp > solution1
cat temp | awk '{ print($2, ":", $11) }' >> solution1
rm temp