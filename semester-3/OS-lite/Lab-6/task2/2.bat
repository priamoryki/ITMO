chcp 65001
::2.1
hostname
echo %computername% > computername.txt
net use d: %computername%

::2.2
"2.2.bat"

::2.3
schtasks /create /sc minute /tn copy /tr "2.2.bat"

::2.4
schtasks /query | find "copy"
schtasks /delete /tn "copy"

::2.5
fc "D:\Важное\Учеба\Семестр 3\OS-lite\Lab-6\task2\2.bat" "\\%computername%\%computername%\2.bat"

::2.6
"2.2.bat"