@echo off
REM Copyright 2016 the original author or authors. All rights reserved.
REM site: http://www.ganshane.com

setlocal
call "%~dp0hall-env.cmd"

set HALLMAIN=nirvana.hall.api.app.HallApiApp

REM set extra_module v62:nirvana.hall.v62.LocalV62Module,nirvana.hall.v62.LocalV62ServiceModule,stark.activerecord.StarkActiveRecordModule,nirvana.hall.v62.LocalV62DataSourceModule
REM set extra_module v70:nirvana.hall.v70.LocalV70Module,nirvana.hall.v70.LocalV70ServiceModule,stark.activerecord.StarkActiveRecordModule,nirvana.hall.v70.LocalDataSourceModule
REM set extra_module gz_v70:nirvana.hall.v70.internal.adapter.gz.LocalV70Module,nirvana.hall.v70.internal.adapter.gz.LocalV70ServiceModule,stark.activerecord.StarkActiveRecordModule,nirvana.hall.v70.internal.adapter.gz.LocalDataSourceModule
set EXTRA_MODULE=nirvana.hall.v62.LocalV62Module,nirvana.hall.v62.LocalV62ServiceModule,stark.activerecord.StarkActiveRecordModule,nirvana.hall.v62.LocalV62DataSourceModule

REM set java options
SET JAVA_OPTIONS=%JAVA_OPTIONS% -Dserver.port=%SERVER_PORT%  -Dserver.home=%SERVER_HOME% -Dapi.extra.module=%EXTRA_MODULE% -Dfile.encoding=utf-8

echo on
call %JAVA% -cp "%CLASSPATH%" %JAVA_OPTIONS% %HALLMAIN%  %*

endlocal
