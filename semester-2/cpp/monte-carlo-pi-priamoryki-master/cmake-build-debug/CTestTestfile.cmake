# CMake generated Testfile for 
# Source directory: D:/Downloads/monte-carlo-pi-priamoryki-master
# Build directory: D:/Downloads/monte-carlo-pi-priamoryki-master/cmake-build-debug
# 
# This file includes the relevant testing commands required for 
# testing this directory and lists subdirectories to be tested as well.
add_test(tests "D:/Downloads/monte-carlo-pi-priamoryki-master/cmake-build-debug/test/runUnitTests.exe")
set_tests_properties(tests PROPERTIES  _BACKTRACE_TRIPLES "D:/Downloads/monte-carlo-pi-priamoryki-master/CMakeLists.txt;96;add_test;D:/Downloads/monte-carlo-pi-priamoryki-master/CMakeLists.txt;0;")
subdirs("googletest")
subdirs("test")
