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
CMAKE_SOURCE_DIR = D:\Downloads\monte-carlo-pi-priamoryki-master

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = D:\Downloads\monte-carlo-pi-priamoryki-master\cmake-build-debug

# Include any dependencies generated for this target.
include CMakeFiles/monte_carlo_pi_lib.dir/depend.make

# Include the progress variables for this target.
include CMakeFiles/monte_carlo_pi_lib.dir/progress.make

# Include the compile flags for this target's objects.
include CMakeFiles/monte_carlo_pi_lib.dir/flags.make

CMakeFiles/monte_carlo_pi_lib.dir/src/pi.cpp.obj: CMakeFiles/monte_carlo_pi_lib.dir/flags.make
CMakeFiles/monte_carlo_pi_lib.dir/src/pi.cpp.obj: CMakeFiles/monte_carlo_pi_lib.dir/includes_CXX.rsp
CMakeFiles/monte_carlo_pi_lib.dir/src/pi.cpp.obj: ../src/pi.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=D:\Downloads\monte-carlo-pi-priamoryki-master\cmake-build-debug\CMakeFiles --progress-num=$(CMAKE_PROGRESS_1) "Building CXX object CMakeFiles/monte_carlo_pi_lib.dir/src/pi.cpp.obj"
	D:\Apps\CLion\MinGW-w64\mingw64\bin\g++.exe  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles\monte_carlo_pi_lib.dir\src\pi.cpp.obj -c D:\Downloads\monte-carlo-pi-priamoryki-master\src\pi.cpp

CMakeFiles/monte_carlo_pi_lib.dir/src/pi.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/monte_carlo_pi_lib.dir/src/pi.cpp.i"
	D:\Apps\CLion\MinGW-w64\mingw64\bin\g++.exe $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E D:\Downloads\monte-carlo-pi-priamoryki-master\src\pi.cpp > CMakeFiles\monte_carlo_pi_lib.dir\src\pi.cpp.i

CMakeFiles/monte_carlo_pi_lib.dir/src/pi.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/monte_carlo_pi_lib.dir/src/pi.cpp.s"
	D:\Apps\CLion\MinGW-w64\mingw64\bin\g++.exe $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S D:\Downloads\monte-carlo-pi-priamoryki-master\src\pi.cpp -o CMakeFiles\monte_carlo_pi_lib.dir\src\pi.cpp.s

CMakeFiles/monte_carlo_pi_lib.dir/src/random_gen.cpp.obj: CMakeFiles/monte_carlo_pi_lib.dir/flags.make
CMakeFiles/monte_carlo_pi_lib.dir/src/random_gen.cpp.obj: CMakeFiles/monte_carlo_pi_lib.dir/includes_CXX.rsp
CMakeFiles/monte_carlo_pi_lib.dir/src/random_gen.cpp.obj: ../src/random_gen.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=D:\Downloads\monte-carlo-pi-priamoryki-master\cmake-build-debug\CMakeFiles --progress-num=$(CMAKE_PROGRESS_2) "Building CXX object CMakeFiles/monte_carlo_pi_lib.dir/src/random_gen.cpp.obj"
	D:\Apps\CLion\MinGW-w64\mingw64\bin\g++.exe  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles\monte_carlo_pi_lib.dir\src\random_gen.cpp.obj -c D:\Downloads\monte-carlo-pi-priamoryki-master\src\random_gen.cpp

CMakeFiles/monte_carlo_pi_lib.dir/src/random_gen.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/monte_carlo_pi_lib.dir/src/random_gen.cpp.i"
	D:\Apps\CLion\MinGW-w64\mingw64\bin\g++.exe $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E D:\Downloads\monte-carlo-pi-priamoryki-master\src\random_gen.cpp > CMakeFiles\monte_carlo_pi_lib.dir\src\random_gen.cpp.i

CMakeFiles/monte_carlo_pi_lib.dir/src/random_gen.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/monte_carlo_pi_lib.dir/src/random_gen.cpp.s"
	D:\Apps\CLion\MinGW-w64\mingw64\bin\g++.exe $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S D:\Downloads\monte-carlo-pi-priamoryki-master\src\random_gen.cpp -o CMakeFiles\monte_carlo_pi_lib.dir\src\random_gen.cpp.s

# Object files for target monte_carlo_pi_lib
monte_carlo_pi_lib_OBJECTS = \
"CMakeFiles/monte_carlo_pi_lib.dir/src/pi.cpp.obj" \
"CMakeFiles/monte_carlo_pi_lib.dir/src/random_gen.cpp.obj"

# External object files for target monte_carlo_pi_lib
monte_carlo_pi_lib_EXTERNAL_OBJECTS =

libmonte_carlo_pi_lib.a: CMakeFiles/monte_carlo_pi_lib.dir/src/pi.cpp.obj
libmonte_carlo_pi_lib.a: CMakeFiles/monte_carlo_pi_lib.dir/src/random_gen.cpp.obj
libmonte_carlo_pi_lib.a: CMakeFiles/monte_carlo_pi_lib.dir/build.make
libmonte_carlo_pi_lib.a: CMakeFiles/monte_carlo_pi_lib.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir=D:\Downloads\monte-carlo-pi-priamoryki-master\cmake-build-debug\CMakeFiles --progress-num=$(CMAKE_PROGRESS_3) "Linking CXX static library libmonte_carlo_pi_lib.a"
	$(CMAKE_COMMAND) -P CMakeFiles\monte_carlo_pi_lib.dir\cmake_clean_target.cmake
	$(CMAKE_COMMAND) -E cmake_link_script CMakeFiles\monte_carlo_pi_lib.dir\link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
CMakeFiles/monte_carlo_pi_lib.dir/build: libmonte_carlo_pi_lib.a

.PHONY : CMakeFiles/monte_carlo_pi_lib.dir/build

CMakeFiles/monte_carlo_pi_lib.dir/clean:
	$(CMAKE_COMMAND) -P CMakeFiles\monte_carlo_pi_lib.dir\cmake_clean.cmake
.PHONY : CMakeFiles/monte_carlo_pi_lib.dir/clean

CMakeFiles/monte_carlo_pi_lib.dir/depend:
	$(CMAKE_COMMAND) -E cmake_depends "MinGW Makefiles" D:\Downloads\monte-carlo-pi-priamoryki-master D:\Downloads\monte-carlo-pi-priamoryki-master D:\Downloads\monte-carlo-pi-priamoryki-master\cmake-build-debug D:\Downloads\monte-carlo-pi-priamoryki-master\cmake-build-debug D:\Downloads\monte-carlo-pi-priamoryki-master\cmake-build-debug\CMakeFiles\monte_carlo_pi_lib.dir\DependInfo.cmake --color=$(COLOR)
.PHONY : CMakeFiles/monte_carlo_pi_lib.dir/depend

