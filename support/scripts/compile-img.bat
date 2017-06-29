@echo off 
 
:: Get Git source last commit time
git log -n1 --pretty=format:%%ai > __v
set/p VERSION=<__v
del __v
echo %VERSION:~0,10%

cd hall-image\c
echo "start building with version %VERSION%"

set ENV_PATH="c:\Program Files (x86)\Microsoft Visual Studio 10.0\VC\vcvarsall.bat"
set KERNEL_DIR="C:\\Users\\jcai\\workspace\\nirvana-kernel"

set ARCH=w32
echo "Building %ARCH% .... "
call %ENV_PATH%  x86
mkdir build-%ARCH%
cd build-%ARCH%
cmake -DKERNEL_DIR=%KERNEL_DIR% -DENABLE_JNI=on -DNIRVANA_SOURCE_VERSION=%VERSION% -DCMAKE_BUILD_TYPE=Release -DARCH=%ARCH% -DNIRVANA_NETWORK_LICENSE=on -G "NMake Makefiles" ..
nmake
cd ..

set ARCH=w64
echo "Building %ARCH% ...."
call %ENV_PATH%  amd64
mkdir build-%ARCH%
cd build-%ARCH%
cmake -DKERNEL_DIR=%KERNEL_DIR% -DENABLE_JNI=on -DNIRVANA_SOURCE_VERSION=%VERSION% -DCMAKE_BUILD_TYPE=Release -DARCH=%ARCH% -DNIRVANA_NETWORK_LICENSE=on -G "NMake Makefiles" ..
nmake


