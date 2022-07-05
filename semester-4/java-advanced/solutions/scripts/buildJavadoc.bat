SET junit=../../java-advanced-2022/lib/quickcheck-0.6.jar:../../java-advanced-2022/lib/junit-4.11.jar
SET implPath=../java-solutions/info/kgeorgiy/ja/lymar/implementor/Implementor.java
SET mainModule=../../java-advanced-2022/modules/info.kgeorgiy.java.advanced.implementor/info/kgeorgiy/java/advanced/implementor/
javadoc -d ../Javadoc -cp %junit% %implPath% %mainModule%ImplerException.java %mainModule%JarImpler.java %mainModule%Impler.java
