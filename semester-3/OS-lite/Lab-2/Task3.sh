#!/bin/bash

ps -e -o pid,ppid,start --no-header | awk '$1 != this_pid && $2 != this_pid {print $1}' this_pid=$$ | sort -k3 | tail -1