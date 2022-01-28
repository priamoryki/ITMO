chcp 65001
::1.1
ver > ver.txt
systeminfo | find "Memory" > systeminfo.txt
echo list disk > list.txt
diskpart /s list.txt > diskpart.txt

::1.2
md test
copy /y "D:\Важное\Учеба\Семестр 3\OS-lite\Lab-6\task1" "D:\Важное\Учеба\Семестр 3\OS-lite\Lab-6\task1\test"
cd test

::1.3
type "D:\Важное\Учеба\Семестр 3\OS-lite\Lab-6\task1\*.*"

::1.4
for %i in (*.*) do if not %i == 1.bat del %i