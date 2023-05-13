@echo off
rem 删除指定文件夹

rem 项目根目录位于script的上级..
for %%I in ("%~dp0\..") do set "rootDir=%%~fI"

rem 删除指定的目录
set deleteDir1=logs
set deleteDir2=target
set deleteDir3=.idea

rem 删除指定后缀的文件
set deleteFile1=.iml

echo ----------------------------------------
echo project dir: %rootDir%
echo the specified folder will be deleted...
echo ----------------------------------------

echo [project root directory]
rem 删除根目录下文件夹++++++++++++++++++
call :deleteRootDirectory "%rootDir%" "%deleteDir1%"
call :deleteRootDirectory "%rootDir%" "%deleteDir3%"

echo ----------------------------------------

rem 删除子目录下的文件夹
echo [project sub directory]
call :deleteSubDirectory "%rootDir%" "%deleteDir1%"
call :deleteSubDirectory "%rootDir%" "%deleteDir2%"

rem 删除子目录下的文件
echo [project sub file]
call :deleteSubFile "%rootDir%" "%deleteFile1%"

echo ----------------------------------------
exit /b


rem 删除指定目录下的指定文件夹
:deleteRootDirectory
echo ==^> %~2
if exist "%~1\%~2" (
    echo %~1\%~2
    rd /S /Q "%~1\%~2"
)
echo.
exit /b

rem 删除指定目录子目录下的指定文件夹
:deleteSubDirectory
echo ==^> %~2

for /D %%i in ("%~1\*") do (
  if exist "%%i\%~2" (
    echo %%~fi\%~2
    rd /S /Q "%%i\%~2"
  )
)
echo.
exit /b


rem 删除指定目录子目录下的指定文件
:deleteSubFile
echo ==^> %~2

for /D %%i in ("%~1\*") do (
  if exist "%%i\*%~2" (
    echo %%~fi\%~2
	del /S /Q "%%i\*%~2"
  )
)
echo.
exit /b
