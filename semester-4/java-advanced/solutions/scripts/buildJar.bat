SET implPath=../java-solutions/info/kgeorgiy/ja/lymar/implementor/*.java

javac -cp ../../java-advanced-2022/artifacts/info.kgeorgiy.java.advanced.implementor.jar %implPath%
cd ../java-solutions
jar cmvf ../scripts/MANIFEST.MF ../scripts/Implementor.jar info/kgeorgiy/ja/lymar/implementor/*.class
