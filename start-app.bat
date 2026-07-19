@echo off
setlocal

set "JAVA_HOME=C:\Program Files\Java\jdk-17"
set "PATH=%JAVA_HOME%\bin;%PATH%"
cd /d "%~dp0"

echo Java Interview Coach baslatiliyor...
echo Proje klasoru: %CD%
echo.

mvn spring-boot:run

echo.
echo Uygulama durdu veya bir hata olustu.
pause
