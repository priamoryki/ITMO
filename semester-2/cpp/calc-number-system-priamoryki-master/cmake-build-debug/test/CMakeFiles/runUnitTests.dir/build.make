# CMAKE generated file: DO NOT EDIT!
# Generated by "MinGW Makefiles" Generator, CMake Version 3.17

# Delete rule output on recipe failure.
.DELETE_ON_ERROR:


#=============================================================================
# Special targets provided by cmake.

# Disable implicit rules so canonical targets will work.
.SUFFIXES:


# Disable VCS-based implicit rules.
% : %,v


# Disable VCS-based implicit rules.
% : RCS/%


# Disable VCS-based implicit rules.
% : RCS/%,v


# Disable VCS-based implicit rules.
% : SCCS/s.%


# Disable VCS-based implicit rules.
% : s.%


.SUFFIXES: .hpux_make_needs_suffix_list


# Command-line flag to silence nested $(MAKE).
$(VERBOSE)MAKESILENT = -s

# Suppress display of executed commands.
$(VERBOSE).SILENT:


# A target that is always out of date.
cmake_force:

.PHONY : cmake_force

#=============================================================================
# Set environment variables for the build.

SHELL = cmd.exe

# The CMake executable.
CMAKE_COMMAND = "D:\Apps\CLion\CLion 2020.3.2\bin\cmake\win\bin\cmake.exe"

# The command to remove a file.
RM = "D:\Apps\CLion\CLion 2020.3.2\bin\cmake\win\bin\cmake.exe" -E rm -f

# Escaping for special characters.
EQUALS = =

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = D:\Downloads\calc-number-system-priamoryki-master

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = D:\Downloads\calc-number-system-priamoryki-master\cmake-build-debug

# Include any dependencies generated for this target.
include test/CMakeFiles/runUnitTests.dir/depend.make

# Include the progress variables for this target.
include test/CMakeFiles/runUnitTests.dir/progress.make

# Include the compile flags for this target's objects.
include test/CMakeFiles/runUnitTests.dir/flags.make

test/CMakeFiles/runUnitTests.dir/src/test.cpp.obj: test/CMakeFiles/runUnitTests.dir/flags.make
test/CMakeFiles/runUnitTests.dir/src/test.cpp.obj: test/CMakeFiles/runUnitTests.dir/includes_CXX.rsp
test/CMakeFiles/runUnitTests.dir/src/test.cpp.obj: ../test/src/test.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=D:\Downloads\calc-number-system-priamoryki-master\cmake-build-debug\CMakeFiles --progress-num=$(CMAKE_PROGRESS_1) "Building CXX object test/CMakeFiles/runUnitTests.dir/src/test.cpp.obj"
	cd /d D:\Downloads\calc-number-system-priamoryki-master\cmake-build-debug\test && D:\Apps\CLion\MinGW-w64\mingw64\bin\g++.exe  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles\runUnitTests.dir\src\test.cpp.obj -c D:\Downloads\calc-number-system-priamoryki-master\test\src\test.cpp

test/CMakeFiles/runUnitTests.dir/src/test.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/runUnitTests.dir/src/test.cpp.i"
	cd /d D:\Downloads\calc-number-system-priamoryki-master\cmake-build-debug\test && D:\Apps\CLion\MinGW-w64\mingw64\bin\g++.exe $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E D:\Downloads\calc-number-system-priamoryki-master\test\src\test.cpp > CMakeFiles\runUnitTests.dir\src\test.cpp.i

test/CMakeFiles/runUnitTests.dir/src/test.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/runUnitTests.dir/src/test.cpp.s"
	cd /d D:\Downloads\calc-number-system-priamoryki-master\cmake-build-debug\test && D:\Apps\CLion\MinGW-w64\mingw64\bin\g++.exe $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S D:\Downloads\calc-number-system-priamoryki-master\test\src\test.cpp -o CMakeFiles\runUnitTests.dir\src\test.cpp.s

# Object files for target runUnitTests
runUnitTests_OBJECTS = \
"CMakeFiles/runUnitTests.dir/src/test.cpp.obj"

# External object files for target runUnitTests
runUnitTests_EXTERNAL_OBJECTS =

test/runUnitTests.exe: test/CMakeFiles/runUnitTests.dir/src/test.cpp.obj
test/runUnitTests.exe: test/CMakeFiles/runUnitTests.dir/build.make
test/runUnitTests.exe: lib/libgtestd.a
test/runUnitTests.exe: lib/libgtest_maind.a
test/runUnitTests.exe: libcalc_number_system_lib.a
test/runUnitTests.exe: lib/libgtestd.a
test/runUnitTests.exe: test/CMakeFiles/runUnitTests.dir/linklibs.rsp
test/runUnitTests.exe: test/CMakeFiles/runUnitTests.dir/objects1.rsp
test/runUnitTests.exe: test/CMakeFiles/runUnitTests.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir=D:\Downloads\calc-number-system-priamoryki-master\cmake-build-debug\CMakeFiles --progress-num=$(CMAKE_PROGRESS_2) "Linking CXX executable runUnitTests.exe"
	cd /d D:\Downloads\calc-number-system-priamoryki-master\cmake-build-debug\test && $(CMAKE_COMMAND) -E cmake_link_script CMakeFiles\runUnitTests.dir\link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
test/CMakeFiles/runUnitTests.dir/build: test/runUnitTests.exe

.PHONY : test/CMakeFiles/runUnitTests.dir/build

test/CMakeFiles/runUnitTests.dir/clean:
	cd /d D:\Downloads\calc-number-system-priamoryki-master\cmake-build-debug\test && $(CMAKE_COMMAND) -P CMakeFiles\runUnitTests.dir\cmake_clean.cmake
.PHONY : test/CMakeFiles/runUnitTests.dir/clean

test/CMakeFiles/runUnitTests.dir/depend:
	$(CMAKE_COMMAND) -E cmake_depends "MinGW Makefiles" D:\Downloads\calc-number-system-priamoryki-master D:\Downloads\calc-number-system-priamoryki-master\test D:\Downloads\calc-number-system-priamoryki-master\cmake-build-debug D:\Downloads\calc-number-system-priamoryki-master\cmake-build-debug\test D:\Downloads\calc-number-system-priamoryki-master\cmake-build-debug\test\CMakeFiles\runUnitTests.dir\DependInfo.cmake --color=$(COLOR)
.PHONY : test/CMakeFiles/runUnitTests.dir/depend

