#!/bin/bash

awk  '$6=="(II)"' /var/log/syslog | sed 's/(II)/Information:/' > full.log
awk  '$6=="(WW)"' /var/log/syslog | sed 's/(WW)/Warning:/' >> full.log

cat full.log