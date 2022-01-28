#!/bin/bash

man bash | tr "[:upper:]" "[:lower:]" | grep -o -i "[[:alnum:]]\{4,\}" | sort | uniq -c | sort -n | tail -3