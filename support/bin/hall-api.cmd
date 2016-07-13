@echo off
REM Copyright 2016 the original author or authors. All rights reserved.
REM site: http://www.ganshane.com

setlocal
call "%~dp0hall-env.cmd"

set HALLMAIN=nirvana.hall.api.app.HallApiApp
echo on
call %JAVA% -cp "%CLASSPATH%" %HALLMAIN%  %*

endlocal
