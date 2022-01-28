#!/bin/bash

ps -e -F | awk '{if($11 ~ "^/sbin/"){print $0}}' > solution2