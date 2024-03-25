@echo off

:: This file will run SlimTrade as administrator.
:: !IMPORTANT! You must edit the very last line of this file to include the path to SlimTrade.jar.

>nul 2>&1 "%SYSTEMROOT%\system32\cacls.exe" "%SYSTEMROOT%\system32\config\system"
if '%errorlevel%' NEQ '0' (goto UACPrompt) else ( goto gotAdmin )
:UACPrompt
echo Set UAC = CreateObject^("Shell.Application"^) > "%temp%\getadmin.vbs"
echo UAC.ShellExecute "%~s0", "", "", "runas", 1 >> "%temp%\getadmin.vbs"
"%temp%\getadmin.vbs"
exit /B
:gotAdmin
if exist "%temp%\getadmin.vbs" ( del "%temp%\getadmin.vbs" )
pushd "%CD%"
CD /D "%~dp0"

:: EDIT HERE
:: Change the file path in the next line to the path to SlimTrade.jar
java -jar "D:\Programming\Java\_EXPORTED JARS\SlimTrade.jar"