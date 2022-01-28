#!/bin/bash

temp=""
res=""

while [[ $temp != q ]]; do
    res+=$temp
    read temp
done

echo $res