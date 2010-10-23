@echo on
rem It is advisable to run this script from the command line
rem The following environment variables should be set before starting:
rem JAVA_HOME
rem 

rem Uncomment the following line if you have OutOfMemoryError errors
rem set TT_OPTS=-Xms128m -Xmx256m

rem check that JAVA_HOME is defined
if defined JAVA_HOME goto have_java_home

echo Please set JAVA_HOME to point to the JDK home
goto the_end

:have_java_home

rem check that TT_HOME is defined
echo %TT_HOME%
if defined TT_HOME goto have_tonguetied_home

rem %~dp0 is name of current script under NT
echo Setting TT_HOME to point to the root tonguetied installation
@set TT_HOME=%~dp0
echo TT_HOME = %TT_HOME%
goto have_tonguetied_home

:have_tonguetied_home

@set LIBDIR=%TT_HOME%\libs

"%JAVA_HOME%\bin\java" %TT_OPTS% -jar %LIBDIR%/server.jar"

:the_end
