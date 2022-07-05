package info.kgeorgiy.ja.lymar.implementor;

import info.kgeorgiy.java.advanced.implementor.ImplerException;
import info.kgeorgiy.java.advanced.implementor.JarImpler;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Class that implements source code of class specified by provided token
 *
 * @author Pavel Lymar
 */
public class Implementor implements JarImpler {
    /**
     * @value System line separator
     */
    private final String LINE_SEPARATOR = System.lineSeparator();
    /**
     * @value separator between expressions
     */
    private final String END_OF_EXPRESSION = ";";
    /**
     * @value name of implemented class
     */
    private String className;

    /**
     * Custom {@link Class} to compare methods and store them in {@link HashSet}
     */
    private class ComparableMethod {
        /**
         * @value {@link Method} that is stored in this {@link ComparableMethod}
         */
        private final Method method;

        /**
         * Constructs {@link ComparableMethod}. Default {@link ComparableMethod} constructor.
         *
         * @param method that is stored in this class. Can be accessed by {@link ComparableMethod#getMethod()}
         */
        public ComparableMethod(Method method) {
            this.method = method;
        }

        /**
         * Returns stored method of this class
         *
         * @return stored method
         */
        public Method getMethod() {
            return method;
        }

        @Override
        public String toString() {
            return method.getName() + getParams(method.getParameterTypes(), true);
        }

        @Override
        public int hashCode() {
            return toString().hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof ComparableMethod) {
                Method objMethod = ((ComparableMethod) obj).getMethod();
                return method.getName().equals(objMethod.getName())
                        && Arrays.equals(method.getParameterTypes(), objMethod.getParameterTypes());
            }
            return false;
        }
    }

    /**
     * Returns the exact number of tabulations.
     *
     * @param count amount of tabulations
     * @return string consisting of {@code count} tabulations
     */
    private String tabs(int count) {
        return "\t".repeat(count);
    }

    /**
     * Returns path of file with implementation.
     * Takes package name and replaces "." with {@link java.io.File#separator}.
     *
     * @param directory   parent path
     * @param packageName name of package with implemented class
     * @param extension   of file starting with "."
     * @return {@link Path} of file with implementation
     */
    private Path getFile(Path directory, String packageName, String extension) {
        return Path.of(Paths.get(directory.toString(), packageName.split("\\.")).resolve(className) + extension);
    }


    /**
     * Crates parent directory.
     * Calls {@link Files#createDirectories} inside.
     *
     * @param path from witch parent directory will be created
     * @throws ImplerException when {@link IOException} is occurred
     */
    private void createParent(Path path) throws ImplerException {
        Path parent = path.getParent();
        try {
            if (parent != null) {
                Files.createDirectories(parent);
            }
        } catch (IOException e) {
            throw new ImplerException("Can't create output directory: " + e.getMessage());
        }
    }

    /**
     * Returns {@link Path} to file with implemented class.
     * Also creates parent directory of this path using {@link #createParent}.
     *
     * @param path        to root directory where implemented class should be created
     * @param packageName name of package with implemented class
     * @return {@link Path} to file with implementation
     * @throws ImplerException when it occurred in {@link #createParent}
     */
    private Path createClassFile(Path path, String packageName) throws ImplerException {
        path = getFile(path, packageName, ".java");
        createParent(path);
        return path;
    }

    /**
     * Returns string representation of parameters that {@link Method} takes.
     * Has {@code withTypes} flag that indicates should this representation has type of each parameter.
     *
     * @param parameters is the array of {@link Class} means the type of each parameter
     * @param withTypes  when is {@code true} then generates with argument types
     * @return string representation of parameters that {@link Method} takes
     */
    private String getParams(Class<?>[] parameters, boolean withTypes) {
        return IntStream.range(0, parameters.length)
                .mapToObj(i -> (withTypes ? parameters[i].getCanonicalName() + " " : "") + "param" + i)
                .collect(Collectors.joining(", ", "(", ")"));
    }

    /**
     * Generates string with oll exceptions throwing inside {@code method}.
     * Returns empty string if {@code method} doesn't have throwing exceptions.
     *
     * @param method for witch string of exceptions is generated
     * @return {@link String} of all throwing {@link Exception} starting with {@code throws} key word
     */
    private String getExceptions(Executable method) {
        Class<?>[] exceptionTypes = method.getExceptionTypes();
        if (exceptionTypes.length == 0) {
            return "";
        }
        return Arrays.stream(exceptionTypes).map(Class::getCanonicalName)
                .collect(Collectors.joining(", ", "throws ", " "));
    }

    /**
     * Returns string representation of implementing {@link Method}.
     * Creates full {@code method} statement.
     *
     * @param returnType the type that {@code method} returns
     * @param methodName the name of implementing {@code method}
     * @param methodBody block statement that {@code method} should have inside
     * @param method     to get string representation from
     * @return {@link String} implemented {@code method}
     */
    private String implementMethod(String returnType, String methodName, String methodBody, Executable method) {
        StringBuilder result = new StringBuilder();
        result.append(tabs(1)).append("public ");
        if (!returnType.isEmpty()) {
            result.append(returnType).append(" ");
        }
        result.append(methodName).append(getParams(method.getParameterTypes(), true)).append(" ")
                .append(getExceptions(method)).append("{").append(LINE_SEPARATOR)
                .append(tabs(2)).append(methodBody).append(LINE_SEPARATOR)
                .append(tabs(1)).append("}").append(LINE_SEPARATOR);
        return result.toString();
    }

    /**
     * Takes {@link Constructor} and returns it body.
     * Calls {@code super} class constructor.
     *
     * @param constructor to implement body from
     * @return body of constructor
     */
    private String implementConstructorBody(Constructor<?> constructor) {
        return "super" + getParams(constructor.getParameterTypes(), false) + END_OF_EXPRESSION;
    }

    /**
     * Takes {@link Class} and returns all of it constructors in strings representation.
     * Only not private constructors will be represented.
     *
     * @param token {@link Class} that specifies what class should be implemented
     * @return strings representation all of it constructors that {@code token} has
     * @throws ImplerException when {@code token} is a utility class
     */
    private String implementConstructors(Class<?> token) throws ImplerException {
        StringBuilder result = new StringBuilder();
        Arrays.stream(token.getDeclaredConstructors())
                .filter(constructor -> !Modifier.isPrivate(constructor.getModifiers()))
                .forEach(
                        constructor -> result.append(
                                implementMethod(
                                        "",
                                        className,
                                        implementConstructorBody(constructor),
                                        constructor
                                )
                        )
                );
        if (!token.isInterface() && result.length() == 0) {
            throw new ImplerException("This is a utility class.");
        }
        return result.toString();
    }

    /**
     * Takes {@link Method} and returns it body.
     * Returns the default value of {@link Method#getReturnType}.
     *
     * @param method to implement body from
     * @return method body
     */
    private String implementMethodBody(Method method) {
        if (!method.getReturnType().isPrimitive()) {
            return "return null" + END_OF_EXPRESSION;
        } else if (method.getReturnType().equals(void.class)) {
            return "";
        } else if (method.getReturnType().equals(boolean.class)) {
            return "return false" + END_OF_EXPRESSION;
        } else {
            return "return 0" + END_OF_EXPRESSION;
        }
    }

    /**
     * Returns all methods that {@code token} specifies in {@link String} format.
     * Takes all methods going down the inheritance tree.
     *
     * @param token {@link Class} that specifies what class should be implemented
     * @return string representation of all methods that {@code token} implements
     */
    private String implementMethods(Class<?> token) {
        HashSet<ComparableMethod> methods = Arrays.stream(token.getMethods())
                .map(ComparableMethod::new)
                .collect(Collectors.toCollection(HashSet::new));
        do {
            methods.addAll(
                    Arrays.stream(token.getDeclaredMethods()).map(ComparableMethod::new).collect(Collectors.toList())
            );
            token = token.getSuperclass();
        } while (token != null);

        StringBuilder result = new StringBuilder();
        methods.stream().map(ComparableMethod::getMethod)
                .filter(method -> Modifier.isAbstract(method.getModifiers()))
                .forEach(
                        method -> result.append(
                                implementMethod(
                                        method.getReturnType().getCanonicalName(),
                                        method.getName(),
                                        implementMethodBody(method),
                                        method
                                )
                        )
                );
        return result.toString();
    }

    /**
     * Returns source code of implemented class.
     *
     * @param token {@link Class} that specifies what class should be implemented
     * @return full code of implemented class
     * @throws ImplerException when it occurred inside {@link #implementConstructors}
     */
    private String implement(Class<?> token) throws ImplerException {
        StringBuilder result = new StringBuilder();
        String classPackage = token.getPackageName();
        if (!classPackage.isEmpty()) {
            // :NOTE: extract ;
            result.append("package ").append(classPackage).append(END_OF_EXPRESSION)
                    .append(LINE_SEPARATOR).append(LINE_SEPARATOR);
        }
        result.append("public class ").append(className).append(" ")
                .append(token.isInterface() ? "implements" : "extends").append(" ")
                .append(token.getCanonicalName()).append(" {").append(LINE_SEPARATOR)
                .append(implementConstructors(token))
                .append(implementMethods(token))
                .append("}");
        return result.toString();
    }

    @Override
    public void implement(Class<?> token, Path root) throws ImplerException {
        if (token.equals(Enum.class)
                // :NOTE: isPrimitive
                || token.isPrimitive()
                || Modifier.isFinal(token.getModifiers())
                || Modifier.isPrivate(token.getModifiers())) {
            throw new ImplerException("Can't implement such class.");
        }

        className = token.getSimpleName() + "Impl";

        try (BufferedWriter writer = Files.newBufferedWriter(createClassFile(root, token.getPackageName()))) {
            for (char c : implement(token).toCharArray()) {
                writer.write(c < 128 ? String.valueOf(c) : String.format("\\u%04x", (int) c));
            }
        } catch (IOException e) {
            throw new ImplerException("Output file error: " + e.getMessage());
        }
    }

    /**
     * Compiles generated class.
     *
     * @param aClass {@link Class} that specifies what class should be implemented
     * @param path   root directory path
     * @throws ImplerException when class can't be compiled
     */
    private void compile(Class<?> aClass, Path path) throws ImplerException {
        final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            throw new ImplerException("Can't find java compiler.");
        }
        final String[] args;
        try {
            args = new String[]{
                    getFile(path, aClass.getPackageName(), ".java").toString(),
                    "-cp",
                    Path.of(aClass.getProtectionDomain().getCodeSource().getLocation().toURI()).toString()
            };
        } catch (URISyntaxException e) {
            throw new ImplerException("Compilation error: " + e.getMessage());
        }
        int exitCode = compiler.run(null, null, null, args);
        if (exitCode != 0) {
            throw new ImplerException("Compilation error with exit code " + exitCode);
        }
    }

    /**
     * Creates {@code .jar} file from compiled class.
     *
     * @param aClass {@link Class} that specifies what class should be implemented
     * @param path   root directory path
     * @throws ImplerException when {@link IOException} occurred inside
     */
    private void createJar(Class<?> aClass, Path path) throws ImplerException {
        createParent(path);
        try (JarOutputStream jarOutputStream = new JarOutputStream(Files.newOutputStream(path))) {
            jarOutputStream.putNextEntry(new JarEntry(
                    aClass.getPackageName().replace(".", "/") + "/" + className + ".class"
            ));
            Files.copy(getFile(path.getParent(), aClass.getPackageName(), ".class"), jarOutputStream);
        } catch (IOException e) {
            throw new ImplerException("Can't zip file: " + e.getMessage());
        }
    }

    @Override
    public void implementJar(Class<?> aClass, Path path) throws ImplerException {
        implement(aClass, path.getParent());
        compile(aClass, path.getParent());
        createJar(aClass, path);
    }

    public static void main(String[] args) {
        if (args == null || (args.length != 2 && args.length != 3)) {
            throw new IllegalArgumentException("Invalid program input. Usage: Implementor (-jar) [class name] [output path]");
        }
        Implementor implementor = new Implementor();
        try {
            if ("-jar".equals(args[0])) {
                implementor.implementJar(Class.forName(args[1]), Path.of(args[2]));
            } else {
                implementor.implement(Class.forName(args[0]), Path.of(args[1]));
            }
        } catch (Exception e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }
}
