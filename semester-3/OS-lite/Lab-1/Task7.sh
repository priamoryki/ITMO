#!/bin/bash

grep -E -o "[[:alnum:]_]+@[[:alnum:]]+(\.[[:alnum:]]+)+" -r /etc -h > emails.lst