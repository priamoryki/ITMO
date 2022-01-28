#!/bin/bash

if [[ $PWD == $HOME ]]; then
	echo $PWD
	exit 0
fi

echo Error
exit 1