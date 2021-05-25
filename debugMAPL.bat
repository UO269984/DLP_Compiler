@echo off
if "%1" == "-d" (
	mapl\GVM.exe %2
) else (
	mapl\TextVM.exe %1
)
echo on