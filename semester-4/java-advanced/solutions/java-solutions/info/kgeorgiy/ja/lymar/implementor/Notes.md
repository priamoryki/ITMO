PS D:\university\java2022\java-advanced-private\test\__current-repo\scripts> .\buildJavadoc.bat

D:\university\java2022\java-advanced-private\test\__current-repo\scripts>SET junit=lib/quickcheck-0.6.jar:
lib/junit-4.11.jar

D:\university\java2022\java-advanced-private\test\__current-repo\scripts>SET
implPath=../java-solutions/info/kgeorgiy/ja/lymar/implementor/Implementor.java

D:\university\java2022\java-advanced-private\test\__current-repo\scripts>SET
mainModule=../../shared/modules/info.kgeorgiy.java.advanced.implementor/info/kgeorgiy/java/advanced/impleme ntor/

D:\university\java2022\java-advanced-private\test\__current-repo\scripts>javadoc -d ../Javadoc -cp
lib/quickcheck-0.6.jar:lib/junit-4.11.jar ../java-solutions/info/kgeorgiy/ja/lymar/im plementor/Implementor.java
../../shared/modules/info.kgeorgiy.java.advanced.implementor/info/kgeorgiy/java/advanced/implementor/ImplerException.java
../../shared/modules/info.kgeorgiy. java.advanced.implementor/info/kgeorgiy/java/advanced/implementor/JarImpler.java
../../shared/modules/info.kgeorgiy.java.advanced.implementor/info/kgeorgiy/java/advanced/implementor/Im pler.java
Loading source file ..\java-solutions\info\kgeorgiy\ja\lymar\implementor\Implementor.java... error: File not found: "
../../shared/modules/info.kgeorgiy.java.advanced.implementor/info/kgeorgiy/java/advanced/implementor/ImplerException.java"
1 error PS D:\university\java2022\java-advanced-private\test\__current-repo\scripts> .\buildJar.bat

D:\university\java2022\java-advanced-private\test\__current-repo\scripts>SET
implPath=../java-solutions/info/kgeorgiy/ja/lymar/implementor/Implementor.java

D:\university\java2022\java-advanced-private\test\__current-repo\scripts>javac -cp
../../shared/artifacts/info.kgeorgiy.java.advanced.implementor.jar ../java-solutions/info/kgeorgiy/ja
/lymar/implementor/Implementor.java ..\java-solutions\info\kgeorgiy\ja\lymar\implementor\Implementor.java:3: error:
package info.kgeorgiy.java.advanced.implementor does not exist import
info.kgeorgiy.java.advanced.implementor.ImplerException; ^
..\java-solutions\info\kgeorgiy\ja\lymar\implementor\Implementor.java:4: error: package
info.kgeorgiy.java.advanced.implementor does not exist import info.kgeorgiy.java.advanced.implementor.JarImpler; ^
..\java-solutions\info\kgeorgiy\ja\lymar\implementor\Implementor.java:28: error: cannot find symbol public class
Implementor implements JarImpler { ^ symbol: class JarImpler
..\java-solutions\info\kgeorgiy\ja\lymar\implementor\Implementor.java:121: error: cannot find symbol private void
createParent(Path path) throws ImplerException { ^ symbol:   class ImplerException location: class Implementor
..\java-solutions\info\kgeorgiy\ja\lymar\implementor\Implementor.java:141: error: cannot find symbol private Path
createClassFile(Path path, String packageName) throws ImplerException { ^ symbol:   class ImplerException location:
class Implementor ..\java-solutions\info\kgeorgiy\ja\lymar\implementor\Implementor.java:219: error: cannot find symbol
private String implementConstructors(Class<?> token) throws ImplerException {
                                                                ^
  symbol:   class ImplerException
  location: class Implementor
..\java-solutions\info\kgeorgiy\ja\lymar\implementor\Implementor.java:299: error: cannot find symbol
    private String implement(Class<?> token) throws ImplerException { ^ symbol:   class ImplerException location: class
Implementor ..\java-solutions\info\kgeorgiy\ja\lymar\implementor\Implementor.java:317: error: cannot find symbol public
void implement(Class<?> token, Path root) throws ImplerException {
                                                            ^
  symbol:   class ImplerException
  location: class Implementor
..\java-solutions\info\kgeorgiy\ja\lymar\implementor\Implementor.java:344: error: cannot find symbol
    private void compile(Class<?> aClass, Path path) throws ImplerException { ^ symbol:   class ImplerException
location: class Implementor ..\java-solutions\info\kgeorgiy\ja\lymar\implementor\Implementor.java:372: error: cannot
find symbol private void createJar(Class<?> aClass, Path path) throws ImplerException {
                                                              ^
  symbol:   class ImplerException
  location: class Implementor
..\java-solutions\info\kgeorgiy\ja\lymar\implementor\Implementor.java:388: error: cannot find symbol
    public void implementJar(Class<?> aClass, Path path) throws ImplerException { ^ symbol:   class ImplerException
location: class Implementor ..\java-solutions\info\kgeorgiy\ja\lymar\implementor\Implementor.java:128: error: cannot
find symbol throw new ImplerException("Can't create output directory: " + e.getMessage()); ^ symbol:   class
ImplerException location: class Implementor ..\java-solutions\info\kgeorgiy\ja\lymar\implementor\Implementor.java:234:
error: cannot find symbol throw new ImplerException("This is a utility class."); ^ symbol:   class ImplerException
location: class Implementor ..\java-solutions\info\kgeorgiy\ja\lymar\implementor\Implementor.java:316: error: method
does not override or implement a method from a supertype @Override ^
..\java-solutions\info\kgeorgiy\ja\lymar\implementor\Implementor.java:323: error: cannot find symbol throw new
ImplerException("Can't implement such class."); ^ symbol:   class ImplerException location: class Implementor
..\java-solutions\info\kgeorgiy\ja\lymar\implementor\Implementor.java:333: error: cannot find symbol throw new
ImplerException("Output file error: " + e.getMessage()); ^ symbol:   class ImplerException location: class Implementor
..\java-solutions\info\kgeorgiy\ja\lymar\implementor\Implementor.java:347: error: cannot find symbol throw new
ImplerException("Can't find java compiler."); ^ symbol:   class ImplerException location: class Implementor
..\java-solutions\info\kgeorgiy\ja\lymar\implementor\Implementor.java:357: error: cannot find symbol throw new
ImplerException("Compilation error: " + e.getMessage()); ^ symbol:   class ImplerException location: class Implementor
..\java-solutions\info\kgeorgiy\ja\lymar\implementor\Implementor.java:361: error: cannot find symbol throw new
ImplerException("Compilation error with exit code " + exitCode); ^ symbol:   class ImplerException location: class
Implementor ..\java-solutions\info\kgeorgiy\ja\lymar\implementor\Implementor.java:383: error: cannot find symbol throw
new ImplerException("Can't zip file: " + e.getMessage()); ^ symbol:   class ImplerException location: class Implementor
..\java-solutions\info\kgeorgiy\ja\lymar\implementor\Implementor.java:387: error: method does not override or implement
a method from a supertype @Override ^ 21 errors

D:\university\java2022\java-advanced-private\test\__current-repo\scripts>jar cvf Implementor.jar
../java-solutions/info/kgeorgiy/ja/lymar/implementor/*
added manifest adding: java-solutions/info/kgeorgiy/ja/lymar/implementor/Implementor.java(in = 15746) (out= 3547)(
deflated 77%)
PS D:\university\java2022\java-advanced-private\test\__current-repo\scripts>
