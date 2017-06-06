@echo off
REM Copyright 2016 the original author or authors. All rights reserved.
REM site: http://www.ganshane.com

setlocal
call "%~dp0hall-env.cmd"

set HALLMAIN=nirvana.hall.image.app.HallImageApp

REM set java options
SET JAVA_OPTIONS=%JAVA_OPTIONS% -Dserver.port=%SERVER_PORT%  -Dserver.home=%SERVER_HOME% -Dfile.encoding=utf-8

echo on
call %JAVA% -cp "%CLASSPATH%" %JAVA_OPTIONS% %HALLMAIN%  %*

endlocal
