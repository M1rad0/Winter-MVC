set nomSrcTxt=classlist.txt
set lib=..\lib
call .\findJavaSrc
set src=
for /f "delims=" %%i in (%nomSrcTxt%) do set src=%src% %%i

javac -parameters -cp "%lib%\*" -d ".." %src%

jar --create --file ..\winter.jar ..\mg

del %nomSrcTxt%
rmdir /S /Q ..\mg