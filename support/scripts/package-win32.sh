rm -rf build*
export VERSION=`git log -n1 --pretty=format:%ai|cut -c1-10`
echo $VERSION
cmd /c support\\scripts\\compile-img.bat
cp hall-image/c/build-w32/hall-image4j.dll hall-image/src/main/resources/META-INF/native/windows32
cp hall-image/c/build-w64/hall-image4j.dll hall-image/src/main/resources/META-INF/native/windows64

#scp build-w32/src/*.dll build-w32/src/*.lib jcai@dev.egfit.com:/opt/app/sites/lichen/nirvana/dll/w32
#scp build-w64/src/*.dll build-w64/src/*.lib jcai@dev.egfit.com:/opt/app/sites/lichen/nirvana/dll/w64
cmd /c support\\scripts\\compile-extractor.bat
cp hall-extractor/c/build-w32/hall-extractor4j.dll hall-extractor/src/main/resources/META-INF/native/windows32
cp hall-extractor/c/build-w64/hall-extractor4j.dll hall-extractor/src/main/resources/META-INF/native/windows64


