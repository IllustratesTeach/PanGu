@echo off
REM Copyright 2016 the original author or authors. All rights reserved.
REM site: http://www.ganshane.com

set HALLCFGDIR=%~dp0%..\config
set HALL_LOG_DIR=%~dp0%..\log

REM for sanity sake assume Java 1.6
REM see: http://java.sun.com/javase/6/docs/technotes/tools/windows/java.html


REM make it work in the release
SET CLASSPATH=%~dp0..\lib\*;%CLASSPATH%

REM make it work for developers
REM SET CLASSPATH=%~dp0..\build\classes;%~dp0..\build\lib\*;%CLASSPATH%

REM set java options
SET JAVA_OPTIONS=-Dserver.port=%SERVER_PORT%  -Dserver.home=%SERVER_HOME%  -Dfile.encoding=utf-8


@REM setup java environment variables

if not defined JAVA_HOME (
  echo Error: JAVA_HOME is not set.
  goto :eof
)

set JAVA_HOME=%JAVA_HOME:"=%

if not exist "%JAVA_HOME%"\bin\java.exe (
  echo Error: JAVA_HOME is incorrectly set.
  goto :eof
)

set JAVA="%JAVA_HOME%"\bin\java
