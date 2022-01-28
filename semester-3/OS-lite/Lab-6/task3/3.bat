::3.1
net start > netstart1.txt

::3.2.1
net stop "dnscache"

::3.2.2
timeout /t 10 /nobreak & net start > netstart2.txt

::3.2.3
fc netstart1.txt netstart2.txt > diff.txt

::3.2.4
net start "dnscache"