rm -rf build*
export VERSION=`git log -n1 --pretty=format:%ai|cut -c1-10`
echo $VERSION
cmd /c support\\scripts\\compile-image.bat
#scp build-w32/src/*.dll build-w32/src/*.lib jcai@dev.egfit.com:/opt/app/sites/lichen/nirvana/dll/w32
#scp build-w64/src/*.dll build-w64/src/*.lib jcai@dev.egfit.com:/opt/app/sites/lichen/nirvana/dll/w64
cmd /c support\\scripts\\compile-extractor.bat


